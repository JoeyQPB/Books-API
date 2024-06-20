package com.joey.Books_API.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class CacheEvictScheduler {
    private final Logger LOGGER = LoggerFactory.getLogger(CacheEvictScheduler.class);

    @CacheEvict(value = "books", allEntries = true)
    @Scheduled(cron = "0 0 * * * *")
    public void evictAllBooksCaches() {
        try {
            LOGGER.info("[:] Clean all books cache!");
        } catch (Exception e) {
            LOGGER.error("ERROR: {}", e.getMessage());
        }
    }

    @CacheEvict(value = "publishers", allEntries = true)
    @Scheduled(cron = "0 0 * * * *")
    public void evictAllPublishersCaches() {
        try {
            LOGGER.info("[:] Clean all publishers cache!");
        } catch (Exception e) {
            LOGGER.error("ERROR: {}", e.getMessage());
        }
    }

    @CacheEvict(value = "authors", allEntries = true)
    @Scheduled(cron = "0 0 * * * *")
    public void evictAllAuthorsCaches() {
        try {
            LOGGER.info("[:] Clean all authors cache!");
        } catch (Exception e) {
            LOGGER.error("ERROR: {}", e.getMessage());
        }
    }
}
