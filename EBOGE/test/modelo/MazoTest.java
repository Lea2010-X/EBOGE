package modelo;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import modelo.cartas.Carta;
import modelo.cartas.Mazo;
import modelo.cartas.TipoCarta;
import modelo.cartas.blanco.Plegaria;
import modelo.cartas.blanco.BoogieWoogie;
import modelo.cartas.global.DistorsionDelEntorno;
import modelo.cartas.global.Monzon;
import modelo.cartas.maestra.GrilletesDelAlma;
import modelo.cartas.maestra.CartaTemploDeLirios;
import modelo.cartas.propia.RegresoACasa;
import modelo.cartas.propia.Flash;

@DisplayName("Pruebas de la Clase Mazo")
public class MazoTest {
	
	private Mazo mazo;
	
	@Nested
    @DisplayName("Al Instanciar el Mazo")
    class Inicializacion {
	
	@DisplayName("Prueba Mazo no vacío")
	@ParameterizedTest(name = "{index} -> Verificando que el mazo {0} no este vacío")
	@EnumSource(TipoCarta.class)
    void testInicializacionNoVacia(TipoCarta tipo) {
        mazo = new Mazo(tipo);
        assertFalse(mazo.vacio(), "El mazo debería inicializarse con cartas, no estar vacío");
    }
	
	@DisplayName("Al Instanciar el mazo debe contener 10 cartas")
	@ParameterizedTest(name = "{index} -> Verificando que el mazo {0} tenga 10 cartas")
	@EnumSource(TipoCarta.class)
	void testMazoIniciaConDiezCartas(TipoCarta tipo) { 
	  

	    Mazo mazo = new Mazo(tipo);
	    
	    int esperado = 10;
	    int actual = mazo.getCantidadCartas();
	    
	    assertEquals(esperado, actual, 
	        "El mazo de tipo " + tipo + " debería inicializarse con 10 cartas.");
	}
	
	}
	
	@Nested
    @DisplayName("Al robar el Mazo")
    class MazoRobado {
		
	@DisplayName("Prueba Mazo rellena tras estar vacío")	
	@ParameterizedTest(name = "{index} -> Verificando que el mazo {0} se relleno")
	@EnumSource(TipoCarta.class)
    void testReabastecimientoAutomatico(TipoCarta tipo) {
        mazo = new Mazo(tipo);
        

        for (int i = 0; i < 10; i++) {
            mazo.robar();
        }

        Carta cartaExtra = mazo.robar();
        
        assertNotNull(cartaExtra, "El mazo debería haberse rellenado automáticamente y devolver una carta");
        assertFalse(mazo.vacio(), "El mazo no debería estar vacío después de rellenarse");
    }
	
	@DisplayName("Prueba Mazo debe vaciarse tras 10 robos")
	@ParameterizedTest(name = "{index} -> Verificando que el mazo {0} este vacío")
	@EnumSource(TipoCarta.class)
	void testMazoDebelEstarVacioAlRobarSuCapacidad(TipoCarta tipo) {

	    Mazo mazo = new Mazo(tipo);
	    int capacidadInicial = 10; 


	    for (int i = 0; i < capacidadInicial; i++) {
	        mazo.robar(); 
	    }
	    assertAll("Verificación del proceso de rellenado automático",
	        
	    	() -> assertEquals(0,mazo.getCantidadCartas(), "La cantidad de cartas despues de robar 10 veces deberia ser 0"),
	    	
	        () -> assertTrue(mazo.vacio(), "El mazo debería estar vacío después de robar 10 veces.")
	        );


	}
	
	@DisplayName("Prueba el mazo debe de devolver el tipo de carta correcta")
	@ParameterizedTest(name = "{index} -> Verificando que el mazo {0} devuelva el tipo correcto")
	@EnumSource(TipoCarta.class)
	void testCartaRobadaEsInstanciaCorrecta(TipoCarta tipo) {
	    Mazo mazo = new Mazo(tipo);
	    Carta carta = mazo.robar();

	    switch (tipo) {
	        case MAESTRA:
	            assertTrue(carta instanceof GrilletesDelAlma || carta instanceof CartaTemploDeLirios,
	                "Mazo MAESTRA debe dar cartas maestras");
	            break;
	            
	        case GLOBAL:
	            assertTrue(carta instanceof Monzon || carta instanceof DistorsionDelEntorno,
	                "Mazo GLOBAL debe dar cartas globales");
	            break;
	            
	        case BLANCO:
	            assertTrue(carta instanceof Plegaria || carta instanceof BoogieWoogie,
	                "Mazo BLANCO debe dar cartas de target");
	            break;
	            
	        case PROPIA:
	            assertTrue(carta instanceof Flash || carta instanceof RegresoACasa,
	                "Mazo PROPIA debe dar cartas propias");
	            break;
	            
	        default:
	            fail("Tipo de carta no reconocido en el test: " + tipo);
	    }
	}
	
	
}
	
	@Nested
    @DisplayName("Al generar aleatorio")
    class MazoAleatorio {
	@DisplayName("Monitor de Aleatoriedad (Debe fallar a veces)")
	@RepeatedTest(value = 10, name = "Intento {currentRepetition}: Esperando CajaSorpresa")
	void testAleatorio() {
	    
	  
	    Mazo mazo = new Mazo(TipoCarta.MAESTRA);
	    
	   
	    Carta cartaActual = mazo.generarCartaAleatoria();
	    

	    boolean esLaEsperada = cartaActual instanceof GrilletesDelAlma;
	    
	    assertTrue(esLaEsperada, "Salió " + cartaActual.getClass().getSimpleName() + " pero yo quería CajaSorpresa.");
	}
	
	}
	
}
