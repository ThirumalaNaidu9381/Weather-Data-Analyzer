package com.weather.controller;

import com.weather.model.Temperature;
import com.weather.model.WeatherResponse;
import com.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*") // Enable CORS for local testing if frontend is served separately
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/add-temperature")
    public void addTemperature(@RequestBody TemperatureRequest request) {
        weatherService.addTemperature(request.getValue());
    }

    @GetMapping("/temperatures")
    public List<Temperature> getAllTemperatures() {
        return weatherService.getAllTemperatures();
    }

    @GetMapping("/average")
    public double getAverageTemperature() {
        return weatherService.getAverageTemperature();
    }

    @GetMapping("/highest")
    public double getHighestTemperature() {
        return weatherService.getHighestTemperature();
    }

    @GetMapping("/lowest")
    public double getLowestTemperature() {
        return weatherService.getLowestTemperature();
    }

    @DeleteMapping("/temperatures/{id}")
    public void deleteTemperature(@PathVariable("id") String id) {
        weatherService.deleteTemperature(id);
    }

    @DeleteMapping("/temperatures")
    public void deleteAllTemperatures() {
        weatherService.deleteAllTemperatures();
    }

    @GetMapping("/current-weather")
    public WeatherResponse getCurrentWeather(@RequestParam("city") String city) {
        return weatherService.getCurrentWeather(city);
    }
}

class TemperatureRequest {
    private double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
