package com.soccer.mongo.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@EqualsAndHashCode
@Setter
@Getter
public abstract class BaseModel {
    @MongoId(FieldType.OBJECT_ID)
    protected String id;

    protected Date createdAt;

    protected Date updatedAt;
}
