package Controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import Modelo.Casilla;
import Modelo.Mapa;
import Modelo.Partida;

public class ControladorVentanaDePartida implements Initializable {

    @FXML
    private Button btn1;

    @FXML
    private Label lb1;

    @FXML
    private GridPane tableroGrid;

    private Partida partida;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicialización por defecto
    }

    @FXML
    private void holaMundo() {
        lb1.setText("hola mundo");
    }

    // El ancho y alto del Grid del Scene

    public double getAnchoTablero() {
        return tableroGrid.getPrefWidth();
    }

    public double getAltoTablero() {
        return tableroGrid.getPrefHeight();
    }

    public void setPartida(Partida partidaRecibida) {
        this.partida = partidaRecibida;
    }


    //Evento de hacer click al boton se crea mapa (Se puede cambiar)
    @FXML
    public void BtnPresionado(ActionEvent event) {
    	
        // Validación de seguridad
        if (partida == null) {
            lb1.setText("Error: No se ha cargado la partida (Objeto nulo).");
            return;
        }

        Mapa mapaGenerado = partida.getMapa();


        dibujarTablero(mapaGenerado);


        aplicarEfectoRojo("#casilla_9");
    }



    private void dibujarTablero(Mapa mapa) {
      
        int filas = mapa.getLargoMapa();
        int cols = mapa.getAnchoMapa();
        Casilla[][] matriz = mapa.getCasillas();

        
        configurarGrid(filas, cols);

        // Recorrer la matriz y crear celdas
        int contadorCasillas = 0;

        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < cols; col++) {
                
                Casilla casillaActual = matriz[fila][col];
                if (casillaActual == null) continue;

                // Se crea el StackPane (La casilla)
                StackPane celdaGrafica = crearCeldaGrafica(casillaActual, filas, cols);
                
                // Añadir al GridPane
                tableroGrid.add(celdaGrafica, col, fila);
                contadorCasillas++;
            }
        }
    }

    // Aqui es donde Grid sabe cuantas columnas y filas debe de crearse es como una cuadricula
    private void configurarGrid(int filas, int cols) {
        for (int i = 0; i < cols; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100.0 / cols);
            tableroGrid.getColumnConstraints().add(col);
        }

        for (int i = 0; i < filas; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / filas);
            tableroGrid.getRowConstraints().add(row);
        }
    }

    private StackPane crearCeldaGrafica(Casilla casilla, int filas, int cols) {
        StackPane celda = new StackPane();

        // Asignación de ID para los jugadores aqui las casillas se crean con ID 
        int idEspiral = casilla.getIndice() + 1;
        celda.setId("casilla_" + idEspiral);

        // Procesamiento de la imagen porque necesita una /(Se puede cambiar)
        String rutaImagen = casilla.getTipo().getRutaImagen();
        if (!rutaImagen.startsWith("/")) {
            rutaImagen = "/" + rutaImagen;
        }
        
        //StackPane admite capas una de ellas es el ImageView donde podemos agregar la imagen
        try {
            InputStream is = getClass().getResourceAsStream(rutaImagen);
            if (is != null) {
                Image img = new Image(is);
                ImageView vistaImagen = new ImageView(img);

                // Ajustes de imagen (Responsive) Ajustes para que el tamaño sea el de la casilla
                vistaImagen.setPreserveRatio(false);
                vistaImagen.fitWidthProperty().bind(tableroGrid.widthProperty().divide(cols));
                vistaImagen.fitHeightProperty().bind(tableroGrid.heightProperty().divide(filas));

                celda.getChildren().add(vistaImagen);
            } else {
                System.err.println("Imagen no encontrada: " + rutaImagen);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return celda;
    }
    
    //Puedes cambiar el id arriba puse un ejemplo era para probar que se puede poner un color arriba de una casilla para jugador
    private void aplicarEfectoRojo(String selectorCasilla) {
        StackPane casillaObjetivo = (StackPane) tableroGrid.lookup(selectorCasilla);

        if (casillaObjetivo != null) {
            // Crear la capa roja
            Region capaRoja = new Region();
            capaRoja.setStyle("-fx-background-color: rgba(255, 0, 0, 0.3); -fx-background-radius: 10;");
            capaRoja.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            // Aplicar margen dentro del StackPane
            StackPane.setMargin(capaRoja, new Insets(5));

            // Añadir al StackPane
            casillaObjetivo.getChildren().add(capaRoja);
            casillaObjetivo.setStyle("-fx-border-color: red; -fx-border-width: 2px;");

            lb1.setText("Éxito: " + selectorCasilla + " modificada.");
        } else {
            lb1.setText("Fallo: No se encontró " + selectorCasilla);
        }
    }
}