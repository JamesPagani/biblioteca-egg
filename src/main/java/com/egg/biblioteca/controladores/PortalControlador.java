package com.egg.biblioteca.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.UsuarioServicio;

@Controller // Esta clase es un Controlador
@RequestMapping("/") // Recibira todas las solicitudes hacia root
public class PortalControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/") // GET /
    public String index() {
        return "index.html";
    }

    @GetMapping("/registrar") // GET /registrar
    public String registrar() {
        return "registro.html";
    }

    @GetMapping("/login") // GET /login
    public String login() {
        return "login.html";
    }

    @PostMapping("/registro") // POST /registro
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap modelo) {
        try {
            usuarioServicio.crearUsuario(nombre, email, password, password2);
            modelo.put("exito", "¡Usuario creado con exito!");
            return "index.html";
        } catch (MiException me) {
            modelo.put("error", me.getMessage());
            return "registro.html";
        }
    }
}
