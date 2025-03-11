package com.egg.biblioteca.controladores;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
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

    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/registrar") // GET /libro/registrar
    public String registrar(ModelMap model) {
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        model.addAttribute("autores", autores);
        model.addAttribute("editoriales", editoriales);
        return "libro_form.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/lista") // GET /libro/lista
    public String listar(ModelMap modelo) {
        List<Libro> libros = libroServicio.listarLibros();
        modelo.addAttribute("libros", libros);
        return "libro_list.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/modificar/{isbn}") // GET /modificar/{isbn}
    public String modificar(@PathVariable Long isbn, ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("libro", libroServicio.obtenerLibro(isbn));
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "libro_modificar.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, String titulo, Integer ejemplares, UUID idAutor, UUID idEditorial, ModelMap modelo) {
        try {
            libroServicio.modificarLibro(titulo, ejemplares, idAutor, idEditorial, isbn);
            modelo.put("exito", "¡Libro modificado con exito!");
            return "redirect:../lista";
        } catch (MiException me) {
            modelo.put("error", me.getMessage());
            return "libro_modificar.html";
        }
    }
}
