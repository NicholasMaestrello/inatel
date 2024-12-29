package com.example.gamemanager.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PublisherCache {

    private final Cache<String, Boolean> publisherCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES) // Adjust expiration as needed
            .build();


    public boolean contains(String publisherId) {
        return publisherCache.getIfPresent(publisherId) != null;
    }


    public void put(String publisherId, boolean exists) {
        publisherCache.put(publisherId, exists);
    }


    public void invalidateAll() {
        publisherCache.invalidateAll();
    }
}
