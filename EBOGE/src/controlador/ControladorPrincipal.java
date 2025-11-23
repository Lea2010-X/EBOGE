package controlador;

import java.io.IOException;
import java.io.InputStream;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import modelo.mapa.GeneradorDeMapa;
import modelo.mapa.Mapa;

public class ControladorPrincipal {
	
	private static final String RUTA_FUENTE_PIXEL = "/fuentes/PressStart2P-Regular.ttf";
    private static final String RUTA_FUENTE_ICONOS = "/fuentes/Font Awesome 7 Free-Solid-900.otf"; 
    
    private static final String RUTA_VISTA_INICIO = "/vista/Inicio.fxml"; 
    private static final String RUTA_VISTA_CONFIGURACION = "/vista/.fxml"; //Por implementar!!
    private static final String RUTA_VISTA_CARGAR_PARTIDA = "/vista/PantallaCarga.fxml";
    private static final String RUTA_VISTA_PARTIDA = "/vista/VentanaDePartida.fxml"; 
    
    
    private static final String TITULO_VENTANA_PRINCIPAL = "EBOGE - Epic Board Game Evolution";
    private static final String HINT_PANTALLA_COMPLETA = "";
        
    private static final double TAMANIO_FUENTE_BASE = 10.0; // necesario para cargar las fuentes

	private Stage ventanaPrincipal;

	public ControladorPrincipal() {

	}

	public void iniciarEBOGE(Stage stage, int ladosDelDado) throws IOException {

		this.ventanaPrincipal = stage;

		mostrarVentanaDeInicio();
		configurarPantallaCompleta(this.ventanaPrincipal);
	}

	public void mostrarVentanaDeInicio() {
		
		try {
            cargarFuentesGlobales();
            inicializarInterfaz(ventanaPrincipal, RUTA_VISTA_INICIO, TITULO_VENTANA_PRINCIPAL);
            
            
        } catch (Exception e) {
            System.err.println("Error critico al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
		
		
	}

	public void mostrarVentanaDeConfiguracion() {

	}

	public void mostrarVentanaDeCarga() {
		try {
            inicializarInterfaz(ventanaPrincipal, RUTA_VISTA_CARGAR_PARTIDA, TITULO_VENTANA_PRINCIPAL);           
        } catch (Exception e) {
            System.err.println("Error critico al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
		
	}

	private void mostrarVentanaDePartida(int ladosDelDado) throws IOException {

	    FXMLLoader loader = inicializarInterfaz(ventanaPrincipal, RUTA_VISTA_PARTIDA, "EBOGE - Partida");

	    ControladorVentanaDePartida controladorPartida = loader.getController();

	    Platform.runLater(() -> {
	        double anchoTablero = controladorPartida.getAnchoTablero();
	        double altoTablero = controladorPartida.getAltoTablero();

	        System.out.println("Ancho del Tablero: " + anchoTablero);
	        System.out.println("Alto del Tablero: " + altoTablero);

	        GeneradorDeMapa generadorDeMapa = new GeneradorDeMapa();
	        Mapa mapaGenerado = generadorDeMapa.generarMapa((int) anchoTablero, (int) altoTablero, ladosDelDado);

	        controladorPartida.dibujarTablero(mapaGenerado);
	    });
	}

	public void mostrarVentanaDeVictoria() {

	}
	
	
	/* Metodos para configurar la pantalla de inicio EBOGE */
	private void cargarFuentesGlobales() {
        cargarFuente(RUTA_FUENTE_PIXEL);
        cargarFuente(RUTA_FUENTE_ICONOS);
    }
	
	private void cargarFuente(String rutaRecurso) {
        try (InputStream fontStream = getClass().getResourceAsStream(rutaRecurso)) {
            if (fontStream != null) {
                Font.loadFont(fontStream, TAMANIO_FUENTE_BASE);
            } else {
                System.err.println("Advertencia: No se pudo encontrar la fuente en " + rutaRecurso);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el flujo de la fuente: " + rutaRecurso);
        }
    }
	
	private FXMLLoader inicializarInterfaz(Stage escenario, String rutaEscena, String titulo) throws IOException {
	    // Validar recurso
	    if (getClass().getResource(rutaEscena) == null) {
	        throw new IOException("No se encontró el archivo FXML en: " + rutaEscena);
	    }

	    // Usamos la instancia new FXMLLoader() en lugar del estático .load()
	    // para poder retornar el cargador y obtener el controlador después.
	    FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaEscena));
	    Parent raiz = loader.load();
	    
	    Scene escena = new Scene(raiz);

	    escenario.setTitle(titulo);
	    escenario.setScene(escena);
	    
	    escenario.setResizable(false);
	    
	    escenario.setFullScreenExitHint(HINT_PANTALLA_COMPLETA);
	    escenario.setFullScreen(true);
	    
	    
	    escenario.show();

	    return loader; 
	}
	
	private void configurarPantallaCompleta(Stage escenario) {
        escenario.focusedProperty().addListener((observable, valorAntiguo, valorNuevo) -> {
            // Si valorNuevo es true (recuperó el foco)
            if (Boolean.TRUE.equals(valorNuevo)) {
                // Usamos runLater para asegurar que el cambio de UI ocurra en el hilo correcto
                // y después de que el sistema operativo termine de procesar el cambio de foco.
                Platform.runLater(() -> escenario.setFullScreen(true));
            }
        });
    }
	
}