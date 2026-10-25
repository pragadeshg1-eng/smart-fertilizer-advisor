package com.vayal.fertilizeradvisor.service;

import com.vayal.fertilizeradvisor.dto.RecommendationRequest;
import com.vayal.fertilizeradvisor.dto.RecommendationResponse;
import com.vayal.fertilizeradvisor.exception.InvalidSoilDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("Recommendation Service Integration Tests")
class RecommendationServiceTest {

    @Autowired
    private RecommendationService recommendationService;

    @Test
    @DisplayName("Should generate STCR calibrated recommendation when soil test report is provided")
    void testCalibratedRecommendationWithSoilTest() {
        RecommendationRequest request = new RecommendationRequest();
        request.setFarmerName("Murugan Test");
        request.setLocation("Thanjavur");
        request.setFarmSizeAcre(2.5);
        request.setCropCode("PADDY");
        request.setSoilType("Clay Soil");
        request.setGrowthStage("BASAL");
        request.setHasSoilTestReport(true);
        request.setNitrogenValue(210.0);   // Low (< 280 kg/ha) -> +25%
        request.setPhosphorusValue(15.0);  // Low (< 23 kg/ha) -> +25%
        request.setPotassiumValue(180.0);  // Medium (130-335 kg/ha) -> 100%
        request.setSoilPh(6.5);            // Neutral / Ideal
        request.setUnit("KG_PER_HA");

        RecommendationResponse response = recommendationService.generateAndSaveRecommendation(request);

        assertNotNull(response);
        assertNotNull(response.getId(), "Recommendation should be persisted and have an ID");
        assertTrue(response.getHasSoilTestReport());
        assertTrue(response.getIsStandardCalibrated());

        // Verify Nutrient Classification
        assertNotNull(response.getSoilHealthSummary());
        assertEquals("LOW", response.getSoilHealthSummary().getNitrogenRating());
        assertEquals("LOW", response.getSoilHealthSummary().getPhosphorusRating());
        assertEquals("MEDIUM", response.getSoilHealthSummary().getPotassiumRating());
        assertEquals(6.5, response.getSoilHealthSummary().getPhValue());

        // Verify that commercial bags were calculated
        assertNotNull(response.getCurrentStageFertilizerBags());
        assertFalse(response.getCurrentStageFertilizerBags().isEmpty(), "Commercial bags should be generated for basal stage");

        // Verify stage split schedule exists
        assertNotNull(response.getStageSplitSchedule());
        assertEquals(4, response.getStageSplitSchedule().size(), "Paddy should have 4 growth stages");

        // Verify organic and bio plan exists
        assertNotNull(response.getOrganicAndBioPlan());
        assertTrue(response.getOrganicAndBioPlan().getFymTons() > 0);
    }

    @Test
    @DisplayName("Should NOT invent chemical dosages when soil test report is NOT available")
    void testGeneralGuidanceWhenNoSoilTestReport() {
        RecommendationRequest request = new RecommendationRequest();
        request.setFarmerName("Ramesh No Report");
        request.setLocation("Solapur");
        request.setFarmSizeAcre(3.0);
        request.setCropCode("COTTON");
        request.setSoilType("Black Cotton Soil");
        request.setGrowthStage("VEGETATIVE");
        request.setHasSoilTestReport(false); // No soil test report

        RecommendationResponse response = recommendationService.generateAndSaveRecommendation(request);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertFalse(response.getHasSoilTestReport());
        assertFalse(response.getIsStandardCalibrated());

        // Chemical fertilizer bags MUST be empty (Requirement 4 & 5)
        assertTrue(response.getCurrentStageFertilizerBags().isEmpty(), "No commercial chemical bags should be invented without soil test");
        assertEquals("NOT_AVAILABLE", response.getSoilHealthSummary().getNitrogenRating());

        // Soil testing guidance MUST be present to guide the farmer
        assertNotNull(response.getSoilTestingGuidance());
        assertTrue(response.getSoilTestingGuidance().contains("soil sample"));

        // Organic manure guidance should be present
        assertNotNull(response.getOrganicAndBioPlan());
        assertTrue(response.getOrganicAndBioPlan().getFymTons() > 0);
    }

    @Test
    @DisplayName("Should throw InvalidSoilDataException when soil test report is claimed but values are null")
    void testThrowsExceptionWhenSoilTestValuesMissing() {
        RecommendationRequest request = new RecommendationRequest();
        request.setFarmerName("Incomplete Farmer");
        request.setLocation("Madurai");
        request.setFarmSizeAcre(1.0);
        request.setCropCode("PADDY");
        request.setSoilType("Clay Soil");
        request.setGrowthStage("BASAL");
        request.setHasSoilTestReport(true);
        // N, P, K, pH intentionally left null

        assertThrows(InvalidSoilDataException.class, () -> {
            recommendationService.generateAndSaveRecommendation(request);
        });
    }
}
