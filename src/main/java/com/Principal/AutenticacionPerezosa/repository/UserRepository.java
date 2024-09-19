package com.Principal.AutenticacionPerezosa.repository;

import com.Principal.AutenticacionPerezosa.model.Role;
import com.Principal.AutenticacionPerezosa.model.RoleName;
import com.Principal.AutenticacionPerezosa.model.User;
import com.Principal.AutenticacionPerezosa.model.crud.Mascotas;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    //Optional<User> findByUsername(String name);

    @EntityGraph(attributePaths = "roleList")
    Optional<User> findByUsername(String username);
    //Optional<User> findByUsername(String username);

}
