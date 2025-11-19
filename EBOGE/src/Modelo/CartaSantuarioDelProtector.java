package Modelo;

public class CartaSantuarioDelProtector extends Carta implements EfectoSinObjetivo{
	
	public CartaSantuarioDelProtector(String rutaImagen) {
        super("Santuario del Protector", TipoCarta.MAESTRA, rutaImagen);
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
