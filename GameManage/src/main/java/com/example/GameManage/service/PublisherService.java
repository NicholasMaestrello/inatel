package com.example.GameManage.service;

import com.example.GameManage.cache.PublisherCache;
import com.example.GameManage.dto.PublisherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;

@Service
public class PublisherService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final PublisherCache publisherCache;

    @Value("${publisher-manager.url}")
    private String publisherManagerUrl;

    @Autowired
    public PublisherService(PublisherCache publisherCache) {
        this.publisherCache = publisherCache;
    }

    public boolean existsById(String publisherId) {
        if (publisherCache.contains(publisherId)) {
            return publisherCache.contains(publisherId);
        }
        boolean exists = fetchAndCachePublisher(publisherId);
        return exists;

    }

    private boolean fetchAndCachePublisher(String publisherId) {
        try {
            restTemplate.getForObject(publisherManagerUrl + "/publisher/" + publisherId, PublisherDto[].class);
            publisherCache.put(publisherId, true);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            publisherCache.put(publisherId, false);
            return false;
        } catch (Exception e){
            //Handle other exceptions
            return false;
        }
    }

}
