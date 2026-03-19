package com.weather.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.weather.exception.CityNotFoundException;
import com.weather.model.Temperature;
import com.weather.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {
    private final List<Temperature> temperatures = new ArrayList<>();
    private final RestTemplate restTemplate;

    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void addTemperature(double value) {
        temperatures.add(new Temperature(value));
    }

    public List<Temperature> getAllTemperatures() {
        return new ArrayList<>(temperatures);
    }

    public double getAverageTemperature() {
        if (temperatures.isEmpty()) return 0.0;
        double sum = 0;
        for (Temperature t : temperatures) {
            sum += t.getValue();
        }
        return sum / temperatures.size();
    }

    public double getHighestTemperature() {
        if (temperatures.isEmpty()) return 0.0;
        double highest = temperatures.get(0).getValue();
        for (Temperature t : temperatures) {
            if (t.getValue() > highest) {
                highest = t.getValue();
            }
        }
        return highest;
    }

    public double getLowestTemperature() {
        if (temperatures.isEmpty()) return 0.0;
        double lowest = temperatures.get(0).getValue();
        for (Temperature t : temperatures) {
            if (t.getValue() < lowest) {
                lowest = t.getValue();
            }
        }
        return lowest;
    }

    public void deleteTemperature(String id) {
        temperatures.removeIf(t -> t.getId() != null && t.getId().equals(id));
    }

    public void deleteAllTemperatures() {
        temperatures.clear();
    }

    public WeatherResponse getCurrentWeather(String city) {
        String geoUrl = "https://geocoding-api.open-meteo.com/v1/search?name=" + city + "&count=1&language=en&format=json";
        JsonNode geoNode = restTemplate.getForObject(geoUrl, JsonNode.class);

        if (geoNode == null || !geoNode.has("results") || geoNode.get("results").isEmpty()) {
            throw new CityNotFoundException("City not found: " + city);
        }

        JsonNode location = geoNode.get("results").get(0);
        double lat = location.get("latitude").asDouble();
        double lon = location.get("longitude").asDouble();

        String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon + "&current_weather=true&hourly=temperature_2m&daily=temperature_2m_max&timezone=auto&past_days=30&forecast_days=2";
        JsonNode weatherNode = restTemplate.getForObject(weatherUrl, JsonNode.class);

        if (weatherNode == null || !weatherNode.has("current_weather")) {
            throw new RuntimeException("Failed to fetch weather data for city: " + city);
        }

        JsonNode currentWeather = weatherNode.get("current_weather");
        double temp = currentWeather.get("temperature").asDouble();
        double windspeed = currentWeather.get("windspeed").asDouble();
        int weathercode = currentWeather.get("weathercode").asInt();

        String condition = mapWeatherCode(weathercode);

        List<String> hourlyTimes = new ArrayList<>();
        List<Double> hourlyTemps = new ArrayList<>();
        if (weatherNode.has("hourly")) {
            JsonNode hourly = weatherNode.get("hourly");
            JsonNode timeArray = hourly.get("time");
            JsonNode tempArray = hourly.get("temperature_2m");
            String currentTimeStr = currentWeather.get("time").asText();
            String todayPrefix = currentTimeStr.substring(0, 10);
            for (int i = 0; i < timeArray.size(); i++) {
                String timeStr = timeArray.get(i).asText();
                if (timeStr.startsWith(todayPrefix)) {
                    // Only include up to the current hour
                    if (timeStr.compareTo(currentTimeStr) <= 0) {
                        hourlyTimes.add(timeStr.substring(11));
                        hourlyTemps.add(tempArray.get(i).asDouble());
                    }
                }
            }
        }

        List<String> dailyTimes = new ArrayList<>();
        List<Double> dailyTemps = new ArrayList<>();
        if (weatherNode.has("daily")) {
            JsonNode daily = weatherNode.get("daily");
            JsonNode timeArray = daily.get("time");
            JsonNode tempArray = daily.get("temperature_2m_max");
            for (int i = 0; i < timeArray.size(); i++) {
                String timeStr = timeArray.get(i).asText();
                if (timeStr.length() >= 10) {
                    dailyTimes.add(timeStr.substring(5)); // format MM-dd
                } else {
                    dailyTimes.add(timeStr);
                }
                dailyTemps.add(tempArray.get(i).asDouble());
            }
        }

        return new WeatherResponse(temp, windspeed, condition, hourlyTimes, hourlyTemps, dailyTimes, dailyTemps);
    }

    private String mapWeatherCode(int code) {
        if (code == 0) return "Clear sky";
        if (code <= 3) return "Partly cloudy";
        if (code <= 49) return "Fog / Overcast";
        if (code <= 69) return "Rain";
        if (code <= 79) return "Snow";
        if (code <= 99) return "Thunderstorm";
        return "Unknown";
    }
}
