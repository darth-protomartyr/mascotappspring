/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascotappspring.demo.controladores;

import com.mascotappspring.demo.entidades.Usuario;
import com.mascotappspring.demo.entidades.Autor;
import com.mascotappspring.demo.entidades.Editorial;
import com.mascotappspring.demo.entidades.Libro;
import com.mascotappspring.demo.excepciones.ErrorServicio;
import com.mascotappspring.demo.repositorios.AutorRepositorio;
import com.mascotappspring.demo.repositorios.EditorialRepositorio;
import com.mascotappspring.demo.repositorios.LibroRepositorio;
import com.mascotappspring.demo.servicios.UsuarioServicio;
import com.mascotappspring.demo.servicios.LibroServicio;
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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Gonzalo
 */
@Controller
@RequestMapping("/libros")
public class LibroControlador {

    
    @Autowired
    private UsuarioServicio usuarioServ;    
    @Autowired
    private LibroRepositorio libroRepo;
    @Autowired
    private LibroServicio libroServ;
    @Autowired
    private AutorRepositorio autorRepo;
    @Autowired
    private EditorialRepositorio ediRepo;

    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR', 'ROLE_USUARIO')")
    @GetMapping("/libro")
    public String libros(HttpSession session, @RequestParam String id, ModelMap modelo) throws ErrorServicio {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        return "libros.html";
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR', 'ROLE_USUARIO')")
    @PostMapping("/proceso-buscar")
    public String buscar(HttpSession session, @RequestParam String id, @RequestParam String qlibro, ModelMap modelo) throws ErrorServicio{
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        Libro libro= null;
        try {            
            libro = libroServ.buscarLibroTitCompl(qlibro);
            modelo.put("libro", libro);
            return "libro.html";
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "libros.html";
        }
    }
    

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @GetMapping("/ingresar")
    public String ingresar(HttpSession session, @RequestParam String id, ModelMap modelo) {
        List<Autor> autores = autorRepo.findAll();
        List<Editorial> editoriales = ediRepo.findAll();
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        modelo.put("autores", autores);
        modelo.put("editoriales", editoriales);
        return "libro-ingresar.html";
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @PostMapping("/proceso-ingresar")
    public String procesoIngresar( ModelMap modelo, @RequestParam String id, HttpSession session, String titulo, Long isbn, Integer ejemplaresTotales, String autorId, String editorialId, MultipartFile archivo) throws ErrorServicio {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        List<Autor> autores = autorRepo.findAll();
        List<Editorial> editoriales = ediRepo.findAll();
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        try {
            libroServ.crearLibro(isbn, titulo, ejemplaresTotales, autorId, editorialId, archivo);
            modelo.put("tit", "Operación Exitosa");
            modelo.put("subTit", "El Libro fue ingresado a la base de datos correctamente.");
            return "succes.html";
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("titulo", titulo);
            modelo.put("isbn", isbn);
            modelo.put("autores", autores);
            modelo.put("editoriales", editoriales);
            modelo.put("autorId", autorId);
            modelo.put("editorialId", editorialId);
            modelo.put("archivo", archivo);
            return "libro-ingresar.html";
        }
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @GetMapping("/modificar")
    public String modificar(HttpSession session, @RequestParam String id, @RequestParam String libroId,  ModelMap modelo){
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        List<Autor> autores = autorRepo.findAll();
        List<Editorial> editoriales = ediRepo.findAll();
        Libro libro = libroRepo.getById(libroId);
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        modelo.put("libro", libro);
        modelo.put("autores", autores);
        modelo.put("editoriales", editoriales);
        return "libro-actualizar.html";
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @PostMapping("/proceso-modificar")
    public String procesoModificar(ModelMap modelo, HttpSession session, @RequestParam String id, @RequestParam String libroId, String titulo, Long isbn, Integer ejemplaresTotales, String autorId, String editorialId, MultipartFile archivo) throws ErrorServicio {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        List<Autor> autores = autorRepo.findAll();
        List<Editorial> editoriales = ediRepo.findAll();
        Libro libro = null;
        try {
            libro = libroServ.buscarLibroId(libroId);
            libroServ.modificar(libroId, isbn, titulo, ejemplaresTotales, autorId, editorialId, archivo);
            modelo.put("tit", "Operación Exitosa");
            modelo.put("subTit", "La información fue ingresada al base de datos correctamente.");
            return "succes.html";
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("libro", libro);
            modelo.put("autores", autores);
            modelo.put("editoriales", editoriales);
            modelo.put("autorId", autorId);
            modelo.put("editorialId", editorialId);
            return "libro-actualizar.html";
        }
    }

    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR', 'ROLE_USUARIO')")
    @GetMapping("/listar-activas")
    public String ListarActiva(HttpSession session, @RequestParam String id, ModelMap modelo) throws ErrorServicio {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        
        
        List<Libro> libros = libroServ.listarLibrosActivos();
        modelo.put("libros", libros);
        
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        return "libros-lista-activos.html";
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @GetMapping("/listar-todas")
    public String ListarTodas(HttpSession session, @RequestParam String id, ModelMap modelo) throws ErrorServicio {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        List<Libro> libros = libroRepo.listarLibrosCompleta();
        modelo.put("libros", libros);
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        return "libros-lista-completa.html";
    }
    
        
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @PostMapping("/proceso-baja")
    public String procesoBaja(ModelMap modelo, HttpSession session, @RequestParam String id, @RequestParam String libroId) throws ErrorServicio {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        
        
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        try {      
            libroServ.darBajaLibro(libroId);
            modelo.put("tit", "Operación Exitosa");
            modelo.put("subTit", "La información fue modificada correctamente.");
            return "succes.html";
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "libros.html";
        }
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @PostMapping("/proceso-alta")
    public String procesoAlta(ModelMap modelo, HttpSession session, @RequestParam String id, @RequestParam String libroId) throws ErrorServicio {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        
        
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        try {     
            libroServ.darAltaLibro(libroId);
            modelo.put("tit", "Operación Exitosa");
            modelo.put("subTit", "La información fue modificada correctamente.");
            return "succes.html";
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "libros.html";
        }
    }
}