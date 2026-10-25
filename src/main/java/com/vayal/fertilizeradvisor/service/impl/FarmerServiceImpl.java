package com.vayal.fertilizeradvisor.service.impl;

import com.vayal.fertilizeradvisor.dto.FarmerDto;
import com.vayal.fertilizeradvisor.entity.Farmer;
import com.vayal.fertilizeradvisor.exception.ResourceNotFoundException;
import com.vayal.fertilizeradvisor.repository.FarmerRepository;
import com.vayal.fertilizeradvisor.service.FarmerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FarmerServiceImpl implements FarmerService {

    private final FarmerRepository farmerRepository;

    public FarmerServiceImpl(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FarmerDto> getAllFarmers() {
        return farmerRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public FarmerDto getFarmerById(Long id) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found with ID: " + id));
        return toDto(farmer);
    }

    @Override
    public FarmerDto createFarmer(FarmerDto dto) {
        Farmer farmer = new Farmer(dto.getName(), dto.getPhoneNumber(), dto.getLocation(), dto.getFarmSizeAcre());
        Farmer saved = farmerRepository.save(farmer);
        return toDto(saved);
    }

    @Override
    public FarmerDto updateFarmer(Long id, FarmerDto dto) {
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found with ID: " + id));

        farmer.setName(dto.getName());
        farmer.setPhoneNumber(dto.getPhoneNumber());
        farmer.setLocation(dto.getLocation());
        farmer.setFarmSizeAcre(dto.getFarmSizeAcre());

        Farmer updated = farmerRepository.save(farmer);
        return toDto(updated);
    }

    @Override
    public void deleteFarmer(Long id) {
        if (!farmerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Farmer not found with ID: " + id);
        }
        farmerRepository.deleteById(id);
    }

    @Override
    public Farmer getOrCreateFarmerEntity(String name, String phone, String location, Double farmSize) {
        List<Farmer> existing = farmerRepository.findByNameIgnoreCase(name.trim());
        if (!existing.isEmpty()) {
            Farmer f = existing.get(0);
            if (farmSize != null && farmSize > 0) f.setFarmSizeAcre(farmSize);
            if (location != null && !location.isBlank()) f.setLocation(location);
            if (phone != null && !phone.isBlank()) f.setPhoneNumber(phone);
            return farmerRepository.save(f);
        }
        Farmer f = new Farmer(name.trim(), phone, location, farmSize != null ? farmSize : 1.0);
        return farmerRepository.save(f);
    }

    private FarmerDto toDto(Farmer farmer) {
        return new FarmerDto(
                farmer.getId(),
                farmer.getName(),
                farmer.getPhoneNumber(),
                farmer.getLocation(),
                farmer.getFarmSizeAcre(),
                farmer.getCreatedAt()
        );
    }
}
