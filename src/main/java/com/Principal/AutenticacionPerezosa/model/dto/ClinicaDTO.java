package com.Principal.AutenticacionPerezosa.model.dto;

import com.Principal.AutenticacionPerezosa.model.crud.clinicas;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ClinicaDTO {


    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private LocalTime horario_apertura;
    private LocalTime horario_cierre;

    public ClinicaDTO(clinicas clinica){
        this.id=clinica.getId();
        this.nombre=clinica.getNombre();
        this.direccion=clinica.getDireccion();
        this.telefono=clinica.getTelefono();
        this.horario_apertura=clinica.getHorario_apertura();
        this.horario_cierre=clinica.getHorario_cierre();
    }

}
