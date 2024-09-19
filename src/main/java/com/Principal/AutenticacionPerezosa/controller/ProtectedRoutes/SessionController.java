package com.Principal.AutenticacionPerezosa.controller.ProtectedRoutes;

import com.Principal.AutenticacionPerezosa.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/session")
public class SessionController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;  // Inyecta JwtTokenProvider

    @PostMapping("/continue")
    public ResponseEntity<Map<String, String>> continueSession(HttpServletRequest request) {
        // Leer el token desde el encabezado
        String refreshToken = request.getHeader("Authorization");

        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);  // Eliminar "Bearer " del inicio

            if (jwtTokenProvider.validateToken(refreshToken)) {
                String username = jwtTokenProvider.getUsernameFromToken(refreshToken);

                // Obtener autenticación y generar un nuevo token de acceso
                Authentication auth = jwtTokenProvider.getAuthentication(refreshToken);
                String newToken = jwtTokenProvider.generateToken(auth);

                Map<String, String> response = new HashMap<>();
                response.put("message", "Sesión renovada.");
                response.put("access_token", newToken);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Refresh token inválido o expirado."));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Token no proporcionado en los encabezados."));
        }
    }

    //Te falta este
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        // Aquí podrías implementar la lógica para invalidar el token o limpiar la sesión
        return ResponseEntity.ok(Map.of("message", "Sesión cerrada con éxito."));
    }







}
