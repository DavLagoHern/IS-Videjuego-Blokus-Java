package model.elements;

import model.tablero.Casillas;

public class Domino extends Fichas {

	public Domino(int tipo, Casillas color){
		super(color, tipo);
		init();
	}

	@Override
	public void init() {
		_partes.add(new Coordenadas(10, 9));
		_partes.add(new Coordenadas(10, 10));

	}
}