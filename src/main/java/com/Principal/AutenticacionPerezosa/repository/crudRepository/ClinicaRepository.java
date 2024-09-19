package com.Principal.AutenticacionPerezosa.repository.crudRepository;

import com.Principal.AutenticacionPerezosa.model.crud.Mascotas;
import com.Principal.AutenticacionPerezosa.model.crud.clinicas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClinicaRepository extends JpaRepository<clinicas,Long> {

    Optional<clinicas> findByNombre(String nombre);
}
