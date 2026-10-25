package com.vayal.fertilizeradvisor.controller;

import com.vayal.fertilizeradvisor.dto.ApiResponse;
import com.vayal.fertilizeradvisor.dto.FarmerDto;
import com.vayal.fertilizeradvisor.service.FarmerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farmers")
@CrossOrigin(origins = "*")
public class FarmerController {

    private final FarmerService farmerService;

    public FarmerController(FarmerService farmerService) {
        this.farmerService = farmerService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FarmerDto>>> getAllFarmers() {
        List<FarmerDto> farmers = farmerService.getAllFarmers();
        return ResponseEntity.ok(ApiResponse.ok("Farmers retrieved successfully", farmers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FarmerDto>> getFarmerById(@PathVariable Long id) {
        FarmerDto farmer = farmerService.getFarmerById(id);
        return ResponseEntity.ok(ApiResponse.ok(farmer));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FarmerDto>> createFarmer(@Valid @RequestBody FarmerDto dto) {
        FarmerDto created = farmerService.createFarmer(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Farmer profile registered successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FarmerDto>> updateFarmer(
            @PathVariable Long id,
            @Valid @RequestBody FarmerDto dto) {
        FarmerDto updated = farmerService.updateFarmer(id, dto);
        return ResponseEntity.ok(ApiResponse.ok("Farmer profile updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFarmer(@PathVariable Long id) {
        farmerService.deleteFarmer(id);
        return ResponseEntity.ok(ApiResponse.ok("Farmer profile deleted successfully", null));
    }
}
