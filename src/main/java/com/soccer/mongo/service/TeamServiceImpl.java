package com.soccer.mongo.service;

import com.soccer.mongo.dtos.CreateTeamDto;
import com.soccer.mongo.exception.TeamNotFoundException;
import com.soccer.mongo.models.Player;
import com.soccer.mongo.models.Team;
import com.soccer.mongo.repositories.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class TeamServiceImpl implements TeamService {

    private static final String TEAM_NOT_FOUND_MSG = "Team not found with id: ";

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
                .orElseThrow(() -> new TeamNotFoundException(TEAM_NOT_FOUND_MSG + id));

        team.setAddress(createTeamDto.getAddress())
                .setName(createTeamDto.getName())
                .setAcronym(createTeamDto.getAcronym());

        return save(team);
    }

    @Override
    public Team addPlayersToTeam(String id, List<String> playerIds) {
        Team teamToUpdate = findById(id)
                .orElseThrow(() -> new TeamNotFoundException(TEAM_NOT_FOUND_MSG + id));

        Set<Player> playersToAdd = getExistingPlayersByIds(playerIds);

        teamToUpdate.getPlayers().addAll(playersToAdd);

        return save(teamToUpdate);
    }

    @Override
    public Team removePlayersFromTeam(String id, List<String> playerIds) {
        Team teamToUpdate = findById(id)
                .orElseThrow(() -> new TeamNotFoundException(TEAM_NOT_FOUND_MSG + id));

        Set<Player> playersToRemove = getExistingPlayersByIds(playerIds);

        teamToUpdate.getPlayers().removeAll(playersToRemove);

        return teamRepository.save(teamToUpdate);
    }

    private Set<Player> getExistingPlayersByIds(List<String> playerIds) {
        return playerIds.stream()
                .map(playerService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}
