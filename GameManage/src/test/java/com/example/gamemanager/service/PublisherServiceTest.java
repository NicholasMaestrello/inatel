package com.example.gamemanager.service;

import com.example.gamemanager.cache.PublisherCache;
import com.example.gamemanager.dto.PublisherDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublisherServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PublisherCache publisherCache;

    @InjectMocks
    private PublisherService publisherService;


    @Test
    void existsById_publisherExists_returnsTrue() {
        String publisherId = "nintendo";
        when(restTemplate.getForObject(anyString(), any())).thenReturn(new PublisherDto[]{new PublisherDto()});
        assertTrue(publisherService.existsById(publisherId));
        verify(restTemplate).getForObject(anyString(), eq(PublisherDto[].class));
    }

    @Test
    void existsById_publisherDoesNotExist_returnsFalse() {
        String publisherId = "nonexistent";
        doThrow(HttpClientErrorException.NotFound.class).when(restTemplate).getForObject(anyString(), eq(PublisherDto[].class));
        assertFalse(publisherService.existsById(publisherId));
        verify(restTemplate).getForObject(anyString(), eq(PublisherDto[].class));
    }

    @Test
    void existsById_networkError_returnsFalse() {
        String publisherId = "nintendo";
        doThrow(new RuntimeException("Network error")).when(restTemplate).getForObject(anyString(), eq(PublisherDto[].class));
        assertFalse(publisherService.existsById(publisherId));
        verify(restTemplate).getForObject(anyString(), eq(PublisherDto[].class));
    }

    @Test
    void existsById_publisherExistsInCache_returnsTrue(){
        String publisherId = "nintendo";
        when(publisherCache.contains(publisherId)).thenReturn(true);
        assertTrue(publisherService.existsById(publisherId));
        verify(restTemplate, never()).getForObject(anyString(), eq(PublisherDto[].class));
    }

    @Test
    void existsById_publisherDoesNotExistInCache_fetchesFromPublisherManager(){
        String publisherId = "nintendo";
        when(publisherCache.contains(publisherId)).thenReturn(false);
        when(restTemplate.getForObject(anyString(), eq(PublisherDto[].class))).thenReturn(new PublisherDto[]{new PublisherDto()});
        assertTrue(publisherService.existsById(publisherId));
        verify(restTemplate).getForObject(anyString(), eq(PublisherDto[].class));
    }

}
