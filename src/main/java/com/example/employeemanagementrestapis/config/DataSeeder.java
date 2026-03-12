package com.example.employeemanagementrestapis.config;

import com.example.employeemanagementrestapis.models.Employee;
import com.example.employeemanagementrestapis.repositories.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(EmployeeRepository employeeRepository) {
        return args -> {
            if (employeeRepository.count() == 0) {
                Employee e1 = new Employee();
                e1.setName("Test Employee 1");
                employeeRepository.save(e1);

                Employee e2 = new Employee();
                e2.setName("Test Employee 2");
                employeeRepository.save(e2);
            }
        };
    }
}

