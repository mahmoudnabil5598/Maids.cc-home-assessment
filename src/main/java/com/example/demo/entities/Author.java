package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Author extends User {

    private String bio;


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authors")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "authors"})
    private Set<Book> books = new HashSet<>();
}
