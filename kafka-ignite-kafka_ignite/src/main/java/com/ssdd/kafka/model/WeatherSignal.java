package com.ssdd.kafka.model;

import com.ssdd.kafka.util.JsonSerializable;

public class WeatherSignal extends JsonSerializable {

    private static final long serialVersionUID = -4820697677113123242L;

    private Long time;
    private String city;
    private Double temperature;
    private Double feelsLike;
    private Long pressure;
    private Long humidity;
    private Double latitude;
    private Double longitude;
    
    public Long getTime() {
        return time;
    }

    public WeatherSignal setTime(Long time) {
        this.time = time;
        return this;
    }

    public String getcity() {
        return city;
    }

    public WeatherSignal setcity(String city) {
        this.city = city;
        return this;
    }

    public Double gettemperature() {
        return temperature;
    }

    public WeatherSignal settemperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    public Double getfeelsLike() {
        return feelsLike;
    }

    public WeatherSignal setfeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
        return this;
    }

    public Long getpressure() {
        return pressure;
    }

    public WeatherSignal setpressure(Long pressure) {
        this.pressure = pressure;
        return this;
    }

    public Long gethumidity() {
        return humidity;
    }

    public WeatherSignal sethumidity(Long humidity) {
        this.humidity = humidity;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public WeatherSignal setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public WeatherSignal setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

}
