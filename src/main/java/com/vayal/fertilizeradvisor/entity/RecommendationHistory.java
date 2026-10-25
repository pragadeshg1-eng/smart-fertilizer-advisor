package com.vayal.fertilizeradvisor.entity;

import jakarta.persistence.*;

/**
 * Entity storing complete recommendation history for auditing, farmers, and extension workers.
 */
@Entity
@Table(name = "recommendation_history")
public class RecommendationHistory extends BaseEntity {

    @Column(name = "farmer_id")
    private Long farmerId;

    @Column(name = "farmer_name", nullable = false, length = 100)
    private String farmerName;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "farm_size_acre", nullable = false)
    private Double farmSizeAcre = 1.0;

    @Column(name = "crop_code", nullable = false, length = 50)
    private String cropCode;

    @Column(name = "crop_name", nullable = false, length = 100)
    private String cropName;

    @Column(name = "soil_type", nullable = false, length = 50)
    private String soilType;

    @Column(name = "growth_stage", nullable = false, length = 50)
    private String growthStage;

    @Column(name = "has_soil_test_report", nullable = false)
    private Boolean hasSoilTestReport = false;

    // Soil test values (null if no soil test report)
    @Column(name = "nitrogen_reported_value")
    private Double nitrogenReportedValue;

    @Column(name = "phosphorus_reported_value")
    private Double phosphorusReportedValue;

    @Column(name = "potassium_reported_value")
    private Double potassiumReportedValue;

    @Column(name = "soil_ph")
    private Double soilPh;

    @Column(name = "unit_used", length = 30)
    private String unitUsed;

    // Agronomic classification (LOW, MEDIUM, HIGH, NONE)
    @Column(name = "nitrogen_rating", length = 20)
    private String nitrogenRating;

    @Column(name = "phosphorus_rating", length = 20)
    private String phosphorusRating;

    @Column(name = "potassium_rating", length = 20)
    private String potassiumRating;

    @Column(name = "ph_classification", length = 50)
    private String phClassification;

    // Recommended nutrient totals (for entire farm size)
    @Column(name = "recommended_n_total_kg")
    private Double recommendedNTotalKg;

    @Column(name = "recommended_p_total_kg")
    private Double recommendedPTotalKg;

    @Column(name = "recommended_k_total_kg")
    private Double recommendedKTotalKg;

    // Current stage nutrient requirement
    @Column(name = "current_stage_n_kg")
    private Double currentStageNKg;

    @Column(name = "current_stage_p_kg")
    private Double currentStagePKg;

    @Column(name = "current_stage_k_kg")
    private Double currentStageKKg;

    // Detailed JSON payloads for breakdown and UI display
    @Column(name = "commercial_bags_json", columnDefinition = "TEXT")
    private String commercialBagsJson;

    @Column(name = "stage_schedule_json", columnDefinition = "TEXT")
    private String stageScheduleJson;

    @Column(name = "general_guidance_json", columnDefinition = "TEXT")
    private String generalGuidanceJson;

    @Column(name = "ph_advisory", columnDefinition = "TEXT")
    private String phAdvisory;

    @Column(name = "summary_text", columnDefinition = "TEXT")
    private String summaryText;

    public RecommendationHistory() {
    }

    public Long getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Long farmerId) {
        this.farmerId = farmerId;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getFarmSizeAcre() {
        return farmSizeAcre;
    }

    public void setFarmSizeAcre(Double farmSizeAcre) {
        this.farmSizeAcre = farmSizeAcre;
    }

    public String getCropCode() {
        return cropCode;
    }

    public void setCropCode(String cropCode) {
        this.cropCode = cropCode;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public String getGrowthStage() {
        return growthStage;
    }

    public void setGrowthStage(String growthStage) {
        this.growthStage = growthStage;
    }

    public Boolean getHasSoilTestReport() {
        return hasSoilTestReport;
    }

    public void setHasSoilTestReport(Boolean hasSoilTestReport) {
        this.hasSoilTestReport = hasSoilTestReport;
    }

    public Double getNitrogenReportedValue() {
        return nitrogenReportedValue;
    }

    public void setNitrogenReportedValue(Double nitrogenReportedValue) {
        this.nitrogenReportedValue = nitrogenReportedValue;
    }

    public Double getPhosphorusReportedValue() {
        return phosphorusReportedValue;
    }

    public void setPhosphorusReportedValue(Double phosphorusReportedValue) {
        this.phosphorusReportedValue = phosphorusReportedValue;
    }

    public Double getPotassiumReportedValue() {
        return potassiumReportedValue;
    }

    public void setPotassiumReportedValue(Double potassiumReportedValue) {
        this.potassiumReportedValue = potassiumReportedValue;
    }

    public Double getSoilPh() {
        return soilPh;
    }

    public void setSoilPh(Double soilPh) {
        this.soilPh = soilPh;
    }

    public String getUnitUsed() {
        return unitUsed;
    }

    public void setUnitUsed(String unitUsed) {
        this.unitUsed = unitUsed;
    }

    public String getNitrogenRating() {
        return nitrogenRating;
    }

    public void setNitrogenRating(String nitrogenRating) {
        this.nitrogenRating = nitrogenRating;
    }

    public String getPhosphorusRating() {
        return phosphorusRating;
    }

    public void setPhosphorusRating(String phosphorusRating) {
        this.phosphorusRating = phosphorusRating;
    }

    public String getPotassiumRating() {
        return potassiumRating;
    }

    public void setPotassiumRating(String potassiumRating) {
        this.potassiumRating = potassiumRating;
    }

    public String getPhClassification() {
        return phClassification;
    }

    public void setPhClassification(String phClassification) {
        this.phClassification = phClassification;
    }

    public Double getRecommendedNTotalKg() {
        return recommendedNTotalKg;
    }

    public void setRecommendedNTotalKg(Double recommendedNTotalKg) {
        this.recommendedNTotalKg = recommendedNTotalKg;
    }

    public Double getRecommendedPTotalKg() {
        return recommendedPTotalKg;
    }

    public void setRecommendedPTotalKg(Double recommendedPTotalKg) {
        this.recommendedPTotalKg = recommendedPTotalKg;
    }

    public Double getRecommendedKTotalKg() {
        return recommendedKTotalKg;
    }

    public void setRecommendedKTotalKg(Double recommendedKTotalKg) {
        this.recommendedKTotalKg = recommendedKTotalKg;
    }

    public Double getCurrentStageNKg() {
        return currentStageNKg;
    }

    public void setCurrentStageNKg(Double currentStageNKg) {
        this.currentStageNKg = currentStageNKg;
    }

    public Double getCurrentStagePKg() {
        return currentStagePKg;
    }

    public void setCurrentStagePKg(Double currentStagePKg) {
        this.currentStagePKg = currentStagePKg;
    }

    public Double getCurrentStageKKg() {
        return currentStageKKg;
    }

    public void setCurrentStageKKg(Double currentStageKKg) {
        this.currentStageKKg = currentStageKKg;
    }

    public String getCommercialBagsJson() {
        return commercialBagsJson;
    }

    public void setCommercialBagsJson(String commercialBagsJson) {
        this.commercialBagsJson = commercialBagsJson;
    }

    public String getStageScheduleJson() {
        return stageScheduleJson;
    }

    public void setStageScheduleJson(String stageScheduleJson) {
        this.stageScheduleJson = stageScheduleJson;
    }

    public String getGeneralGuidanceJson() {
        return generalGuidanceJson;
    }

    public void setGeneralGuidanceJson(String generalGuidanceJson) {
        this.generalGuidanceJson = generalGuidanceJson;
    }

    public String getPhAdvisory() {
        return phAdvisory;
    }

    public void setPhAdvisory(String phAdvisory) {
        this.phAdvisory = phAdvisory;
    }

    public String getSummaryText() {
        return summaryText;
    }

    public void setSummaryText(String summaryText) {
        this.summaryText = summaryText;
    }
}
