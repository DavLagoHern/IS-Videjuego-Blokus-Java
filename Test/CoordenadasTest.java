import static org.junit.Assert.*;

import org.junit.Test;

import model.elements.Coordenadas;

public class CoordenadasTest {

	@Test
	public void Sumatest() {
		Coordenadas a = new Coordenadas(1, 2);
		Coordenadas b = new Coordenadas(3, 4);
		Coordenadas actual = Coordenadas.suma(a,  b);
		int colExpected = 6;
		int filaExpected = 4;
		assertEquals(colExpected, actual.getCol());
		assertEquals(filaExpected, actual.getFila());
	}
	
	@Test
	public void Restatest() {
		Coordenadas a = new Coordenadas(1, 2);
		Coordenadas b = new Coordenadas(3, 4);
		Coordenadas actual = Coordenadas.resta(b,  a);
		int colExpected = 2;
		int filaExpected = 9;
		assertEquals(colExpected, actual.getCol());
		assertEquals(filaExpected, actual.getFila());
	}

}
