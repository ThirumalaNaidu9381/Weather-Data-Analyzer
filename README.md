# Weather Data Analyzer

📌 **Project Overview**
Weather Data Analyzer is a web-based application developed using Spring Boot that helps you track and visualize real-time temperature data. The system allows you to search for any global city to see its current weather, and manually log local temperatures to monitor trends over time.

The application connects to the Open-Meteo API to fetch accurate weather forecasts and uses Chart.js to display dynamic, interactive line graphs on the frontend.

This project is useful for:
- Tracking live weather and temperature for any city
- Managing and logging manual temperature readings
- Visualizing hourly and daily temperature trends
- Learning how to integrate external APIs with a Spring Boot backend

🚀 **Features**
- Fetch real-time weather details and forecasts
- Manage and log custom temperature readings
- Connect to Open-Meteo Geocoding & Forecast APIs
- Dynamic charts updating in real-time
- Static frontend resources (HTML, CSS, JS)

🛠️ **Tech Stack**
**Backend**
- Java – Version 17
- Spring Boot – Version 3.2.3
- Spring Boot Web Starter
- Maven – Dependency Management
- REST API Integration (Spring RestTemplate)

**Frontend (Static Resources)**
- HTML5
- CSS3
- Vanilla JavaScript
- Chart.js (Data Visualization)

📂 **Project Structure**
*You can add a project directory screenshot here.*

🔄 **Project Workflow**
The application follows a simple Spring Boot architecture flow:

**1️⃣ Application Start**
The project starts from:
`AnalyzerApplication.java`
Spring Boot initializes the application and starts the Tomcat embedded server on port 8080.

**2️⃣ External API Integration**
`WeatherService.java` handles outbound requests to the Open-Meteo API.
Responsibilities:
- Resolve city names to coordinates
- Fetch current weather and 24-hour/monthly temperature forecasts
- Filter forecast data strictly up to the current hour

**3️⃣ Controller Layer**
`WeatherController.java`
Handles incoming HTTP requests such as:
- Searching for a city's current weather
- Adding manual temperature readings
- Fetching temperature aggregates (Average, Highest, Lowest)
- Deleting historical temperature inputs

**4️⃣ Model Layer**
The model classes represent data transferred between the frontend and backend.
`WeatherResponse.java`: Stores complex weather states, times, and temperature arrays.
`Temperature.java`: Stores manual temperature records and generated timestamps.

**5️⃣ Static Resources**
Located in:
`src/main/resources/static`
Contains:
- `script.js` (DOM manipulation and Chart.js initialization)
- `style.css` (UI styling)
- `index.html` (Main layout)

⚙️ **Prerequisites**
Before running the project, make sure to install the following:
- Java JDK 17
- Maven 3.8+
- Active internet connection (for API calls)
- IDE (IntelliJ / Eclipse / VS Code)

🗄️ **Data Storage**
Currently, this project uses an ephemeral in-memory `ArrayList` to store manually added temperatures for quick testing and prototyping. 
*Note: You do not need to install MySQL or setup a database to run this application!*

▶️ **How to Run the Project**

**Step 1**
Clone the repository or download the project.
```bash
git clone https://github.com/ThirumalaNaidu9381/Weather-Data-Analyzer.git
```

**Step 2**
Navigate to the project directory.
```bash
cd Weather-Data-Analyzer
```

**Step 3**
Run the Spring Boot application using Maven.
```bash
mvn spring-boot:run
```
OR run the `AnalyzerApplication.java` file directly from your IDE.

**Step 4**
Open the application in your browser:
http://localhost:8080

📸 **Application Screenshots**
*You can insert screenshots of your application here.*

Example:
- Main Dashboard
- Weather Search Result
- Hourly/Daily Temperature Charts

📈 **Future Improvements**
- Connect to a MySQL database to persist temperature records permanently
- Build a complete modern frontend (React/Angular)
- Deploy application to cloud hosting (AWS / Heroku / Render)
- Add user authentication to support multiple dashboards
