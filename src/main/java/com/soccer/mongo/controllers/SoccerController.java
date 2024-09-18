package com.soccer.mongo.controllers;

import com.soccer.mongo.dtos.CreatePlayerDto;
import com.soccer.mongo.dtos.CreateTeamDto;
import com.soccer.mongo.exception.TeamNotFoundException;
import com.soccer.mongo.models.Player;
import com.soccer.mongo.models.Team;
import com.soccer.mongo.service.PlayerService;
import com.soccer.mongo.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SoccerController {

    private final TeamService teamService;
    private final PlayerService playerService;

    public SoccerController(TeamService teamService, PlayerService playerService) {
        this.teamService = teamService;
        this.playerService = playerService;
    }

    @GetMapping("/teams")
    public String getAllTeams(Model model) {
        List<Team> allTeams = teamService.findAllTeams();
        model.addAttribute("teams", allTeams);
        return "/soccer/teams";
    }

    @GetMapping("/teams/{id}")
    public String getTeamById(@PathVariable String id, Model model) {
        Team team = teamService.findById(id).orElseThrow(() -> new TeamNotFoundException("Team not found with id: " + id));
        model.addAttribute("team", team);
        return "/soccer/edit_team";
    }

    @GetMapping("/teams/create")
    public String showCreateTeamForm(Model model) {
        model.addAttribute("team", new CreateTeamDto());
        return "/soccer/create_team";  // returns create_team.html
    }

    @PostMapping("/teams")
    public String createTeam(@ModelAttribute CreateTeamDto createTeamDto) {
        teamService.save(createTeamDto.toTeam());
        return "redirect:/teams";
    }

    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody CreatePlayerDto createPlayerDto) {
        Player playerCreated = playerService.save(createPlayerDto.toPlayer());
        return new ResponseEntity<>(playerCreated, HttpStatus.CREATED);
    }

    @PutMapping("/teams/{id}")
    public String updateTeam(@PathVariable String id, @ModelAttribute CreateTeamDto createTeamDto) {
        teamService.updateTeam(id, createTeamDto);
        return "redirect:/teams";
    }

    @DeleteMapping("/teams/delete/{id}")
    public String deleteTeam(@PathVariable String id) {
        teamService.deleteById(id);
        return "redirect:/teams";
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