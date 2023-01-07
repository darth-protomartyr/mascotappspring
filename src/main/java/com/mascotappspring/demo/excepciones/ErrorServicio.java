package com.mascotappspring.demo.excepciones;
public class ErrorServicio extends Exception {
    public ErrorServicio(String msn) {
        super(msn);
    }
}