package com.vayal.fertilizeradvisor.service;

import com.vayal.fertilizeradvisor.dto.FarmerDto;
import com.vayal.fertilizeradvisor.entity.Farmer;

import java.util.List;

public interface FarmerService {
    List<FarmerDto> getAllFarmers();
    FarmerDto getFarmerById(Long id);
    FarmerDto createFarmer(FarmerDto dto);
    FarmerDto updateFarmer(Long id, FarmerDto dto);
    void deleteFarmer(Long id);
    Farmer getOrCreateFarmerEntity(String name, String phone, String location, Double farmSize);
}
