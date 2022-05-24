package view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.Controller;
import model.elements.Observer;
import model.tablero.Casillas;

public class ButtonPanel extends JPanel implements Observer{
	
	private static final long serialVersionUID = 1L;
	private Controller ctrl;
	private JButton redButton;
	private JButton greenButton;
	private JButton yellowButton;
	private JButton blueButton;
	private JButton pasarTurnoButton;
	private JButton cancelarFichaButton;
	private JLabel help;
	private int numPlayers;
	private ArrayList<JButton> playerButton;
	private Casillas color;
	private boolean game_ended = false;
	
	private PlayersButtonManager button_funct;
	/**
	 * Constructor del ButtonPanel
	 * 
	 * @param ctrl
	 * @param checkIfIA
	 */
	public ButtonPanel(Controller ctrl, boolean checkIfIA) {
		this.numPlayers = ctrl.getNumPlayers();
		this.ctrl = ctrl;
		ctrl.addObserver(this);
		playerButton = new ArrayList<>();
		this.setLayout(new FlowLayout());
		initGUI();
		ctrl.setTurno(checkIfIA);
	}
	
	/**
	 * Inicializa la GUI del panel de botones
	 */
	private void initGUI() {
		greenButton = new JButton("Green Player");
		playerButton.add(greenButton);
		redButton = new JButton("Red Player");
		playerButton.add(redButton);
		yellowButton = new JButton("Yellow Player");
		playerButton.add(yellowButton);
		blueButton = new JButton("Blue Player");
		playerButton.add(blueButton);
		pasarTurnoButton = new JButton("Pasar Turno");
		cancelarFichaButton = new JButton("Cancelar Ficha");
		help = new JLabel("Pulsa 'h' para mostrar la ayuda");
		ActionButtonManager action_funct = new ActionButtonManager();
		pasarTurnoButton.addActionListener(action_funct);
		cancelarFichaButton.addActionListener(action_funct);
		button_funct = new PlayersButtonManager();
		for(int i = 0; i < numPlayers; ++i) {
			this.add(playerButton.get(i));
			playerButton.get(i).addActionListener(button_funct);
		}
		this.add(pasarTurnoButton);
		this.add(cancelarFichaButton);

		this.add(help);
	}
	
	/**
	 * Termina el juego
	 */
	public void endGame() {
		for(int i = 0; i < playerButton.size(); ++i) {
			playerButton.get(i).setEnabled(false);
		}
		pasarTurnoButton.setEnabled(false);
		cancelarFichaButton.setEnabled(false);
		game_ended = true;
		
	}
	
	class ActionButtonManager implements ActionListener {		
		/**
		 * Comprobar si pasa turno o cancelar ficha
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == pasarTurnoButton)
				ctrl.setTurno(true);
			else if(e.getSource() == cancelarFichaButton)
				ctrl.cancelarFicha();
			
		}
	}
	
	class PlayersButtonManager implements ActionListener {
		/**
		 * Asignar color y colocar llamada a asignar la ficha
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int fichaColocar = -1;
			if (e.getSource() == redButton) color = Casillas.ROJO;
			else if (e.getSource() == greenButton) color = Casillas.VERDE;
			else if(e.getSource() == yellowButton) color = Casillas.AMARILLO;
			else if(e.getSource() == blueButton) color = Casillas.AZUL;
			fichaColocar = queFichaColocar();
			if(fichaColocar < 22 && fichaColocar > 0) ctrl.addFicha(fichaColocar, color);
		}
	}
	
	/**
	 * Seleccionar la ficha que se quiera colocar
	 * 
	 * @return opcion
	 */
	public int queFichaColocar() {
		int opcion = -1;
		String n = (String)JOptionPane.showInputDialog( this, "¿Que ficha quieres colocar?",
				"¿Que ficha quieres colocar?", JOptionPane.QUESTION_MESSAGE, null, null, "");
		if(n != null) {
			opcion = Integer.parseInt(n);			
		}
		return opcion;
	}

	@Override
	public void onRegister(Casillas[][] tablero) {}
	
	@Override
	public void changeBoard(Casillas[][] tablero) {}

	/**
	 * Cambia el turno del jugador
	 */
	@Override
	public void turnChanged(int turno) {
		for (int i = 0; i < numPlayers; ++i) {
			if (i != turno) playerButton.get(i).setEnabled(false);
			else playerButton.get(i).setEnabled(true);
		}
	}

	@Override
	public void pointsChanged(ArrayList<Integer> points) {}

	/**
	 * Comprueba si ha llegado al final del juego
	 */
	@Override
	public void isFinished(boolean end) {
		if (end && !game_ended) endGame();	
	}

	@Override
	public void cambioFichas(int index) {}

	/**
	 * Cambia el numero de jugadores
	 */
	@Override
	public void changeNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
		for (int i = 0; i < numPlayers; ++i) {
			this.add(playerButton.get(i));
			playerButton.get(i).addActionListener(button_funct);
		}
	}
}