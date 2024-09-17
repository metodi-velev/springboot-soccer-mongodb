package com.soccer.mongo.exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(String s) {
        super(s);
    }
}
