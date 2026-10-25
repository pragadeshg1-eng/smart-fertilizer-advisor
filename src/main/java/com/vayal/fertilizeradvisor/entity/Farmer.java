package com.vayal.fertilizeradvisor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * Entity representing a farmer profile.
 */
@Entity
@Table(name = "farmers")
public class Farmer extends BaseEntity {

    @NotBlank(message = "Farmer name is required")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @NotBlank(message = "Location is required")
    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Positive(message = "Farm size must be greater than zero")
    @Column(name = "farm_size_acre", nullable = false)
    private Double farmSizeAcre = 1.0;

    public Farmer() {
    }

    public Farmer(String name, String phoneNumber, String location, Double farmSizeAcre) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.farmSizeAcre = farmSizeAcre != null ? farmSizeAcre : 1.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
