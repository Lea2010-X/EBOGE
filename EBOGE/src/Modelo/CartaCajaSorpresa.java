package Modelo;

public class CartaCajaSorpresa extends Carta implements EfectoSinObjetivo{
	
	public CartaCajaSorpresa(String rutaImagen) {
        super("Caja Sorpresa", TipoCarta.MAESTRA, rutaImagen);
    }

	@Override
	public void aplicar(Partida partida) {
		Mapa mapa = partida.getMapa();
		Jugador jugadorActivo = partida.getJugadorActual();
		
        int posActual = jugadorActivo.getPosicion();
        
        int posAnterior = posActual - 1;
        
        // VALIDACIÓN: Solo aplicamos si no estamos en la casilla siguiente al inicio (para no sobrescribir la casilla del inicio)
        if (posAnterior >= 1 && mapa.identificarTipoDeCasilla(posAnterior) != TipoCasilla.TRAMPA) {        
            mapa.modificarTipoDeCasilla(posAnterior, TipoCasilla.TRAMPA); 
            
        } else {
            System.out.println("No se puede poner una trampa en el inicio.");
        }
	}
	
	
	
	

}
