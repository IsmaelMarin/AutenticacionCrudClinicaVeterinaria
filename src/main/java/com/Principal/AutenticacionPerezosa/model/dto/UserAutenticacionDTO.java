
package com.Principal.AutenticacionPerezosa.model.dto;

import com.Principal.AutenticacionPerezosa.model.User;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserAutenticacionDTO {


    private String username;
    private List<String> roles;
    private String token;
    //private Date fechaCreacion;
    //private Date fechaExpiracion;
    private String fechaCreacion;
    private String fechaExpiracion;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public UserAutenticacionDTO() {
    }


    public UserAutenticacionDTO(String username, List<String> roles, String token, Date fechaCreacion, Date fechaExpiracion) {
        this.username=username;
        this.roles=roles;
        this.token=token;
        this.fechaCreacion=formatDate(fechaCreacion);
        this.fechaExpiracion=formatDate(fechaExpiracion);

    }
    private String formatDate(Date date) {
        return date != null ? dateFormat.format(date) : null;
    }
}
