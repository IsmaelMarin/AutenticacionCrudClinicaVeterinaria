package com.Principal.AutenticacionPerezosa.controller.ProtectedRoutes.crud;

import com.Principal.AutenticacionPerezosa.exception.ResourceNotFoundException;
import com.Principal.AutenticacionPerezosa.model.User;
import com.Principal.AutenticacionPerezosa.model.crud.Mascotas;
import com.Principal.AutenticacionPerezosa.model.crud.historial_clinico;
import com.Principal.AutenticacionPerezosa.model.dto.HistorialDTO;
import com.Principal.AutenticacionPerezosa.model.dto.MascotaDTO;
import com.Principal.AutenticacionPerezosa.repository.UserRepository;
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
@RequestMapping("/api/protected/historial")
public class historial_clinicoController {


    @Autowired
    private MascotasRepository mascotaRepository;
    @Autowired
    private HistorialClinicoRepository historialClinicoRepository;


    @PostMapping
    public ResponseEntity<?> crearHistorial(@Valid @RequestBody historial_clinico historial) {
        //Mascotas nuevaMascota = mascotaRepository.save(mascotas);
        //return new ResponseEntity<>(nuevaMascota, HttpStatus.CREATED);

        //Cargamos el objeto mascotas cuando hay relaciones
        Mascotas mascotasNo = mascotaRepository.findById(historial.getMascotas().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con ID: " + historial.getMascotas().getId()));

        historial.setMascotas(mascotasNo);

        historial_clinico nuevoHistorial = historialClinicoRepository.save(historial);

        return new ResponseEntity<>(new HistorialDTO(nuevoHistorial),HttpStatus.CREATED);
    }

    @GetMapping("/{historialId}")
    public ResponseEntity<?> obtenerHistorialId(@PathVariable Long historialId) {
        historial_clinico historial = historialClinicoRepository.findById(historialId)
                .orElseThrow(() -> new ResourceNotFoundException("Historial no encontrado con ID: " + historialId));
        //return ResponseEntity.ok(new MascotaDTO(mascotas));
        //return new ResponseEntity.(new MascotaDTO(mascotas));
        return new ResponseEntity<>(new HistorialDTO(historial),HttpStatus.OK);
    }

    @GetMapping("/historialAll")
    public ResponseEntity<?> obtenerHistorialAll(){
        List<historial_clinico> listaHistorial= historialClinicoRepository.findAll();

        if(listaHistorial.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontrarón historiales registrados");
        }

        //return new ResponseEntity<>(new MascotaDTO(listaMascotas),HttpStatus.OK);

        //return ResponseEntity.status(HttpStatus.OK)
        //.body(listaMascotas);

        List<HistorialDTO> listaHistorialDTO = listaHistorial.stream()
                .map(HistorialDTO::new) //
                .toList();

        // Devolver la lista de DTOs
        return ResponseEntity.status(HttpStatus.OK).body(listaHistorialDTO);

    }

    @DeleteMapping("/eliminar/{historialId}")
    public ResponseEntity<String> eliminarHistorialId(@PathVariable Long historialId) {
        historial_clinico historialClinico = historialClinicoRepository.findById(historialId)
                .orElseThrow(() -> new ResourceNotFoundException("Historial no encontrado con ID: " + historialId));
        historialClinicoRepository.deleteById(historialId);
        return new ResponseEntity<>("Historial eliminado correctamente", HttpStatus.OK);
    }

    @PutMapping("/actualizar/{historialId}")
    public ResponseEntity<?> actualizarHistorialId(@PathVariable Long historialId, @Valid @RequestBody historial_clinico detallesHistorial) {


        historial_clinico historial = historialClinicoRepository.findById(historialId)
                .orElseThrow(() -> new ResourceNotFoundException("Historial no encontrado con ID: " + historialId));

        //System.out.println(mascotas);
        // Actualizar los campos necesarios
        historial.setFecha(detallesHistorial.getFecha());
        historial.setDescripcion(detallesHistorial.getDescripcion());

        //mascotas.setUserNo(nuevoUser); //Este cambio del id, unicamente le aparecera al administrador

        // Guardar los cambios
        historial_clinico historialActualizado = historialClinicoRepository.save(historial);

        return new ResponseEntity<>(new HistorialDTO(historialActualizado),HttpStatus.OK);

    }
}
