package com.ssdd.kafka.parser;

import com.ssdd.kafka.entity.WeatherSignalEntity;
import com.ssdd.kafka.model.WeatherData;
import com.ssdd.kafka.model.WeatherSignal;
import com.ssdd.kafka.util.HashCodeUtil;
import com.ssdd.kafka.validation.WeatherSignalValidator;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parsea json embebido y convierte a objetos WeatherSignalEntity
 * 
 */
public class WeatherDataParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherDataParser.class);

    private Gson gson = new Gson();

    private WeatherSignalValidator validator;

    public WeatherDataParser(WeatherSignalValidator validator) {
        this.validator = validator;
    }

    public List<WeatherSignalEntity> parse(String json) throws Exception {
        // parse incoming json
        WeatherData WeatherData = gson.fromJson(json, WeatherData.class);

        // enrich, filter and return network signal entities
        if (WeatherData == null || WeatherData.getSignals() == null) {
            return new ArrayList<>(0);
        }
        return WeatherData.getSignals().stream()
                          .map(WeatherSignal -> convert(WeatherData.getDeviceId(), WeatherSignal)) // convert objects
                          .map(entity -> entity.setId(generateUniqueId(entity))) // add unique id
                          .filter(entity -> validator.isValid(entity))
                          .collect(Collectors.toList());
    }

    /**
     * Convierte objeto WeatherSignal a WeatherSignalEntity usando info de device id del objeto WeatherData
     */
    private WeatherSignalEntity convert(String deviceId, WeatherSignal WeatherSignal) {
        return new WeatherSignalEntity()
            .setDeviceId(deviceId)
            .setTime(WeatherSignal.getTime())
            .setLatitude(WeatherSignal.getLatitude())
            .setLongitude(WeatherSignal.getLongitude())
            .setcity(WeatherSignal.getcity())
            .settemperature(WeatherSignal.gettemperature())
            .setfeelsLike(WeatherSignal.getfeelsLike())
            .setpressure(WeatherSignal.getpressure())
            .sethumidity(WeatherSignal.gethumidity());
    }

    /**
     * Genera Id unico
     */
    private String generateUniqueId(WeatherSignalEntity entity) {
        try {
            return HashCodeUtil.getHashString(
                    entity.getDeviceId(),
                    entity.getTime(),
                    entity.getcity(),
                    entity.getpressure(),
                    entity.gethumidity(),
                    entity.gettemperature(),
                    entity.getfeelsLike(),
                    entity.getLatitude(),
                    entity.getLongitude());
        } catch (Exception e) {
            LOGGER.error("Error generating unique id", e);
            return null;
        }
    }

}
