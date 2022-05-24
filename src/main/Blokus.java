package main;


import javax.swing.SwingUtilities;

import control.Controller;
import model.Game;
import view.GUI;

public class Blokus {
	/**
	 * Metodo main, llama a start() y captura excepciones
	 * @param args
	 */
	public static void main(String[] args) {
		try { start(); } 
		catch(Exception e) {
			System.err.println("Something went wrong ...");
			System.err.println();
			e.printStackTrace();
		}
	}

	/**
	 * Inicializa el controlador con un nuevo Game y genera la interfaz grafica
	 * con dicho controlador, iniciando el programa
	 * @throws Exception
	 */
	private static void start() throws Exception {
		Controller ctrl = new Controller(new Game());
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				new GUI(ctrl);
			}
		});
	}
}