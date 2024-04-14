package com.example.demo.seeders;


import com.example.demo.dtos.requests.AuthorRequest;
import com.example.demo.services.AuthorService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuthorSeeder {
    private final AuthorService authorService;


    @Autowired
    public AuthorSeeder(AuthorService authorService) {
        this.authorService = authorService;
    }


    @SneakyThrows
    @PostConstruct
    @Transactional
    public void seedAuthors() {

        AuthorRequest user1 = new AuthorRequest("Author1", "12345678", "Author Author 1", new Date(893980800), "bio");
        AuthorRequest user2 = new AuthorRequest("Author2", "12345678", "Author Author 2", new Date(893980800), "bio");
        AuthorRequest user3 = new AuthorRequest("Author3", "12345678", "Author Author 3", new Date(893980800), "bio");
        AuthorRequest user4 = new AuthorRequest("Author4", "12345678", "Author Author 4", new Date(893980800), "bio");
        AuthorRequest user5 = new AuthorRequest("Author5", "12345678", "Author Author 5", new Date(893980800), "bio");
        AuthorRequest user6 = new AuthorRequest("Author6", "12345678", "Author Author 6", new Date(893980800), "bio");
        AuthorRequest user7 = new AuthorRequest("Author7", "12345678", "Author Author 7", new Date(893980800), "bio");
        AuthorRequest user8 = new AuthorRequest("Author8", "12345678", "Author Author 8", new Date(893980800), "bio");
        AuthorRequest user9 = new AuthorRequest("Author9", "12345678", "Author Author 9", new Date(893980800), "bio");
        AuthorRequest user10 = new AuthorRequest("Author10", "12345678", "Author Author 10", new Date(893980800), "bio");
        AuthorRequest user11 = new AuthorRequest("Author11", "12345678", "Author Author 11", new Date(893980800), "bio");
        AuthorRequest user12 = new AuthorRequest("Author12", "12345678", "Author Author 12", new Date(893980800), "bio");


        // save lo to database
        authorService.createUser(user1);
        authorService.createUser(user2);
        authorService.createUser(user3);
        authorService.createUser(user4);
        authorService.createUser(user5);
        authorService.createUser(user6);
        authorService.createUser(user7);
        authorService.createUser(user8);
        authorService.createUser(user9);
        authorService.createUser(user10);
        authorService.createUser(user11);
        authorService.createUser(user12);

    }
}
