package com.vayal.fertilizeradvisor.service.strategy;

import com.vayal.fertilizeradvisor.dto.RecommendationRequest;
import com.vayal.fertilizeradvisor.dto.RecommendationResponse;
import com.vayal.fertilizeradvisor.entity.Crop;
import com.vayal.fertilizeradvisor.entity.Fertilizer;
import com.vayal.fertilizeradvisor.entity.GuidanceRule;

import java.util.List;

/**
 * Interface defining the strategy contract for generating fertilizer recommendations.
 * Demonstrates Java OOP Interfaces and Polymorphism.
 */
public interface RecommendationStrategy {

    /**
     * Determines whether this strategy handles the given recommendation request.
     */
    boolean supports(RecommendationRequest request);

    /**
     * Executes agricultural recommendation logic.
     */
    RecommendationResponse generateRecommendation(
            RecommendationRequest request,
            Crop crop,
            List<GuidanceRule> rules,
            List<Fertilizer> fertilizers
    );
}
