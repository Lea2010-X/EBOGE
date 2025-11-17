package Modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Mapa {

	private int anchoMapa;
	private int largoMapa;

	private int anchoCasilla;
	private int largoCasilla;

	private TipoCasilla[] tiposPorCasilla;
	private String rutaMapa;

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

	//Faltan metodos que utilizan jugador

	public TipoCasilla identificarTipoDeCasilla(int numCasilla) {
		if (numCasilla < 0 || numCasilla >= tiposPorCasilla.length) {
			return null;
		}
		return tiposPorCasilla[numCasilla];
	}
	
	public void eliminarTxt() {
		try {
			File archivo = new File(rutaMapa);
			if (archivo.exists()) {
				archivo.delete();
			}
		} catch (Exception e) {
			System.err.println("Error al eliminar el archivo del mapa.");
		}
	}

	private void cargarMapa() {
		try (BufferedReader lector = new BufferedReader(new FileReader(rutaMapa))) {

			String linea;

			while ((linea = lector.readLine()) != null) {

				int pos = 0;

				while (true) {

					int inicio = linea.indexOf('[', pos);
					if (inicio == -1)
						break;

					int fin = linea.indexOf(']', inicio);
					if (fin == -1)
						break;

					String contenido = linea.substring(inicio + 1, fin);
					String[] partes = contenido.split(",");

					if (partes.length >= 2) {
						int numCasilla = Integer.parseInt(partes[0].trim());
						char simbolo = partes[1].trim().charAt(0);

						TipoCasilla tipo = convertirLetraATipo(simbolo);

						if (numCasilla >= 0 && numCasilla < tiposPorCasilla.length) {
							tiposPorCasilla[numCasilla] = tipo;
						}
					}

					pos = fin + 1;
				}
			}

		} catch (Exception e) {
			System.err.println("Error al cargar el mapa desde el archivo: " + rutaMapa);
		}
	}

	private TipoCasilla convertirLetraATipo(char letra) {
		for (TipoCasilla tipo : TipoCasilla.values()) {
			if (tipo.getSimbolo() == letra) {
				return tipo;
			}
		}
		return null;
	}

	public void imprimirMapaEnConsola() {
		System.out.println("=== Contenido del mapa ===");

		for (int i = 0; i < tiposPorCasilla.length; i++) {
			TipoCasilla tipo = tiposPorCasilla[i];

			if (tipo == null) {
				System.out.println("Casilla " + i + ": (sin tipo)");
			} else {
				System.out.println("Casilla " + i + ": " + tipo.name());
			}
		}
	}
	
	public int geTotalCasillas() {
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
}