package com.mascotappspring.demo.servicios;


import com.mascotappspring.demo.enumeraciones.Genero;
import com.mascotappspring.demo.entidades.Mascota;
import com.mascotappspring.demo.entidades.Par;
import com.mascotappspring.demo.excepciones.ErrorServicio;
import com.mascotappspring.demo.repositorios.MascotaRepositorio;
import com.mascotappspring.demo.repositorios.ParRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ParServicio {
    @Autowired
    MascotaRepositorio mascotaRepo;
    @Autowired
    ParRepositorio parRepo;
    @Autowired
    FotoServicio picServ;


    @Transactional
    public Par crearPar(String mascotaId1, String mascotaId2) throws ErrorServicio {
        Par par = new Par();
        Mascota mascota1  =new Mascota();
        Mascota mascota2  =new Mascota();
        Optional <Mascota> rta1 = mascotaRepo.buscaMascotaId(mascotaId1);
        Optional <Mascota> rta2 = mascotaRepo.buscaMascotaId(mascotaId2);
        if (rta1.isPresent()) {
            mascota1 = rta1.get();
        } else {
            throw new ErrorServicio ("La mascota ingresada no se encuentra en la base de datos");            
        }
        if (rta2.isPresent()) {
            mascota2 = rta1.get();
        } else {
            throw new ErrorServicio ("La mascota ingresada no se encuentra en la base de datos");            
        }
        par.setAlta(true);
        par.setMatched(false);
        par.setFechaAlta(new Date());
        par.setLiker(mascota1);
        par.setLiked(mascota2);
        return parRepo.save(par);
    }


    @Transactional(readOnly = true)
    public List<Mascota> listarLikeds(String idMascota) {
        List<Par> paresLKD = parRepo.likeds(idMascota);
        List<Mascota> mascotas = new ArrayList<Mascota>();
        Iterator<Par> it = paresLKD.iterator();
        while(it.hasNext()) {
            Par par = it.next();
            Mascota pet = par.getLiked();
            if (pet != null) {
                mascotas.add(pet);
            }
        }
        return mascotas;
    }
}