/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascotappspring.demo.repositorios;


import com.mascotappspring.demo.entidades.Mascota;
import com.mascotappspring.demo.enumeraciones.Raza;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota, String>{
    
    @Query("SELECT e FROM Mascota e WHERE e.nombre = :nombre AND e.altaMascota = true AND e.usuario.id = :id")
    public Optional<Mascota> buscaMascotaNom (@Param("nombre") String nombre, @Param("id") String id);
    
    @Query("SELECT e FROM Mascota e WHERE e.nombre = :nombre")
    public Optional<Mascota> buscaMascotaNomCompl (@Param("nombre") String nombre);
    
    @Query("SELECT e FROM Mascota e WHERE e.id = :id AND e.altaMascota = true")
    public Optional<Mascota> buscaMascotaId (@Param("id") String id);
    
    @Query("SELECT e FROM Mascota e WHERE e.id = :id")
    public Optional<Mascota> buscaMascotaIdCompl (@Param("id") String id);
    
    @Query("SELECT e FROM Mascota e WHERE e.altaMascota = true")
    public List<Mascota> listarMascotaActiva();
    
    @Query("SELECT e FROM Mascota e")
    public List<Mascota> listarMascotaCompleta();
    
    @Query("SELECT e FROM Mascota e WHERE e.usuario.id = :id AND e.altaMascota = true")
    public List<Mascota> buscaMascotaUser (@Param("id") String id);
    
    @Query("SELECT e FROM Mascota e WHERE e.raza = :raza AND e.altaMascota = true")
    public Optional<List<Mascota>> listarMascotaRaza(@Param("raza") Raza raza);
    
}