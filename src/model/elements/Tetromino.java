package model.elements;

import model.tablero.Casillas;

public class Tetromino extends Fichas{
	
	public Tetromino(int tipo, Casillas color){
		super(color, tipo);
		init();
	}
	
	@Override
	public void init() {
		switch (_type) {
		case 5:
			_partes.add(new Coordenadas(10, 10)); 
			_partes.add(new Coordenadas(10, 9));
			_partes.add(new Coordenadas(10, 11));
			_partes.add(new Coordenadas(10, 12));
			break;

		case 6:
			_partes.add(new Coordenadas(10, 10)); 
			_partes.add(new Coordenadas(10, 9));
			_partes.add(new Coordenadas(10, 11));
			_partes.add(new Coordenadas(11, 11));
			break;
			
		case 7:
			_partes.add(new Coordenadas(10, 10)); 
			_partes.add(new Coordenadas(10, 9));
			_partes.add(new Coordenadas(10, 11));
			_partes.add(new Coordenadas(11, 10));
			break;
			
		case 8:
			_partes.add(new Coordenadas(10, 10)); 
			_partes.add(new Coordenadas(11, 10));
			_partes.add(new Coordenadas(11, 11));
			_partes.add(new Coordenadas(10, 11));
			break;
			
		case 9:
			_partes.add(new Coordenadas(10, 10)); 
			_partes.add(new Coordenadas(9, 10));
			_partes.add(new Coordenadas(10, 11));
			_partes.add(new Coordenadas(11, 11));
			break;
		}
		
	}
}