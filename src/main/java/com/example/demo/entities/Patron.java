package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Patron extends User {


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patron")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "patron"})
    private Set<ContactInformation> contactInformation = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "patrons")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "patrons"})
    private Set<Book> books = new HashSet<>();
}
