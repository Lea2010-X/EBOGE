package modelo.mapa;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class MapaTest {

    private Mapa mapa;

    @BeforeEach
    void setUp() {
        GeneradorDeMapa generador = new GeneradorDeMapa();
        
        mapa = generador.generarMapa(500, 500, 6);
    }

    @Test
    @DisplayName("Debe permitir buscar una casilla por su índice")
    void testIdentificarTipoDeCasilla() {
        TipoCasilla tipo = mapa.identificarTipoDeCasilla(0);
        assertNotNull(tipo);
        assertEquals(TipoCasilla.INICIO, tipo);
    }

    @Test
    @DisplayName("Debe retornar NULL si buscamos un índice fuera de rango")
    void testIdentificarIndiceInvalido() {
        int total = mapa.getTotalCasillas();
        
        // Probamos el índice siguiente al último (Out of bounds)
        assertNull(mapa.identificarTipoDeCasilla(total), "Debería ser null porque el índice excede el tamaño");
        
        assertNull(mapa.identificarTipoDeCasilla(-1));
    }

    @Test
    @DisplayName("Debe permitir modificar una casilla en tiempo de ejecución")
    void testModificarCasilla() {
        int indicePrueba = 1;
        

        mapa.modificarTipoDeCasilla(indicePrueba, TipoCasilla.TRAMPA);
        
        assertEquals(TipoCasilla.TRAMPA, mapa.identificarTipoDeCasilla(indicePrueba));
    }
    
    @Test
    @DisplayName("Verificar consistencia de dimensiones calculadas")
    void testDimensiones() {
        // Solo verificamos que los getters devuelvan datos coherentes > 0
        assertTrue(mapa.getAnchoCasilla() > 0);
        assertTrue(mapa.getLargoCasilla() > 0);
        assertTrue(mapa.getTotalCasillas() > 0);
        
        assertEquals(mapa.getTotalCasillas(), mapa.getAnchoMapa() * mapa.getLargoMapa());
    }
}
