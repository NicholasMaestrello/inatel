package com.example.gamemanager.cache;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PublisherCacheTest {

    @InjectMocks
    private PublisherCache publisherCache;

    @Test
    void testPutAndGet() {
        String publisherId = "nintendo";
        publisherCache.put(publisherId, true);
        assertTrue(publisherCache.contains(publisherId));
    }


    @Test
    void testInvalidateAll() {
        String publisherId1 = "nintendo";
        String publisherId2 = "sega";
        publisherCache.put(publisherId1, true);
        publisherCache.put(publisherId2, true);
        assertTrue(publisherCache.contains(publisherId1));
        assertTrue(publisherCache.contains(publisherId2));
        publisherCache.invalidateAll();
        assertFalse(publisherCache.contains(publisherId1));
        assertFalse(publisherCache.contains(publisherId2));

    }


    @Test
    void testContains_existingPublisher() {
        String publisherId = "nintendo";
        publisherCache.put(publisherId, true);
        assertTrue(publisherCache.contains(publisherId));
    }


    @Test
    void testContains_nonExistingPublisher() {
        String publisherId = "konami";
        assertFalse(publisherCache.contains(publisherId));
    }

    @Test
    void testPut_overwritesExistingEntry(){
        String publisherId = "nintendo";
        publisherCache.put(publisherId, true);
        assertTrue(publisherCache.contains(publisherId));
        publisherCache.put(publisherId, false);
        assertFalse(publisherCache.contains(publisherId));

    }
}
