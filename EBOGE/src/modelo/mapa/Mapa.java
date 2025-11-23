package modelo.mapa;

public class Mapa {

    private int anchoMapa;   
    private int largoMapa;   

    private int anchoCasilla;
    private int largoCasilla;

    private Casilla[][] casillas;

    // Constructor
    public Mapa(int anchoMapa, int largoMapa, int anchoCasilla, int largoCasilla, Casilla[][] casillas) {
        this.anchoMapa = anchoMapa;
        this.largoMapa = largoMapa;
        this.anchoCasilla = anchoCasilla;
        this.largoCasilla = largoCasilla;
        this.casillas = casillas;
    }

    // Getters
    public int getTotalCasillas() {
        return anchoMapa * largoMapa;
    }

    public int getAnchoMapa() {
        return anchoMapa;
    }

    public int getLargoMapa() {
        return largoMapa;
    }

    public int getAnchoCasilla() {
        return anchoCasilla;
    }

    public int getLargoCasilla() {
        return largoCasilla;
    }
    
    public Casilla[][] getCasillas() {
        return casillas;
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
            casilla.setTipo(nuevoTipo);
        }
    }
    


    //Métodos privados

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
