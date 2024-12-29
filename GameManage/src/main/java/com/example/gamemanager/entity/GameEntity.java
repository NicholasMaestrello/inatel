package com.example.gamemanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Entity
public class GameEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private String publisherId;

    @ElementCollection
    @CollectionTable(name = "game_time_played", joinColumns = @JoinColumn(name = "game_id"))
    @MapKeyColumn(name = "play_date")
    @Column(name = "play_time")
    private Map<LocalDate, Integer> timePlayed = new HashMap<>();

}
