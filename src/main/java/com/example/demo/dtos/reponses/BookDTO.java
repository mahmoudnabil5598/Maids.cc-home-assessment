package com.example.demo.dtos.reponses;

import com.example.demo.entities.Author;
import com.example.demo.entities.Patron;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static com.example.demo.utils.SystemUtil.writeObjectIntoString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "books"})
    private Set<AuthorDTO> authors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int publicationYear;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ISBN;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "books"})
    private Set<PatronDTO> patrons;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isBorrowed;

    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode tree = objectMapper.valueToTree(this);

        return writeObjectIntoString(tree);
    }
}
