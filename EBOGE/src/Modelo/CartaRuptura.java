package Modelo;

import java.util.List;

public class CartaRuptura extends Carta implements EfectoSinObjetivo{
	
	public CartaRuptura(String rutaImagen) {
        super("Ruptura", TipoCarta.GLOBAL, rutaImagen);
    }

	@Override
	public void aplicar(Partida partida) {

        List<Jugador> jugadores = partida.getJugadores();
        
        for (Jugador jugador : jugadores) {
            jugador.retroceder(5);
        }
	}    
}
