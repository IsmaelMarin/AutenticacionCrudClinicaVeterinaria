package com.Principal.AutenticacionPerezosa.controller.ProtectedRoutes.crud;

import com.Principal.AutenticacionPerezosa.exception.ResourceNotFoundException;
import com.Principal.AutenticacionPerezosa.model.Role;
import com.Principal.AutenticacionPerezosa.model.RoleName;
import com.Principal.AutenticacionPerezosa.model.User;
import com.Principal.AutenticacionPerezosa.model.crud.Mascotas;
import com.Principal.AutenticacionPerezosa.model.dto.MascotaDTO;
import com.Principal.AutenticacionPerezosa.model.dto.RoleDTO;
import com.Principal.AutenticacionPerezosa.repository.RoleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/protected/roles")
public class rolesController {

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping
    public ResponseEntity<?> crearRole(@Valid @RequestBody RoleDTO roleDTO) {
        try {
            // Validar el nombre del rol
            String roleNameStr = roleDTO.getName().toUpperCase(); // Normaliza el valor a mayúsculas
            RoleName roleName;

            try {
                roleName = RoleName.valueOf(roleNameStr); // Verifica si el valor existe en el enum
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: Rol inválido");
            }

            Optional<Role> existingRole = roleRepository.findByName(roleName);

            if (existingRole.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: El rol ya existe");
            }

            // Crear nuevo rol
            Role newRole = new Role();
            newRole.setName(roleName);
            roleRepository.save(newRole);

            return ResponseEntity.status(HttpStatus.CREATED).body(newRole);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ERROR");
        }
    }

    @GetMapping("/{rolesId}")
    public ResponseEntity<?> obtenerRolesId(@PathVariable Long rolesId) {
        Role roles = roleRepository.findById(rolesId)
                .orElseThrow(() -> new ResourceNotFoundException("Role no encontrado con ID: " + rolesId));
        //return ResponseEntity.ok(new MascotaDTO(mascotas));
        //return new ResponseEntity.(new MascotaDTO(mascotas));
        return new ResponseEntity<>(roles,HttpStatus.OK);
        //return new ResponseEntity<>(new MascotaDTO(mascotas),HttpStatus.OK);
    }



    @GetMapping("/rolesAll")
    public ResponseEntity<?> obtenerRolesAll(){
        List<Role> listaRoles= roleRepository.findAll();

        if(listaRoles.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontrarón roles registradas");
        }

        //return new ResponseEntity<>(new MascotaDTO(listaMascotas),HttpStatus.OK);

        //return ResponseEntity.status(HttpStatus.OK)
        //.body(listaMascotas);

        return ResponseEntity.status(HttpStatus.OK).body(listaRoles);

    }
    /*

    @DeleteMapping("/eliminar/{roleId}")
    public ResponseEntity<String> eliminarMascotaId(@PathVariable Long roleId) {
        Role roles = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role no encontrado con ID: " + roleId));
        roleRepository.deleteById(roleId);
        return new ResponseEntity<>("Role eliminado correctamente", HttpStatus.OK);
    }

     */







}
