package com.app.springbootjwtpostgres.controller;

import com.app.springbootjwtpostgres.dto.JwtResponse;
import com.app.springbootjwtpostgres.dto.LoginRequest;
import com.app.springbootjwtpostgres.dto.SignupRequest;
import com.app.springbootjwtpostgres.entity.Role;
import com.app.springbootjwtpostgres.entity.User;
import com.app.springbootjwtpostgres.security.JwtUtils;
import com.app.springbootjwtpostgres.service.RoleService;
import com.app.springbootjwtpostgres.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController // This annotation marks this class as a controller component that will be managed by Spring and that will return JSON responses by default.
@RequestMapping("/api/auth") // This annotation specifies the base URL path for this controller class.
@CrossOrigin(origins = "*", maxAge = 3600) // This annotation allows cross-origin requests from any origin and with a maximum age of 3600 seconds (1 hour).
public class AuthController {

    @Autowired // This annotation allows us to inject the AuthenticationManager interface into this class.
    private AuthenticationManager authenticationManager;

    @Autowired // This annotation allows us to inject the UserService class into this class.
    private UserService userService;

    @Autowired // This annotation allows us to inject the RoleService class into this class.
    private RoleService roleService;

    @Autowired // This annotation allows us to inject the JwtUtils class into this class.
    private JwtUtils jwtUtils;

    @PostMapping("/signup") // This annotation specifies that this method handles POST requests at the /signup endpoint of this controller class.
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) { // This method takes a valid SignupRequest object as the request body and returns a ResponseEntity object as the response.

        if (userService.existsByUsername(signupRequest.getUsername())) { // This block checks if the username already exists in the database using the UserService class.
            return ResponseEntity.badRequest().body("Error: Username is already taken!"); // If yes, it returns a bad request response with an error message.
        }

        if (userService.existsByEmail(signupRequest.getEmail())) { // This block checks if the email already exists in the database using the UserService class.
            return ResponseEntity.badRequest().body("Error: Email is already in use!"); // If yes, it returns a bad request response with an error message.
        }
        Set<Role> roles = new HashSet<>(); // This line creates a new set of Role objects.
        User user = new User(
                0L,
                signupRequest.getUsername(),
                signupRequest.getPassword(),
                signupRequest.getEmail(), roles); // This line creates a new User object with the data from the SignupRequest object.

        Set<String> strRoles = signupRequest.getRoles(); // This line gets the set of role names from the SignupRequest object.


        if (strRoles == null || strRoles.isEmpty()) { // This block checks if the set of role names is null or empty. If yes, it assigns a default role of "user" to the user.
            Role userRole = roleService.findByName("user"); // This line finds the role entity with the name "user" using the RoleService class.
            if (userRole == null) { // This block checks if the role entity is null. If yes, it creates a new role entity with the name "user" and saves it to the database using the RoleService class.
                userRole = new Role();
                userRole.setName("user");
                roleService.saveRole(userRole);
            }
            roles.add(userRole); // This line adds the role entity to the set of roles.
        } else { // If no, it iterates over the set of role names and finds or creates the corresponding role entities and adds them to the set of roles.
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.findByName("admin");
                        if (adminRole == null) {
                            adminRole = new Role();
                            adminRole.setName("admin");
                            roleService.saveRole(adminRole);
                        }
                        roles.add(adminRole);
                        break;
                    case "moderator":
                        Role modRole = roleService.findByName("moderator");
                        if (modRole == null) {
                            modRole = new Role();
                            modRole.setName("moderator");
                            roleService.saveRole(modRole);
                        }
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleService.findByName("user");
                        if (userRole == null) {
                            userRole = new Role();
                            userRole.setName("user");
                            roleService.saveRole(userRole);
                        }
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles); // This line sets the set of roles to the user object.
        userService.saveUser(user); // This line saves the user object to the database using the UserService class.

        return ResponseEntity.ok("User registered successfully!"); // This line returns an ok response with a success message.
    }

    @PostMapping("/login") // This annotation specifies that this method handles POST requests at the /login endpoint of this controller class.
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) { // This method takes a valid LoginRequest object as the request body and returns a ResponseEntity object as the response.

        Authentication authentication = authenticationManager.authenticate( // This line authenticates the user using the AuthenticationManager interface and returns an Authentication object that represents the authenticated user.
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())); // This line creates a new UsernamePasswordAuthenticationToken object with the username and password from the LoginRequest object.

        SecurityContextHolder.getContext().setAuthentication(authentication); // This line sets the Authentication object to the security context of the current thread using the SecurityContextHolder class.
        String jwt = jwtUtils.generateJwtToken(authentication); // This line generates a JWT token using the JwtUtils class and the Authentication object.

        UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // This line gets the UserDetails object from the Authentication object.
        List<String> roles = userDetails.getAuthorities().stream() // This line gets a list of role names from the UserDetails object by streaming over its authorities and mapping them to their names.
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        User currentUser = userService.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok(new JwtResponse(jwt,currentUser.getId(), userDetails.getUsername(), currentUser.getEmail(), roles)); // This line returns an ok response with a new JwtResponse object that contains the JWT token and some user information.
    }

}
