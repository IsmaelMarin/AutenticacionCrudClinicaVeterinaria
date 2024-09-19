package com.Principal.AutenticacionPerezosa.model.crud;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "servicios")
public class servicios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Lob
    @Getter
    @Setter
    @NotBlank
    private String nombre;

    @Lob
    @Getter
    @Setter
    @NotBlank
    private String descripcion;

    @Column(precision = 10, scale = 3)
    @Getter
    @Setter
    @NotNull
    private BigDecimal precio;

    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "servicios")
    private List<citas> citasServicioList = new ArrayList<>();

}
