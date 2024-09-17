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
        Team updatedTeam = teamService.updateTeam(id, createTeamDto);
        return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable String id) {
        teamService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/players/bulk")
    public ResponseEntity<List<Player>> createPlayers(@RequestBody List<CreatePlayerDto> createPlayerDtoList) {
        List<Player> playersCreated = playerService.saveAll(createPlayerDtoList);
        return new ResponseEntity<>(playersCreated, HttpStatus.CREATED);
    }

    @PostMapping("/teams/{id}/players/add")
    public ResponseEntity<Team> addPlayersToTeam(@PathVariable String id, @RequestBody List<String> playerIds) {
        Team updatedTeam = teamService.addPlayersToTeam(id, playerIds);
        return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
    }

    @PostMapping("/teams/{id}/players/remove")
    public ResponseEntity<Team> removePlayersFromTeam(@PathVariable String id, @RequestBody List<String> playerIds) {
        Team updatedTeam = teamService.removePlayersFromTeam(id, playerIds);
        return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
    }
}