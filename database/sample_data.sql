-- =====================================================================
-- DATABASE SAMPLE DATA: VAYAL - SMART FERTILIZER ADVISOR
-- Contains verified agricultural rules and clearly marked demo samples
-- =====================================================================

USE fertilizer_advisor_db;

-- 1. Crops
INSERT INTO crops (id, crop_code, crop_name, scientific_name, category, base_nitrogen_kg_ha, base_phosphorus_kg_ha, base_potassium_kg_ha, preferred_soil_types, optimum_ph_min, optimum_ph_max, organic_manure_tons_per_acre, description)
VALUES
(1, 'PADDY', 'Rice / Paddy', 'Oryza sativa', 'Cereal', 120.0, 40.0, 40.0, 'Clay Soil, Alluvial Soil, Clay Loam', 5.5, 7.0, 5.0, 'Primary staple cereal crop. Requires puddled land, balanced split nitrogen, basal phosphatic application, and sufficient potassium for disease resistance.'),
(2, 'WHEAT', 'Wheat', 'Triticum aestivum', 'Cereal', 120.0, 60.0, 40.0, 'Alluvial Soil, Sandy Loam, Clay Loam', 6.0, 7.5, 4.0, 'Major rabi cereal crop. Demands critical irrigation and top-dressed nitrogen at Crown Root Initiation (CRI) and jointing stages.'),
(3, 'MAIZE', 'Maize / Corn', 'Zea mays', 'Cereal', 135.0, 62.5, 50.0, 'Sandy Loam, Red Loam, Alluvial Soil', 6.0, 7.5, 5.0, 'Heavy feeder coarse grain crop. High nitrogen demand during vegetative knee-high and reproductive silking/tasseling stages.'),
(4, 'COTTON', 'Cotton', 'Gossypium hirsutum', 'Commercial', 120.0, 60.0, 60.0, 'Black Cotton Soil, Deep Clay, Alluvial Soil', 6.5, 8.0, 5.0, 'Major fiber crop. Responsive to balanced potassium and nitrogen splits during square initiation and boll development.'),
(5, 'SUGARCANE', 'Sugarcane', 'Saccharum officinarum', 'Commercial', 275.0, 62.5, 112.5, 'Alluvial Soil, Deep Fertile Loam, Clay Loam', 6.5, 7.8, 10.0, 'High biomass cash crop with heavy seasonal nutrient requirement. Split nitrogen applications match formative tillering and grand growth periods.'),
(6, 'GROUNDNUT', 'Groundnut / Peanut', 'Arachis hypogaea', 'Oilseed / Legume', 17.0, 34.0, 54.0, 'Sandy Loam, Red Sandy Loam', 6.0, 7.0, 4.0, 'Leguminous oilseed with nitrogen-fixing root nodules. High phosphorus demand for root proliferation and calcium/gypsum for pod filling.'),
(7, 'TOMATO', 'Tomato', 'Solanum lycopersicum', 'Vegetable', 150.0, 100.0, 100.0, 'Sandy Loam, Red Loam, Alluvial Soil', 6.0, 7.0, 8.0, 'High-yielding horticultural vegetable requiring abundant organic base, basal phosphorus, and potassium for fruit firmness and color.')
ON DUPLICATE KEY UPDATE crop_name=VALUES(crop_name);

-- 2. Fertilizers
INSERT INTO fertilizers (id, fertilizer_name, short_name, fertilizer_type, n_percent, p_percent, k_percent, bag_weight_kg, description)
VALUES
(1, 'Neem Coated Urea (46:0:0)', 'Urea', 'CHEMICAL', 46.0, 0.0, 0.0, 45.0, 'Highest nitrogen concentration commercial fertilizer with neem oil coating to retard nitrification and reduce ammonia volatilization.'),
(2, 'Di-ammonium Phosphate (18:46:0)', 'DAP', 'CHEMICAL', 18.0, 46.0, 0.0, 50.0, 'Standard phosphatic fertilizer supplying water-soluble phosphate and starter nitrogen for rapid root system establishment.'),
(3, 'Muriate of Potash (0:0:60)', 'MOP', 'CHEMICAL', 0.0, 0.0, 60.0, 50.0, 'Primary potassium source essential for water regulation, enzymatic activation, lodging prevention, and grain filling.'),
(4, 'Single Super Phosphate (0:16:0)', 'SSP', 'CHEMICAL', 0.0, 16.0, 0.0, 50.0, 'Phosphorus fertilizer containing 16% P2O5, 11% Sulfur, and 19% Calcium, ideal for oilseeds and pulse root nodules.'),
(5, 'NPK Complex (20:20:0:13)', 'Complex 20:20:0', 'CHEMICAL', 20.0, 20.0, 0.0, 50.0, 'Ammonium phosphate sulphate containing 20% N, 20% P2O5, and 13% vital secondary sulfur.'),
(6, 'Well-rotted Farmyard Manure (FYM)', 'FYM', 'ORGANIC', 0.5, 0.25, 0.5, 1000.0, 'Decomposed cattle dung and litter building soil organic carbon, moisture retention, and beneficial rhizosphere microflora.'),
(7, 'Vermicompost', 'Vermicompost', 'ORGANIC', 1.5, 1.0, 1.5, 50.0, 'Earthworm-digested organic humic compost enriched with plant growth hormones, macro, and micro-nutrients.')
ON DUPLICATE KEY UPDATE fertilizer_name=VALUES(fertilizer_name);

-- 3. Guidance Rules
INSERT INTO guidance_rules (id, crop_code, growth_stage, stage_name, nitrogen_split_percent, phosphorus_split_percent, potassium_split_percent, stage_advisory, organic_bio_advisory, general_practice_advisory)
VALUES
(1, 'PADDY', 'BASAL', 'Basal / Sowing & Transplanting', 50.0, 100.0, 50.0, 'Apply entire phosphatic fertilizer (DAP/SSP) and 50% each of Nitrogen and Potash just before final puddling and leveling.', 'Apply 5 tons FYM/acre + broadcast Azospirillum @ 2 kg/acre and Phosphobacteria @ 2 kg/acre mixed in moist manure.', 'Maintain a thin film of water (2 cm) during transplanting.'),
(2, 'PADDY', 'VEGETATIVE', 'Active Tillering (20-25 DAT)', 25.0, 0.0, 0.0, 'Top dress 25% Nitrogen (Urea) when tillering begins. Drain standing water prior to urea application and re-flood after 24 hours.', 'Foliar spray of 1% urea or Panchagavya (3%) if tillers look pale.', 'Keep field weed-free using cono-weeder or manual weeding.'),
(3, 'PADDY', 'FLOWERING', 'Panicle Initiation & Booting (40-45 DAT)', 25.0, 0.0, 50.0, 'Top dress remaining 25% Nitrogen and 50% Potash (MOP) to optimize panicle length, spikelet fertility, and prevent grain discoloration.', 'Spray 1% Potassium Nitrate (13-0-45) or 0.5% Zinc Sulphate if deficient.', 'Maintain 5 cm standing water during panicle emergence and flowering.'),
(4, 'PADDY', 'MATURITY', 'Grain Ripening & Harvest', 0.0, 0.0, 0.0, 'No chemical fertilizer application. Nutrients remobilize from leaves to developing grains.', 'No organic manure application.', 'Drain water completely from the field 10 to 12 days before anticipated harvest date.'),
(5, 'WHEAT', 'BASAL', 'Basal / Sowing', 50.0, 100.0, 100.0, 'Drill 50% Nitrogen and 100% Phosphorus and Potash 3-4 cm below seeds during sowing.', 'Incorporate 4 tons FYM/acre during preparatory tillage.', 'Ensure proper seedbed moisture for uniform germination.'),
(6, 'WHEAT', 'VEGETATIVE', 'Crown Root Initiation (21-25 DAS)', 25.0, 0.0, 0.0, 'First irrigation at CRI is most critical. Top dress 25% Nitrogen as Neem-coated Urea immediately after irrigation.', 'Apply Azotobacter seed treatment @ 250g/10kg seeds.', 'Interculture weeding to control Phalaris minor.'),
(7, 'WHEAT', 'FLOWERING', 'Jointing & Booting (45-60 DAS)', 25.0, 0.0, 0.0, 'Top dress remaining 25% Nitrogen before second critical irrigation to boost ear head development.', 'Foliar spray of 2% Urea if flag leaf turns pale.', 'Avoid irrigation during high velocity wind to prevent lodging.'),
(8, 'WHEAT', 'MATURITY', 'Grain Filling & Ripening', 0.0, 0.0, 0.0, 'Nutrient uptake concludes; stop fertilizer application.', 'No manure needed.', 'Terminal irrigation at dough stage to avoid shriveling.'),
(9, 'MAIZE', 'BASAL', 'Basal / Sowing', 25.0, 100.0, 50.0, 'Band place 25% Nitrogen, 100% Phosphorus, and 50% Potash 5 cm away and below the seed row.', 'Apply 5 tons FYM/acre + Azospirillum @ 2 kg/acre.', 'Ensure ridges and furrows for drainage.'),
(10, 'MAIZE', 'VEGETATIVE', 'Knee-High Stage (25-30 DAS)', 50.0, 0.0, 0.0, 'Apply 50% Nitrogen (Urea) along the side of crop rows and earth up immediately to cover fertilizer.', 'Incorporate poultry manure or compost if organic.', 'Earthing up prevents stem breakage and lodging.'),
(11, 'MAIZE', 'FLOWERING', 'Tasseling & Silking (45-50 DAS)', 25.0, 0.0, 50.0, 'Apply remaining 25% Nitrogen and 50% Potash just before tassel emergence to maximize kernel count.', 'Spray 1% Borax if cob filling is incomplete.', 'Maintain optimal moisture; water stress at tasseling drops yield.'),
(12, 'MAIZE', 'MATURITY', 'Dough Stage & Cob Maturity', 0.0, 0.0, 0.0, 'No fertilizer application.', 'No manure needed.', 'Harvest when husk leaves turn brown and black layer forms at kernel base.'),
(13, 'COTTON', 'BASAL', 'Basal / Sowing', 33.0, 100.0, 33.0, 'Apply 33% Nitrogen, 100% Phosphorus, and 33% Potash in furrows before dibbling seeds.', 'Apply 5 tons FYM/acre + Phosphobacteria @ 2 kg/acre.', 'Maintain plant spacing of 90 x 60 cm.'),
(14, 'COTTON', 'VEGETATIVE', 'Square Formation (40-45 DAS)', 33.0, 0.0, 33.0, 'Side dress 33% Nitrogen and 33% Potash followed by weeding and earthing up.', 'Spray 1% Magnesium Sulphate + 0.5% Zinc Sulphate if leaf reddening begins.', 'Scout for sucking pests.'),
(15, 'COTTON', 'FLOWERING', 'Peak Flowering & Boll Development (70-80 DAS)', 34.0, 0.0, 34.0, 'Side dress remaining 34% Nitrogen and 34% Potash to enhance boll retention and fiber strength.', 'Foliar spray of 1% DAP or 1% Potassium Nitrate (13-0-45) at 15-day intervals.', 'Ensure moisture to prevent boll shedding.'),
(16, 'COTTON', 'MATURITY', 'Boll Opening & Picking', 0.0, 0.0, 0.0, 'Stop chemical application.', 'No manure needed.', 'Pick clean, dry bolls in morning hours.'),
(17, 'SUGARCANE', 'BASAL', 'Basal / Planting Setts', 30.0, 100.0, 30.0, 'Apply 100% Phosphorus (SSP/DAP) and 30% each of Nitrogen and Potash in furrows before planting setts.', 'Apply 10 tons well-rotted FYM/compost per acre + Azotobacter/Gluconacetobacter @ 4 kg/acre.', 'Dip two-budded setts in 0.1% Carbendazim before planting in furrows.'),
(18, 'SUGARCANE', 'VEGETATIVE', 'Tillering & Formative Phase (30-60 DAP)', 40.0, 0.0, 35.0, 'Top-dress 40% Nitrogen and 35% Potash during partial earthing up at 45-60 DAP.', 'Foliar spray of 2% Urea + 1% Zinc Sulphate if tillers show chlorosis.', 'Carry out partial earthing-up and weed control to promote vigorous tillering.'),
(19, 'SUGARCANE', 'FLOWERING', 'Grand Growth & Elongation (90-120 DAP)', 30.0, 0.0, 35.0, 'Top-dress remaining 30% Nitrogen and 35% Potash along with full earthing-up at 90-120 DAP.', 'Spray 1% Ferrous Sulphate + 0.5% Urea if interveinal iron chlorosis appears.', 'Full earthing-up and trash mulching in furrows to conserve soil moisture.'),
(20, 'SUGARCANE', 'MATURITY', 'Cane Ripening & Sugar Synthesis', 0.0, 0.0, 0.0, 'Stop nitrogen fertilization 3 months prior to harvest to enhance sucrose synthesis and juice quality.', 'No manure application during ripening.', 'Withhold irrigation 15 days before cutting to prevent sucrose inversion.'),
(21, 'GROUNDNUT', 'BASAL', 'Basal / Sowing', 50.0, 100.0, 50.0, 'Band-place 50% Nitrogen, 100% Phosphorus (SSP preferred for sulfur content), and 50% Potash 5 cm below seed level.', 'Apply 4 tons FYM/acre + Rhizobium culture @ 200g/10kg seeds + Phosphobacteria @ 2 kg/acre.', 'Ensure fine tilth seedbed and treat seeds with Trichoderma viride @ 4g/kg.'),
(22, 'GROUNDNUT', 'VEGETATIVE', 'Vegetative & Early Flowering (20-25 DAS)', 25.0, 0.0, 25.0, 'Top dress 25% Nitrogen and 25% Potash at 20-25 DAS before flowering.', 'Foliar spray of 1% DAP or Panchagavya 3% at start of flowering.', 'Light hoeing and weeding; finish all intercultural operations before peg initiation.'),
(23, 'GROUNDNUT', 'FLOWERING', 'Pegging & Pod Filling (40-45 DAS)', 25.0, 0.0, 25.0, 'Apply remaining 25% Nitrogen and 25% Potash. Critical: Apply Agricultural Gypsum @ 200 kg/acre around plants and lightly cover with soil to supply Calcium for pod shell hardening.', 'Foliar spray of 0.5% Borax at 45 DAS to avoid hollow heart kernel disorder.', 'DO NOT disturb soil with hoes or interculture implements after peg penetration.'),
(24, 'GROUNDNUT', 'MATURITY', 'Pod Maturity & Harvest (90-105 DAS)', 0.0, 0.0, 0.0, 'Nutrient application ceases; allow pods to harden.', 'No manure required.', 'Check maturity by examining inner shell color (brownish-black inner lining indicates full maturity).'),
(25, 'TOMATO', 'BASAL', 'Basal / Transplanting', 30.0, 100.0, 30.0, 'Apply 100% Phosphorus (SSP/DAP), 30% Nitrogen, and 30% Potash in furrows before transplanting healthy 25-day seedlings.', 'Incorporate 8 tons well-decomposed FYM or 2 tons Vermicompost per acre + Azospirillum @ 2 kg/acre + Trichoderma @ 1 kg/acre.', 'Transplant in afternoon or cloudy weather and give immediate light irrigation.'),
(26, 'TOMATO', 'VEGETATIVE', 'Early Vegetative Growth (20-25 DAT)', 35.0, 0.0, 35.0, 'Top-dress 35% Nitrogen (Urea) and 35% Potash (MOP) 10 cm away from plant stems followed by light earthing-up.', 'Spray 1% 19:19:19 or Jeevamrutha at 10-day intervals.', 'Stake plants with bamboo poles to prevent fruit soil contact and disease.'),
(27, 'TOMATO', 'FLOWERING', 'Flowering & Fruit Setting (45-50 DAT)', 35.0, 0.0, 35.0, 'Apply remaining 35% Nitrogen and 35% Potash to support heavy fruit cluster enlargement.', 'Spray Calcium Nitrate @ 5g/L + Boron @ 1g/L to prevent Blossom End Rot (BER) in tomatoes.', 'Maintain uniform soil moisture; avoid alternate drying and flooding which causes fruit cracking.'),
(28, 'TOMATO', 'MATURITY', 'Fruit Ripening & Harvesting', 0.0, 0.0, 0.0, 'Cease chemical soil fertilization; fruit ripening relies on translocated assimilates.', 'Foliar Potassium Schoenite (0.5%) if color development is uneven.', 'Harvest fruits at breaker or pink stage for market transport.')
ON DUPLICATE KEY UPDATE stage_name=VALUES(stage_name);

-- 4. Clearly Marked Demonstration Farmers
INSERT INTO farmers (id, name, phone_number, location, farm_size_acre)
VALUES
(1, '[DEMO SAMPLE] Murugan P.', '9842100001', 'Thanjavur, Tamil Nadu', 2.5),
(2, '[DEMO SAMPLE] Gurpreet Singh', '9815000002', 'Ludhiana, Punjab', 5.0),
(3, '[DEMO SAMPLE] Ramesh Patil', '9422000003', 'Solapur, Maharashtra', 3.0),
(4, '[DEMO SAMPLE] Anbu Selvam', '9789000004', 'Madurai, Tamil Nadu', 1.5)
ON DUPLICATE KEY UPDATE name=VALUES(name);
