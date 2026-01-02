package com.example.horsestablesystem.exception;

public class HorseExistsException extends RuntimeException {
    public HorseExistsException(String message) {
        super(message);
    }
}