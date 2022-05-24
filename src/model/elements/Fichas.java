package model.elements;

import java.util.ArrayList;

import model.tablero.Casillas;

public abstract class Fichas {
	public ArrayList<Coordenadas> _partes;
	public Casillas color;
	protected int _type;
	public Fichas(Casillas playerColor, int type){
		_partes = new ArrayList<Coordenadas>();
		color = playerColor;
		_type = type;
	}
	
	/**
	 * Mueve la ficha segun lo que haya recibido por teclado
	 * @param i
	 * @param direction
	 * @return las coordenadas de la ficha en su nueva posicion
	 */
	public Coordenadas mover(int i, String direction) {

		Coordenadas aux = new Coordenadas(_partes.get(i));
		if(direction.equals("RIGHT") || direction.equals("D")) {
			aux._col += 1;
		}
		else if(direction.equals("LEFT") || direction.equals("A"))  {
			aux._col -=1;
		}
		else if(direction.equals("UP") || direction.equals("W")) {
			aux._fila -=1;
		}
		else if(direction.equals("DOWN") || direction.equals("S"))  {
			aux._fila +=1;
		}
		return aux;

	}
	/**
	 * Rota en el sentido que queramos la ficha a colocar
	 * @param i
	 * @param sentido
	 * @return la coordenada de la ficha rotada hacia el sentido que queramos
	 */
	public Coordenadas rotar(int i, String sentido) {
		Coordenadas pos = new Coordenadas(Coordenadas.resta(_partes.get(i), _partes.get(0)));
		if (sentido.equals("RIGHT") || sentido.equals("M")) {
			int aux = pos._col;
			pos._col = -pos._fila; pos._fila = aux;
		}
		else if (sentido.equals("LEFT") || sentido.equals("N")) {
			int aux = pos._col;
			pos._col = pos._fila; pos._fila = -aux;
		}
		pos.setFila(_partes.get(0).getFila() + pos._fila);
		pos.setCol(_partes.get(0).getCol() + pos._col);
		return pos;
	}
	
	/**
	 * Devuelve los puntos de esa ficha
	 * @return los puntos de la ficha
	 */
	public int getPoints() {
		int points = 0;
		for(int i = 0; i < _partes.size(); ++i) {
			points ++;
		}
		return points;
	}
	
	/**
	 * Devuelve que ficha que se esta usando
	 * @return la ficha que es la que se está usando
	 */
	public int getType() {
		return _type;
	}
	
	/**
	 * Inicializa la ficha que hemos pedido a traves de _type y las coordenadas iniciales para previsualizarla
	 * es la (10,10) que es el centro del tablero
	 */
	public abstract void init();
}