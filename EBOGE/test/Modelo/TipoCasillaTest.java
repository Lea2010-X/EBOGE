package Modelo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.RepeatedTest;

public class TipoCasillaTest {

	@RepeatedTest(10)
	void casillaAleatoriaDebeSerValida() {
		TipoCasilla tipo = TipoCasilla.aleatorio();

		assertAll(() -> assertNotEquals(TipoCasilla.INICIO, tipo, "El tipo retornado fue INICIO"),
				() -> assertNotEquals(TipoCasilla.FINAL, tipo, "El tipo retornado fue FINAL"));
	}
}
