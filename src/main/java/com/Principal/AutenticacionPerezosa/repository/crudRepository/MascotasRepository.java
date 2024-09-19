package com.Principal.AutenticacionPerezosa.repository.crudRepository;

import com.Principal.AutenticacionPerezosa.model.crud.Mascotas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MascotasRepository extends JpaRepository<Mascotas,Long> {

    Optional<Mascotas> findByNameAndUserNo_Id(String name, Long userId);
}
