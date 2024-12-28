package com.example.GameManage.service;


import com.example.GameManage.entity.GameEntity;
import com.example.GameManage.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Transactional
    public GameEntity createGame(GameEntity game) {
        return gameRepository.save(game);
    }

    public List<GameEntity> getAllGames() {
        return gameRepository.findAll();
    }

    public List<GameEntity> getGamesByPublisher(String publisherId) {
        return gameRepository.findAll().stream()
                .filter(game -> game.getPublisherId().equals(publisherId))
                .collect(Collectors.toList());
    }
}
