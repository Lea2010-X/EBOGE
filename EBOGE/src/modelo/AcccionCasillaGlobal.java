package modelo;

import modelo.cartas.Carta;
import modelo.cartas.EfectoSinObjetivo;
import modelo.cartas.Mazo;
import modelo.cartas.TipoCarta;
import modelo.jugador.Jugador;

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
