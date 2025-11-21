package Controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Vista/VentanaDePartida.fxml")
        );

        Parent root = loader.load();

        ControladorVentanaDePartida controlador = loader.getController(); 

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Partida");


        stage.setMaximized(true);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}