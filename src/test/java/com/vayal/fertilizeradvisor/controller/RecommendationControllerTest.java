package com.vayal.fertilizeradvisor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vayal.fertilizeradvisor.dto.RecommendationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Recommendation Controller End-to-End REST Tests")
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/crops should return verified crop list")
    void testGetCrops() throws Exception {
        mockMvc.perform(get("/api/crops"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(7))))
                .andExpect(jsonPath("$.data[0].cropCode").isNotEmpty());
    }

    @Test
    @DisplayName("POST /api/recommendations with valid request should return 201 and recommendation payload")
    void testCreateRecommendation() throws Exception {
        RecommendationRequest request = new RecommendationRequest();
        request.setFarmerName("Gurpreet Singh");
        request.setLocation("Ludhiana, Punjab");
        request.setFarmSizeAcre(4.0);
        request.setCropCode("WHEAT");
        request.setSoilType("Alluvial Soil");
        request.setGrowthStage("BASAL");
        request.setHasSoilTestReport(true);
        request.setNitrogenValue(240.0);
        request.setPhosphorusValue(20.0);
        request.setPotassiumValue(150.0);
        request.setSoilPh(7.2);
        request.setUnit("KG_PER_HA");

        mockMvc.perform(post("/api/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.farmerName").value("Gurpreet Singh"))
                .andExpect(jsonPath("$.data.cropCode").value("WHEAT"))
                .andExpect(jsonPath("$.data.hasSoilTestReport").value(true))
                .andExpect(jsonPath("$.data.soilHealthSummary.phValue").value(7.2));
    }

    @Test
    @DisplayName("POST /api/recommendations with missing mandatory fields should return 400 Bad Request")
    void testCreateRecommendationValidationFailure() throws Exception {
        RecommendationRequest invalidRequest = new RecommendationRequest();
        // Missing farmerName, location, cropCode, etc.

        mockMvc.perform(post("/api/recommendations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.fieldErrors").isMap());
    }

    @Test
    @DisplayName("GET /api/recommendations should return history list")
    void testGetRecommendationsHistory() throws Exception {
        mockMvc.perform(get("/api/recommendations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }
}
