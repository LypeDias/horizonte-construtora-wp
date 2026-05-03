package com.api_horizonte.api_horizonte.Infraestructure.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="Users")

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "userActive")
    private UserActive userActive;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(getRole() == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_CLIENT"));
        else return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
    }

    @Override
    public String getUsername() {
        return getEmail();
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
        return true;
    }
}
