package com.app.springbootjwtpostgres.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data // This annotation generates getters, setters, constructors, and toString methods for this class.
@NoArgsConstructor // This annotation generates a no-argument constructor for this class.
@AllArgsConstructor // This annotation generates an all-argument constructor for this class.
public class SignupRequest {

    private String username; // This field stores the username of the user.

    private String password; // This field stores the password of the user.

    private String email; // This field stores the email of the user.

    private Set<String> roles; // This field stores a set of role names that belong to the user.

}