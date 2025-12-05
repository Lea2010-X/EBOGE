package modelo.cartas.blanco;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.Partida;
import modelo.jugador.ColorJugador;
import modelo.jugador.Jugador;
import modelo.mapa.Casilla;
import modelo.mapa.Mapa;
import modelo.mapa.TipoCasilla;

class PlegariaTest {

	private Partida partida;
	private Jugador jugadorObjetivo;
	private Plegaria carta; 
	
	private List<Jugador> jugadores; 
	
	private static final int CARAS_DADO_PAR = 6;
	private static final int CARAS_DADO_IMPAR = 5;
	
	@BeforeEach
	void setUp(){
		Jugador jugadorInicial = new Jugador("Jugador uno", ColorJugador.AMARILLO);
		jugadorObjetivo = new Jugador("Jugador objetivo", ColorJugador.NEGRO);
		jugadores = new ArrayList<>();
		
		jugadores.add(jugadorInicial);
		jugadores.add(jugadorObjetivo);
		
		Casilla[][] casillas = new Casilla[50][1];
        for(int i=0; i<50; i++) casillas[i][0] = new Casilla(TipoCasilla.NORMAL, i);
        Mapa mapa = new Mapa(1, 50, casillas);
        
		partida = new Partida(jugadores, CARAS_DADO_PAR, mapa);
		carta = new Plegaria();
	}
	
	@Test
	public void testAvanceConDadoPar() {
		assertEquals(0, jugadorObjetivo.getPosicion());
		
		carta.aplicar(jugadorObjetivo, partida);
		
		assertEquals(3, jugadorObjetivo.getPosicion());
	}
	
	@Test
	public void testAvanceConDadoImpar() {				
		Partida partidaConDadoImpar = new Partida(jugadores, CARAS_DADO_IMPAR, partida.getMapa());
		carta.aplicar(jugadorObjetivo, partidaConDadoImpar);
		
		assertEquals(3, jugadorObjetivo.getPosicion());
	}
}
