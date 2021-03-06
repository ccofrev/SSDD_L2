package com.ssdd.kafka.main;

import com.ssdd.kafka.processor.WeatherDataKafkaProcessor;
import com.ssdd.kafka.util.ConfigUtil;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * se inicia procesamiento kafka en función de la información entrante como json
 */
public class WeatherDataProcessor implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherDataProcessor.class);

    private static Processor<byte[], byte[]> getProcessor() {
        return new WeatherDataKafkaProcessor();
    }

    @Override
    public void run() {
        LOGGER.info("Initializing kafka processor...");

        Properties properties = ConfigUtil.getConfig("weather-data");
        String topics = properties.getProperty("topic.names");
        StreamsConfig config = new StreamsConfig(properties);

        LOGGER.info("Start listening topics: " + topics);

        TopologyBuilder builder = new TopologyBuilder().addSource("SOURCE", topics.split(","))
                                                       .addProcessor("PROCESSOR", WeatherDataProcessor::getProcessor, "SOURCE");

        KafkaStreams streams = new KafkaStreams(builder, config);
        streams.start();
    }

    public static void main(String[] args) throws Exception {
        new WeatherDataProcessor().run();
    }

}
