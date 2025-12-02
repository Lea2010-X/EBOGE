package modelo.cartas.maestra;

import java.util.List;

import modelo.AccionCasillaTrampa;
import modelo.Partida;
import modelo.cartas.Carta;
import modelo.cartas.EfectoSinObjetivo;
import modelo.cartas.TipoCarta;
import modelo.jugador.Jugador;
import modelo.mapa.Mapa;
import modelo.mapa.TipoCasilla;

public class GrilletesDelAlma extends Carta implements EfectoSinObjetivo {

    public GrilletesDelAlma() {
        super("Grilletes del alma", TipoCarta.MAESTRA, "/imagenes/Cartas/GrilletesDelAlma.png");
    }

    @Override
    public void aplicar(Partida partida) {
        Mapa mapa = partida.getMapa();
        List<Jugador> jugadores = partida.getJugadores();
        Jugador jugadorActivo = partida.getJugadorActual();

        int posActiva = jugadorActivo.getPosicion();

        Jugador jugadorSiguiente = null;
        Jugador jugadorAnterior = null;

        int distSiguiente = Integer.MAX_VALUE;
        int distAnterior  = Integer.MAX_VALUE;

        for (Jugador j : jugadores) {
            if (j == jugadorActivo) continue;

            int pos = j.getPosicion();

            if (pos > posActiva) { 
                int d = pos - posActiva;
                if (d < distSiguiente) {
                    distSiguiente = d;
                    jugadorSiguiente = j;
                }
            } else if (pos < posActiva) { 
                int d = posActiva - pos;
                if (d < distAnterior) {
                    distAnterior = d;
                    jugadorAnterior = j;
                }
            }
        }

        AccionCasillaTrampa accionTrampa = new AccionCasillaTrampa();

        if (jugadorAnterior != null) {
            System.out.println("Grilletes del alma: " + jugadorAnterior.getNombre()
                    + " es atrapado por detrás y sufre el efecto de los grilletes.");
            accionTrampa.ejecutar(partida, jugadorAnterior);
        }

        if (jugadorSiguiente != null) {
            int posTrampa = jugadorSiguiente.getPosicion();

            if (posTrampa > 0) {
                mapa.modificarTipoDeCasilla(posTrampa, TipoCasilla.TRAMPA);
                System.out.println("Grilletes del alma: La casilla " + posTrampa
                        + " ha sido encadenada y ahora es una TRAMPA.");
            }

            System.out.println("Grilletes del alma: " + jugadorSiguiente.getNombre()
                    + " queda atrapado adelante y recibe el efecto de los grilletes.");

            accionTrampa.ejecutar(partida, jugadorSiguiente);
        }
    }
}
