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
public enum RazaPerro {
    NS_NC(0, "Desconocida"),
    NO_ESPECIFICADA(1,"No especificada"),
    BEAGLE(02, "Beagle"),
    BORDER_COLLIE(3, "Border Collie"),
    BOXER(4, "Boxer"),
    BULL_TERRIER(5, "Bull Terrier"),
    BULLDOG_AMERICANO(6, "Bulldog inglés"),
    BULLDOG_FRANCÉS(07, "Bulldog francés"),
    BULLMASTIFF(8, "Bullmastiff"),
    CANICHE_TOY(9, "Caniche toy"),
    CHIHUAHUA(10, "Chuihuahua"),
    CHOW_CHOW(11, "Chow Chow"),
    COCKER_SPANIEL(12, "Cocker Spaniel"),
    DOBERMAN(14, "Doberman"),
    DOGO_ARGENTINO(15, "Dogo argentino"),
    DOGO_DE_BURDEOS(16, "Dogo de Burdeos"),
    DÁLMATA(13, "Dalmata"),
    FILA_BRASILEIRO(17, "Fila Brasilero"),
    FOX_TERRIER(18, "Fox Terrier"),
    GALGO(19, "Galgo"),
    GOLDEN_RETRIEVER(20, "Golden Retriever"),
    GRAN_DANÉS(21, "Gran danés"),
    HUSKY_SIBERIANO(22, "Husky Siberiano"),
    MASTÍN_NAPOLITANO(23, "Mastín napolitano"),
    OVEJERO_ALEMÁN(24, "Ovejero Alemán"),
    ROTTWEILER(25, "Rottweiller"),
    SAN_BERNARDO(26, "San Bernardo" ),
    SETTER_IRLANDÉS(27, "Setter irladés"),
    SHAR_PEI(28, "Shar Pei"),
    VIEJO_PASTOR_INGLÉS(29, "Viejo Pastor"),
    AKITA(30, "Akita");



    //Atributos
    private int numRaza;
    private String nameRaza;

    //Constructor
    RazaPerro(int numRaza, String nameRaza) {
        this.nameRaza = nameRaza;
        this.numRaza = numRaza;
    }

    //getters
    public int getNumRaza() {
        return numRaza;
    }
    public String getNameRaza() {
        return nameRaza;
    }
}