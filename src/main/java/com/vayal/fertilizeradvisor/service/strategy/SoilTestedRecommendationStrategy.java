package com.vayal.fertilizeradvisor.service.strategy;

import com.vayal.fertilizeradvisor.dto.RecommendationRequest;
import com.vayal.fertilizeradvisor.dto.RecommendationResponse;
import com.vayal.fertilizeradvisor.dto.RecommendationResponse.*;
import com.vayal.fertilizeradvisor.entity.Crop;
import com.vayal.fertilizeradvisor.entity.Fertilizer;
import com.vayal.fertilizeradvisor.entity.GuidanceRule;
import com.vayal.fertilizeradvisor.exception.InvalidSoilDataException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Strategy implementation when farmer provides a verified Soil Test Report.
 * Uses Soil Test Crop Response (STCR) principles, verified ICAR standard RDF,
 * stage split calendar, and exact commercial bag calculations.
 */
@Component
public class SoilTestedRecommendationStrategy implements RecommendationStrategy {

    private final UnitConverter unitConverter;

    public SoilTestedRecommendationStrategy(UnitConverter unitConverter) {
        this.unitConverter = unitConverter;
    }

    @Override
    public boolean supports(RecommendationRequest request) {
        return Boolean.TRUE.equals(request.getHasSoilTestReport());
    }

    @Override
    public RecommendationResponse generateRecommendation(
            RecommendationRequest request,
            Crop crop,
            List<GuidanceRule> rules,
            List<Fertilizer> fertilizers) {

        // Validate that soil test figures are provided
        if (request.getNitrogenValue() == null || request.getPhosphorusValue() == null ||
            request.getPotassiumValue() == null || request.getSoilPh() == null) {
            throw new InvalidSoilDataException(
                    "Soil test report is marked as available, but complete N, P, K and pH values were not provided. " +
                    "Please fill in all soil test parameters or select 'No' for general guidance.");
        }

        double farmSize = request.getFarmSizeAcre() != null ? request.getFarmSizeAcre() : 1.0;
        String unit = request.getUnit() != null ? request.getUnit() : UnitConverter.UNIT_KG_PER_HA;

        // 1. Convert reported values to standard kg/ha
        double nReported = request.getNitrogenValue();
        double pReported = request.getPhosphorusValue();
        double kReported = request.getPotassiumValue();
        double soilPh = request.getSoilPh();

        double nKgHa = unitConverter.convertNitrogenToKgHa(nReported, unit);
        double pKgHa = unitConverter.convertPhosphorusToKgHa(pReported, unit);
        double kKgHa = unitConverter.convertPotassiumToKgHa(kReported, unit);

        // 2. Classify into Nutrient Ratings (LOW, MEDIUM, HIGH)
        NutrientRating nRating = NutrientRating.classifyNitrogen(nKgHa);
        NutrientRating pRating = NutrientRating.classifyPhosphorus(pKgHa);
        NutrientRating kRating = NutrientRating.classifyPotassium(kKgHa);

        // 3. Calibrate Recommended Dose of Fertilizers (RDF) based on STCR factors
        double adjustedNPerHa = crop.getBaseNitrogenKgHa() * nRating.getAdjustmentFactor();
        double adjustedPPerHa = crop.getBasePhosphorusKgHa() * pRating.getAdjustmentFactor();
        double adjustedKPerHa = crop.getBasePotassiumKgHa() * kRating.getAdjustmentFactor();

        // 4. Scale to farmer's total farm size in acres
        double seasonalTotalNKg = round(unitConverter.scalePerHaToFarmAcre(adjustedNPerHa, farmSize));
        double seasonalTotalPKg = round(unitConverter.scalePerHaToFarmAcre(adjustedPPerHa, farmSize));
        double seasonalTotalKKg = round(unitConverter.scalePerHaToFarmAcre(adjustedKPerHa, farmSize));

        // 5. Stage Splits and Schedule
        List<StageSplitItem> scheduleItems = new ArrayList<>();
        GuidanceRule currentStageRule = null;
        double currentStageNKg = 0.0;
        double currentStagePKg = 0.0;
        double currentStageKKg = 0.0;

        String currentStageCode = request.getGrowthStage() != null ? request.getGrowthStage().toUpperCase().trim() : "BASAL";

        for (GuidanceRule rule : rules) {
            double stageN = round(seasonalTotalNKg * (rule.getNitrogenSplitPercent() / 100.0));
            double stageP = round(seasonalTotalPKg * (rule.getPhosphorusSplitPercent() / 100.0));
            double stageK = round(seasonalTotalKKg * (rule.getPotassiumSplitPercent() / 100.0));

            scheduleItems.add(new StageSplitItem(
                    rule.getGrowthStage(),
                    rule.getStageName(),
                    rule.getNitrogenSplitPercent(),
                    rule.getPhosphorusSplitPercent(),
                    rule.getPotassiumSplitPercent(),
                    stageN,
                    stageP,
                    stageK,
                    rule.getStageAdvisory()
            ));

            if (rule.getGrowthStage().equalsIgnoreCase(currentStageCode)) {
                currentStageRule = rule;
                currentStageNKg = stageN;
                currentStagePKg = stageP;
                currentStageKKg = stageK;
            }
        }

        // Fallback if current stage rule not found
        if (currentStageRule == null && !rules.isEmpty()) {
            currentStageRule = rules.get(0);
            currentStageNKg = scheduleItems.get(0).getnKg();
            currentStagePKg = scheduleItems.get(0).getpKg();
            currentStageKKg = scheduleItems.get(0).getkKg();
        }

        // 6. Calculate Commercial Fertilizer Bags (DAP, Urea, MOP)
        List<CommercialBagItem> currentBags = calculateCommercialBags(currentStageNKg, currentStagePKg, currentStageKKg, currentStageRule != null ? currentStageRule.getStageName() : "Current Stage");
        List<CommercialBagItem> seasonalBags = calculateCommercialBags(seasonalTotalNKg, seasonalTotalPKg, seasonalTotalKKg, "Full Season Total");

        // 7. pH Classification and Soil Amendment Advisory
        String phClassification;
        String phAdvisory;
        if (soilPh < 6.0) {
            phClassification = "Acidic Soil (pH " + soilPh + ")";
            phAdvisory = String.format("Soil is acidic (pH %.2f). Apply Agricultural Dolomite Lime or Calcium Carbonate at 250-350 kg/acre about 2-3 weeks before planting to neutralise active acidity and improve phosphorus uptake.", soilPh);
        } else if (soilPh <= 7.5) {
            phClassification = "Neutral / Ideal (pH " + soilPh + ")";
            phAdvisory = String.format("Soil pH (%.2f) is in the optimal range (6.0 - 7.5) for nutrient availability and root development.", soilPh);
        } else if (soilPh <= 8.5) {
            phClassification = "Moderately Alkaline / Calcareous (pH " + soilPh + ")";
            phAdvisory = String.format("Soil is moderately alkaline (pH %.2f). High calcium may fix phosphorus and induce zinc deficiency. Apply Zinc Sulphate (ZnSO4 @ 10 kg/acre) and incorporate green manure.", soilPh);
        } else {
            phClassification = "Strongly Alkaline / Sodic (pH " + soilPh + ")";
            phAdvisory = String.format("Soil is strongly sodic/alkaline (pH %.2f). Apply Agricultural Gypsum at 400-500 kg/acre followed by copious irrigation and drainage to leach out excess exchangeable sodium.", soilPh);
        }

        // 8. Organic and Biofertilizer Plan
        OrganicAndBioPlan organicPlan = new OrganicAndBioPlan();
        double fymTotal = round((crop.getOrganicManureTonsPerAcre() != null ? crop.getOrganicManureTonsPerAcre() : 5.0) * farmSize);
        organicPlan.setFymTons(fymTotal);
        organicPlan.setVermicompostRecommendation(String.format("Apply %.1f tons of well-decomposed Farmyard Manure (FYM) or %.1f tons of Vermicompost during final land preparation.", fymTotal, fymTotal * 0.4));
        organicPlan.setBiofertilizers("Soil application: Azospirillum / Azotobacter @ 2 kg/acre + Phosphobacteria (PSB) @ 2 kg/acre mixed with 50 kg moist FYM and broadcast before sowing/transplanting.");
        organicPlan.setGreenManureRecommendation("Incorporate in-situ green manure (Daincha / Sunn hemp @ 20 kg seeds/acre) at 45 days after sowing before puddling.");

        // 9. Warnings and Best Practices
        List<String> warnings = new ArrayList<>();
        warnings.add("Do not apply Urea on standing water in paddy; drain water to a thin film before top dressing.");
        warnings.add("Never mix Single Super Phosphate (SSP) or DAP directly with unslaked lime or wood ash, as it fixes phosphorus into insoluble forms.");
        warnings.add("Split nitrogen applications as scheduled; applying excess nitrogen in a single dose triggers vegetative lodging and pest susceptibility.");

        List<String> bmpList = new ArrayList<>();
        bmpList.add("Apply all phosphatic fertilizers (DAP/SSP) as basal placement in the root zone (5 cm deep) for maximum root contact.");
        bmpList.add("Incorporate neem-coated urea to retard nitrification and reduce volatilization losses.");
        bmpList.add("Maintain optimum soil moisture when applying chemical fertilizers to ensure efficient dissolution and uptake.");

        // Build Response
        RecommendationResponse response = new RecommendationResponse();
        response.setFarmerName(request.getFarmerName());
        response.setLocation(request.getLocation());
        response.setFarmSizeAcre(farmSize);
        response.setCropName(crop.getCropName());
        response.setCropCode(crop.getCropCode());
        response.setScientificName(crop.getScientificName());
        response.setSoilType(request.getSoilType());
        response.setGrowthStage(currentStageCode);
        response.setGrowthStageName(currentStageRule != null ? currentStageRule.getStageName() : currentStageCode);
        response.setHasSoilTestReport(true);
        response.setIsStandardCalibrated(true);
        response.setCreatedAt(LocalDateTime.now());

        // Soil health summary
        SoilHealthSummary sh = new SoilHealthSummary();
        sh.setNitrogenRating(nRating.name());
        sh.setNitrogenReported(nReported);
        sh.setNitrogenNormalisedKgHa(round(nKgHa));
        sh.setPhosphorusRating(pRating.name());
        sh.setPhosphorusReported(pReported);
        sh.setPhosphorusNormalisedKgHa(round(pKgHa));
        sh.setPotassiumRating(kRating.name());
        sh.setPotassiumReported(kReported);
        sh.setPotassiumNormalisedKgHa(round(kKgHa));
        sh.setPhValue(soilPh);
        sh.setPhClassification(phClassification);
        sh.setUnitUsed(unit);
        response.setSoilHealthSummary(sh);

        response.setTotalSeasonalNutrientRequirement(new NutrientRequirement(seasonalTotalNKg, seasonalTotalPKg, seasonalTotalKKg));
        response.setCurrentStageNutrientRequirement(new NutrientRequirement(currentStageNKg, currentStagePKg, currentStageKKg));
        response.setCurrentStageFertilizerBags(currentBags);
        response.setTotalSeasonalFertilizerBags(seasonalBags);
        response.setStageSplitSchedule(scheduleItems);
        response.setOrganicAndBioPlan(organicPlan);
        response.setPhAdvisory(phAdvisory);
        response.setAgronomicWarnings(warnings);
        response.setBestManagementPractices(bmpList);
        response.setSoilTestingGuidance("Soil test report calibrated successfully. Periodic soil testing every 2 to 3 years is recommended to monitor nutrient replenishment.");

        String summary = String.format(
                "Soil-Calibrated Fertilizer Plan for %s (%.2f acres %s, %s): Current stage requires N: %.1f kg, P2O5: %.1f kg, K2O: %.1f kg. Total seasonal requirement adjusted for soil status (%s N, %s P, %s K).",
                request.getFarmerName(), farmSize, crop.getCropName(), response.getGrowthStageName(),
                currentStageNKg, currentStagePKg, currentStageKKg,
                nRating.name(), pRating.name(), kRating.name());
        response.setSummaryText(summary);

        return response;
    }

    /**
     * Calculates commercial fertilizer bags (DAP, Urea, MOP) to fulfill elemental N, P2O5, K2O requirements.
     */
    private List<CommercialBagItem> calculateCommercialBags(double nKg, double pKg, double kKg, String timing) {
        List<CommercialBagItem> list = new ArrayList<>();

        // 1. DAP (18% N, 46% P2O5, 50 kg bag)
        double dapKg = 0.0;
        double dapBags = 0.0;
        double nFromDap = 0.0;
        if (pKg > 0) {
            dapKg = round(pKg / 0.46);
            dapBags = round(dapKg / 50.0);
            nFromDap = dapKg * 0.18;
            list.add(new CommercialBagItem(
                    "DAP (Di-ammonium Phosphate 18:46:0)",
                    "DAP",
                    dapKg,
                    dapBags,
                    50.0,
                    timing,
                    "Basal placement in root zone; incorporate during land preparation."
            ));
        }

        // 2. Urea (46% N, 45 kg standard bag)
        double remainingN = Math.max(0.0, nKg - nFromDap);
        if (remainingN > 0) {
            double ureaKg = round(remainingN / 0.46);
            double ureaBags = round(ureaKg / 45.0);
            list.add(new CommercialBagItem(
                    "Neem Coated Urea (46:0:0)",
                    "Urea",
                    ureaKg,
                    ureaBags,
                    45.0,
                    timing,
                    "Broadcasting / top-dressing on moist soil followed by light hoeing."
            ));
        }

        // 3. MOP (Muriate of Potash 0:0:60, 50 kg bag)
        if (kKg > 0) {
            double mopKg = round(kKg / 0.60);
            double mopBags = round(mopKg / 50.0);
            list.add(new CommercialBagItem(
                    "MOP (Muriate of Potash 0:0:60)",
                    "MOP",
                    mopKg,
                    mopBags,
                    50.0,
                    timing,
                    "Soil application along with basal or split as per crop schedule."
            ));
        }

        return list;
    }

    private double round(double val) {
        return BigDecimal.valueOf(val).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
