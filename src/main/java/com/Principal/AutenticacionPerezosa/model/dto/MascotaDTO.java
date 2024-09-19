package com.Principal.AutenticacionPerezosa.model.dto;

import com.Principal.AutenticacionPerezosa.model.crud.Mascotas;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MascotaDTO {

    private Long id;
    private String name;
    private String raza;
    private int edad;
    private String Dueño;

    public MascotaDTO(Mascotas mascotas){

        this.id=mascotas.getId();
        this.name=mascotas.getName();
        this.raza=mascotas.getRaza();
        this.edad=mascotas.getEdad();
        this.Dueño=mascotas.getUserNo().getUsername();

    }
}
