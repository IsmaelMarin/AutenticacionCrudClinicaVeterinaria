package com.Principal.AutenticacionPerezosa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "autentications")
public class Autenticacion {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "autenticacionList")
    private List<User> userList2=new ArrayList<>();
    */

    @Getter
    @Setter
    @JsonIgnoreProperties({"autenticacionList"})
    @ManyToOne
    private User user;

    @Getter
    @Setter
    @Column(name = "access_token", nullable = false, unique = true)
    private String accessToken;

    @Getter
    @Setter
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_creacion;

    @Getter
    @Setter
    @Column(name = "fecha_expiracion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_expiracion;

    public Autenticacion(){

    }
    public Autenticacion(User user, String accessToken, Date fechaCreacion, Date fechaExpiracion) {
        this.user = user;
        this.accessToken = accessToken;
        this.fecha_creacion = fechaCreacion;
        this.fecha_expiracion = fechaExpiracion;
    }


}
