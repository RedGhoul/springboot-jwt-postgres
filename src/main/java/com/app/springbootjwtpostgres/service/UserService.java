package com.app.springbootjwtpostgres.service;

import com.app.springbootjwtpostgres.entity.Role;
import com.app.springbootjwtpostgres.entity.User;
import com.app.springbootjwtpostgres.repository.RoleRepository;
import com.app.springbootjwtpostgres.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired; // This annotation allows us to inject other components into this class.
import org.springframework.security.crypto.password.PasswordEncoder; // This interface provides methods for encoding and verifying passwords.
import org.springframework.stereotype.Service; // This annotation marks this class as a service component that will be managed by Spring.

import java.util.List;

@Service // This annotation marks this class as a service component that will be managed by Spring.
public class UserService {

    @Autowired // This annotation allows us to inject the UserRepository interface into this class.
    private UserRepository userRepository;

    @Autowired // This annotation allows us to inject the RoleRepository interface into this class.
    private RoleRepository roleRepository;

    @Autowired // This annotation allows us to inject the PasswordEncoder interface into this class.
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) { // This method saves a user entity to the database.
        user.setPassword(passwordEncoder.encode(user.getPassword())); // This line encodes the user's password using the PasswordEncoder interface.
        return userRepository.save(user); // This line saves the user entity using the UserRepository interface.
    }

    public User findByUsername(String username) { // This method finds a user entity by username.
        return userRepository.findUserByUsername(username).orElse(null); // This line returns the user entity if found, or null otherwise, using the UserRepository interface.
    }

    public List<User> findAllUsers() { // This method returns a list of all user entities in the database.
        return userRepository.findAll(); // This line returns the list of user entities using the UserRepository interface.
    }

    public boolean existsByUsername(String username) { // This method checks if a user entity exists by username.
        return userRepository.existsByUsername(username); // This line returns true or false using the UserRepository interface.
    }

    public boolean existsByEmail(String email) { // This method checks if a user entity exists by email.
        return userRepository.existsByEmail(email); // This line returns true or false using the UserRepository interface.
    }

    public User addRoleToUser(String username, String roleName) { // This method adds a role entity to a user entity by their names.
        User user = userRepository.findUserByUsername(username).orElse(null); // This line finds the user entity by username using the UserRepository interface.
        Role role = roleRepository.findRoleByName(roleName).orElse(null); // This line finds the role entity by name using the RoleRepository interface.

        if (user != null && role != null) { // This block checks if both entities are not null.
            user.getRoles().add(role); // This line adds the role entity to the set of roles of the user entity.
            return userRepository.save(user); // This line saves the updated user entity using the UserRepository interface and returns it.
        }

        return null; // This line returns null if either entity is null.
    }

}