package com.vayal.fertilizeradvisor.repository;

import com.vayal.fertilizeradvisor.entity.Fertilizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FertilizerRepository extends JpaRepository<Fertilizer, Long> {
    Optional<Fertilizer> findByShortNameIgnoreCase(String shortName);
    Optional<Fertilizer> findByFertilizerNameIgnoreCase(String name);
    List<Fertilizer> findByFertilizerTypeIgnoreCase(String fertilizerType);
}
