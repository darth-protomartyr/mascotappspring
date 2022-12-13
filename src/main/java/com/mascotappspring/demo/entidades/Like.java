package com.mascotappspring.demo.entidades;


import com.sun.istack.NotNull;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;


@Entity
public class Like {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLike;
    @OneToOne
    private Mascota liker;
    @OneToOne
    private Mascota liked;
    private boolean alta;
    @NotNull
    private boolean match;

    public Like() {
    }

    public Like(String id, Date fechaLike, Mascota liker, Mascota liked, boolean alta, boolean match) {
        this.id = id;
        this.fechaLike = fechaLike;
        this.liker = liker;
        this.alta = alta;
        this.match= false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFechaLike() {
        return fechaLike;
    }

    public void setFechaLike(Date fechaLike) {
        this.fechaLike = fechaLike;
    }

    public Mascota getLiker() {
        return liker;
    }

    public void setLiker(Mascota liker) {
        this.liker = liker;
    }

    public boolean isAlta() {
        return alta;
    }

    public void setAlta(boolean alta) {
        this.alta = alta;
    }

    public Mascota getLiked() {
        return liked;
    }

    public void setLiked(Mascota liked) {
        this.liked = liked;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }
    
    
}