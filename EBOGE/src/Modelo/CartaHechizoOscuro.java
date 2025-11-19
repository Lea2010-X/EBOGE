package Modelo;

public class CartaHechizoOscuro extends Carta implements EfectoSinObjetivo{

	public CartaHechizoOscuro(String imagenRuta) {
		super("Hechizo oscuro", TipoCarta.PROPIA, imagenRuta);
	}

	@Override
    public void aplicar(Partida partida) {
       Dado dado = partida.getDado();
       Jugador jugadorActivo = partida.getJugadorActual();
       
       int tiradaExtra = dado.lanzar();
       
       int casillasTotales = partida.getTotalCasillas();
       jugadorActivo.avanzar(tiradaExtra, casillasTotales);    
    }

}
