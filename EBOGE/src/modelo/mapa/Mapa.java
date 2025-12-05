package modelo.mapa;

import modelo.CambioCasillaEventManager;

public class Mapa {

    private int anchoMapa;   
    private int largoMapa;   

    private Casilla[][] casillas;
    private final CambioCasillaEventManager gestorCambios = new CambioCasillaEventManager();

    // Constructor
    public Mapa(int anchoMapa, int largoMapa, Casilla[][] casillas) {
        this.anchoMapa = anchoMapa;
        this.largoMapa = largoMapa;
        this.casillas = casillas;
    }


    public int getTotalCasillas() {
        return anchoMapa * largoMapa;
    }

    public int getAnchoMapa() {
        return anchoMapa;
    }

    public int getLargoMapa() {
        return largoMapa;
    }

    public Casilla[][] getCasillas() {
        return casillas;
    }

    public CambioCasillaEventManager getGestorDeCambios() {
        return gestorCambios;
    }

    public TipoCasilla identificarTipoDeCasilla(int numeroCasilla) {

        Casilla casilla = buscarCasillaPorIndice(numeroCasilla);
        return (casilla != null) ? casilla.getTipo() : null;
    }

    public void modificarTipoDeCasilla(int numeroCasilla, TipoCasilla nuevoTipo) {

        if (nuevoTipo == null) {
            return;
        }

        Casilla casilla = buscarCasillaPorIndice(numeroCasilla);
        if (casilla != null) {
            TipoCasilla anterior = casilla.getTipo();
            casilla.setTipo(nuevoTipo);


            gestorCambios.notificar(casilla.getIndice(), anterior, nuevoTipo);
        }
    }



    private Casilla buscarCasillaPorIndice(int numeroCasilla) {

        if (numeroCasilla < 0 || numeroCasilla >= getTotalCasillas()) {
            return null;
        }

        for (int fila = 0; fila < largoMapa; fila++) {
            for (int columna = 0; columna < anchoMapa; columna++) {
                Casilla casilla = casillas[fila][columna];
                if (casilla != null && casilla.getIndice() == numeroCasilla) {
                    return casilla;
                }
            }
        }

        return null;
    }
}
