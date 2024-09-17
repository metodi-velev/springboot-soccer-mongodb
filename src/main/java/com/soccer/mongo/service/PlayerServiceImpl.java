package com.soccer.mongo.service;

import com.soccer.mongo.models.Player;
import com.soccer.mongo.repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player save(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public List<Player> saveAll(List<Player> players) {
        return playerRepository.saveAll(players);
    }

    @Override
    public Optional<Player> findById(String playerId) {
        return playerRepository.findById(playerId);
    }
}
