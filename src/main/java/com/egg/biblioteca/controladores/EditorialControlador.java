package com.egg.biblioteca.controladores;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.EditorialServicio;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap model) {
        try {
            editorialServicio.crearEditorial(nombre);
            model.addAttribute("exito", "¡La Editorial se ha creado con exito!");
        } catch (MiException me) {
            model.addAttribute("error", "¡La Editorial debe tener un nombre!");
            Logger.getLogger(EditorialControlador.class.getName()).log(Level.SEVERE, null, me);
            return "editorial_form.html";
        }
        return "index.html";
    }
}
