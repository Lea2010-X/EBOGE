package modelo.cartas.maestra;

import modelo.Partida;
import modelo.cartas.Carta;
import modelo.cartas.EfectoSinObjetivo;
import modelo.cartas.TipoCarta;
import modelo.jugador.Jugador;
import modelo.mapa.Mapa;
import modelo.mapa.TipoCasilla;

public class CartaTemploDeLirios extends Carta implements EfectoSinObjetivo {
	
    public CartaTemploDeLirios() {
        super("Templo de Lirios", TipoCarta.MAESTRA, "/imagenes/Cartas/TemploDeLirios.png");
    }

    @Override
    public void aplicar(Partida partida) {
        Mapa mapa = partida.getMapa();
        Jugador jugadorActivo = partida.getJugadorActual();

        int posActual    = jugadorActivo.getPosicion();
        int posSiguiente = posActual + 1;

        if (validarCasilla(posSiguiente, mapa)) {
            mapa.modificarTipoDeCasilla(posSiguiente, TipoCasilla.PROPIA);
            System.out.println("Templo de Lirios: la casilla " + posSiguiente + " ahora es PROPIA.");
        } else {
            System.out.println("Templo de Lirios falla: la casilla siguiente no es válida o ya es PROPIA/FINAL.");
        }
    }

    public boolean validarCasilla(int posicionSiguiente, Mapa mapa) {
        if (posicionSiguiente < 0 || posicionSiguiente >= mapa.getTotalCasillas()) {
            return false;
        }

        TipoCasilla tipo = mapa.identificarTipoDeCasilla(posicionSiguiente);
        return tipo != TipoCasilla.FINAL && tipo != TipoCasilla.PROPIA;
    }
}

