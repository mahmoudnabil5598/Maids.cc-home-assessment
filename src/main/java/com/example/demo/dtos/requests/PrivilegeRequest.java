package com.example.demo.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrivilegeRequest {


    private Long id;

    @NotNull(message = "Privilege name is required")
    @NotBlank(message = "Privilege name must not be blank")
    private String name;
}
