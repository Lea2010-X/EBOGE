package modelo.mapa;

import java.io.FileWriter;

public class GeneradorDeMapa {

    private static final int RONDAS_ESPERADAS = 10;

    private int anchoPanel;       // W
    private int altoPanel;        // H

    private int cantidadFilas;    // m
    private int cantidadColumnas; // n

    private int ladosDelDado;     // L


    private int anchoCasillaEstimado;  // wc aproximado
    private int altoCasillaEstimado;   // hc aproximado

    private Casilla[][] casillas;

    public Mapa generarMapa(int anchoPanel, int altoPanel, int ladosDelDado) {

        this.anchoPanel = anchoPanel;
        this.altoPanel = altoPanel;
        this.ladosDelDado = ladosDelDado;

        calcularDimensionesLogicas();

        casillas = new Casilla[cantidadFilas][cantidadColumnas];

        recorrerEspiralYCrearCasillas();

        return new Mapa(cantidadColumnas, cantidadFilas, casillas);
    }

    private void calcularDimensionesLogicas() {

        double esperanzaDelDado = (ladosDelDado + 1) / 2.0;
        double casillasTeoricas = RONDAS_ESPERADAS * esperanzaDelDado;
        int cantidadCasillas = (int) Math.round(casillasTeoricas);

        double filasAproximadas =
                Math.sqrt((cantidadCasillas * (double) altoPanel) / (double) anchoPanel);
        int filas = Math.max(1, (int) Math.round(filasAproximadas));

        int columnas = (int) Math.ceil(cantidadCasillas / (double) filas);

        cantidadFilas = filas;
        cantidadColumnas = columnas;

        anchoCasillaEstimado = anchoPanel / cantidadColumnas;
        altoCasillaEstimado = altoPanel / cantidadFilas;
    }

    public int getAnchoCasillaEstimado() {
        return anchoCasillaEstimado;
    }

    public int getAltoCasillaEstimado() {
        return altoCasillaEstimado;
    }


    private void recorrerEspiralYCrearCasillas() {

        int limiteSuperior = 0;
        int limiteInferior = cantidadFilas - 1;
        int limiteIzquierdo = 0;
        int limiteDerecho = cantidadColumnas - 1;
        int indiceActual = 0;
        int totalCasillas = cantidadFilas * cantidadColumnas;

        while (limiteSuperior <= limiteInferior
                && limiteIzquierdo <= limiteDerecho
                && indiceActual < totalCasillas) {

            // Bajar por columna izquierda
            for (int fila = limiteSuperior; fila <= limiteInferior && indiceActual < totalCasillas; fila++) {
                crearCasillaEnPosicion(fila, limiteIzquierdo, indiceActual++, totalCasillas);
            }
            limiteIzquierdo++;

            // Ir a la derecha por fila inferior
            for (int columna = limiteIzquierdo; columna <= limiteDerecho && indiceActual < totalCasillas; columna++) {
                crearCasillaEnPosicion(limiteInferior, columna, indiceActual++, totalCasillas);
            }
            limiteInferior--;

            // Subir por columna derecha
            for (int fila = limiteInferior; fila >= limiteSuperior && indiceActual < totalCasillas; fila--) {
                crearCasillaEnPosicion(fila, limiteDerecho, indiceActual++, totalCasillas);
            }
            limiteDerecho--;

            // Ir a la izquierda por fila superior
            for (int columna = limiteDerecho; columna >= limiteIzquierdo && indiceActual < totalCasillas; columna--) {
                crearCasillaEnPosicion(limiteSuperior, columna, indiceActual++, totalCasillas);
            }
            limiteSuperior++;
        }
    }

    private void crearCasillaEnPosicion(int fila, int columna, int indiceCasilla, int totalCasillas) {
        TipoCasilla tipo;

        if (indiceCasilla == 0) {
            tipo = TipoCasilla.INICIO;
        } else if (indiceCasilla == totalCasillas - 1) {
            tipo = TipoCasilla.FINAL;
        } else {
            tipo = TipoCasilla.aleatorio();
        }

        casillas[fila][columna] = new Casilla(tipo, indiceCasilla);
    }
}
