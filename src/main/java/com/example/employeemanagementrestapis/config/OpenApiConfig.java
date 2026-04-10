package com.example.employeemanagementrestapis.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Employee Management REST APIs",
                version = "v1",
                description = "API documentation for employee management endpoints",
                contact = @Contact(name = "API Support"),
                license = @License(name = "Internal Use")
        ),
        servers = {
                @Server(url = "/", description = "Current environment")
        }
)
public class OpenApiConfig {
}
