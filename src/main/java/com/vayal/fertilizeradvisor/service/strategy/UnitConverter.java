package com.vayal.fertilizeradvisor.service.strategy;

import org.springframework.stereotype.Component;

/**
 * Standard Agronomic Unit Converter.
 * Converts reported soil lab test readings (ppm, lbs/acre, kg/ha)
 * to standard baseline ICAR units (kg/ha).
 */
@Component
public class UnitConverter {

    public static final String UNIT_KG_PER_HA = "KG_PER_HA";
    public static final String UNIT_PPM = "PPM";
    public static final String UNIT_LBS_PER_ACRE = "LBS_PER_ACRE";

    // 1 Hectare = 2.47105 Acres
    public static final double HECTARE_TO_ACRE = 2.47105;
    public static final double LBS_PER_ACRE_TO_KG_HA = 1.12085;

    /**
     * Converts Nitrogen to standard kg/ha.
     */
    public double convertNitrogenToKgHa(Double value, String unit) {
        if (value == null || value <= 0) return 0.0;
        if (unit == null) return value;

        return switch (unit.toUpperCase().trim()) {
            case UNIT_PPM -> value * 2.0; // 1 ppm = ~2 kg/ha in 2x10^6 kg furrow slice soil
            case UNIT_LBS_PER_ACRE -> value * LBS_PER_ACRE_TO_KG_HA;
            default -> value; // standard kg/ha
        };
    }

    /**
     * Converts Phosphorus to standard P2O5 kg/ha.
     */
    public double convertPhosphorusToKgHa(Double value, String unit) {
        if (value == null || value <= 0) return 0.0;
        if (unit == null) return value;

        return switch (unit.toUpperCase().trim()) {
            case UNIT_PPM -> value * 2.0 * 2.29; // 1 ppm Olsen P -> kg P/ha -> P2O5 factor 2.29
            case UNIT_LBS_PER_ACRE -> value * LBS_PER_ACRE_TO_KG_HA;
            default -> value; // standard kg/ha
        };
    }

    /**
     * Converts Potassium to standard K2O kg/ha.
     */
    public double convertPotassiumToKgHa(Double value, String unit) {
        if (value == null || value <= 0) return 0.0;
        if (unit == null) return value;

        return switch (unit.toUpperCase().trim()) {
            case UNIT_PPM -> value * 2.0 * 1.205; // 1 ppm K -> kg K/ha -> K2O factor 1.205
            case UNIT_LBS_PER_ACRE -> value * LBS_PER_ACRE_TO_KG_HA;
            default -> value; // standard kg/ha
        };
    }

    /**
     * Converts a rate per hectare to a total rate for the farmer's acreage.
     * Total = (ratePerHa / 2.47105) * farmSizeAcre
     */
    public double scalePerHaToFarmAcre(double ratePerHa, double farmSizeAcre) {
        if (farmSizeAcre <= 0) farmSizeAcre = 1.0;
        return (ratePerHa / HECTARE_TO_ACRE) * farmSizeAcre;
    }
}
