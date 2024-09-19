package com.Principal.AutenticacionPerezosa.model.dto;

import com.Principal.AutenticacionPerezosa.model.crud.historial_clinico;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HistorialDTO {

    private Long id;
    private LocalDateTime fecha;
    private String descripcion;
    private String mascota;


    public HistorialDTO(historial_clinico historial){
        this.id=historial.getId();
        this.fecha=historial.getFecha();
        this.descripcion=historial.getDescripcion();
        this.mascota=historial.getMascotas().getName();

    }
}
