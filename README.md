Weather Data Analyzer
A web application for fetching real-time weather and analyzing temperature trends, built with Spring Boot backend and vanilla JavaScript frontend.

Project Structure
weather-data-analyzer/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/weather/
│   │   │       ├── AnalyzerApplication.java             # Main Spring Boot application
│   │   │       ├── controller/
│   │   │       │   └── WeatherController.java           # REST API endpoints
│   │   │       ├── exception/
│   │   │       │   ├── CityNotFoundException.java       # Custom exception for missing cities
│   │   │       │   └── GlobalExceptionHandler.java      # Handles API errors
│   │   │       ├── model/
│   │   │       │   ├── Temperature.java                 # Model for manual temperature entries
│   │   │       │   └── WeatherResponse.java             # Data transfer object for weather data
│   │   │       └── service/
│   │   │           └── WeatherService.java              # Business logic and external API calls
│   │   └── resources/
│   │       ├── application.properties                   # Configuration
│   │       └── static/                                  # Frontend files
│   │           ├── index.html                           # Main HTML page
│   │           ├── script.js                            # Frontend JavaScript
│   │           ├── style.css                            # CSS styles
│   │           └── chart.js                             # Chart.js library file
│   └── test/
│       └── java/
│           └── com/weather/                             # Unit tests
├── target/                                              # Build output
├── pom.xml                                              # Maven configuration
└── README.md                                            # This file

Backend
The backend is built with Spring Boot 3.x, using:

Spring Web: For REST API endpoints
RestTemplate: To fetch data from the external Open-Meteo API
In-Memory List: For temporary storage of manual temperature inputs

Key Components
WeatherController: Handles HTTP requests for weather data
POST /add-temperature: Add a new manual temperature reading
GET /temperatures: Get all manual temperature readings
GET /average: Get average of recorded temperatures
GET /highest: Get highest recorded temperature
GET /lowest: Get lowest recorded temperature
DELETE /temperatures/{id}: Delete a specific temperature record
DELETE /temperatures: Clear all records
GET /current-weather?city={city}: Fetch real-time weather and forecast data

WeatherService: Contains business logic and calls the Open-Meteo API
Temperature Entity: Represents a manual temperature reading with fields: id, value, timestamp

Configuration
Port: 8080 (default)

Frontend
The frontend is a single-page application using vanilla JavaScript, HTML, and CSS.

Features
Weather Search: Look up any city to get current conditions
Temperature Charts: View hourly and daily temperature forecasts on line graphs
Add Temperature: Form to add manual temperature readings that update the graphs
Statistics Dashboard: View average, highest, and lowest temperatures

Technologies
HTML5: Structure
CSS3: Styling
JavaScript (ES6+): Interactivity and API calls
Chart.js: For rendering temperature graphs

Key Files
index.html: Main page with sections for different views
script.js: Handles UI logic, API calls, dynamic charts, and data display
style.css: Responsive styling

Getting Started
Prerequisites:

Java 17+
Maven

Run the Application:

mvn spring-boot:run

Access the Application:

Open http://localhost:8080 in your browser
The frontend will load, and you can start searching for weather or adding temperatures

Database
Uses an in-memory Java ArrayList for simplicity (no external DB required)
Manual temperature data is cleared when the application restarts

Development
Backend: Java 17, Spring Boot 3.x
Frontend: Vanilla JS, no build tools required
Testing: JUnit for backend tests

Notes
CORS is enabled for all origins in the controller
Static files are served from /src/main/resources/static/
The application runs on port 8080 by default
