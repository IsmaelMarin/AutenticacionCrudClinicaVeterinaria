package com.Principal.AutenticacionPerezosa.model.crud;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name ="clinicas")
public class clinicas {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @NotBlank
    private String nombre;

    @Getter
    @Setter
    @NotBlank
    private String direccion;

    @Getter
    @Setter
    @NotBlank
    private String telefono;

    @Getter
    @Setter
    @NotNull
    private LocalTime horario_apertura;

    @Getter
    @Setter
    @NotNull
    private LocalTime horario_cierre;

    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "clinicas", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<citas> citasClinicaList;

}
