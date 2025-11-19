package Modelo;

public class CartaMiedoDeUltratumba extends Carta implements EfectoConObjetivo{
	
	public CartaMiedoDeUltratumba(String rutaArchivo) {
        super("Miedo de Ultratumba", TipoCarta.BLANCO, rutaArchivo);
    }

	@Override
	public void aplicar(Jugador jugadorObjetivo, Partida partida) {		
		Jugador jugadorActivo = partida.getJugadorActual();

        int posActivo = jugadorActivo.getPosicion();
        int posObjetivo = jugadorObjetivo.getPosicion();

        // Intercambiamos posiciones usando el setter de Jugador
        jugadorActivo.setPosicion(posObjetivo);
        jugadorObjetivo.setPosicion(posActivo);
        
        System.out.println(jugadorActivo.getNombre() + " se mueve a la casilla " + posObjetivo);
        System.out.println(jugadorObjetivo.getNombre() + " es arrastrado a la casilla " + posActivo);
	}
}
