package com.example.employeemanagementrestapis.services;

import com.example.employeemanagementrestapis.dtos.auth.AuthRequest;
import com.example.employeemanagementrestapis.dtos.auth.AuthResponse;
import com.example.employeemanagementrestapis.exceptions.custom.BusinessLogicException;
import com.example.employeemanagementrestapis.exceptions.custom.ResourceNotFoundException;
import com.example.employeemanagementrestapis.models.User;
import com.example.employeemanagementrestapis.models.enums.RoleType;
import com.example.employeemanagementrestapis.repositories.UserRepository;
import com.example.employeemanagementrestapis.security.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            CustomUserDetailsService customUserDetailsService,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
    }

    @Transactional
    public User registerUser(String email, String password) {
        String normalizedEmail = normalizeEmail(email);

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new BusinessLogicException("User with email '" + normalizedEmail + "' already exists.");
        }

        String hashedPassword = passwordEncoder.encode(password);

        User newUser = new User();
        newUser.setEmail(normalizedEmail);
        newUser.setPassword(hashedPassword);
        newUser.setSystemRole(RoleType.EMPLOYEE);
        return userRepository.save(newUser);
    }

    public User authenticateUser(String email, String password) {
        String normalizedEmail = normalizeEmail(email);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(normalizedEmail, password));

        return userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User with email: " + normalizedEmail + " not found."));
    }

    @Transactional
    public AuthResponse register(AuthRequest request) {
        User user = registerUser(request.email(), request.password());
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        return AuthResponse.from(user, token, jwtService.getExpirationMs());
    }

    public AuthResponse login(AuthRequest request) {
        User user = authenticateUser(request.email(), request.password());
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        return AuthResponse.from(user, token, jwtService.getExpirationMs());
    }

    private String normalizeEmail(String email) {
        if (email == null) {
            throw new BusinessLogicException("Email is required.");
        }

        String normalizedEmail = email.trim().toLowerCase();
        if (normalizedEmail.isBlank()) {
            throw new BusinessLogicException("Email is required.");
        }

        return normalizedEmail;
    }
}
