package control;

import model.Game;
import model.elements.Observer;
import model.tablero.Casillas;

public class Controller {
	private Game _game;
	
	/**
	 * Constructor del controlador
	 * @param game
	 */
	public Controller(Game game) { _game = game; }
	
	/**
	 * Limpia los observadores del Game
	 */
	public void clearObservers() { _game.clearObservers(); }
	
	/**
	 * Obtiene el numero de jugadores del Game
	 * @return Numero de jugadores
	 */
	public int getNumPlayers() { return _game.getNumPlayers(); }
	
	/**
	 * Asigna el numero de jugadores del Game
	 * @param numPlayers
	 */
	public void setNumPlayers(int numPlayers) { _game.setNumPlayers(numPlayers); }
	
	/**
	 * Asigna el turno del Game
	 * @param checkIfIA
	 */
	public void setTurno(boolean checkIfIA) { _game.setTurno(checkIfIA); }
	
	/**
	 * Agrega una ficha con su color al Game
	 * @param ficha
	 * @param color
	 */
	public void addFicha(int ficha, Casillas color) { _game.addFicha(ficha, color); }
	
	/**
	 * Mueve una ficha del Game en una direccion dir
	 * @param dir
	 */
	public void move(String dir) { _game.moverFicha(dir); }
	
	/**
	 * Rota una ficha del Game en un sentido dir
	 * @param dir
	 */
	public void rotate(String dir) { _game.rotarFicha(dir); }
	
	/**
	 * Agrega un observador al Game
	 * @param o
	 */
	public void addObserver(Observer o) { _game.addObserver(o); }
	
	/**
	 * Confirma la colocacion de una ficha
	 * @return True si se pudo colocar, false si no
	 */
	public boolean confirmarFicha() { return _game.confirmarFicha(); }
	
	/**
	 * Cancela la colocacion de una ficha
	 */
	public void cancelarFicha() { _game.cancelarFicha(); }
	
	/**
	 * Comprueba la finalizacion de la partida
	 */
	public void checkEnd() { _game.checkEnd(); }
	
	/**
	 * Agrega un jugador humano a la partida
	 * @param jh
	 */
	public void setJugadorHumano(int jh) { _game.setJugadorHumano(jh); }
	
	/**
	 * Asigna el modo de dificultad de la IA
	 * @param mode
	 */
	public void setIAMode(String mode) { _game.setIAMode(mode); }

	/**
	 * Obtiene del Game el numero de jugador que es el usuario
	 * @return Numero de jugador del usuario
	 */
	public int getJugadorHumano() { return _game.getJugadorHumano(); }

	/**
	 * Obtiene del Game el modo de dificultad de la IA
	 * @return Modo de dificultad de la IA
	 */
	public String getIAMode() { return _game.getIAMode(); }
}