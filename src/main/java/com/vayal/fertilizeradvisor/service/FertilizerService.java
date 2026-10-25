package com.vayal.fertilizeradvisor.service;

import com.vayal.fertilizeradvisor.dto.FertilizerDto;
import com.vayal.fertilizeradvisor.entity.Fertilizer;

import java.util.List;

public interface FertilizerService {
    List<FertilizerDto> getAllFertilizers();
    FertilizerDto getFertilizerById(Long id);
    List<Fertilizer> getAllFertilizerEntities();
}
