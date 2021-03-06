package com.ssdd.kafka.processor;

import com.ssdd.kafka.ignite.WeatherSignalIgniteRepository;
import com.ssdd.kafka.parser.WeatherDataParser;
import com.ssdd.kafka.validation.WeatherSignalValidator;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * un "kafka processor" procesa la información en json y guarda la información procesada en ignite
 */
public class WeatherDataKafkaProcessor implements Processor<byte[], byte[]> {

    public static final Logger logger = LoggerFactory.getLogger(WeatherDataKafkaProcessor.class);

    private ProcessorContext context;

    private WeatherSignalIgniteRepository WeatherSignalRepository;

    private WeatherDataParser parser;

    @Override
    public void init(ProcessorContext processorContext) {
        this.context = processorContext;
        this.context.schedule(1000);

        WeatherSignalRepository = new WeatherSignalIgniteRepository();

        parser = new WeatherDataParser(new WeatherSignalValidator());
    }

    @Override
    public void process(byte[] key, byte[] value) {
        String json = new String(value);
        try {
            parser.parse(json)
                  .forEach(WeatherSignalRepository::save);
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.error("Error processing request, data: " + json, e);
            } else {
                logger.error("Error processing request", e);
            }
        }

    }

    @Override
    public void punctuate(long timestamp) {
        context.commit();
    }

    @Override
    public void close() {
        WeatherSignalRepository.close();
    }
    
}
