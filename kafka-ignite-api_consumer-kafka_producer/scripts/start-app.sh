#!/bin/bash

cd ..
mvn clean package -Pmake-jar
java -Xms1g -Xmx1g -cp target/kafka-ignite-api_consumer-kafka_producer-*-dist.jar com.ssdd.kafka.main.Main