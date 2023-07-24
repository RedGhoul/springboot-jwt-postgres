package com.app.springbootjwtpostgres.controller;

import com.app.springbootjwtpostgres.entity.User;
import com.app.springbootjwtpostgres.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // This annotation marks this class as a controller component that will be managed by Spring and that will return JSON responses by default.
@RequestMapping("/api/users") // This annotation specifies the base URL path for this controller class.
@CrossOrigin(origins = "*", maxAge = 3600) // This annotation allows cross-origin requests from any origin and with a maximum age of 3600 seconds (1 hour).
public class UserController {

    @Autowired // This annotation allows us to inject the UserService class into this class.
    private UserService userService;

    @GetMapping("/") // This annotation specifies that this method handles GET requests at the / endpoint of this controller class.
    @PreAuthorize("hasRole('ADMIN')") // This annotation specifies that this method can only be accessed by users who have the role of "ADMIN".
    public ResponseEntity<List<User>> getAllUsers() { // This method returns a ResponseEntity object that contains a list of all user entities in the database as the body.
        List<User> users = userService.findAllUsers(); // This line gets the list of all user entities using the UserService class.
        return ResponseEntity.ok(users); // This line returns an ok response with the list of user entities as the body.
    }

    @PostMapping("/add-role/{username}/{roleName}") // This annotation specifies that this method handles POST requests at the /add-role/{username}/{roleName} endpoint of this controller class, where {username} and {roleName} are path variables that represent the username and role name respectively.
    public ResponseEntity<?> addRoleToUser(@PathVariable String username, @PathVariable String roleName) { // This method takes two path variables as parameters and returns a ResponseEntity object as the response.
        User user = userService.addRoleToUser(username, roleName); // This line adds a role entity to a user entity by their names using the UserService class and returns the updated user entity.
        if (user != null) { // This block checks if the user entity is not null. If yes, it returns an ok response with a success message.
            return ResponseEntity.ok("Role added successfully to user!");
        }

        return ResponseEntity.badRequest().body("Error: User or role not found!"); // If no, it returns a bad request response with an error message.
    }

}
