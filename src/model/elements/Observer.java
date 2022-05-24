package model.elements;

import java.util.ArrayList;

import model.tablero.Casillas;

public interface Observer {
	/**
	 * Cuando se registra un tablero
	 * @param tablero
	 */
	public void onRegister(Casillas[][] tablero);
	/**
	 * Cuando el tablero cambia
	 * @param tablero
	 */
	public void changeBoard(Casillas[][] tablero);
	/**
	 * cuando cambia de turno
	 * @param turno
	 */
	public void turnChanged(int turno);
	/**
	 * cuando la puntuacion de los jugadores cambia
	 * @param points
	 */
	public void pointsChanged(ArrayList<Integer> points);
	/**
	 * cuando el juego a terminado
	 * @param end
	 */
	public void isFinished(boolean end);
	/**
	 * cuando hay un cambio de fichas
	 * @param index
	 */
	public void cambioFichas(int index);
	/**
	 * cuando se cambia el numero de jugadores
	 * @param numPlayers
	 */
	public void changeNumPlayers(int numPlayers);
}
