package Modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Mapa {

	private int anchoMapa;
	private int largoMapa;

	private int anchoCasilla;
	private int largoCasilla;

	private TipoCasilla[] tiposPorCasilla;
	private String rutaMapa;


	//Constructor
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


	//Getters
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


	//Métodos Públicos 
	
	public TipoCasilla identificarTipoDeCasilla(int numeroCasilla) {
		if (numeroCasilla < 0 || numeroCasilla >= tiposPorCasilla.length) {
			return null;
		}
		return tiposPorCasilla[numeroCasilla];
	}

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



	//Métodos privados
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
						char letraTipo = partesCasilla[1].trim().charAt(0);

						TipoCasilla tipo = convertirLetraATipo(letraTipo);

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


	private TipoCasilla convertirLetraATipo(char letraCasilla) {
		for (TipoCasilla tipo : TipoCasilla.values()) {
			if (tipo.getLetra() == letraCasilla) {
				return tipo;
			}
		}
		return null;
	}
}