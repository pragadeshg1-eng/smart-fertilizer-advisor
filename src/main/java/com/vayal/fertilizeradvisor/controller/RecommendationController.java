package com.vayal.fertilizeradvisor.controller;

import com.vayal.fertilizeradvisor.dto.ApiResponse;
import com.vayal.fertilizeradvisor.dto.RecommendationRequest;
import com.vayal.fertilizeradvisor.dto.RecommendationResponse;
import com.vayal.fertilizeradvisor.service.RecommendationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin(origins = "*")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RecommendationResponse>> generateRecommendation(
            @Valid @RequestBody RecommendationRequest request) {
        RecommendationResponse response = recommendationService.generateAndSaveRecommendation(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Fertilizer recommendation generated and saved successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RecommendationResponse>>> getAllRecommendations() {
        List<RecommendationResponse> list = recommendationService.getAllRecommendations();
        return ResponseEntity.ok(ApiResponse.ok("Recommendation history retrieved successfully", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RecommendationResponse>> getRecommendationById(@PathVariable Long id) {
        RecommendationResponse response = recommendationService.getRecommendationById(id);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<ApiResponse<List<RecommendationResponse>>> getRecommendationsByFarmer(
            @PathVariable Long farmerId) {
        List<RecommendationResponse> list = recommendationService.getRecommendationsByFarmerId(farmerId);
        return ResponseEntity.ok(ApiResponse.ok("Farmer recommendations retrieved", list));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRecommendation(@PathVariable Long id) {
        recommendationService.deleteRecommendation(id);
        return ResponseEntity.ok(ApiResponse.ok("Recommendation record deleted successfully", null));
    }
}
