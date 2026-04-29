package com.api_horizonte.api_horizonte.Infraestructure.Repositories;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional <User> findUserById(int id);

    Optional <User> findUserByEmail(String email);

    Optional <User> findUserByName(String name);

    Optional <User> findUserByCpf(String cpf);

    boolean existsByEmailAndIdNot(String email, int id);

    //@Transactional
    //void deleteUserById(int id);
}
