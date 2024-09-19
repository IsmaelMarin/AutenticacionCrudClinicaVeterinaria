package com.Principal.AutenticacionPerezosa.model.dto;

import com.Principal.AutenticacionPerezosa.model.crud.servicios;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ServicioDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;

    public ServicioDTO(servicios detallesServicio){
        this.id=detallesServicio.getId();
        this.nombre=detallesServicio.getNombre();
        this.descripcion=detallesServicio.getDescripcion();
        this.precio=detallesServicio.getPrecio();
    }
}
