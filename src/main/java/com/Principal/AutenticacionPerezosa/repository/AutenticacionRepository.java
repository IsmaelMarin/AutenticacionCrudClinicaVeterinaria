package com.Principal.AutenticacionPerezosa.repository;

import com.Principal.AutenticacionPerezosa.model.Autenticacion;
import com.Principal.AutenticacionPerezosa.model.Role;
import com.Principal.AutenticacionPerezosa.model.RoleName;
import com.Principal.AutenticacionPerezosa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutenticacionRepository extends JpaRepository<Autenticacion,Long> {

    //Optional<User> findByUserName(String username);

}
