package com.example;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;


@DisplayName("Pruebas de la Clase Dado")
class DadoTest {

    @Nested
    @DisplayName("Constructor (Validación RF-02)")
    class ConstructorRF02 {

        @Test
        @DisplayName("Debe crear un dado con 6 caras")
        void testConstructorDadoNormal() {
            // Arrange & Act
            Dado dado = new Dado(6);
            
            // Assert
            assertEquals(6, dado.getCaras());
        }

        @Test
        @DisplayName("Debe fallar si las caras son < 3")
        void testConstructorMenosDe3Caras() {
            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> {
                new Dado(2); // Límite inferior es 3 (según RF-02)
            }, "Debe fallar, RF-02 dice min 3 caras");
        }

        @Test
        @DisplayName("Debe fallar si las caras son > 30")
        void testConstructorMasDe30Caras() {
            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> {
                new Dado(31); // Límite superior es 30 (según RF-02)
            }, "Debe fallar, RF-02 dice max 30 caras");
        }

        @Test
        @DisplayName("Debe permitir los límites (3 y 30)")
        void testConstructorEnLimites() {
            // Act & Assert
            assertDoesNotThrow(() -> new Dado(3));
            assertDoesNotThrow(() -> new Dado(30));
        }
    }

    
    @RepeatedTest(100)
    @DisplayName("lanzar() debe devolver un valor siempre dentro del rango [1, Caras]")
    void testLanzarDentroDeRango() {
        // Arrange
        Dado dado = new Dado(10); 

        // Act
        int resultado = dado.lanzar();

        // Assert
        assertTrue(resultado >= 1, "El resultado no puede ser menor que 1");
        assertTrue(resultado <= 10, "El resultado no puede ser mayor que 10");
    }
}