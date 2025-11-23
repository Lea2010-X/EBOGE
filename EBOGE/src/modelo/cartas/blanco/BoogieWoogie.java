package modelo.cartas.blanco;

import modelo.Partida;
import modelo.cartas.Carta;
import modelo.cartas.EfectoConObjetivo;
import modelo.cartas.TipoCarta;
import modelo.jugador.Jugador;

public class BoogieWoogie extends Carta implements EfectoConObjetivo{
	
	public BoogieWoogie() {
        super("Boogie Woogie", TipoCarta.BLANCO, "/imagenes/Cartas/BoogieWoogie.png");
    }

	@Override
	public void aplicar(Jugador jugadorObjetivo, Partida partida) {		
		Jugador jugadorActivo = partida.getJugadorActual();

        int posActivo = jugadorActivo.getPosicion();
        int posObjetivo = jugadorObjetivo.getPosicion();

        jugadorActivo.setPosicion(posObjetivo);
        jugadorObjetivo.setPosicion(posActivo);
        
        System.out.println(jugadorActivo.getNombre() + " se mueve a la casilla " + posObjetivo);
        System.out.println(jugadorObjetivo.getNombre() + " es arrastrado a la casilla " + posActivo);
	}
}
