package com.vayal.fertilizeradvisor.service;

import com.vayal.fertilizeradvisor.dto.CropDto;
import com.vayal.fertilizeradvisor.entity.Crop;

import java.util.List;

public interface CropService {
    List<CropDto> getAllCrops();
    CropDto getCropById(Long id);
    CropDto getCropByCode(String code);
    Crop getCropEntityByCode(String code);
    CropDto createCrop(CropDto dto);
}
