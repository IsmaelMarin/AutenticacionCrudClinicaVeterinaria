package com.Principal.AutenticacionPerezosa.repository;

import com.Principal.AutenticacionPerezosa.model.Role;
import com.Principal.AutenticacionPerezosa.model.RoleName;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName name); //Encontrar un rol por su nombre
}
