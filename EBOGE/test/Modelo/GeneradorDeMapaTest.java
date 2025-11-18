package Modelo;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GeneradorDeMapaTest {

    private final String NOMBRE_ARCHIVO_MAPA = "Mapa.txt";
    private GeneradorDeMapa generador = new GeneradorDeMapa();
    private Mapa mapaGenerado; 


    private final int W = 800;
    private final int H = 400;
    private final int L = 6;


   
    @BeforeEach
    void generarMapa() {
        this.mapaGenerado = generador.generarMapa(W, H, L);
    }

    @AfterEach
    void eliminarArchivo() {
        new File(NOMBRE_ARCHIVO_MAPA).delete();
    }
    
    @Test
    void verificarExistenciaDelTxt() {
        File archivo = new File(NOMBRE_ARCHIVO_MAPA);
        assertTrue(archivo.exists(), "El archivo 'Mapa.txt' debe existir después de la generación.");
    }

    @Test
    void verificarFormatoYInicio() throws IOException {
       
        String primeraLinea = "";
        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO_MAPA))) {

            primeraLinea = lector.readLine();
        }
        
        assertNotNull(primeraLinea, "La primera línea del archivo no debe ser nula");
        assertTrue(primeraLinea.startsWith("[0,I]"),"La primera casilla debe comenzar con el formato [0,I]");
    }
}

