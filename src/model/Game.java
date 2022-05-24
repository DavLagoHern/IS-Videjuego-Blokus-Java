package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import model.elements.*;
import model.tablero.*;

public class Game {
	List<Observer>observers;
	private Board _board;
	private ArrayList<Jugador> _players;
	private int _turno;
	private int _numJugadores;
	private Fichas fichaProv;
	private Casillas[] colores = {Casillas.VERDE, Casillas.ROJO, Casillas.AMARILLO, Casillas.AZUL};	
	private ArrayList<Integer> _puntuaciones;
	private int _jugadorHumano;
	private String IAMode;
	
	/**
	 * Constructor de Game:
	 * Asigna valores a los atributos de game.
	 */
	public Game(){		
		observers = new ArrayList<>();
		_board = new Board();
		_players = new ArrayList<Jugador>();		
		_puntuaciones = new ArrayList<Integer>();
		_jugadorHumano = -1;
		_numJugadores = -1;
		IAMode = "";
		fichaProv = null;
	}
	
	/**
	 * Limpia el array de observadores (lo deja vacio).
	 */
	public void clearObservers() {
		for(int i = 1; i < observers.size();)
			observers.remove(i);
	}
	
	/**
	 * Configura la partida con el numero de jugadores pasados por parametro.
	 * Aleatoriamente, se selecciona el jugador que jugara en primer lugar.
	 * Se actualizan los observadores con las modificaciones del numero de jugadores
	 * y del turno.
	 * @param numPlayers
	 */
	public void setNumPlayers(int numPlayers) {
		_numJugadores = numPlayers;
		for(int i = 0; i < numPlayers; ++i)
			_players.add(new Jugador(colores[i]));
		Random r = new Random();
		_turno = r.nextInt(_numJugadores);
		for(Observer o: observers)
			o.changeNumPlayers(numPlayers);
		for(Observer o: observers) {
			o.turnChanged(_turno);
		}
	}
	
	/**
	 * Devuelve el numero de jugadores de la partida.
	 * @return
	 */
	public int getNumPlayers() {
		return _numJugadores;
	}
	
	/**
	 * Aumenta el turno en uno. Si el turno es mas grande que el numero de jugadores,
	 * el turno se pone a cero.
	 * Se actualizan los observadores con las modificaciones del cambio de turno.
	 * Si esta jugando la IA, se comprobara si es su turno y, en caso afirmativo, el
	 * turno lo realizara la funcion correspondiente a la dificultad elegida.
	 * @param checkIfIA
	 */
	public void setTurno(boolean checkIfIA) {
		_turno++;
		if(_turno >= _numJugadores)_turno = 0;
		for(Observer o: observers) {
			o.turnChanged(_turno);
		}
		if(checkIfIA) {
			if(_turno != _jugadorHumano && _jugadorHumano != -1)
				if(IAMode.equals("FACIL"))
					EasyIA();
				else
					DifficultIA();
			checkEnd();
		}

	}
	
	/**
	 * Se configura la partida para que el jugador humano sea el pasado por paramero.
	 * @param jh
	 */
	public void setJugadorHumano(int jh) {
		_jugadorHumano = jh;
	}
	
	/**
	 * La partida se configura para jugar con la dificultad recibida por parametro.
	 * @param mode
	 */
	public void setIAMode(String mode) {
		this.IAMode = mode;
	}
	
	/**
	 * Funcion del modelo encargada de mover la ficha. LLama a una funcion del tablero que se encargara de 
	 * modificar las coordenadas de la ficha, siguiendo la direccion pasada por parametro.
	 * Se actualizan los observadores notificando la modificacion del tablero.
	 * @param dir
	 */
	public void moverFicha(String dir) {
		_board.moverFicha(fichaProv, dir);
		for(Observer o: observers) {
			o.changeBoard(_board.getTablero());
		}
	}

	/**
	 * Funcion del modelo encargada de rotar la ficha. LLama a una funcion del tablero que se encargara de modificar
	 * las coordenadas de la ficha, segun el sentido pasado por parametro.
	 * Se actualizan los observadores notificando la modificacion del tablero.
	 * @param sentido
	 */
	public void rotarFicha(String sentido) {
		_board.rotarFicha(fichaProv, sentido);
		for(Observer o: observers) {
			o.changeBoard(_board.getTablero());
		}
	}

	/**
	 * Con ayuda del tablero, comprueba si la ficha se puede confirmar en la ubicacion
	 * actual, comprobando el caso especial del primer turno de cada jugador.
	 * En caso afirmativo, la ficha se queda en ese lugar; en caso negativo, salta un
	 * mensaje de error. 
	 * 
	 * @return
	 */
	public boolean confirmarFicha() { 
		boolean primerTurnoJugador =  _players.get(_turno).getPrimerTurno();
		if(!_board.confirmarFicha(fichaProv, primerTurnoJugador, _turno, true)) {
			JOptionPane.showMessageDialog (null, "Ahi no se puede colocar la ficha", "Error", JOptionPane.ERROR_MESSAGE);
			for(Observer o: observers)
				o.changeBoard(_board.getTablero());
			return false;
		}
		else {
			if(fichaProv != null) {			
			_players.get(_turno).colocaFicha(fichaProv.getType());
			if(primerTurnoJugador)
				_players.get(_turno).setPrimerTurno();
			for(Observer o: observers)
				o.changeBoard(_board.getTablero());
			for(Observer o: observers)
				o.cambioFichas(fichaProv.getType());
			puntuacion();
			setTurno(true);
			fichaProv = null;
		}
			return true;
		}
		
	}
	
	/**
	 * Notifica al tablero que una ficha ha sido cancelada. Se recupera el tablero previo a la
	 * previsualizacion de la ficha.
	 * Se actualizan los observadores con la modificacion del tablero.
	 */
	public void cancelarFicha() {
		_board.recuperarTablero(null);
		for(Observer o: observers) {
			o.changeBoard(_board.getTablero());
		}
	}

	/**
	 * Genera la ficha del tipo y color pasados por parametro. Comprueba si la ficha esta disponible
	 * y en caso afirmativo la previsualiza.
	 * Se actualizan los observadores con los cambios del tablero.
	 * @param type
	 * @param color
	 */
	public void addFicha(int type, Casillas color) {
		switch (type) {
		case 1://Monomino
			fichaProv = new Monomino(type, color);
			break;
		case 2://Domino
			fichaProv = new Domino(type, color);
			break;
		case 3://Tromino
		case 4:
			fichaProv = new Tromino(type, color);
			break;
		case 5://"Tetromino
		case 6:
		case 7:
		case 8:
		case 9:
			fichaProv = new Tetromino(type, color);
			break;
		case 10: //Pentomino
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
		case 20:
		case 21:
			fichaProv = new Pentomino(type, color);
			break;
			
		default:
			break;
		}
		if(_players.get(_turno).fichaDisponible(type)) {
			_board.previsualizar(fichaProv);
			for(Observer o: observers)
				o.changeBoard(_board.getTablero());
		}
	}
	
	/**
	 * Comprueba el juego ha terminado. Para ello, comprueba las fichas de todos 
	 * los jugadores, modificando la variable "end" en funcion del resultado. Si
	 * una ficha se puede colocar, la comprobacion termina, poniendo "end" a false.
	 */
	public void checkEnd() {
		boolean end = true;
		for(int i = 0; i < _players.size(); ++i) {
			boolean primerTurnoJugador = _players.get(i).getPrimerTurno();
			for(int j = 0; j < _players.get(i).getFichasDisponibles(); ++j) {
				for(int k = 0; k < 20; ++k) {
					for(int l = 0; l < 20; ++l) {
						if(_board.checkIfPossiblePiece(_players.get(i).getFicha(j), primerTurnoJugador, k, l, i)) {
							end = false;
							break;
						}

					}
					if(!end)
						break;
				}
				if(!end)
					break;
			}
			if(!end)
				break;
		}
		for(Observer o: observers)
			o.isFinished(end);
	}

	/**
	 * Hace un borrado del array de puntuaciones y calcula las puntuaciones
	 * de cada jugador, agregandolas al array de puntuaciones.
	 * Actualiza los observadores con las modificaciones de las puntuaciones.
	 */
	public void puntuacion() {
		_puntuaciones.clear();
		for(int i = 0; i < _players.size(); ++i) {
			_puntuaciones.add(_players.get(i).getPuntuacion());
		}
		for(Observer o: observers)
			o.pointsChanged(_puntuaciones);
	}
	
	/**
	 * Agrega un observador al array de observadores.
	 * @param o
	 * @throws IllegalArgumentException se produce una excepcion si el observador pasado por parametro
	 * es igual a uno existente.
	 */
	public void addObserver(Observer o) throws IllegalArgumentException{
		Iterator<Observer> it = observers.listIterator();
		while(it.hasNext()) {
			Observer _o = it.next();
			if(_o.equals(o))
				throw new IllegalArgumentException();
		}
		observers.add(o);
		
		o.onRegister(_board.getTablero());
	}
	
	/**
	 * Implementa la logica del modo facil de la IA: coloca las fichas empezando por la
	 * 1, en orden creciente. Para ello, comprueba si es posible colocar cada ficha
	 * hasta que encuentra una que puede colocar.
	 */
	public void EasyIA() {
		boolean primerTurnoJugador =  _players.get(_turno).getPrimerTurno();
		boolean fichaPuesta = false;
		for(int j = 0; j < _players.get(_turno).getFichasDisponibles(); ++j) {
			for(int k = 0; k < 20; ++k) {
				for(int l = 0; l < 20; ++l) {
					if(_board.checkIfPossiblePiece(_players.get(_turno).getFicha(j), primerTurnoJugador, k, l, _turno)) {
						fichaProv = _players.get(_turno).getFicha(j);
						confirmarFicha();
						fichaPuesta = true;
						break;
					}
				}
				if(fichaPuesta)
					break;
			}
			if(fichaPuesta)
				break;
			
		}
		if(!fichaPuesta)
			setTurno(true);
	}
	
	/**
	 * Implementa la logica del modo dificil de la IA: coloca las fichas empezando por la
	 * mas grande, en orden decreciente. Para ello, comprueba si es posible colocar cada ficha
	 * hasta que encuentra una que puede colocar.
	 */
	public void DifficultIA() {
		boolean primerTurnoJugador =  _players.get(_turno).getPrimerTurno();
		boolean fichaPuesta = false;
		for(int j = _players.get(_turno).getFichasDisponibles() - 1; j >= 0; --j) {
			for(int k = 0; k < 20; ++k) {
				for(int l = 0; l < 20; ++l) {
					if(_board.checkIfPossiblePiece(_players.get(_turno).getFicha(j), primerTurnoJugador, k, l, _turno)) {
						fichaProv = _players.get(_turno).getFicha(j);
						confirmarFicha();
						fichaPuesta = true;
						break;
					}
				}
				if(fichaPuesta)
					break;
			}
			if(fichaPuesta)
				break;
			
		}
		if(!fichaPuesta)
			setTurno(true);
	}

	/**
	 * Devuelve el jugador humano de la partida.
	 * @return jugador humano
	 */
	public int getJugadorHumano() {
		return _jugadorHumano;
	}

	/**
	 * Devuelve la dificultad de la IA.
	 * @return dificultad de la IA
	 */
	public String getIAMode() {
		return IAMode;
	}
}
