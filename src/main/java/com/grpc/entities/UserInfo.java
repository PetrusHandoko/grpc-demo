package com.grpc.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class UserInfo {

    private String lastName;
    private String firstName;
    private String email;

    public UserInfo(String last, String first, String mail) {
        lastName = last;
        firstName = first;
        email =mail;
    }
}
