package com.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AnalyzerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyzerApplication.class, args);
    }

    // Bean for making external API calls (e.g., to Open-Meteo)
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
