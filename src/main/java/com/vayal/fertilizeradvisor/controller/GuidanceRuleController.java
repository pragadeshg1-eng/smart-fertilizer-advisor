package com.vayal.fertilizeradvisor.controller;

import com.vayal.fertilizeradvisor.dto.ApiResponse;
import com.vayal.fertilizeradvisor.entity.GuidanceRule;
import com.vayal.fertilizeradvisor.service.GuidanceRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guidance-rules")
@CrossOrigin(origins = "*")
public class GuidanceRuleController {

    private final GuidanceRuleService guidanceRuleService;

    public GuidanceRuleController(GuidanceRuleService guidanceRuleService) {
        this.guidanceRuleService = guidanceRuleService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GuidanceRule>>> getAllRules() {
        List<GuidanceRule> rules = guidanceRuleService.getAllRules();
        return ResponseEntity.ok(ApiResponse.ok("Verified guidance rules retrieved successfully", rules));
    }

    @GetMapping("/crop/{cropCode}")
    public ResponseEntity<ApiResponse<List<GuidanceRule>>> getRulesByCrop(@PathVariable String cropCode) {
        List<GuidanceRule> rules = guidanceRuleService.getRulesByCropCode(cropCode);
        return ResponseEntity.ok(ApiResponse.ok("Guidance rules for crop " + cropCode + " retrieved", rules));
    }
}
