# Weather Data Analyzer

A simple full-stack web application built with Java Spring Boot and vanilla JavaScript. It fetches real-time weather data for any city and shows temperature trends using Chart.js.

## Features
- Search for a city to get its current temperature and weather conditions.
- View hourly and daily temperature forecasts on line graphs.
- Add your own custom temperature readings to the graphs.
- Calculates average, highest, and lowest temperatures from the recorded data.

## Tech Stack
- **Backend**: Java 17, Spring Boot (Spring Web), Maven
- **Frontend**: HTML/CSS, Vanilla JS, Chart.js
- **API used**: Open-Meteo API

## How to run locally

1. Make sure you have Java 17 and Maven installed on your machine.
2. Clone this repository:
   ```bash
   git clone https://github.com/ThirumalaNaidu9381/Weather-Data-Analyzer.git
   ```
3. Open the project folder in your terminal and run:
   ```bash
   mvn spring-boot:run
   ```
   *Note: If you run this from an IDE like IntelliJ or Eclipse, just run the `AnalyzerApplication.java` main class.*

4. Once the application starts, open your browser and go to `http://localhost:8080`.

## Endpoints
If you want to test the APIs directly, here are the main routes:
- `GET /current-weather?city={cityName}` - fetch real-time weather
- `POST /add-temperature` - save a new custom temperature
- `GET /temperatures` - get all saved temperatures
- `DELETE /temperatures/{id}` - delete a specific reading
- `GET /average`, `/highest`, `/lowest` - stats endpoints

## Contributing
Feel free to open an issue or submit a pull request if you find any bugs or want to add a new feature.
