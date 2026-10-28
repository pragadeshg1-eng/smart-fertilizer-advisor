package com.vayal.fertilizeradvisor.config;

import com.vayal.fertilizeradvisor.entity.Crop;
import com.vayal.fertilizeradvisor.entity.Farmer;
import com.vayal.fertilizeradvisor.entity.Fertilizer;
import com.vayal.fertilizeradvisor.entity.GuidanceRule;
import com.vayal.fertilizeradvisor.repository.CropRepository;
import com.vayal.fertilizeradvisor.repository.FarmerRepository;
import com.vayal.fertilizeradvisor.repository.FertilizerRepository;
import com.vayal.fertilizeradvisor.repository.GuidanceRuleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Automatically initializes verified ICAR / TNAU agronomic standards
 * on application startup across all database environments (MySQL and H2).
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final CropRepository cropRepository;
    private final FertilizerRepository fertilizerRepository;
    private final GuidanceRuleRepository guidanceRuleRepository;
    private final FarmerRepository farmerRepository;

    public DataInitializer(
            CropRepository cropRepository,
            FertilizerRepository fertilizerRepository,
            GuidanceRuleRepository guidanceRuleRepository,
            FarmerRepository farmerRepository) {
        this.cropRepository = cropRepository;
        this.fertilizerRepository = fertilizerRepository;
        this.guidanceRuleRepository = guidanceRuleRepository;
        this.farmerRepository = farmerRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        initCrops();
        initFertilizers();
        initGuidanceRules();
        initDemoFarmers();
    }

    private void initCrops() {
        if (cropRepository.count() > 0) return;

        List<Crop> crops = List.of(
                new Crop("PADDY", "Rice / Paddy", "Oryza sativa", "Cereal",
                        120.0, 40.0, 40.0,
                        "Clay Soil, Alluvial Soil, Clay Loam", 5.5, 7.0, 5.0,
                        "Primary staple cereal crop. Requires puddled land, balanced split nitrogen, basal phosphatic application, and sufficient potassium for disease resistance."),

                new Crop("WHEAT", "Wheat", "Triticum aestivum", "Cereal",
                        120.0, 60.0, 40.0,
                        "Alluvial Soil, Sandy Loam, Clay Loam", 6.0, 7.5, 4.0,
                        "Major rabi cereal crop. Demands critical irrigation and top-dressed nitrogen at Crown Root Initiation (CRI) and jointing stages."),

                new Crop("MAIZE", "Maize / Corn", "Zea mays", "Cereal",
                        135.0, 62.5, 50.0,
                        "Sandy Loam, Red Loam, Alluvial Soil", 6.0, 7.5, 5.0,
                        "Heavy feeder coarse grain crop. High nitrogen demand during vegetative knee-high and reproductive silking/tasseling stages."),

                new Crop("COTTON", "Cotton", "Gossypium hirsutum", "Commercial",
                        120.0, 60.0, 60.0,
                        "Black Cotton Soil, Deep Clay, Alluvial Soil", 6.5, 8.0, 5.0,
                        "Major fiber crop. Responsive to balanced potassium and nitrogen splits during square initiation and boll development."),

                new Crop("SUGARCANE", "Sugarcane", "Saccharum officinarum", "Commercial",
                        275.0, 62.5, 112.5,
                        "Alluvial Soil, Deep Fertile Loam, Clay Loam", 6.5, 7.8, 10.0,
                        "High biomass cash crop with heavy seasonal nutrient requirement. Split nitrogen applications match formative tillering and grand growth periods."),

                new Crop("GROUNDNUT", "Groundnut / Peanut", "Arachis hypogaea", "Oilseed / Legume",
                        17.0, 34.0, 54.0,
                        "Sandy Loam, Red Sandy Loam", 6.0, 7.0, 4.0,
                        "Leguminous oilseed with nitrogen-fixing root nodules. High phosphorus demand for root proliferation and calcium/gypsum for pod filling."),

                new Crop("TOMATO", "Tomato", "Solanum lycopersicum", "Vegetable",
                        150.0, 100.0, 100.0,
                        "Sandy Loam, Red Loam, Alluvial Soil", 6.0, 7.0, 8.0,
                        "High-yielding horticultural vegetable requiring abundant organic base, basal phosphorus, and potassium for fruit firmness and color.")
        );

        cropRepository.saveAll(crops);
    }

    private void initFertilizers() {
        if (fertilizerRepository.count() > 0) return;

        List<Fertilizer> fertilizers = List.of(
                new Fertilizer("Neem Coated Urea (46:0:0)", "Urea", "CHEMICAL",
                        46.0, 0.0, 0.0, 45.0,
                        "Highest nitrogen concentration commercial fertilizer with neem oil coating to retard nitrification and reduce ammonia volatilization."),

                new Fertilizer("Di-ammonium Phosphate (18:46:0)", "DAP", "CHEMICAL",
                        18.0, 46.0, 0.0, 50.0,
                        "Standard phosphatic fertilizer supplying water-soluble phosphate and starter nitrogen for rapid root system establishment."),

                new Fertilizer("Muriate of Potash (0:0:60)", "MOP", "CHEMICAL",
                        0.0, 0.0, 60.0, 50.0,
                        "Primary potassium source essential for water regulation, enzymatic activation, lodging prevention, and grain filling."),

                new Fertilizer("Single Super Phosphate (0:16:0)", "SSP", "CHEMICAL",
                        0.0, 16.0, 0.0, 50.0,
                        "Phosphorus fertilizer containing 16% P2O5, 11% Sulfur, and 19% Calcium, ideal for oilseeds and pulse root nodules."),

                new Fertilizer("NPK Complex (20:20:0:13)", "Complex 20:20:0", "CHEMICAL",
                        20.0, 20.0, 0.0, 50.0,
                        "Ammonium phosphate sulphate containing 20% N, 20% P2O5, and 13% vital secondary sulfur."),

                new Fertilizer("Well-rotted Farmyard Manure (FYM)", "FYM", "ORGANIC",
                        0.5, 0.25, 0.5, 1000.0,
                        "Decomposed cattle dung and litter building soil organic carbon, moisture retention, and beneficial rhizosphere microflora."),

                new Fertilizer("Vermicompost", "Vermicompost", "ORGANIC",
                        1.5, 1.0, 1.5, 50.0,
                        "Earthworm-digested organic humic compost enriched with plant growth hormones, macro, and micro-nutrients.")
        );

        fertilizerRepository.saveAll(fertilizers);
    }

    private void initGuidanceRules() {
        if (guidanceRuleRepository.count() > 0) return;

        List<GuidanceRule> rules = List.of(
                // Paddy
                new GuidanceRule("PADDY", "BASAL", "Basal / Sowing & Transplanting",
                        50.0, 100.0, 50.0,
                        "Apply entire phosphatic fertilizer (DAP/SSP) and 50% each of Nitrogen and Potash just before final puddling and leveling.",
                        "Apply 5 tons FYM/acre + broadcast Azospirillum @ 2 kg/acre and Phosphobacteria @ 2 kg/acre mixed in moist manure.",
                        "Maintain a thin film of water (2 cm) during transplanting."),

                new GuidanceRule("PADDY", "VEGETATIVE", "Active Tillering (20-25 DAT)",
                        25.0, 0.0, 0.0,
                        "Top dress 25% Nitrogen (Urea) when tillering begins. Drain standing water prior to urea application and re-flood after 24 hours.",
                        "Foliar spray of 1% urea or Panchagavya (3%) if tillers look pale.",
                        "Keep field weed-free using cono-weeder or manual weeding."),

                new GuidanceRule("PADDY", "FLOWERING", "Panicle Initiation & Booting (40-45 DAT)",
                        25.0, 0.0, 50.0,
                        "Top dress remaining 25% Nitrogen and 50% Potash (MOP) to optimize panicle length, spikelet fertility, and prevent grain discoloration.",
                        "Spray 1% Potassium Nitrate (13-0-45) or 0.5% Zinc Sulphate if deficient.",
                        "Maintain 5 cm standing water during panicle emergence and flowering."),

                new GuidanceRule("PADDY", "MATURITY", "Grain Ripening & Harvest",
                        0.0, 0.0, 0.0,
                        "No chemical fertilizer application. Nutrients remobilize from leaves to developing grains.",
                        "No organic manure application.",
                        "Drain water completely from the field 10 to 12 days before anticipated harvest date."),

                // Wheat
                new GuidanceRule("WHEAT", "BASAL", "Basal / Sowing",
                        50.0, 100.0, 100.0,
                        "Drill 50% Nitrogen and 100% Phosphorus and Potash 3-4 cm below seeds during sowing.",
                        "Incorporate 4 tons FYM/acre during preparatory tillage.",
                        "Ensure proper seedbed moisture for uniform germination."),

                new GuidanceRule("WHEAT", "VEGETATIVE", "Crown Root Initiation (21-25 DAS)",
                        25.0, 0.0, 0.0,
                        "First irrigation at CRI is most critical. Top dress 25% Nitrogen as Neem-coated Urea immediately after irrigation.",
                        "Apply Azotobacter seed treatment @ 250g/10kg seeds.",
                        "Interculture weeding to control Phalaris minor."),

                new GuidanceRule("WHEAT", "FLOWERING", "Jointing & Booting (45-60 DAS)",
                        25.0, 0.0, 0.0,
                        "Top dress remaining 25% Nitrogen before second critical irrigation to boost ear head development.",
                        "Foliar spray of 2% Urea if flag leaf turns pale.",
                        "Avoid irrigation during high velocity wind to prevent lodging."),

                new GuidanceRule("WHEAT", "MATURITY", "Grain Filling & Ripening",
                        0.0, 0.0, 0.0,
                        "Nutrient uptake concludes; stop fertilizer application.",
                        "No manure needed.",
                        "Terminal irrigation at dough stage to avoid shriveling."),

                // Maize
                new GuidanceRule("MAIZE", "BASAL", "Basal / Sowing",
                        25.0, 100.0, 50.0,
                        "Band place 25% Nitrogen, 100% Phosphorus, and 50% Potash 5 cm away and below the seed row.",
                        "Apply 5 tons FYM/acre + Azospirillum @ 2 kg/acre.",
                        "Ensure ridges and furrows for drainage."),

                new GuidanceRule("MAIZE", "VEGETATIVE", "Knee-High Stage (25-30 DAS)",
                        50.0, 0.0, 0.0,
                        "Apply 50% Nitrogen (Urea) along the side of crop rows and earth up immediately to cover fertilizer.",
                        "Incorporate poultry manure or compost if organic.",
                        "Earthing up prevents stem breakage and lodging."),

                new GuidanceRule("MAIZE", "FLOWERING", "Tasseling & Silking (45-50 DAS)",
                        25.0, 0.0, 50.0,
                        "Apply remaining 25% Nitrogen and 50% Potash just before tassel emergence to maximize kernel count.",
                        "Spray 1% Borax if cob filling is incomplete.",
                        "Maintain optimal moisture; water stress at tasseling drops yield."),

                new GuidanceRule("MAIZE", "MATURITY", "Dough Stage & Cob Maturity",
                        0.0, 0.0, 0.0,
                        "No fertilizer application.",
                        "No manure needed.",
                        "Harvest when husk leaves turn brown and black layer forms at kernel base."),

                // Cotton
                new GuidanceRule("COTTON", "BASAL", "Basal / Sowing",
                        33.0, 100.0, 33.0,
                        "Apply 33% Nitrogen, 100% Phosphorus, and 33% Potash in furrows before dibbling seeds.",
                        "Apply 5 tons FYM/acre + Phosphobacteria @ 2 kg/acre.",
                        "Maintain plant spacing of 90 x 60 cm."),

                new GuidanceRule("COTTON", "VEGETATIVE", "Square Formation (40-45 DAS)",
                        33.0, 0.0, 33.0,
                        "Side dress 33% Nitrogen and 33% Potash followed by weeding and earthing up.",
                        "Spray 1% Magnesium Sulphate + 0.5% Zinc Sulphate if leaf reddening begins.",
                        "Scout for sucking pests."),

                new GuidanceRule("COTTON", "FLOWERING", "Peak Flowering & Boll Development (70-80 DAS)",
                        34.0, 0.0, 34.0,
                        "Side dress remaining 34% Nitrogen and 34% Potash to enhance boll retention and fiber strength.",
                        "Foliar spray of 1% DAP or 1% Potassium Nitrate (13-0-45) at 15-day intervals.",
                        "Ensure moisture to prevent boll shedding."),

                new GuidanceRule("COTTON", "MATURITY", "Boll Opening & Picking",
                        0.0, 0.0, 0.0,
                        "Stop chemical application.",
                        "No manure needed.",
                        "Pick clean, dry bolls in morning hours."),

                // Sugarcane
                new GuidanceRule("SUGARCANE", "BASAL", "Basal / Planting Setts",
                        30.0, 100.0, 30.0,
                        "Apply 100% Phosphorus (SSP/DAP) and 30% each of Nitrogen and Potash in furrows before planting setts.",
                        "Apply 10 tons well-rotted FYM/compost per acre + Azotobacter/Gluconacetobacter @ 4 kg/acre.",
                        "Dip two-budded setts in 0.1% Carbendazim before planting in furrows."),

                new GuidanceRule("SUGARCANE", "VEGETATIVE", "Tillering & Formative Phase (30-60 DAP)",
                        40.0, 0.0, 35.0,
                        "Top-dress 40% Nitrogen and 35% Potash during partial earthing up at 45-60 DAP.",
                        "Foliar spray of 2% Urea + 1% Zinc Sulphate if tillers show chlorosis.",
                        "Carry out partial earthing-up and weed control to promote vigorous tillering."),

                new GuidanceRule("SUGARCANE", "FLOWERING", "Grand Growth & Elongation (90-120 DAP)",
                        30.0, 0.0, 35.0,
                        "Top-dress remaining 30% Nitrogen and 35% Potash along with full earthing-up at 90-120 DAP.",
                        "Spray 1% Ferrous Sulphate + 0.5% Urea if interveinal iron chlorosis appears.",
                        "Full earthing-up and trash mulching in furrows to conserve soil moisture."),

                new GuidanceRule("SUGARCANE", "MATURITY", "Cane Ripening & Sugar Synthesis",
                        0.0, 0.0, 0.0,
                        "Stop nitrogen fertilization 3 months prior to harvest to enhance sucrose synthesis and juice quality.",
                        "No manure application during ripening.",
                        "Withhold irrigation 15 days before cutting to prevent sucrose inversion."),

                // Groundnut
                new GuidanceRule("GROUNDNUT", "BASAL", "Basal / Sowing",
                        50.0, 100.0, 50.0,
                        "Band-place 50% Nitrogen, 100% Phosphorus (SSP preferred for sulfur content), and 50% Potash 5 cm below seed level.",
                        "Apply 4 tons FYM/acre + Rhizobium culture @ 200g/10kg seeds + Phosphobacteria @ 2 kg/acre.",
                        "Ensure fine tilth seedbed and treat seeds with Trichoderma viride @ 4g/kg."),

                new GuidanceRule("GROUNDNUT", "VEGETATIVE", "Vegetative & Early Flowering (20-25 DAS)",
                        25.0, 0.0, 25.0,
                        "Top dress 25% Nitrogen and 25% Potash at 20-25 DAS before flowering.",
                        "Foliar spray of 1% DAP or Panchagavya 3% at start of flowering.",
                        "Light hoeing and weeding; finish all intercultural operations before peg initiation."),

                new GuidanceRule("GROUNDNUT", "FLOWERING", "Pegging & Pod Filling (40-45 DAS)",
                        25.0, 0.0, 25.0,
                        "Apply remaining 25% Nitrogen and 25% Potash. Critical: Apply Agricultural Gypsum @ 200 kg/acre around plants and lightly cover with soil to supply Calcium for pod shell hardening.",
                        "Foliar spray of 0.5% Borax at 45 DAS to avoid hollow heart kernel disorder.",
                        "DO NOT disturb soil with hoes or interculture implements after peg penetration."),

                new GuidanceRule("GROUNDNUT", "MATURITY", "Pod Maturity & Harvest (90-105 DAS)",
                        0.0, 0.0, 0.0,
                        "Nutrient application ceases; allow pods to harden.",
                        "No manure required.",
                        "Check maturity by examining inner shell color (brownish-black inner lining indicates full maturity)."),

                // Tomato
                new GuidanceRule("TOMATO", "BASAL", "Basal / Transplanting",
                        30.0, 100.0, 30.0,
                        "Apply 100% Phosphorus (SSP/DAP), 30% Nitrogen, and 30% Potash in furrows before transplanting healthy 25-day seedlings.",
                        "Incorporate 8 tons well-decomposed FYM or 2 tons Vermicompost per acre + Azospirillum @ 2 kg/acre + Trichoderma @ 1 kg/acre.",
                        "Transplant in afternoon or cloudy weather and give immediate light irrigation."),

                new GuidanceRule("TOMATO", "VEGETATIVE", "Early Vegetative Growth (20-25 DAT)",
                        35.0, 0.0, 35.0,
                        "Top-dress 35% Nitrogen (Urea) and 35% Potash (MOP) 10 cm away from plant stems followed by light earthing-up.",
                        "Spray 1% 19:19:19 or Jeevamrutha at 10-day intervals.",
                        "Stake plants with bamboo poles to prevent fruit soil contact and disease."),

                new GuidanceRule("TOMATO", "FLOWERING", "Flowering & Fruit Setting (45-50 DAT)",
                        35.0, 0.0, 35.0,
                        "Apply remaining 35% Nitrogen and 35% Potash to support heavy fruit cluster enlargement.",
                        "Spray Calcium Nitrate @ 5g/L + Boron @ 1g/L to prevent Blossom End Rot (BER) in tomatoes.",
                        "Maintain uniform soil moisture; avoid alternate drying and flooding which causes fruit cracking."),

                new GuidanceRule("TOMATO", "MATURITY", "Fruit Ripening & Harvesting",
                        0.0, 0.0, 0.0,
                        "Cease chemical soil fertilization; fruit ripening relies on translocated assimilates.",
                        "Foliar Potassium Schoenite (0.5%) if color development is uneven.",
                        "Harvest fruits at breaker or pink stage for market transport.")
        );

        guidanceRuleRepository.saveAll(rules);
    }

    private void initDemoFarmers() {
        if (farmerRepository.count() > 0) return;

        List<Farmer> demoFarmers = List.of(
                new Farmer("[DEMO SAMPLE] Murugan P.", "9842100001", "Thanjavur, Tamil Nadu", 2.5),
                new Farmer("[DEMO SAMPLE] Gurpreet Singh", "9815000002", "Ludhiana, Punjab", 5.0),
                new Farmer("[DEMO SAMPLE] Ramesh Patil", "9422000003", "Solapur, Maharashtra", 3.0),
                new Farmer("[DEMO SAMPLE] Anbu Selvam", "9789000004", "Madurai, Tamil Nadu", 1.5)
        );

        farmerRepository.saveAll(demoFarmers);
    }
}
