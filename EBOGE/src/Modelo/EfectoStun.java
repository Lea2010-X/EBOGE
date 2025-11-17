package com.example;

/**
 * (Patrón Strategy)
 *
 * Implementa un "Efecto de Inmovilización" (Stun).
 * Altera el estado del Jugador (Patrón State).
 */
public class EfectoStun implements EfectoJugador {

    
    private int turnosRestantes;
    private boolean haExpirado;

    /**
     * Construye un efecto de Stun (inmovilización).
     *
     * @param turnos El número de turnos que durará el efecto (1 a 3).
     */
    public EfectoStun(int turnos) {
        // Validación (1 <= n <= 3)
        this.turnosRestantes = Math.max(1, Math.min(turnos, 3));
        this.haExpirado = false;
    }

    /**
     * Al aplicarse, cambia el estado del jugador a INMOVILIZADO.
     */
    @Override
    public void aplicarEfectoInicial(Jugador jugador) {
        jugador.setEstado(EstadoJugador.INMOVILIZADO);
    }

    /**
     * Al inicio de cada turno, reduce su duración.
     */
    @Override
    public void actualizarEfectoPorTurno(Jugador jugador) {
        if (this.haExpirado) {
            return;
        }

        this.turnosRestantes--;

        if (this.turnosRestantes <= 0) {
            this.haExpirado = true;
        }
    }

    /**
     * Al expirar, devuelve el estado del jugador a NORMAL.
     * Realiza una comprobación de seguridad para no quitar el estado
     * si otro efecto de Stun sigue activo.
     */
    @Override
    public void removerEfecto(Jugador jugador) {
        // Contamos si hay otro efecto de Stun activo
        long otrosStunsActivos = jugador.getEfectosActivos().stream()
                .filter(efecto -> efecto instanceof EfectoStun)
                .filter(efecto -> !efecto.equals(this))
                .count();

        // Solo revierte el estado si no hay otros stuns
        if (otrosStunsActivos == 0) {
            jugador.setEstado(EstadoJugador.NORMAL);
        }
    }

    /**
     * Informa al Jugador si debe ser removido de la lista.
     */
    @Override
    public boolean haExpirado() {
        return this.haExpirado;
    }

    @Override
    public String getNombre() {
        return "Inmovilizado";
    }
}