package com.example.demo.exceptions;

public class BookIsAlreadyBorrowedException extends RuntimeException {
    public BookIsAlreadyBorrowedException(Long id) {

        super("The book with id " + id + " is already being borrowed");
    }
}
