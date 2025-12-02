package modelo.mapa;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import modelo.mapa.Casilla;
import modelo.mapa.TipoCasilla;

class CasillaTest {
	
	TipoCasilla tipoEsperado;
	int indiceEsperado;
	Casilla casilla;
	
	@BeforeEach void setUp() {
		tipoEsperado = TipoCasilla.INICIO; 
		indiceEsperado = 0;
		casilla = new Casilla(tipoEsperado, indiceEsperado);
		
	}
	
	@Test
    @DisplayName("El constructor debe asignar correctamente el tipo y el indice")
    void constructorTest() {
        assertAll("Propiedades iniciales",
            () -> assertEquals(tipoEsperado, casilla.getTipo()),
            () -> assertEquals(indiceEsperado, casilla.getIndice())
        );
    }

    @Test
    @DisplayName("Los setters deben actualizar los valores correctamente")
    void testSetters() {
        casilla.setTipo(TipoCasilla.TRAMPA);
        casilla.setIndice(99);

        assertEquals(TipoCasilla.TRAMPA, casilla.getTipo(), "El tipo debería haber cambiado a TRAMPA");
        assertEquals(99, casilla.getIndice(), "El índice debería ser 99");
    }

}
