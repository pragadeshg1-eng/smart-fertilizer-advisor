package com.vayal.fertilizeradvisor.controller;

import com.vayal.fertilizeradvisor.dto.ApiResponse;
import com.vayal.fertilizeradvisor.dto.CropDto;
import com.vayal.fertilizeradvisor.service.CropService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
@CrossOrigin(origins = "*")
public class CropController {

    private final CropService cropService;

    public CropController(CropService cropService) {
        this.cropService = cropService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CropDto>>> getAllCrops() {
        List<CropDto> crops = cropService.getAllCrops();
        return ResponseEntity.ok(ApiResponse.ok("Verified crops retrieved successfully", crops));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CropDto>> getCropById(@PathVariable Long id) {
        CropDto crop = cropService.getCropById(id);
        return ResponseEntity.ok(ApiResponse.ok(crop));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<CropDto>> getCropByCode(@PathVariable String code) {
        CropDto crop = cropService.getCropByCode(code);
        return ResponseEntity.ok(ApiResponse.ok(crop));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CropDto>> createCrop(@Valid @RequestBody CropDto dto) {
        CropDto created = cropService.createCrop(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Crop standard created successfully", created));
    }
}
