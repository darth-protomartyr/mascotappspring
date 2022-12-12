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
public enum Color {
    BLANCO(1, "Blanco"),
    NEGRO(2, "Negro"),
    MARRON(3,"Marrón"),
    FUEGO(4,"Fuego"),
    AZUL(5,"Azul"),
    VERDE(6, "Verde"),
    AMARILLO(7, "Amarillo"),
    NARANJA(8, "Naranja"),
    GRIS(9, "Gris"),
    ROJO(10, "rojo"),
    VIOLETA(11, "violeta"),
    ROSA(12, "rosa"),
    CELESTE(13, "celeste"),
    NS_NC(14, "No sabe/No Contesta");

    private int numCol;
    private String nameCol;

    //Constructor
    Color(int numCol, String nameCol) {
        this.numCol = numCol;
        this.nameCol = nameCol;
    }

    //Getters
    public int getNumCol() {
        return numCol;
    }

    public String getNameCol() {
        return nameCol;
    }
}