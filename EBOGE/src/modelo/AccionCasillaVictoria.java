package modelo;

import modelo.jugador.Jugador;

public class AccionCasillaVictoria implements AccionCasilla{

	@Override
    public void ejecutar(Partida partida, Jugador jugador) {
        partida.setJuegoTerminado(true);
    }

}
