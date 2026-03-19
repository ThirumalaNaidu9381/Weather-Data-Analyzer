package com.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.weather.exception.CityNotFoundException;
import com.weather.model.Temperature;
import com.weather.model.WeatherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAndGetAllTemperatures() {
        weatherService.addTemperature(25.5);
        weatherService.addTemperature(30.0);

        List<Temperature> temps = weatherService.getAllTemperatures();
        assertEquals(2, temps.size());
        assertEquals(25.5, temps.get(0).getValue());
        assertEquals(30.0, temps.get(1).getValue());
    }

    @Test
    void testStatisticalMethods() {
        weatherService.addTemperature(10.0);
        weatherService.addTemperature(20.0);
        weatherService.addTemperature(30.0);

        assertEquals(20.0, weatherService.getAverageTemperature());
        assertEquals(30.0, weatherService.getHighestTemperature());
        assertEquals(10.0, weatherService.getLowestTemperature());
    }

    @Test
    void testDeleteTemperature() {
        weatherService.addTemperature(15.0);
        List<Temperature> temps = weatherService.getAllTemperatures();
        String id = temps.get(0).getId();

        weatherService.deleteTemperature(id);

        assertTrue(weatherService.getAllTemperatures().isEmpty());
    }

    @Test
    void testGetCurrentWeather_Success() {
        ObjectMapper mapper = new ObjectMapper();

        // Mock Geo Node
        ObjectNode geoNode = mapper.createObjectNode();
        ArrayNode results = mapper.createArrayNode();
        ObjectNode location = mapper.createObjectNode();
        location.put("latitude", 51.5085);
        location.put("longitude", -0.1257);
        results.add(location);
        geoNode.set("results", results);

        // Mock Weather Node
        ObjectNode weatherNode = mapper.createObjectNode();
        ObjectNode currentWeather = mapper.createObjectNode();
        currentWeather.put("temperature", 18.5);
        currentWeather.put("windspeed", 12.0);
        currentWeather.put("weathercode", 3);
        weatherNode.set("current_weather", currentWeather);

        when(restTemplate.getForObject(org.mockito.ArgumentMatchers.contains("geocoding-api"), eq(com.fasterxml.jackson.databind.JsonNode.class))).thenReturn(geoNode);
        when(restTemplate.getForObject(org.mockito.ArgumentMatchers.contains("api.open-meteo.com/v1/forecast"), eq(com.fasterxml.jackson.databind.JsonNode.class))).thenReturn(weatherNode);

        WeatherResponse response = weatherService.getCurrentWeather("London");

        assertNotNull(response);
        assertEquals(18.5, response.getTemperature());
        assertEquals(12.0, response.getWindSpeed());
        assertEquals("Partly cloudy", response.getWeatherCondition());
    }

    @Test
    void testGetCurrentWeather_CityNotFound() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode emptyGeoNode = mapper.createObjectNode();
        emptyGeoNode.set("results", mapper.createArrayNode());

        when(restTemplate.getForObject(org.mockito.ArgumentMatchers.contains("geocoding-api"), eq(com.fasterxml.jackson.databind.JsonNode.class))).thenReturn(emptyGeoNode);

        assertThrows(CityNotFoundException.class, () -> weatherService.getCurrentWeather("UnknownCity"));
    }
}
