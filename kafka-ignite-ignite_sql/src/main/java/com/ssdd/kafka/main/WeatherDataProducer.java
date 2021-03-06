package com.ssdd.kafka.main;

import com.ssdd.kafka.model.OWM;
import com.ssdd.kafka.model.WeatherData;
import com.ssdd.kafka.model.WeatherSignal;
import com.ssdd.kafka.util.ConfigUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Se inicia kafka producer y se genera data
 */
public class WeatherDataProducer implements Runnable {

    private static final long INCOMING_DATA_INTERVAL = 60*1000;

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherDataProducer.class);

    @Override
    public void run() {
        LOGGER.info("Initializing kafka producer...");

        Properties properties = ConfigUtil.getConfig("weather-data");
        properties.put("key.serializer", StringSerializer.class);
        properties.put("value.serializer", StringSerializer.class);
        properties.put("acks", "all");
        properties.put("retries", 0);

        String topic = properties.getProperty("topic.names");
        LOGGER.info("Start producing weather data to topic: " + topic);

        Producer<String, String> producer = new KafkaProducer<>(properties);

        Random random = new Random();

        final int deviceCount = 100;
        List<String> deviceIds = IntStream.range(0, deviceCount)
                                          .mapToObj(i-> UUID.randomUUID().toString())
                                          .collect(Collectors.toList());

        
        String ciudad = "Santiago,CL";
        RestTemplateBuilder rtb = new RestTemplateBuilder();
        RestTemplate rt = rtb.build();
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+ ciudad +"&appid=eab0ed65ebf5e665f20d3eec2c82f4a7";
        WeatherSignal weatherSignal;

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            WeatherData weatherData = new WeatherData();
            OWM owm = new OWM();

            weatherSignal = new WeatherSignal();

            for(int ii=0; ii<3; ii++){
                try {
                    owm = rt.getForObject(url, OWM.class);
                    String salida = String.format("VALORES OWM: %.2f %.2f", owm.getMainWeather().gettemperature(), owm.getMainWeather().getfeelsLike());
                    LOGGER.info(salida);
                    weatherSignal.setpressure((long) 40+random.nextInt(3));
                    weatherSignal.sethumidity((long) 50+random.nextInt(1));
                    weatherSignal.settemperature((double) 15+random.nextInt(2));
                    weatherSignal.setfeelsLike((double) 15+random.nextInt(3));
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }

            weatherData.setDeviceId(deviceIds.get(random.nextInt(deviceCount-1)));            
            weatherData.setSignals(new ArrayList<>());
            
            
            weatherSignal.setcity(ciudad);

            /*
            weatherSignal.setpressure((long) random.nextInt(1000));
            weatherSignal.sethumidity((long) random.nextInt(1000));
            weatherSignal.settemperature((double) random.nextInt(100));
            weatherSignal.setfeelsLike((double) random.nextInt(100));*/


            weatherSignal.setTime(System.currentTimeMillis());
            weatherData.getSignals().add(weatherSignal);
            

            String key = "key-" + System.currentTimeMillis();
            String value = weatherData.toString();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Api data generated: " + key + ", " + value);
            }

            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
            producer.send(record);

            try {
                Thread.sleep(INCOMING_DATA_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        producer.close();
    }

    public static void main(String[] args) throws Exception {
        new WeatherDataProducer().run();
    }

}
