package com.weather.model;

import java.util.List;

public class WeatherResponse {
    private double temperature;
    private double windSpeed;
    private String weatherCondition;
    private List<String> hourlyTimes;
    private List<Double> hourlyTemps;
    private List<String> dailyTimes;
    private List<Double> dailyTemps;

    public WeatherResponse() {}

    public WeatherResponse(double temperature, double windSpeed, String weatherCondition, 
                           List<String> hourlyTimes, List<Double> hourlyTemps, 
                           List<String> dailyTimes, List<Double> dailyTemps) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.weatherCondition = weatherCondition;
        this.hourlyTimes = hourlyTimes;
        this.hourlyTemps = hourlyTemps;
        this.dailyTimes = dailyTimes;
        this.dailyTemps = dailyTemps;
    }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }

    public String getWeatherCondition() { return weatherCondition; }
    public void setWeatherCondition(String weatherCondition) { this.weatherCondition = weatherCondition; }

    public List<String> getHourlyTimes() { return hourlyTimes; }
    public void setHourlyTimes(List<String> hourlyTimes) { this.hourlyTimes = hourlyTimes; }

    public List<Double> getHourlyTemps() { return hourlyTemps; }
    public void setHourlyTemps(List<Double> hourlyTemps) { this.hourlyTemps = hourlyTemps; }

    public List<String> getDailyTimes() { return dailyTimes; }
    public void setDailyTimes(List<String> dailyTimes) { this.dailyTimes = dailyTimes; }

    public List<Double> getDailyTemps() { return dailyTemps; }
    public void setDailyTemps(List<Double> dailyTemps) { this.dailyTemps = dailyTemps; }
}
