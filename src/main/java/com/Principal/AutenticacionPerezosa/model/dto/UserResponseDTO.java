package com.Principal.AutenticacionPerezosa.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class UserResponseDTO {

    private UserAutenticacionDTO userAutenticacionDTO;
    private String refreshToken;
    //private Date refreshTokenExpiracion;
    private String refreshTokenExpiracion;


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public UserResponseDTO() {
    }

    public UserResponseDTO(UserAutenticacionDTO userAutenticacionDTO, String refreshToken, Date refreshTokenExpiracion) {
        this.userAutenticacionDTO=userAutenticacionDTO;
        this.refreshToken=refreshToken;
        this.refreshTokenExpiracion=formatDate(refreshTokenExpiracion);

    }
    private String formatDate(Date date) {
        return date != null ? dateFormat.format(date) : null;
    }
}
