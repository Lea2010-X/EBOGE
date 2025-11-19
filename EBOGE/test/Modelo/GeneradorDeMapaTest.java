package Modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GeneradorDeMapaTest {

	private GeneradorDeMapa generador = new GeneradorDeMapa();
	private Mapa mapaGenerado;

	private static final int W = 800;
	private static final int H = 400;
	private static final int L = 6;

	private static final int TOTAL_ESPERADO = 18;
	private static final int COLUMNAS_ESPERADAS = 6;
	private static final int FILAS_ESPERADAS = 3;

	@BeforeEach
	void generarMapa() {
		this.mapaGenerado = generador.generarMapa(W, H, L);
		assertNotNull(mapaGenerado, "El mapa generado no debe ser nulo");
	}

	@Test
	void totalDeCasillasCoincideConLoEsperado() {
		int totalCasillas = mapaGenerado.getTotalCasillas();
		assertEquals(TOTAL_ESPERADO, totalCasillas, "El total de casillas no coincide con el esperado");
	}

	@Test
	void numeroDeColumnasCoincideConLoEsperado() {
		int columnas = mapaGenerado.getAnchoMapa();
		assertEquals(COLUMNAS_ESPERADAS, columnas, "El número de columnas no coincide con el esperado");
	}

	@Test
	void numeroDeFilasCoincideConLoEsperado() {
		int filas = mapaGenerado.getLargoMapa();
		assertEquals(FILAS_ESPERADAS, filas, "El número de filas no coincide con el esperado");
	}

	@Test
	void laCasillaDeInicioEsDeTipoInicio() {
		assertEquals(TipoCasilla.INICIO, mapaGenerado.identificarTipoDeCasilla(0),"La casilla 0 debe ser de tipo INICIO");
	}

	@Test
	void laCasillaFinalEsDeTipoFinal() {
		int totalCasillas = mapaGenerado.getTotalCasillas();

		assertEquals(TipoCasilla.FINAL, mapaGenerado.identificarTipoDeCasilla(totalCasillas - 1),"La última casilla debe ser de tipo FINAL");
	}
}
