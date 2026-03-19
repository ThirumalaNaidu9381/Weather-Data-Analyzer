# Weather Data Analyzer

A web application I built for easily checking the current weather of any city and analyzing daily temperature trends using line graphs. The backend is powered by Spring Boot, and the frontend uses plain vanilla JavaScript and HTML.

## Project Structure
weather-data-analyzer/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/weather/
│   │   │       ├── AnalyzerApplication.java             # Main application runner
│   │   │       ├── controller/
│   │   │       │   └── WeatherController.java           # Where all the API endpoints live
│   │   │       ├── exception/
│   │   │       │   ├── CityNotFoundException.java       
│   │   │       │   └── GlobalExceptionHandler.java      # Catches bad requests or API errors
│   │   │       ├── model/
│   │   │       │   ├── Temperature.java                 
│   │   │       │   └── WeatherResponse.java             # Holds the parsed weather data
│   │   │       └── service/
│   │   │           └── WeatherService.java              # The business logic (calls Open-Meteo API)
│   │   └── resources/
│   │       ├── application.properties                   # Basic Spring config
│   │       └── static/                                  
│   │           ├── index.html                           # The main UI
│   │           ├── script.js                            # Frontend logic and DOM manipulation
│   │           ├── style.css                            # All the custom styles
│   │           └── chart.js                             # Chart.js library for drawing the graphs
│   └── test/
│       └── java/
│           └── com/weather/                             # Unit tests for the controller and service
├── target/                                              
├── pom.xml                                              # All my Maven dependencies
└── README.md                                            # This file right here

## Backend
I built the backend with Spring Boot 3.x using Java 17.
- **Spring Web**: To create the REST API endpoints that the frontend talks to.
- **RestTemplate**: Used inside the service layer to pull real-time JSON data from the external Open-Meteo API.
- **In-Memory Storage**: I used an internal ArrayList to store any manual temperature readings so you don't need a clunky database just to run the app.

## Key Components
**WeatherController**: 
This controller routes all the HTTP requests:
- `POST /add-temperature`: Save a new temperature reading manually.
- `GET /temperatures`: Fetch every manual reading you've added.
- `GET /average, /highest, /lowest`: Gets the math stats for the current data.
- `DELETE /temperatures/{id}`: Delete a specific reading.
- `DELETE /temperatures`: Wipe the board clean.
- `GET /current-weather?city={city}`: The main endpoint that grabs live weather and 24-hr forecasts for the graphs.

**WeatherService**: 
Does the heavy lifting. It calls the Open-Meteo API, parses the current temperature, wind speed, and weather condition, and filters the hourly forecast so it strictly only returns data up to the current hour (no future predictions ruining the real-time graph).

## Configuration
- Runs on Port `8080` by default.

## Frontend
The frontend is a lightweight single-page application built entirely with vanilla JavaScript, HTML, and CSS without any complex frameworks like React.

## Features
- **Search Weather**: Type in any city name and get its immediate current temperature and conditions.
- **Dynamic Charts**: Two line graphs automatically render to show you the hour-by-hour temperature for today, and the day-by-day temperatures for the month.
- **Add Manual Readings**: You can type in an exact temperature and it instantly adds it to the list and recalculates the stats.
- **Stats Dashboard**: A quick view at the top that calculates the Average, Highest, and Lowest temperatures from whatever data is currently loaded.

## Technologies
- **HTML5**: For the app structure.
- **CSS3**: For styling (I used CSS variables for colors).
- **JavaScript (ES6+)**: Handles all the API fetch requests and updates the HTML.
- **Chart.js**: The library I used to draw the temperature line graphs.

## Key Files
- `index.html`: The only HTML page. Contains the weather search box, the charts canvas, and the manual input list.
- `script.js`: Where all the magic happens. It listens for button clicks, fetches data from my Java backend, and updates the DOM and Charts.
- `style.css`: Keeps things looking modern and responsive.

## Getting Started

**Prerequisites:**
- Make sure you have **Java 17** (or newer) and **Maven** installed on your computer.

**Run the Application:**
Open up your terminal in the project folder and type:
```bash
mvn spring-boot:run
```
*(If you are using IntelliJ or Eclipse, you can just click Run on `AnalyzerApplication.java`)*

**Access the Application:**
Open your browser and navigate to `http://localhost:8080`.
The UI will pop up, and you can immediately start searching for cities!

## Database
I intentionally kept this simple by using a Java `ArrayList` to store data in memory while the server runs.
- **No external DB required**: You don't need to install MySQL, SQLite, or Postgres to test this out!
- Keep in mind that when you stop the Spring Boot server, your manual temperature records will reset.

## Development
- **Backend**: Java 17, Spring Boot 3.x
- **Frontend**: Standard web files (no Node.js or Webpack needed)
- **Testing**: Standard JUnit tests used for testing the back-end controllers and services.

## Notes
- CORS is enabled across the board in the controller so you can test the frontend separately if you want to.
- All the HTML, CSS, and JS files are served statically straight from `/src/main/resources/static/`.
