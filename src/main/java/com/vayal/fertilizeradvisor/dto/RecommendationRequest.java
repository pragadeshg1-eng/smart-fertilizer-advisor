package com.vayal.fertilizeradvisor.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RecommendationRequest {

    private Long farmerId;

    @NotBlank(message = "Farmer name is required")
    private String farmerName;

    private String phoneNumber;

    @NotBlank(message = "Farm location is required")
    private String location;

    @NotNull(message = "Farm size in acres is required")
    @Positive(message = "Farm size must be greater than zero")
    private Double farmSizeAcre = 1.0;

    @NotBlank(message = "Crop selection is required")
    private String cropCode;

    @NotBlank(message = "Soil type is required")
    private String soilType;

    @NotBlank(message = "Crop growth stage is required")
    private String growthStage;

    @NotNull(message = "Please indicate if you have a soil test report")
    private Boolean hasSoilTestReport = false;

    // Optional fields populated only when hasSoilTestReport is true
    @Positive(message = "Nitrogen value must be positive when reported")
    private Double nitrogenValue;

    @Positive(message = "Phosphorus value must be positive when reported")
    private Double phosphorusValue;

    @Positive(message = "Potassium value must be positive when reported")
    private Double potassiumValue;

    @DecimalMin(value = "3.0", message = "Soil pH must be at least 3.0")
    @DecimalMax(value = "11.0", message = "Soil pH must not exceed 11.0")
    private Double soilPh;

    private String unit = "KG_PER_HA"; // KG_PER_HA, PPM, LBS_PER_ACRE

    private Double organicCarbonPercent;

    public RecommendationRequest() {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Double getNitrogenValue() {
        return nitrogenValue;
    }

    public void setNitrogenValue(Double nitrogenValue) {
        this.nitrogenValue = nitrogenValue;
    }

    public Double getPhosphorusValue() {
        return phosphorusValue;
    }

    public void setPhosphorusValue(Double phosphorusValue) {
        this.phosphorusValue = phosphorusValue;
    }

    public Double getPotassiumValue() {
        return potassiumValue;
    }

    public void setPotassiumValue(Double potassiumValue) {
        this.potassiumValue = potassiumValue;
    }

    public Double getSoilPh() {
        return soilPh;
    }

    public void setSoilPh(Double soilPh) {
        this.soilPh = soilPh;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getOrganicCarbonPercent() {
        return organicCarbonPercent;
    }

    public void setOrganicCarbonPercent(Double organicCarbonPercent) {
        this.organicCarbonPercent = organicCarbonPercent;
    }
}
