package Modelo;

/**
 * (Patrón Strategy)
 * Define la "Estrategia de Efecto" que pueda
 * aplicarse a un Jugador. El Jugador usará esta interfaz
 * sin conocer la implementación concreta (ej. EfectoStun).
 */
public interface EfectoJugador {

    /**
     * Se llama una vez, al momento de aplicar el efecto al jugador.
     * Aquí es donde se debe cambiar el estado del jugador si es necesario.
     *
     * @param jugador El jugador que recibe el efecto.
     */
    void aplicarEfectoInicial(Jugador jugador);

    /**
     * Se llama al inicio de cada turno del jugador.
     * Gestiona la lógica por turno y la duración.
     *
     * @param jugador El jugador que tiene el efecto.
     */
    void actualizarEfectoPorTurno(Jugador jugador);

    /**
     * Se llama una vez, cuando el efecto expira y va a ser removido.
     * Aquí es donde se debe revertir el estado del jugador.
     *
     * @param jugador El jugador del que se remueve el efecto.
     */
    void removerEfecto(Jugador jugador);

    /**
     * Comprueba si el efecto ya ha expirado (ej. sus turnos llegaron a 0).
     *
     * @return true si el efecto debe ser removido, false en caso contrario.
     */
    boolean haExpirado();

    /**
     * Obtiene el nombre descriptivo del efecto.
     *
     * @return El nombre del efecto (ej. "Inmovilizado").
     */
    String getNombre();
}