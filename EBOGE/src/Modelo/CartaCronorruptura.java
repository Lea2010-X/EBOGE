package Modelo;

public class CartaCronorruptura extends Carta implements EfectoSinObjetivo{

	public CartaCronorruptura(String imagenRuta) {
		super("Cronorruptura", TipoCarta.PROPIA, imagenRuta);
		
	}

    @Override
    public void aplicar(Partida partida) {
    		Jugador jugadorActivo = partida.getJugadorActual();
    		
        int movimientoPrevio = jugadorActivo.getUltimoMovimiento();           
        jugadorActivo.retroceder(movimientoPrevio);
    }
}
