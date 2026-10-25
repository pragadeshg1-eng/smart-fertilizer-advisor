package com.vayal.fertilizeradvisor.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecommendationResponse {

    private Long id;
    private Long farmerId;
    private String farmerName;
    private String location;
    private Double farmSizeAcre;
    private String cropName;
    private String cropCode;
    private String scientificName;
    private String soilType;
    private String growthStage;
    private String growthStageName;
    private Boolean hasSoilTestReport;
    private Boolean isStandardCalibrated;
    private LocalDateTime createdAt;

    // Soil Health Report Card
    private SoilHealthSummary soilHealthSummary;

    // Total Nutrient Requirements for entire farm area (in kg)
    private NutrientRequirement totalSeasonalNutrientRequirement;

    // Current Growth Stage Nutrient Requirements (in kg)
    private NutrientRequirement currentStageNutrientRequirement;

    // Commercial fertilizer bag recommendations
    private List<CommercialBagItem> currentStageFertilizerBags = new ArrayList<>();
    private List<CommercialBagItem> totalSeasonalFertilizerBags = new ArrayList<>();

    // Stage split application calendar
    private List<StageSplitItem> stageSplitSchedule = new ArrayList<>();

    // Organic and Biofertilizer advisory
    private OrganicAndBioPlan organicAndBioPlan;

    // Soil amendment advisory (pH correction)
    private String phAdvisory;

    // Best Management Practices and warnings
    private List<String> agronomicWarnings = new ArrayList<>();
    private List<String> bestManagementPractices = new ArrayList<>();

    // Soil testing guidance (highlighted when test is missing)
    private String soilTestingGuidance;

    // Plain text summary
    private String summaryText;

    public RecommendationResponse() {
    }

    // --- Inner Helper DTOs ---

    public static class SoilHealthSummary {
        private String nitrogenRating;
        private Double nitrogenReported;
        private Double nitrogenNormalisedKgHa;

        private String phosphorusRating;
        private Double phosphorusReported;
        private Double phosphorusNormalisedKgHa;

        private String potassiumRating;
        private Double potassiumReported;
        private Double potassiumNormalisedKgHa;

        private Double phValue;
        private String phClassification;
        private String unitUsed;

        public SoilHealthSummary() {}

        public String getNitrogenRating() { return nitrogenRating; }
        public void setNitrogenRating(String nitrogenRating) { this.nitrogenRating = nitrogenRating; }
        public Double getNitrogenReported() { return nitrogenReported; }
        public void setNitrogenReported(Double nitrogenReported) { this.nitrogenReported = nitrogenReported; }
        public Double getNitrogenNormalisedKgHa() { return nitrogenNormalisedKgHa; }
        public void setNitrogenNormalisedKgHa(Double nitrogenNormalisedKgHa) { this.nitrogenNormalisedKgHa = nitrogenNormalisedKgHa; }
        public String getPhosphorusRating() { return phosphorusRating; }
        public void setPhosphorusRating(String phosphorusRating) { this.phosphorusRating = phosphorusRating; }
        public Double getPhosphorusReported() { return phosphorusReported; }
        public void setPhosphorusReported(Double phosphorusReported) { this.phosphorusReported = phosphorusReported; }
        public Double getPhosphorusNormalisedKgHa() { return phosphorusNormalisedKgHa; }
        public void setPhosphorusNormalisedKgHa(Double phosphorusNormalisedKgHa) { this.phosphorusNormalisedKgHa = phosphorusNormalisedKgHa; }
        public String getPotassiumRating() { return potassiumRating; }
        public void setPotassiumRating(String potassiumRating) { this.potassiumRating = potassiumRating; }
        public Double getPotassiumReported() { return potassiumReported; }
        public void setPotassiumReported(Double potassiumReported) { this.potassiumReported = potassiumReported; }
        public Double getPotassiumNormalisedKgHa() { return potassiumNormalisedKgHa; }
        public void setPotassiumNormalisedKgHa(Double potassiumNormalisedKgHa) { this.potassiumNormalisedKgHa = potassiumNormalisedKgHa; }
        public Double getPhValue() { return phValue; }
        public void setPhValue(Double phValue) { this.phValue = phValue; }
        public String getPhClassification() { return phClassification; }
        public void setPhClassification(String phClassification) { this.phClassification = phClassification; }
        public String getUnitUsed() { return unitUsed; }
        public void setUnitUsed(String unitUsed) { this.unitUsed = unitUsed; }
    }

    public static class NutrientRequirement {
        private Double nitrogenKg;
        private Double phosphorusKg;
        private Double potassiumKg;

        public NutrientRequirement() {}

        public NutrientRequirement(Double nitrogenKg, Double phosphorusKg, Double potassiumKg) {
            this.nitrogenKg = nitrogenKg;
            this.phosphorusKg = phosphorusKg;
            this.potassiumKg = potassiumKg;
        }

        public Double getNitrogenKg() { return nitrogenKg; }
        public void setNitrogenKg(Double nitrogenKg) { this.nitrogenKg = nitrogenKg; }
        public Double getPhosphorusKg() { return phosphorusKg; }
        public void setPhosphorusKg(Double phosphorusKg) { this.phosphorusKg = phosphorusKg; }
        public Double getPotassiumKg() { return potassiumKg; }
        public void setPotassiumKg(Double potassiumKg) { this.potassiumKg = potassiumKg; }
    }

    public static class CommercialBagItem {
        private String fertilizerName;
        private String shortName;
        private Double quantityKg;
        private Double bagsCount;
        private Double bagWeightKg;
        private String applicationTiming;
        private String applicationMethod;

        public CommercialBagItem() {}

        public CommercialBagItem(String fertilizerName, String shortName, Double quantityKg, Double bagsCount, Double bagWeightKg, String applicationTiming, String applicationMethod) {
            this.fertilizerName = fertilizerName;
            this.shortName = shortName;
            this.quantityKg = quantityKg;
            this.bagsCount = bagsCount;
            this.bagWeightKg = bagWeightKg;
            this.applicationTiming = applicationTiming;
            this.applicationMethod = applicationMethod;
        }

        public String getFertilizerName() { return fertilizerName; }
        public void setFertilizerName(String fertilizerName) { this.fertilizerName = fertilizerName; }
        public String getShortName() { return shortName; }
        public void setShortName(String shortName) { this.shortName = shortName; }
        public Double getQuantityKg() { return quantityKg; }
        public void setQuantityKg(Double quantityKg) { this.quantityKg = quantityKg; }
        public Double getBagsCount() { return bagsCount; }
        public void setBagsCount(Double bagsCount) { this.bagsCount = bagsCount; }
        public Double getBagWeightKg() { return bagWeightKg; }
        public void setBagWeightKg(Double bagWeightKg) { this.bagWeightKg = bagWeightKg; }
        public String getApplicationTiming() { return applicationTiming; }
        public void setApplicationTiming(String applicationTiming) { this.applicationTiming = applicationTiming; }
        public String getApplicationMethod() { return applicationMethod; }
        public void setApplicationMethod(String applicationMethod) { this.applicationMethod = applicationMethod; }
    }

    public static class StageSplitItem {
        private String stageCode;
        private String stageName;
        private Double nPercent;
        private Double pPercent;
        private Double kPercent;
        private Double nKg;
        private Double pKg;
        private Double kKg;
        private String instructions;

        public StageSplitItem() {}

        public StageSplitItem(String stageCode, String stageName, Double nPercent, Double pPercent, Double kPercent, Double nKg, Double pKg, Double kKg, String instructions) {
            this.stageCode = stageCode;
            this.stageName = stageName;
            this.nPercent = nPercent;
            this.pPercent = pPercent;
            this.kPercent = kPercent;
            this.nKg = nKg;
            this.pKg = pKg;
            this.kKg = kKg;
            this.instructions = instructions;
        }

        public String getStageCode() { return stageCode; }
        public void setStageCode(String stageCode) { this.stageCode = stageCode; }
        public String getStageName() { return stageName; }
        public void setStageName(String stageName) { this.stageName = stageName; }
        public Double getnPercent() { return nPercent; }
        public void setnPercent(Double nPercent) { this.nPercent = nPercent; }
        public Double getpPercent() { return pPercent; }
        public void setpPercent(Double pPercent) { this.pPercent = pPercent; }
        public Double getkPercent() { return kPercent; }
        public void setkPercent(Double kPercent) { this.kPercent = kPercent; }
        public Double getnKg() { return nKg; }
        public void setnKg(Double nKg) { this.nKg = nKg; }
        public Double getpKg() { return pKg; }
        public void setpKg(Double pKg) { this.pKg = pKg; }
        public Double getkKg() { return kKg; }
        public void setkKg(Double kKg) { this.kKg = kKg; }
        public String getInstructions() { return instructions; }
        public void setInstructions(String instructions) { this.instructions = instructions; }
    }

    public static class OrganicAndBioPlan {
        private Double fymTons;
        private String vermicompostRecommendation;
        private String biofertilizers;
        private String greenManureRecommendation;

        public OrganicAndBioPlan() {}

        public Double getFymTons() { return fymTons; }
        public void setFymTons(Double fymTons) { this.fymTons = fymTons; }
        public String getVermicompostRecommendation() { return vermicompostRecommendation; }
        public void setVermicompostRecommendation(String vermicompostRecommendation) { this.vermicompostRecommendation = vermicompostRecommendation; }
        public String getBiofertilizers() { return biofertilizers; }
        public void setBiofertilizers(String biofertilizers) { this.biofertilizers = biofertilizers; }
        public String getGreenManureRecommendation() { return greenManureRecommendation; }
        public void setGreenManureRecommendation(String greenManureRecommendation) { this.greenManureRecommendation = greenManureRecommendation; }
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getFarmerId() { return farmerId; }
    public void setFarmerId(Long farmerId) { this.farmerId = farmerId; }
    public String getFarmerName() { return farmerName; }
    public void setFarmerName(String farmerName) { this.farmerName = farmerName; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Double getFarmSizeAcre() { return farmSizeAcre; }
    public void setFarmSizeAcre(Double farmSizeAcre) { this.farmSizeAcre = farmSizeAcre; }
    public String getCropName() { return cropName; }
    public void setCropName(String cropName) { this.cropName = cropName; }
    public String getCropCode() { return cropCode; }
    public void setCropCode(String cropCode) { this.cropCode = cropCode; }
    public String getScientificName() { return scientificName; }
    public void setScientificName(String scientificName) { this.scientificName = scientificName; }
    public String getSoilType() { return soilType; }
    public void setSoilType(String soilType) { this.soilType = soilType; }
    public String getGrowthStage() { return growthStage; }
    public void setGrowthStage(String growthStage) { this.growthStage = growthStage; }
    public String getGrowthStageName() { return growthStageName; }
    public void setGrowthStageName(String growthStageName) { this.growthStageName = growthStageName; }
    public Boolean getHasSoilTestReport() { return hasSoilTestReport; }
    public void setHasSoilTestReport(Boolean hasSoilTestReport) { this.hasSoilTestReport = hasSoilTestReport; }
    public Boolean getIsStandardCalibrated() { return isStandardCalibrated; }
    public void setIsStandardCalibrated(Boolean isStandardCalibrated) { this.isStandardCalibrated = isStandardCalibrated; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public SoilHealthSummary getSoilHealthSummary() { return soilHealthSummary; }
    public void setSoilHealthSummary(SoilHealthSummary soilHealthSummary) { this.soilHealthSummary = soilHealthSummary; }
    public NutrientRequirement getTotalSeasonalNutrientRequirement() { return totalSeasonalNutrientRequirement; }
    public void setTotalSeasonalNutrientRequirement(NutrientRequirement totalSeasonalNutrientRequirement) { this.totalSeasonalNutrientRequirement = totalSeasonalNutrientRequirement; }
    public NutrientRequirement getCurrentStageNutrientRequirement() { return currentStageNutrientRequirement; }
    public void setCurrentStageNutrientRequirement(NutrientRequirement currentStageNutrientRequirement) { this.currentStageNutrientRequirement = currentStageNutrientRequirement; }
    public List<CommercialBagItem> getCurrentStageFertilizerBags() { return currentStageFertilizerBags; }
    public void setCurrentStageFertilizerBags(List<CommercialBagItem> currentStageFertilizerBags) { this.currentStageFertilizerBags = currentStageFertilizerBags; }
    public List<CommercialBagItem> getTotalSeasonalFertilizerBags() { return totalSeasonalFertilizerBags; }
    public void setTotalSeasonalFertilizerBags(List<CommercialBagItem> totalSeasonalFertilizerBags) { this.totalSeasonalFertilizerBags = totalSeasonalFertilizerBags; }
    public List<StageSplitItem> getStageSplitSchedule() { return stageSplitSchedule; }
    public void setStageSplitSchedule(List<StageSplitItem> stageSplitSchedule) { this.stageSplitSchedule = stageSplitSchedule; }
    public OrganicAndBioPlan getOrganicAndBioPlan() { return organicAndBioPlan; }
    public void setOrganicAndBioPlan(OrganicAndBioPlan organicAndBioPlan) { this.organicAndBioPlan = organicAndBioPlan; }
    public String getPhAdvisory() { return phAdvisory; }
    public void setPhAdvisory(String phAdvisory) { this.phAdvisory = phAdvisory; }
    public List<String> getAgronomicWarnings() { return agronomicWarnings; }
    public void setAgronomicWarnings(List<String> agronomicWarnings) { this.agronomicWarnings = agronomicWarnings; }
    public List<String> getBestManagementPractices() { return bestManagementPractices; }
    public void setBestManagementPractices(List<String> bestManagementPractices) { this.bestManagementPractices = bestManagementPractices; }
    public String getSoilTestingGuidance() { return soilTestingGuidance; }
    public void setSoilTestingGuidance(String soilTestingGuidance) { this.soilTestingGuidance = soilTestingGuidance; }
    public String getSummaryText() { return summaryText; }
    public void setSummaryText(String summaryText) { this.summaryText = summaryText; }
}
