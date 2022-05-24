package model.elements;

import model.tablero.Casillas;

public class Monomino extends Fichas {

	public Monomino(int tipo, Casillas color){
		super(color, tipo);
		init();
	}

	@Override
	public void init() {
		_partes.add(new Coordenadas(10, 10));
	}
}