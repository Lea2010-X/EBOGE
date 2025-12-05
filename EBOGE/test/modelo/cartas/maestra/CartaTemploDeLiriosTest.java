package modelo.cartas.maestra;

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

class CartaTemploDeLiriosTest {

    private Partida partida;
    private Jugador jugadorActivo;
    private CartaTemploDeLirios carta;
    private Mapa mapa;

    @BeforeEach
    void setUp() {
        jugadorActivo = new Jugador("Constructor", ColorJugador.BLANCO);
        List<Jugador> lista = new ArrayList<>();
        lista.add(jugadorActivo);

        Casilla[][] casillas = new Casilla[50][1];
        for(int i=0; i<50; i++) casillas[i][0] = new Casilla(TipoCasilla.NORMAL, i);
        mapa = new Mapa(1, 50, casillas);

        partida = new Partida(lista, 6, mapa);
        carta = new CartaTemploDeLirios();
    }

    @Test
    @DisplayName("Debe convertir la casilla siguiente en PROPIA")
    void testConvierteCasillaNormal() {
        partida.moverPorCarta(jugadorActivo, 10);
        
        // Verificamos que la 11 sea NORMAL
        assertEquals(TipoCasilla.NORMAL, mapa.identificarTipoDeCasilla(11));

        carta.aplicar(partida);

        // La 11 ahora debe ser PROPIA
        assertEquals(TipoCasilla.PROPIA, mapa.identificarTipoDeCasilla(11), 
                "La casilla siguiente (11) debió cambiar a PROPIA");
    }

    @Test
    @DisplayName("No debe modificar si la siguiente es FINAL")
    void testNoModificaFinal() {
        mapa.modificarTipoDeCasilla(11, TipoCasilla.FINAL);
        partida.moverPorCarta(jugadorActivo, 10); 

        carta.aplicar(partida);

        assertEquals(TipoCasilla.FINAL, mapa.identificarTipoDeCasilla(11), 
                "No debió sobreescribir la casilla final");
    }
}