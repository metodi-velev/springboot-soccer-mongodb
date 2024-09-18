package com.soccer.mongo.dtos;

import com.soccer.mongo.models.Address;
import com.soccer.mongo.models.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeamDto {
    private String name;

    private String acronym;

    private Address address;

    public Team toTeam() {
        return Team.builder()
                .name(name)
                .acronym(acronym)
                .address(address)
                .players(new HashSet<>())
                .build();
    }
}
