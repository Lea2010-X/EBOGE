package Controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;


public class ControladorVentanaDePartida implements Initializable {
	@FXML
	private Button btn1;

	@FXML
	private Label lb1;
	
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn1.setOnAction(e -> holaMundo());
    }
    
	@FXML
	private void holaMundo() {
		lb1.setText("hola mundo");
	}
}
