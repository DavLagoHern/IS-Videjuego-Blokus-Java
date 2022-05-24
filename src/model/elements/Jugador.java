package model.elements;

import java.util.ArrayList;

import model.tablero.Casillas;

public class Jugador {
	private int marcador;
	private Casillas color;
	private ArrayList<Fichas> listaFichas;
	private boolean primerTurno;
	
	/**
	 * Constructor del jugador
	 * @param clr
	 */
	public Jugador(Casillas clr) {
		this.primerTurno = true;
		this.marcador = 0;
		this.color = clr;
		this.listaFichas = crearLista();
	}
	
	/**
	 * Devuelve si es su primer turno 
	 * @return el valor que tenga primerTurno(true, false)
	 */
	public boolean getPrimerTurno() {
		return primerTurno;
	}
	
	/**
	 * Establece que es su primer turno a false ya que ya ha pasado al segundo
	 */
	public void setPrimerTurno() {
		primerTurno = false;
	}

	
	/**
	 * Devuelve la puntuacion del jugador, su marcador, haciendo la suma de las piezas
	 * @return el marcador con la puntuacion
	 */
	public int getPuntuacion() {
		marcador = 0;
		for(int i = 0; i < listaFichas.size(); ++i) {
			marcador -= listaFichas.get(i).getPoints();
		}
		if(marcador == 0)
			marcador += 15;
		return marcador;
	}	
	
	/**
	 * Devuelve el color del jugador
	 * @return enum que indica el color
	 */
	public Casillas getColor() {
		return color;
	}
	
	/**
	 * Se crea una lista de Fichas agregando todas las fichas a ese mismo array
	 * @return se devuelve el array de fichas
	 */
	public ArrayList<Fichas> crearLista(){
		ArrayList<Fichas> aux = new ArrayList<Fichas>();
		for(int i = 1; i <= 21; i++) {
			switch (i) {
			case 1://Monomino
				aux.add(i - 1, new Monomino(i, color));
				break;
			case 2://Domino
				aux.add(i - 1, new Domino(i, color));
				break;
			case 3://Tromino
			case 4:
				aux.add(i - 1, new Tromino(i, color));
				break;
			case 5://"Tetromino
			case 6:
			case 7:
			case 8:
			case 9:
				aux.add(i - 1, new Tetromino(i, color));
				break;
				
			default://Pentomino
				aux.add(i - 1, new Pentomino(i, color));
				break;
			}
		}
		return aux;
	}
	
	/**
	 * Se elimina del array la ficha que se ha colocado
	 * @param i
	 */
	public void colocaFicha(int i) {
		for(int j = 0; j < listaFichas.size(); ++j) {
			if(listaFichas.get(j).getType() == i) {
				listaFichas.remove(j);
				break;
			}
		}
	}
	
	/**
	 * Se pregunta si la ficha esta disopnible para colocarla
	 * @param type
	 * @return true si esta disponible, false si no lo esta
	 */
	public boolean fichaDisponible(int type) {
		for(int i = 0; i < listaFichas.size(); ++i) {
			if(listaFichas.get(i).getType() == type)
				return true;
		}
		return false;
	}
	
	/**
	 * Devuelve el numero de fichas que quedan disponibles, la longitud del array
	 * @return la longitud del array
	 */
	public int getFichasDisponibles(){
		return listaFichas.size();
	}
	
	/**
	 * Devuelve la ficha que se le pido por el parametro i
	 * @param i
	 * @return devuelve la ficha pedida
	 */
	public Fichas getFicha(int i) {
		return listaFichas.get(i);
	}
}