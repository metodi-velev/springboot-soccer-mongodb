package com.soccer.mongo.dtos;

import com.soccer.mongo.models.Player;
import com.soccer.mongo.models.PlayerPosition;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class CreatePlayerDto {
    private String name;

    private Date birthDate;

    private PlayerPosition position;

    private boolean isAvailable;

    public Player toPlayer() {
        return Player.builder()
                .name(name)
                .birthDate(birthDate)
                .position(position)
                .isAvailable(isAvailable)
                .build();
    }
}