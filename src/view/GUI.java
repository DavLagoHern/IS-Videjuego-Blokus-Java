package view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import control.Controller;
import model.elements.Observer;
import model.tablero.Casillas;
import view.Dialogs.*;

public class GUI extends JFrame implements Observer{
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JPanel tablero;
	private JPanel buttonPanel;
	private int turno;
	private JPanel mainPanel;
	private JPanel panelIconosAux;
	private JPanel panelIzquierdo;
	private JPanel panelDerecho;
	private JPanel panelIconos;
	private int numPlayers;
	@SuppressWarnings("rawtypes")
	private ArrayList<DefaultListModel> modelList;

	@SuppressWarnings("rawtypes")
	public GUI(Controller ctrl){
		this.controller = ctrl;
		numPlayers = controller.getNumPlayers();
		ctrl.addObserver(this);
		modelList = new ArrayList<DefaultListModel>();
		showConfigDialogs();
	}
	
	/**
	 * Inicializa la GUI general
	 * @param checkIfIA
	 */
	private void initGUI(boolean checkIfIA) {
		controller.clearObservers();
		mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		tablero = new TableroGUI(controller);
		tablero.setMinimumSize(new Dimension(700,700));
		tablero.setMaximumSize(new Dimension(700,700));
		tablero.setPreferredSize(new Dimension(700,700));
		panelIconosAux = new JPanel(new GridLayout(4, 1));
		modelList.clear();
		
		initModelList();
		
		buttonPanel = new ButtonPanel(controller, checkIfIA);

		initPaneles();
	
		mainPanel.add(panelIzquierdo, BorderLayout.WEST);
		mainPanel.add(panelDerecho, BorderLayout.EAST);
	}
	
	/**
	 * Inicializa la lista de los jugadores
	 */
	private void initModelList() {
		for (int j = 0; j < numPlayers; j++) {
			DefaultListModel<ImageIcon> model = new DefaultListModel<ImageIcon>();
			model = new DefaultListModel<ImageIcon>();
			for (int i = 1; i < 22; i++) {
				String url = "src/iconos/" + Integer.toString(j+1) + "/" + Integer.toString(i) + ".PNG";
				model.addElement(new ImageIcon(url));
			}
			JList<ImageIcon> list = new JList<ImageIcon>(model);
			list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			list.setVisibleRowCount(1);
			JScrollPane scrollPanel = new JScrollPane(list);
			scrollPanel.setPreferredSize(new Dimension(500,150));
			scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
			panelIconosAux.add(scrollPanel);
			modelList.add(model);
		}
	}
	
	/**
	 * Inicializa los paneles de la GUI
	 */
	private void initPaneles() {
		panelIzquierdo = new JPanel();
		panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.PAGE_AXIS));
		panelIzquierdo.add(tablero);
		panelIzquierdo.add(buttonPanel);
	
		panelIconos = new JPanel();
		panelIconos.setPreferredSize(new Dimension(500,151*numPlayers));
		panelIconos.add(panelIconosAux);	
	
		panelDerecho = new JPanel();
		panelDerecho.setPreferredSize(new Dimension(500,650));
		panelDerecho.add(panelIconos);
	}
	
	/**
	 * Opciones de muestra de la ventana
	 */
	private void showGUI() {
		this.setBounds(100, 50, 1300, 720);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * Dialogo para seleccionar el modo de juego
	 */
	private void showConfigDialogs() {
		boolean exit = false;
		Object[] options = {"Multijugador", "IA"};
		int n = JOptionPane.showOptionDialog(this, "Elige un modo de juego", "Modo de juego",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[1]);
		if(n != -1) { //Si no elegimos modo, se termina el juego
			NumPlayersDialog numPlayersDialog = new NumPlayersDialog(this, controller);
			numPlayersDialog.open();
			if(controller.getNumPlayers() != -1) {
				if(n == 1) {
					ChooseColorDialog colorDialog = new ChooseColorDialog(this, controller);
					IADifficultyDialog IADifficultyDialog = new IADifficultyDialog(this, controller);
					if(controller.getNumPlayers() != -1) { //si cerramos la ventana sin elegir jugadores, se termina el juego
						colorDialog.open();
						if(controller.getJugadorHumano() != -1) { 
							IADifficultyDialog.open();
							if(!controller.getIAMode().equals("")) {
								initGUI(true);
								showGUI();							
							}
							else exit = true;
						}
						else exit = true; //si cerramos la ventana sin color, se termina el juego
					}
					else exit = true;
				}
				else {
					initGUI(false);
					showGUI();
				}
			}
			else exit = true;
		}
		else exit = true;
		if(exit) System.exit(0);
	}

	@Override
	public void onRegister(Casillas[][] tablero) {}
	@Override
	public void changeBoard(Casillas[][] tablero) {}
	/**
	 * Actualiza el turno
	 */
	@Override
	public void turnChanged(int turno) { this.turno = turno; }
	@Override
	public void pointsChanged(ArrayList<Integer> points) {}
	@Override
	public void isFinished(boolean end) {}
	/**
	 * Comprueba y elimina fichas
	 */
	@Override
	public void cambioFichas(int index) {
		for (int i = 0; i < modelList.get(turno).size(); ++i) {
			if(modelList.get(turno).get(i).toString().equals("src/iconos/" + Integer.toString(turno + 1) + "/" + Integer.toString(index) + ".PNG")) {
				modelList.get(turno).remove(i);
				break;
			}
		}
	}
	/**
	 * Actualiza el numero de jugadores
	 */
	@Override
	public void changeNumPlayers(int numPlayers) { this.numPlayers = numPlayers; }
}