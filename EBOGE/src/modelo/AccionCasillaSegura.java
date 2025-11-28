package modelo;

import modelo.jugador.Jugador;

public class AccionCasillaSegura implements AccionCasilla{

	@Override
	public void ejecutar(Partida partida, Jugador jugadorActual) {
		//Simplemente no hacemos nada
		System.out.println("Casilla segura no ocurre nada");
		
	}

}
