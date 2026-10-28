# Vayal – Smart Fertilizer Advisor 🌾
> Precision Agriculture Decision-Support & Soil Health System for Farmers

[![Java](https://img.shields.io/badge/Java-17%20%7C%2021-orange.svg)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)]()

**Vayal** (meaning fertile farm landscape / paddy field) is a full-stack agricultural decision-support web application. It provides scientifically verified, crop-specific fertilizer recommendations calibrated by certified soil test reports using Indian Council of Agricultural Research (ICAR) & State Agricultural Universities (TNAU) Soil Test Crop Response (STCR) principles.

When soil test reports are **not** available, Vayal strictly adheres to agronomic ethics: **it does not guess or invent arbitrary chemical dosages**. Instead, it guides the farmer on proper composite soil sampling protocols, local soil testing facilities, and safe organic baseline manuring.

---

## 🌟 Key Features

1. **Modern Agriculture-Themed Responsive Interface**: Earthy and sprout palette with intuitive responsive layouts for mobile, tablet, and desktop.
2. **Dynamic Soil Test Toggle**:
   - **Soil Test Available**: Accepts Nitrogen (N), Phosphorus (P), Potassium (K), and Soil pH in configurable units (`kg/ha`, `ppm / mg/kg`, `lbs/acre`).
   - **Soil Test Unavailable**: Disables numerical dosage inputs, presents composite soil sampling guides, and directs farmers to nearest Krishi Vigyan Kendras (KVKs).
3. **Verified Agronomic Rule Engine (Zero Guesswork)**:
   - Built on verified ICAR/TNAU standards for **7 key Indian crops**: Rice/Paddy, Wheat, Maize, Cotton, Sugarcane, Groundnut, and Tomato.
   - Soil Test Crop Response (STCR) adjustments: Low (+25%), Medium (100% RDF), High (-25%).
4. **Commercial Fertilizer Bag Calculator**:
   - Automatically calculates actual farmer bags of **Neem-Coated Urea (45kg/50kg)**, **DAP (50kg)**, and **MOP (50kg)** tailored to farmer's exact acreage.
5. **Growth Stage Split Scheduling**:
   - Breaks down applications by stage (Basal, Vegetative, Flowering/Panicle Initiation, Maturity).
6. **Soil pH & Salinity Advisory**:
   - Automated Agricultural Lime / Dolomite advisory for acidic soils (pH < 6.0).
   - Agricultural Gypsum advisory for strongly alkaline/sodic soils (pH > 8.5).
7. **Complete Audit & History Tracking**:
   - Saves all farmer requests, soil ratings, and generated plans in MySQL with search, review, and PDF print capabilities.

---

## 🏗️ Technology Stack & Architecture

- **Backend**: Java 21 / 25, Spring Boot 3.3.4 (Spring Data JPA, Spring Web, Jakarta Bean Validation)
- **Frontend**: Semantic HTML5, Responsive Modern CSS3 (Grid & Flexbox), Vanilla JavaScript (ES6+ REST client)
- **Database**: MySQL 8.0+ (with H2 in-memory profile for instant automated testing)
- **Build Tool**: Apache Maven
- **IDE**: Google Antigravity / Eclipse / VS Code

### Java OOP Concepts Demonstrated
- **Encapsulation**: Private entity fields with verified accessor methods, immutable DTOs, and clean REST boundaries.
- **Interfaces**: Service contracts (`RecommendationService`, `FarmerService`, `CropService`, `FertilizerService`, `GuidanceRuleService`) and strategy interfaces (`RecommendationStrategy`, `UnitConverter`).
- **Inheritance & Polymorphism**:
  - `BaseEntity` mapped superclass providing audit timestamps across all entities.
  - Custom domain exception hierarchy (`AgriculturalException` -> `ResourceNotFoundException`, `InvalidSoilDataException`, `AgriculturalRuleException`).
  - Dynamic `RecommendationStrategy` implementations (`SoilTestedRecommendationStrategy` vs `GeneralGuidanceStrategy`).
- **Collections & Streams**: List processing, stage split filtering, bag aggregation, and sorting.
- **Exception Handling**: Global centralized `@RestControllerAdvice` delivering structured RFC-compliant JSON errors with detailed validation field maps.

---

## 🔒 Security Best Practice: Database Credentials

In accordance with security standards:
> **Database passwords are NEVER hardcoded in source code.**

All database connection credentials are read dynamically from environment variables or external configuration. A `.env.example` file is provided:

```bash
# Environment variables
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/fertilizer_advisor_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=your_mysql_password
```

---

## 📁 Project Folder Structure

```
D:\MedicineStockManagement/
├── pom.xml                                   # Maven project definition
├── .env.example                              # Environment configuration template
├── README.md                                 # Documentation & setup guide
├── database/
│   ├── schema.sql                            # Full MySQL 8.0 DDL schema
│   └── sample_data.sql                       # ICAR reference data & demo farmers
├── src/
│   ├── main/
│   │   ├── java/com/vayal/fertilizeradvisor/
│   │   │   ├── FertilizerAdvisorApplication.java
│   │   │   ├── controller/                   # REST API Endpoints
│   │   │   ├── dto/                          # Request & Response Data Transfer Objects
│   │   │   ├── entity/                       # JPA Database Entities
│   │   │   ├── exception/                    # Custom Exceptions & Global Handler
│   │   │   ├── repository/                   # Spring Data JPA Repositories
│   │   │   └── service/                      # Business Logic & Strategies
│   │   └── resources/
│   │       ├── application.properties        # App properties (Dynamic credentials)
│   │       ├── application-test.properties   # H2 test database profile
│   │       ├── data.sql                      # Automated data seeder
│   │       └── static/                       # Modern Frontend
│   │           ├── index.html
│   │           ├── css/styles.css
│   │           └── js/api.js, app.js
│   └── test/                                 # Unit & Integration Test Suite
```

---

## 🚀 Quick Setup & Execution

### Prerequisites
- Java JDK 17, 21, or 25 installed
- Apache Maven 3.8+ (or use the included `run.bat` / `run.ps1`)
- Optional: MySQL Server 8.0+ (the app defaults to embedded H2 for instant zero-configuration local runs)

### Quick Start (Zero-Configuration Embedded Mode)
Simply run either script from the project folder:

#### Windows CMD:
```cmd
run.bat
```

#### Windows PowerShell:
```powershell
.\run.ps1
```

Or using Maven directly:
```bash
mvn spring-boot:run
```

### Production / MySQL Setup (Optional)
If you wish to use an external MySQL instance:
1. Ensure MySQL is running on port 3306.
2. Set your environment variables and launch:
```powershell
$env:SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/fertilizer_advisor_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
$env:SPRING_DATASOURCE_USERNAME="root"
$env:SPRING_DATASOURCE_PASSWORD="YourMySQLPassword"
$env:SPRING_DATASOURCE_DRIVER="com.mysql.cj.jdbc.Driver"
mvn spring-boot:run
```

### Step 3: Access the Web Dashboard
Open your browser and navigate to:
```
http://localhost:8080/
```

---

## 🧪 Running Automated Tests

Run the complete test suite (Unit conversions, STCR algorithms, REST mock MVC integration):

```powershell
mvn test
```

All tests execute against an isolated in-memory H2 database instance and do not modify your local MySQL records.

---

## 📡 REST API Reference

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/crops` | List verified ICAR crops & baseline RDF |
| `GET` | `/api/crops/{id}` | Get crop details |
| `GET` | `/api/fertilizers` | List commercial & organic fertilizer specs |
| `GET` | `/api/guidance-rules` | List stage split rules & advisories |
| `GET` | `/api/farmers` | List farmer profiles |
| `POST` | `/api/farmers` | Register a new farmer profile |
| `POST` | `/api/recommendations` | Generate & persist fertilizer recommendation |
| `GET` | `/api/recommendations` | Retrieve past recommendations history |
| `GET` | `/api/recommendations/{id}` | Retrieve single recommendation details |
| `DELETE` | `/api/recommendations/{id}` | Delete recommendation history entry |

---

## 📄 License
This project is open-source under the MIT License.
Developed for agricultural education and precision farmer advisory.
