/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascotappspring.demo.repositorios;

import com.mascotappspring.demo.entidades.Prestamo;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, String> {
    
    //Muestra un prestamo dado de alta
    @Query("SELECT p FROM Prestamo p WHERE p.id = :prestamoId AND p.alta = true")
    public Optional<Prestamo> buscaPrestamoIdAlta (@Param("prestamoId") String prestamoId);
    
    //Muestra todas las solicitudes de préstamo
    @Query("SELECT s FROM Prestamo s WHERE s.alta = false AND s.fechaAlta is null")
    public Optional <List<Prestamo>> listarPrestamoSolicitados();

    //Lista todos los prestamos solicitados por un usuario 
    @Query("SELECT p FROM Prestamo p WHERE p.usuario.id = :usuarioId AND p.fechaAlta is null")
    public Optional<List<Prestamo>> listarPrestamoSolicitadosUsuarioID (@Param("usuarioId") String usuarioId);
    
    //Lista todos los prestamos solicitados por un usuario 
    @Query("SELECT p FROM Prestamo p WHERE p.usuario.id = :usuarioId AND p.alta = true")
    public Optional<List<Prestamo>> buscaPrestamoActivosUsuarioID (@Param("usuarioId") String usuarioId);
    

    //Lista los prestamos activos de un determinado libro
    @Query("SELECT p FROM Prestamo p WHERE p.libro.titulo = :titulo AND p.alta = true")
    public Optional<List<Prestamo>> buscaPrestamoLibro (@Param("titulo") String titulo);

    //Muestra todos los préstamos activos 
    @Query("SELECT s FROM Prestamo s WHERE s.alta = true")
    public Optional <List<Prestamo>> listarPrestamo();
    
    @Query("SELECT s FROM Prestamo s")
    public Optional <List<Prestamo>> listarPrestamoTot();

    
    //Selecciona los prestamos activos que están incluidos en una orden
    @Query("SELECT p FROM Prestamo p WHERE p.orden.id = :ordenId AND p.alta = true")
    public Optional<List<Prestamo>> listarPrestamoByOrden(@Param("ordenId")String ordenId);
    
}