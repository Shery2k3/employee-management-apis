package com.example.employeemanagementrestapis.models;

import com.example.employeemanagementrestapis.models.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType systemRole = RoleType.EMPLOYEE;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdAt;
}
