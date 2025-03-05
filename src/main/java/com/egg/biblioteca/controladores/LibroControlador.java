package com.egg.biblioteca.controladores;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;
import com.egg.biblioteca.servicios.EditorialServicio;
import com.egg.biblioteca.servicios.LibroServicio;

@Controller // Esta clase es un Controlador
@RequestMapping("/libro") // Recibira todas las solicitudes enviadas a "/libro"
public class LibroControlador {
    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar") // GET /libro/registrar
    public String registrar(ModelMap model) {
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        model.addAttribute("autores", autores);
        model.addAttribute("editoriales", editoriales);
        return "libro_form.html";
    }

    @PostMapping("/registro") // POST /libro/registro
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares, @RequestParam UUID idAutor,
            @RequestParam UUID idEditorial, ModelMap model) {
        try {
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            model.addAttribute("exito", "¡Se ha añadido un Libro a la biblioteca!");
        } catch (MiException me) {
            model.addAttribute("error", me.getMessage());
            Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE, null, me);
            return "libro_form.html";
        }
        return "index.html";
    }
}
