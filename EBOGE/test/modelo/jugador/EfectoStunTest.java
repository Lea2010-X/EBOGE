package modelo.jugador;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias: Efecto Stun")
class EfectoStunTest {

    private Jugador jugador;

    @BeforeEach
    void setUp() {
        jugador = new Jugador("TestPlayer", ColorJugador.BLANCO);
    }

    @Test
    @DisplayName("Constructor debe limitar los turnos entre 1 y 3")
    void testLimitesTurnos() {
        // Caso < 1
        EfectoStun stunCorto = new EfectoStun(-5);
        stunCorto.actualizarEfectoPorTurno(jugador);
        assertTrue(stunCorto.haExpirado(), "Debe durar al menos 1 turno (se reduce y expira)");

        // Caso > 3
        EfectoStun stunLargo = new EfectoStun(10);
        // Simulamos 3 turnos
        stunLargo.actualizarEfectoPorTurno(jugador);
        stunLargo.actualizarEfectoPorTurno(jugador);
        stunLargo.actualizarEfectoPorTurno(jugador);
        assertTrue(stunLargo.haExpirado(), "Debe durar máximo 3 turnos");
    }

    @Test
    @DisplayName("removerEfecto no debe cambiar estado si hay otro Stun activo")
    void testMultiplesStuns() {
        // Arrange
        EfectoStun stun1 = new EfectoStun(1);
        EfectoStun stun2 = new EfectoStun(2);

        jugador.aplicarEfecto(stun1);
        jugador.aplicarEfecto(stun2); 

        // Act: Actualizar (stun1 expira)
        jugador.actualizarEfectos();

        // Assert
        assertEquals(1, jugador.getEfectosActivos().size(), "Debe quedar 1 efecto");
        assertEquals(EstadoJugador.INMOVILIZADO, jugador.getEstado(), "El jugador debe seguir inmovilizado por el segundo efecto");
        assertTrue(jugador.estaInmovilizado());
    }
    
    @Test
    @DisplayName("removerEfecto debe restaurar a NORMAL si es el último Stun")
    void testUltimoStun() {
        // Arrange
        EfectoStun stun = new EfectoStun(1);
        jugador.aplicarEfecto(stun);
        
        // Act
        jugador.actualizarEfectos();
        
        // Assert
        assertEquals(EstadoJugador.NORMAL, jugador.getEstado());
    }
}