package com.api_horizonte.api_horizonte.Controller;

import com.api_horizonte.api_horizonte.Business.UserService;
import com.api_horizonte.api_horizonte.Infraestructure.DTO.AuthenticationDTO;
import com.api_horizonte.api_horizonte.Infraestructure.DTO.LoginResponseDTO;
import com.api_horizonte.api_horizonte.Infraestructure.Security.TokenService;
import jakarta.validation.Valid;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
