package com.example.GameManage.controller;


import com.example.GameManage.dto.GameDto;
import com.example.GameManage.entity.GameEntity;
import com.example.GameManage.mapper.GameMapper;
import com.example.GameManage.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameMapper gameMapper;

    @PostMapping
    public ResponseEntity<GameDto> createGame(@RequestBody @Validated GameDto gameDto) {
        try {
            var game = gameMapper.mapDtoToEntity(gameDto); // Add this mapping method
            var createdGame = gameService.createGame(game);
            return new ResponseEntity<>(gameMapper.mapEntityToDto(createdGame), HttpStatus.CREATED);
        } catch (IllegalArgumentException | DateTimeParseException e) { //Improved exception handling
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<GameDto>> getAllGames() {
        return ResponseEntity.ok(gameService.getAllGames().stream()
                .map(gameMapper::mapEntityToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping(params = "publisherId")
    public ResponseEntity<List<GameDto>> getGamesByPublisher(@RequestParam String publisherId) {
        return ResponseEntity.ok(gameService.getGamesByPublisher(publisherId).stream()
                .map(gameMapper::mapEntityToDto)
                .collect(Collectors.toList()));
    }
}
