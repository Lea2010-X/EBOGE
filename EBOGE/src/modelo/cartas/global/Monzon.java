package modelo.cartas.global;

import java.util.List;

import modelo.Partida;
import modelo.cartas.Carta;
import modelo.cartas.EfectoSinObjetivo;
import modelo.cartas.TipoCarta;
import modelo.jugador.Jugador;

public class Monzon extends Carta implements EfectoSinObjetivo{
	
	public Monzon() {
        super("Monzon", TipoCarta.GLOBAL, "/imagenes/Cartas/Monzon.png");
    }

	@Override
	public void aplicar(Partida partida) {

        List<Jugador> jugadores = partida.getJugadores();
    	int carasDado=partida.getDado().getCaras();
       	int retroceso = (int) Math.ceil(carasDado / 2.0);
       	
        for (Jugador jugador : jugadores) {
            jugador.retroceder(retroceso);
        }
	}    
}
