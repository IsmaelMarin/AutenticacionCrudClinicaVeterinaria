package com.Principal.AutenticacionPerezosa.controller.ProtectedRoutes.crud;

import com.Principal.AutenticacionPerezosa.exception.ResourceNotFoundException;
import com.Principal.AutenticacionPerezosa.model.crud.clinicas;
import com.Principal.AutenticacionPerezosa.model.crud.servicios;
import com.Principal.AutenticacionPerezosa.model.dto.ServicioDTO;
import com.Principal.AutenticacionPerezosa.repository.crudRepository.ServiciosRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/protected/servicios")
public class serviciosController {

    @Autowired
    private ServiciosRepository serviciosRepository;

    // Crear un nuevo servicio
    @PostMapping
    public ResponseEntity<?> crearServicio(@Valid @RequestBody servicios nuevoServicio) {


        Optional<servicios> servicioExistente = serviciosRepository.findByNombre(nuevoServicio.getNombre());

        if(servicioExistente.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: Ya tienes un servicio registrado con el nombre: " + nuevoServicio.getNombre());
        }


        servicios servicioGuardado = serviciosRepository.save(nuevoServicio);
        return new ResponseEntity<>(new ServicioDTO(servicioGuardado), HttpStatus.CREATED);
    }

    // Obtener un servicio por ID
    @GetMapping("/{servicioId}")
    public ResponseEntity<?> obtenerServicioId(@PathVariable Long servicioId) {
        servicios servicio = serviciosRepository.findById(servicioId)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado con ID: " + servicioId));
        return new ResponseEntity<>(new ServicioDTO(servicio), HttpStatus.OK);
    }

    // Obtener todos los servicios
    @GetMapping("/serviciosAll")
    public ResponseEntity<?> obtenerServiciosAll() {
        List<servicios> listaServicios = serviciosRepository.findAll();

        if (listaServicios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron servicios registrados");
        }

        List<ServicioDTO> listaServicioDTO = listaServicios.stream()
                .map(ServicioDTO::new)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(listaServicioDTO);
    }

    // Eliminar un servicio por ID
    @DeleteMapping("/eliminar/{servicioId}")
    public ResponseEntity<String> eliminarServicioId(@PathVariable Long servicioId) {
        servicios servicio = serviciosRepository.findById(servicioId)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado con ID: " + servicioId));
        serviciosRepository.deleteById(servicioId);
        return new ResponseEntity<>("Servicio eliminado correctamente", HttpStatus.OK);
    }

    // Actualizar un servicio por ID
    @PutMapping("/actualizar/{servicioId}")
    public ResponseEntity<?> actualizarServicioId(@PathVariable Long servicioId, @Valid @RequestBody servicios detallesServicio) {


        /*
        Optional<servicios> servicioExistente = serviciosRepository.findByNombre(detallesServicio.getNombre());

        if(servicioExistente.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: Ya tienes un servicio registrado con el nombre: " + detallesServicio.getNombre());
        }

         */


        servicios servicio = serviciosRepository.findById(servicioId)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado con ID: " + servicioId));

        // Actualizar los campos necesarios
        servicio.setNombre(detallesServicio.getNombre());
        servicio.setDescripcion(detallesServicio.getDescripcion());
        servicio.setPrecio(detallesServicio.getPrecio());

        // Guardar los cambios
        servicios servicioActualizado = serviciosRepository.save(servicio);
        return new ResponseEntity<>(new ServicioDTO(servicioActualizado), HttpStatus.OK);
    }
}
