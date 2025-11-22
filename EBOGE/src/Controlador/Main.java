package Controlador;

import java.util.ArrayList;
import java.util.List;

import Modelo.GeneradorDeMapa;
import Modelo.Jugador;
import Modelo.Mapa;
import Modelo.Partida;
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
        
        double anchoGrid = controlador.getAnchoTablero(); 
        double altoGrid = controlador.getAltoTablero();
     
        GeneradorDeMapa generador = new GeneradorDeMapa();
        
        int ladosDado = 6; //Lados de dado no implementado
        
        List<Jugador> jugadores = new ArrayList<>();
        //Lista de jugadores no implementado
        
        Mapa mapaGenerado = generador.generarMapa((int)anchoGrid, (int)altoGrid, ladosDado);
        Partida partida = new Partida(jugadores, ladosDado, mapaGenerado);
        controlador.setPartida(partida);
        
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