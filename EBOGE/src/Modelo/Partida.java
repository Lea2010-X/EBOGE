package Modelo;

import java.util.List;

public class Partida {
	private List<Jugador> jugadores;
	private Mapa mapa;
    private Mazo mazoPropias;
    private Mazo mazoBlancos;
    private Mazo mazoGlobales;
    private Mazo mazoMaestras;
    private Dado dado;
    private int indexJugadorActual;
    

    // Constructor
    public Partida(List<Jugador> jugadores, int carasDado, Mapa mapa) {
        this.jugadores = jugadores;
        this.mapa = mapa;
        this.dado = new Dado(carasDado);
        this.indexJugadorActual = 0;
        
        // Creacion y barajeo de los mazos
        this.mazoPropias = new Mazo(TipoCarta.PROPIA);
        this.mazoBlancos = new Mazo(TipoCarta.BLANCO);
        this.mazoGlobales = new Mazo(TipoCarta.GLOBAL);
        this.mazoMaestras = new Mazo(TipoCarta.MAESTRA);
    }
    
    // --- Getters que las Cartas necesitarán ---
    
    public Dado getDado() {
        return this.dado;
    }
    
    public Mapa getMapa() {
        return this.mapa;
    }
    
    public Mazo getMazo(TipoCarta tipo) {
        switch (tipo) {
            case PROPIA:
                return mazoPropias;
            case BLANCO:
                return mazoBlancos;
            case GLOBAL:
                return mazoGlobales;
            case MAESTRA:
                return mazoMaestras;
            default:

                return null;
        }
    }
    
    public List<Jugador> getJugadores() {
        return jugadores;
    }
    
    public Jugador getJugadorActual() {
    		return jugadores.get(indexJugadorActual);
    }
    
    public void pasarAlSiguienteJugador() {
    		indexJugadorActual = (indexJugadorActual + 1) % jugadores.size();
    }
    
    //nuevo!
    public int getTotalCasillas() {
    		return this.getMapa().getTotalCasillas();
    }
    
    
    
    
}
