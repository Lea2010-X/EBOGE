package modelo.cartas.blanco;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

class BoogieWoogieTest {

    private Partida partida;
    private Jugador jugadorActivo;
    private Jugador jugadorObjetivo;
    private BoogieWoogie carta;
    
    private static final int CARAS_DADO = 6;

    @BeforeEach
    void setUp() {
        jugadorActivo = new Jugador("Jugador A", ColorJugador.AZUL);
        jugadorObjetivo = new Jugador("Jugador B", ColorJugador.ROJO);

        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugadorActivo);  
        jugadores.add(jugadorObjetivo);

        // Esto es suficiente porque BoogieWoogie solo necesita saber el total de casillas.
        int filas = 20;
        int columnas = 1;
        Casilla[][] casillas = new Casilla[filas][columnas];
        
        for (int i = 0; i < filas; i++) {
            casillas[i][0] = new Casilla(TipoCasilla.NORMAL, i);
        }

        Mapa mapa = new Mapa(columnas, filas, casillas);

        partida = new Partida(jugadores, CARAS_DADO, mapa);

        carta = new BoogieWoogie();
    }

    @Test
    @DisplayName("Debe intercambiar posiciones cuando el objetivo está adelante")
    void testIntercambioObjetivoAdelante() {
        // Movemos manualmente a los jugadores para que no estén en 0     
        partida.moverPorCarta(jugadorActivo, 5); 
        partida.moverPorCarta(jugadorObjetivo, 12); 

        assertEquals(5, jugadorActivo.getPosicion());
        assertEquals(12, jugadorObjetivo.getPosicion());

        carta.aplicar(jugadorObjetivo, partida);

        assertEquals(12, jugadorActivo.getPosicion(), "El jugador activo debió tomar la posición del objetivo");
        assertEquals(5, jugadorObjetivo.getPosicion(), "El objetivo debió tomar la posición del activo");
    }

    @Test
    @DisplayName("Debe intercambiar posiciones cuando el objetivo está atrás")
    void testIntercambioObjetivoAtras() {
        partida.moverPorCarta(jugadorActivo, 15);

        partida.moverPorCarta(jugadorObjetivo, 3);

        carta.aplicar(jugadorObjetivo, partida);

        assertEquals(3, jugadorActivo.getPosicion());
        assertEquals(15, jugadorObjetivo.getPosicion());
    }

    @Test
    @DisplayName("Si ambos están en la misma casilla, se quedan igual")
    void testMismaPosicion() {
        partida.moverPorCarta(jugadorActivo, 8);
        partida.moverPorCarta(jugadorObjetivo, 8);

        carta.aplicar(jugadorObjetivo, partida);

        assertEquals(8, jugadorActivo.getPosicion());
        assertEquals(8, jugadorObjetivo.getPosicion());
    }
}