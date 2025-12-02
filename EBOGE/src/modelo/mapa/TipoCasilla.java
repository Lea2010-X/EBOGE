package modelo.mapa;

import java.util.Random;

public enum TipoCasilla {

	INICIO('I', "/imagenes/CasillaInicio.png", 0),
	FINAL('F', "/imagenes/CasillaFinal.png", 0),
	NORMAL('N', "/imagenes/CasillaNormal.png", 50),
	PROPIA('P', "/imagenes/CasillaPropia.png", 15),
	BLANCO('B', "/imagenes/CasillaBlanco.png", 10),
	TRAMPA('T', "/imagenes/CasillaTrampa.png", 10),
	GLOBAL('G', "/imagenes/CasillaGlobal.png", 10), 
	MAESTRA('M', "/imagenes/CasillaMaestra.png", 5);

	private final char letra;
	private final String rutaImagen;
	private final int probabilidad;

	private static final Random random = new Random();

	TipoCasilla(char letra, String rutaImagen, int probabilidad) {
		this.letra = letra;
		this.rutaImagen = rutaImagen;
		this.probabilidad = probabilidad;
	}

	public char getLetra() {
		return letra;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public int getProbabilidad() {
		return probabilidad;
	}

	public static TipoCasilla aleatorio() {

		int numeroAleatorio = random.nextInt(100);
		int acumulado = 0;

		for (TipoCasilla tipo : TipoCasilla.values()) {
			if (tipo == INICIO || tipo == FINAL)
				continue;

			acumulado += tipo.getProbabilidad();

			if (numeroAleatorio < acumulado) {
				return tipo;
			}
		}

		return NORMAL;
	}
}

