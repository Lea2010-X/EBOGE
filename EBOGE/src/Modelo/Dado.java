package Modelo;

import java.util.Random;

/**
 * Representa un dado configurable.
 */
public class Dado {

    private final int caras;
    private final Random random;

    /**
     * Construye un dado con un número específico de caras.
     *
     * @param caras El número de caras del dado (3 a 30).
     */
    public Dado(int caras) {
        
        if (caras < 3 || caras > 30) {
            throw new IllegalArgumentException("El número de caras debe estar entre 3 y 30.");
        }
        this.caras = caras;
        this.random = new Random();
    }

    /**
     * Simula el lanzamiento del dado.
     *
     * @return Un número aleatorio entre 1 y el número de caras (ambos inclusive).
     */
    public int lanzar() {
        
        return random.nextInt(this.caras) + 1;
    }

    /**
     * Obtiene el número de caras de este dado.
     *
     * @return El número de caras.
     */
    public int getCaras() {
        return this.caras;
    }
}
