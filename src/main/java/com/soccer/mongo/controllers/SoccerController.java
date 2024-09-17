package com.soccer.mongo.controllers;

import com.soccer.mongo.dtos.CreatePlayerDto;
import com.soccer.mongo.dtos.CreateTeamDto;
import com.soccer.mongo.models.Player;
import com.soccer.mongo.models.Team;
import com.soccer.mongo.service.PlayerService;
import com.soccer.mongo.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class SoccerController {

    private final TeamService teamService;
    private final PlayerService playerService;

    public SoccerController(TeamService teamService, PlayerService playerService) {
        this.teamService = teamService;
        this.playerService = playerService;
    }

    @GetMapping("/teams")
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> allTeams = teamService.findAllTeams();

        return new ResponseEntity<>(allTeams, HttpStatus.CREATED);
    }

    @PostMapping("/teams")
    public ResponseEntity<Team> createTeam(@RequestBody CreateTeamDto createTeamDto) {
        Team teamCreated = teamService.save(createTeamDto.toTeam());

        return new ResponseEntity<>(teamCreated, HttpStatus.CREATED);
    }

    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody CreatePlayerDto createPlayerDto) {
        Player playerCreated = playerService.save(createPlayerDto.toPlayer());

        return new ResponseEntity<>(playerCreated, HttpStatus.CREATED);
    }

    @PutMapping("/teams/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable String id, @RequestBody CreateTeamDto createTeamDto) {
        Optional<Team> optionalTeam = teamService.findById(id);

        if (optionalTeam.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Team teamToUpdate = optionalTeam.get()
                .setAddress(createTeamDto.getAddress())
                .setName(createTeamDto.getName())
                .setAcronym(createTeamDto.getAcronym());

        Team updatedTeam = teamService.save(teamToUpdate);

        return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable String id) {
        teamService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/players/bulk")
    public ResponseEntity<List<Player>> createPlayers(@RequestBody List<CreatePlayerDto> createPlayerDtoList) {
        List<Player> players = createPlayerDtoList
                .stream()
                .map(CreatePlayerDto::toPlayer)
                .toList();

        List<Player> playersCreated = playerService.saveAll(players);

        return new ResponseEntity<>(playersCreated, HttpStatus.CREATED);
    }

    @PostMapping("/teams/{id}/players")
    public ResponseEntity<Team> addPlayersToTeam(@PathVariable String id, @RequestBody List<String> playerIds) {
        Optional<Team> optionalTeam = teamService.findById(id);

        if (optionalTeam.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Team teamToUpdate = optionalTeam.get();

        Set<Player> playersToAdd = playerIds.stream()
                .map(playerService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        teamToUpdate.setPlayers(playersToAdd);

        Team teamUpdated = teamService.save(teamToUpdate);

        return new ResponseEntity<>(teamUpdated, HttpStatus.OK);
    }
}