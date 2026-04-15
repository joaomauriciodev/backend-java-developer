package com.cmanager.app.core.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Value("${spring.cache.caffeine.expire-after-write-minutes:10}")
    private long expireAfterWriteMinutes;

    @Value("${spring.cache.caffeine.maximum-size:500}")
    private long maximumSize;

    @Bean
    public CacheManager cacheManager() {
        final CaffeineCacheManager manager = new CaffeineCacheManager("shows");
        manager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(expireAfterWriteMinutes, TimeUnit.MINUTES)
                .maximumSize(maximumSize));
        return manager;
    }
}
