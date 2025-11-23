package modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import modelo.Dado;
import modelo.jugador.ColorJugador;
import modelo.jugador.EfectoJugador;
import modelo.jugador.EfectoStun;
import modelo.jugador.EstadoJugador;
import modelo.jugador.Jugador;



@DisplayName("Pruebas de la Clase Jugador")
class JugadorTest {

    private Jugador jugador;

    
    @BeforeEach
    void setUp() {
        jugador = new Jugador("Carlos", ColorJugador.ROJO);
    }

    
    @Nested
    @DisplayName("Constructor y Estado Inicial")
    class ConstructorYEstadoInicial {

        @Test
        @DisplayName("Debe crear un jugador con estado inicial correcto")
        void testConstructorEstadoInicial() {
            // Assert (Verificar)
            assertEquals("Carlos", jugador.getNombre(), "El nombre debe ser 'Carlos'");
            assertEquals(ColorJugador.ROJO, jugador.getColor(), "El color debe ser ROJO");
            assertEquals(0 , jugador.getPosicion(), "La posición inicial debe ser 0");
            assertEquals(EstadoJugador.NORMAL, jugador.getEstado(), "El estado inicial debe ser NORMAL");
            assertTrue(jugador.getEfectosActivos().isEmpty(), "La lista de efectos debe empezar vacía");
            assertFalse(jugador.estaInmovilizado(), "El jugador no debe empezar inmovilizado");
        }

        @Test
        @DisplayName("Debe lanzar IllegalArgumentException con nombre nulo")
        void testConstructorNombreNulo() {
            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> {
                new Jugador(null, ColorJugador.AZUL);
            }, "El constructor debe validar un nombre nulo");
        }

        @Test
        @DisplayName("Debe lanzar IllegalArgumentException con nombre vacío")
        void testConstructorNombreVacio() {
            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> {
                new Jugador("   ", ColorJugador.VERDE); // Nombre solo con espacios
            }, "El constructor debe validar un nombre vacío o en blanco");
        }

        @Test
        @DisplayName("Debe lanzar NullPointerException con color nulo")
        void testConstructorColorNulo() {
            // Act & Assert
            assertThrows(NullPointerException.class, () -> {
                new Jugador("Ana", null);
            }, "El constructor debe validar un color nulo");
        }
    }

    
    @Nested
    @DisplayName("Patrón Strategy (Gestión de Efectos)")
    class GestionDeEfectos {

        @Test
        @DisplayName("aplicarEfecto debe añadir un EfectoStun y cambiar estado")
        void testAplicarEfectoStun() {
            // Arrange
            EfectoJugador stun = new EfectoStun(3); // 3 turnos

            // Act
            jugador.aplicarEfecto(stun);

            // Assert
            assertEquals(EstadoJugador.INMOVILIZADO, jugador.getEstado(), "El estado debe cambiar a INMOVILIZADO");
            assertTrue(jugador.estaInmovilizado(), "El método estaInmovilizado() debe ser true");
            assertEquals(1, jugador.getEfectosActivos().size(), "El efecto debe estar en la lista");
            assertTrue(jugador.getEfectosActivos().contains(stun), "La lista debe contener el efecto stun");
        }

        @Test
        @DisplayName("actualizarEfectos debe reducir turnos pero mantener el efecto")
        void testActualizarEfectosUnTurno() {
            // Arrange
            EfectoJugador stun = new EfectoStun(3); // 3 turnos
            jugador.aplicarEfecto(stun);

            // Act
            jugador.actualizarEfectos(); // Pasa 1 turno

            // Assert
            assertEquals(EstadoJugador.INMOVILIZADO, jugador.getEstado(), "El estado debe seguir INMOVILIZADO");
            assertEquals(1, jugador.getEfectosActivos().size(), "El efecto aún debe estar en la lista");
        }

        @Test
        @DisplayName("actualizarEfectos debe remover el efecto cuando expira")
        void testActualizarEfectosHastaExpirar() {
            // Arrange
            EfectoJugador stun = new EfectoStun(1); // 1 turno
            jugador.aplicarEfecto(stun);
            assertEquals(EstadoJugador.INMOVILIZADO, jugador.getEstado(), "Pre-check: El jugador está inmovilizado");

            // Act
            jugador.actualizarEfectos(); // Pasa 1 turno, el efecto expira

            // Assert
            assertEquals(EstadoJugador.NORMAL, jugador.getEstado(), "El estado debe volver a NORMAL");
            assertTrue(jugador.getEfectosActivos().isEmpty(), "La lista de efectos debe quedar vacía");
            assertFalse(jugador.estaInmovilizado(), "El jugador ya no debe estar inmovilizado");
        }

        @Test
        @DisplayName("removerEfecto no debe cambiar estado si hay otro Stun")
        void testRemoverEfectoConStunDuplicado() {
            // Arrange
            EfectoJugador stunA = new EfectoStun(1); 
            EfectoJugador stunB = new EfectoStun(2); 
            jugador.aplicarEfecto(stunA);
            jugador.aplicarEfecto(stunB); 

            // Act
            jugador.actualizarEfectos(); 

            // Assert
            assertEquals(EstadoJugador.INMOVILIZADO, jugador.getEstado(), "El estado NO debe cambiar a NORMAL, porque stunB sigue activo");
            assertEquals(1, jugador.getEfectosActivos().size(), "Solo debe quedar 1 efecto en la lista");
            assertTrue(jugador.getEfectosActivos().contains(stunB), "El efecto restante debe ser stunB");
        }
    }

    
    @Nested
    @DisplayName("Lógica de Lanzar Dado")
    class LanzarDado {

        @Test
        @DisplayName("lanzarDado debe devolver un valor dentro del rango")
        void testLanzarDadoNormal() {
            // Arrange
            Dado dado = new Dado(6); // Dado de 6 caras

            // Act
            int resultado = jugador.lanzarDado(dado);

            // Assert
            assertTrue(resultado >= 1 && resultado <= 6, "El resultado (" + resultado + ") debe estar entre 1 y 6");
        }

        @Test
        @DisplayName("lanzarDado debe lanzar NullPointerException si el dado es nulo")
        void testLanzarDadoNulo() {
            // Act & Assert
            assertThrows(NullPointerException.class, () -> {
                jugador.lanzarDado(null);
            }, "Debe lanzar excepción si el dado es nulo");
        }

        @Test
        @DisplayName("lanzarDado debe duplicar el resultado si hay un Buff")
        void testLanzarDadoConBuff() {
            // Arrange
            
            EfectoJugador buffMovimientoDoble = new EfectoJugador() {
                public void aplicarEfectoInicial(Jugador j) {}
                public void actualizarEfectoPorTurno(Jugador j) {}
                public void removerEfecto(Jugador j) {}
                public boolean haExpirado() { return false; } // Nunca expira
                public String getNombre() { return "Movimiento Doble"; } // Nombre clave
            };
            jugador.aplicarEfecto(buffMovimientoDoble);

            
            Dado dadoFalso = new Dado(6) {
                @Override
                public int lanzar() {
                    return 4; 
                }
            };

            // Act
            int resultado = jugador.lanzarDado(dadoFalso);

            // Assert
            assertEquals(8, resultado, "El resultado debe ser 4 * 2 = 8");
        }
    }

    
    @Nested
    @DisplayName("Contrato de Equals y HashCode")
    class EqualsYHashCode {

        @Test
        @DisplayName("Dos jugadores son 'equals' si tienen el mismo nombre (ignora mayúsculas)")
        void testEqualsMismoNombre() {
            // Arrange
            Jugador jugador1 = new Jugador("David", ColorJugador.AZUL);
            Jugador jugador2 = new Jugador("david", ColorJugador.VERDE); // Mismo nombre, dif. caso y color

            // Act & Assert
            assertTrue(jugador1.equals(jugador2), "Los jugadores deben ser iguales por nombre");
            assertEquals(jugador1.hashCode(), jugador2.hashCode(), "Los hashCodes deben ser iguales");
        }

        @Test
        @DisplayName("Dos jugadores no son 'equals' si tienen diferente nombre")
        void testEqualsDiferenteNombre() {
            // Arrange
            Jugador jugador1 = new Jugador("Elena", ColorJugador.ROSA);
            Jugador jugador2 = new Jugador("Felipe", ColorJugador.ROSA);

            // Act & Assert
            assertFalse(jugador1.equals(jugador2), "Los jugadores no deben ser iguales");
        }
    }
}