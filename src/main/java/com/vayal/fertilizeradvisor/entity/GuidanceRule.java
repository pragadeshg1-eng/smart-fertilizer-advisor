package com.vayal.fertilizeradvisor.entity;

import jakarta.persistence.*;

/**
 * Entity storing verified, crop-specific agricultural guidance rules.
 */
@Entity
@Table(name = "guidance_rules")
public class GuidanceRule extends BaseEntity {

    @Column(name = "crop_code", nullable = false, length = 50)
    private String cropCode;

    @Column(name = "growth_stage", nullable = false, length = 50)
    private String growthStage; // BASAL, VEGETATIVE, FLOWERING, MATURITY

    @Column(name = "stage_name", nullable = false, length = 100)
    private String stageName;

    // Split ratio percentages of total N, P, K for this stage (e.g., 50 for 50%)
    @Column(name = "nitrogen_split_percent", nullable = false)
    private Double nitrogenSplitPercent = 0.0;

    @Column(name = "phosphorus_split_percent", nullable = false)
    private Double phosphorusSplitPercent = 0.0;

    @Column(name = "potassium_split_percent", nullable = false)
    private Double potassiumSplitPercent = 0.0;

    @Column(name = "stage_advisory", columnDefinition = "TEXT")
    private String stageAdvisory;

    @Column(name = "organic_bio_advisory", columnDefinition = "TEXT")
    private String organicBioAdvisory;

    @Column(name = "general_practice_advisory", columnDefinition = "TEXT")
    private String generalPracticeAdvisory;

    public GuidanceRule() {
    }

    public GuidanceRule(String cropCode, String growthStage, String stageName,
                        Double nitrogenSplitPercent, Double phosphorusSplitPercent, Double potassiumSplitPercent,
                        String stageAdvisory, String organicBioAdvisory, String generalPracticeAdvisory) {
        this.cropCode = cropCode;
        this.growthStage = growthStage;
        this.stageName = stageName;
        this.nitrogenSplitPercent = nitrogenSplitPercent != null ? nitrogenSplitPercent : 0.0;
        this.phosphorusSplitPercent = phosphorusSplitPercent != null ? phosphorusSplitPercent : 0.0;
        this.potassiumSplitPercent = potassiumSplitPercent != null ? potassiumSplitPercent : 0.0;
        this.stageAdvisory = stageAdvisory;
        this.organicBioAdvisory = organicBioAdvisory;
        this.generalPracticeAdvisory = generalPracticeAdvisory;
    }

    public String getCropCode() {
        return cropCode;
    }

    public void setCropCode(String cropCode) {
        this.cropCode = cropCode;
    }

    public String getGrowthStage() {
        return growthStage;
    }

    public void setGrowthStage(String growthStage) {
        this.growthStage = growthStage;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Double getNitrogenSplitPercent() {
        return nitrogenSplitPercent;
    }

    public void setNitrogenSplitPercent(Double nitrogenSplitPercent) {
        this.nitrogenSplitPercent = nitrogenSplitPercent;
    }

    public Double getPhosphorusSplitPercent() {
        return phosphorusSplitPercent;
    }

    public void setPhosphorusSplitPercent(Double phosphorusSplitPercent) {
        this.phosphorusSplitPercent = phosphorusSplitPercent;
    }

    public Double getPotassiumSplitPercent() {
        return potassiumSplitPercent;
    }

    public void setPotassiumSplitPercent(Double potassiumSplitPercent) {
        this.potassiumSplitPercent = potassiumSplitPercent;
    }

    public String getStageAdvisory() {
        return stageAdvisory;
    }

    public void setStageAdvisory(String stageAdvisory) {
        this.stageAdvisory = stageAdvisory;
    }

    public String getOrganicBioAdvisory() {
        return organicBioAdvisory;
    }

    public void setOrganicBioAdvisory(String organicBioAdvisory) {
        this.organicBioAdvisory = organicBioAdvisory;
    }

    public String getGeneralPracticeAdvisory() {
        return generalPracticeAdvisory;
    }

    public void setGeneralPracticeAdvisory(String generalPracticeAdvisory) {
        this.generalPracticeAdvisory = generalPracticeAdvisory;
    }
}
