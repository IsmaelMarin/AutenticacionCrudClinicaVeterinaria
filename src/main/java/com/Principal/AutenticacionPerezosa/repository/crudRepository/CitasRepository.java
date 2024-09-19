package com.Principal.AutenticacionPerezosa.repository.crudRepository;

import com.Principal.AutenticacionPerezosa.model.crud.citas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitasRepository extends JpaRepository<citas,Long> {
}
