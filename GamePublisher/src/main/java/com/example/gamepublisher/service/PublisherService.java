package com.example.gamepublisher.service;


import com.example.gamepublisher.entity.PublisherEntity;
import com.example.gamepublisher.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<PublisherEntity> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Optional<PublisherEntity> getPublisherById(String id) {
        return publisherRepository.findById(id);
    }

    public PublisherEntity createPublisher(PublisherEntity publisher) {
        return publisherRepository.save(publisher);
    }
}
