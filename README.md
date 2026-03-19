# Weather Data Analyzer

Weather Data Analyzer is a full-stack mini-project built with Java Spring Boot and Vanilla JavaScript. It provides real-time weather information for any given city, stores temperature records, and dynamically visualizes temperature data using Chart.js.

## Features
- **Real-Time Weather Data**: Fetch current weather conditions and temperatures for any city worldwide using the Open-Meteo API.
- **Data Visualization**: View both hourly (Today) and daily (This Month) temperature trends on interactive line charts.
- **Temperature Management**: Add custom temperature readings manually, and remove the latest readings when needed.
- **Statistical Analysis**: Calculate and view the Average, Highest, and Lowest temperatures from the recorded data set.

## Technologies Used
- **Backend**: Java 17, Spring Boot, Spring Web
- **Frontend**: HTML5, CSS3, Vanilla JavaScript, Chart.js
- **API**: Open-Meteo Geocoding & Weather API

## Prerequisites
Before running this project, ensure you have the following installed on your machine:
- Java Development Kit (JDK) 17 or higher
- Maven (optional, if running via the wrapper)
- A modern web browser

## Getting Started

### 1. Clone the repository
```bash
git clone <your-repository-url>
cd Weather-Data-Analyzer
```

### 2. Run the application
You can run the application using your preferred IDE (e.g., IntelliJ IDEA, Eclipse) by executing the `AnalyzerApplication` main class.

Alternatively, you can run it from the terminal using Maven:
```bash
mvnw spring-boot:run
```

### 3. Access the Application
Once the application starts successfully, open your web browser and navigate to:
```
http://localhost:8080
```

## Project Structure
- `src/main/java/com/weather`: Contains all Java source code (Controllers, Services, Models, Exceptions).
- `src/main/resources/static`: Contains frontend assets (`index.html`, `style.css`, `script.js`).

## How to Use
1. **Search for a City**: Type a city name in the input box and select the right option from the autocomplete suggestions. Click "Get Weather" to fetch real-time data.
2. **View Charts**: Once the weather is loaded, the charts will automatically update with hourly and daily forecast data.
3. **Add Manual Temperature**: After searching for a city, you can add custom temperature readings in the panel provided. These will instantly reflect in the charts and statistics.
4. **View Statistics**: Click the respective buttons to calculate the Average, Highest, or Lowest temperatures based on the recorded data.

## Contributing
Contributions, issues, and feature requests are welcome! Feel free to check the issues page and submit pull requests.
