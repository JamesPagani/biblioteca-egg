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

import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.enums.Rol;
import com.egg.biblioteca.servicios.UsuarioServicio;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "panel.html";
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "user_list.html";
    }

    @GetMapping("/usuarios/{id}")
    public String modificarUsuario(@PathVariable UUID id, ModelMap modelo) {
        modelo.addAttribute("usuario", usuarioServicio.obtenerUsuario(id));
        return "user_modificar.html";
    }
    
    @PostMapping("/usuarios/{id}")
    public String modificarUsuario(@PathVariable UUID id, @RequestParam String nombre, String email, Rol rol, ModelMap modelo) {
        try {
            usuarioServicio.modificarUsuario(id, nombre, email, rol);
            modelo.put("exito", "Se ha actualizado la informacion del usuario.");
            return "redirect:/admin/usuarios";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            Logger.getLogger(AdminControlador.class.getName()).log(Level.SEVERE, null, e);
            return "user_modificar.html";
        }
    }
}
