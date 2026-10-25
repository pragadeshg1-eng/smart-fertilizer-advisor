package com.vayal.fertilizeradvisor.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vayal.fertilizeradvisor.dto.RecommendationRequest;
import com.vayal.fertilizeradvisor.dto.RecommendationResponse;
import com.vayal.fertilizeradvisor.dto.RecommendationResponse.*;
import com.vayal.fertilizeradvisor.entity.*;
import com.vayal.fertilizeradvisor.exception.AgriculturalRuleException;
import com.vayal.fertilizeradvisor.exception.ResourceNotFoundException;
import com.vayal.fertilizeradvisor.repository.RecommendationHistoryRepository;
import com.vayal.fertilizeradvisor.service.CropService;
import com.vayal.fertilizeradvisor.service.FarmerService;
import com.vayal.fertilizeradvisor.service.FertilizerService;
import com.vayal.fertilizeradvisor.service.GuidanceRuleService;
import com.vayal.fertilizeradvisor.service.RecommendationService;
import com.vayal.fertilizeradvisor.service.strategy.RecommendationStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecommendationServiceImpl implements RecommendationService {

    private final List<RecommendationStrategy> strategies;
    private final CropService cropService;
    private final FarmerService farmerService;
    private final FertilizerService fertilizerService;
    private final GuidanceRuleService guidanceRuleService;
    private final RecommendationHistoryRepository historyRepository;
    private final ObjectMapper objectMapper;

    public RecommendationServiceImpl(
            List<RecommendationStrategy> strategies,
            CropService cropService,
            FarmerService farmerService,
            FertilizerService fertilizerService,
            GuidanceRuleService guidanceRuleService,
            RecommendationHistoryRepository historyRepository,
            ObjectMapper objectMapper) {
        this.strategies = strategies;
        this.cropService = cropService;
        this.farmerService = farmerService;
        this.fertilizerService = fertilizerService;
        this.guidanceRuleService = guidanceRuleService;
        this.historyRepository = historyRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public RecommendationResponse generateAndSaveRecommendation(RecommendationRequest request) {
        // Validate soil test inputs early if marked as available
        if (Boolean.TRUE.equals(request.getHasSoilTestReport())) {
            if (request.getNitrogenValue() == null || request.getPhosphorusValue() == null ||
                request.getPotassiumValue() == null || request.getSoilPh() == null) {
                throw new com.vayal.fertilizeradvisor.exception.InvalidSoilDataException(
                        "Soil test report is marked as available, but complete N, P, K and pH values were not provided. " +
                        "Please fill in all soil test parameters or select 'No' for general guidance.");
            }
        }

        // 1. Get or create Farmer record
        Farmer farmer = farmerService.getOrCreateFarmerEntity(
                request.getFarmerName(),
                request.getPhoneNumber(),
                request.getLocation(),
                request.getFarmSizeAcre()
        );

        // 2. Retrieve verified Crop standards
        Crop crop = cropService.getCropEntityByCode(request.getCropCode());

        // 3. Retrieve verified Guidance Rules and Fertilizers
        List<GuidanceRule> rules = guidanceRuleService.getRulesByCropCode(crop.getCropCode());
        List<Fertilizer> fertilizers = fertilizerService.getAllFertilizerEntities();

        // 4. Select Strategy dynamically (Strategy Pattern)
        RecommendationStrategy strategy = strategies.stream()
                .filter(s -> s.supports(request))
                .findFirst()
                .orElseThrow(() -> new AgriculturalRuleException("No suitable recommendation strategy found for this request."));

        // 5. Generate Agronomic Recommendation
        RecommendationResponse response = strategy.generateRecommendation(request, crop, rules, fertilizers);
        response.setFarmerId(farmer.getId());

        // 6. Save in MySQL RecommendationHistory
        RecommendationHistory history = new RecommendationHistory();
        history.setFarmerId(farmer.getId());
        history.setFarmerName(farmer.getName());
        history.setLocation(farmer.getLocation());
        history.setFarmSizeAcre(request.getFarmSizeAcre());
        history.setCropCode(crop.getCropCode());
        history.setCropName(crop.getCropName());
        history.setSoilType(request.getSoilType());
        history.setGrowthStage(request.getGrowthStage());
        history.setHasSoilTestReport(request.getHasSoilTestReport());

        if (Boolean.TRUE.equals(request.getHasSoilTestReport())) {
            history.setNitrogenReportedValue(request.getNitrogenValue());
            history.setPhosphorusReportedValue(request.getPhosphorusValue());
            history.setPotassiumReportedValue(request.getPotassiumValue());
            history.setSoilPh(request.getSoilPh());
            history.setUnitUsed(request.getUnit());

            if (response.getSoilHealthSummary() != null) {
                history.setNitrogenRating(response.getSoilHealthSummary().getNitrogenRating());
                history.setPhosphorusRating(response.getSoilHealthSummary().getPhosphorusRating());
                history.setPotassiumRating(response.getSoilHealthSummary().getPotassiumRating());
                history.setPhClassification(response.getSoilHealthSummary().getPhClassification());
            }

            if (response.getTotalSeasonalNutrientRequirement() != null) {
                history.setRecommendedNTotalKg(response.getTotalSeasonalNutrientRequirement().getNitrogenKg());
                history.setRecommendedPTotalKg(response.getTotalSeasonalNutrientRequirement().getPhosphorusKg());
                history.setRecommendedKTotalKg(response.getTotalSeasonalNutrientRequirement().getPotassiumKg());
            }

            if (response.getCurrentStageNutrientRequirement() != null) {
                history.setCurrentStageNKg(response.getCurrentStageNutrientRequirement().getNitrogenKg());
                history.setCurrentStagePKg(response.getCurrentStageNutrientRequirement().getPhosphorusKg());
                history.setCurrentStageKKg(response.getCurrentStageNutrientRequirement().getPotassiumKg());
            }
        } else {
            history.setNitrogenRating("NOT_AVAILABLE");
            history.setPhosphorusRating("NOT_AVAILABLE");
            history.setPotassiumRating("NOT_AVAILABLE");
            history.setPhClassification("Unknown (Lab Analysis Pending)");
            history.setRecommendedNTotalKg(0.0);
            history.setRecommendedPTotalKg(0.0);
            history.setRecommendedKTotalKg(0.0);
        }

        history.setPhAdvisory(response.getPhAdvisory());
        history.setSummaryText(response.getSummaryText());

        // Serialize structured objects to JSON
        try {
            history.setCommercialBagsJson(objectMapper.writeValueAsString(response.getCurrentStageFertilizerBags()));
            history.setStageScheduleJson(objectMapper.writeValueAsString(response.getStageSplitSchedule()));
            history.setGeneralGuidanceJson(objectMapper.writeValueAsString(response.getOrganicAndBioPlan()));
        } catch (Exception e) {
            history.setCommercialBagsJson("[]");
            history.setStageScheduleJson("[]");
            history.setGeneralGuidanceJson("{}");
        }

        RecommendationHistory savedHistory = historyRepository.save(history);
        response.setId(savedHistory.getId());
        response.setCreatedAt(savedHistory.getCreatedAt());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecommendationResponse> getAllRecommendations() {
        return historyRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::mapHistoryToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RecommendationResponse getRecommendationById(Long id) {
        RecommendationHistory history = historyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation history record not found with ID: " + id));
        return mapHistoryToResponse(history);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecommendationResponse> getRecommendationsByFarmerId(Long farmerId) {
        return historyRepository.findByFarmerIdOrderByCreatedAtDesc(farmerId).stream()
                .map(this::mapHistoryToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRecommendation(Long id) {
        if (!historyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recommendation history record not found with ID: " + id);
        }
        historyRepository.deleteById(id);
    }

    private RecommendationResponse mapHistoryToResponse(RecommendationHistory h) {
        RecommendationResponse r = new RecommendationResponse();
        r.setId(h.getId());
        r.setFarmerId(h.getFarmerId());
        r.setFarmerName(h.getFarmerName());
        r.setLocation(h.getLocation());
        r.setFarmSizeAcre(h.getFarmSizeAcre());
        r.setCropCode(h.getCropCode());
        r.setCropName(h.getCropName());
        r.setSoilType(h.getSoilType());
        r.setGrowthStage(h.getGrowthStage());
        r.setGrowthStageName(h.getGrowthStage());
        r.setHasSoilTestReport(h.getHasSoilTestReport());
        r.setIsStandardCalibrated(Boolean.TRUE.equals(h.getHasSoilTestReport()));
        r.setCreatedAt(h.getCreatedAt());
        r.setPhAdvisory(h.getPhAdvisory());
        r.setSummaryText(h.getSummaryText());

        SoilHealthSummary sh = new SoilHealthSummary();
        sh.setNitrogenRating(h.getNitrogenRating());
        sh.setNitrogenReported(h.getNitrogenReportedValue());
        sh.setPhosphorusRating(h.getPhosphorusRating());
        sh.setPhosphorusReported(h.getPhosphorusReportedValue());
        sh.setPotassiumRating(h.getPotassiumRating());
        sh.setPotassiumReported(h.getPotassiumReportedValue());
        sh.setPhValue(h.getSoilPh());
        sh.setPhClassification(h.getPhClassification());
        sh.setUnitUsed(h.getUnitUsed());
        r.setSoilHealthSummary(sh);

        r.setTotalSeasonalNutrientRequirement(new NutrientRequirement(
                h.getRecommendedNTotalKg() != null ? h.getRecommendedNTotalKg() : 0.0,
                h.getRecommendedPTotalKg() != null ? h.getRecommendedPTotalKg() : 0.0,
                h.getRecommendedKTotalKg() != null ? h.getRecommendedKTotalKg() : 0.0
        ));

        r.setCurrentStageNutrientRequirement(new NutrientRequirement(
                h.getCurrentStageNKg() != null ? h.getCurrentStageNKg() : 0.0,
                h.getCurrentStagePKg() != null ? h.getCurrentStagePKg() : 0.0,
                h.getCurrentStageKKg() != null ? h.getCurrentStageKKg() : 0.0
        ));

        try {
            if (h.getCommercialBagsJson() != null && !h.getCommercialBagsJson().isBlank()) {
                r.setCurrentStageFertilizerBags(objectMapper.readValue(
                        h.getCommercialBagsJson(), new TypeReference<List<CommercialBagItem>>() {}));
            }
            if (h.getStageScheduleJson() != null && !h.getStageScheduleJson().isBlank()) {
                r.setStageSplitSchedule(objectMapper.readValue(
                        h.getStageScheduleJson(), new TypeReference<List<StageSplitItem>>() {}));
            }
            if (h.getGeneralGuidanceJson() != null && !h.getGeneralGuidanceJson().isBlank()) {
                r.setOrganicAndBioPlan(objectMapper.readValue(
                        h.getGeneralGuidanceJson(), OrganicAndBioPlan.class));
            }
        } catch (Exception ignored) {
        }

        if (Boolean.FALSE.equals(h.getHasSoilTestReport())) {
            r.setSoilTestingGuidance(
                    "Certified soil test report pending. Please submit a composite soil sample to your nearest KVK or District Soil Testing Lab for chemical fertilizer calibration.");
        }

        return r;
    }
}
