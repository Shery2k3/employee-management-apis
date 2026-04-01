package com.example.employeemanagementrestapis.models;

import com.example.employeemanagementrestapis.models.enums.EmployeeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    // Self-referencing FK to show managerial hierarchy, null means top of the chain of command
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    @JsonIgnore
    private Employee manager;

    // For reverse traversal later
    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Employee> subordinates = new ArrayList<>();

    @Column
    private String jobTitle;

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
