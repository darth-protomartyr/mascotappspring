package com.mascotappspring.demo.servicios;


import com.mascotappspring.demo.entidades.Orden;
import com.mascotappspring.demo.entidades.Prestamo;
import com.mascotappspring.demo.entidades.Usuario;
import com.mascotappspring.demo.excepciones.ErrorServicio;
import com.mascotappspring.demo.repositorios.OrdenRepositorio;
import com.mascotappspring.demo.repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mascotappspring.demo.repositorios.PrestamoRepositorio;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


/**
 *
 * @author Gonzalo
 */
@Service
public class AdministradorServicio {
    @Autowired
    private PrestamoRepositorio prestamoRepo;
    @Autowired
    private UsuarioRepositorio usuarioRepo;
    @Autowired
    private UsuarioServicio usuarioServ;
    @Autowired
    private OrdenRepositorio ordenRepo;
    
    @Transactional(readOnly = true)
    public List<Usuario> listarSolicitantes() throws ErrorServicio {
        List<Prestamo> prestamos = new ArrayList();
        HashSet<Usuario> solicitantesHS = new HashSet();
        Optional<List<Prestamo>> rta = prestamoRepo.listarPrestamoSolicitados();
        if (rta.isPresent()) {
            prestamos = rta.get();
        }
        for (Prestamo prestamo : prestamos) {
            solicitantesHS.add(prestamo.getUsuario());
        }
        List<Usuario> solicitantes = new ArrayList(solicitantesHS);
        return solicitantes;
    }   

    @Transactional(readOnly = true)
    public List<Orden> listarActivas() {
        List<Orden> activos = new ArrayList();
        Optional <List<Orden>> rta = ordenRepo.listarActivas();
        if (rta.isPresent()) {
            activos= rta.get();
        }
        return activos;
    }
    
    @Transactional(readOnly = true)
    public List<Orden> listarVencidas() {
        List <Orden> vencidas = new ArrayList();
        List <Orden> todas = listarActivas();
        for (Orden toda : todas) {
            if (toda.getFechaDevolucion().before(new Date())) {
                vencidas.add(toda);
            }
        }
        return vencidas;
    }
    
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