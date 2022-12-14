/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascotappspring.demo.repositorios;


import com.mascotappspring.demo.entidades.Par;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParRepositorio extends JpaRepository<Par, String>{
    @Query("SELECT e FROM Par e WHERE e.liker.id = :id AND e.alta = true AND e.matched = false")
    public List<Par> likeds (@Param("id") String id);

    @Query("SELECT e FROM Par e WHERE e.liked.id = :id AND e.alta = true AND e.matched = false")
    public List<Par> likers (@Param("id") String id);

    @Query("SELECT e FROM Par e WHERE e.liked.id = :id AND e.alta = true AND e.matched = true")
    public List<Par> matchersLd (@Param("id") String id);

    @Query("SELECT e FROM Par e WHERE e.liker.id = :id AND e.alta = true AND e.matched = true")
    public List<Par> matchersLr (@Param("id") String id);

}