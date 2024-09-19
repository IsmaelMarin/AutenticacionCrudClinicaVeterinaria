package com.Principal.AutenticacionPerezosa.Service;

import com.Principal.AutenticacionPerezosa.model.Autenticacion;
import com.Principal.AutenticacionPerezosa.model.User;
import com.Principal.AutenticacionPerezosa.repository.AutenticacionRepository;
import com.Principal.AutenticacionPerezosa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService {

    @Autowired
    private AutenticacionRepository autenticacionRepository;

    @Autowired
    private UserRepository userRepository;


    // Cambia de BCryptPasswordEncoder a PasswordEncoder
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
    private final PasswordEncoder passwordEncoder;

    public AutenticacionService(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }

     */

    public void guardarAutenticacion(Autenticacion autenticacion) {
        autenticacionRepository.save(autenticacion);
    }

    //Verificar si la contraseña proporcionada por el usuario es la misma que la almacenada en la bd
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Error: Usuario no encontrado"));
    }

}
