package com.soccer.mongo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "teams")
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@Data
public class Team extends BaseModel {
    @Indexed(unique = true)
    @Field
    private String name;

    @Field
    @Indexed(unique = true)
    private String acronym;

    private Address address;

    @DBRef
    private Set<Player> players;

    public Team() {
        this.players = new HashSet<>();
    }
}