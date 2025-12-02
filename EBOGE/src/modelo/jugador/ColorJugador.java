package modelo.jugador;

public enum ColorJugador {

	BLANCO('W', "#FFFFFF"), NEGRO('K', "#000000"), ROJO('R', "#FF0000"), NARANJA('O', "#FFA500"),
	AMARILLO('A', "#FFFF00"), AZUL('L', "#0000FF"), VERDE('E', "#00FF00"), ROSA('S', "#FF69B4");

	private final char letra;
	private final String codigoHex;

	ColorJugador(char letra, String codigoHex) {
		this.letra = letra;
		this.codigoHex = codigoHex;
	}

	public char getLetra() {
		return letra;
	}

	public String getCodigoHex() {
		return codigoHex;
	}
}