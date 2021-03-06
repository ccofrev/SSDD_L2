package com.ssdd.kafka.ignite;

import com.ssdd.kafka.entity.WeatherSignalEntity;
import com.ssdd.kafka.model.WeatherSignal;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WeatherSignalIgniteRepository {

    private static final String CACHE = "WeatherSignal";
    
    private static final Duration DURATION = new Duration(TimeUnit.MINUTES, 15); // 15 minutes window

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherSignalIgniteRepository.class);

    private IgniteRepository igniteRepository;

    private IgniteCache<String, WeatherSignalEntity> cache;

    public WeatherSignalIgniteRepository() {
        this.igniteRepository = IgniteRepositoryFactory.getInstance();

        CacheConfiguration<String, WeatherSignal> cacheConfig = new CacheConfiguration<>(CACHE);
        cacheConfig.setCacheMode(CacheMode.REPLICATED);
        cacheConfig.setIndexedTypes(String.class, WeatherSignalEntity.class);
        this.cache = igniteRepository.getCache(cacheConfig)
                                     .withExpiryPolicy(new CreatedExpiryPolicy(DURATION));
    }

    public void save(WeatherSignalEntity entity) {
        cache.put(entity.getId(), entity);
        LOGGER.info("Saved in ignite: " + entity);
    }

    public List<List<?>> sqlQuery(String query) {
        SqlFieldsQuery sql = new SqlFieldsQuery(query);
        try (QueryCursor<List<?>> cursor = cache.query(sql)) {
            return cursor.getAll();
        }
    }

    public void close() {
        igniteRepository.close();
    }

}
