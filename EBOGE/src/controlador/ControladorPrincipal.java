package controlador;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import modelo.MotorDeTurnos;
import modelo.Partida;
import modelo.jugador.ColorJugador;
import modelo.jugador.Jugador;
import modelo.mapa.GeneradorDeMapa;
import modelo.mapa.Mapa;

public class ControladorPrincipal {

    private static final String RUTA_FUENTE_PIXEL = "/fuentes/PressStart2P-Regular.ttf";
    private static final String RUTA_FUENTE_ICONOS = "/fuentes/Font Awesome 7 Free-Solid-900.otf";

    private static final String RUTA_VISTA_INICIO = "/vista/Inicio.fxml";
    
    private static final String RUTA_VISTA_CONFIGURACION = "/vista/ConfiguracionJugador.fxml";
    private static final String RUTA_VISTA_CARGAR_PARTIDA = "/vista/PantallaCarga.fxml";
    private static final String RUTA_VISTA_PARTIDA = "/vista/VentanaDePartida.fxml";

    private static final String TITULO_VENTANA_PRINCIPAL = "EBOGE - Epic Board Game Evolution";

    private static final double TAMANIO_FUENTE_BASE = 10.0;

    private Stage ventanaPrincipal;
    private Partida partida;

    
    private List<Jugador> jugadoresPartida;
    private int carasDadoPartida;

    public ControladorPrincipal() {
    }

    public void iniciarEBOGE(Stage stage) throws IOException {
        this.ventanaPrincipal = stage;
        mostrarVentanaDeInicio();
    }

    public void mostrarVentanaDeInicio() {
        try {
            cargarFuentesGlobales();

            // 1. Usamos inicializarInterfaz.
            FXMLLoader loader = inicializarInterfaz(ventanaPrincipal, RUTA_VISTA_INICIO, TITULO_VENTANA_PRINCIPAL);

            // 2. Conectamos el controlador
            ControladorInicio controladorInicio = loader.getController();
            controladorInicio.setControladorPrincipal(this);
            
            // Nota: La navegación a configuración se hace desde el FXML de Inicio

        } catch (Exception e) {
            System.err.println("Error critico al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public void mostrarVentanaDeConfiguracion() {
        try {
            FXMLLoader loader = inicializarInterfaz(ventanaPrincipal, RUTA_VISTA_CONFIGURACION, "EBOGE - Configuración");
            ControladorConfiguracion configController = loader.getController();
            configController.setControladorPrincipal(this); 
            
        } catch (Exception e) {
            System.err.println("Error al cargar la configuración: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void configurarPartida(List<Jugador> jugadores, int carasDado) {
        this.jugadoresPartida = jugadores;
        this.carasDadoPartida = carasDado;
        System.out.println("Partida configurada con " + jugadores.size() + " jugadores y dado de " + carasDado + " caras.");
    }

    public void mostrarVentanaDeCarga() {
        try {

            FXMLLoader loader = inicializarInterfaz(ventanaPrincipal, RUTA_VISTA_CARGAR_PARTIDA, "EBOGE - Cargando");

            ControladorPantallaCarga cargaController = loader.getController();
            
            // Usamos Platform.runLater para asegurar que la transición de ventana 
            // ocurra en el hilo de aplicación de JavaFX al finalizar la carga.
            cargaController.setOnCargaTerminada(() -> {
                Platform.runLater(this::mostrarVentanaDePartida);
            });

        } catch (Exception e) {
            System.err.println("Error critico al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarVentanaDePartida() {
        try {
            FXMLLoader loader = inicializarInterfaz(ventanaPrincipal, RUTA_VISTA_PARTIDA, "EBOGE - Partida");
            ControladorVentanaDePartida controladorPartida = loader.getController();
            
            /* * Es necesario esperar a que el layout de JavaFX termine de calcular 
             * las dimensiones del Grid (tablero) antes de generar el mapa lógico.
             * Sin runLater, el ancho/alto podrían ser 0.0.
             */
            Platform.runLater(() -> {
                double anchoTablero = controladorPartida.getAnchoTablero();
                double altoTablero = controladorPartida.getAltoTablero();

                System.out.println("Ancho del Tablero: " + anchoTablero);
                System.out.println("Alto del Tablero: " + altoTablero);

                
                iniciarPartidaNueva((int) anchoTablero, (int) altoTablero);

                MotorDeTurnos motor = new MotorDeTurnos(partida);
                controladorPartida.inicializarConMotor(motor);
            });

        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    private void iniciarPartidaNueva(int anchoTablero, int altoTablero) {
        
        if (jugadoresPartida == null || jugadoresPartida.isEmpty()) {
            throw new IllegalStateException("CRÍTICO: Se intentó iniciar partida sin jugadores configurados.");
        }

        GeneradorDeMapa generador = new GeneradorDeMapa();
        Mapa mapaGenerado = generador.generarMapa(anchoTablero, altoTablero, carasDadoPartida);

        this.partida = new Partida(jugadoresPartida, carasDadoPartida, mapaGenerado);
    }


  
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

    private void aplicarParchePantallaCompleta(Stage stage) {
        stage.setResizable(true);
        
        if (stage.isShowing()) {
            stage.setMaximized(false);
            stage.setMaximized(true);
        } else {
            stage.setMaximized(true);
        }
    }
    
    private FXMLLoader inicializarInterfaz(Stage escenario, String rutaEscena, String titulo) throws IOException {
        if (getClass().getResource(rutaEscena) == null) {
            throw new IOException("No se encontró el archivo FXML en: " + rutaEscena);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaEscena));
        Parent raiz = loader.load();

        Scene escena = new Scene(raiz);
		escenario.setTitle(titulo);
		escenario.setScene(escena);
		aplicarParchePantallaCompleta(ventanaPrincipal);
	    escenario.show();

        return loader; 
    }
}