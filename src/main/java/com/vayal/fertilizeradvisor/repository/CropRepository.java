package com.vayal.fertilizeradvisor.repository;

import com.vayal.fertilizeradvisor.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
    Optional<Crop> findByCropCodeIgnoreCase(String cropCode);
    Optional<Crop> findByCropNameIgnoreCase(String cropName);
}
