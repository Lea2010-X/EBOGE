package modelo.cartas.blanco;

import java.util.Random;

import modelo.Partida;
import modelo.cartas.Carta;
import modelo.cartas.EfectoConObjetivo;
import modelo.cartas.TipoCarta;
import modelo.jugador.EfectoStun;
import modelo.jugador.Jugador;

public class Plegaria extends Carta implements EfectoConObjetivo{
	
	
	public Plegaria() {
        super("plegaria", TipoCarta.BLANCO, "/imagenes/Cartas/Plegaria.png");
    }

	@Override
	public void aplicar(Jugador jugadorObjetivo, Partida partida) {
		
		 int carasDado=partida.getDado().getCaras();
		 int avance = (int) Math.ceil(carasDado / 2.0);
		 int casillasTotales = partida.getTotalCasillas();
		 jugadorObjetivo.avanzar(avance, casillasTotales);

	}
}
