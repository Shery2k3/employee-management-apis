package com.example.employeemanagementrestapis.dtos.auth;

import com.example.employeemanagementrestapis.models.User;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        UserResponse user
) {
    public static AuthResponse from(User user, String accessToken, long expiresIn) {
        return new AuthResponse(accessToken, "Bearer", expiresIn, UserResponse.from(user));
    }
}
