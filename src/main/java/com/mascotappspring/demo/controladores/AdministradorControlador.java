/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascotappspring.demo.controladores;
import com.mascotappspring.demo.entidades.Usuario;
import com.mascotappspring.demo.enumeraciones.Genero;
import com.mascotappspring.demo.enumeraciones.Rol;
import com.mascotappspring.demo.excepciones.ErrorServicio;
import com.mascotappspring.demo.repositorios.UsuarioRepositorio;
import com.mascotappspring.demo.servicios.AdministradorServicio;
import com.mascotappspring.demo.servicios.UsuarioServicio;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
@RequestMapping("/administradores")
public class AdministradorControlador {

    
@Autowired
AdministradorServicio adminServ;
@Autowired
UsuarioRepositorio usuarioRepo;
@Autowired
UsuarioServicio usuarioServ;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @GetMapping("/administrador")
    public String usuarios(ModelMap modelo, HttpSession session, @RequestParam String id) throws ErrorServicio {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        List <Usuario> usuarios = usuarioRepo.findAll();
        List <Usuario> bajados = usuarioServ.listarBajados();
        
        modelo.put("bajados",bajados);
        modelo.put("usuarios",usuarios);
        String role = login.getRol().toString();
        modelo.put("role", role);
        return "administrador.html";
    }
 
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/finalizar-proceso-baja-cuenta")
    public String finalizarBajaCuenta(ModelMap modelo, HttpSession session, @RequestParam String id, @RequestParam String solicitId) throws ErrorServicio {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        String role = login.getRol().toString();
        modelo.put("role", role);
        modelo.addAttribute("perfil", login);
        adminServ.completarBajaDeUsuario(solicitId);
        modelo.put("tit", "Operación Exitosa");
        modelo.put("subTit", "La baja del usuario es efectiva.");
        return "succes.html";
    }
    
    

    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/proceso-buscar-id")
    public String buscarId(ModelMap modelo, HttpSession session, @RequestParam String id, @RequestParam String user) throws ErrorServicio {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        List <Usuario> usuarios = usuarioRepo.findAll();

        String role = login.getRol().toString();
        modelo.put("role", role);
        Usuario usuario = null;
        Optional <Usuario> rta = usuarioRepo.findById(user);
        if (rta.isPresent()) {
            usuario = rta.get();
        }
        try {
            if(usuario.getRol().equals(Rol.ADMIN)) {
                throw new ErrorServicio("El usuario es administrador por lo que no puede ser modificado desde este formulario");
            }
            List <Usuario> bajados = usuarioServ.listarBajados();
            List<Genero> generos = new ArrayList<Genero>(Arrays.asList(Genero.values()));
            List<Rol> roles = new ArrayList<Rol>(Arrays.asList(Rol.values()));
            roles.remove(Rol.ADMIN);
            modelo.put("bajados", bajados);
            modelo.put("roles", roles);
            modelo.put("generos", generos);
            modelo.put("perfil",usuario);
            modelo.put("usuarios",usuarios);
            return "administrador.html";
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("usuarios",usuarios);
            return "administrador.html";
        }
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/proceso-buscar-nombre")
    public String buscarNom(ModelMap modelo, HttpSession session, @RequestParam String id, @RequestParam String qUsuario) throws ErrorServicio {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        List <Usuario> usuarios = usuarioRepo.findAll();
        try {
            Usuario usuario = null;
            Optional <Usuario> rta = usuarioRepo.buscaUsuarioNomTot(qUsuario);
            if (rta.isPresent()) {
                usuario = rta.get();
            } else {
                throw new ErrorServicio("El Usuario no se encuantra en la base de datos");
            }
            
            if(usuario.getRol().equals(Rol.ADMIN)) {
                throw new ErrorServicio("El usuario es administrador por lo que no puede ser modificado desde este formulario");
            }
            
            List<Genero> generos = new ArrayList<Genero>(Arrays.asList(Genero.values()));
            List<Rol> roles = new ArrayList<Rol>(Arrays.asList(Rol.values()));
            List <Usuario> bajados = usuarioServ.listarBajados();
            roles.remove(Rol.ADMIN);
            modelo.put("roles", roles);
            modelo.put("generos", generos);
            modelo.put("perfil",usuario);
            modelo.put("usuarios",usuarios);
            modelo.put("bajados", bajados);

            return "administrador.html";
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("usuarios",usuarios);
            return "administrador.html";
        }
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/proceso-modificar-rol")
    public String modificarRol(ModelMap modelo, HttpSession session, @RequestParam String id, @RequestParam String usuarioId, @RequestParam String rol) throws ErrorServicio {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        Usuario usuario = usuarioServ.modificarRol(usuarioId, rol);
        modelo.put("tit", "Operación Exitosa");
        if (usuario.getRol().equals(Rol.EDITOR)) {
            modelo.put("subTit", "El usuario ahora tiene el rol de Editor");
        } else {
            modelo.put("subTit", "El usuario ahora tiene el rol de Usuario");
        }
        return "succes.html";
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/editar-perfil")
    public String modificarUsuario(ModelMap modelo, HttpSession session, @RequestParam String id, @RequestParam String usuarioId, String name, String pass1, String pass2, int generoId, String mail, MultipartFile archivo) throws ErrorServicio { 
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        
        String role = login.getRol().toString();
        modelo.put("role", role);
        List<Genero> generos = new ArrayList<Genero>(Arrays.asList(Genero.values()));
        Usuario usuario = null;
        usuario = usuarioServ.buscarPorId(usuarioId);
        try {        
            usuarioServ.modificar(usuarioId, name, pass1, pass2, generoId, mail, archivo);
            modelo.put("tit", "Operación Exitosa");
            modelo.put("subTit", "La información fue ingresada al base de datos correctamente.");
            return "succes.html";
        } catch (ErrorServicio ex) {
            List <Usuario> bajados = usuarioServ.listarBajados();
            modelo.put("error", ex.getMessage());
            modelo.put("perfil", usuario);
            modelo.put("generos", generos);
            modelo.put("bajados", bajados);
            return "administrador.html";
        }
    }
}