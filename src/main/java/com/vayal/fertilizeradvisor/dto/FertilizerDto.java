package com.vayal.fertilizeradvisor.dto;

public class FertilizerDto {
    private Long id;
    private String fertilizerName;
    private String shortName;
    private String fertilizerType;
    private Double nPercent;
    private Double pPercent;
    private Double kPercent;
    private Double bagWeightKg;
    private String description;

    public FertilizerDto() {
    }

    public FertilizerDto(Long id, String fertilizerName, String shortName, String fertilizerType,
                         Double nPercent, Double pPercent, Double kPercent,
                         Double bagWeightKg, String description) {
        this.id = id;
        this.fertilizerName = fertilizerName;
        this.shortName = shortName;
        this.fertilizerType = fertilizerType;
        this.nPercent = nPercent;
        this.pPercent = pPercent;
        this.kPercent = kPercent;
        this.bagWeightKg = bagWeightKg;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFertilizerName() {
        return fertilizerName;
    }

    public void setFertilizerName(String fertilizerName) {
        this.fertilizerName = fertilizerName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFertilizerType() {
        return fertilizerType;
    }

    public void setFertilizerType(String fertilizerType) {
        this.fertilizerType = fertilizerType;
    }

    public Double getnPercent() {
        return nPercent;
    }

    public void setnPercent(Double nPercent) {
        this.nPercent = nPercent;
    }

    public Double getpPercent() {
        return pPercent;
    }

    public void setpPercent(Double pPercent) {
        this.pPercent = pPercent;
    }

    public Double getkPercent() {
        return kPercent;
    }

    public void setkPercent(Double kPercent) {
        this.kPercent = kPercent;
    }

    public Double getBagWeightKg() {
        return bagWeightKg;
    }

    public void setBagWeightKg(Double bagWeightKg) {
        this.bagWeightKg = bagWeightKg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
