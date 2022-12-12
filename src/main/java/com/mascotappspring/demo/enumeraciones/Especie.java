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
public enum Especie {
    PERRO("perro", 1),
    GATO("gato", 2),
    TORTUGA("tortuga", 3),
    AVE("ave", 4),
    ROEDOR("roedor",5),
    NS_NC("Indefinida", 6);

    String nameSpe;
    int numSpe;
    Especie(String nameSpe, int numSpe) {
        this.nameSpe = nameSpe;
        this.numSpe = numSpe;
    }

    public String getNameSpe() {
        return nameSpe;
    }

    public int getNumSpe() {
        return numSpe;
    }
}