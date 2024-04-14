package com.example.demo.dtos.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.demo.utils.SystemUtil.writeObjectIntoString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactInformationRequest {


    private String mobileNumber;

    private String address;

    @NotNull(message = "patronId is required ")
    private Long patronId;


    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode tree = objectMapper.valueToTree(this);

        return writeObjectIntoString(tree);
    }
}
