# Weather Data Analyzer

## Project Overview
Weather Data Analyzer is a simple web-based application built with Spring Boot and vanilla JavaScript. It helps users search for real-time weather data for any city and visualize temperature trends using line graphs. 

I built this project to learn how to integrate an external API (Open-Meteo) with a Java backend and how to connect it to a frontend using REST APIs and Chart.js.

This project is useful for:
- Checking current weather conditions of any city in the world
- Seeing day and month temperature graphs
- Managing custom temperature inputs to see how they affect the average, max, and min temperatures.

## Features
- Search a city and get real-time weather data
- View hourly and daily temperature trends on a graph
- Add your own custom temperatures manually
- View average, highest, and lowest temperatures from the recorded data
- Delete manually added temperature records
- Connects to the Open-Meteo API for real data

## Tech Stack
**Backend:**
- Java 17
- Spring Boot 3.2.3
- Spring Web
- Maven

**Frontend:**
- HTML5
- CSS3
- Vanilla JavaScript
- Chart.js

## Project Workflow
1. **Application Start**: The project starts from `AnalyzerApplication.java`. It spins up an embedded Tomcat server on port 8080.
2. **API Connection**: The `WeatherService.java` class handles fetching data from the Open-Meteo API using `RestTemplate`.
3. **Controllers**: The `WeatherController.java` is responsible for handling frontend requests (like getting weather info, adding temperatures, and deleting temperatures).
4. **Data Models**: Simple Java classes like `Student` or `Temperature` hold the data coming in and out.
5. **Frontend**: The `script.js` handles the search box, updating the UI, and rendering the Chart.js graphs dynamically when data is fetched.

## Prerequisites
Before you run this project, make sure you have:
- Java JDK 17 installed
- Maven installed
- An IDE like IntelliJ, Eclipse, or VS Code
- Internet connection (so the weather API works)

## Database Setup
Currently, this project uses an in-memory Java `ArrayList` to store the manually inputted temperatures, so **you don't need to install MySQL** or set up any database to run it. Just run the app and it works out of the box!

## How to Run the Project

**Step 1:** Clone the repository
```bash
git clone https://github.com/ThirumalaNaidu9381/Weather-Data-Analyzer.git
```

**Step 2:** Open the project directory
```bash
cd Weather-Data-Analyzer
```

**Step 3:** Run the application
You can run it directly using Maven in your terminal:
```bash
mvn spring-boot:run
```
Or you can just run `AnalyzerApplication.java` directly from your IDE.

**Step 4:** Open your browser
Go to `http://localhost:8080` to see the application!

## Future Improvements
- Add a real database like MySQL to save the manually added temperatures permanently.
- Add user login functionality so different users can track their own temperatures.
- Deploy the project live on a cloud platform like AWS or Render.
