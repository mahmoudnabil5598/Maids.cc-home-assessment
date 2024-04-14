package com.example.demo.controllers;

import com.example.demo.services.AuthorService;
import com.example.demo.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@Slf4j
public class AuthorController extends UserController {
    private final AuthorService authorService;

    @SuppressWarnings("unused")
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    protected UserService getService() {
        return authorService;
    }
}
