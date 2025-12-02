package modelo.cartas;

import modelo.Partida;
import modelo.jugador.Jugador;

public interface EfectoConObjetivo {
	void aplicar(Jugador jugadorObjetivo, Partida partida);
}
