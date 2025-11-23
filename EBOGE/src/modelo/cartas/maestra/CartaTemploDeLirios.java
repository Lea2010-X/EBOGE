package modelo.cartas.maestra;

import modelo.Partida;
import modelo.cartas.Carta;
import modelo.cartas.EfectoSinObjetivo;
import modelo.cartas.TipoCarta;
import modelo.jugador.Jugador;
import modelo.mapa.Mapa;
import modelo.mapa.TipoCasilla;

public class CartaTemploDeLirios extends Carta implements EfectoSinObjetivo{
	
	public CartaTemploDeLirios() {
        super("Templo de Lirios", TipoCarta.MAESTRA, "/imagenes/Cartas/TemploDeLirios.png");
    }

	@Override
	public void aplicar(Partida partida) {
		Mapa mapa = partida.getMapa();
		Jugador jugadorActivo = partida.getJugadorActual();
		
        int posSiguiente = jugadorActivo.getPosicion() + 1;
        
        if (validarCasilla(posSiguiente, mapa)) {                    	    
            mapa.modificarTipoDeCasilla(posSiguiente, TipoCasilla.PROPIA);
            
        } else {
            System.out.println("La carta falla... la casilla siguiente no es válida o ya es propia.");
        }
	}
	
	public boolean validarCasilla(int posicionSiguiente, Mapa mapa) {
		return (mapa.identificarTipoDeCasilla(posicionSiguiente) != TipoCasilla.FINAL && mapa.identificarTipoDeCasilla(posicionSiguiente) != TipoCasilla.PROPIA);
	}
}
