package com.example.gamepublisher.repository;


import com.example.gamepublisher.entity.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PublisherRepository extends JpaRepository<PublisherEntity, String> {
}

