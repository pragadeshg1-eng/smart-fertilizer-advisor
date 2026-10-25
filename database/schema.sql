-- =====================================================================
-- DATABASE SCHEMA: VAYAL - SMART FERTILIZER ADVISOR
-- Database: MySQL 8.0+
-- =====================================================================

CREATE DATABASE IF NOT EXISTS fertilizer_advisor_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE fertilizer_advisor_db;

-- 1. Farmers Table
CREATE TABLE IF NOT EXISTS farmers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    location VARCHAR(100) NOT NULL,
    farm_size_acre DOUBLE NOT NULL DEFAULT 1.0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_farmer_name (name),
    INDEX idx_farmer_location (location)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Crops Table (Verified ICAR Standards)
CREATE TABLE IF NOT EXISTS crops (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    crop_code VARCHAR(50) NOT NULL UNIQUE,
    crop_name VARCHAR(100) NOT NULL,
    scientific_name VARCHAR(150),
    category VARCHAR(50),
    base_nitrogen_kg_ha DOUBLE NOT NULL,
    base_phosphorus_kg_ha DOUBLE NOT NULL,
    base_potassium_kg_ha DOUBLE NOT NULL,
    preferred_soil_types VARCHAR(255),
    optimum_ph_min DOUBLE DEFAULT 6.0,
    optimum_ph_max DOUBLE DEFAULT 7.5,
    organic_manure_tons_per_acre DOUBLE DEFAULT 5.0,
    description TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_crop_code (crop_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Fertilizers Table
CREATE TABLE IF NOT EXISTS fertilizers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fertilizer_name VARCHAR(100) NOT NULL UNIQUE,
    short_name VARCHAR(30),
    fertilizer_type VARCHAR(50) NOT NULL,
    n_percent DOUBLE NOT NULL DEFAULT 0.0,
    p_percent DOUBLE NOT NULL DEFAULT 0.0,
    k_percent DOUBLE NOT NULL DEFAULT 0.0,
    bag_weight_kg DOUBLE NOT NULL DEFAULT 50.0,
    description TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_fert_type (fertilizer_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Guidance Rules Table
CREATE TABLE IF NOT EXISTS guidance_rules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    crop_code VARCHAR(50) NOT NULL,
    growth_stage VARCHAR(50) NOT NULL,
    stage_name VARCHAR(100) NOT NULL,
    nitrogen_split_percent DOUBLE NOT NULL DEFAULT 0.0,
    phosphorus_split_percent DOUBLE NOT NULL DEFAULT 0.0,
    potassium_split_percent DOUBLE NOT NULL DEFAULT 0.0,
    stage_advisory TEXT,
    organic_bio_advisory TEXT,
    general_practice_advisory TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_rule_crop_stage (crop_code, growth_stage)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. Recommendation History Table
CREATE TABLE IF NOT EXISTS recommendation_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    farmer_id BIGINT,
    farmer_name VARCHAR(100) NOT NULL,
    location VARCHAR(100),
    farm_size_acre DOUBLE NOT NULL DEFAULT 1.0,
    crop_code VARCHAR(50) NOT NULL,
    crop_name VARCHAR(100) NOT NULL,
    soil_type VARCHAR(50) NOT NULL,
    growth_stage VARCHAR(50) NOT NULL,
    has_soil_test_report BOOLEAN NOT NULL DEFAULT FALSE,
    nitrogen_reported_value DOUBLE,
    phosphorus_reported_value DOUBLE,
    potassium_reported_value DOUBLE,
    soil_ph DOUBLE,
    unit_used VARCHAR(30),
    nitrogen_rating VARCHAR(20),
    phosphorus_rating VARCHAR(20),
    potassium_rating VARCHAR(20),
    ph_classification VARCHAR(50),
    recommended_n_total_kg DOUBLE,
    recommended_p_total_kg DOUBLE,
    recommended_k_total_kg DOUBLE,
    current_stage_n_kg DOUBLE,
    current_stage_p_kg DOUBLE,
    current_stage_k_kg DOUBLE,
    commercial_bags_json TEXT,
    stage_schedule_json TEXT,
    general_guidance_json TEXT,
    ph_advisory TEXT,
    summary_text TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_rec_farmer (farmer_id),
    INDEX idx_rec_created (created_at DESC),
    CONSTRAINT fk_history_farmer FOREIGN KEY (farmer_id) REFERENCES farmers(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
