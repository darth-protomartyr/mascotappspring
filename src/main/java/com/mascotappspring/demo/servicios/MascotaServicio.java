/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascotappspring.demo.servicios;


import com.mascotappspring.demo.entidades.Mascota;
import com.mascotappspring.demo.entidades.Foto;
import com.mascotappspring.demo.entidades.Par;
import com.mascotappspring.demo.entidades.Usuario;
import com.mascotappspring.demo.enumeraciones.Color;
import com.mascotappspring.demo.enumeraciones.Especie;
import com.mascotappspring.demo.enumeraciones.Genero;
import com.mascotappspring.demo.enumeraciones.Raza;
import com.mascotappspring.demo.excepciones.ErrorServicio;
import com.mascotappspring.demo.repositorios.MascotaRepositorio;
import com.mascotappspring.demo.repositorios.ParRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author Gonzalo
 */
@Service
public class MascotaServicio {
    @Autowired
    MascotaRepositorio mascotaRepo;
    @Autowired
    FotoServicio picServ;
    @Autowired
    ParServicio parServ;
    @Autowired
    ParRepositorio parRepo;


    @Transactional
    public Mascota crearMascota(Usuario usuario, int especieId, String nombre, String apodo, int genId, int colId, int razaId, MultipartFile archivo) throws ErrorServicio {
        if (especieId != 1 && especieId != 2 && especieId != 3 && especieId != 4 && especieId != 5 && especieId != 6) {
            throw new ErrorServicio("Falta la especie de la mascota");
        }
        
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("Falta el nombre de la mascota");
        }
        
        String userId = usuario.getId();
        
        Optional<Mascota> rta = mascotaRepo.buscaMascotaNom(nombre, userId);
        if (rta.isPresent()) {
            throw new ErrorServicio("El mail ya se encuentra registrado en la base de datos");
        }
        
        if (apodo == null || apodo.isEmpty()) {
            throw new ErrorServicio("Falta el apodo de la mascota");
        } 
        
        if (genId != 1 && genId != 2 && genId != 3) {
            throw new ErrorServicio("Falta el genero de la mascota");
        }
        
        if (colId !=1 && colId !=2 && colId !=3 && colId !=4 && colId !=5 && colId !=6 && colId !=7 && colId !=8 && colId !=9 && colId !=10 && colId !=11 &&colId !=12 &&colId !=13 &&colId !=14) {
            throw new ErrorServicio("Falta el color de la mascota");
        }
        
        if (razaId != 1 && razaId != 2 && razaId != 3 && razaId != 4 && razaId != 5 && razaId != 6 && razaId != 7 && razaId != 8 && 
                razaId != 9 && razaId != 10 && razaId != 11 && razaId != 12 && razaId != 13 && razaId != 14 && razaId != 15 && razaId != 16 && razaId != 17 && 
                razaId != 18 && razaId != 19 && razaId != 20 && razaId != 21 && razaId != 22 && razaId != 23 && razaId != 24 && razaId != 25 && 
                razaId != 26 && razaId != 27 && razaId != 28 && razaId != 29 && razaId != 30 && razaId != 31 && razaId != 32 && razaId != 33 && razaId != 34 &&
                razaId != 35 && razaId != 36) {
            throw new ErrorServicio("Falta la raza de la mascota");
        }
        
        if (archivo == null || archivo.isEmpty()) {
            throw new ErrorServicio("Falta la imagen de la mascota");
        }
        
        Color color = validarColor(colId);
        Genero gen = validarGenero(genId);
        Raza raza = validarRaza(razaId);
        Especie especie = validarEspecie(especieId);
        
        int spe = especie.getEspecieId();
        int race = raza.getEsp();
        
        if (spe == 1 && race == 2) {
            throw new ErrorServicio("Usted no ingresó una raza canina.");
        }
        
        if (spe == 2 && race == 1) {
            throw new ErrorServicio("Usted no ingresó una raza felina.");
        }
        Mascota tobias = new Mascota();
        tobias.setUsuario(usuario);
        tobias.setEspecie(especie);
        tobias.setNombre(nombre);
        tobias.setApodo(apodo);
        tobias.setFechaAlta(new Date());
        tobias.setAltaMascota(true);
        tobias.setGen(gen);
        tobias.setCol(color);
        tobias.setRaza(raza);
        Foto foto = picServ.guardar(archivo);
        tobias.setFoto(foto);
        return mascotaRepo.save(tobias);
    }


    @Transactional(readOnly=true)
    public List<Mascota> listarPetRace(String idMascota) throws ErrorServicio {
        Mascota pet1 = new Mascota();
        Optional<Mascota> rta = mascotaRepo.buscaMascotaId(idMascota);
        if (rta.isPresent()) {
            pet1 = rta.get();
        }
        Raza raza = pet1.getRaza();
        List<Mascota> mascotasProv = new ArrayList<Mascota>();
        Optional <List<Mascota>> rta1 = mascotaRepo.listarMascotaRaza(raza);//Lista raza
        if(rta1.isPresent()) {
            mascotasProv = rta1.get();
        }
        mascotasProv.remove(pet1);//elimina mascota propia
        List<Mascota> mascotasMacho = new ArrayList<Mascota>(); //lista mascota según sexo
        List<Mascota> mascotasHembra = new ArrayList<Mascota>();
        Iterator<Mascota> it = mascotasProv.iterator();
        while(it.hasNext()) {
            Mascota pet2 = it.next();
            if(pet2.getGen().equals(Genero.HOMBRE)) {
                mascotasMacho.add(pet2);
            } else if(pet2.getGen().equals(Genero.MUJER)) {
                mascotasHembra.add(pet2);
            }
        } 
        List<Mascota> mascotas = new ArrayList<Mascota>();
        Genero gene = pet1.getGen();
        if(gene.equals(Genero.HOMBRE)) {
            mascotas = mascotasHembra;
        } else if(gene.equals(Genero.MUJER))  {
            mascotas = mascotasMacho;
        } else {
            throw new ErrorServicio("El sexo de su mascota es otro");
        }
        List<Mascota> mascotasLk = parServ.listarLikeds(idMascota); // elimina mascotas con like dado 
        if(mascotasLk.size()>0) {
            Iterator<Mascota> it2 = mascotasLk.iterator();
            while(it2.hasNext()) {
                Mascota pet3 = it2.next();
                mascotas.remove(pet3);
            }   
        }
        List<Mascota> mascotasMd = listarMatches(idMascota); //elimina mascotas matcheadas
        if(mascotasMd.size()>0) {
            Iterator<Mascota> it2 = mascotasMd.iterator();
            while(it2.hasNext()) {
                Mascota pet4 = it2.next();
                mascotas.remove(pet4);
            }   
        }
        List<Par> parMascotas = parRepo.likers(idMascota);//elimina mascotas a quien le gustas
        List<Mascota> mascotasLr = new ArrayList<Mascota>();
        for (Par par : parMascotas) {
            Mascota pet5 = new Mascota();
            pet5 = par.getLiker();
            mascotasLr.add(pet5);
        }
        if(mascotasLr.size()>0) {
            Iterator<Mascota> it2 = mascotasLr.iterator();
            while(it2.hasNext()) {
                Mascota pet6 = it2.next();
                mascotas.remove(pet6);
            }   
        }
        return mascotas;
    }
    
    
    @Transactional(readOnly=true)
    public List<Mascota> listarMatches(String id) {
        Mascota pet1 = new Mascota();
        Optional<Mascota> rta = mascotaRepo.buscaMascotaId(id);
        if(rta.isPresent()) {
            pet1=rta.get(); 
        }
        List<Par> paresLR = parRepo.matchersLr(id);
        List<Par> paresLD = parRepo.matchersLd(id);
        paresLD.addAll(paresLR);
        List<Mascota> mascotas1 = new ArrayList<Mascota>();
        for (Par par : paresLD) {
            Mascota pet2 = new Mascota();
            pet2 = par.getLiked();
            mascotas1.add(pet2);
            pet2 = par.getLiker();
            mascotas1.add(pet2);
        }
        Set<Mascota> petSet = new HashSet<>(mascotas1);
        List<Mascota> mascotas = new ArrayList<Mascota>(petSet);
        mascotas.remove(pet1);
        return mascotas;
    }
    
    
    @Transactional(readOnly=true)
    public Mascota buscarMascotaId(String id) throws ErrorServicio {
        Optional<Mascota> respuesta = mascotaRepo.findById(id);
        if (respuesta.isPresent()) {
            Mascota mascota = respuesta.get();
            return mascota;
        } else {
            throw new ErrorServicio("No se encontró la mascota solicitada");
        }
    }


    static Genero validarGenero(int generoId) throws ErrorServicio {
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
    
    
    static Color validarColor(int colorId) throws ErrorServicio {
        switch (colorId) {
            case 1:
                return Color.BLANCO;
            case 2:
                return Color.NEGRO;
            case 3:
                return Color.MARRON;
            case 4:
                return Color.FUEGO;
            case 5:
                return Color.AZUL;
            case 6:
                return Color.VERDE;
            case 7:
                return Color.AMARILLO;
            case 8:
                return Color.NARANJA;
            case 9:
                return Color.GRIS;
            case 10:
                return Color.ROJO;
            case 11:
                return Color.VIOLETA;
            case 12:
                return Color.ROSA;
            case 13:
                return Color.CELESTE;
            case 14:
                return Color.NS_NC;              
            default:
                throw new ErrorServicio("No ingresó un dato válido en la categoria Color");
        }
    }
    
    
    static Especie validarEspecie(int especieId) throws ErrorServicio {
        switch (especieId) {
            case 1:
                return Especie.PERRO;
            case 2:
                return Especie.GATO;
            case 3:
                return Especie.TORTUGA;            
            case 4:
                return Especie.AVE;            
            case 5:
                return Especie.ROEDOR;            
            case 6:
                return Especie.NS_NC;
            default:
                throw new ErrorServicio("No ingresó un dato válido en la categoria género");
        }
    }
    
    
    static Raza validarRaza(int razaId) throws ErrorServicio {
        switch (razaId) {
            case 1:
                return Raza.PEKINES;
            case 2:
                return Raza.BEAGLE;
            case 3:
                return Raza.BORDER_COLLIE;
            case 4:
                return Raza.BOXER;
            case 5:
                return Raza.BULL_TERRIER;
            case 6:
                return Raza.BULLDOG_AMERICANO;
            case 7:
                return Raza.BULLDOG_FRANCÉS;
            case 8:
                return Raza.BULLMASTIFF;
            case 9:
                return Raza.CANICHE_TOY;
            case 10:
                return Raza.CHIHUAHUA;
            case 11:
                return Raza.CHOW_CHOW;
            case 12:
                return Raza.COCKER_SPANIEL;
            case 13:
                return Raza.DOBERMAN;
            case 14:
                return Raza.DOGO_ARGENTINO;
            case 15:
                return Raza.DOGO_DE_BURDEOS;
            case 16:
                return Raza.DÁLMATA;
            case 17:
                return Raza.FILA_BRASILEIRO;
            case 18:
                return Raza.FOX_TERRIER;
            case 19:
                return Raza.GALGO;
            case 20:
                return Raza.GOLDEN_RETRIEVER;
            case 21:
                return Raza.GRAN_DANÉS;
            case 22:
                return Raza.HUSKY_SIBERIANO;
            case 23:
                return Raza.MASTÍN_NAPOLITANO;
            case 24:
                return Raza.OVEJERO_ALEMÁN;
            case 25:
                return Raza.ROTTWEILER;
            case 26:
                return Raza.SAN_BERNARDO;
            case 27:
                return Raza.SETTER_IRLANDÉS;
            case 28:
                return Raza.SHAR_PEI;
            case 29:
                return Raza.VIEJO_PASTOR_INGLÉS;
            case 30:
                return Raza.AKITA;
            case 31:
                return Raza.OTRA_PERRO;    
            case 32:
                return Raza.PERSA;
            case 33:
                return Raza.AZUL_RUSO;
            case 34:
                return Raza.SIAMÉS;
            case 35:
                return Raza.SIBERIANO;
            case 36:
                return Raza.BENGALÍ;
            case 37:
                return Raza.OTRA_GATO;
            case 38:
                return Raza.OTRA_INDISTINTA;
            default:
                throw new ErrorServicio("No ingresó un dato válido en la categoria Color");
        }
    }
}