package com.vayal.fertilizeradvisor.repository;

import com.vayal.fertilizeradvisor.entity.GuidanceRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuidanceRuleRepository extends JpaRepository<GuidanceRule, Long> {
    List<GuidanceRule> findByCropCodeIgnoreCase(String cropCode);
    Optional<GuidanceRule> findByCropCodeIgnoreCaseAndGrowthStageIgnoreCase(String cropCode, String growthStage);
}
