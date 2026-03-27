package com.example.employeemanagementrestapis.exceptions.custom;

public class BusinessLogicException extends RuntimeException {
    public BusinessLogicException(String message) {
        super(message);
    }
}
