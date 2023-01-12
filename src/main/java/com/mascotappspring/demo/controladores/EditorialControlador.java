/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascotappspring.demo.controladores;

import com.mascotappspring.demo.entidades.Usuario;
import com.mascotappspring.demo.entidades.Editorial;
import com.mascotappspring.demo.excepciones.ErrorServicio;
import com.mascotappspring.demo.repositorios.EditorialRepositorio;
import com.mascotappspring.demo.servicios.UsuarioServicio;
import com.mascotappspring.demo.servicios.EditorialServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author Gonzalo
 */
@Controller
@RequestMapping("/editoriales")
public class EditorialControlador {

    
    @Autowired
    private UsuarioServicio usuarioServ;    
    @Autowired
    private EditorialRepositorio editorialRepo;
    @Autowired
    private EditorialServicio editorialServ;

    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR', 'ROLE_USUARIO')")
    @GetMapping("/editorial")
    public String editoriales(HttpSession session, @RequestParam String id, ModelMap modelo) throws ErrorServicio {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        
        
        return "editoriales.html";
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR', 'ROLE_USUARIO')")
    @PostMapping("/proceso-buscar")
    public String buscar(HttpSession session, @RequestParam String id, @RequestParam String qeditorial, ModelMap modelo) throws ErrorServicio{
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        
        
        Editorial editorial= null;
        try {
        editorial = editorialServ.consultaEditorialNomCompl(qeditorial);
        modelo.put("editorial", editorial);
        return "editorial.html";
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "editoriales.html";
        }
    }
   
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @GetMapping("/ingresar")
    public String ingresar(HttpSession session, @RequestParam String id, ModelMap modelo){
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        return "editorial-ingresar.html";
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @PostMapping("/proceso-ingresar")
    public String procesoIngresar(HttpSession session, @RequestParam String id, @RequestParam String nombre, ModelMap modelo) throws ErrorServicio {
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null || !login.getId().equals(id)) {
                return "redirect:/login";
            }
            
        String role = login.getRol().toString();
        modelo.put("role", role);   
    try {
            editorialServ.crearEditorial(nombre);
            modelo.put("tit", "Operación Exitosa");
            modelo.put("subTit", "La Editorial fue ingresada a la base de datos correctamente.");
            return "succes.html";
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("Nombre", nombre);
            return "editorial-ingresar.html";
        }
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR', 'ROLE_USUARIO')")
    @GetMapping("/listar-activas")
    public String ListarActiva(HttpSession session, @RequestParam String id, ModelMap modelo) throws ErrorServicio {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        
        
        List<Editorial> editoriales = editorialRepo.listarEditorialActiva();
        modelo.put("editoriales", editoriales);
        return "editoriales-lista-activas.html";
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @GetMapping("/listar-todas")
    public String ListarTodas(HttpSession session, @RequestParam String id, ModelMap modelo) throws ErrorServicio {
        List<Editorial> editoriales = editorialRepo.listarEditorialCompleta();
        modelo.put("editoriales", editoriales);
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        return "editoriales-lista-completa.html";
    }
    
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @GetMapping("/modificar")
    public String modificar(HttpSession session, @RequestParam String id, @RequestParam String ediId,  ModelMap modelo) throws ErrorServicio{
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        
        String role = login.getRol().toString();
        modelo.put("role", role);        
        Editorial editorial = editorialServ.consultaEditorialId(ediId);
        modelo.put("editorial", editorial);
        return "editorial-actualizar.html";
    }

    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @PostMapping("/proceso-modificar")
    public String procesoModificar(ModelMap modelo, HttpSession session, @RequestParam String id, @RequestParam String ediId, @RequestParam String nombre) throws ErrorServicio {
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null || !login.getId().equals(id)) {
                return "redirect:/login";
            }
            
        String role = login.getRol().toString();
        modelo.put("role", role);
        try {
            editorialServ.modificarEditorial(ediId, nombre);
            //session.setAttribute("usuariosession", usuario);
            modelo.put("tit", "Operación Exitosa");
            modelo.put("subTit", "La información fue modificada correctamente.");
            return "succes.html";
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "editorial-actualizar.html";
        }
    }

    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @PostMapping("/proceso-baja")
    public String procesoBaja(ModelMap modelo, HttpSession session, @RequestParam String id, @RequestParam String ediId) throws ErrorServicio {
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null || !login.getId().equals(id)) {
                return "redirect:/login";
            }
            
        String role = login.getRol().toString();
        modelo.put("role", role);
        try {
            editorialServ.bajaEditorial(ediId);
            modelo.put("tit", "Operación Exitosa");
            modelo.put("subTit", "La información fue modificada correctamente.");
            return "succes.html";
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "editoriales.html";
        }
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @PostMapping("/proceso-alta")
    public String procesoAlta(ModelMap modelo, HttpSession session, @RequestParam String id, @RequestParam String ediId) throws ErrorServicio {
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null || !login.getId().equals(id)) {
                return "redirect:/login";
            }
            
        String role = login.getRol().toString();
        modelo.put("role", role);
        try {
            editorialServ.altaEditorial(ediId);
            modelo.put("tit", "Operación Exitosa");
            modelo.put("subTit", "La información fue modificada correctamente.");
            return "succes.html";
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "editoriales.html";
        }
    }
}