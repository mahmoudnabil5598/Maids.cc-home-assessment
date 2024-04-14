package com.example.demo.dtos.requests;

import com.example.demo.entities.Author;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {


    @NotNull(message = "Book title is required")
    @NotBlank(message = "Book title must not be blank")
    private String title;

    @Min(value = 1, message = "publication year must be greater than 0")
    private int publicationYear;

    @NotNull(message = "ISBN title is required")
    @NotBlank(message = "ISBN title must not be blank")
    private String ISBN;

    private Set<Long> authors = new HashSet<>();
    
}
