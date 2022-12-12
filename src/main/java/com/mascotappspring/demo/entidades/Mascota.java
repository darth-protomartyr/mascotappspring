
package com.mascotappspring.demo.entidades;

import com.mascotappspring.demo.enumeraciones.Color;
import com.mascotappspring.demo.enumeraciones.Especie;
import com.mascotappspring.demo.enumeraciones.Genero;
import com.sun.istack.NotNull;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Gonzalo
 */
@Entity
public class Mascota {

//    ATRIBUTOS
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String id;
    @NotNull
    @Enumerated(EnumType.STRING)
    protected Especie especie;
    @NotNull
    protected String nombre;
    protected String apodo;
    protected int energía;
    @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaAlta;
    @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaNacimiento;
    @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaUltimaModificacion;
    protected Boolean altaMascota;
    @Enumerated(EnumType.STRING)
    protected Genero gen;
    @Enumerated(EnumType.STRING)
    protected com.mascotappspring.demo.enumeraciones.Color col;
    @ManyToOne
    protected Usuario user;
    
    public Mascota() {
    }

    public Mascota(String id, Especie especie, String nombre, String apodo, int energía, Date fechaAlta, Date fechaNacimiento, Date fechaUltimaModificacion, Boolean altaMascota, Genero gen, Color col, Usuario user) {
        this.id = id;
        this.especie = especie;
        this.nombre = nombre;
        this.apodo = apodo;
        this.energía = energía;
        this.fechaAlta = fechaAlta;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaUltimaModificacion = fechaUltimaModificacion;
        this.altaMascota = altaMascota;
        this.gen = gen;
        this.col = col;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public int getEnergía() {
        return energía;
    }

    public void setEnergía(int energía) {
        this.energía = energía;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public Boolean getAltaMascota() {
        return altaMascota;
    }

    public void setAltaMascota(Boolean altaMascota) {
        this.altaMascota = altaMascota;
    }

    public Genero getGen() {
        return gen;
    }

    public void setGen(Genero gen) {
        this.gen = gen;
    }

    public Color getCol() {
        return col;
    }

    public void setCol(Color col) {
        this.col = col;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}