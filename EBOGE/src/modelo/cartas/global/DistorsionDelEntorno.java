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

public class DistorsionDelEntorno extends Carta implements EfectoSinObjetivo{
	
	private Random random = new Random();

    public DistorsionDelEntorno() {
        super("Distorsion del entorno", TipoCarta.GLOBAL, "/imagenes/Cartas/DistorsionDelEntorno.png");
    }

	@Override
	public void aplicar(Partida partida) {
        
        Mapa mapa = partida.getMapa();
        List<Jugador> jugadores = partida.getJugadores();

        // 1. Necesitamos encontrar todas las casillas "Normales"
        List<Integer> casillasNormales = new ArrayList<>();
        int totalCasillas = mapa.getTotalCasillas();
        
        for (int i = 0; i < totalCasillas; i++) {
            // (Asumimos que tienes un enum TipoCasilla.NORMAL)
            if (mapa.identificarTipoDeCasilla(i) == TipoCasilla.NORMAL) {
                casillasNormales.add(i);
            }
        }

        if (casillasNormales.isEmpty()) {
            System.out.println("No hay casillas normales para reubicar. No pasa nada.");
            return;
        }

        // 2. Barajamos las posiciones
        Collections.shuffle(casillasNormales, random);

        // 3. Reubicamos a cada jugador en una casilla normal
        for (int i = 0; i < jugadores.size(); i++) {
            // Tomamos una posición aleatoria (o la i-ésima barajada)
            // Usamos módulo (%) para evitar error si hay más jugadores que casillas
            int nuevaPosicion = casillasNormales.get(i % casillasNormales.size());
            
            Jugador j = jugadores.get(i);
            j.setPosicion(nuevaPosicion);
            System.out.println(j.getNombre() + " es transportado a la casilla " + nuevaPosicion);
        }
	}
}	
