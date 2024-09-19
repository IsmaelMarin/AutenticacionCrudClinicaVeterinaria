package com.Principal.AutenticacionPerezosa.controller.ProtectedRoutes.crud;

import com.Principal.AutenticacionPerezosa.exception.ResourceNotFoundException;
import com.Principal.AutenticacionPerezosa.model.User;
import com.Principal.AutenticacionPerezosa.model.crud.Mascotas;
import com.Principal.AutenticacionPerezosa.model.dto.MascotaDTO;
import com.Principal.AutenticacionPerezosa.repository.UserRepository;
import com.Principal.AutenticacionPerezosa.repository.crudRepository.MascotasRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/protected/mascotas")
public class mascotasController {


    @Autowired
    private MascotasRepository mascotaRepository;
    @Autowired
    private UserRepository userNoRepository;


    @PostMapping
    public ResponseEntity<?> crearMacota(@Valid @RequestBody Mascotas mascotas) {
        //Mascotas nuevaMascota = mascotaRepository.save(mascotas);
        //return new ResponseEntity<>(nuevaMascota, HttpStatus.CREATED);

        Optional<Mascotas> mascotaExistente = mascotaRepository.findByNameAndUserNo_Id(mascotas.getName(),mascotas.getUserNo().getId());

        if(mascotaExistente.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: Ya tienes una mascota registrada con el nombre: " + mascotas.getName());
        }

        //Cargamos el objeto User cuando hay relaciones
        User userNo = userNoRepository.findById(mascotas.getUserNo().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + mascotas.getUserNo().getId()));

        //Asignamos el usuario a la mascota
        mascotas.setUserNo(userNo);

        Mascotas nuevaMascota = mascotaRepository.save(mascotas);

        return new ResponseEntity<>(new MascotaDTO(nuevaMascota),HttpStatus.CREATED);
    }

    @GetMapping("/{mascotaId}")
    public ResponseEntity<?> obtenerMascotaId(@PathVariable Long mascotaId) {
        Mascotas mascotas = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con ID: " + mascotaId));
        //return ResponseEntity.ok(new MascotaDTO(mascotas));
        //return new ResponseEntity.(new MascotaDTO(mascotas));
        return new ResponseEntity<>(new MascotaDTO(mascotas),HttpStatus.OK);
    }

    @GetMapping("/mascotaAll")
    public ResponseEntity<?> obtenerMascotaAll(){
        List<Mascotas> listaMascotas= mascotaRepository.findAll();

        if(listaMascotas.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontrarón mascotas registradas");
        }

        //return new ResponseEntity<>(new MascotaDTO(listaMascotas),HttpStatus.OK);

        //return ResponseEntity.status(HttpStatus.OK)
                //.body(listaMascotas);

        // Convertir la lista de Mascotas a una lista de MascotaDTO
        List<MascotaDTO> listaMascotaDTO = listaMascotas.stream()
                .map(MascotaDTO::new) // Convierte cada Mascota a un MascotaDTO
                .toList();

        // Devolver la lista de DTOs
        return ResponseEntity.status(HttpStatus.OK).body(listaMascotaDTO);

    }

    @DeleteMapping("/eliminar/{mascotaId}")
    public ResponseEntity<String> eliminarMascotaId(@PathVariable Long mascotaId) {
        Mascotas mascotas = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrado con ID: " + mascotaId));
        mascotaRepository.deleteById(mascotaId);
        return new ResponseEntity<>("Mascota eliminada correctamente", HttpStatus.OK);
    }

    @PutMapping("/actualizar/{mascotaId}")
    public ResponseEntity<?> actualizarMascotaId(@PathVariable Long mascotaId, @Valid @RequestBody Mascotas detallesMascota) {

        /*
        Optional<Mascotas> mascotaExistente = mascotaRepository.findByNameAndUserNo_Id(detallesMascota.getName(),detallesMascota.getUserNo().getId());


        if(mascotaExistente.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: Ya tienes una mascota registrada con el nombre: " + detallesMascota.getName());
        }
        */

        Mascotas mascotas = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrado con ID: " + mascotaId));

        // Buscar el nuevo usuario asociado
        User nuevoUser = userNoRepository.findById(detallesMascota.getUserNo().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + detallesMascota.getUserNo().getId()));


        //System.out.println(mascotas);
        // Actualizar los campos necesarios
        mascotas.setName(detallesMascota.getName());
        mascotas.setRaza(detallesMascota.getRaza());
        mascotas.setEdad(detallesMascota.getEdad());
        mascotas.setUserNo(nuevoUser); //Este cambio del id, unicamente le aparecera al administrador

        // Guardar los cambios
        Mascotas mascotaActualizado = mascotaRepository.save(mascotas);

        return new ResponseEntity<>(new MascotaDTO(mascotaActualizado),HttpStatus.OK);

    }

}
