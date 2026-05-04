package com.api_horizonte.api_horizonte.Infraestructure.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                        //Metodo público (sem autenticação)
                        .requestMatchers(HttpMethod.POST, "/users/client").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/login").permitAll() //JWT

                        //users
                        .requestMatchers(HttpMethod.POST, "/users/admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/me").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.PUT,  "/users/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN")

                        // ── Contracts ─────────────────────────────────────────────────
                        .requestMatchers(HttpMethod.POST,  "/contracts").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,   "/contracts/realstate/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,   "/contracts/realstate/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/contracts/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,   "/contracts/user/{cpf}").hasRole("ADMIN")  // ← virou só ADMIN
                        .requestMatchers(HttpMethod.GET,   "/contracts/me").hasAnyRole("ADMIN", "CLIENT") // ← novo

                        // ── Financial ─────────────────────────────────────────────
                        .requestMatchers(HttpMethod.GET,   "/financial/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,   "/financial/maturity/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,   "/financial/status/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/financial/{id}/pay").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,   "/financial/contract/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,   "/financial/me").hasAnyRole("ADMIN", "CLIENT")

                        // ── Real States ───────────────────────────────────────────────
                        .requestMatchers(HttpMethod.GET,  "/api/real-states/all").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,  "/api/real-states/**").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers(HttpMethod.POST, "/api/real-states").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,  "/api/real-states/**").hasRole("ADMIN")

                        // ── Units ─────────────────────────────────────────────────
                        .requestMatchers(HttpMethod.POST, "/units").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,  "/units/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,  "/units/**").hasAnyRole("ADMIN", "CLIENT")

                        // ── Qualquer outra rota bloqueada ─────────────────────────────
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
