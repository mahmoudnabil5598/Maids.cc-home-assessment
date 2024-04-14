package com.example.demo.seeders;

import com.example.demo.dtos.requests.BookRequest;
import com.example.demo.services.BookService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@DependsOn({"authorSeeder"})

public class BookSeeder {

    private final BookService bookService;


    @Autowired
    public BookSeeder(BookService bookService) {
        this.bookService = bookService;
    }


    @SneakyThrows
    @PostConstruct
    @Transactional
    public void seedBooks() {

        Set<Long> myHashSet = new HashSet<>();
        myHashSet.add(1L);
        myHashSet.add(2L);
        myHashSet.add(3L);


        BookRequest book1 = new BookRequest("Book1", 2021, "1-abc", myHashSet);
        BookRequest book2 = new BookRequest("Book2", 2020, "2-abc", myHashSet);
        BookRequest book3 = new BookRequest("Book3", 2020, "3-abc", myHashSet);
        BookRequest book4 = new BookRequest("Book4", 2020, "4-abc", myHashSet);
        BookRequest book5 = new BookRequest("Book5", 2020, "5-abc", myHashSet);
        BookRequest book6 = new BookRequest("Book6", 2020, "6-abc", myHashSet);
        BookRequest book7 = new BookRequest("Book7", 2020, "7-abc", myHashSet);
        BookRequest book8 = new BookRequest("Book8", 2020, "8-abc", myHashSet);
        BookRequest book9 = new BookRequest("Book9", 2020, "9-abc", myHashSet);
        BookRequest book10 = new BookRequest("Book10", 2020, "10-abc", myHashSet);
        BookRequest book11 = new BookRequest("Book11", 2020, "11-abc", myHashSet);
        BookRequest book12 = new BookRequest("Book12", 2020, "12-abc", myHashSet);


        // save lo to database
        bookService.createBook(book1);
        bookService.createBook(book2);
        bookService.createBook(book3);
        bookService.createBook(book4);
        bookService.createBook(book5);
        bookService.createBook(book6);
        bookService.createBook(book7);
        bookService.createBook(book8);
        bookService.createBook(book9);
        bookService.createBook(book10);
        bookService.createBook(book11);
        bookService.createBook(book12);
    }
}
