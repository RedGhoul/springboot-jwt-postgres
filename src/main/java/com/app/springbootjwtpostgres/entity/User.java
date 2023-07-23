package com.app.springbootjwtpostgres.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity // This annotation marks this class as an entity class that will be mapped to a database table.
@Table(name = "users") // This annotation specifies the name of the table that this entity class will be mapped to.
@Data // This annotation generates getters, setters, constructors, and toString methods for this class.
@NoArgsConstructor // This annotation generates a no-argument constructor for this class.
@AllArgsConstructor // This annotation generates an all-argument constructor for this class.
public class User {

    @Id // This annotation marks this field as the primary key of the table.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This annotation specifies that this field will be generated automatically by the database.
    private Long id;

    @Column(name = "username", nullable = false, unique = true) // This annotation specifies the name, nullability, and uniqueness of the column that this field will be mapped to.
    private String username;

    @Column(name = "password", nullable = false) // This annotation specifies the name and nullability of the column that this field will be mapped to.
    private String password;

    @Column(name = "email", nullable = false, unique = true) // This annotation specifies the name, nullability, and uniqueness of the column that this field will be mapped to.
    private String email;

    @ManyToMany(fetch = FetchType.EAGER) // This annotation specifies that this field represents a many-to-many relationship with another entity class, and that the related entities will be fetched eagerly (loaded together with this entity).
    @JoinTable(name = "user_roles", // This annotation specifies the name of the join table that will store the relationship between this entity class and another entity class.
            joinColumns = @JoinColumn(name = "user_id"), // This annotation specifies the name of the column in the join table that references the primary key of this entity class.
            inverseJoinColumns = @JoinColumn(name = "role_id")) // This annotation specifies the name of the column in the join table that references the primary key of another entity class.
    private Set<Role> roles = new HashSet<>(); // This field stores a set of roles that belong to this user.

}