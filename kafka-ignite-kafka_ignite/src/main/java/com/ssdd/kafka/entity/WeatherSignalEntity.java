package com.ssdd.kafka.entity;

import com.ssdd.kafka.util.JsonSerializable;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class WeatherSignalEntity extends JsonSerializable {

    private static final long serialVersionUID = -6650766189688673925L;

    @QuerySqlField
    private String id;

    @QuerySqlField(index = true)
    private String deviceId;

    @QuerySqlField(index = true)
    private Long time;

    @QuerySqlField(index = true)
    private String city;

    @QuerySqlField
    private Double temperature;

    @QuerySqlField
    private Double feelsLike;

    @QuerySqlField
    private Long pressure;

    @QuerySqlField
    private Long humidity;

    @QuerySqlField
    private Double latitude;

    @QuerySqlField
    private Double longitude;

    public String getId() {
        return id;
    }

    public WeatherSignalEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public WeatherSignalEntity setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public Long getTime() {
        return time;
    }

    public WeatherSignalEntity setTime(Long time) {
        this.time = time;
        return this;
    }

    public String getcity() {
        return city;
    }

    public WeatherSignalEntity setcity(String city) {
        this.city = city;
        return this;
    }

    public Double gettemperature() {
        return temperature;
    }

    public WeatherSignalEntity settemperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    public Double getfeelsLike() {
        return feelsLike;
    }

    public WeatherSignalEntity setfeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
        return this;
    }

    public Long getpressure() {
        return pressure;
    }

    public WeatherSignalEntity setpressure(Long pressure) {
        this.pressure = pressure;
        return this;
    }

    public Long gethumidity() {
        return humidity;
    }

    public WeatherSignalEntity sethumidity(Long humidity) {
        this.humidity = humidity;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public WeatherSignalEntity setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public WeatherSignalEntity setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }
    
}
