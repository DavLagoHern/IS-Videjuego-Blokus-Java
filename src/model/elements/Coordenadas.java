package model.elements;

public class Coordenadas {
	protected int _fila, _col;
	
	/**
	 * Constructor de las coordenadas
	 * @param fila
	 * @param col
	 */
	public Coordenadas(int fila, int col){
		_fila = fila;
		_col = col;
	}
	
	/**
	 * Constructor de las coordenadas por copia
	 * @param coordenadas
	 */
	public Coordenadas(Coordenadas coordenadas) {
		_col = coordenadas._col;
		_fila = coordenadas._fila;
	}

	/**
	 * Asigna la fila a la coordenada
	 * @param fila
	 */
	public void setFila(int fila){
		_fila = fila;
	}
	
	/**
	 * Devuelve la fila de la coordenada
	 * @return la fila de la coordenada
	 */
	public int getFila(){
		return _fila;
	}
	
	/**
	 * Asignla la columna a la coordenada
	 * @param 
	 */
	public void setCol(int col){
		_col = col;
	}
	
	/**
	 * Devuelve la columna de la coordenada
	 * @return la columna de la coordenada
	 */
	public int getCol(){
		return _col;
	}
	
	/**
	 * Suma dos coordenadas distintas
	 * @param a
	 * @param b
	 * @return la coordenada ya sumada
	 */
	public static Coordenadas suma(Coordenadas a, Coordenadas b) {
		return new Coordenadas(a._fila + b._fila, a._col + b._col);
	}
	
	/**
	 * Resta dos coordenadas distintas
	 * @param a
	 * @param b
	 * @return la coordenada ya restada
	 */
	public static Coordenadas resta(Coordenadas a, Coordenadas b) {
		return new Coordenadas(a._fila - b._fila, a._col - b._col);
	}
}
