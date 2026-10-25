package com.vayal.fertilizeradvisor.service;

import com.vayal.fertilizeradvisor.dto.RecommendationRequest;
import com.vayal.fertilizeradvisor.dto.RecommendationResponse;

import java.util.List;

public interface RecommendationService {
    RecommendationResponse generateAndSaveRecommendation(RecommendationRequest request);
    List<RecommendationResponse> getAllRecommendations();
    RecommendationResponse getRecommendationById(Long id);
    List<RecommendationResponse> getRecommendationsByFarmerId(Long farmerId);
    void deleteRecommendation(Long id);
}
