package com.egg.biblioteca.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // Esta clase es un Controlador
@RequestMapping("/") // Recibira todas las solicitudes hacia root
public class PortalControlador {

    @GetMapping("/") // GET /
    public String index() {
        return "index.html";
    }
}
