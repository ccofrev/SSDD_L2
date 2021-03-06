package com.ssdd.kafka.main;

import java.util.Arrays;
import java.util.List;

/**
 * Kafka Ignite Streamin - api_consumer-kafka_producer
 *
 * Esta clase está preparada para ejecutar varios ejectuables "runnables" en paralelo, aunque para estos
 * efectos solo se ejecuta uno.
 */
public class Main {

    public static void main(String[] args) {
        List<Runnable> jobs = Arrays.asList(
                
                new IgniteStreamingSQLQuery()
                
                );
        jobs.parallelStream()
            .map(Thread::new)
            .forEach(Thread::run);
    }

}
