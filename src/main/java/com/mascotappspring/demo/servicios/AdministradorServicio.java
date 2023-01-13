package com.mascotappspring.demo.servicios;



import com.mascotappspring.demo.entidades.Usuario;
import com.mascotappspring.demo.excepciones.ErrorServicio;
import com.mascotappspring.demo.repositorios.UsuarioRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Gonzalo
 */
@Service
public class AdministradorServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepo;

    
    @Transactional
    public void completarBajaDeUsuario(String solicitId) {
        Usuario usuario = null;
        Optional <Usuario> rta = usuarioRepo.buscaUsuarioIdAlta(solicitId);
        if(rta.isPresent()) {
            usuario = rta.get();
        }
        usuario.setAlta(Boolean.FALSE);
        usuarioRepo.save(usuario);
    }
}