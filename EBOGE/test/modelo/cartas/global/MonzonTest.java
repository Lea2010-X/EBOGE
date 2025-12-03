package modelo.cartas.global;

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

class MonzonTest {

	private Partida partida;
    private Jugador primerJugador;
    private Jugador segundoJugador;
    private Monzon carta;

    @BeforeEach
    void setUp() {
        primerJugador = new Jugador("A", ColorJugador.ROJO);
        segundoJugador = new Jugador("B", ColorJugador.AZUL);
        
        List<Jugador> lista = new ArrayList<>();
        lista.add(primerJugador);
        lista.add(segundoJugador);

        Casilla[][] casillas = new Casilla[50][1];
        for(int i=0; i<50; i++) casillas[i][0] = new Casilla(TipoCasilla.NORMAL, i);
        Mapa mapa = new Mapa(1, 50, casillas);

        // Dado de 6 caras -> Retroceso de 3
        partida = new Partida(lista, 6, mapa);
        carta = new Monzon();
    }

    @Test
    void testRetrocesoMasivo() {
        // Colocamos a los jugadores lejos del inicio
        partida.moverPorCarta(primerJugador, 10);
        partida.moverPorCarta(segundoJugador, 20);

        carta.aplicar(partida);

        assertEquals(7, primerJugador.getPosicion());
        assertEquals(17, segundoJugador.getPosicion());
    }
}
