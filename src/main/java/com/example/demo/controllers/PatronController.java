package com.example.demo.controllers;

import com.example.demo.services.PatronService;
import com.example.demo.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patrons")
@Slf4j
public class PatronController extends UserController {

    private final PatronService patronService;

    @SuppressWarnings("unused")
    public PatronController(PatronService authorService) {
        this.patronService = authorService;
    }

    @Override
    protected UserService getService() {
        return patronService;
    }
}
