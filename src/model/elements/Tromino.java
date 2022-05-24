package model.elements;

import model.tablero.Casillas;

public class Tromino extends Fichas {

	public Tromino(int tipo, Casillas color){
		super(color, tipo);
		init();
	}

	@Override
	public void init() {
		switch (_type) {
		case 3:
			_partes.add(new Coordenadas(10, 10));
			_partes.add(new Coordenadas(10, 9));
			_partes.add(new Coordenadas(10, 11));
			break;

		case 4:
			_partes.add(new Coordenadas(10, 10));
			_partes.add(new Coordenadas(10, 9));
			_partes.add(new Coordenadas(11, 10));
			break;
		}

	}
}