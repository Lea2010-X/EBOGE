package modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias: Clase Dado")
class DadoTest {

    @Test
    @DisplayName("Debe crear un dado con número de caras válido (3-30)")
    void testConstructorValido() {
        assertDoesNotThrow(() -> new Dado(3));
        assertDoesNotThrow(() -> new Dado(30));
        
        Dado d = new Dado(6);
        assertEquals(6, d.getCaras());
    }

    @Test
    @DisplayName("Debe lanzar excepción si las caras están fuera de rango")
    void testConstructorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Dado(2), "Mínimo 3 caras");
        assertThrows(IllegalArgumentException.class, () -> new Dado(31), "Máximo 30 caras");
    }

    @Test
    @DisplayName("El lanzamiento debe estar dentro del rango [1, caras]")
    void testLanzamiento() {
        Dado dado = new Dado(10);
        
        for (int i = 0; i < 50; i++) {
            int resultado = dado.lanzar();
            assertTrue(resultado >= 1 && resultado <= 10, "Resultado fuera de rango: " + resultado);
        }
    }
}