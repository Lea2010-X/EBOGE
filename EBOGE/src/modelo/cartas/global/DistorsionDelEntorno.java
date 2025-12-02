package modelo.cartas.global;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import modelo.Partida;
import modelo.cartas.Carta;
import modelo.cartas.EfectoSinObjetivo;
import modelo.cartas.TipoCarta;
import modelo.jugador.Jugador;
import modelo.mapa.Mapa;
import modelo.mapa.TipoCasilla;

public class DistorsionDelEntorno extends Carta implements EfectoSinObjetivo {
	
    private Random random = new Random();

    public DistorsionDelEntorno() {
        super("Distorsion del entorno", TipoCarta.GLOBAL, "/imagenes/Cartas/DistorcionDelEntorno.png");
    }

    @Override
    public void aplicar(Partida partida) {

        Mapa mapa = partida.getMapa();
        List<Jugador> jugadores = partida.getJugadores();

        List<Integer> casillasNormales = new ArrayList<>();
        int totalCasillas = mapa.getTotalCasillas();

        for (int i = 0; i < totalCasillas; i++) {
            if (mapa.identificarTipoDeCasilla(i) == TipoCasilla.NORMAL) {
                casillasNormales.add(i);
            }
        }

        if (casillasNormales.isEmpty()) {
            System.out.println("No hay casillas normales para reubicar. No pasa nada.");
            return;
        }

        Collections.shuffle(casillasNormales, random);

        for (int i = 0; i < jugadores.size(); i++) {

            Jugador j = jugadores.get(i);
            int destino = casillasNormales.get(i % casillasNormales.size());

            int pasos = destino - j.getPosicion();

            partida.moverPorCarta(j, pasos);

            System.out.println(j.getNombre() + " es transportado a la casilla " + destino);
        }
    }
}


