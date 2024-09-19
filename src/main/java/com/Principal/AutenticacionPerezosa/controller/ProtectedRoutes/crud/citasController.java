package com.Principal.AutenticacionPerezosa.controller.ProtectedRoutes.crud;

import com.Principal.AutenticacionPerezosa.exception.ResourceNotFoundException;
import com.Principal.AutenticacionPerezosa.exception.UserValidator;
import com.Principal.AutenticacionPerezosa.model.Role;
import com.Principal.AutenticacionPerezosa.model.RoleName;
import com.Principal.AutenticacionPerezosa.model.User;
import com.Principal.AutenticacionPerezosa.model.crud.Mascotas;
import com.Principal.AutenticacionPerezosa.model.crud.citas;
import com.Principal.AutenticacionPerezosa.model.crud.clinicas;
import com.Principal.AutenticacionPerezosa.model.crud.servicios;
import com.Principal.AutenticacionPerezosa.model.dto.CitasDTO;
import com.Principal.AutenticacionPerezosa.model.dto.UserRoleDTO;
import com.Principal.AutenticacionPerezosa.repository.crudRepository.CitasRepository;
import com.Principal.AutenticacionPerezosa.repository.crudRepository.ClinicaRepository;
import com.Principal.AutenticacionPerezosa.repository.crudRepository.MascotasRepository;
import com.Principal.AutenticacionPerezosa.repository.crudRepository.ServiciosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/protected/citas")
public class citasController {

    @Autowired
    private CitasRepository citasRepository;

    @Autowired
    private MascotasRepository mascotasRepository;

    @Autowired
    private ClinicaRepository clinicasRepository;

    @Autowired
    private ServiciosRepository serviciosRepository;



    @PostMapping
    public ResponseEntity<?> registrarCita(@Valid @RequestBody citas detallesCita) {
        // Crear una nueva instancia de la entidad `citas`
        citas nuevaCita = new citas();

        // Asignar la fecha y hora de la cita
        nuevaCita.setFechaHora(detallesCita.getFechaHora());

        // Buscar la mascota asociada
        Mascotas mascota = mascotasRepository.findById(detallesCita.getMascotas().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con ID: " + detallesCita.getMascotas().getId()));
        nuevaCita.setMascotas(mascota);

        // Buscar la clínica asociada
        clinicas clinica = clinicasRepository.findById(detallesCita.getClinicas().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Clínica no encontrada con ID: " + detallesCita.getClinicas().getId()));
        nuevaCita.setClinicas(clinica);

        // Asignar los servicios a la cita (convertir a entidades gestionadas por JPA)
        List<servicios> listaServicios = new ArrayList<>();
        for (servicios servicio : detallesCita.getServicios()) {
            // Buscar cada servicio por su ID para asegurar que es una entidad gestionada
            servicios servicioEntidad = serviciosRepository.findById(servicio.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado con ID: " + servicio.getId()));
            listaServicios.add(servicioEntidad);
        }
        nuevaCita.setServicios(listaServicios);

        // Guardar la nueva cita en la base de datos
        citas citaGuardada = citasRepository.save(nuevaCita);

        //DTO PERSONALIZADOS
       //Agregarlo luego

        // Retornar la respuesta con la cita registrada
        return new ResponseEntity<>(citaGuardada, HttpStatus.CREATED);
        //return new ResponseEntity<>(new CitasDTO(citaGuardada), HttpStatus.CREATED);
    }

    // Obtener una cita por ID
    @GetMapping("/{citaId}")
    public ResponseEntity<?> obtenerCitaPorId(@PathVariable Long citaId) {
        citas cita = citasRepository.findById(citaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + citaId));

        //return new ResponseEntity<>(new CitasDTO(cita), HttpStatus.OK);
        return new ResponseEntity<>(cita, HttpStatus.CREATED);
    }

    // Obtener todas las citas
    @GetMapping("/all")
    public ResponseEntity<?> obtenerCitasAll() {
        List<citas> listaCitas = citasRepository.findAll();

        if (listaCitas.isEmpty()) {
            return new ResponseEntity<>("No se encontraron citas registradas", HttpStatus.NOT_FOUND);
        }

        /*
        List<CitasDTO> listaCitasDTO = listaCitas.stream()
                .map(CitasDTO::new)
                .collect(Collectors.toList());

         */

        return new ResponseEntity<>(listaCitas, HttpStatus.OK);
    }

    // Eliminar una cita por ID
    @DeleteMapping("/eliminar/{citaId}")
    public ResponseEntity<?> eliminarCita(@PathVariable Long citaId) {
        // Buscar la cita por ID
        citas cita = citasRepository.findById(citaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + citaId));

        // Eliminar la cita de la base de datos
        citasRepository.delete(cita);

        return new ResponseEntity<>("Cita eliminada correctamente", HttpStatus.OK);
    }

    // Actualizar una cita por ID
    @PutMapping("/actualizar/{citaId}")
    public ResponseEntity<?> actualizarCita(@PathVariable Long citaId, @Valid @RequestBody citas detallesCita) {
        // Buscar la cita por ID
        citas citaExistente = citasRepository.findById(citaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + citaId));

        // Actualizar fecha y hora de la cita
        citaExistente.setFechaHora(detallesCita.getFechaHora());


        // Buscar la mascota asociada
        Mascotas mascota = mascotasRepository.findById(detallesCita.getMascotas().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con ID: " + detallesCita.getMascotas().getId()));
        citaExistente.setMascotas(mascota);

        // Buscar la clínica asociada
        clinicas clinica = clinicasRepository.findById(detallesCita.getClinicas().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Clínica no encontrada con ID: " + detallesCita.getClinicas().getId()));
        citaExistente.setClinicas(clinica);

        // Asignar los servicios a la cita (convertir a entidades gestionadas por JPA)
        List<servicios> listaServicios = new ArrayList<>();
        for (servicios servicio : detallesCita.getServicios()) {
            // Buscar cada servicio por su ID para asegurar que es una entidad gestionada
            servicios servicioEntidad = serviciosRepository.findById(servicio.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado con ID: " + servicio.getId()));
            listaServicios.add(servicioEntidad);
        }
        citaExistente.setServicios(listaServicios);

        // Guardar la nueva cita en la base de datos
        citas citaGuardada = citasRepository.save(citaExistente);

        //DTO PERSONALIZADOS
        //Agregarlo luego

        // Retornar la respuesta con la cita registrada
        //return new ResponseEntity<>(citaGuardada, HttpStatus.CREATED);
        //return new ResponseEntity<>(new CitasDTO(citaGuardada), HttpStatus.CREATED);
        return new ResponseEntity<>(citaGuardada, HttpStatus.CREATED);
    }

}
