package controlador;

import java.util.Scanner;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static int ladosDado;

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingresa los lados del dado: ");
		ladosDado = scanner.nextInt();

		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		ControladorPrincipal controladorPrincipal = new ControladorPrincipal();
		controladorPrincipal.iniciarEBOGE(stage, ladosDado);
	}
}