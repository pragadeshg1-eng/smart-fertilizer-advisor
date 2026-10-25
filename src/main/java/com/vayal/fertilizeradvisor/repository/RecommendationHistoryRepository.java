package com.vayal.fertilizeradvisor.repository;

import com.vayal.fertilizeradvisor.entity.RecommendationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationHistoryRepository extends JpaRepository<RecommendationHistory, Long> {
    List<RecommendationHistory> findByFarmerIdOrderByCreatedAtDesc(Long farmerId);
    List<RecommendationHistory> findAllByOrderByCreatedAtDesc();

    @Query("SELECT r FROM RecommendationHistory r WHERE LOWER(r.farmerName) LIKE LOWER(CONCAT('%', :farmerName, '%')) ORDER BY r.createdAt DESC")
    List<RecommendationHistory> findByFarmerNameContainingIgnoreCaseOrderByCreatedAtDesc(@Param("farmerName") String farmerName);

    List<RecommendationHistory> findByCropCodeIgnoreCaseOrderByCreatedAtDesc(String cropCode);
}
