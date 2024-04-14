package com.example.demo.dtos.reponses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import static com.example.demo.utils.SystemUtil.writeObjectIntoString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactInformationDTO {


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "contactInformation"})
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PatronDTO patron;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mobileNumber;


    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode tree = objectMapper.valueToTree(this);

        return writeObjectIntoString(tree);
    }

}
