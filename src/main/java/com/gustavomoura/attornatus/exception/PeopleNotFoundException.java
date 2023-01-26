package com.gustavomoura.attornatus.exception;

public class PeopleNotFoundException extends RuntimeException{

    public PeopleNotFoundException(String message) {
        super(message);
    }
}
