package modelo.jugador;

import modelo.Dado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias: Clase Jugador")
class JugadorTest {

    private Jugador jugador;

    @BeforeEach
    void setUp() {
        // Arrange: Estado inicial común
        jugador = new Jugador("DarthNetza", ColorJugador.ROJO);
    }

    @Nested
    @DisplayName("Validaciones del Constructor")
    class ConstructorTests {

        @Test
        @DisplayName("Debe crear un jugador con valores iniciales correctos")
        void testConstructorExitoso() {
            assertAll("Estado inicial",
                () -> assertEquals("DarthNetza", jugador.getNombre()),
                () -> assertEquals(ColorJugador.ROJO.getCodigoHex(), jugador.getColor()),
                () -> assertEquals(0, jugador.getPosicion(), "Posición inicial debe ser 0"),
                () -> assertEquals(EstadoJugador.NORMAL, jugador.getEstado(), "Estado inicial debe ser NORMAL"),
                () -> assertTrue(jugador.getEfectosActivos().isEmpty(), "No debe tener efectos al inicio"),
                () -> assertEquals(0, jugador.getUltimoMovimiento()),
                () -> assertFalse(jugador.requiereActivacion())
            );
        }

        @Test
        @DisplayName("Debe lanzar excepción si el nombre es nulo o vacío")
        void testNombreInvalido() {
            assertThrows(IllegalArgumentException.class, () -> new Jugador(null, ColorJugador.AZUL));
            assertThrows(IllegalArgumentException.class, () -> new Jugador("", ColorJugador.AZUL));
            assertThrows(IllegalArgumentException.class, () -> new Jugador("   ", ColorJugador.AZUL));
        }

        @Test
        @DisplayName("Debe lanzar excepción si el color es nulo")
        void testColorInvalido() {
            assertThrows(NullPointerException.class, () -> new Jugador("Player1", null));
        }
    }

    @Nested
    @DisplayName("Lógica de Movimiento")
    class MovimientoTests {

        @Test
        @DisplayName("Debe mover al jugador correctamente dentro de los límites")
        void testMoverNormal() {
            // Act
            jugador.mover(5, 100);

            // Assert
            assertEquals(5, jugador.getPosicion());
            assertEquals(5, jugador.getUltimoMovimiento());
        }

        @Test
        @DisplayName("Debe respetar el límite inferior (0)")
        void testMoverLimiteInferior() {
            // Arrange
            jugador.setPosicion(2);

            // Act: Intentar retroceder 5 casillas
            jugador.mover(-5, 100);

            // Assert
            assertEquals(0, jugador.getPosicion(), "No debe bajar de 0");
        }

        @Test
        @DisplayName("Debe respetar el límite superior (totalCasillas - 1)")
        void testMoverLimiteSuperior() {
            // Arrange
            int totalCasillas = 50;
            jugador.setPosicion(45);

            // Act: Intentar avanzar más allá del final
            jugador.mover(10, totalCasillas);

            // Assert
            assertEquals(49, jugador.getPosicion(), "Debe quedarse en la última casilla (49)");
        }
    }

    @Nested
    @DisplayName("Interacción con el Dado")
    class DadoTests {

        @Test
        @DisplayName("Debe lanzar el dado y devolver un resultado válido")
        void testLanzarDado() {
            Dado dado = new Dado(6);
            int resultado = jugador.lanzarDado(dado);
            assertTrue(resultado >= 1 && resultado <= 6);
        }

        @Test
        @DisplayName("Debe aplicar 'Movimiento Doble' si tiene el efecto activo")
        void testLanzarDadoConBuff() {
            // Arrange: Creamos una implementación anónima del efecto para probar la lógica
            EfectoJugador buffDoble = new EfectoJugador() {
                public void aplicarEfectoInicial(Jugador j) {}
                public void actualizarEfectoPorTurno(Jugador j) {}
                public void removerEfecto(Jugador j) {}
                public boolean haExpirado() { return false; }
                public String getNombre() { return "Movimiento Doble"; } 
            };
            jugador.aplicarEfecto(buffDoble);

            // Mock del dado para controlar el resultado
            Dado dadoFalso = new Dado(6) {
                @Override
                public int lanzar() { return 3; }
            };

            // Act
            int resultado = jugador.lanzarDado(dadoFalso);

            // Assert
            assertEquals(6, resultado, "El resultado debería duplicarse (3 * 2 = 6)");
        }
        
        @Test
        @DisplayName("Debe lanzar excepción si el dado es nulo")
        void testDadoNulo() {
            assertThrows(NullPointerException.class, () -> jugador.lanzarDado(null));
        }
    }

    @Nested
    @DisplayName("Gestión de Efectos (Strategy & State)")
    class EfectosTests {

        @Test
        @DisplayName("Aplicar efecto debe agregarlo a la lista y ejecutar su acción inicial")
        void testAplicarEfecto() {
            EfectoStun stun = new EfectoStun(2);
            
            jugador.aplicarEfecto(stun);

            assertEquals(1, jugador.getEfectosActivos().size());
            assertEquals(EstadoJugador.INMOVILIZADO, jugador.getEstado(), "El efecto Stun debe cambiar el estado");
            assertTrue(jugador.estaInmovilizado());
        }

        @Test
        @DisplayName("Actualizar efectos debe reducir turnos y limpiar expirados")
        void testCicloDeVidaEfecto() {
            // Arrange: Stun de 1 turno
            EfectoStun stun = new EfectoStun(1);
            jugador.aplicarEfecto(stun);
            assertEquals(EstadoJugador.INMOVILIZADO, jugador.getEstado());

            // Act: Pasar turno
            jugador.actualizarEfectos();

            // Assert: El efecto expiró, se removió y restauró el estado
            assertTrue(jugador.getEfectosActivos().isEmpty(), "La lista debe estar vacía");
            assertEquals(EstadoJugador.NORMAL, jugador.getEstado(), "El estado debe volver a NORMAL");
        }
    }
}