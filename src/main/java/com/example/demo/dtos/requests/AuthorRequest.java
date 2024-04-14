package com.example.demo.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AuthorRequest extends UserRequest {

    @NotBlank(message = "Author's bio can not be blank")
    @NotNull(message = "Author's bio is required")
    private String bio;

    public AuthorRequest(String userName, String password, String fullName, Date birthDate, String bio) {
        super(userName, password, fullName, birthDate);
        this.bio = bio;
    }
}

