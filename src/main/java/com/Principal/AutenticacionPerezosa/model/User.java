package com.Principal.AutenticacionPerezosa.model;

import com.Principal.AutenticacionPerezosa.model.crud.Mascotas;
import com.Principal.AutenticacionPerezosa.model.crud.citas;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    @Getter
    @Setter
    private String nombre;

     */

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String direccion;

    //Validar telefono
    @Getter
    @Setter
    private String telefono;

    @Getter
    @Setter
    private LocalDateTime fecha_creacion;

    public User(){
        this.fecha_creacion=LocalDateTime.now();
    }

    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name="user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnoreProperties({"userList"})
    private List<Role> roleList = new ArrayList<>();

    /*
    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.PERSIST})
    @JoinTable(
            name="user_autenticacion",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "autenticacion_id")

    )
    @JsonIgnoreProperties({"userList2"})
    private List<Autenticacion> autenticacionList = new ArrayList<>();

     */

    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Autenticacion> autenticacionList;


    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@JsonIgnoreProperties({"userNo"}) //Ignoramos el estudiante de la lista de mascotas
    @OneToMany(mappedBy = "userNo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mascotas> mascotasList;


    /*
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@JsonIgnoreProperties({"userNo"}) //Ignoramos el estudiante de la lista de mascotas
    @OneToMany(mappedBy = "userNo2", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<citas> citasUserList;

    */


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
