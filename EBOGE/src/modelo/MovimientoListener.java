package modelo;

import modelo.jugador.Jugador;

public interface MovimientoListener {
	void movimiento(Jugador jugador, int posicionAnterior, int nuevaPosicion);
}
