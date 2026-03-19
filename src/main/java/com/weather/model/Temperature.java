package com.weather.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Temperature {
    private String id;
    private double value;
    private LocalDateTime timestamp;

    public Temperature() {}

    public Temperature(double value) {
        this.id = UUID.randomUUID().toString();
        this.value = value;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
