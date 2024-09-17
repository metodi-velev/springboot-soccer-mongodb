package com.soccer.mongo.service;

import com.soccer.mongo.models.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    Player save(Player player);
    List<Player> saveAll(List<Player> players);
    Optional<Player> findById(String playerId);
}
