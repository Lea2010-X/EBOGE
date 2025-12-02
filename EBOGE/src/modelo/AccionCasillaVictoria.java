package modelo;

import modelo.jugador.Jugador;

public class AccionCasillaVictoria implements AccionCasilla{

	@Override
    public void ejecutar(Partida partida, Jugador jugador) {
        System.out.println("¡JUGADOR " + jugador.getNombre() + " HA LLEGADO AL FINAL!");
        partida.setJuegoTerminado(true);
    }

}
