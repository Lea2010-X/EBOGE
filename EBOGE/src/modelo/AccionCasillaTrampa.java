package modelo;

import java.util.Random;

import modelo.jugador.EfectoStun;
import modelo.jugador.Jugador;

public class AccionCasillaTrampa implements AccionCasilla {

	private final Random random = new Random();
	
	@Override
	public void ejecutar(Partida partida, Jugador jugadorActual) {
		
		int duracion = random.nextInt(4);
		
        jugadorActual.aplicarEfecto(new EfectoStun(duracion));
        jugadorActual.setTurnosEfecto(duracion);
		
	}

}
