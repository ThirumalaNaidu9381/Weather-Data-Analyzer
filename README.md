# 🌤️ Weather Data Analyzer

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=java&logoColor=white) 
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white) 
![JavaScript](https://img.shields.io/badge/JavaScript-ES6-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Chart.js](https://img.shields.io/badge/Chart.js-FF6384?style=for-the-badge&logo=chartdotjs&logoColor=white)

**Weather Data Analyzer** is a full-stack, real-time application designed to aggregate, analyze, and visualize daily and monthly temperature trends for any global city. Engineered with a robust Java/Spring Boot backend and a vanilla JavaScript frontend, the application provides a smooth, frictionless user experience for interacting with environmental data.

---

## 🚀 Key Features

- **Real-Time Meteorologic Data Integration**: Integrates directly with the Open-Meteo API to fetch up-to-the-hour weather metrics and multi-day forecasts based on geolocation.
- **Dynamic Data Visualization**: Leverages Chart.js to render fluid, responsive line graphs that visually compare historical timelines against real-time data inputs.
- **Manual Data Ingestion**: Grants users the capability to append custom, localized temperature readings that seamlessly blend into the live datasets and charts.
- **Statistical Aggregation Engine**: Computes dataset derivatives automatically—delivering Average, Peak (Highest), and Baseline (Lowest) temperatures on the fly.
- **Intelligent Time Filtering**: Custom algorithm applied in the persistence layer to truncate forecasted data and strictly present metrics captured up to the *current physical hour*.

---

## 🛠️ Technology Stack

### Backend
- **Core**: Java 17
- **Framework**: Spring Boot 3.2.x (Spring Web)
- **Http Client**: Spring `RestTemplate` for precise external API resolution
- **Build Tool**: Maven

### Frontend
- **Languages**: HTML5, CSS3 (Custom Variables, Flexbox/Grid), Vanilla ES6 JavaScript
- **Libraries**: Chart.js (for high-performance canvas rendering)
- **API Resolution**: Asynchronous Fetch API with Debouncing logic for city search optimization.

### External APIs
- **Open-Meteo Geocoding API**: Resolves abstract city strings into highly specific ISO coordinate mapping.
- **Open-Meteo Forecast API**: Pulls strict temperature logic (`temperature_2m` & `temperature_2m_max`).

---

## ⚙️ System Architecture

The project follows a standard **Monolithic MVC Pattern**:

1. **Client Tier**: A lightweight, SPAsque (Single Page Application-like) DOM engineered with native JavaScript. 
2. **Controller Layer**: RESTful principles are adhered to within the `WeatherController.java`, mapping external HTTP requests to internal services.
3. **Service Layer**: Contains the core business logic (`WeatherService.java`), memory aggregations, and outbound REST template polling.
4. **Data Layer**: Currently utilizes an ephemeral memory list (`ArrayList`) tailored for rapid session prototyping, configurable to swap to JPA/Hibernate.

---

## 📦 Prerequisites

Ensure you have the following configured on your development machine:
- **Java Development Kit (JDK) 17** or newer
- **Apache Maven** (Integrated or standalone)
- A modern web browser (Chrome, Edge, Firefox, Safari)

---

## 🚦 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/ThirumalaNaidu9381/Weather-Data-Analyzer.git
cd Weather-Data-Analyzer
```

### 2. Bootstrapping the Application
The easiest way to boot the server is through the Maven Spring Boot plugin. Run the following command from the root directory:
```bash
mvn spring-boot:run
```
*Tip: If you are using an IDE such as IntelliJ IDEA, VS Code, or Eclipse, simply run the `AnalyzerApplication.java` main class.*

### 3. Access the Client
Once Maven signals `Started AnalyzerApplication`, the Tomcat servlet will be actively listening. Open your browser to:
```text
http://localhost:8080
```

---

## 📡 API Endpoints Reference

If you desire to interact with the backend programmatically via Postman or cURL, the following REST endpoints are exposed:

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/current-weather?city={city}`| Fetches real-time API forecast data and maps datasets |
| `POST` | `/add-temperature` | Accepts a JSON payload `{ "value": 25.5 }` |
| `GET` | `/temperatures` | Retrieves all manually recorded temperatures |
| `DELETE`| `/temperatures/{id}` | Removes a specific historical recording via ID |
| `GET` | `/average` | Returns the mean aggregate for the active dataset |
| `GET` | `/highest` | Returns the absolute peak peak value |

---

## 🤝 Contributing

Contributions are heavily encouraged. Please follow standard git-flow practices:
1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

*Architected and maintained by Thirumala Naidu.* 👨‍💻
