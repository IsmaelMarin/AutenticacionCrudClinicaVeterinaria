package com.Principal.AutenticacionPerezosa.controller.unauthenticatedRoutes;

import com.Principal.AutenticacionPerezosa.Service.UserService;
import com.Principal.AutenticacionPerezosa.exception.ResourceNotFoundException;
import com.Principal.AutenticacionPerezosa.exception.UserValidator;
import com.Principal.AutenticacionPerezosa.model.Autenticacion;
import com.Principal.AutenticacionPerezosa.model.Role;
import com.Principal.AutenticacionPerezosa.model.RoleName;
import com.Principal.AutenticacionPerezosa.model.User;
import com.Principal.AutenticacionPerezosa.model.crud.Mascotas;
import com.Principal.AutenticacionPerezosa.model.dto.MascotaDTO;
import com.Principal.AutenticacionPerezosa.model.dto.UserAutenticacionDTO;
import com.Principal.AutenticacionPerezosa.model.dto.UserRoleDTO;
import com.Principal.AutenticacionPerezosa.repository.AutenticacionRepository;
import com.Principal.AutenticacionPerezosa.repository.RoleRepository;
import com.Principal.AutenticacionPerezosa.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AutenticacionRepository autenticacionRepository;

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService=userService;
        this.passwordEncoder=passwordEncoder;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> userMap) {


        // Validar el mapa de usuario usando la clase UserValidator
        List<String> validationErrors = UserValidator.validateUserMap(userMap);

        // Si hay errores, devolverlos como respuesta
        if (!validationErrors.isEmpty()) {
            return ResponseEntity.badRequest().body(validationErrors);
        }

        /*
        // Validar el mapa de usuario usando la clase UserValidator
        ResponseEntity<String> validationResponse = UserValidator.validateUserMap(userMap);
        if (validationResponse != null) {
            return validationResponse; // Retornar el error si existe
        }

         */


        /*
        // Crear el usuario y establecer el nombre de usuario y contraseña desde el Map
        User user = new User();
        String username = (String) userMap.get("username");
        String password = (String) userMap.get("password");

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); //Encriptar la contraseña

         */


        // Establecer valores desde el Map
        User user = new User();
        String username = (String) userMap.get("username");
        String password = (String) userMap.get("password");
        String email = (String) userMap.get("email");
        String direccion = (String) userMap.get("direccion");
        String telefono = (String) userMap.get("telefono");
        // Obtener la fecha como String desde el Map
        String fechaCreacionStr = (String) userMap.get("fecha_creacion");


        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); //Encriptar la contraseña
        user.setEmail(email);
        user.setDireccion(direccion);
        user.setTelefono(telefono);

        // Procesar la lista de roles
        List<Role> roles = new ArrayList<>();
        List<Object> roleList = (List<Object>) userMap.get("roleList"); // Extraer lista de roles

        // Verificar si el nombre de usuario ya existe
        if (userService.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(new UserRoleDTO(user, "Error: El nombre de usuario ya está en uso."));
        }


        for (Object roleNameObj : roleList) {
            String roleNameStr = roleNameObj.toString(); // Convertir Object a String


            if (!roleNameStr.equals("ROLE_USER") && !roleNameStr.equals("ROLE_ADMIN")) {
                return  ResponseEntity.badRequest().body("ROL INCORRECTO, SOLO PUEDE INGRESAR LOS ROLES: ROLER_USER, ROLE_ADMIN");
            }

            RoleName roleName = RoleName.valueOf(roleNameStr); // Convertir String a RoleName (enum)
            //System.out.println(roleName);
            // Buscar el rol en la base de datos
            // Buscar el rol en la base de datos
            Optional<Role> roleOptional = roleRepository.findByName(roleName);

    /*
    Role role2 = roleRepository.findByName(roleName)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

    */
            Role role2 = null;  //Variable de instancia
            if(roleOptional.isPresent()){
                role2 = roleOptional.get();

            }else{
                // Si el rol no existe, lo creas y lo guardas en la base de datos
                role2 = new Role();
                role2.setName(roleName);  // Establecer el nombre del rol (enum)
                roleRepository.save(role2);  // Guardar el nuevo rol en la base de datos

            }

            roles.add(role2);
            //roleRepository.save(role2);



        }
        user.setRoleList(roles);

        // Guardar el nuevo usuario
        userRepository.save(user);

        // Retornar respuesta exitosa
        return ResponseEntity.ok(new UserRoleDTO<>(user, "usuario registrado exitosamente"));
    }


    @GetMapping("/{usuarioId}")
    public ResponseEntity<User> obtenerUsuarioId(@PathVariable Long usuarioId) {
        User userNo = userRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId));
        return ResponseEntity.ok(userNo);
    }
    @GetMapping("/usuarioAll")
    public ResponseEntity<?> obtenerUsuarioAll(){
        List<User> listaUser= userRepository.findAll();

        if(listaUser.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontrarón usuarios registrados");
        }

        //return new ResponseEntity<>(new MascotaDTO(listaMascotas),HttpStatus.OK);

        //return ResponseEntity.status(HttpStatus.OK)
        //.body(listaMascotas);

        return ResponseEntity.status(HttpStatus.OK).body(listaUser);

    }



    @DeleteMapping("/eliminar/{usuarioId}")
    public ResponseEntity<String> eliminarUsuarioId(@PathVariable Long usuarioId) {
        User userNo = userRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId));
        userRepository.deleteById(usuarioId);
        return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
    }

    @PutMapping("/actualizar/{usuarioId}")
    public ResponseEntity<?> actualizarUsuarioId(@PathVariable Long usuarioId ,@RequestBody Map<String, Object> userMap){


        List<String> validationErrors = UserValidator.validateUserMap(userMap);

        // Si hay errores, devolverlos como respuesta
        if (!validationErrors.isEmpty()) {
            return ResponseEntity.badRequest().body(validationErrors);
        }

        User usuarioExistente = userRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Establecer valores desde el Map
        User user = new User();
        String username = (String) userMap.get("username");
        String password = (String) userMap.get("password");
        String email = (String) userMap.get("email");
        String direccion = (String) userMap.get("direccion");
        String telefono = (String) userMap.get("telefono");
        // Obtener la fecha como String desde el Map
        String fechaCreacionStr = (String) userMap.get("fecha_creacion");


        Optional<User> nombreExistente = userRepository.findByUsername(username);


        if(nombreExistente.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: Ya tienes un usuario registrado con ese mismo nombre: " + username);
        }


        usuarioExistente.setUsername(username);
        usuarioExistente.setPassword(passwordEncoder.encode(password)); //Encriptar la contraseña
        usuarioExistente.setEmail(email);
        usuarioExistente.setDireccion(direccion);
        usuarioExistente.setTelefono(telefono);



        // No modificar la fecha de creación
        if (fechaCreacionStr == null) {
            usuarioExistente.setFecha_creacion(usuarioExistente.getFecha_creacion());
        }



        // Procesar la lista de roles
        List<Role> roles = new ArrayList<>();
        List<Object> roleList = (List<Object>) userMap.get("roleList"); // Extraer lista de roles

        /*
        // Verificar si el nombre de usuario ya existe
        if (userService.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(new UserRoleDTO(user, "Error: El nombre de usuario ya está en uso."));
        }

         */



        for (Object roleNameObj : roleList) {
            String roleNameStr = roleNameObj.toString(); // Convertir Object a String


            if (!roleNameStr.equals("ROLE_USER") && !roleNameStr.equals("ROLE_ADMIN")) {
                return  ResponseEntity.badRequest().body("ROL INCORRECTO, SOLO PUEDE INGRESAR LOS ROLES: ROLER_USER, ROLE_ADMIN");
            }

            RoleName roleName = RoleName.valueOf(roleNameStr); // Convertir String a RoleName (enum)
            //System.out.println(roleName);
            // Buscar el rol en la base de datos
            // Buscar el rol en la base de datos
            Optional<Role> roleOptional = roleRepository.findByName(roleName);

    /*
    Role role2 = roleRepository.findByName(roleName)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

    */
            Role role2 = null;  //Variable de instancia
            if(roleOptional.isPresent()){
                role2 = roleOptional.get();

            }else{
                // Si el rol no existe, lo creas y lo guardas en la base de datos
                role2 = new Role();
                role2.setName(roleName);  // Establecer el nombre del rol (enum)
                roleRepository.save(role2);  // Guardar el nuevo rol en la base de datos

            }

            roles.add(role2);
            //roleRepository.save(role2);



        }
        usuarioExistente.setRoleList(roles);

        // Guardar el usuario Actualizado
        userRepository.save(usuarioExistente);

        // Retornar respuesta exitosa
        return ResponseEntity.ok(new UserRoleDTO<>(usuarioExistente, "usuario actualizado exitosamente"));

    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        // El proceso de login se maneja en el filtro JwtAuthenticationFilter
        return credentials;
    }



}
