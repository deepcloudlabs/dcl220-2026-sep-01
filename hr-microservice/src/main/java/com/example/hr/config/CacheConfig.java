package com.example.hr.config;


import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.annotation.EnableCaching;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    CacheManager cacheManager() {
        var manager = new ConcurrentMapCacheManager();
        manager.setCacheNames(List.of("employees"));
        return manager;
    }
}