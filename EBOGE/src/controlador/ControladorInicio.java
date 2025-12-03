package controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Label;

public class ControladorInicio  implements Initializable{
	
	private static final String RUTA_VISTA_CONFIGURACION = ""; //para el menu de configuracion 
    private static final String RUTA_CARPETA_MUSICA = "/musica";
    private static final String TEMA_PRINCIPAL = "Tema Principal.mp3";
    
    private static final String ICONO_SONIDO_ACTIVO = "\uf028";
    private static final String ICONO_SONIDO_MUTE = "\uf026";
    
	
	@FXML
	private Button btnJugar;
	
	@FXML 
	private Button btnSalir;
	
	@FXML 
	private ToggleButton btnMusica;
	
	@FXML 
	private Label lblTitulo;
	
	@FXML 
	private ComboBox<String> cboxMusica;
	
	@FXML
	private AnchorPane menuInicial;

	private MediaPlayer reproductorMusica;
	
	private Runnable accionJugar;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnMusica.setText(ICONO_SONIDO_ACTIVO);
		
		iniciarMusicaDeFondo();
		animacionTitulo();		
		cargarListaCanciones();
        
		if (!cboxMusica.getItems().isEmpty()) {
	        String nombreASeleccionar = TEMA_PRINCIPAL.replace(".mp3", "");
	        
	        cboxMusica.getSelectionModel().select(nombreASeleccionar);
	        if (cboxMusica.getSelectionModel().isEmpty()) {
	             cboxMusica.getSelectionModel().select(0);
	        }
	    }
	}
	
	public void setOnJugar(Runnable accion) {
        this.accionJugar = accion;
    }
	
	@FXML
	public void cargarJuego(ActionEvent event) {
		if (accionJugar != null) {
            accionJugar.run();
        } else {
            System.err.println("Error: No se ha definido la acción para el botón Jugar.");
        }
	}
	
	@FXML 
	public void salirJuego(ActionEvent event) {
		javafx.application.Platform.exit();
	    System.exit(0);
	}
	
	@FXML 
	public void gestionarMusica(ActionEvent event) {
		alternarEstadoSonido();
	}
	
	
	private void animacionTitulo() {
		ScaleTransition scale = new ScaleTransition();
		scale.setNode(lblTitulo);
		scale.setDuration(Duration.millis(1000));
		scale.setCycleCount(TranslateTransition.INDEFINITE);
		scale.setInterpolator(Interpolator.EASE_OUT);
		scale.setByX(0.1);
		scale.setByY(0.1);
		scale.setAutoReverse(true);
		scale.play();
	}
		
	
	public void iniciarMusicaDeFondo() {
		reproducirMusica(TEMA_PRINCIPAL);
	}
	
		
	public void alternarEstadoSonido() {
		if(btnMusica.isSelected()) {
			reproductorMusica.setMute(true);
			btnMusica.setText(ICONO_SONIDO_MUTE); 
			
		} else {
			reproductorMusica.setMute(false);
			btnMusica.setText(ICONO_SONIDO_ACTIVO); 
		}
	}
	
	
	private void cargarListaCanciones() {
		try {	        
	        URL url = getClass().getResource(RUTA_CARPETA_MUSICA); 

	        if (url == null) {
	            System.err.println("No se encontro la carpeta dentro de la raiz");
	            return;
	        }
	        
	        File carpeta = new File(url.toURI());

	        if (!carpeta.exists() || !carpeta.isDirectory()) {
                return;
            }

            File[] archivos = carpeta.listFiles();
            if (archivos == null) {
                return;
            }
            
            for (File archivo : archivos) {
                agregarArchivoSiEsValido(archivo);
            }
            
            configurarListenerCombo(); //Para cambiar la musica 
            	        

	    } catch (URISyntaxException e) {
            System.err.println("Error de sintaxis en la URI de musica: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private void agregarArchivoSiEsValido(File archivo) {
		if (archivo.isFile() && archivo.getName().toLowerCase().endsWith(".mp3")) {	                    	
        	String nombreConExtension = archivo.getName();	                    	
        	String nombreSinExtension = nombreConExtension.substring(0, nombreConExtension.length() - 4);
        	
            cboxMusica.getItems().add(nombreSinExtension);
        }
	}
	
	private void configurarListenerCombo() {
		cboxMusica.setOnAction(event -> {
            String cancionSeleccionada = cboxMusica.getValue();
            if (cancionSeleccionada != null) {
            	String cancionConExtension = cancionSeleccionada + ".mp3";
                reproducirMusica(cancionConExtension);
            }
        });
	}
	
	private void reproducirMusica(String nombreArchivo) {
		// Guardar si estaba en mute
	    boolean estabaMuteado = (reproductorMusica != null) && reproductorMusica.isMute();

	    if (reproductorMusica != null) {
	        reproductorMusica.stop();
	        reproductorMusica.dispose();
	    }

	    try {	     	    	
	    	String rutaRecurso = "/musica/" + nombreArchivo; 	        
	        URL urlCancion = getClass().getResource(rutaRecurso);

	        if (urlCancion == null) {
	            System.err.println("No se encontró el archivo: " + rutaRecurso);
	            return;
	        }

	        String path = urlCancion.toExternalForm();	 
	        
	        Media audio = new Media(path);
	        
	        reproductorMusica = new MediaPlayer(audio);
	        reproductorMusica.setCycleCount(MediaPlayer.INDEFINITE);
	        reproductorMusica.setMute(estabaMuteado); // Restaurar mute
	        reproductorMusica.play();
	    } catch (Exception e) {
	        System.err.println("Error al cargar la música: " + nombreArchivo);
	        e.printStackTrace();
	    }	    
	}
}
