package model.tablero;

import java.util.ArrayList;

import model.elements.Coordenadas;
import model.elements.Fichas;

public class Board {
	private final static int FILA = 20;
	private final static int COL = 20;
	private Casillas tablero[][];
	private Casillas tablero_confirmadas[][];
	private ArrayList<Coordenadas> coordenadas_copia;
	private Coordenadas[] casilla_inicio = { new Coordenadas(0, 0), new Coordenadas(0, 19), new Coordenadas(19, 0), new Coordenadas(19, 19) };
	private Coordenadas diferencia;
	
	/**
	 * Constructor del tablero
	 */
	public Board() {
		tablero = new Casillas[FILA][COL];
		tablero_confirmadas = new Casillas[FILA][COL];
		iniciarTablero();
		coordenadas_copia = new ArrayList<>();
	}

	/**
	 * Inicializacion del tablero basico y del tablero de fichas confirmadas
	 * con todas las casillas vacias
	 */
	private void iniciarTablero() {
		for (int i = 0; i < FILA; i++) {
			for (int j = 0; j < COL; j++) {
				tablero[i][j] = Casillas.VACIO;
				tablero_confirmadas[i][j] = Casillas.VACIO;
			}
		}
	}

	/**
	 * Desmodifica el tablero tras un cambio (P.Ej.: Volver a poner unas casillas en blanco
	 * si se cancela la colocacion de una ficha)
	 * @param fichaProv
	 */
	public void recuperarTablero(Fichas fichaProv) {
		for (int i = 0; i < FILA; ++i) {
			for(int j = 0; j < COL; ++j) {
				tablero[i][j] = tablero_confirmadas[i][j];
			}
		}
		if (fichaProv != null) previsualizar(fichaProv);
	}
	
	/**
	 * Recupera las coordenadas de una ficha (P.Ej.: Se intenta mover una ficha fuera del
	 * tablero y a mitad de operacion se detecta que no es posible moverla ahi)
	 * @param fichaProv
	 */
	private void recuperarCoordenadas(Fichas fichaProv) {
		for (int i = 0; i < coordenadas_copia.size(); ++i) {
			fichaProv._partes.get(i).setFila(coordenadas_copia.get(i).getFila());
			fichaProv._partes.get(i).setCol(coordenadas_copia.get(i).getCol());
		}
	}
	
	/**
	 * Se copian las coordenadas de una ficha por si se necesitan recuperar
	 * @param fichaProv
	 */
	private void copiarCoordenadas(Fichas fichaProv) {
		coordenadas_copia.clear();
		for (int i = 0; i < fichaProv._partes.size(); i++) {
			Coordenadas coord = fichaProv._partes.get(i);
			coordenadas_copia.add(coord);
		}
	}
	
	/**
	 * Se mueve una ficha en una direccion dada
	 * @param fichaProv
	 * @param dir
	 */
	public void moverFicha(Fichas fichaProv, String dir) {
		copiarCoordenadas(fichaProv);
		for (int i = 0; i < fichaProv._partes.size(); i++) {
			Coordenadas aux = fichaProv.mover(i, dir);
			if(outOfBounds(aux)) {
				recuperarCoordenadas(fichaProv);
				recuperarTablero(fichaProv);
				break;
			}
			fichaProv._partes.set(i, aux);
		}	
		recuperarTablero(fichaProv);
	}
	
	/**
	 * Se rota una ficha en un sentido dado
	 * @param fichaProv
	 * @param sentido
	 */
	public void rotarFicha(Fichas fichaProv, String sentido) {
		copiarCoordenadas(fichaProv);
		for(int i = 1; i < fichaProv._partes.size(); ++i) {
			Coordenadas aux = fichaProv.rotar(i, sentido);
			if(outOfBounds(aux)) {
				recuperarCoordenadas(fichaProv);
				recuperarTablero(fichaProv);
				break;
			}
			fichaProv._partes.set(i, aux);
		}
		recuperarTablero(fichaProv);
	}

	/**
	 * Confirma la colocacion de una ficha provisional, comprobando si es posible o no
	 * @param fichaProv
	 * @param primerTurnoJugador
	 * @param turno
	 * @param put
	 * @return True si se pudo confirmar la ficha, false si no
	 */
	public boolean confirmarFicha(Fichas fichaProv, boolean primerTurnoJugador, int turno, boolean put) {
		boolean esquina = false;
		if(fichaProv != null) {
			
			for (Coordenadas pos : fichaProv._partes) {
			if (outOfBounds(pos) || tablero_confirmadas[pos.getFila()][pos.getCol()] != Casillas.VACIO) {
				recuperarCoordenadas(fichaProv);
				recuperarTablero(fichaProv);
				return false;
			}
			if (pos.getFila() == casilla_inicio[turno].getFila() &&
					pos.getCol() == casilla_inicio[turno].getCol()) esquina = true;
		}
		if (primerTurnoJugador && !esquina) {
			recuperarCoordenadas(fichaProv);
			recuperarTablero(fichaProv);
			return false;
		}
		if(!primerTurnoJugador && !checkDiagonal(fichaProv)) return false;
		if(put) {
			for (int i = 0; i < (fichaProv._partes).size(); ++i) {
				tablero_confirmadas[fichaProv._partes.get(i).getFila()][fichaProv._partes.get(i).getCol()] = fichaProv.color;
				tablero[fichaProv._partes.get(i).getFila()][fichaProv._partes.get(i).getCol()] = fichaProv.color;
			}
		}
	}
		return true;
	}

	/**
	 * Comprueba si una ficha provisional hace diagonal con otra de su mismo color
	 * @param fichaProv
	 * @return True si se cumple, false si no
	 */
	private boolean checkDiagonal(Fichas fichaProv) {
		boolean diagonal = false;
		Casillas clr = fichaProv.color;
		for (int a = 0; a < fichaProv._partes.size(); a++) {
			int row = fichaProv._partes.get(a).getFila();
			int col = fichaProv._partes.get(a).getCol();
			for (int i = row - 1; i <= row + 1; i++) {
				if(i >= 0 && i < 20) {
					for (int j = col - 1; j <= col + 1; j++) {
						if (j >= 0 && j < 20 && tablero_confirmadas[i][j].equals(clr)) {
								if((i == row) || (j == col)) return false;
								else diagonal = true;
						}
					}
				}
			}
		}
		return diagonal;
	}

	/**
	 * Parsea un enum Casillas a su String correspondiente
	 * @param casilla
	 * @return La casilla en formato String
	 */
	private String casillasToString(Casillas casilla) {
		switch (casilla) {
		case VACIO:
			return "  ";
		case VERDE:
			return "Ve";
		case ROJO:
			return "Ro";
		case AMARILLO:
			return "Am";
		case AZUL:
			return "Az";
		default:
			return "";
		}
	}
	
	/**
	 * Obtiene la casilla de una posicion dada en formato String
	 * @param row
	 * @param col
	 * @return El String de la casilla en esa posicion
	 */
	public String getPositionToString(int row, int col) { return casillasToString(tablero[row][col]); }
	
	/**
	 * Previsualiza en el tablero una ficha provisional
	 * @param fichaProv
	 */
	public void previsualizar(Fichas fichaProv) { 
		copiarCoordenadas(fichaProv);
		for (int i = 0; i < (fichaProv._partes).size(); ++i) {
			tablero[fichaProv._partes.get(i).getFila()][fichaProv._partes.get(i).getCol()] = fichaProv.color;
		}
	}
	
	/**
	 * Comprueba si unas coordenadas dadas se encuentran fuera del tablero
	 * @param coord
	 * @return True si se sale del tablero, false si no
	 */
	public static boolean outOfBounds(Coordenadas coord) {
		if (coord.getFila() < 0 || coord.getFila() >= FILA) return true;
		if (coord.getCol() < 0 || coord.getCol() >= COL) return true;
		return false;
	}
	
	/**
	 * Comprueba si una ficha se puede colocar en la posicion (i, j) 
	 * @param ficha
	 * @param primerTurno
	 * @param i
	 * @param j
	 * @param turno
	 * @return True si es posible, false si no
	 */
	public boolean checkIfPossiblePiece(Fichas ficha, boolean primerTurno, int i, int j, int turno) {
		copiarCoordenadas(ficha);
		boolean posible = false;
		Coordenadas coordCentral = new Coordenadas(ficha._partes.get(0));
		diferencia = Coordenadas.resta(new Coordenadas(i, j), coordCentral);
		for (int k = 1; k < 4; ++k) {
			for (int l = 0; l < ficha._partes.size(); ++l) {
				Coordenadas newCoordenadas = new Coordenadas(Coordenadas.suma(diferencia, ficha._partes.get(l)));
				ficha._partes.set(l, newCoordenadas);
			}
			if (confirmarFicha(ficha, primerTurno, turno, false)) {
				posible = true;
				break;
			}
			else recuperarCoordenadas(ficha);
			rotarFicha(ficha, "RIGHT");
		}	
		recuperarTablero(null);
		return posible;
	}
	
	/**
	 * Devuelve el parametro diferencia
	 * @return Parametro Coordenadas diferencia
	 */
	public Coordenadas getDiferencia() { return diferencia; }
	
	/**
	 * Devuelve el tablero
	 * @return Matriz de Casillas tablero
	 */
	public Casillas[][] getTablero(){ return tablero; }
}