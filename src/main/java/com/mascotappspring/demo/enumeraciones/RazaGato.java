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
public enum RazaGato {
    NS_NC(0, "Desconocida"),
    NO_ESPECIFICADA(1,"No especificada"),
    AZUL_RUSO(2, "Azul Ruso"),
    SIAMÉS(3,"Siamés"),
    SIBERIANO(4,"Siberiano"),
    BENGALÍ(5,"Bengalí"),
    PERSA(6,"Persa");


    private int numRaza;
    private String nameRaza;

    //Constructor
    RazaGato(int numRaza, String nameRaza) {
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
