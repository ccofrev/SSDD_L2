package com.ssdd.kafka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OWM {

    private WeatherSignal main;

    public OWM() {
        //
    }

    public WeatherSignal getMainWeather() {
        return main;
    }

    public void setMain(WeatherSignal main) {
        this.main = main;
    }

    @Override
    public String toString() {
        return "OWM [mainWeather=" + main + "]";
    }

    
    
    

    
    
    
}
