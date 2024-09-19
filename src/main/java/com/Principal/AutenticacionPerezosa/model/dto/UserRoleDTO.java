package com.Principal.AutenticacionPerezosa.model.dto;

import com.Principal.AutenticacionPerezosa.model.Role;
import com.Principal.AutenticacionPerezosa.model.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class UserRoleDTO<T> {

    /*
    private User user;

    public UserRoleDTO(User user){
        this.user=user;
    }

     */

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
    private String password;

    @Getter
    @Setter
    private List<Object> roleList;

    /*
    @Getter
    @Setter
    private String nombre;

     */

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String direccion;

    //Validar telefono
    @Getter
    @Setter
    private String telefono;

    @Getter
    @Setter
    private String message;



    public UserRoleDTO(){

    }
    /*
    public UserRoleDTO(User user, String message){
        this.username= user.getUsername();
        this.password= user.getPassword();
        this.roleList = user.getRoleList().stream()
                .map(role -> role.getName().name())  // Convertir el RoleName a String
                .collect(Collectors.toList());
        //this.roleList=user.getRoleList();
        //this.roleList = user.getRoleList().stream().map(role -> role.getName().name()).collect(Collectors.toList());
        this.message=message;


    }
    */

    // Constructor con parámetros
    public UserRoleDTO(User user, String message) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roleList = user.getRoleList().stream()
                .map(role -> role.getName().name())  // Convertir RoleName a String
                .collect(Collectors.toList());
        this.email=user.getEmail();
        this.direccion=user.getDireccion();
        this.telefono=user.getTelefono();
        this.message = message;
    }
}
