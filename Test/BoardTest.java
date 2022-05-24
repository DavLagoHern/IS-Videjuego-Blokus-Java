import static org.junit.Assert.*;

import org.junit.Test;

import model.elements.Coordenadas;
import model.tablero.Board;

public class BoardTest {

	@Test
	public void boundsTest() {
		Coordenadas a = new Coordenadas(20, 19);
		boolean actual = Board.outOfBounds(a);
		assertTrue(actual);
	}

}
