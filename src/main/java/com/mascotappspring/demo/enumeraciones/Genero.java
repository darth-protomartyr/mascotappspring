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
public enum Genero {
    HOMBRE("Hombre", "Macho", 1),
    MUJER("Mujer", "Hembra", 2),
    OTRO("Otro", "Otro", 3);
    String genH;
    String genM;
    int id;
    private Genero(String genH, String genM, int id) {
        this.genH = genH;
        this.genM = genM;
        this.id = id;
    }

    public String getGenH() {
        return genH;
    }
    
        public String getGenM() {
        return genM;
    }
    
    public int getId() {
        return id;
    }
}
