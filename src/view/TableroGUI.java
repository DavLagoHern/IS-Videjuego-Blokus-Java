package view;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import control.Controller;
import model.elements.Observer;
import model.tablero.Casillas;

public class TableroGUI extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	
	private Controller ctrl;
	private JPanel[][] casillas;	
	private ArrayList<Integer> points;
	private boolean points_table = false;

	/**
	 * Constructor del GUI del tablero
	 * @param ctrl
	 */
	public TableroGUI(Controller ctrl) {
		this.ctrl = ctrl;
		casillas = new JPanel[20][20];
		points = new ArrayList<Integer>();
		initGUI();
		ctrl.addObserver(this);
	}
	
	/**
	 * Incializa la GUI del tablero
	 */
	private void initGUI() {
		setLayout(new GridLayout(20,20));
		setBorder(BorderFactory.createLineBorder(Color.black));
		MouseManager mouseManager = new MouseManager();
		for (int i = 0; i < 20; ++i) {
			for (int j = 0; j < 20; ++j) {
				casillas[i][j] = new JPanel();
				casillas[i][j].setBackground(Color.white);
				casillas[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				casillas[i][j].setSize(25,25);
				casillas[i][j].addMouseListener(mouseManager);
				add(casillas[i][j]);
			}
		}
		KeyManager keyManager = new KeyManager();
		this.addKeyListener(keyManager);
	}
	
	class MouseManager implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) { requestFocus(); }
		@Override
		public void mouseExited(MouseEvent e) {}
	}
	
	class KeyManager implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {}

		/**
		 * Seleccion de tecla para mover la ficha
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				ctrl.move("UP");
				break;
			case KeyEvent.VK_DOWN:
				ctrl.move("DOWN");
				break;
			case KeyEvent.VK_RIGHT:
				ctrl.move("RIGHT");
				break;
			case KeyEvent.VK_LEFT:
				ctrl.move("LEFT");
				break;
			case KeyEvent.VK_N:
				ctrl.rotate("LEFT");
				break;
			case KeyEvent.VK_M:
				ctrl.rotate("RIGHT");
				break;
			case KeyEvent.VK_ENTER:
				if(ctrl.confirmarFicha())
					ctrl.checkEnd();
				break;
			case KeyEvent.VK_H:
				JOptionPane.showMessageDialog(getParent(), "Para seleccionar ficha pulsa en el boton del color que corresponda."
						+ "\n- Flechas para mover la ficha.\n- 'n' para rotar la ficha a la izquierda."
						+ "\n- 'm' para rotar la ficha a la derecha.\n- Enter para confirmar ficha."
						+ "\n- 'p' para mostrar la puntuacion.\n- 'h' para ayuda.\n Las esquinas de inicio son:\n VERDE: superior izquierda."
						+ "\n ROJO: superior derecha.\n AMARILLO: inferior izquierda.\n AZUL: inferior derecha.", "AYUDA",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			case KeyEvent.VK_P:
				crearTablaPuntuaciones();
				break;
			default:
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {}
	}
	
	/**
	 * Creacion de la tabla de puntuaciones
	 */
	public void crearTablaPuntuaciones() {
		PointsDialog pointsDialog = new PointsDialog((Frame) SwingUtilities.getWindowAncestor(this), ctrl, points);
		pointsDialog.open();
		points_table = true;
	}
		
	/**
	 * Aplica el color a cada casilla
	 * @param tablero
	 */
	private void selectColor(Casillas[][] tablero) {
		for (int i = 0; i < 20; ++i) {
			for (int j = 0; j < 20; ++j) {
				switch(tablero[i][j]) {
				case VACIO:
					casillas[i][j].setBackground(Color.white);
					break;
				case VERDE:
					casillas[i][j].setBackground(Color.green);
					break;
				case ROJO:
					casillas[i][j].setBackground(Color.red);
					break;
				case AMARILLO:
					casillas[i][j].setBackground(Color.yellow);
					break;
				case AZUL:
					casillas[i][j].setBackground(Color.blue);
					break;
				}
			}
		}
	}

	/**
	 * Al registrar se vuelve a pintar el tablero
	 */
	public void onRegister(Casillas[][] tablero) { selectColor(tablero); }

	/**
	 * Al detectarse un cambio en el tablero se vuelve a pintar las casillas
	 */
	public void changeBoard(Casillas[][] tablero) { selectColor(tablero); }

	/**
	 * Se actualizan los puntos
	 */
	@Override
	public void pointsChanged(ArrayList<Integer> points) { this.points = points; }
	
	/**
	 * Se comprueba el final del juego para crear la tabla de puntuaciones
	 */
	@Override
	public void isFinished(boolean end) {
			if(end && !points_table) crearTablaPuntuaciones();
	}
	@Override
	public void turnChanged(int turno) {}
	@Override
	public void cambioFichas(int index) {}
	@Override
	public void changeNumPlayers(int numPlayers) {}
}