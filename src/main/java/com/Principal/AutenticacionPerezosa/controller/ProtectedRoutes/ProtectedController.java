package com.Principal.AutenticacionPerezosa.controller.ProtectedRoutes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/protected")
public class ProtectedController {

    @GetMapping("/data")
    public Map<String,String> getProtectedData(){
        Map<String,String> response = new HashMap<>();
        response.put("message","Este es un recurso protegido. Solo accesible con un token válido.");
        return response;


    }

}
