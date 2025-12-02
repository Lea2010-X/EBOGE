package modelo.cartas.blanco;

import modelo.Partida;
import modelo.cartas.Carta;
import modelo.cartas.EfectoConObjetivo;
import modelo.cartas.TipoCarta;
import modelo.jugador.Jugador;

public class BoogieWoogie extends Carta implements EfectoConObjetivo {

    public BoogieWoogie() {
        super("Boogie Woogie", TipoCarta.BLANCO, "/imagenes/Cartas/BoogieWoogie.png");
    }

    @Override
    public void aplicar(Jugador jugadorObjetivo, Partida partida) {
        Jugador jugadorActivo = partida.getJugadorActual();

        int posActivo   = jugadorActivo.getPosicion();
        int posObjetivo = jugadorObjetivo.getPosicion();

        int pasosActivo   = posObjetivo - posActivo;
        int pasosObjetivo = posActivo - posObjetivo;

        partida.moverPorCarta(jugadorActivo, pasosActivo);
        partida.moverPorCarta(jugadorObjetivo, pasosObjetivo);
    }
}

