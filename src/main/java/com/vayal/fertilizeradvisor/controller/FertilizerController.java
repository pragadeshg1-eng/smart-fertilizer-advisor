package com.vayal.fertilizeradvisor.controller;

import com.vayal.fertilizeradvisor.dto.ApiResponse;
import com.vayal.fertilizeradvisor.dto.FertilizerDto;
import com.vayal.fertilizeradvisor.service.FertilizerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fertilizers")
@CrossOrigin(origins = "*")
public class FertilizerController {

    private final FertilizerService fertilizerService;

    public FertilizerController(FertilizerService fertilizerService) {
        this.fertilizerService = fertilizerService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FertilizerDto>>> getAllFertilizers() {
        List<FertilizerDto> fertilizers = fertilizerService.getAllFertilizers();
        return ResponseEntity.ok(ApiResponse.ok("Fertilizer standards retrieved successfully", fertilizers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FertilizerDto>> getFertilizerById(@PathVariable Long id) {
        FertilizerDto fertilizer = fertilizerService.getFertilizerById(id);
        return ResponseEntity.ok(ApiResponse.ok(fertilizer));
    }
}
