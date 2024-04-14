package com.example.demo.exceptions;

public class PublicationYearInTheFutureException extends RuntimeException {

    public PublicationYearInTheFutureException() {
        super("Publication year can not be in the future");
    }
}
