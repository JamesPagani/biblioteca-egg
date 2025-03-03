package com.egg.biblioteca.controladores;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;

@Controller // Esta clase es un Controlador
@RequestMapping("/autor") // Recibira todas las solicitudes hacia "/autor"
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registrar") // GET /autor/registrar
    public String registrar() {
        return "autor_form.html";
    }

    @PostMapping("/registro") // POST /autor/registro
    public String registro(@RequestParam String nombre) {
        try {
            autorServicio.crearAutor(nombre);
        } catch (MiException me) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, me);
            return "autor_form.html";
        }
        return "index.html";
    }
}
