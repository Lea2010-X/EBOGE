package modelo.cartas.propia;

import modelo.Partida;
import modelo.cartas.Carta;
import modelo.cartas.EfectoSinObjetivo;
import modelo.cartas.TipoCarta;
import modelo.jugador.EfectoStun;
import modelo.jugador.Jugador;

public class RegresoACasa extends Carta implements EfectoSinObjetivo{

	public RegresoACasa() {
		super("Regreso a casa", TipoCarta.PROPIA, "/imagenes/Cartas/RegresoACasa.png");
		
	}

    @Override
    public void aplicar(Partida partida) {
    	
    	Jugador jugadorActivo = partida.getJugadorActual();
    	jugadorActivo.retroceder(2);
    	jugadorActivo.aplicarEfecto(new EfectoStun(2));
    	
    }
}
