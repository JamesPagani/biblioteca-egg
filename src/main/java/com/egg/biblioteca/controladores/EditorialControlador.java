package com.egg.biblioteca.controladores;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.EditorialServicio;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar") // GET /editorial/registrar
    public String registrar() {
        return "editorial_form.html";
    }

    @GetMapping("/lista") // GET /editorial/listar
    public String listar(ModelMap model) {
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        model.addAttribute("editoriales", editoriales);
        return "editorial_list.html";
    }

    @GetMapping("/modificar/{id}") // GET /editorial/modificar/{id}
    public String modificar(@PathVariable UUID id, ModelMap modelo) {
        modelo.addAttribute("editorial", editorialServicio.obtenerEditorial(id));
        return "editorial_modificar.html";
    }

    @PostMapping("/registro") // POST /editorial/registro
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

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable UUID id, String nombre, ModelMap modelo) {
        try {
            editorialServicio.modificarEditorial(nombre, id);
            modelo.put("exito", "¡Editorial modificada con exito!");
            return "redirect:../lista";
        } catch (MiException me) {
            modelo.put("error", me.getMessage());
            return "editorial_modificar.html";
        }
    }
}
