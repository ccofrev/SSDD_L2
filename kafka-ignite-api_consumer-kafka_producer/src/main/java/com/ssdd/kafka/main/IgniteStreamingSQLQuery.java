package com.ssdd.kafka.main;

import com.ssdd.kafka.ignite.WeatherSignalIgniteRepository;

import java.util.Arrays;
import java.util.List;

/**
 * This class connects Ignite instance and displays multiple SQL query results continuously
 */
public class IgniteStreamingSQLQuery implements Runnable {

    private static final long POLL_TIMEOUT = 10000; //10000 ms -> 10s

    private static final List<String> QUERIES = Arrays.asList(
            "SELECT city, time, AVG(temperature) AS AvgTemp FROM WeatherSignalEntity " + "GROUP BY deviceId ORDER BY time DESC LIMIT 5",
            "SELECT city, time, AVG(feelsLike) AS AvgFeelsLike FROM WeatherSignalEntity " + "GROUP BY city ORDER BY time DESC LIMIT 5",
            "SELECT city, time, AVG(pressure) AS AvgPress FROM WeatherSignalEntity " + "GROUP BY deviceId ORDER BY time DESC LIMIT 5");

    @Override
    public void run() {
        System.out.println("Initializing Ignite queries...");
        WeatherSignalIgniteRepository WeatherSignalRepository = new WeatherSignalIgniteRepository();
        try {
            List<List<?>> rows = null;
            do {
                // wait for incoming data
                try {
                    Thread.sleep(POLL_TIMEOUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                for (String sql : QUERIES) {
                    rows = WeatherSignalRepository.sqlQuery(sql);
                    System.out.println("------------------------------------------");
                    for (List<?> row : rows) {
                        System.out.println(row);
                    }
                }
                System.out.println();
            }
            while (rows != null && rows.size() > 0);
        } finally {
            WeatherSignalRepository.close();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new IgniteStreamingSQLQuery().run();
    }

}
