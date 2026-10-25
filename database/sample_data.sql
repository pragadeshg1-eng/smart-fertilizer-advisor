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
(8, 'WHEAT', 'MATURITY', 'Grain Filling & Ripening', 0.0, 0.0, 0.0, 'Nutrient uptake concludes; stop fertilizer application.', 'No manure needed.', 'Terminal irrigation at dough stage to avoid shriveling.')
ON DUPLICATE KEY UPDATE stage_name=VALUES(stage_name);

-- 4. Clearly Marked Demonstration Farmers
INSERT INTO farmers (id, name, phone_number, location, farm_size_acre)
VALUES
(1, '[DEMO SAMPLE] Murugan P.', '9842100001', 'Thanjavur, Tamil Nadu', 2.5),
(2, '[DEMO SAMPLE] Gurpreet Singh', '9815000002', 'Ludhiana, Punjab', 5.0),
(3, '[DEMO SAMPLE] Ramesh Patil', '9422000003', 'Solapur, Maharashtra', 3.0),
(4, '[DEMO SAMPLE] Anbu Selvam', '9789000004', 'Madurai, Tamil Nadu', 1.5)
ON DUPLICATE KEY UPDATE name=VALUES(name);
