package com.Principal.AutenticacionPerezosa.repository.crudRepository;

import com.Principal.AutenticacionPerezosa.model.crud.clinicas;
import com.Principal.AutenticacionPerezosa.model.crud.servicios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiciosRepository extends JpaRepository<servicios,Long> {

    Optional<servicios> findByNombre(String nombre);
}
