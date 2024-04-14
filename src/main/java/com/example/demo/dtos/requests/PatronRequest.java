package com.example.demo.dtos.requests;


import java.util.Date;

public class PatronRequest extends UserRequest {
    public PatronRequest(String userName, String password, String fullName, Date birthDate) {
        super(userName, password, fullName, birthDate);
    }
}
