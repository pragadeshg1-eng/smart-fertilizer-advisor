package com.vayal.fertilizeradvisor.service.impl;

import com.vayal.fertilizeradvisor.dto.FertilizerDto;
import com.vayal.fertilizeradvisor.entity.Fertilizer;
import com.vayal.fertilizeradvisor.exception.ResourceNotFoundException;
import com.vayal.fertilizeradvisor.repository.FertilizerRepository;
import com.vayal.fertilizeradvisor.service.FertilizerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class FertilizerServiceImpl implements FertilizerService {

    private final FertilizerRepository fertilizerRepository;

    public FertilizerServiceImpl(FertilizerRepository fertilizerRepository) {
        this.fertilizerRepository = fertilizerRepository;
    }

    @Override
    public List<FertilizerDto> getAllFertilizers() {
        return fertilizerRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FertilizerDto getFertilizerById(Long id) {
        Fertilizer f = fertilizerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fertilizer not found with ID: " + id));
        return toDto(f);
    }

    @Override
    public List<Fertilizer> getAllFertilizerEntities() {
        return fertilizerRepository.findAll();
    }

    private FertilizerDto toDto(Fertilizer f) {
        return new FertilizerDto(
                f.getId(),
                f.getFertilizerName(),
                f.getShortName(),
                f.getFertilizerType(),
                f.getnPercent(),
                f.getpPercent(),
                f.getkPercent(),
                f.getBagWeightKg(),
                f.getDescription()
        );
    }
}
