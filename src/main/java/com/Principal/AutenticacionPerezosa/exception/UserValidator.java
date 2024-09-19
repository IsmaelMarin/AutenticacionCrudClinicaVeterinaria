package com.Principal.AutenticacionPerezosa.exception;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserValidator {


    //Cuando el usuario se registre se utilizara un Map
    public static List<String> validateUserMap(Map<String, Object> userMap) {
        List<String> errors = new ArrayList<>();

        // Validar que el campo "username" exista y no esté vacío
        if (!userMap.containsKey("username") || userMap.get("username") == null || userMap.get("username").toString().trim().isEmpty()) {
            errors.add("Error: El nombre de usuario no puede estar vacío");
        }

        // Validar que el campo "password" exista y no esté vacío
        if (!userMap.containsKey("password") || userMap.get("password") == null || userMap.get("password").toString().trim().isEmpty()) {
            errors.add("Error: La contraseña no puede estar vacía");
        }

        // Validar que el campo "roleList" exista, no esté vacío y sea una lista válida
        if (!userMap.containsKey("roleList") || userMap.get("roleList") == null || !(userMap.get("roleList") instanceof List) || ((List<?>) userMap.get("roleList")).isEmpty()) {
            errors.add("Error: Debe proporcionar al menos un rol válido");
        }


        // Validar que el campo "email" exista y tenga un formato de email válido
        if (!userMap.containsKey("email") || userMap.get("email") == null || userMap.get("email").toString().trim().isEmpty()) {
            errors.add("Error: El email no puede estar vacío");
        } else {
            String email = userMap.get("email").toString().trim();
            if (!email.matches("^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$")) { // Regex para validación de email
                errors.add("Error: El email no tiene un formato válido");
            }
        }

        // Validar que el campo "direccion" exista y no esté vacío
        if (!userMap.containsKey("direccion") || userMap.get("direccion") == null || userMap.get("direccion").toString().trim().isEmpty()) {
            errors.add("Error: La dirección no puede estar vacía");
        }

        // Validar que el campo "telefono" exista y tenga un formato válido (ejemplo simple)
        if (!userMap.containsKey("telefono") || userMap.get("telefono") == null || userMap.get("telefono").toString().trim().isEmpty()) {
            errors.add("Error: El teléfono no puede estar vacío");
        } else {
            String telefono = userMap.get("telefono").toString().trim();
            if (!telefono.matches("^\\d{9}$")) { // Regex simple para validar un número de teléfono de 10 dígitos
                errors.add("Error: El teléfono debe contener 9 dígitos");
            }
        }


        return errors;
    }


    /*
    List<String> errors = new ArrayList<>();

    public static ResponseEntity<String> validateUserMap(Map<String, Object> userMap) {
        // Validar que el campo "username" exista y no esté vacío
        if (!userMap.containsKey("username") || userMap.get("username") == null || userMap.get("username").toString().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: El nombre de usuario no puede estar vacío");
        }

        // Validar que el campo "password" exista y no esté vacío
        if (!userMap.containsKey("password") || userMap.get("password") == null || userMap.get("password").toString().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: La contraseña no puede estar vacía");
        }

        // Validar que el campo "roleList" exista, no esté vacío y sea una lista válida
        if (!userMap.containsKey("roleList") || userMap.get("roleList") == null || !(userMap.get("roleList") instanceof List) || ((List<?>) userMap.get("roleList")).isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Debe proporcionar al menos un rol válido");
        }

        // Si pasa todas las validaciones, retornar null (sin error)
        return null;
    }

     */

}
