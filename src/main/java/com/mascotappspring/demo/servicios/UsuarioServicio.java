/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascotappspring.demo.servicios;


import com.mascotappspring.demo.entidades.Foto;
import com.mascotappspring.demo.entidades.Usuario;
import com.mascotappspring.demo.enumeraciones.Genero;
import com.mascotappspring.demo.enumeraciones.Rol;
import com.mascotappspring.demo.excepciones.ErrorServicio;
import com.mascotappspring.demo.repositorios.UsuarioRepositorio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author Gonzalo
 */

@Service
public class UsuarioServicio implements UserDetailsService {
    @Autowired
    private UsuarioRepositorio usuarioRepo;
    @Autowired
    private FotoServicio picServ;
    @Autowired
    private NotificacionServicio notServ;
//-----------------------------------------------
    @Transactional
    public Usuario registrarUsuario(String nombre, String apellido, String dni, String pass1, String pass2, int id, MultipartFile archivo, String mail) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("Falta el nombre del usuario");
        }
        
        if (pass1 == null || pass1.isEmpty()) {
            throw new ErrorServicio("Falta ingresar el password del usuario");
        }
        
        if (pass2 == null || pass2.isEmpty()) {
            throw new ErrorServicio("Falta ingresar la comprobacion del password del usuario");
        }
        
        String pass = new String();
        if (pass1.equals(pass2)) {
            pass = pass1;
        } else {
            throw new ErrorServicio("No coinciden los passwords ingresados.");
        }
        
        if (pass.length() < 3) {
            throw new ErrorServicio("El password ingresado posee menos de 4 caracteres");
        }
        
        if (id != 1 && id != 2 && id != 3) {
            throw new ErrorServicio("Falta ingresar el sexo del usuario");
        }
        
        Optional<Usuario> rta = usuarioRepo.buscaUsuarioMail(mail);
        if (rta.isPresent()) {
            throw new ErrorServicio("El mail ya se encuentra registrado en la base de datos");
        }
        
        Optional<Usuario> rta1 = usuarioRepo.buscaUsuarioDni(dni);
        if (rta1.isPresent()) {
            throw new ErrorServicio("El dni ya se encuentra registrado en la base de datos");
        }
        
        
        if (mail == null || mail.isEmpty()) {
            throw new ErrorServicio("Falta ingresar el sexo del usuario");
        }
    
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDni(dni);
        String passCrypt = new BCryptPasswordEncoder().encode(pass);
        usuario.setPass(passCrypt);
        usuario.setGenero(validateGenero(id));
        Foto foto = picServ.guardar(archivo);
        usuario.setFoto(foto);
        usuario.setAlta(true);
        usuario.setMail(validarMail(mail));
        usuario.setRol(Rol.USUARIO);
        usuario.setSolicitudBaja(Boolean.FALSE);
        //notServ.enviar("Bienvenido a Librodepository", "Librodepository", usuario.getMail());
        return usuarioRepo.save(usuario);
    }
    
    @Transactional
    public void modificar(String id, String name, String pass1 , String pass2, int generoId, String mail, MultipartFile archivo) throws ErrorServicio {
        
        Usuario usuario = null;
        Optional<Usuario> rta1 = usuarioRepo.findById(id);
        if(rta1.isPresent()) {
            usuario = rta1.get();
            if (name == null || name.isEmpty()) {
                usuario.setNombre(usuario.getNombre());
            } else {
                usuario.setNombre(name);
            }
            
            String pass = null;
            
            if (pass1 != null && !pass1.isEmpty() && pass2 != null && !pass2.isEmpty()) {
                if (pass1.equals(pass2)) {
                    pass = pass1;
                    if (pass.length() > 3) {
                        String passCrypt = new BCryptPasswordEncoder().encode(pass);
                        usuario.setPass(passCrypt);
                    } else {
                        throw new ErrorServicio("El password ingresado posee menos de 4 caracteres");
                    }
                } else {
                    throw new ErrorServicio("No coinciden los passwords ingresados.");
                }
            }
                      
            if (generoId != 1 && generoId != 2 && generoId != 3) {
                usuario.setGenero(usuario.getGenero());
            } else {
                usuario.setGenero(validateGenero(generoId));
            }
            
            Optional<Usuario> rta = usuarioRepo.buscaUsuarioMail(mail);
            if (rta.isPresent() && !mail.equals(usuario.getMail())) {
                throw new ErrorServicio("El mail ya se encuentra registrado en la base de datos");
            }
            

            if (mail == null || mail.isEmpty() || mail == usuario.getMail()) {
                usuario.setMail(usuario.getMail());
            } else {
                usuario.setMail(validarMail(mail));
            }

            if (archivo == null || archivo.isEmpty()) {
                usuario.setFoto(usuario.getFoto());
            } else {
                String idFoto = null;
                if (usuario.getFoto() != null){
                    idFoto = usuario.getFoto().getId();
                }
                Foto foto = picServ.actualizar(idFoto, archivo);
                usuario.setFoto(foto);
            }
        } else {
            throw new ErrorServicio("No hay un socio registrado con ese nombre.");
        }
        usuarioRepo.save(usuario);
        //notServ.enviar("LA modificación ha sido realizada", "Librodepository", usuario.getMail());
    }
    
    
    @Transactional
    public void bajaDeUsuario(String id, String pass) throws ErrorServicio {
        Usuario usuarioPrestamo;
        Optional<Usuario> rta1 = usuarioRepo.buscaUsuarioIdAlta(id);
        if(rta1.isPresent()) {
            usuarioPrestamo = rta1.get();
            if (usuarioPrestamo.getAlta().equals(false)) {
                throw new ErrorServicio("El usuario se encuentra dado de baja.");
            } else {
                 usuarioPrestamo.setAlta(Boolean.TRUE);
                 usuarioRepo.save(usuarioPrestamo);
            }
        } else {
            throw new ErrorServicio ("El nombre de usuario que ingresó no se encuentra en la base de datos");
        }
    }
    
    @Transactional
    public void altaDeUsuario(String id, String pass) throws ErrorServicio {
        Optional<Usuario> rta = usuarioRepo.buscaUsuarioIdAlta(id);
        Usuario usuario = rta.get();
        if (rta.isPresent()) {
            if (usuario.getAlta().equals(true)) {
                throw new ErrorServicio("El usuario se encuentra dado de alta.");
            } else {
            usuario.setAlta(Boolean.TRUE);
            usuarioRepo.save(usuario);
            }
        } else {
            throw new ErrorServicio ("El nombre de usuario que ingresó no se encuentra en la base de datos");
        }
    }
    
//    @Transactional
//    public void modificarPenalidad(String id, int penalidad) throws ErrorServicio {
//        Optional<Usuario> rta = usuarioRepo.buscaUsuarioIdAlta(id);
//        Usuario usuario = rta.get();
//        if (rta.isPresent()) {
//            if (usuario.getAlta().equals(true)) {
//                throw new ErrorServicio("El usuario se encuentra dado de alta.");
//            } else {
//            usuario.setAlta(Boolean.TRUE);
//            usuarioRepo.save(usuario);
//            }
//        } else {
//            throw new ErrorServicio ("El nombre de usuario que ingresó no se encuentra en la base de datos");
//        }
//    }
    
    
    //--------------------------------------Solo Usuario
    @Transactional(readOnly = true)
    public Usuario consultaUsuario(String nom) throws ErrorServicio {
        Optional<Usuario> rta = usuarioRepo.buscaUsuarioNom(nom);
        if (rta.isPresent()) {
            Usuario usuario = rta.get();
            return usuario;
        } else {
            throw new ErrorServicio ("El nombre del usuario que ingresó no se encuentra en la base de datos");
        }
    }
    
    @Transactional(readOnly = true)
    public List<Usuario> consultaListaUsuarios() throws ErrorServicio {
        List<Usuario> usuarios = null;
        Optional<List<Usuario>> rta = usuarioRepo.listarUsuarios();
        if (rta.isPresent()) {
            usuarios = rta.get();
        }
        return usuarios;
    }

    static Genero validateGenero(int generoId) throws ErrorServicio {
        switch (generoId) {
            case 1:
                return Genero.HOMBRE;
            case 2:
                return Genero.MUJER;
            case 3:
                return Genero.OTRO;
            default:
                throw new ErrorServicio("No ingresó un dato válido en la categoria género");
        }
    }
    
    private static String validarMail(String mail) throws ErrorServicio {
	if (mail.contains("@") && mail.contains(".com")) {
            return mail;
        } else {
            throw new ErrorServicio("No es una direccion e-mail válida");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario;
        Optional <Usuario> rta = usuarioRepo.buscaUsuarioMail(mail);
        if (rta.isPresent()) {
            usuario = rta.get();
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + usuario.getRol());
            permisos.add(p1);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            
            User user = new User(usuario.getMail(), usuario.getPass(), permisos);
            return user;
            
        } else {
            return null;
        }
    }
    
    @Transactional(readOnly=true)
    public Usuario buscarPorId(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepo.findById(id);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            return usuario;
        } else {
            throw new ErrorServicio("No se encontró el usuario solicitado");
        }
    }


    @Transactional(readOnly=true)
    public List<Usuario> listarBajados() throws ErrorServicio {
        List <Usuario> bajados = new ArrayList();
        Optional <List<Usuario>> rta = usuarioRepo.listarSolicitudesBaja();
        if(rta.isPresent()) {
            bajados = rta.get();
        }
        return bajados;
    }


    @Transactional
    public void iniciarBajaDeUsuario(String id) throws ErrorServicio {
        Usuario usuario = null;
        Optional<Usuario> rta1 = usuarioRepo.buscaUsuarioIdAlta(id);
        if(rta1.isPresent()) {
            usuario = rta1.get();
            usuario.setSolicitudBaja(Boolean.TRUE);
            usuarioRepo.save(usuario);
        } else {
            throw new ErrorServicio ("El nombre de usuario que ingresó no se encuentra en la base de datos");
        }
    }

    
    public String cleanString(String str) {
        str = str.toLowerCase().replace(" ", "");
        return str;
    }
    
    
    public static Date dateConverter (String newPen)throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
        Date date = sdf.parse(newPen);  
        return date;
    } 

    public Usuario modificarRol(String id, String rol) {
        rol = cleanString(rol);
        Usuario usuario = null;
        Optional<Usuario> rta1 = usuarioRepo.findById(id);
        if(rta1.isPresent()) {
            usuario = rta1.get();
        }
        
        if(rol.equals("editor")) {
            usuario.setRol(Rol.EDITOR);
        }
        
        if(rol.equals("usuario")) {
            usuario.setRol(Rol.USUARIO);
        }
        
        return usuarioRepo.save(usuario);
    }
}