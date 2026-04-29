package com.api_horizonte.api_horizonte.Business;

import com.api_horizonte.api_horizonte.Infraestructure.DTO.UserRequest;
import com.api_horizonte.api_horizonte.Infraestructure.DTO.UserRequestRefresh;
import com.api_horizonte.api_horizonte.Infraestructure.DTO.UserResponseDefault;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.UserActive;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.UserRole;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.User;
import com.api_horizonte.api_horizonte.Infraestructure.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDefault findUserByName (String name){
        User user = userRepository.findUserByName(name).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado"));

        return new UserResponseDefault(
                user.getCpf(),
                user.getName(),
                user.getEmail()
        );
    }

    public UserResponseDefault findUserByEmail (String email){
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado")
        );

        return new UserResponseDefault(
                user.getCpf(),
                user.getName(),
                user.getEmail()
        );
    }

    public UserResponseDefault createUserAdmin (UserRequest userRequest){
        return createUser(userRequest, UserRole.ADMIN);
    }

    public UserResponseDefault createUserClient (UserRequest userRequest){
        return createUser(userRequest, UserRole.CLIENTE);
    }

    public UserResponseDefault createUser (UserRequest userRequest, UserRole role){

        //Validação
        if(userRepository.findUserByEmail(userRequest.email()).isPresent()){
            throw new RuntimeException("Email já cadastrado");
        }

        //Validação
        if(userRequest.cpf().length() != 11 || !userRequest.cpf().matches("[0-9]+")){
            throw new RuntimeException("CPF está no formato errado");
        }

        //Validação
        if(userRepository.findUserByCpf(userRequest.cpf()).isPresent()){
            throw new RuntimeException("CPF já existe");
        }

        //Criptografar senha
        String passwordHash = passwordEncoder.encode(userRequest.password());

        UserActive definedActive = UserActive.ATIVO;

        User user = User.builder()
                .cpf(userRequest.cpf())
                .name(userRequest.name())
                .email(userRequest.email())
                .password(passwordHash)
                .role(role)
                .userActive(definedActive)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return new UserResponseDefault(
                user.getCpf(),
                user.getName(),
                user.getEmail()
        );
    }

    public UserResponseDefault updateUserById(int id, UserRequestRefresh newUserRequest){
        User user = userRepository.findUserById(id).orElseThrow(
                () -> new RuntimeException("Id customer is invalid")
        );

        //validação de email
        /*if(newUserRequest.email() != null) {
            if(!newUserRequest.email().equals(user.getEmail())){
                long totalEmails = userRepository.countByEmail(newUserRequest.email());
                if (totalEmails >= 2) {
                    throw new RuntimeException("Email já existe!!");
                }
            }
        }*/

        if (newUserRequest.email() != null) {
            if (!newUserRequest.email().equals(user.getEmail())) {

                boolean exists = userRepository
                        .existsByEmailAndIdNot(newUserRequest.email(), id);

                if (exists) {
                    throw new RuntimeException("Email já existe!");
                }

                user.setEmail(newUserRequest.email());
            }
        }

        if(newUserRequest.oldPassword() != null && newUserRequest.newPassword() != null) {

            boolean matches = passwordEncoder.matches(
                    newUserRequest.oldPassword(),
                    user.getPassword()
            );

            if(matches){
                user.setPassword(passwordEncoder.encode(newUserRequest.newPassword()));
            } else {
                throw new RuntimeException("Senha antiga está inválida");
            }

        }

        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        return new UserResponseDefault(
                user.getCpf(),
                user.getName(),
                user.getEmail()
        );



    }
}
