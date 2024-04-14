package com.example.demo.seeders;

import com.example.demo.dtos.requests.PatronRequest;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.PatronService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PatronSeeder {
    private final PatronService patronService;


    @Autowired
    public PatronSeeder(PatronService patronService) {
        this.patronService = patronService;
    }

    @SneakyThrows
    @PostConstruct
    @Transactional
    public void seedPatrons() {

        PatronRequest user1 = new PatronRequest("Patron1", "12345678", "Patron Patron 1", new Date(893980800));
        PatronRequest user2 = new PatronRequest("Patron2", "12345678", "Patron Patron 2", new Date(893980800));
        PatronRequest user3 = new PatronRequest("Patron3", "12345678", "Patron Patron 3", new Date(893980800));
        PatronRequest user4 = new PatronRequest("Patron4", "12345678", "Patron Patron 4", new Date(893980800));
        PatronRequest user5 = new PatronRequest("Patron5", "12345678", "Patron Patron 5", new Date(893980800));
        PatronRequest user6 = new PatronRequest("Patron6", "12345678", "Patron Patron 6", new Date(893980800));
        PatronRequest user7 = new PatronRequest("Patron7", "12345678", "Patron Patron 7", new Date(893980800));
        PatronRequest user8 = new PatronRequest("Patron8", "12345678", "Patron Patron 8", new Date(893980800));
        PatronRequest user9 = new PatronRequest("Patron9", "12345678", "Patron Patron 9", new Date(893980800));
        PatronRequest user10 = new PatronRequest("Patron10", "12345678", "Patron Patron 10", new Date(893980800));
        PatronRequest user11 = new PatronRequest("Patron11", "12345678", "Patron Patron 11", new Date(893980800));
        PatronRequest user12 = new PatronRequest("Patron12", "12345678", "Patron Patron 12", new Date(893980800));


        // save lo to database
        patronService.createUser(user1);
        patronService.createUser(user2);
        patronService.createUser(user3);
        patronService.createUser(user4);
        patronService.createUser(user5);
        patronService.createUser(user6);
        patronService.createUser(user7);
        patronService.createUser(user8);
        patronService.createUser(user9);
        patronService.createUser(user10);
        patronService.createUser(user11);
        patronService.createUser(user12);

    }
}
