package com.example.employeemanagementrestapis.security;

import com.example.employeemanagementrestapis.models.User;
import com.example.employeemanagementrestapis.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username.trim())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return AppUserDetails.from(user);
    }
}
