/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascotappspring.demo.repositorios;
import com.mascotappspring.demo.entidades.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    //Busca usuario de alta por nombre
    @Query("SELECT s FROM Usuario s WHERE s.nombre = :nombre AND s.alta = true")
    public Optional<Usuario> buscaUsuarioNom (@Param("nombre") String nombre);
    
    @Query("SELECT s FROM Usuario s WHERE s.nombre = :nombre")
    public Optional<Usuario> buscaUsuarioNomTot (@Param("nombre") String nombre);
    
    //Busca usuario de alta por id
    @Query("SELECT s FROM Usuario s WHERE s.id = :usuarioId AND s.alta = true")
    public Optional<Usuario> buscaUsuarioIdAlta (@Param("usuarioId") String usuarioId);
        
//    @Query("SELECT s FROM Usuario s WHERE s.pass = :pass AND s.alta = true")
//    public Usuario buscaUsuarioPass (@Param("pass") String nombre);
    
    //busca usuarios por mail
    @Query("SELECT s FROM Usuario s WHERE s.mail = :mail AND s.alta = true")
    public Optional<Usuario> buscaUsuarioMail(String mail);
    
    
        @Query("SELECT s FROM Usuario s WHERE s.dni = :dni AND s.alta = true")
    public Optional<Usuario> buscaUsuarioDni(String dni);
    
    //Lista usuarios de alta
    @Query("SELECT s FROM Usuario s WHERE s.alta = true")
    public Optional<List<Usuario>> listarUsuarios();
    
    //Lista pedidos de baja de Usuarios
    @Query("SELECT u FROM Usuario u WHERE u.solicitudBaja = true AND u.alta = true")
    public Optional<List<Usuario>> listarSolicitudesBaja();
}