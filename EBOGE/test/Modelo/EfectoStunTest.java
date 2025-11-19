package Modelo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias para la estrategia concreta EfectoStun.
 * Verifica la lógica interna de conteo de turnos y expiración.
 */
@DisplayName("Pruebas de la Clase EfectoStun")
class EfectoStunTest {

    // Usamos un Jugador "Stub" solo como contexto para los métodos
    private Jugador jugadorContexto;
    private EfectoStun efectoStun;

    @BeforeEach
    void setUp() {
        jugadorContexto = new Jugador("Stub", ColorJugador.BLANCO);
    }

    @Nested
    @DisplayName("Constructor (Validación RF-14)")
    class ConstructorRF14 {

        @Test
        @DisplayName("Debe crearse con 3 turnos si se piden 3")
        void testConstructorTurnosNormales() {
            // Arrange & Act
            efectoStun = new EfectoStun(3);
            
            // Assert
            assertFalse(efectoStun.haExpirado(), "No debe estar expirado al crearse");
        }

        @Test
        @DisplayName("Debe limitar los turnos a 3 (Max RF-14) si se piden más")
        void testConstructorTurnosMaximo() {
            // Arrange
            efectoStun = new EfectoStun(10); // Se piden 10 (inválido)

            // Act
            efectoStun.actualizarEfectoPorTurno(jugadorContexto); // Pasa 1 turno
            efectoStun.actualizarEfectoPorTurno(jugadorContexto); // Pasa 2 turnos
            efectoStun.actualizarEfectoPorTurno(jugadorContexto); // Pasa 3 turnos

            // Assert
            assertTrue(efectoStun.haExpirado(), "Debe expirar al 3er turno, no al 10mo");
        }

        @Test
        @DisplayName("Debe limitar los turnos a 1 (Min RF-14) si se pide 0 o menos")
        void testConstructorTurnosMinimo() {
            // Arrange
            efectoStun = new EfectoStun(0); // Se piden 0 (inválido)

            // Act
            assertFalse(efectoStun.haExpirado(), "No debe estar expirado al crearse");
            efectoStun.actualizarEfectoPorTurno(jugadorContexto); // Pasa 1 turno

            // Assert
            assertTrue(efectoStun.haExpirado(), "Debe expirar al 1er turno");
        }
    }

    @Nested
    @DisplayName("Ciclo de Vida del Efecto")
    class CicloDeVida {

        @Test
        @DisplayName("aplicarEfectoInicial debe cambiar el estado a INMOVILIZADO")
        void testAplicarEfectoInicial() {
            // Arrange
            efectoStun = new EfectoStun(2);
            jugadorContexto.setEstado(EstadoJugador.NORMAL); // Estado inicial

            // Act
            efectoStun.aplicarEfectoInicial(jugadorContexto);

            // Assert
            assertEquals(EstadoJugador.INMOVILIZADO, jugadorContexto.getEstado());
        }

        @Test
        @DisplayName("actualizarEfectoPorTurno debe reducir turnos y expirar")
        void testActualizarTurnosYExpirar() {
            // Arrange
            efectoStun = new EfectoStun(2); // 2 turnos

            // Act 1
            efectoStun.actualizarEfectoPorTurno(jugadorContexto);
            
            // Assert 1
            assertFalse(efectoStun.haExpirado(), "No debe expirar en el turno 1");

            // Act 2
            efectoStun.actualizarEfectoPorTurno(jugadorContexto);

            // Assert 2
            assertTrue(efectoStun.haExpirado(), "Debe expirar en el turno 2");
        }

        @Test
        @DisplayName("removerEfecto debe cambiar estado a NORMAL")
        void testRemoverEfecto() {
            // Arrange
            efectoStun = new EfectoStun(1);
            jugadorContexto.setEstado(EstadoJugador.INMOVILIZADO); // Estado inicial

            // Act
            efectoStun.removerEfecto(jugadorContexto);

            // Assert
            assertEquals(EstadoJugador.NORMAL, jugadorContexto.getEstado());
        }
    }
}