package com.vayal.fertilizeradvisor.service.strategy;

import com.vayal.fertilizeradvisor.dto.RecommendationRequest;
import com.vayal.fertilizeradvisor.dto.RecommendationResponse;
import com.vayal.fertilizeradvisor.dto.RecommendationResponse.*;
import com.vayal.fertilizeradvisor.entity.Crop;
import com.vayal.fertilizeradvisor.entity.Fertilizer;
import com.vayal.fertilizeradvisor.entity.GuidanceRule;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Strategy implementation when farmer DOES NOT have a Soil Test Report.
 * Strictly adheres to agronomic ethics:
 * - Does NOT invent NPK values or commercial chemical fertilizer dosages.
 * - Recommends comprehensive soil testing procedures and nearby lab resources.
 * - Provides verified organic manuring, biofertilizer inoculants, and growth stage management.
 */
@Component
public class GeneralGuidanceStrategy implements RecommendationStrategy {

    @Override
    public boolean supports(RecommendationRequest request) {
        return request.getHasSoilTestReport() == null || !request.getHasSoilTestReport();
    }

    @Override
    public RecommendationResponse generateRecommendation(
            RecommendationRequest request,
            Crop crop,
            List<GuidanceRule> rules,
            List<Fertilizer> fertilizers) {

        double farmSize = request.getFarmSizeAcre() != null ? request.getFarmSizeAcre() : 1.0;
        String currentStageCode = request.getGrowthStage() != null ? request.getGrowthStage().toUpperCase().trim() : "BASAL";

        // Find stage rule for contextual stage advice
        GuidanceRule currentStageRule = rules.stream()
                .filter(r -> r.getGrowthStage().equalsIgnoreCase(currentStageCode))
                .findFirst()
                .orElse(!rules.isEmpty() ? rules.get(0) : null);

        // Stage Guidance without synthetic dosage
        List<StageSplitItem> stageItems = new ArrayList<>();
        for (GuidanceRule rule : rules) {
            stageItems.add(new StageSplitItem(
                    rule.getGrowthStage(),
                    rule.getStageName(),
                    rule.getNitrogenSplitPercent(),
                    rule.getPhosphorusSplitPercent(),
                    rule.getPotassiumSplitPercent(),
                    null, // Do not invent numerical kg
                    null,
                    null,
                    rule.getStageAdvisory()
            ));
        }

        // Soil Health Summary indicating uncalibrated state
        SoilHealthSummary sh = new SoilHealthSummary();
        sh.setNitrogenRating("NOT_AVAILABLE");
        sh.setPhosphorusRating("NOT_AVAILABLE");
        sh.setPotassiumRating("NOT_AVAILABLE");
        sh.setPhClassification("Unknown (Lab Analysis Pending)");
        sh.setUnitUsed("N/A");

        // Safe Organic and Biofertilizer Plan
        OrganicAndBioPlan organicPlan = new OrganicAndBioPlan();
        double baseFymRate = crop.getOrganicManureTonsPerAcre() != null ? crop.getOrganicManureTonsPerAcre() : 5.0;
        double fymTotal = round(baseFymRate * farmSize);
        organicPlan.setFymTons(fymTotal);
        organicPlan.setVermicompostRecommendation(String.format(
                "Apply %.1f tons of well-rotted Farmyard Manure (FYM) or %.1f tons of Vermicompost across your %.2f acres during final plowing.",
                fymTotal, fymTotal * 0.4, farmSize));
        organicPlan.setBiofertilizers(
                "Inoculate soil with Azospirillum / Azotobacter @ 2 kg/acre and Phosphate Solubilizing Bacteria (PSB) @ 2 kg/acre mixed with 50 kg farmyard manure. For leguminous crops, use Rhizobium culture.");
        organicPlan.setGreenManureRecommendation(
                "Sow green manure crops such as Daincha (Sesbania aculeata) or Sunn hemp (Crotalaria juncea) @ 20 kg/acre and incorporate into the soil 45 days after sowing before planting the main crop.");

        // Detailed Soil Testing Advisory
        String soilTestingGuide =
                "IMPORTANT NOTICE: Because a certified soil test report is not available for your land, numerical chemical fertilizer dosages are omitted to prevent nutrient imbalance, soil toxicity, and financial loss.\n\n" +
                "Steps to Get Your Soil Tested:\n" +
                "1. Collection Method: Collect soil samples in a 'V' shape cut to a depth of 15 cm (0-6 inches) from 8-10 random spots in a zigzag pattern across your field.\n" +
                "2. Preparation: Mix collected soil thoroughly on a clean tarp, shade-dry, and divide by quartering to retain 500 grams.\n" +
                "3. Submission: Submit the sample to your nearest Krishi Vigyan Kendra (KVK), District Soil Testing Laboratory, or Assistant Director of Agriculture (ADA) office.\n" +
                "4. Parameters to Request: Available Nitrogen (N), Available Phosphorus (P), Available Potassium (K), Soil pH, and Electrical Conductivity (EC).";

        // Agronomic Warnings
        List<String> warnings = new ArrayList<>();
        warnings.add("Applying chemical fertilizers without soil testing risks soil acidification, groundwater nitrate pollution, and crop lodging.");
        warnings.add("Excessive urea application in uncalibrated fields attracts sucking pests (aphids, BPH) and blast fungal infections.");
        warnings.add("Do not apply chemical fertilizers directly to dry soil; adequate soil moisture is mandatory.");

        // Best Management Practices
        List<String> bmpList = new ArrayList<>();
        bmpList.add("Rely on organic amendments (compost, FYM, green manure) as the primary nutrient foundation until soil test results are received.");
        bmpList.add("Practice crop rotation with legumes (cowpea, gram, groundnut) to naturally enrich soil nitrogen.");
        bmpList.add("Ensure proper land leveling to prevent localized water stagnation and nutrient runoff.");
        bmpList.add(String.format("Official ICAR Reference Standard for %s: %s kg N-P2O5-K2O/ha (Provided as regional benchmark for discussion with your local agriculture extension officer).",
                crop.getCropName(),
                String.format("%.0f:%.0f:%.0f", crop.getBaseNitrogenKgHa(), crop.getBasePhosphorusKgHa(), crop.getBasePotassiumKgHa())));

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
        response.setHasSoilTestReport(false);
        response.setIsStandardCalibrated(false);
        response.setCreatedAt(LocalDateTime.now());
        response.setSoilHealthSummary(sh);

        // Nutrient requirements and commercial bags are intentionally omitted/empty
        response.setTotalSeasonalNutrientRequirement(new NutrientRequirement(0.0, 0.0, 0.0));
        response.setCurrentStageNutrientRequirement(new NutrientRequirement(0.0, 0.0, 0.0));
        response.setCurrentStageFertilizerBags(new ArrayList<>());
        response.setTotalSeasonalFertilizerBags(new ArrayList<>());

        response.setStageSplitSchedule(stageItems);
        response.setOrganicAndBioPlan(organicPlan);
        response.setPhAdvisory("Soil pH unknown. Chemical amendment (lime/gypsum) is deferred until laboratory pH measurement.");
        response.setAgronomicWarnings(warnings);
        response.setBestManagementPractices(bmpList);
        response.setSoilTestingGuidance(soilTestingGuide);

        String summary = String.format(
                "General Agronomic Guidance for %s (%.2f acres %s, %s): Soil test report was not provided. Standard safe organic practices and biofertilizers are recommended. Chemical fertilizer dosages are omitted pending lab soil test.",
                request.getFarmerName(), farmSize, crop.getCropName(), response.getGrowthStageName());
        response.setSummaryText(summary);

        return response;
    }

    private double round(double val) {
        return BigDecimal.valueOf(val).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
