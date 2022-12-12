/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascotappspring.demo.entidades;


import com.mascotappspring.demo.entidades.Foto;
import com.sun.istack.NotNull;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 *
 * @author Gonzalo
 */
@Entity
public class Usuario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @NotNull
    private String nombre;
    private String apellido;
    private String dni;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @NotNull
    private String pass;
    @NotNull
    @Column(unique = true)
    private String mail;
    @Enumerated(EnumType.STRING)
    private com.mascotappspring.demo.enumeraciones.Genero genero;
    @Enumerated(EnumType.STRING)
    private com.mascotappspring.demo.enumeraciones.Rol rol;
    private Boolean solicitudBaja;
    private Boolean penalidad;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPenalidad;
    @OneToOne
    private com.mascotappspring.demo.entidades.Foto foto;
    private Boolean alta;
    public Usuario() {
    }

    public Usuario(String id, String nombre, String apellido, String dni, Date fechaNacimiento, String pass, String mail, com.mascotappspring.demo.enumeraciones.Genero genero, com.mascotappspring.demo.enumeraciones.Rol rol, Boolean solicitudBaja, Boolean penalidad, Date fechaPenalidad, Foto foto, Boolean alta) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.pass = pass;
        this.mail = mail;
        this.genero = genero;
        this.rol = rol;
        this.solicitudBaja = solicitudBaja;
        this.penalidad = penalidad;
        this.fechaPenalidad = fechaPenalidad;
        this.foto = foto;
        this.alta = alta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public com.mascotappspring.demo.enumeraciones.Genero getGenero() {
        return genero;
    }

    public void setGenero(com.mascotappspring.demo.enumeraciones.Genero genero) {
        this.genero = genero;
    }

    public com.mascotappspring.demo.enumeraciones.Rol getRol() {
        return rol;
    }

    public void setRol(com.mascotappspring.demo.enumeraciones.Rol rol) {
        this.rol = rol;
    }

    public Boolean getSolicitudBaja() {
        return solicitudBaja;
    }

    public void setSolicitudBaja(Boolean solicitudBaja) {
        this.solicitudBaja = solicitudBaja;
    }

    public Boolean getPenalidad() {
        return penalidad;
    }

    public void setPenalidad(Boolean penalidad) {
        this.penalidad = penalidad;
    }

    public Date getFechaPenalidad() {
        return fechaPenalidad;
    }

    public void setFechaPenalidad(Date fechaPenalidad) {
        this.fechaPenalidad = fechaPenalidad;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

}