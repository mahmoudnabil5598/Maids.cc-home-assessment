package com.example.demo.dtos.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

import static com.example.demo.utils.SystemUtil.writeObjectIntoString;

@AllArgsConstructor
@Getter
@Setter
public class UserRequest {
    @NotBlank(message = "username is required")
    private String userName;
    @NotBlank(message = "password is required")
    @Length(min = 8, max = 32, message = "password length must be greater than or equal 8 and smaller than or equal 32")
    private String password;
    @NotBlank(message = "full name is required")
    @Length(min = 8, max = 32, message = "full name length must be greater than or equal 8 and smaller than or equal 32")
    private String fullName;
    @NotNull(message = "birthdate is required")
    private Date birthDate;


    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode tree = objectMapper.valueToTree(this);

        return writeObjectIntoString(tree);
    }
}
