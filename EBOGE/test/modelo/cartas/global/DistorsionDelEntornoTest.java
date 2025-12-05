package modelo.cartas.global;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

class DistorsionDelEntornoTest {

    private Partida partida;
    private Jugador jugadorUno;
    private Jugador jugadorDos;
    private DistorsionDelEntorno carta;
    
    private static final int CARAS_DADO = 6;

    @BeforeEach
    void setUp() {
        jugadorUno = new Jugador("A", ColorJugador.ROJO);
        jugadorDos = new Jugador("B", ColorJugador.AZUL);
        
        List<Jugador> lista = new ArrayList<>();
        lista.add(jugadorUno);
        lista.add(jugadorDos);

        // Se crea un mapa donde solo la casilla 15 es normal 
        Casilla[][] casillas = new Casilla[50][1];
        for(int i=0; i<50; i++) {
            if (i == 15) {
                casillas[i][0] = new Casilla(TipoCasilla.NORMAL, i);
            } else {
                casillas[i][0] = new Casilla(TipoCasilla.TRAMPA, i);
            }
        }
        Mapa mapa = new Mapa(1, 50, casillas);

        partida = new Partida(lista, CARAS_DADO, mapa);
        carta = new DistorsionDelEntorno();
    }

    @Test
    void testReubicacionEnCasillasNormales() {
        assertEquals(0, jugadorUno.getPosicion());
        assertEquals(0, jugadorDos.getPosicion());

        // Los mandara todos a la casilla 15
        carta.aplicar(partida);

        assertEquals(15, jugadorUno.getPosicion(), "Debió moverse a la unica casilla normal disponible");
        assertEquals(15, jugadorDos.getPosicion(), "Debió moverse a la unica casilla normal disponible");
    }
}