package com.Principal.AutenticacionPerezosa.repository.crudRepository;

import com.Principal.AutenticacionPerezosa.model.crud.historial_clinico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialClinicoRepository extends JpaRepository<historial_clinico,Long> {
}
