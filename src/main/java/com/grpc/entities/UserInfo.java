package com.grpc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private String lastName;
    private String firstName;
    private String email;
    private String dateOfBirth;

    private String creationDate;
}
