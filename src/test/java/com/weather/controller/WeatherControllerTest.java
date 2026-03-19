package com.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.exception.CityNotFoundException;
import com.weather.model.Temperature;
import com.weather.model.WeatherResponse;
import com.weather.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherController.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    void testAddTemperature() throws Exception {
        doNothing().when(weatherService).addTemperature(anyDouble());

        String jsonPayload = "{\"value\": 25.5}";

        mockMvc.perform(post("/add-temperature")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllTemperatures() throws Exception {
        Temperature t1 = new Temperature(20.0);
        Temperature t2 = new Temperature(25.0);
        when(weatherService.getAllTemperatures()).thenReturn(Arrays.asList(t1, t2));

        mockMvc.perform(get("/temperatures"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].value").value(20.0))
                .andExpect(jsonPath("$[1].value").value(25.0));
    }

    @Test
    void testGetAverageTemperature() throws Exception {
        when(weatherService.getAverageTemperature()).thenReturn(22.5);

        mockMvc.perform(get("/average"))
                .andExpect(status().isOk())
                .andExpect(content().string("22.5"));
    }

    @Test
    void testGetCurrentWeather_Success() throws Exception {
        WeatherResponse mockResponse = new WeatherResponse(18.0, 10.0, "Clear sky", Arrays.asList(), Arrays.asList(), Arrays.asList(), Arrays.asList());
        when(weatherService.getCurrentWeather("Paris")).thenReturn(mockResponse);

        mockMvc.perform(get("/current-weather").param("city", "Paris"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature").value(18.0))
                .andExpect(jsonPath("$.weatherCondition").value("Clear sky"));
    }

    @Test
    void testGetCurrentWeather_CityNotFound() throws Exception {
        when(weatherService.getCurrentWeather("InvalidCity")).thenThrow(new CityNotFoundException("City not found: InvalidCity"));

        mockMvc.perform(get("/current-weather").param("city", "InvalidCity"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("City not found: InvalidCity"));
    }
}
