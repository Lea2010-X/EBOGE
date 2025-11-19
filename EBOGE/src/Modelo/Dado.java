package com.example;

import java.util.Random;


public class Dado {

    private final int caras;
    private final Random random;

   
    public Dado(int caras) {
        
        if (caras < 3 || caras > 30) {
            throw new IllegalArgumentException("El número de caras debe estar entre 3 y 30.");
        }
        this.caras = caras;
        this.random = new Random();
    }

    
    public int lanzar() {
        
        return random.nextInt(this.caras) + 1;
    }

    
    public int getCaras() {
        return this.caras;
    }
}
