package com.mascotappspring.demo.servicios;


import com.mascotappspring.demo.entidades.Libro;
import com.mascotappspring.demo.entidades.Orden;
import com.mascotappspring.demo.entidades.Prestamo;
import com.mascotappspring.demo.entidades.Usuario;
import com.mascotappspring.demo.excepciones.ErrorServicio;
import com.mascotappspring.demo.repositorios.LibroRepositorio;
import com.mascotappspring.demo.repositorios.OrdenRepositorio;
import com.mascotappspring.demo.repositorios.UsuarioRepositorio;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mascotappspring.demo.repositorios.PrestamoRepositorio;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Gonzalo
 */
@Service
public class PrestamoServicio {
    @Autowired
    private PrestamoRepositorio prestamoRepo;
    @Autowired
    private LibroRepositorio libroRepo;
    @Autowired
    private LibroServicio libroServ;
    @Autowired
    private UsuarioRepositorio usuarioRepo;
    @Autowired
    private OrdenRepositorio ordenRepo;
    
    @Transactional
    public Prestamo iniciarPrestamo(String usuarioId, String libroId) throws ErrorServicio {
        
        if (usuarioId == null || usuarioId.isEmpty()) {
            throw new ErrorServicio("Falta ingresar el número de socio");
        }
        
        if (libroId == null || libroId.isEmpty()) {
            throw new ErrorServicio("Falta ingresar el libro que desea pedir");
        }
        
        int limite = limitePrestamo(usuarioId);
        if (limite >= 5) {
            throw new ErrorServicio("Usted ha excedido el límite de préstamos permitidos.");
        }
        
        Usuario usuarioPrestamo = null;
        Optional<Usuario> rta = usuarioRepo.buscaUsuarioIdAlta(usuarioId);
        if(rta.isPresent()) {
            usuarioPrestamo = rta.get();
        } else {
            throw new ErrorServicio("No hay un socio registrado con ese nombre.");
        }
        
        if (usuarioPrestamo.getPenalidad() == true) {
            throw new ErrorServicio("Usted se encuentra penalizado para el préstamo de Libros");
        }
        
        Libro libroPrestamo = null; 
        Optional<Libro> rta1 = libroRepo.buscaLibroId(libroId);
        if(rta1.isPresent()) {
            libroPrestamo = rta1.get();
        } else {
            throw new ErrorServicio("No hay un libro registrado con ese Título.");
        }
        
        Prestamo prestamo = new Prestamo();
        
        prestamo.setAlta(Boolean.FALSE);
        
        if (libroPrestamo.getEjemplaresRestantes() > 0) {
            prestamo.setLibro(libroPrestamo);
            libroPrestamo.setEjemplaresRestantes(libroPrestamo.getEjemplaresRestantes() - 1);
            libroPrestamo.setEjemplaresPrestados(libroPrestamo.getEjemplaresPrestados() + 1);
            
        } else {
            throw new ErrorServicio("No hay ejemplares disponibles para préstamo");
        }
        
        prestamo.setOrden(null);
        prestamo.setUsuario(usuarioPrestamo);
        prestamo.setFechaSolicitud(new Date());
        libroServ.modEjemplaresRet(libroPrestamo);
        return prestamoRepo.save(prestamo);
    }
    
    
    
    @Transactional
    public Prestamo completarPrestamo(String idPrestamo,String ordenId) throws ErrorServicio, ParseException {
        Prestamo prestamo = new Prestamo();
        Optional <Prestamo> rta = prestamoRepo.findById(idPrestamo);
        if (rta.isPresent()) {
            prestamo = rta.get();
        }
        prestamo.setAlta(true);
        prestamo.setFechaAlta(new Date());
        prestamo.setFechaDevolucion(generarFechaDevolucion(prestamo.getFechaAlta()));
        
        Orden orden = null;
        Optional <Orden> rta1 = ordenRepo.findById(ordenId);
        if(rta1.isPresent()) {
            orden = rta1.get();
        }
        
        prestamo.setOrden(orden);
        
        Date vencimiento = prestamo.getFechaDevolucion();
        orden.setFechaDevolucion(vencimiento);
        ordenRepo.save(orden);
        
        return prestamo;
    }
    
    
    
    @Transactional
    public void bajaPrestamo(String idPrestamo, String ordenId) throws ErrorServicio, ParseException {
        Prestamo prestamo = null;
        Optional <Prestamo> rta = prestamoRepo.findById(idPrestamo);
        if (rta.isPresent()) {
            prestamo = rta.get();
        }        
        Usuario usuario = prestamo.getUsuario();
        Libro libro = prestamo.getLibro();      
        prestamo.setAlta(false);
        prestamo.setFechaBaja(new Date());
        Date dateBaja = prestamo.getFechaBaja();
        Date dateVenc = prestamo.getFechaDevolucion();
        Date datePen = usuario.getFechaPenalidad();
        if (dateVenc.before(dateBaja)) {
            usuario.setPenalidad(Boolean.TRUE);
            usuario.setFechaPenalidad(diasPenalidad(dateBaja, dateVenc, datePen));
        }
        libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() - 1);
        libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() + 1);
    }
    

    static LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    
    @Transactional(readOnly = true)
    public boolean verificarPrestamosEnCurso(String id) {
        boolean prestamo = false;
        Usuario usuario = null;
        List <Orden> ordenes = null;
        Optional <Usuario> rta = usuarioRepo.buscaUsuarioIdAlta(id);
        if (rta.isPresent()) {
            usuario = rta.get();
        }
        
        Optional <List<Orden>> rta1 = ordenRepo.listarActivas();
        if(rta1.isPresent()) {
            ordenes = rta1.get();
        }
        
        for (Orden orden : ordenes) {
            if(orden.getUsuario().equals(usuario)) {
                prestamo = true;
            }
        }
        
        return prestamo;
    }

    @Transactional(readOnly = true)   
    private int limitePrestamo(String usuarioId) {
        int limite = 0;
        List <Prestamo> prestamos = null;
        Optional <List <Prestamo>> rta = prestamoRepo.listarPrestamoSolicitadosUsuarioID(usuarioId);
        if (rta.isPresent()) {
            prestamos = rta.get();
        }
        limite = prestamos.size();
        return limite;
    }
    
    
    static Date generarFechaDevolucion(Date dateAlta) throws ParseException {
        int horas = 176;
        Calendar calendar = Calendar.getInstance();	
        calendar.setTime(dateAlta); // Configuramos la fecha que se recibe
        calendar.add(Calendar.HOUR, horas);  // numero de horas a añadir, o restar en caso de horas<0
        return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas
    }
    
    
    static Date diasPenalidad(Date dateBaja, Date dateVenc, Date datePen) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ROOT);
	String dBaja = dateFormat.format(dateBaja);
       	String dVenc = dateFormat.format(dateVenc);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ROOT);
        Date baja = sdf.parse(dBaja);
        Date venc = sdf.parse(dVenc);

        long diffInMillies = Math.abs(baja.getTime() - venc.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        
        int days = (int) diff;
        
        Calendar calendar = Calendar.getInstance();
        if (datePen == null) {
            calendar.setTime(dateBaja);
            calendar.add(Calendar.DAY_OF_YEAR, days);  
            return calendar.getTime();
        } else {
            calendar.setTime(datePen);
            calendar.add(Calendar.DAY_OF_YEAR, days); 
            return calendar.getTime();
        }
    }

    public void bajaSolicitud(String solicitId) {
       Prestamo prestamo = null;
       Optional<Prestamo> rta = prestamoRepo.findById(solicitId);
       if (rta.isPresent()) {
           prestamo = rta.get();
       }
       prestamoRepo.delete(prestamo);
    }
}