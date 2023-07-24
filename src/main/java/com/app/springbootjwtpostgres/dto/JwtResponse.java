package com.app.springbootjwtpostgres.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // This annotation generates getters, setters, constructors, and toString methods for this class.
@NoArgsConstructor // This annotation generates a no-argument constructor for this class.
@AllArgsConstructor
public class JwtResponse {

    private String token; // This field stores the JWT token.

    private String type = "Bearer"; // This field stores the token type.

    private Long id; // This field stores the id of the user.

    private String username; // This field stores the username of the user.

    private String email; // This field stores the email of the user.

    private List<String> roles; // This field stores a list of role names that belong to the user.

    public JwtResponse(String jwt, Long id, String username, String email, List<String> roles) {
        this.token = jwt;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}