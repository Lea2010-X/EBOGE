package controlador;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import modelo.MotorDeTurnos;
import modelo.Partida;
import modelo.jugador.ColorJugador;
import modelo.jugador.Jugador;
import modelo.mapa.GeneradorDeMapa;
import modelo.mapa.Mapa;

public class ControladorPrincipal {

	private static final String RUTA_FUENTE_PIXEL = "/fuentes/PressStart2P-Regular.ttf";
	private static final String RUTA_FUENTE_ICONOS = "/fuentes/Font Awesome 7 Free-Solid-900.otf";

	private static final String RUTA_VISTA_INICIO = "/vista/Inicio.fxml";
	private static final String RUTA_VISTA_CONFIGURACION = "/vista/.fxml";
	private static final String RUTA_VISTA_CARGAR_PARTIDA = "/vista/PantallaCarga.fxml";
	private static final String RUTA_VISTA_PARTIDA = "/vista/VentanaDePartida.fxml";

	private static final String TITULO_VENTANA_PRINCIPAL = "EBOGE - Epic Board Game Evolution";

	private static final double TAMANIO_FUENTE_BASE = 10.0;

	private Stage ventanaPrincipal;
	private Partida partida;

	public ControladorPrincipal() {
	}

	public void iniciarEBOGE(Stage stage) throws IOException {

		this.ventanaPrincipal = stage;

		configurarPantallaPrincipal(ventanaPrincipal);
		mostrarVentanaDePartida();
	}

	private void configurarPantallaPrincipal(Stage stage) {
		stage.setTitle(TITULO_VENTANA_PRINCIPAL);
		stage.setResizable(false);
		stage.setMaximized(true);
	}

	public void mostrarVentanaDeInicio() {
		try {
			cargarFuentesGlobales();
			inicializarInterfaz(ventanaPrincipal, RUTA_VISTA_INICIO, TITULO_VENTANA_PRINCIPAL);
		} catch (Exception e) {
			System.err.println("Error critico al iniciar la aplicación: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void mostrarVentanaDeConfiguracion() {
		// pendiente
	}

	public void mostrarVentanaDeCarga() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(RUTA_VISTA_CARGAR_PARTIDA));
			Parent raiz = loader.load();

			ControladorPantallaCarga cargaController = loader.getController();

			cargaController.setOnCargaTerminada(() -> {
				mostrarVentanaDePartida();
			});

			Scene escena = new Scene(raiz);

			ventanaPrincipal.setTitle("EBOGE - Cargando");
			ventanaPrincipal.setScene(escena);
			ventanaPrincipal.setResizable(false);
			ventanaPrincipal.setMaximized(true);
			ventanaPrincipal.show();

		} catch (Exception e) {
			System.err.println("Error critico al iniciar la aplicación: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void mostrarVentanaDePartida() {
		try {
			FXMLLoader loader = inicializarInterfaz(ventanaPrincipal, RUTA_VISTA_PARTIDA, "EBOGE - Partida");
			ControladorVentanaDePartida controladorPartida = loader.getController();

			Platform.runLater(() -> {
				double anchoTablero = controladorPartida.getAnchoTablero();
				double altoTablero = controladorPartida.getAltoTablero();

				System.out.println("Ancho del Tablero: " + anchoTablero);
				System.out.println("Alto del Tablero: " + altoTablero);

				crearPartidaDemo((int) anchoTablero, (int) altoTablero);

				MotorDeTurnos motor = new MotorDeTurnos(partida);


				controladorPartida.inicializarConMotor(motor);

			});

		} catch (Exception e) {
			System.err.println("Error al iniciar la aplicación: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void crearPartidaDemo(int anchoTablero, int altoTablero) {

		if (this.partida != null) {
			return;
		}

		List<Jugador> jugadores = new ArrayList<>();

		jugadores.add(new Jugador("fLOCH", ColorJugador.AZUL));
		jugadores.add(new Jugador("DarthNetza", ColorJugador.ROJO));
		jugadores.add(new Jugador("eLtATUADO", ColorJugador.AMARILLO));

		int carasDado = 5;

		GeneradorDeMapa generador = new GeneradorDeMapa();
		Mapa mapaGenerado = generador.generarMapa(anchoTablero, altoTablero, carasDado);

		this.partida = new Partida(jugadores, carasDado, mapaGenerado);
	}

	public void mostrarVentanaDeVictoria() {
		// pendiente
	}

	private void cargarFuentesGlobales() {
		cargarFuente(RUTA_FUENTE_PIXEL);
		cargarFuente(RUTA_FUENTE_ICONOS);
	}

	private void cargarFuente(String rutaRecurso) {
		try (InputStream fontStream = getClass().getResourceAsStream(rutaRecurso)) {
			if (fontStream != null) {
				Font.loadFont(fontStream, TAMANIO_FUENTE_BASE);
			} else {
				System.err.println("Advertencia: No se pudo encontrar la fuente en " + rutaRecurso);
			}
		} catch (IOException e) {
			System.err.println("Error al leer el flujo de la fuente: " + rutaRecurso);
		}
	}

	private FXMLLoader inicializarInterfaz(Stage escenario, String rutaEscena, String titulo) throws IOException {
		if (getClass().getResource(rutaEscena) == null) {
			throw new IOException("No se encontró el archivo FXML en: " + rutaEscena);
		}

		FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaEscena));
		Parent raiz = loader.load();

		Scene escena = new Scene(raiz);

		escenario.setTitle(titulo);
		escenario.setScene(escena);
		escenario.setResizable(false);
		escenario.setMaximized(true);
		escenario.show();

		return loader;
	}

}
