package com.example.employeemanagementrestapis.security;

import com.example.employeemanagementrestapis.models.User;
import com.example.employeemanagementrestapis.models.enums.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AppUserDetails implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final RoleType roleType;
    private final boolean active;

    public AppUserDetails(Long id, String email, String password, RoleType roleType, boolean active) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roleType = roleType;
        this.active = active;
    }

    public static AppUserDetails from(User user) {
        return new AppUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getSystemRole(),
                Boolean.TRUE.equals(user.getIsActive())
        );
    }

    public Long getId() {
        return id;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + roleType.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
