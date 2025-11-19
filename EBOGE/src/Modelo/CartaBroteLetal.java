package Modelo;

import java.util.Random;

public class CartaBroteLetal extends Carta implements EfectoConObjetivo{
	
	private Random random = new Random();
	
	public CartaBroteLetal(String rutaImagen) {
        super("Brote Letal", TipoCarta.BLANCO, rutaImagen);
    }

	@Override
	public void aplicar(Jugador jugadorObjetivo, Partida partida) {
		
		int turnosDeStun = random.nextInt(3) + 1;
		
		jugadorObjetivo.aplicarEfecto(new EfectoStun(turnosDeStun));
		// Futuro requisito: hacer que el efecto Stun siga hasta que el 
		// jugador saque un número de dado par
	}
}
