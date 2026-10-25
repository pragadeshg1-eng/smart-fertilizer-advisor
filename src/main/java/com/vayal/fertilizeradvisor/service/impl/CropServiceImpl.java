package com.vayal.fertilizeradvisor.service.impl;

import com.vayal.fertilizeradvisor.dto.CropDto;
import com.vayal.fertilizeradvisor.entity.Crop;
import com.vayal.fertilizeradvisor.exception.ResourceNotFoundException;
import com.vayal.fertilizeradvisor.repository.CropRepository;
import com.vayal.fertilizeradvisor.service.CropService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CropServiceImpl implements CropService {

    private final CropRepository cropRepository;

    public CropServiceImpl(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CropDto> getAllCrops() {
        return cropRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CropDto getCropById(Long id) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Crop not found with ID: " + id));
        return toDto(crop);
    }

    @Override
    @Transactional(readOnly = true)
    public CropDto getCropByCode(String code) {
        return toDto(getCropEntityByCode(code));
    }

    @Override
    @Transactional(readOnly = true)
    public Crop getCropEntityByCode(String code) {
        return cropRepository.findByCropCodeIgnoreCase(code.trim())
                .or(() -> cropRepository.findByCropNameIgnoreCase(code.trim()))
                .orElseThrow(() -> new ResourceNotFoundException("Crop not found with code: " + code));
    }

    @Override
    public CropDto createCrop(CropDto dto) {
        Crop crop = new Crop(
                dto.getCropCode(),
                dto.getCropName(),
                dto.getScientificName(),
                dto.getCategory(),
                dto.getBaseNitrogenKgHa(),
                dto.getBasePhosphorusKgHa(),
                dto.getBasePotassiumKgHa(),
                dto.getPreferredSoilTypes(),
                dto.getOptimumPhMin(),
                dto.getOptimumPhMax(),
                dto.getOrganicManureTonsPerAcre(),
                dto.getDescription()
        );
        return toDto(cropRepository.save(crop));
    }

    private CropDto toDto(Crop crop) {
        return new CropDto(
                crop.getId(),
                crop.getCropCode(),
                crop.getCropName(),
                crop.getScientificName(),
                crop.getCategory(),
                crop.getBaseNitrogenKgHa(),
                crop.getBasePhosphorusKgHa(),
                crop.getBasePotassiumKgHa(),
                crop.getPreferredSoilTypes(),
                crop.getOptimumPhMin(),
                crop.getOptimumPhMax(),
                crop.getOrganicManureTonsPerAcre(),
                crop.getDescription()
        );
    }
}
