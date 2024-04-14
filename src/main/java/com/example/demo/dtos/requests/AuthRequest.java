package com.example.demo.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthRequest {

    @NotBlank(message = "username is required")
    private String userName;

    @NotBlank(message = "password is required")
    private String password;
}
