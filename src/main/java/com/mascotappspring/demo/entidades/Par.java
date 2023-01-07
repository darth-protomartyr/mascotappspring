/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascotappspring.demo.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Gonzalo
 */
@Entity
public class Par {
    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private Boolean alta;
    private Boolean matched;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMatch;
    @ManyToOne
    @JoinColumn(name = "par_mascotaLR")
    private Mascota liker;
    @ManyToOne
    @JoinColumn(name = "par_mascotaLD")
    private Mascota liked;


    public Par() {
    }


    public Par(String id, Boolean alta, Date fechaAlta, Mascota liker, Mascota liked, List<Mascota> mascotas) {
        this.id = id;
        this.alta = alta;
        this.matched = false;
        this.fechaAlta = fechaAlta;
        this.liker = liker;
        this.liked = liked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Boolean getMatched() {
        return matched;
    }

    public void setMatched(Boolean matched) {
        this.matched = matched;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaMatch() {
        return fechaMatch;
    }

    public void setFechaMatch(Date fechaMatch) {
        this.fechaMatch = fechaMatch;
    }
    
        public Mascota getLiker() {
        return liker;
    }

    public void setLiker(Mascota liker) {
        this.liker = liker;
    }

    public Mascota getLiked() {
        return liked;
    }

    public void setLiked(Mascota liked) {
        this.liked = liked;
    }
}
