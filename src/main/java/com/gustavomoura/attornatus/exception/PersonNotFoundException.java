package com.gustavomoura.attornatus.exception;

public class PersonNotFoundException extends RuntimeException{

    public PersonNotFoundException(String message) {
        super(message);
    }
}
