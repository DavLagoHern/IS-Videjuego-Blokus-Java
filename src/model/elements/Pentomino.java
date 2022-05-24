package model.elements;

import model.tablero.Casillas;

public class Pentomino extends Fichas{
	
	public Pentomino(int tipo, Casillas color){
		super(color, tipo);
		init();
	}

	@Override
	public void init() {
		switch (_type) {
		case 10:
			_partes.add(new Coordenadas(10, 10)); 
			_partes.add(new Coordenadas(10, 8));
			_partes.add(new Coordenadas(10, 9));
			_partes.add(new Coordenadas(10, 11));
			_partes.add(new Coordenadas(10, 12));
			break;
		case 11:
			_partes.add(new Coordenadas(10, 10)); 
			_partes.add(new Coordenadas(10, 9));
			_partes.add(new Coordenadas(10, 8));
			_partes.add(new Coordenadas(10, 11));
			_partes.add(new Coordenadas(9, 11));
			break;
		case 12:
			_partes.add(new Coordenadas(10, 10)); 
			_partes.add(new Coordenadas(10, 8));
			_partes.add(new Coordenadas(10, 9));
			_partes.add(new Coordenadas(9, 10));
			_partes.add(new Coordenadas(9, 11));
			break;
		case 13:
			_partes.add(new Coordenadas(10, 10)); 
			_partes.add(new Coordenadas(10, 9));
			_partes.add(new Coordenadas(9, 10));
			_partes.add(new Coordenadas(10, 11));
			_partes.add(new Coordenadas(9, 11));
			break;
		case 14:
			_partes.add(new Coordenadas(10, 10)); 
			_partes.add(new Coordenadas(9, 10));
			_partes.add(new Coordenadas(11, 10));
			_partes.add(new Coordenadas(9, 9));
			_partes.add(new Coordenadas(11, 9));
			break;
		case 15:
			_partes.add(new Coordenadas(10, 10)); 
			_partes.add(new Coordenadas(10, 9));
			_partes.add(new Coordenadas(10, 12));
			_partes.add(new Coordenadas(10, 11));
			_partes.add(new Coordenadas(11, 10));
			break;
		case 16:
			_partes.add(new Coordenadas(10, 10)); 
			_partes.add(new Coordenadas(10, 9));
			_partes.add(new Coordenadas(10, 8));
			_partes.add(new Coordenadas(11, 10));
			_partes.add(new Coordenadas(9, 10));
			break;
		case 17:
			_partes.add(new Coordenadas(10, 10)); 
			_partes.add(new Coordenadas(10, 9));
			_partes.add(new Coordenadas(10, 8));
			_partes.add(new Coordenadas(11, 10));
			_partes.add(new Coordenadas(12, 10));
			break;
		case 18:
			_partes.add(new Coordenadas(10, 10)); 
			_partes.add(new Coordenadas(10, 9));
			_partes.add(new Coordenadas(9, 9));
			_partes.add(new Coordenadas(11, 10));
			_partes.add(new Coordenadas(11, 11));
			break;
		case 19:
			_partes.add(new Coordenadas(10, 10)); 
			_partes.add(new Coordenadas(10, 9));
			_partes.add(new Coordenadas(10, 11));
			_partes.add(new Coordenadas(9, 9));
			_partes.add(new Coordenadas(11, 11));
			break;
		case 20:
			_partes.add(new Coordenadas(10, 10)); 
			_partes.add(new Coordenadas(9, 10));
			_partes.add(new Coordenadas(9, 9));
			_partes.add(new Coordenadas(10, 11));
			_partes.add(new Coordenadas(11, 10));
			break;
		case 21:
			_partes.add(new Coordenadas(10, 10)); 
			_partes.add(new Coordenadas(10, 9));
			_partes.add(new Coordenadas(10, 11));
			_partes.add(new Coordenadas(9, 10));
			_partes.add(new Coordenadas(11, 10));
			break;
		}
	}
}