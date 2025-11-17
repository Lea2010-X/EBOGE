package Modelo;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class GeneradorDeMapa {

	private static final int RONDAS_ESPERADAS = 5;
	private static final String NOMBRE_ARCHIVO_MAPA = "Mapa.txt";

	private int anchoPanel;// W
	private int altoPanel;// H

	private int cantidadFilas;// m
	private int cantidadColumnas;// n

	private int ladosDelDado;// L

	private int anchoCasilla;// wc
	private int altoCasilla;// hc

	private int[][] indicesCasillas;
	private TipoCasilla[][] tiposDeCasilla;

	public Mapa generarMapa(int anchoPanel, int altoPanel, int ladosDelDado) {

		this.anchoPanel = anchoPanel;
		this.altoPanel = altoPanel;
		this.ladosDelDado = ladosDelDado;

		calcularDimensionesLogicas();

		indicesCasillas = new int[cantidadFilas][cantidadColumnas];
		tiposDeCasilla = new TipoCasilla[cantidadFilas][cantidadColumnas];

		recorrerEspiral();
		asignarTiposDeCasilla();

		escribirArchivoMapa();

		return new Mapa(cantidadColumnas, cantidadFilas, anchoCasilla, altoCasilla, NOMBRE_ARCHIVO_MAPA);
	}

	private void calcularDimensionesLogicas() {

		double esperanzaDelDado = (ladosDelDado + 1) / 2.0;
		double casillasTeoricas = RONDAS_ESPERADAS * esperanzaDelDado;
		int cantidadCasillas = (int) Math.round(casillasTeoricas);

		double filasAproximadas = Math.sqrt((cantidadCasillas * (double) altoPanel) / (double) anchoPanel);
		int filas = Math.max(1, (int) Math.round(filasAproximadas));

		int columnas = (int) Math.ceil(cantidadCasillas / (double) filas);

		cantidadFilas = filas;
		cantidadColumnas = columnas;

		anchoCasilla = anchoPanel / cantidadColumnas;
		altoCasilla = altoPanel / cantidadFilas;
	}

	private void recorrerEspiral() {

		int limiteSuperior = 0;
		int limiteInferior = cantidadFilas - 1;
		int limiteIzquierdo = 0;
		int limiteDerecho = cantidadColumnas - 1;
		int indiceActual = 0;
		int totalCasillas = cantidadFilas * cantidadColumnas;

		while (limiteSuperior <= limiteInferior && limiteIzquierdo <= limiteDerecho && indiceActual < totalCasillas) {

			// Bajar por columna izquierda
			for (int fila = limiteSuperior; fila <= limiteInferior && indiceActual < totalCasillas; fila++) {
				indicesCasillas[fila][limiteIzquierdo] = indiceActual++;
			}
			limiteIzquierdo++;

			// Ir a la derecha por fila inferior
			for (int columna = limiteIzquierdo; columna <= limiteDerecho && indiceActual < totalCasillas; columna++) {
				indicesCasillas[limiteInferior][columna] = indiceActual++;
			}
			limiteInferior--;

			// Subir por columna derecha
			for (int fila = limiteInferior; fila >= limiteSuperior && indiceActual < totalCasillas; fila--) {
				indicesCasillas[fila][limiteDerecho] = indiceActual++;
			}
			limiteDerecho--;

			// Ir a la izquierda por fila superior
			for (int columna = limiteDerecho; columna >= limiteIzquierdo && indiceActual < totalCasillas; columna--) {
				indicesCasillas[limiteSuperior][columna] = indiceActual++;
			}
			limiteSuperior++;
		}
	}

	private void asignarTiposDeCasilla() {

		int totalCasillas = cantidadFilas * cantidadColumnas;

		for (int fila = 0; fila < cantidadFilas; fila++) {
			for (int columna = 0; columna < cantidadColumnas; columna++) {
				int indiceCasilla = indicesCasillas[fila][columna];

				if (indiceCasilla == 0) {
					tiposDeCasilla[fila][columna] = TipoCasilla.INICIO;
				} else if (indiceCasilla == totalCasillas - 1) {
					tiposDeCasilla[fila][columna] = TipoCasilla.FINAL;
				} else {
					tiposDeCasilla[fila][columna] = TipoCasilla.aleatorio();
				}
			}
		}
	}

	private void escribirArchivoMapa() {

		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO_MAPA))) {

			for (int fila = 0; fila < cantidadFilas; fila++) {
				StringBuilder linea = new StringBuilder();

				for (int columna = 0; columna < cantidadColumnas; columna++) {
					int indiceCasilla = indicesCasillas[fila][columna];
					TipoCasilla tipo = tiposDeCasilla[fila][columna];

					char simboloTipo = tipo.getSimbolo();

					linea.append("[").append(indiceCasilla).append(",").append(simboloTipo).append(",()]");
				}

				escritor.write(linea.toString());
				escritor.newLine();
			}

		} catch (Exception excepcion) {
			System.err.println("Error al generar el archivo del mapa.");
		}
	}

}
