package controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import modelo.jugador.ColorJugador;
import modelo.jugador.Jugador;

public class ControladorConfiguracion implements Initializable {

    @FXML private VBox contenedorJugadores;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<ColorJugador> cboxColor;
    @FXML private ComboBox<Integer> cboxDado;

    private List<Jugador> jugadoresConfigurados;
    private ControladorPrincipal controladorPrincipal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        jugadoresConfigurados = new ArrayList<>();
        
        cboxColor.getItems().addAll(ColorJugador.values());
        
        for (int i = 3; i <= 30; i++) {
            cboxDado.getItems().add(i);
        }
        
        cboxDado.getSelectionModel().select(Integer.valueOf(6)); 
    }

    public void setControladorPrincipal(ControladorPrincipal principal) {
        this.controladorPrincipal = principal;
    }


    @FXML
    public void agregarJugador() {
        String nombre = txtNombre.getText().trim();
        ColorJugador colorSeleccionado = cboxColor.getValue();

        if (validarEntrada(nombre, colorSeleccionado)) {
            
            Jugador nuevoJugador = new Jugador(nombre, colorSeleccionado);
            jugadoresConfigurados.add(nuevoJugador);

            
            agregarFilaVisual(jugadoresConfigurados.size(), nuevoJugador);
            
            
            txtNombre.clear();
            
            cboxColor.getItems().remove(colorSeleccionado);
            cboxColor.getSelectionModel().clearSelection();
            
            txtNombre.requestFocus();
        }
    }

    @FXML
    public void iniciarPartida() {
        
        if (jugadoresConfigurados.size() < 2) {
            mostrarAlerta("Faltan jugadores", "Se necesitan mínimo 2 jugadores para iniciar la partida.");
            return;
        }

        int carasDado = cboxDado.getValue();
        
        if (controladorPrincipal != null) {
            controladorPrincipal.configurarPartida(jugadoresConfigurados, carasDado);
            controladorPrincipal.mostrarVentanaDeCarga();
        } else {
            System.err.println("Error: ControladorPrincipal no está conectado.");
        }
    }

    private void agregarFilaVisual(int numero, Jugador jugador) {
        HBox fila = new HBox(15); 
        fila.setAlignment(Pos.CENTER_LEFT);
        fila.setStyle("-fx-padding: 5;");

        
        Label lblNum = new Label(numero + "");
        lblNum.getStyleClass().add("texto-lista-pixel");

        
        Label lblNom = new Label(jugador.getNombre());
        lblNom.getStyleClass().add("texto-lista-pixel");
        lblNom.setPrefWidth(150); 

        
        Pane colorBox = new Pane();
        colorBox.setPrefSize(20, 20);
        
        
        String hexColor = jugador.getColor();
        
        
        colorBox.setStyle("-fx-background-color: " + hexColor + "; -fx-border-color: black; -fx-border-width: 2;");

        
        fila.getChildren().addAll(lblNum, lblNom, colorBox);
        contenedorJugadores.getChildren().add(fila);
    }

    


    private boolean validarEntrada(String nombre, ColorJugador color) {
        
        if (nombre.isEmpty()) {
            mostrarAlerta("Datos inválidos", "El nombre no puede estar vacío.");
            return false;
        }
        
        if (color == null) {
            mostrarAlerta("Datos inválidos", "Debes seleccionar un color para la ficha.");
            return false;
        }
        
        if (jugadoresConfigurados.size() >= 8) {
            mostrarAlerta("Límite alcanzado", "El máximo es de 8 jugadores.");
            return false;
        }
        
        for (Jugador j : jugadoresConfigurados) {
            if (j.getNombre().equalsIgnoreCase(nombre)) {
                mostrarAlerta("Nombre duplicado", "Ya existe un jugador con ese nombre.");
                return false;
            }
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("EBOGE");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
