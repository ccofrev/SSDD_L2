package com.ssdd.kafka.validation;

import com.ssdd.kafka.entity.WeatherSignalEntity;
import com.ssdd.kafka.main.WeatherDataProducer;
import com.ssdd.kafka.model.WeatherSignal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherSignalValidator {

    public boolean isValid(WeatherSignalEntity entity) {
        if(
            entity.getId() != null &&
            entity.getDeviceId() != null &&
            entity.getTime() != null &&
            entity.gettemperature() != null &&
            entity.getfeelsLike() != null &&
            entity.getpressure() != null &&
            entity.gethumidity() != null &&
            entity.gettemperature() > 0 &&
            entity.getfeelsLike() > 0 &&
            entity.getpressure() > 0 &&
            entity.gethumidity() > 0){ 
                return true;
            }else{
                Logger LOGGER = LoggerFactory.getLogger(WeatherDataProducer.class);
                LOGGER.debug("Not valid weather data");
                return false;
            }
    }

    public boolean isValid(WeatherSignal ws) {
        if(

            ws.gettemperature() != null &&
            ws.getfeelsLike() != null &&
            ws.getpressure() != null &&
            ws.gethumidity() != null &&
            ws.gettemperature() > 0 &&
            ws.getfeelsLike() > 0 &&
            ws.getpressure() > 0 &&
            ws.gethumidity() > 0){ 
                return true;
            }else{
                Logger LOGGER = LoggerFactory.getLogger(WeatherDataProducer.class);
                LOGGER.debug("Not valid weather data");
                return false;
            }
    }

}
