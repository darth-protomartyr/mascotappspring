/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mascotappspring.demo.enumeraciones;

/**
 *
 * @author Gonzalo
 */
public enum Raza {
    
    PEKINES(1,"Pekinés", 1),
    BEAGLE(2, "Beagle", 1),
    BORDER_COLLIE(3, "Border Collie", 1),
    BOXER(4, "Boxer", 1),
    BULL_TERRIER(5, "Bull Terrier", 1),
    BULLDOG_AMERICANO(6, "Bulldog inglés", 1),
    BULLDOG_FRANCÉS(07, "Bulldog francés", 1),
    BULLMASTIFF(8, "Bullmastiff", 1),
    CANICHE_TOY(9, "Caniche toy", 1),
    CHIHUAHUA(10, "Chuihuahua", 1),
    CHOW_CHOW(11, "Chow Chow", 1),
    COCKER_SPANIEL(12, "Cocker Spaniel", 1),
    DOBERMAN(14, "Doberman", 1),
    DOGO_ARGENTINO(15, "Dogo argentino", 1),
    DOGO_DE_BURDEOS(16, "Dogo de Burdeos", 1),
    DÁLMATA(13, "Dalmata", 1),
    FILA_BRASILEIRO(17, "Fila Brasilero", 1),
    FOX_TERRIER(18, "Fox Terrier", 1),
    GALGO(19, "Galgo", 1),
    GOLDEN_RETRIEVER(20, "Golden Retriever", 1),
    GRAN_DANÉS(21, "Gran danés", 1),
    HUSKY_SIBERIANO(22, "Husky Siberiano", 1),
    MASTÍN_NAPOLITANO(23, "Mastín napolitano", 1),
    OVEJERO_ALEMÁN(24, "Ovejero Alemán", 1),
    ROTTWEILER(25, "Rottweiller", 1),
    SAN_BERNARDO(26, "San Bernardo", 1),
    SETTER_IRLANDÉS(27, "Setter irladés", 1),
    SHAR_PEI(28, "Shar Pei", 1),
    VIEJO_PASTOR_INGLÉS(29, "Viejo Pastor", 1),
    AKITA(30, "Akita", 1),
    OTRA_PERRO(31, "Otra", 1),
//  Razas Felinas
    PERSA(32,"Persa", 2),
    AZUL_RUSO(33, "Azul Ruso", 2),
    SIAMÉS(34,"Siamés", 2),
    SIBERIANO(35,"Siberiano", 2),
    BENGALÍ(36,"Bengalí", 2),
    OTRA_GATO(37, "Otra", 2),
//  Otros
    OTRA_INDISTINTA(38,"Otra",3);
    
    



    //Atributos
    private int razaId;
    private String razaName;
    private int esp;

    //Constructor
    Raza(int razaId, String razaName, int esp) {
        this.razaName = razaName;
        this.razaId = razaId;
        this.esp = esp;
    }

    //getters

    public int getRazaId() {
        return razaId;
    }

    public String getRazaName() {
        return razaName;
    }

    public int getEsp() {
        return esp;
    }

}