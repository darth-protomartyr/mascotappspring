
package com.mascotappspring.demo.controladores;

import com.mascotappspring.demo.entidades.Usuario;
import com.mascotappspring.demo.entidades.Mascota;
import com.mascotappspring.demo.entidades.Editorial;
import com.mascotappspring.demo.entidades.Libro;
import com.mascotappspring.demo.enumeraciones.Color;
import com.mascotappspring.demo.enumeraciones.Especie;
import com.mascotappspring.demo.enumeraciones.Genero;
import com.mascotappspring.demo.enumeraciones.Raza;
import com.mascotappspring.demo.excepciones.ErrorServicio;
import com.mascotappspring.demo.repositorios.MascotaRepositorio;
import com.mascotappspring.demo.repositorios.EditorialRepositorio;
import com.mascotappspring.demo.repositorios.LibroRepositorio;
import com.mascotappspring.demo.servicios.UsuarioServicio;
import com.mascotappspring.demo.servicios.LibroServicio;
import com.mascotappspring.demo.servicios.MascotaServicio;
import java.util.ArrayList;
import java.util.Arrays;
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
@RequestMapping("/mascotas")
public class MascotaControlador {

    
    @Autowired
    private UsuarioServicio usuarioServ;    
    @Autowired
    private MascotaServicio mascotaServ;
    @Autowired
    private MascotaRepositorio mascotaRepo;

    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR', 'ROLE_USUARIO')")
    @GetMapping("/mascota")
    public String mascotas(HttpSession session, @RequestParam String id, ModelMap modelo) throws ErrorServicio {
        List<Mascota> mascotas = mascotaRepo.findAll();
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        modelo.put("mascotas", mascotas);
        modelo.put("pen", "La cuenta se encuentra penalizada para realizar préstamos");
        String role = login.getRol().toString();
        modelo.put("role", role);
        return "mascotas.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR', 'ROLE_USUARIO')")
    @GetMapping("/ingresar")
    public String ingresar(HttpSession session, @RequestParam String id, ModelMap modelo){
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }
        List<Genero> generos = new ArrayList<Genero>(Arrays.asList(Genero.values()));
        List<Especie> especies = new ArrayList<Especie>(Arrays.asList(Especie.values()));
        List<Color> colores = new ArrayList<Color>(Arrays.asList(Color.values()));
        List<Raza> razas = new ArrayList<Raza>(Arrays.asList(Raza.values()));
        modelo.put("generos", generos);
        modelo.put("especies", especies);
        modelo.put("colores", colores);
        modelo.put("razas", razas);
        modelo.put("pen", "La cuenta se encuentra penalizada para realizar préstamos");
        String role = login.getRol().toString();
        modelo.put("role", role);
        return "mascota-ingresar.html";
    }
    
    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR', 'ROLE_USUARIO')")
    @PostMapping("/proceso-ingresar")
    public String procesoIngresar(HttpSession session, @RequestParam String id, @RequestParam int especieId, @RequestParam String nombre, @RequestParam String apodo, @RequestParam int genId, @RequestParam int colorId, @RequestParam int razaId, @RequestParam MultipartFile archivo, ModelMap modelo) throws ErrorServicio {
        Usuario login = (Usuario) session.getAttribute("usuariosession");
        if (login == null || !login.getId().equals(id)) {
            return "redirect:/login";
        }

        modelo.put("pen", "La cuenta se encuentra penalizada para realizar préstamos");
        String role = login.getRol().toString();
        modelo.put("role", role);
        try {
            mascotaServ.crearMascota( login, especieId, nombre, apodo, genId, colorId, razaId,archivo);
            modelo.put("tit", "Operación Exitosa");
            modelo.put("subTit", "La Mascota fue ingresada a la base de datos correctamente.");
            return "succes.html";
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("especieId", especieId);
            modelo.put("nombre", nombre);
            modelo.put("apodo", apodo);
            modelo.put("genId", genId);
            modelo.put("colId", colorId);
            modelo.put("razaId", razaId);
            modelo.put("archivo", archivo);
            return "mascota-ingresar.html";
        }
    }
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR', 'ROLE_USUARIO')")
//    @PostMapping("/proceso-buscar")
//    public String buscar(HttpSession session, @RequestParam String id, @RequestParam String qmascota, ModelMap modelo) throws ErrorServicio{
//        Usuario login = (Usuario) session.getAttribute("usuariosession");
//        if (login == null || !login.getId().equals(id)) {
//            return "redirect:/login";
//        }
//        modelo.put("pen", "La cuenta se encuentra penalizada para realizar préstamos");
//        String role = login.getRol().toString();
//        modelo.put("role", role);
//        Libro mascota= null;
//        try {            
//            mascota = mascotaServ.buscarLibroTitCompl(qmascota);
//            modelo.put("mascota", mascota);
//            return "mascota.html";
//        } catch (ErrorServicio ex) {
//            modelo.put("error", ex.getMessage());
//            return "mascotas.html";
//        }
//    }
    
//
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
//    @GetMapping("/ingresar")
//    public String ingresar(HttpSession session, @RequestParam String id, ModelMap modelo) {
//        List<Mascota> mascotas = mascotaRepo.findAll();
//        List<Editorial> editoriales = ediRepo.findAll();
//        Usuario login = (Usuario) session.getAttribute("usuariosession");
//        if (login == null || !login.getId().equals(id)) {
//            return "redirect:/login";
//        }
//        modelo.put("pen", "La cuenta se encuentra penalizada para realizar préstamos");
//        String role = login.getRol().toString();
//        modelo.put("role", role);
//        modelo.put("mascotas", mascotas);
//        modelo.put("editoriales", editoriales);
//        return "mascota-ingresar.html";
//    }
//    
//    
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
//    @PostMapping("/proceso-ingresar")
//    public String procesoIngresar( ModelMap modelo, @RequestParam String id, HttpSession session, String titulo, Long isbn, Integer ejemplaresTotales, String mascotaId, String editorialId, MultipartFile archivo) throws ErrorServicio {
//        Usuario login = (Usuario) session.getAttribute("usuariosession");
//        if (login == null || !login.getId().equals(id)) {
//            return "redirect:/login";
//        }
//        List<Mascota> mascotas = mascotaRepo.findAll();
//        List<Editorial> editoriales = ediRepo.findAll();
//        modelo.put("pen", "La cuenta se encuentra penalizada para realizar préstamos");
//        String role = login.getRol().toString();
//        modelo.put("role", role);
//        try {
//            mascotaServ.crearLibro(isbn, titulo, ejemplaresTotales, mascotaId, editorialId, archivo);
//            modelo.put("tit", "Operación Exitosa");
//            modelo.put("subTit", "El Libro fue ingresado a la base de datos correctamente.");
//            return "succes.html";
//        } catch (ErrorServicio e) {
//            modelo.put("error", e.getMessage());
//            modelo.put("titulo", titulo);
//            modelo.put("isbn", isbn);
//            modelo.put("mascotas", mascotas);
//            modelo.put("editoriales", editoriales);
//            modelo.put("mascotaId", mascotaId);
//            modelo.put("editorialId", editorialId);
//            modelo.put("archivo", archivo);
//            return "mascota-ingresar.html";
//        }
//    }
//    
//    
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
//    @GetMapping("/modificar")
//    public String modificar(HttpSession session, @RequestParam String id, @RequestParam String mascotaId,  ModelMap modelo){
//        Usuario login = (Usuario) session.getAttribute("usuariosession");
//        if (login == null || !login.getId().equals(id)) {
//            return "redirect:/login";
//        }
//        List<Mascota> mascotas = mascotaRepo.findAll();
//        List<Editorial> editoriales = ediRepo.findAll();
//        Libro mascota = mascotaRepo.getById(mascotaId);
//        modelo.put("pen", "La cuenta se encuentra penalizada para realizar préstamos");
//        String role = login.getRol().toString();
//        modelo.put("role", role);
//        modelo.put("mascota", mascota);
//        modelo.put("mascotas", mascotas);
//        modelo.put("editoriales", editoriales);
//        return "mascota-actualizar.html";
//    }
//    
//    
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
//    @PostMapping("/proceso-modificar")
//    public String procesoModificar(ModelMap modelo, HttpSession session, @RequestParam String id, @RequestParam String mascotaId, String titulo, Long isbn, Integer ejemplaresTotales, String mascotaId, String editorialId, MultipartFile archivo) throws ErrorServicio {
//        Usuario login = (Usuario) session.getAttribute("usuariosession");
//        if (login == null || !login.getId().equals(id)) {
//            return "redirect:/login";
//        }
//        modelo.put("pen", "La cuenta se encuentra penalizada para realizar préstamos");
//        String role = login.getRol().toString();
//        modelo.put("role", role);
//        List<Mascota> mascotas = mascotaRepo.findAll();
//        List<Editorial> editoriales = ediRepo.findAll();
//        Libro mascota = null;
//        try {
//            mascota = mascotaServ.buscarLibroId(mascotaId);
//            mascotaServ.modificar(mascotaId, isbn, titulo, ejemplaresTotales, mascotaId, editorialId, archivo);
//            modelo.put("tit", "Operación Exitosa");
//            modelo.put("subTit", "La información fue ingresada al base de datos correctamente.");
//            return "succes.html";
//        } catch (ErrorServicio ex) {
//            modelo.put("error", ex.getMessage());
//            modelo.put("mascota", mascota);
//            modelo.put("mascotas", mascotas);
//            modelo.put("editoriales", editoriales);
//            modelo.put("mascotaId", mascotaId);
//            modelo.put("editorialId", editorialId);
//            return "mascota-actualizar.html";
//        }
//    }
//
//    
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR', 'ROLE_USUARIO')")
//    @GetMapping("/listar-activas")
//    public String ListarActiva(HttpSession session, @RequestParam String id, ModelMap modelo) throws ErrorServicio {
//        Usuario login = (Usuario) session.getAttribute("usuariosession");
//        if (login == null || !login.getId().equals(id)) {
//            return "redirect:/login";
//        }
//        
//        
//        List<Libro> mascotas = mascotaServ.listarLibrosActivos();
//        modelo.put("mascotas", mascotas);
//        
//        modelo.put("pen", "La cuenta se encuentra penalizada para realizar préstamos");
//        String role = login.getRol().toString();
//        modelo.put("role", role);
//        return "mascotas-lista-activos.html";
//    }
//    
//    
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
//    @GetMapping("/listar-todas")
//    public String ListarTodas(HttpSession session, @RequestParam String id, ModelMap modelo) throws ErrorServicio {
//        Usuario login = (Usuario) session.getAttribute("usuariosession");
//        if (login == null || !login.getId().equals(id)) {
//            return "redirect:/login";
//        }
//        List<Libro> mascotas = mascotaRepo.listarLibrosCompleta();
//        modelo.put("mascotas", mascotas);
//        modelo.put("pen", "La cuenta se encuentra penalizada para realizar préstamos");
//        String role = login.getRol().toString();
//        modelo.put("role", role);
//        return "mascotas-lista-completa.html";
//    }
//    
//        
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
//    @PostMapping("/proceso-baja")
//    public String procesoBaja(ModelMap modelo, HttpSession session, @RequestParam String id, @RequestParam String mascotaId) throws ErrorServicio {
//        Usuario login = (Usuario) session.getAttribute("usuariosession");
//        if (login == null || !login.getId().equals(id)) {
//            return "redirect:/login";
//        }
//        
//        
//        modelo.put("pen", "La cuenta se encuentra penalizada para realizar préstamos");
//        String role = login.getRol().toString();
//        modelo.put("role", role);
//        try {      
//            mascotaServ.darBajaLibro(mascotaId);
//            modelo.put("tit", "Operación Exitosa");
//            modelo.put("subTit", "La información fue modificada correctamente.");
//            return "succes.html";
//        } catch (ErrorServicio ex) {
//            modelo.put("error", ex.getMessage());
//            return "mascotas.html";
//        }
//    }
//    
//    
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
//    @PostMapping("/proceso-alta")
//    public String procesoAlta(ModelMap modelo, HttpSession session, @RequestParam String id, @RequestParam String mascotaId) throws ErrorServicio {
//        Usuario login = (Usuario) session.getAttribute("usuariosession");
//        if (login == null || !login.getId().equals(id)) {
//            return "redirect:/login";
//        }
//        
//        
//        modelo.put("pen", "La cuenta se encuentra penalizada para realizar préstamos");
//        String role = login.getRol().toString();
//        modelo.put("role", role);
//        try {     
//            mascotaServ.darAltaLibro(mascotaId);
//            modelo.put("tit", "Operación Exitosa");
//            modelo.put("subTit", "La información fue modificada correctamente.");
//            return "succes.html";
//        } catch (ErrorServicio ex) {
//            modelo.put("error", ex.getMessage());
//            return "mascotas.html";
//        }
//    }
}