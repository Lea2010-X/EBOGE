package controlador;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
import modelo.MotorDeTurnos;
import modelo.Partida;
import modelo.cartas.Carta;
import modelo.cartas.TipoCarta;
import modelo.jugador.Jugador;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import modelo.cartas.EfectoConObjetivo;
import modelo.cartas.EfectoSinObjetivo;

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
	
	private final Label lblMensajeEstado;

	public ControladorMazosYTurnos(Button btnPropio, Button btnObjetivo, Button btnGlobal, Button btnMaestro,
			Button btnLanzarDado, Label lblTurnoTexto, Label lblNombreJugador, Label lblResultadoDado,
			StackPane overlayCarta, Button btnCartaGrande, Label lblMensajeEstado) {

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
		
		this.lblMensajeEstado = lblMensajeEstado;

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
	
	private void notificarEvento(String mensaje) {
        if (lblMensajeEstado != null) {
            lblMensajeEstado.setText(mensaje);
        }
        System.out.println(mensaje);
    }

	public void actualizarLabelTurno(Jugador actual) {		
		lblTurnoTexto.setText("Turno de:");
		lblNombreJugador.setText(actual.getNombre());
		
		// Aplicamos el color del jugador al texto para mejor feedback visual
		String hex = actual.getColor();
		lblNombreJugador.setStyle("-fx-text-fill: " + hex + ";" + "-fx-font-size: 20px;" + "-fx-font-weight: bold;");
	}
	

	public void lanzarDado(MotorDeTurnos motor) {
		Partida partida = motor.getPartida();
		if (partida == null) return;

		btnLanzarDado.setDisable(true); // Evitamos doble clic accidental

		Jugador jugador = motor.iniciarTurno();
		int pasos = motor.lanzarDado();
		
		mostrarResultadosDado(jugador, pasos);
		
		motor.calcularNuevaPosicion(pasos);
		motor.ejecutarAccionEnCasilla();
		
		if (partida.getJuegoTerminado()) {
	        manejarVictoria(jugador);
	        return; 
	    }

	    verificarEfectosTrampa(jugador);	
	    gestionarFlujoDeCartas(motor, partida);
	}
	
	public void mostrarResultadosDado(Jugador jugador, int pasos) {
		if (lblResultadoDado != null) {
			lblResultadoDado.setText("Resultado: " + pasos);
		}
		notificarEvento(jugador.getNombre() + " avanzó " + pasos + " casillas.");
	}
	
	private void manejarVictoria(Jugador jugador) {
	    notificarEvento("¡VICTORIA! " + jugador.getNombre() + " HA GANADO LA PARTIDA ");
	    btnLanzarDado.setDisable(true); 
	}

	private void verificarEfectosTrampa(Jugador jugador) {
	    if (jugador.estaInmovilizado()) {
	        notificarEvento(jugador.getNombre() + " cayó en una trampa por " + jugador.getTurnosEfecto() + " turnos.");
	    }
	}

	private void gestionarFlujoDeCartas(MotorDeTurnos motor, Partida partida) {
	    TipoCarta tipoMazo = motor.getMazoHabilitado();
	    actualizarBotonesMazos(tipoMazo);

	    if (tipoMazo != null) {
	    	// Esperamos interacción del usuario para robar carta
	        notificarEvento("Puedes agarrar una carta de tipo " + tipoMazo);
	    } else if (!motor.hayCartaPendiente()) {
	    	// Si no hay carta que robar ni usar, el turno acaba automáticamente
	        terminarTurnoUI(motor, partida);
	    }
	}

	private void terminarTurnoUI(MotorDeTurnos motor, Partida partida) {
		ocultarCartaEnPantalla();
	    motor.terminarTurno(); 
	    actualizarLabelTurno(partida.getJugadorActual());
	    btnLanzarDado.setDisable(false);
	}

	public void abrirMazo(TipoCarta tipo, MotorDeTurnos motor) {
		if (motor == null) return;

		Carta carta = motor.robarCartaDeMazo(tipo);
		if (carta == null) {
			notificarEvento("No puedes robar una carta de tipo " + tipo + "en este momento");
			return;
		}
		
		notificarEvento("Has robado una carta de tipo  " + carta.getNombre());
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
		if (tipoMazo == null) return;

		switch (tipoMazo) {
		case PROPIA -> btnAbrirMazoPropio.setDisable(false);
		case BLANCO -> btnAbrirMazoObjetivo.setDisable(false);
		case GLOBAL -> btnAbrirMazoGlobal.setDisable(false);
		case MAESTRA -> btnAbrirMazoMaestro.setDisable(false);
		}
	}

	private void mostrarCartaEnPantalla(Carta carta) {
		if (overlayCarta == null || btnCartaGrande == null) return;

		overlayCarta.setVisible(true);
		overlayCarta.setManaged(true);

		String ruta = carta.getImagenRuta();
		if (ruta != null && !ruta.isBlank()) {
			String style = "-fx-background-image: url('" + ruta + "');" + "-fx-background-size: contain;"
					+ "-fx-background-repeat: no-repeat;" + "-fx-background-position: center;";
			btnCartaGrande.setStyle(style);
		}
		btnLanzarDado.setDisable(true); // Bloqueo mientras lee la carta
	}

	private void ocultarCartaEnPantalla() {
		if (overlayCarta != null) {
			overlayCarta.setVisible(false);
			overlayCarta.setManaged(false);
		}
	}
	
	
	public void usarCartaPendiente(MotorDeTurnos motor) {
	    if (motor == null) return;
	    Partida partida = motor.getPartida();

	    if (!motor.hayCartaPendiente()) {
	        ocultarCartaEnPantalla();
	        btnLanzarDado.setDisable(false);
	        return;
	    }

	    Carta carta = motor.getCartaPendiente();

	    // CASO 1: Efecto directo
	    if (carta instanceof EfectoSinObjetivo) {
	        motor.usarCartaPendienteSinObjetivo();
	        terminarTurnoUI(motor, partida);
	        return;
	    }

	    // CASO 2: Diálogo de selección
	    if (carta instanceof EfectoConObjetivo) {
	        List<Jugador> objetivos = new ArrayList<>(partida.getJugadores());
	        objetivos.remove(partida.getJugadorActual());

	        if (objetivos.isEmpty()) { btnLanzarDado.setDisable(false); return; }

	        List<String> nombres = new ArrayList<>();
	        for (Jugador jugador : objetivos) nombres.add(jugador.getNombre());
	        
	        //Mostrar la ventana de selección
	        ChoiceDialog<String> dialog = new ChoiceDialog<>(nombres.get(0), nombres);
	        dialog.initStyle(StageStyle.UNDECORATED);
	        dialog.setHeaderText("Usando: " + carta.getNombre());
	        dialog.setContentText("Elige víctima:");
	        dialog.setGraphic(null);

	        //Cargamos el CSS de Partida
	        dialog.getDialogPane().getStylesheets().add(
	            getClass().getResource("/vista/Partida.css").toExternalForm()
	        );

	        dialog.getDialogPane().getStyleClass().add("dialogo-gamer");


	        Optional<String> result = dialog.showAndWait();
	        if (result.isPresent()) {
	            for (Jugador jugador : objetivos) {
	                if (jugador.getNombre().equals(result.get())) {
	                    motor.usarCartaPendienteConObjetivo(jugador);
	                    terminarTurnoUI(motor, partida);
	                    return;
	                }
	            }
	        }
	    }	   	    
	}
}
