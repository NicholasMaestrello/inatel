package com.example.gamepublisher.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Data
public class PublisherEntity {

    @Id
    @NotNull
    private String id;

    @NotNull
    private String name;
}
