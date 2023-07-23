package com.app.springbootjwtpostgres.repository;


import com.app.springbootjwtpostgres.entity.User;
import org.springframework.data.jpa.repository.JpaRepository; // This interface provides basic CRUD methods for working with JPA entities.
import org.springframework.stereotype.Repository; // This annotation marks this interface as a repository component that will be managed by Spring.

import java.util.Optional;

@Repository // This annotation marks this interface as a repository component that will be managed by Spring.
public interface UserRepository extends JpaRepository<User, Long> { // This interface extends JpaRepository interface and specifies the entity type and the primary key type.

    Optional<User> findUserByUsername(String username); // This method declares a custom query method that will find a user by username. Spring Data JPA will automatically generate the implementation based on the method name.

    Boolean existsByUsername(String username); // This method declares a custom query method that will check if a user exists by username. Spring Data JPA will automatically generate the implementation based on the method name.

    Boolean existsByEmail(String email); // This method declares a custom query method that will check if a user exists by email. Spring Data JPA will automatically generate the implementation based on the method name.

}