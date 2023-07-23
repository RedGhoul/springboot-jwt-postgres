package com.app.springbootjwtpostgres.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // This annotation generates getters, setters, constructors, and toString methods for this class.
@NoArgsConstructor // This annotation generates a no-argument constructor for this class.
@AllArgsConstructor // This annotation generates an all-argument constructor for this class.
public class LoginRequest {

    private String username; // This field stores the username of the user.

    private String password; // This field stores the password of the user.

}