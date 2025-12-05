package controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import modelo.CambioCasillaListener;
import modelo.MovimientoListener;
import modelo.jugador.Jugador;
import modelo.mapa.Casilla;
import modelo.mapa.Mapa;
import modelo.mapa.TipoCasilla;

public class ControladorTablero implements MovimientoListener, CambioCasillaListener {

    private final GridPane gridTablero;
    private final Map<Jugador, Pane> fichasJugadores = new HashMap<>();

    private int filas;
    private int columnas;

    public ControladorTablero(GridPane gridTablero) {
        this.gridTablero = gridTablero;
    }

    public void dibujarTablero(Mapa mapa, List<Jugador> jugadores) {
        this.filas = mapa.getLargoMapa();
        this.columnas = mapa.getAnchoMapa();

        Casilla[][] matrizCasillas = mapa.getCasillas();

        gridTablero.getChildren().clear();
        gridTablero.getColumnConstraints().clear();
        gridTablero.getRowConstraints().clear();

        configurarDistribucionGrid(filas, columnas);

        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                Casilla casilla = matrizCasillas[f][c];
                if (casilla == null) continue;

                StackPane celda = crearCelda(casilla, filas, columnas);
                gridTablero.add(celda, c, f);
            }
        }

        colocarFichasIniciales(jugadores);
    }

    private void configurarDistribucionGrid(int numeroFilas, int numeroColumnas) {
        for (int c = 0; c < numeroColumnas; c++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100.0 / numeroColumnas);
            gridTablero.getColumnConstraints().add(col);
        }

        for (int f = 0; f < numeroFilas; f++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / numeroFilas);
            gridTablero.getRowConstraints().add(row);
        }
    }

    private StackPane crearCelda(Casilla casilla, int totalFilas, int totalColumnas) {
        StackPane celda = new StackPane();
        celda.setPadding(Insets.EMPTY);
        celda.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        int indiceCasilla = casilla.getIndice();
        celda.setId(String.valueOf(indiceCasilla));

        Image imagenCasilla = new Image(
                getClass().getResourceAsStream(casilla.getTipo().getRutaImagen())
        );

        ImageView imagenFondoCasilla = new ImageView(imagenCasilla);
        imagenFondoCasilla.setPreserveRatio(false);
        imagenFondoCasilla.fitWidthProperty().bind(
                gridTablero.widthProperty().divide(totalColumnas)
        );
        imagenFondoCasilla.fitHeightProperty().bind(
                gridTablero.heightProperty().divide(totalFilas)
        );
        celda.getChildren().add(imagenFondoCasilla);

        Label etiquetaID = new Label(String.valueOf(indiceCasilla));
        etiquetaID.setStyle(
                "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;" +
                "-fx-effect: dropshadow(gaussian, black, 4, 0.8, 0, 0);"
        );
        StackPane.setAlignment(etiquetaID, Pos.TOP_LEFT);
        StackPane.setMargin(etiquetaID, new Insets(4));
        celda.getChildren().add(etiquetaID);

        FlowPane contenedorFichas = new FlowPane();
        contenedorFichas.setPickOnBounds(false);
        contenedorFichas.setId("fichas-" + indiceCasilla);
        contenedorFichas.setHgap(3);
        contenedorFichas.setVgap(3);
        contenedorFichas.setPrefWrapLength(40);
        StackPane.setAlignment(contenedorFichas, Pos.CENTER);
        celda.getChildren().add(contenedorFichas);

        return celda;
    }

    private void colocarFichasIniciales(List<Jugador> jugadores) {
        fichasJugadores.clear();
        int index = 0;

        for (Jugador jugador : jugadores) {
            int indiceCasilla = jugador.getPosicion();
            Pane contenedor = buscarContenedorFichas(indiceCasilla);
            if (contenedor == null) {
                System.err.println("No se encontró contenedor de fichas para casilla " +
                        indiceCasilla + " (" + jugador.getNombre() + ")");
                continue;
            }

            Pane ficha = crearFichaParaJugador(jugador, index++);
            fichasJugadores.put(jugador, ficha);
            contenedor.getChildren().add(ficha);
        }
    }

    private Pane crearFichaParaJugador(Jugador jugador, int index) {
        Pane ficha = new Pane();
        ficha.setPrefSize(16, 16);

        String hex = jugador.getColor();
        ficha.setStyle(
                "-fx-background-color: " + hex + ";" +
                "-fx-background-radius: 4;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 1;"
        );

        ficha.setId("ficha-" + jugador.getNombre());
        return ficha;
    }

    private StackPane buscarCeldaPorIndice(int indiceCasilla) {
        return (StackPane) gridTablero.lookup("#" + indiceCasilla);
    }

    private Pane buscarContenedorFichas(int indiceCasilla) {
        StackPane celda = buscarCeldaPorIndice(indiceCasilla);
        if (celda == null) return null;
        return (Pane) celda.lookup("#fichas-" + indiceCasilla);
    }

    private void moverFicha(Jugador jugador, int posAnterior, int nuevaPosicion) {
        Pane ficha = fichasJugadores.get(jugador);
        if (ficha == null) {
            ficha = crearFichaParaJugador(jugador, 0);
            fichasJugadores.put(jugador, ficha);
        }

        Pane contAnterior = buscarContenedorFichas(posAnterior);
        Pane contNuevo = buscarContenedorFichas(nuevaPosicion);

        if (contAnterior != null) {
            contAnterior.getChildren().remove(ficha);
        }
        if (contNuevo != null) {
            contNuevo.getChildren().add(ficha);
        }

        System.out.println("Moviendo " + jugador.getNombre() +
                " de " + posAnterior + " a " + nuevaPosicion);
    }





    @Override
    public void tipoCasillaCambiado(int indiceCasilla,
                                    TipoCasilla tipoAnterior,
                                    TipoCasilla nuevoTipo) {
        StackPane celda = buscarCeldaPorIndice(indiceCasilla);
        if (celda == null) return;

        if (!celda.getChildren().isEmpty()) {
            celda.getChildren().remove(0); 
        }

        Image img = new Image(getClass().getResourceAsStream(nuevoTipo.getRutaImagen()));
        ImageView fondo = new ImageView(img);
        fondo.setPreserveRatio(false);

        fondo.fitWidthProperty().bind(
                gridTablero.widthProperty().divide(columnas)
        );
        fondo.fitHeightProperty().bind(
                gridTablero.heightProperty().divide(filas)
        );

        celda.getChildren().add(0, fondo);

        System.out.println("Casilla " + indiceCasilla +
                " actualizada a tipo " + nuevoTipo);
    }

	@Override
	public void movimiento(Jugador jugador, int posAnterior, int nuevaPos) {
		 moverFicha(jugador, posAnterior, nuevaPos);
	}
}
