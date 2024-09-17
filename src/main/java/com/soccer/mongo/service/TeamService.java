package com.soccer.mongo.service;

import com.soccer.mongo.models.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    Team save(Team team);
    Optional<Team> findById(String id);
    void deleteById(String id);

    List<Team> findAllTeams();
}
