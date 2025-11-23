package controlador;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.mapa.GeneradorDeMapa;
import modelo.mapa.Mapa;

public class ControladorPrincipal {

	private Stage ventanaPrincipal;

	public ControladorPrincipal() {

	}

	public void iniciarEBOGE(Stage stage, int ladosDelDado) throws IOException {

		this.ventanaPrincipal = stage;

		mostrarVentanaDePartida(ladosDelDado);
	}

	public void mostrarVentanaDeInicio() {

	}

	public void mostrarVentanaDeConfiguracion() {

	}

	public void mostrarVentanaDeCarga() {

	}

	private void mostrarVentanaDePartida(int ladosDelDado) throws IOException {

		FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource("/Vista/VentanaDePartida.fxml"));

		Parent raiz = cargadorFXML.load();
		ControladorVentanaDePartida controladorPartida = cargadorFXML.getController();

		Scene escena = new Scene(raiz);

		ventanaPrincipal.setScene(escena);
		ventanaPrincipal.setTitle("EBOGE - Partida");
		ventanaPrincipal.setMaximized(true);
		ventanaPrincipal.show();

		Platform.runLater(() -> {

			double anchoTablero = controladorPartida.getAnchoTablero();
			double altoTablero = controladorPartida.getAltoTablero();

			System.out.println("Ancho del Tablero: " + anchoTablero);
			System.out.println("Alto del Tablero: " + altoTablero);

			GeneradorDeMapa generadorDeMapa = new GeneradorDeMapa();
			Mapa mapaGenerado = generadorDeMapa.generarMapa((int) anchoTablero, (int) altoTablero, ladosDelDado);

			controladorPartida.dibujarTablero(mapaGenerado);
		});
	}

	public void mostrarVentanaDeVictoria() {

	}
}