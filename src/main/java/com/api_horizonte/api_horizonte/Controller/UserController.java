package com.api_horizonte.api_horizonte.Controller;

import com.api_horizonte.api_horizonte.Business.UserService;
import com.api_horizonte.api_horizonte.Infraestructure.DTO.UserRequest;
import com.api_horizonte.api_horizonte.Infraestructure.DTO.UserRequestRefresh;
import com.api_horizonte.api_horizonte.Infraestructure.DTO.UserResponseDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 🔹 Criar CLIENTE (público)
    @PostMapping("/client")
    public ResponseEntity<UserResponseDefault> createClient(
            @RequestBody UserRequest userRequest
    ) {
        UserResponseDefault response = userService.createUserClient(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 🔹 Criar ADMIN (proteger depois com segurança)
    @PostMapping("/admin")
    public ResponseEntity<UserResponseDefault> createAdmin(
            @RequestBody UserRequest userRequest
    ) {
        UserResponseDefault response = userService.createUserAdmin(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 🔹 Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDefault> updateUser(
            @PathVariable int id,
            @RequestBody UserRequestRefresh request
    ) {
        UserResponseDefault response = userService.updateUserById(id, request);
        return ResponseEntity.ok(response);
    }

    // 🔹 Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDefault> findById(@PathVariable int id) {
        UserResponseDefault response = userService.findUserById(id);
        return ResponseEntity.ok(response);
    }

    // 🔹 Listar todos usuários (admin)
    @GetMapping
    public ResponseEntity<List<UserResponseDefault>> findAll() {
        List<UserResponseDefault> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    // 🔹 Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
