package controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import modelo.mapa.Casilla;
import modelo.mapa.Mapa;

public class ControladorVentanaDePartida implements Initializable {

	@FXML
	private AnchorPane panelFondo;

	@FXML
	private GridPane gridTablero;

	@FXML
	private HBox barraInferior;

	@FXML
	private Button btnAbrirMazoPropio;
	// hola
	@FXML
	private Button btnAbrirMazoObjetivo;

	@FXML
	private Button btnAbrirMazoGlobal;

	@FXML
	private Button btnAbrirMazoMaestro;

	@FXML
	private Button btnLanzarDado;

	private static final double BORDE_LATERAL = 90.0;
	private static final double MARGEN_SUPERIOR = 35.0;
	private static final double FACTOR_ALTURA_TABLERO = 0.60;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		gridTablero.setLayoutX(BORDE_LATERAL);
		gridTablero.setLayoutY(MARGEN_SUPERIOR);

		gridTablero.prefWidthProperty().bind(panelFondo.widthProperty().subtract(BORDE_LATERAL * 2));

		gridTablero.prefHeightProperty().bind(panelFondo.heightProperty().multiply(FACTOR_ALTURA_TABLERO));

		btnLanzarDado.setOnAction(e -> lanzarDado());
		btnAbrirMazoPropio.setOnAction(e -> abrirMazoPropio());
		btnAbrirMazoObjetivo.setOnAction(e -> abrirMazoObjetivo());
		btnAbrirMazoGlobal.setOnAction(e -> abrirMazoGlobal());
		btnAbrirMazoMaestro.setOnAction(e -> abrirMazoMaestro());
	}

	private void lanzarDado() {
		System.out.println("Lanzar dado");
	}

	private void abrirMazoPropio() {
		System.out.println("Abrir mazo propio");
	}

	private void abrirMazoObjetivo() {
		System.out.println("Abrir mazo objetivo");
	}

	private void abrirMazoGlobal() {
		System.out.println("Abrir mazo global");
	}

	private void abrirMazoMaestro() {
		System.out.println("Abrir mazo maestro");
	}

	public double getAnchoTablero() {
		return gridTablero.getWidth();
	}

	public double getAltoTablero() {
		return gridTablero.getHeight();
	}

	public void dibujarTablero(Mapa mapa) {

		int numeroFilas = mapa.getLargoMapa();
		int numeroColumnas = mapa.getAnchoMapa();
		Casilla[][] matrizCasillas = mapa.getCasillas();

		gridTablero.getChildren().clear();
		gridTablero.getColumnConstraints().clear();
		gridTablero.getRowConstraints().clear();

		configurarDistribucionGrid(numeroFilas, numeroColumnas);

		for (int fila = 0; fila < numeroFilas; fila++) {

			for (int columna = 0; columna < numeroColumnas; columna++) {

				Casilla casillaActual = matrizCasillas[fila][columna];
				if (casillaActual == null)
					continue;

				StackPane celda = crearCelda(casillaActual, numeroFilas, numeroColumnas);

				gridTablero.add(celda, columna, fila);
			}
		}
	}

	private void configurarDistribucionGrid(int numeroFilas, int numeroColumnas) {

		for (int indiceColumna = 0; indiceColumna < numeroColumnas; indiceColumna++) {

			ColumnConstraints columnasConfig = new ColumnConstraints();
			columnasConfig.setPercentWidth(100.0 / numeroColumnas);

			gridTablero.getColumnConstraints().add(columnasConfig);
		}

		for (int indiceFila = 0; indiceFila < numeroFilas; indiceFila++) {

			RowConstraints filasConfig = new RowConstraints();
			filasConfig.setPercentHeight(100.0 / numeroFilas);

			gridTablero.getRowConstraints().add(filasConfig);
		}
	}

	private StackPane crearCelda(Casilla casilla, int totalFilas, int totalColumnas) {

		StackPane celda = new StackPane();
		celda.setPadding(Insets.EMPTY);
		celda.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		int indiceCasilla = casilla.getIndice();
		celda.setId(String.valueOf(indiceCasilla));

		Image imagenCasilla = new Image(getClass().getResourceAsStream(casilla.getTipo().getRutaImagen()));

		ImageView imagenFondoCasilla = new ImageView(imagenCasilla);

		imagenFondoCasilla.setPreserveRatio(false);
		imagenFondoCasilla.fitWidthProperty().bind(gridTablero.widthProperty().divide(totalColumnas));
		imagenFondoCasilla.fitHeightProperty().bind(gridTablero.heightProperty().divide(totalFilas));

		celda.getChildren().add(imagenFondoCasilla);

		Label etiquetaID = new Label(String.valueOf(indiceCasilla));
		etiquetaID.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;"
				+ "-fx-effect: dropshadow(gaussian, black, 4, 0.8, 0, 0);");

		StackPane.setAlignment(etiquetaID, Pos.TOP_LEFT);
		StackPane.setMargin(etiquetaID, new Insets(4));

		celda.getChildren().add(etiquetaID);

		return celda;
	}
}
