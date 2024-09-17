package com.soccer.mongo.service;

import com.soccer.mongo.dtos.CreateTeamDto;
import com.soccer.mongo.exception.TeamNotFoundException;
import com.soccer.mongo.models.Player;
import com.soccer.mongo.models.Team;
import com.soccer.mongo.repositories.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final PlayerService playerService;

    public TeamServiceImpl(TeamRepository teamRepository, PlayerService playerService) {
        this.teamRepository = teamRepository;
        this.playerService = playerService;
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

    @Override
    public Team updateTeam(String id, CreateTeamDto createTeamDto) {
        Team team = findById(id)
                .orElseThrow(() -> new TeamNotFoundException("Team not found with id: " + id));

        team.setAddress(createTeamDto.getAddress())
                .setName(createTeamDto.getName())
                .setAcronym(createTeamDto.getAcronym());

        return save(team);
    }

    @Override
    public Team addPlayersToTeam(@PathVariable String id, @RequestBody List<String> playerIds) {
        Team teamToUpdate = findById(id)
                .orElseThrow(() -> new TeamNotFoundException("Team not found with id: " + id));

        Set<Player> playersToAdd = playerIds.stream()
                .map(playerService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        teamToUpdate.getPlayers().addAll(playersToAdd);

        return save(teamToUpdate);
    }
}
