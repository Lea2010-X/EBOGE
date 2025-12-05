package modelo.cartas.propia;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import modelo.Partida;
import modelo.jugador.ColorJugador;
import modelo.jugador.Jugador;
import modelo.mapa.Casilla;
import modelo.mapa.Mapa;
import modelo.mapa.TipoCasilla;

class FlashTest {

	private Partida partida;
    private Jugador jugadorActivo;
    private Flash carta;

    @BeforeEach
    void setUp() {
        jugadorActivo = new Jugador("FlashMan", ColorJugador.AMARILLO);
        List<Jugador> lista = new ArrayList<>();
        lista.add(jugadorActivo);

        Casilla[][] casillas = new Casilla[50][1];
        for (int i = 0; i < 50; i++) casillas[i][0] = new Casilla(TipoCasilla.NORMAL, i);
        Mapa mapa = new Mapa(1, 50, casillas);

        partida = new Partida(lista, 6, mapa);
        carta = new Flash();
    }

    @Test
    @DisplayName("Flash avanza tercio del dado (6 caras avanza 2 pasos)")
    void testAvanceNormal() {
        assertEquals(0, jugadorActivo.getPosicion());

        carta.aplicar(partida);

        assertEquals(2, jugadorActivo.getPosicion(), "Con dado de 6 caras, debió avanzar 2 casillas");
    }

}
