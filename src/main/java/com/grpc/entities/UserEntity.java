package com.grpc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Getter
    private String lastName;
    @Getter
    private String firstName;
    @Getter
    private String email;
    @Getter
    private String dateOfBirth;
    @Getter
    private String timeStamp;
}
