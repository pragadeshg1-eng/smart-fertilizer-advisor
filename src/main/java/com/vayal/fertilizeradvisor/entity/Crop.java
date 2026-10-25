package com.vayal.fertilizeradvisor.entity;

import jakarta.persistence.*;

/**
 * Entity representing verified crop agronomic standards (ICAR/TNAU/FAO).
 */
@Entity
@Table(name = "crops")
public class Crop extends BaseEntity {

    @Column(name = "crop_code", nullable = false, unique = true, length = 50)
    private String cropCode;

    @Column(name = "crop_name", nullable = false, length = 100)
    private String cropName;

    @Column(name = "scientific_name", length = 150)
    private String scientificName;

    @Column(name = "category", length = 50)
    private String category;

    // Recommended Dose of Fertilizers (RDF) in kg/ha
    @Column(name = "base_nitrogen_kg_ha", nullable = false)
    private Double baseNitrogenKgHa;

    @Column(name = "base_phosphorus_kg_ha", nullable = false)
    private Double basePhosphorusKgHa;

    @Column(name = "base_potassium_kg_ha", nullable = false)
    private Double basePotassiumKgHa;

    @Column(name = "preferred_soil_types", length = 255)
    private String preferredSoilTypes;

    @Column(name = "optimum_ph_min")
    private Double optimumPhMin = 6.0;

    @Column(name = "optimum_ph_max")
    private Double optimumPhMax = 7.5;

    @Column(name = "organic_manure_tons_per_acre")
    private Double organicManureTonsPerAcre = 5.0;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public Crop() {
    }

    public Crop(String cropCode, String cropName, String scientificName, String category,
                Double baseNitrogenKgHa, Double basePhosphorusKgHa, Double basePotassiumKgHa,
                String preferredSoilTypes, Double optimumPhMin, Double optimumPhMax,
                Double organicManureTonsPerAcre, String description) {
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
