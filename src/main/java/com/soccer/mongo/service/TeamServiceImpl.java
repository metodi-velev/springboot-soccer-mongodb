package com.soccer.mongo.service;

import com.soccer.mongo.models.Team;
import com.soccer.mongo.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Optional<Team> findById(String id) {
        return teamRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        teamRepository.deleteById(id);
    }

    @Override
    public List<Team> findAllTeams() {
        return teamRepository.findAll();
    }
}
