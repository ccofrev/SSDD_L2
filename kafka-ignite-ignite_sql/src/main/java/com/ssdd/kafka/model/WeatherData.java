package com.ssdd.kafka.model;

import com.ssdd.kafka.util.JsonSerializable;

import java.util.List;

public class WeatherData extends JsonSerializable {

    private static final long serialVersionUID = -7188517737272721658L;

    private String deviceId;
    private Long time;
    private List<WeatherSignal> signals;

    public String getDeviceId() {
        return deviceId;
    }

    public WeatherData setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public Long getTime() {
        return time;
    }

    public WeatherData setTime(Long time) {
        this.time = time;
        return this;
    }

    public List<WeatherSignal> getSignals() {
        return signals;
    }

    public WeatherData setSignals(List<WeatherSignal> signals) {
        this.signals = signals;
        return this;
    }
    
}
