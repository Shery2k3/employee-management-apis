package com.example.employeemanagementrestapis.models;

import com.example.employeemanagementrestapis.models.enums.EmployeeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String phone;

    @Column(nullable = true)
    private String address;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type")
    private EmployeeType employmentType;
}
