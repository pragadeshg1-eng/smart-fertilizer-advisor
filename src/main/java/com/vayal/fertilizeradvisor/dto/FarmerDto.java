package com.vayal.fertilizeradvisor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class FarmerDto {
    private Long id;

    @NotBlank(message = "Farmer name must not be blank")
    private String name;

    private String phoneNumber;

    @NotBlank(message = "Location must not be blank")
    private String location;

    @NotNull(message = "Farm size is required")
    @Positive(message = "Farm size must be greater than zero")
    private Double farmSizeAcre;

    private LocalDateTime createdAt;

    public FarmerDto() {
    }

    public FarmerDto(Long id, String name, String phoneNumber, String location, Double farmSizeAcre, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.farmSizeAcre = farmSizeAcre;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
