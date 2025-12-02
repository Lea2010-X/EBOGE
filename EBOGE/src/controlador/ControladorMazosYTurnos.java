package controlador;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import modelo.MotorDeTurnos;
import modelo.Partida;
import modelo.cartas.Carta;
import modelo.cartas.TipoCarta;
import modelo.jugador.Jugador;

public class ControladorMazosYTurnos {

	private final Button btnAbrirMazoPropio;
	private final Button btnAbrirMazoObjetivo;
	private final Button btnAbrirMazoGlobal;
	private final Button btnAbrirMazoMaestro;
	private final Button btnLanzarDado;

	private final Label lblTurnoTexto;
	private final Label lblNombreJugador;
	private final Label lblResultadoDado;

	private final StackPane overlayCarta;
	private final Button btnCartaGrande;

	public ControladorMazosYTurnos(Button btnPropio, Button btnObjetivo, Button btnGlobal, Button btnMaestro,
			Button btnLanzarDado, Label lblTurnoTexto, Label lblNombreJugador, Label lblResultadoDado,
			StackPane overlayCarta, Button btnCartaGrande) {

		this.btnAbrirMazoPropio = btnPropio;
		this.btnAbrirMazoObjetivo = btnObjetivo;
		this.btnAbrirMazoGlobal = btnGlobal;
		this.btnAbrirMazoMaestro = btnMaestro;
		this.btnLanzarDado = btnLanzarDado;

		this.lblTurnoTexto = lblTurnoTexto;
		this.lblNombreJugador = lblNombreJugador;
		this.lblResultadoDado = lblResultadoDado;

		this.overlayCarta = overlayCarta;
		this.btnCartaGrande = btnCartaGrande;

		if (overlayCarta != null) {
			overlayCarta.setVisible(false);
			overlayCarta.setManaged(false);
		}

		deshabilitarTodosLosMazos();
	}

	public void estadoInicial() {
		if (lblTurnoTexto != null)
			lblTurnoTexto.setText("Turno de:");
		if (lblResultadoDado != null)
			lblResultadoDado.setText("-");
	}

	public void actualizarLabelTurno(Jugador actual) {
		
		lblTurnoTexto.setText("Turno de:");
		lblNombreJugador.setText(actual.getNombre());

		String hex = actual.getColor();
		lblNombreJugador.setStyle("-fx-text-fill: " + hex + ";" + "-fx-font-size: 20px;" + "-fx-font-weight: bold;");
	}

	public void lanzarDado(MotorDeTurnos motor) {


		Partida partida = motor.getPartida();
		if (partida == null)
			return;

		btnLanzarDado.setDisable(true);

		Jugador jugador = motor.iniciarTurno();
		int pasos = motor.lanzarDado();

		if (lblResultadoDado != null) {
			lblResultadoDado.setText("Resultado: " + pasos);
		}

		System.out.println("Turno de " + jugador.getNombre() + " | Dado: " + pasos);

		if (pasos <= 0) {

			motor.terminarTurno();
			actualizarLabelTurno(partida.getJugadorActual());
			btnLanzarDado.setDisable(false);
			return;
		}

		motor.calcularNuevaPosicion(pasos);
		motor.ejecutarAccionEnCasilla();

		TipoCarta tipoMazo = motor.getMazoHabilitado();
		actualizarBotonesMazos(tipoMazo);

		if (tipoMazo == null && !motor.hayCartaPendiente()) {
			motor.terminarTurno();
			actualizarLabelTurno(partida.getJugadorActual());
			btnLanzarDado.setDisable(false);
		} else if (tipoMazo != null) {
			System.out.println("Puedes agarrar una carta de tipo " + tipoMazo);
		}
	}

	public void abrirMazo(TipoCarta tipo, MotorDeTurnos motor) {
		if (motor == null)
			return;

		Carta carta = motor.robarCartaDeMazo(tipo);
		if (carta == null) {
			System.out.println("No puedes robar carta de tipo " + tipo + " en este momento.");
			return;
		}

		System.out.println("Has robado la carta: " + carta.getNombre());
		mostrarCartaEnPantalla(carta);
		deshabilitarTodosLosMazos();
	}

	private void deshabilitarTodosLosMazos() {
		btnAbrirMazoPropio.setDisable(true);
		btnAbrirMazoObjetivo.setDisable(true);
		btnAbrirMazoGlobal.setDisable(true);
		btnAbrirMazoMaestro.setDisable(true);
	}

	private void actualizarBotonesMazos(TipoCarta tipoMazo) {
		deshabilitarTodosLosMazos();
		if (tipoMazo == null)
			return;

		switch (tipoMazo) {
		case PROPIA -> btnAbrirMazoPropio.setDisable(false);
		case BLANCO -> btnAbrirMazoObjetivo.setDisable(false);
		case GLOBAL -> btnAbrirMazoGlobal.setDisable(false);
		case MAESTRA -> btnAbrirMazoMaestro.setDisable(false);
		}
	}

	private void mostrarCartaEnPantalla(Carta carta) {
		if (overlayCarta == null || btnCartaGrande == null)
			return;

		overlayCarta.setVisible(true);
		overlayCarta.setManaged(true);

		String ruta = carta.getImagenRuta();
		if (ruta != null && !ruta.isBlank()) {
			String style = "-fx-background-image: url('" + ruta + "');" + "-fx-background-size: contain;"
					+ "-fx-background-repeat: no-repeat;" + "-fx-background-position: center;";
			btnCartaGrande.setStyle(style);
		}

		btnLanzarDado.setDisable(true);
	}

	private void ocultarCartaEnPantalla() {
		if (overlayCarta != null) {
			overlayCarta.setVisible(false);
			overlayCarta.setManaged(false);
		}
	}

	public void usarCartaPendiente(MotorDeTurnos motor) {
		if (motor == null)
			return;

		Partida partida = motor.getPartida();

		if (!motor.hayCartaPendiente()) {
			System.out.println("No hay carta pendiente por usar.");
			ocultarCartaEnPantalla();
			btnLanzarDado.setDisable(false);
			return;
		}

		motor.usarCartaPendiente();
		ocultarCartaEnPantalla();
		motor.terminarTurno();
		actualizarLabelTurno(partida.getJugadorActual());
		btnLanzarDado.setDisable(false);
	}
}
