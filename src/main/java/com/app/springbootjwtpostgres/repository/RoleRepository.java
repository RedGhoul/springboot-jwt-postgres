package com.app.springbootjwtpostgres.repository;


import com.app.springbootjwtpostgres.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository; // This interface provides basic CRUD methods for working with JPA entities.
import org.springframework.stereotype.Repository; // This annotation marks this interface as a repository component that will be managed by Spring.

import java.util.Optional;

@Repository // This annotation marks this interface as a repository component that will be managed by Spring.
public interface RoleRepository extends JpaRepository<Role, Long> { // This interface extends JpaRepository interface and specifies the entity type and the primary key type.

    Optional<Role> findRoleByName(String name); // This method declares a custom query method that will find a role by name.

}