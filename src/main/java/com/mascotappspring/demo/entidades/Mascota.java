
package com.mascotappspring.demo.entidades;

import com.mascotappspring.demo.enumeraciones.Color;
import com.mascotappspring.demo.enumeraciones.Especie;
import com.mascotappspring.demo.enumeraciones.Genero;
import com.sun.istack.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Gonzalo
 */
@Entity
@Table(name = "mascota")

public class Mascota {

//    ATRIBUTOS
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String id;
    @ManyToOne()
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
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
    @ManyToMany(mappedBy = "mascotas")
    private List<Par> pares = new ArrayList<Par>();

    public Mascota() {
    }

    public Mascota(String id, Usuario usuario, Especie especie, String nombre, String apodo, int energía, Date fechaAlta, Date fechaNacimiento, Date fechaUltimaModificacion, Boolean altaMascota, Genero gen, Color col) {
        this.id = id;
        this.usuario = usuario;
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
//        this.yuntas = yuntas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

//    public List<Yunta> getYuntas() {
//        return yuntas;
//    }
//
//    public void setPares(List<Yunta> yuntas) {
//        this.yuntas = yuntas;
//    }
    
}