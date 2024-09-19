package com.Principal.AutenticacionPerezosa.security;

import com.Principal.AutenticacionPerezosa.Service.AutenticacionService;
import com.Principal.AutenticacionPerezosa.model.Autenticacion;
import com.Principal.AutenticacionPerezosa.model.Role;
import com.Principal.AutenticacionPerezosa.model.RoleName;
import com.Principal.AutenticacionPerezosa.model.User;
import com.Principal.AutenticacionPerezosa.model.dto.UserAutenticacionDTO;
import com.Principal.AutenticacionPerezosa.model.dto.UserResponseDTO;
import com.Principal.AutenticacionPerezosa.model.dto.UserRoleDTO;
import com.Principal.AutenticacionPerezosa.repository.AutenticacionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    //Utilización de beans

    private final AuthenticationManager authenticationManager;
    private  final JwtTokenProvider jwtTokenProvider;
    private final AutenticacionService autenticacionService;

    @Autowired
    private AutenticacionRepository autenticacionRepository;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, AutenticacionService autenticacionService){
        this.authenticationManager=authenticationManager;
        this.jwtTokenProvider=jwtTokenProvider;
        this.autenticacionService=autenticacionService;
        setFilterProcessesUrl("/api/auth/login");
    }

    //@Autowired
    //private AutenticacionService autenticacionService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Map<String, String> credentials = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            String username = credentials.get("username");
            String password = credentials.get("password");

            // Validar si el usuario está registrado
            //User user = autenticacionService.loadUserByUsername(username);

            // Validar si el usuario está registrado




            User user;
            try {
                user = autenticacionService.loadUserByUsername(username);
            } catch (RuntimeException e) {
                // Manejar el caso en el que el usuario no es encontrado
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Error: Usuario no encontrado");
                return null;  // Termina el proceso de autenticación aquí
            }




            /*
            // Verificar si la contraseña es incorrecta
            if (!autenticacionService.checkPassword(password, user.getPassword())) {
                throw new BadCredentialsException("Error: Contraseña incorrecta");
            }

             */

            // Verificar si la contraseña es incorrecta
            if (!autenticacionService.checkPassword(password, user.getPassword())) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Error: Contraseña incorrecta");
                return null; // Terminar el proceso aquí para las credenciales incorrectas
            }

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    /*
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Map<String, String> credentials = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.get("username"),
                            credentials.get("password")
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

     */


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        // Generar accessToken
        String token = jwtTokenProvider.generateToken(authResult);
        //System.out.println(token);
        // Generar refreshToken
        String refreshToken = jwtTokenProvider.generateRefreshToken(authResult);

        //System.out.println(refreshToken);

        // Fechas de creación y expiración del accessToken
        Date fechaCreacion = new Date();
        Date fechaExpiracion = jwtTokenProvider.getTokenExpirationDate(token);

        // Tiempo de expiración del refreshToken
        Date refreshTokenExpiracion = jwtTokenProvider.getRefreshTokenExpirationDate(refreshToken);
        //System.out.println(fechaExpiracion);
        // Obtener detalles del usuario autenticado
        User user = (User) authResult.getPrincipal();  // Asume que el principal es de tipo User

        //System.out.println(user);
        // Obtener los roles del usuario
        List<String> roles = new ArrayList<>();
        for (Role role : user.getRoleList()) {
            roles.add(role.getName().toString());
        }
        //System.out.println(roles);

        UserAutenticacionDTO userAutenticacionDTO = new UserAutenticacionDTO(user.getUsername(),roles, token, fechaCreacion, fechaExpiracion);
        //userDTO UserDTO = new userDTO(user.getUsername(),user.getRoles(), token, fechaCreacion, fechaExpiracion);
        //Long usuarioId = user.getId();  // Aquí obtenemos el ID del usuario autenticado
        // Suponiendo que ya tienes una instancia de User
        //User user = userRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("User not found"));

        //System.out.println(user);
        // Crear objeto Autenticacion (sin guardar el refreshToken en la BD)
        Autenticacion autenticacion = new Autenticacion(user, token, fechaCreacion, fechaExpiracion);
        System.out.println(autenticacion);
        autenticacionService.guardarAutenticacion(autenticacion);

        // Crear el DTO de respuesta
        UserResponseDTO userResponseDTO = new UserResponseDTO(userAutenticacionDTO,refreshToken,refreshTokenExpiracion);

        //System.out.println(refreshTokenExpiracion);
        // Responder con el objeto Autenticacion, el refreshToken y su tiempo de expiración

        String jsonResponse = new ObjectMapper().writeValueAsString(new HashMap<String, Object>() {{
            put("Login",userResponseDTO);
            // put("autenticacion", authResponseDTO);  // Objeto autenticación guardado en la BD
            //put("refreshToken", refreshToken);    // El refreshToken generado
            //put("refreshTokenExpiracion", refreshTokenExpiracion);  // Fecha de expiración del refreshToken
        }});
        System.out.println(jsonResponse); // Imprime el JSON para depuración
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }


}
