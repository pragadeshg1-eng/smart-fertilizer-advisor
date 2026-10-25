package com.vayal.fertilizeradvisor.entity;

import jakarta.persistence.*;

/**
 * Entity representing commercial and organic fertilizer specifications.
 */
@Entity
@Table(name = "fertilizers")
public class Fertilizer extends BaseEntity {

    @Column(name = "fertilizer_name", nullable = false, unique = true, length = 100)
    private String fertilizerName;

    @Column(name = "short_name", length = 30)
    private String shortName;

    @Column(name = "fertilizer_type", nullable = false, length = 50)
    private String fertilizerType; // CHEMICAL, ORGANIC, BIO_FERTILIZER

    @Column(name = "n_percent", nullable = false)
    private Double nPercent = 0.0;

    @Column(name = "p_percent", nullable = false)
    private Double pPercent = 0.0;

    @Column(name = "k_percent", nullable = false)
    private Double kPercent = 0.0;

    @Column(name = "bag_weight_kg", nullable = false)
    private Double bagWeightKg = 50.0;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public Fertilizer() {
    }

    public Fertilizer(String fertilizerName, String shortName, String fertilizerType,
                      Double nPercent, Double pPercent, Double kPercent,
                      Double bagWeightKg, String description) {
        this.fertilizerName = fertilizerName;
        this.shortName = shortName;
        this.fertilizerType = fertilizerType;
        this.nPercent = nPercent != null ? nPercent : 0.0;
        this.pPercent = pPercent != null ? pPercent : 0.0;
        this.kPercent = kPercent != null ? kPercent : 0.0;
        this.bagWeightKg = bagWeightKg != null ? bagWeightKg : 50.0;
        this.description = description;
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
