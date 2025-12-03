package modelo.cartas.maestra;

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

class GrilletesDelAlmaTest {

    private Partida partida;
    private Jugador activo, anterior, siguiente, lejano;
    private GrilletesDelAlma carta;
    private Mapa mapa;

    @BeforeEach
    void setUp() {
        activo = new Jugador("Activo", ColorJugador.ROJO);
        anterior = new Jugador("Anterior", ColorJugador.AZUL);
        siguiente = new Jugador("Siguiente", ColorJugador.VERDE);
        lejano = new Jugador("Lejano", ColorJugador.AMARILLO);

        List<Jugador> lista = new ArrayList<>();
        lista.add(activo);
        lista.add(anterior);
        lista.add(siguiente);
        lista.add(lejano);

        Casilla[][] casillas = new Casilla[50][1];
        for(int i=0; i<50; i++) casillas[i][0] = new Casilla(TipoCasilla.NORMAL, i);
        mapa = new Mapa(1, 50, casillas);

        partida = new Partida(lista, 6, mapa);
        carta = new GrilletesDelAlma();
    }

    @Test
    @DisplayName("Debe afectar al jugador inmediato anterior y posterior")
    void testGrilletesLogica() {

        partida.moverPorCarta(activo, 20);
        partida.moverPorCarta(anterior, 15);
        partida.moverPorCarta(lejano, 5);         
        partida.moverPorCarta(siguiente, 25);

        carta.aplicar(partida);
       
        // Verificamos que la casilla del jugador siguiente se transformó en una trampa
        assertEquals(TipoCasilla.TRAMPA, mapa.identificarTipoDeCasilla(25), 
                "La casilla del jugador de adelante debió volverse TRAMPA");

        assertEquals(TipoCasilla.NORMAL, mapa.identificarTipoDeCasilla(15), 
                "La casilla del jugador de atrás no debió cambiar");
     
        assertEquals(TipoCasilla.NORMAL, mapa.identificarTipoDeCasilla(5));       
    }
}