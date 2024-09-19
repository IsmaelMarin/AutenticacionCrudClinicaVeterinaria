package com.Principal.AutenticacionPerezosa.model.crud;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name ="historial_clinica")
public class historial_clinico {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private LocalDateTime fecha;

    @Lob
    @Getter
    @Setter
    private String descripcion;

    public historial_clinico(){
        this.fecha=LocalDateTime.now();
    }

    @Getter
    @Setter
    @JsonIgnoreProperties({"historialClinicoMascotaList"})
    @ManyToOne
    private Mascotas mascotas;

}
