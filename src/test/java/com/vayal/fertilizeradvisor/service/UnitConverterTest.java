package com.vayal.fertilizeradvisor.service;

import com.vayal.fertilizeradvisor.service.strategy.NutrientRating;
import com.vayal.fertilizeradvisor.service.strategy.UnitConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit Converter & Agronomic Rating Tests")
class UnitConverterTest {

    private UnitConverter unitConverter;

    @BeforeEach
    void setUp() {
        unitConverter = new UnitConverter();
    }

    @Test
    @DisplayName("Should convert PPM to standard kg/ha accurately")
    void testPpmConversion() {
        // 100 ppm Nitrogen -> 200 kg/ha
        double nKgHa = unitConverter.convertNitrogenToKgHa(100.0, UnitConverter.UNIT_PPM);
        assertEquals(200.0, nKgHa, 0.01);

        // Standard kg/ha should return unmodified
        double standardVal = unitConverter.convertNitrogenToKgHa(280.0, UnitConverter.UNIT_KG_PER_HA);
        assertEquals(280.0, standardVal, 0.01);
    }

    @Test
    @DisplayName("Should convert lbs/acre to kg/ha accurately")
    void testLbsPerAcreConversion() {
        double kgHa = unitConverter.convertNitrogenToKgHa(100.0, UnitConverter.UNIT_LBS_PER_ACRE);
        assertEquals(112.085, kgHa, 0.01);
    }

    @Test
    @DisplayName("Should scale hectare dose to farmer acreage accurately")
    void testScalePerHaToFarmAcre() {
        // 120 kg/ha for 2.47105 acres should equal exactly 120 kg
        double farmKg = unitConverter.scalePerHaToFarmAcre(120.0, 2.47105);
        assertEquals(120.0, farmKg, 0.01);

        // 100 kg/ha for 1 acre should equal approx 40.47 kg
        double oneAcreKg = unitConverter.scalePerHaToFarmAcre(100.0, 1.0);
        assertEquals(40.47, oneAcreKg, 0.1);
    }

    @Test
    @DisplayName("Should correctly classify Nitrogen levels per ICAR thresholds")
    void testNitrogenClassification() {
        assertEquals(NutrientRating.LOW, NutrientRating.classifyNitrogen(200.0));
        assertEquals(NutrientRating.MEDIUM, NutrientRating.classifyNitrogen(350.0));
        assertEquals(NutrientRating.HIGH, NutrientRating.classifyNitrogen(600.0));
        assertEquals(NutrientRating.NOT_AVAILABLE, NutrientRating.classifyNitrogen(null));
    }

    @Test
    @DisplayName("Should correctly classify Phosphorus levels per ICAR thresholds")
    void testPhosphorusClassification() {
        assertEquals(NutrientRating.LOW, NutrientRating.classifyPhosphorus(15.0));
        assertEquals(NutrientRating.MEDIUM, NutrientRating.classifyPhosphorus(35.0));
        assertEquals(NutrientRating.HIGH, NutrientRating.classifyPhosphorus(70.0));
    }

    @Test
    @DisplayName("Should correctly classify Potassium levels per ICAR thresholds")
    void testPotassiumClassification() {
        assertEquals(NutrientRating.LOW, NutrientRating.classifyPotassium(100.0));
        assertEquals(NutrientRating.MEDIUM, NutrientRating.classifyPotassium(200.0));
        assertEquals(NutrientRating.HIGH, NutrientRating.classifyPotassium(400.0));
    }
}
