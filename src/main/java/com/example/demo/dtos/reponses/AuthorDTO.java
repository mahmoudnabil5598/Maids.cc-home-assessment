package com.example.demo.dtos.reponses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorDTO extends UserDTO {


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String bio;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "authors"})
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<BookDTO> books;


}
