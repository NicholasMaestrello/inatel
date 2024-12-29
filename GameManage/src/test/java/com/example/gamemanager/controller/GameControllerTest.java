package com.example.gamemanager.controller;

import com.example.gamemanager.cache.PublisherCache;
import com.example.gamemanager.dto.GameDto;
import com.example.gamemanager.mapper.GameMapper;
import com.example.gamemanager.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private GameService gameService;

    private final GameMapper gameMapper = Mappers.getMapper(GameMapper.class);

    @Autowired
    private ObjectMapper objectMapper;

    private GameController gameController;

    @BeforeEach
    void setUp() {
        // Mock necessary dependencies
        PublisherCache publisherCache = new PublisherCache(); // Create a real instance here
        gameController = new GameController(gameService, gameMapper, publisherCache);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        this.objectMapper = new ObjectMapper();
    }


    @Test
    void createGame_validInput_returnsCreated() throws Exception {
        GameDto gameDto = new GameDto();
        gameDto.setPublisherId("nintendo");
        gameDto.setName("Mario");
        Map<LocalDate,Integer> timePlayed = new HashMap<>();
        timePlayed.put(LocalDate.now(),10);
        gameDto.setTimePlayed(timePlayed);

        var game = gameMapper.mapDtoToEntity(gameDto);

        when(gameService.createGame(any())).thenReturn(game);

        MvcResult result = mockMvc.perform(post("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andExpect(status().isCreated())
                .andReturn();


        String content = result.getResponse().getContentAsString();
        assertThat(content, containsString("nintendo"));
        assertThat(content, containsString("Mario"));
    }


    @Test
    void createGame_invalidInput_returnsBadRequest() throws Exception {
        GameDto gameDto = new GameDto();
        gameDto.setPublisherId("nin");
        gameDto.setName("Ma");
        Map<LocalDate,Integer> timePlayed = new HashMap<>();
        timePlayed.put(LocalDate.now(),10);
        gameDto.setTimePlayed(timePlayed);

        when(gameService.createGame(any()))
                .thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void getAllGames_returnsOk() throws Exception {
        mockMvc.perform(get("/api/game")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getGamesByPublisher_returnsOk() throws Exception {
        mockMvc.perform(get("/api/game")
                        .param("publisherId", "nintendo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}