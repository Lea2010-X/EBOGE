package modelo.cartas.propia;

import modelo.Partida;
import modelo.cartas.Carta;
import modelo.cartas.EfectoSinObjetivo;
import modelo.cartas.TipoCarta;
import modelo.jugador.Jugador;

public class Flash extends Carta implements EfectoSinObjetivo{

	public Flash() {
		super("Flash", TipoCarta.PROPIA,"/imagenes/Cartas/Flash.png");
	}

	@Override
	public void aplicar(Partida partida) {
	    Jugador jugadorActivo = partida.getJugadorActual();
	    int carasDado = partida.getDado().getCaras();

	    int avance = (int) Math.ceil(carasDado / 3.0);


	    partida.moverPorDado(jugadorActivo, avance);
	}


}
