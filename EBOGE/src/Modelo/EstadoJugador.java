package com.example;

/**
 * (Patrón State)
 * Representa los estados discretos en los que puede estar un Jugador.
 * El comportamiento del jugador puede depender de este estado.
 *
 */
public enum EstadoJugador {
    /**
     * El jugador puede moverse y actuar normalmente.
     */
    NORMAL,

    /**
     * El jugador no puede moverse (está "stuneado").
     */
    INMOVILIZADO
    
    // Futuros estados
}
