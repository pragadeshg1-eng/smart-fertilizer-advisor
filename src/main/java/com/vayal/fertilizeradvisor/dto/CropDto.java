package com.vayal.fertilizeradvisor.dto;

public class CropDto {
    private Long id;
    private String cropCode;
    private String cropName;
    private String scientificName;
    private String category;
    private Double baseNitrogenKgHa;
    private Double basePhosphorusKgHa;
    private Double basePotassiumKgHa;
    private String preferredSoilTypes;
    private Double optimumPhMin;
    private Double optimumPhMax;
    private Double organicManureTonsPerAcre;
    private String description;

    public CropDto() {
    }

    public CropDto(Long id, String cropCode, String cropName, String scientificName, String category,
                   Double baseNitrogenKgHa, Double basePhosphorusKgHa, Double basePotassiumKgHa,
                   String preferredSoilTypes, Double optimumPhMin, Double optimumPhMax,
                   Double organicManureTonsPerAcre, String description) {
        this.id = id;
        this.cropCode = cropCode;
        this.cropName = cropName;
        this.scientificName = scientificName;
        this.category = category;
        this.baseNitrogenKgHa = baseNitrogenKgHa;
        this.basePhosphorusKgHa = basePhosphorusKgHa;
        this.basePotassiumKgHa = basePotassiumKgHa;
        this.preferredSoilTypes = preferredSoilTypes;
        this.optimumPhMin = optimumPhMin;
        this.optimumPhMax = optimumPhMax;
        this.organicManureTonsPerAcre = organicManureTonsPerAcre;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getBaseNitrogenKgHa() {
        return baseNitrogenKgHa;
    }

    public void setBaseNitrogenKgHa(Double baseNitrogenKgHa) {
        this.baseNitrogenKgHa = baseNitrogenKgHa;
    }

    public Double getBasePhosphorusKgHa() {
        return basePhosphorusKgHa;
    }

    public void setBasePhosphorusKgHa(Double basePhosphorusKgHa) {
        this.basePhosphorusKgHa = basePhosphorusKgHa;
    }

    public Double getBasePotassiumKgHa() {
        return basePotassiumKgHa;
    }

    public void setBasePotassiumKgHa(Double basePotassiumKgHa) {
        this.basePotassiumKgHa = basePotassiumKgHa;
    }

    public String getPreferredSoilTypes() {
        return preferredSoilTypes;
    }

    public void setPreferredSoilTypes(String preferredSoilTypes) {
        this.preferredSoilTypes = preferredSoilTypes;
    }

    public Double getOptimumPhMin() {
        return optimumPhMin;
    }

    public void setOptimumPhMin(Double optimumPhMin) {
        this.optimumPhMin = optimumPhMin;
    }

    public Double getOptimumPhMax() {
        return optimumPhMax;
    }

    public void setOptimumPhMax(Double optimumPhMax) {
        this.optimumPhMax = optimumPhMax;
    }

    public Double getOrganicManureTonsPerAcre() {
        return organicManureTonsPerAcre;
    }

    public void setOrganicManureTonsPerAcre(Double organicManureTonsPerAcre) {
        this.organicManureTonsPerAcre = organicManureTonsPerAcre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
