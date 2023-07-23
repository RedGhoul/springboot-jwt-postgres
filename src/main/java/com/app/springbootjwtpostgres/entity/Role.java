package com.app.springbootjwtpostgres.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity // This annotation marks this class as an entity class that will be mapped to a database table.
@Table(name = "roles") // This annotation specifies the name of the table that this entity class will be mapped to.
@Data // This annotation generates getters, setters, constructors, and toString methods for this class.
@NoArgsConstructor // This annotation generates a no-argument constructor for this class.
@AllArgsConstructor // This annotation generates an all-argument constructor for this class.
public class Role {

    @Id // This annotation marks this field as the primary key of the table.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This annotation specifies that this field will be generated automatically by the database.
    private Long id;

    @Column(name = "name", nullable = false, unique = true) // This annotation specifies the name, nullability, and uniqueness of the column that this field will be mapped to.
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY) // This annotation specifies that this field represents a many-to-many relationship with another entity class, and that the related entities will be fetched lazily (loaded on demand).
    private Set<User> users = new HashSet<>(); // This field stores a set of users that have this role.

}