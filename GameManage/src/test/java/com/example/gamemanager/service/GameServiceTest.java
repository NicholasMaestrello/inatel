package com.example.gamemanager.service;

import com.example.gamemanager.entity.GameEntity;
import com.example.gamemanager.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PublisherService publisherService;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {

    }

    @Test
    @Transactional
    void createGame_validGame_savesGame() {
        GameEntity game = new GameEntity();
        game.setPublisherId("nintendo");
        game.setName("Mario");
        Map<LocalDate, Integer> timePlayed = new HashMap<>();
        timePlayed.put(LocalDate.now(), 10);
        game.setTimePlayed(timePlayed);

        when(gameRepository.save(any(GameEntity.class))).thenReturn(game);
        GameEntity createdGame = gameService.createGame(game);
        assertNotNull(createdGame);
        assertEquals("nintendo", createdGame.getPublisherId());
        assertEquals("Mario", createdGame.getName());
    }



    @Test
    void createGame_invalidPublisher_throwsException() {
        GameEntity game = new GameEntity();
        game.setPublisherId("invalidPublisher");
        game.setName("Game");

        when(publisherService.existsById(game.getPublisherId())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> gameService.createGame(game));
    }


    @Test
    void getAllGames_returnsAllGames() {
        GameEntity game1 = new GameEntity();
        game1.setPublisherId("nintendo");
        game1.setName("Mario");
        Map<LocalDate, Integer> timePlayed1 = new HashMap<>();
        timePlayed1.put(LocalDate.now(), 10);
        game1.setTimePlayed(timePlayed1);

        GameEntity game2 = new GameEntity();
        game2.setPublisherId("sega");
        game2.setName("Sonic");
        Map<LocalDate, Integer> timePlayed2 = new HashMap<>();
        timePlayed2.put(LocalDate.now(), 5);
        game2.setTimePlayed(timePlayed2);

        List<GameEntity> games = Arrays.asList(game1, game2);
        when(gameRepository.findAll()).thenReturn(games);

        List<GameEntity> retrievedGames = gameService.getAllGames();
        assertEquals(2, retrievedGames.size());
        assertEquals("nintendo", retrievedGames.get(0).getPublisherId());
        assertEquals("sega", retrievedGames.get(1).getPublisherId());

    }


    @Test
    void getGamesByPublisher_returnsGamesByPublisher() {
        GameEntity game1 = new GameEntity();
        game1.setPublisherId("nintendo");
        game1.setName("Mario");
        Map<LocalDate, Integer> timePlayed1 = new HashMap<>();
        timePlayed1.put(LocalDate.now(), 10);
        game1.setTimePlayed(timePlayed1);

        GameEntity game2 = new GameEntity();
        game2.setPublisherId("nintendo");
        game2.setName("Zelda");
        Map<LocalDate, Integer> timePlayed2 = new HashMap<>();
        timePlayed2.put(LocalDate.now(), 5);
        game2.setTimePlayed(timePlayed2);

        GameEntity game3 = new GameEntity();
        game3.setPublisherId("sega");
        game3.setName("Sonic");
        Map<LocalDate, Integer> timePlayed3 = new HashMap<>();
        timePlayed3.put(LocalDate.now(), 5);
        game3.setTimePlayed(timePlayed3);

        List<GameEntity> games = Arrays.asList(game1, game2, game3);
        when(gameRepository.findAll()).thenReturn(games);

        List<GameEntity> retrievedGames = gameService.getGamesByPublisher("nintendo");
        assertEquals(2, retrievedGames.size());
        assertEquals("nintendo", retrievedGames.get(0).getPublisherId());
        assertEquals("nintendo", retrievedGames.get(1).getPublisherId());

    }
}
