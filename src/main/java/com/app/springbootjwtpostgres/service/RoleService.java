package com.app.springbootjwtpostgres.service;

import com.app.springbootjwtpostgres.entity.Role;
import com.app.springbootjwtpostgres.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired; // This annotation allows us to inject other components into this class.
import org.springframework.stereotype.Service; // This annotation marks this class as a service component that will be managed by Spring.

import java.util.List;

@Service // This annotation marks this class as a service component that will be managed by Spring.
public class RoleService {

    @Autowired // This annotation allows us to inject the RoleRepository interface into this class.
    private RoleRepository roleRepository;

    public Role saveRole(Role role) { // This method saves a role entity to the database.
        return roleRepository.save(role); // This line saves the role entity using the RoleRepository interface and returns it.
    }

    public Role findByName(String name) { // This method finds a role entity by name.
        return roleRepository.findRoleByName(name).orElse(null); // This line returns the role entity if found, or null otherwise, using the RoleRepository interface.
    }

    public List<Role> findAllRoles() { // This method returns a list of all role entities in the database.
        return roleRepository.findAll(); // This line returns the list of role entities using the RoleRepository interface.
    }

}