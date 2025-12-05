package modelo.cartas.propia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

class RegresoACasaTest {

    private Partida partida;
    private Jugador jugadorActivo;
    private RegresoACasa carta;

    @BeforeEach
    void setUp() {
        jugadorActivo = new Jugador("Italiano", ColorJugador.VERDE);
        List<Jugador> lista = new ArrayList<>();
        lista.add(jugadorActivo);

        Casilla[][] casillas = new Casilla[50][1];
        for (int i = 0; i < 50; i++) casillas[i][0] = new Casilla(TipoCasilla.NORMAL, i);
        Mapa mapa = new Mapa(1, 50, casillas);

        partida = new Partida(lista, 6, mapa);
        carta = new RegresoACasa();
    }

    @Test
    @DisplayName("Debe retroceder 2 casillas y aplicar STUN")
    void testRetrocesoYStun() {
        // Movemos al jugador a la casilla 10 para que pueda retroceder
        partida.moverPorCarta(jugadorActivo, 10);
        
        carta.aplicar(partida);

        assertEquals(8, jugadorActivo.getPosicion(), "El jugador debió retroceder 2 casillas");
        assertTrue(jugadorActivo.estaInmovilizado(), "El jugador debería estar inmovilizado");
    }
}
