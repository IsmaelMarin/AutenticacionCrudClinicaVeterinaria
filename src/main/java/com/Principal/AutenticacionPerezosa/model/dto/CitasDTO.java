package com.Principal.AutenticacionPerezosa.model.dto;

import com.Principal.AutenticacionPerezosa.model.crud.citas;
import com.Principal.AutenticacionPerezosa.model.crud.servicios;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CitasDTO {

    private Long id;
    private LocalDateTime fechaHora;
    private Long mascotaId;
    private Long clinicaId;
    private List<Long> serviciosIds;

    public CitasDTO(citas cita) {
        this.id = cita.getId();
        this.fechaHora = cita.getFechaHora();
        this.mascotaId = cita.getMascotas().getId();
        this.clinicaId = cita.getClinicas().getId();
        this.serviciosIds = cita.getServicios().stream()
                .map(servicios::getId)
                .collect(Collectors.toList());
    }
}
