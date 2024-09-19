package com.Principal.AutenticacionPerezosa.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class RoleDTO {
    @NotBlank
    @NotNull
    @Getter
    @Setter
    private String name;
}
