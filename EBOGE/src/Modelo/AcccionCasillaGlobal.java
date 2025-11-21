package Modelo;

public class AcccionCasillaGlobal implements AccionCasilla {

	@Override
	public void ejecutar(Partida partida, Jugador jugadorActual) {
		
		 Mazo mazoPropias = partida.getMazo(TipoCarta.GLOBAL);
		 
		 if (mazoPropias != null && !mazoPropias.vacio()) {
	            Carta carta = mazoPropias.robar();          
	            ((EfectoSinObjetivo) carta).aplicar(partida); 
	        }
		 
		
	}

}
