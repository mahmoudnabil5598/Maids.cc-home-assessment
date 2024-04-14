package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "books"})
    private Set<Author> authors = new HashSet<>();

    @Column(nullable = false)
    private int publicationYear;

    @Column(unique = true, nullable = false)
    private String ISBN;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "books_patron",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "patron_id"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "books"})
    private Set<Patron> patrons = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "books"})
    private Patron currentPatron;

    private Boolean isBorrowed = false;

}
