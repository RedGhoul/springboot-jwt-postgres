package com.app.springbootjwtpostgres.security;

import com.app.springbootjwtpostgres.entity.Role;
import com.app.springbootjwtpostgres.entity.User;
import com.app.springbootjwtpostgres.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service // This annotation marks this class as a service component that will be managed by Spring.
public class UserDetailsServiceImpl implements UserDetailsService { // This class implements UserDetailsService interface and overrides its methods.

    @Autowired // This annotation allows us to inject the UserService class into this class.
    private UserService userService;

    @Override // This annotation indicates that this method overrides a method from the interface.
    @Transactional // This annotation marks this method as transactional, meaning that it will be executed within a database transaction.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // This method loads user details by username and returns a UserDetails object or throws an exception if not found.
        User user = userService.findByUsername(username); // This line finds the user entity by username using the UserService class.

        if (user == null) { // This block checks if the user entity is null. If yes, it throws an exception with an error message.
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }

        return UserDetailsImpl.build(user); // If no, it returns a new User object (from Spring Security) with the username, password, and authorities of the user entity, using a helper method to map roles to authorities.
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) { // This is a helper method that maps
// This is a helper method that maps a collection of role entities to a collection of granted authorities.
        return roles.stream() // This line streams over the collection of role entities.
                .map(role -> new SimpleGrantedAuthority(role.getName())) // This line maps each role entity to a new SimpleGrantedAuthority object with the role name as the authority.
                .collect(Collectors.toList()); // This line collects the mapped authorities into a list and returns it.
    }

}