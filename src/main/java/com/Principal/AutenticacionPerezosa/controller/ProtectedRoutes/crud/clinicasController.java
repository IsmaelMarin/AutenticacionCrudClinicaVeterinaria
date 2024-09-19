package com.Principal.AutenticacionPerezosa.controller.ProtectedRoutes.crud;


import com.Principal.AutenticacionPerezosa.exception.ResourceNotFoundException;
import com.Principal.AutenticacionPerezosa.model.crud.Mascotas;
import com.Principal.AutenticacionPerezosa.model.crud.clinicas;
import com.Principal.AutenticacionPerezosa.model.crud.historial_clinico;
import com.Principal.AutenticacionPerezosa.model.dto.ClinicaDTO;
import com.Principal.AutenticacionPerezosa.model.dto.HistorialDTO;
import com.Principal.AutenticacionPerezosa.repository.crudRepository.ClinicaRepository;
import com.Principal.AutenticacionPerezosa.repository.crudRepository.HistorialClinicoRepository;
import com.Principal.AutenticacionPerezosa.repository.crudRepository.MascotasRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/protected/clinicas")
public class clinicasController {

    @Autowired
    private ClinicaRepository clinicaRepository;


    @PostMapping
    public ResponseEntity<?> crearClinica(@Valid @RequestBody clinicas detalleClinica) {
        //Mascotas nuevaMascota = mascotaRepository.save(mascotas);
        //return new ResponseEntity<>(nuevaMascota, HttpStatus.CREATED);


        Optional<clinicas> clinicaExistente = clinicaRepository.findByNombre(detalleClinica.getNombre());

        if(clinicaExistente.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: Ya tienes una clinica registrada con el nombre: " + detalleClinica.getNombre());
        }

        clinicas nuevaClinica = clinicaRepository.save(detalleClinica);

        return new ResponseEntity<>(new ClinicaDTO(nuevaClinica), HttpStatus.CREATED);
    }

    @GetMapping("/{clinicaId}")
    public ResponseEntity<?> obtenerClinicaId(@PathVariable Long clinicaId) {
        clinicas clinicas = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ResourceNotFoundException("clinica no encontrado con ID: " + clinicaId));
        //return ResponseEntity.ok(new MascotaDTO(mascotas));
        //return new ResponseEntity.(new MascotaDTO(mascotas));
        return new ResponseEntity<>(new ClinicaDTO(clinicas),HttpStatus.OK);
    }

    @GetMapping("/clinicaAll")
    public ResponseEntity<?> obtenerClinicaAll(){
        List<clinicas> listaClinicas= clinicaRepository.findAll();

        if(listaClinicas.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontrarón clinicas registrados");
        }

        //return new ResponseEntity<>(new MascotaDTO(listaMascotas),HttpStatus.OK);

        //return ResponseEntity.status(HttpStatus.OK)
        //.body(listaMascotas);

        List<ClinicaDTO> listaClinicasDTO = listaClinicas.stream()
                .map(ClinicaDTO::new) //
                .toList();

        // Devolver la lista de DTOs
        return ResponseEntity.status(HttpStatus.OK).body(listaClinicasDTO);

    }

    @DeleteMapping("/eliminar/{clinicaId}")
    public ResponseEntity<String> eliminarClinicaId(@PathVariable Long clinicaId) {
        clinicas clinicas = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ResourceNotFoundException("Clinica no encontrada con ID: " + clinicaId));
        clinicaRepository.deleteById(clinicaId);
        return new ResponseEntity<>("Clinica eliminada correctamente", HttpStatus.OK);
    }

    @PutMapping("/actualizar/{clinicaId}")
    public ResponseEntity<?> actualizarClinicaId(@PathVariable Long clinicaId, @Valid @RequestBody clinicas detallesClinica) {

        /*
        Optional<clinicas> clinicaExistente = clinicaRepository.findByNombre(detallesClinica.getNombre());

        if(clinicaExistente.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: Ya tienes una clinica registrada con el nombre: " + detallesClinica.getNombre());
        }

         */


        clinicas clinicas = clinicaRepository.findById(clinicaId)
                .orElseThrow(() -> new ResourceNotFoundException("Clinica no encontrado con ID: " + clinicaId));

        clinicas.setNombre(detallesClinica.getNombre());
        clinicas.setDireccion(detallesClinica.getDireccion());
        clinicas.setTelefono(detallesClinica.getTelefono());
        clinicas.setHorario_cierre(detallesClinica.getHorario_cierre());
        clinicas.setHorario_cierre(detallesClinica.getHorario_cierre());

        //mascotas.setUserNo(nuevoUser); //Este cambio del id, unicamente le aparecera al administrador

        // Guardar los cambios
        clinicas clinicaActualizado = clinicaRepository.save(clinicas);

        return new ResponseEntity<>(new ClinicaDTO(clinicaActualizado),HttpStatus.OK);

    }
}
