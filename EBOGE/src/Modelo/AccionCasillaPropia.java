package Modelo;

public class AccionCasillaPropia implements AccionCasilla {
	
	//El mesero que sirve la comida 
    @Override
    public void ejecutar(Partida partida, Jugador jugadorActual) {
        Mazo mazoPropias = partida.getMazo(TipoCarta.PROPIA);
        
        if (mazoPropias != null && !mazoPropias.vacio()) {
            Carta carta = mazoPropias.robar();          
            ((EfectoSinObjetivo) carta).aplicar(partida); 
        }
    }
}