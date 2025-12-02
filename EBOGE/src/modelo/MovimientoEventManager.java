package modelo;

import java.util.ArrayList;
import java.util.List;

import modelo.jugador.Jugador;

public class MovimientoEventManager {

    private final List<MovimientoListener> listeners = new ArrayList<>();

    public void suscribir(MovimientoListener listener) {
        listeners.add(listener);
    }

    public void desuscribir(MovimientoListener listener) {
        listeners.remove(listener);
    }

    public void notificar(Jugador jugador, int posicionAnterior, int nuevaPosicion) {
        for (MovimientoListener listener : listeners) {
            listener.movimiento(jugador, posicionAnterior, nuevaPosicion);
        }
    }
}
