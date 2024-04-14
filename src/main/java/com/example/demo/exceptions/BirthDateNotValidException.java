package com.example.demo.exceptions;

public class BirthDateNotValidException extends RuntimeException {

    public BirthDateNotValidException() {

        super("birthdate must be in the past");
    }
}
