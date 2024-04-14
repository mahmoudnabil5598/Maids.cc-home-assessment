package com.example.demo.exceptions;

public class BookIsNotBorrowed extends RuntimeException {

    public BookIsNotBorrowed(Long id) {

        super("The book with id " + id + " is not being borrowed");
    }
}
