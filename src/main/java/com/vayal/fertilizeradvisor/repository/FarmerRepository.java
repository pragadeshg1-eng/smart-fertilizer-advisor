package com.vayal.fertilizeradvisor.repository;

import com.vayal.fertilizeradvisor.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    List<Farmer> findByNameIgnoreCase(String name);

    @Query("SELECT f FROM Farmer f WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Farmer> searchByName(@Param("name") String name);
}
