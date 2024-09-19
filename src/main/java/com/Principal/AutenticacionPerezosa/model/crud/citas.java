package com.Principal.AutenticacionPerezosa.model.crud;

import com.Principal.AutenticacionPerezosa.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="citas")
public class citas {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private LocalDateTime fechaHora;

    public citas(){
        //Establecemos la fecha y hora por defecto
        this.fechaHora= LocalDateTime.now();
    }


    @Getter
    @Setter
    @JsonIgnoreProperties({"citasMascotaList"})
    @ManyToOne
    private Mascotas mascotas;

    @Getter
    @Setter
    @JsonIgnoreProperties({"citasClinicaList"})
    @ManyToOne
    private clinicas clinicas;


    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "citas_servicios",
            joinColumns = @JoinColumn(name = "citas_id"),
            inverseJoinColumns = @JoinColumn(name = "servicios_id")
    )
    //@JsonProperty("cursos")
    @JsonIgnoreProperties({"citasServicioList"})
    private List<servicios> servicios = new ArrayList<>();
}
