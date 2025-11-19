package Modelo;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Mapa {

    private int anchoMapa;
    private int largoMapa;
    
public class Mapa {

    private int anchoMapa;   
    private int largoMapa;   


    private int anchoCasilla;
    private int largoCasilla;


    private TipoCasilla[] tiposPorCasilla;
    private String rutaMapa;

    // ==========================
    // Constructor
    // ==========================

    public Mapa(int anchoMapa, int largoMapa, int anchoCasilla, int largoCasilla, String rutaMapa) {
        this.anchoMapa = anchoMapa;
        this.largoMapa = largoMapa;
        this.anchoCasilla = anchoCasilla;
        this.largoCasilla = largoCasilla;
        this.rutaMapa = rutaMapa;

        int totalCasillas = anchoMapa * largoMapa;
        this.tiposPorCasilla = new TipoCasilla[totalCasillas];

        cargarMapa();
    }

    // ==========================
    // Getters
    // ==========================

    public int getTotalCasillas() {
        return tiposPorCasilla.length;
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

    public String getRutaMapa() {
        return rutaMapa;
    }

    // ==========================
    // Métodos públicos del mapa
    // ==========================

    /**
     * Devuelve el tipo de casilla asociado a un número de casilla.
     * Si el índice es inválido, devuelve null.
     */
    public TipoCasilla identificarTipoDeCasilla(int numeroCasilla) {
        if (numeroCasilla < 0 || numeroCasilla >= tiposPorCasilla.length) {
            return null;
        }
        return tiposPorCasilla[numeroCasilla];
    }
    
    
    /**
     * Modifica el tipo de una casilla específica en tiempo de ejecución.
     * Cumple con el requisito RF-11 (Modificación del tablero) para cartas Maestras.
     * * @param numeroCasilla El índice de la casilla a modificar (0 a N-1).
     * @param nuevoTipo El nuevo enum TipoCasilla que tendrá esta posición.
     */
    public void cambiarTipoDeCasilla(int numeroCasilla, TipoCasilla nuevoTipo) {
        //Validar si la casilla existe en el tablero
        if (numeroCasilla >= 0 && numeroCasilla < tiposPorCasilla.length) {
            
            this.tiposPorCasilla[numeroCasilla] = nuevoTipo;
                     
        } else {
            System.err.println("Error: Intento de modificar una casilla fuera de rango (" + numeroCasilla + ")");
        }
    }
    

    /**
     * Elimina el archivo de texto asociado al mapa, si existe.
     */
    public void eliminarTxt() {
        try {
            File archivo = new File(rutaMapa);
            if (archivo.exists()) {
                archivo.delete();
            }
        } catch (Exception excepcion) {
            System.err.println("Error al eliminar el archivo del mapa.");
        }
    }

    /**
     * Imprime en consola el arreglo lineal de tipos de casilla
     * (principalmente para depuración).
     */
    public void imprimirMapaEnConsola() {
        System.out.println("=== Contenido del mapa ===");

        for (int indice = 0; indice < tiposPorCasilla.length; indice++) {
            TipoCasilla tipo = tiposPorCasilla[indice];

            if (tipo == null) {
                System.out.println("Casilla " + indice + ": (sin tipo)");
            } else {
                System.out.println("Casilla " + indice + ": " + tipo.name());
            }
        }
    }

    // ==========================
    // Métodos privados
    // ==========================

    /**
     * Lee el archivo de mapa y rellena el arreglo tiposPorCasilla.
     * El formato esperado por casilla es: [numero,tipo,()]
     */
    private void cargarMapa() {
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaMapa))) {

            String lineaActual;

            while ((lineaActual = lector.readLine()) != null) {

                int posicionBusqueda = 0;

                while (true) {
                    int inicioCasilla = lineaActual.indexOf('[', posicionBusqueda);
                    if (inicioCasilla == -1) {
                        break;
                    }

                    int finCasilla = lineaActual.indexOf(']', inicioCasilla);
                    if (finCasilla == -1) {
                        break;
                    }

                    String contenidoCasilla = lineaActual.substring(inicioCasilla + 1, finCasilla);
                    String[] partesCasilla = contenidoCasilla.split(",");

                    if (partesCasilla.length >= 2) {
                        int numeroCasilla = Integer.parseInt(partesCasilla[0].trim());
                        char simboloTipo = partesCasilla[1].trim().charAt(0);

                        TipoCasilla tipo = convertirLetraATipo(simboloTipo);

                        if (numeroCasilla >= 0 && numeroCasilla < tiposPorCasilla.length) {
                            tiposPorCasilla[numeroCasilla] = tipo;
                        }
                    }

                    posicionBusqueda = finCasilla + 1;
                }
            }

        } catch (IOException excepcion) {
            System.err.println("Error al cargar el mapa desde el archivo: " + rutaMapa);
        }
    }

    /**
     * Convierte el símbolo del archivo (letra) al enum TipoCasilla.
     */
    
    private TipoCasilla convertirLetraATipo(char simboloCasilla) {
	    	for (TipoCasilla tipo : TipoCasilla.values()) {
	            if (tipo.getSimbolo() == simboloCasilla) {
	                return tipo;
	            }
	    }
	    	return null;
    }
}
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