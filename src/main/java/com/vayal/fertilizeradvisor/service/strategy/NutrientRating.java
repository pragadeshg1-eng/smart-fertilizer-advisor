package com.vayal.fertilizeradvisor.service.strategy;

/**
 * Enumeration representing soil nutrient ratings according to ICAR / Soil Health Card standards.
 */
public enum NutrientRating {
    LOW(1.25, "Deficient - Low soil reserve. Recommended dose increased by 25% to build soil health and sustain yield."),
    MEDIUM(1.00, "Sufficient - Optimum soil fertility. Standard Recommended Dose of Fertilizers (100% RDF) advised."),
    HIGH(0.75, "High / Surplus - Abundant soil reserves. Recommended dose reduced by 25% to prevent toxicity, economic loss, and leaching."),
    NOT_AVAILABLE(1.00, "Soil test report not provided. Scientific calibration cannot be performed.");

    private final double adjustmentFactor;
    private final String agronomicDescription;

    NutrientRating(double adjustmentFactor, String agronomicDescription) {
        this.adjustmentFactor = adjustmentFactor;
        this.agronomicDescription = agronomicDescription;
    }

    public double getAdjustmentFactor() {
        return adjustmentFactor;
    }

    public String getAgronomicDescription() {
        return agronomicDescription;
    }

    /**
     * Classifies available Nitrogen (kg/ha) into Low, Medium, High.
     * ICAR Benchmark: Low < 280 kg/ha, Medium 280-560 kg/ha, High > 560 kg/ha.
     */
    public static NutrientRating classifyNitrogen(Double nKgHa) {
        if (nKgHa == null || nKgHa <= 0) return NOT_AVAILABLE;
        if (nKgHa < 280.0) return LOW;
        if (nKgHa <= 560.0) return MEDIUM;
        return HIGH;
    }

    /**
     * Classifies available Phosphorus (P2O5 in kg/ha) into Low, Medium, High.
     * ICAR Benchmark: Low < 23 kg/ha (< 10 kg Olsen P/ha), Medium 23-56 kg/ha, High > 56 kg/ha.
     */
    public static NutrientRating classifyPhosphorus(Double pKgHa) {
        if (pKgHa == null || pKgHa <= 0) return NOT_AVAILABLE;
        if (pKgHa < 23.0) return LOW;
        if (pKgHa <= 56.0) return MEDIUM;
        return HIGH;
    }

    /**
     * Classifies available Potassium (K2O in kg/ha) into Low, Medium, High.
     * ICAR Benchmark: Low < 130 kg/ha (< 110 kg K/ha), Medium 130-335 kg/ha, High > 335 kg/ha.
     */
    public static NutrientRating classifyPotassium(Double kKgHa) {
        if (kKgHa == null || kKgHa <= 0) return NOT_AVAILABLE;
        if (kKgHa < 130.0) return LOW;
        if (kKgHa <= 335.0) return MEDIUM;
        return HIGH;
    }
}
