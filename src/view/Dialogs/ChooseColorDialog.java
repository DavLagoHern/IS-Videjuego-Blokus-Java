package view.Dialogs;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.Controller;

public class ChooseColorDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private Controller ctrl;
	private String[] colores = {"VERDE", "ROJO", "AMARILLO", "AZUL"};
	private JPanel mainPanel;
	private DefaultComboBoxModel<String> ColorBoxModel;
	private JComboBox<String> ColorBox;
	private JButton ok;
	private JPanel boxPanel;
	private JPanel okPanel;
	
	/**
	 * Constructor
	 * @param parent
	 * @param controller
	 */
	public ChooseColorDialog(Frame parent, Controller controller) {
		super(parent, "Seleccion de color", true);
		ctrl = controller;
		initGUI();
	}
	
	/**
	 * Funcion que crea y construye el panel principal
	 */
	private void initGUI() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		setContentPane(mainPanel);
		
		boxPanel();
		okPanel();
		
		mainPanel.add(boxPanel);
		mainPanel.add(okPanel);
	}
	
	/**
	 *Funcion que crea el panel de los colores
	 */
	private void boxPanel() {
		boxPanel = new JPanel(new FlowLayout());
		boxPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		ColorBoxModel = new DefaultComboBoxModel<String>();
		for (String color : colores) ColorBoxModel.addElement(color);
		ColorBox = new JComboBox<String>(ColorBoxModel);
		
		boxPanel.add(new JLabel("Elige tu color: "));
		boxPanel.add(ColorBox);
	}
	
	/**
	 * Funcion que crea el panel del boton 'OK'
	 */
	private void okPanel() {
		okPanel = new JPanel();
		ok = new JButton("OK");
		mainPanel.add(ok);
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChooseColorDialog.this.setVisible(false);
				switch((String) ColorBox.getSelectedItem()) {
				case "VERDE":
					ctrl.setJugadorHumano(0);
					break;
				case "ROJO":
					ctrl.setJugadorHumano(1);
					break;
				case "AMARILLO":
					ctrl.setJugadorHumano(2);
					break;
				case "AZUL":
					ctrl.setJugadorHumano(3);
					break;
				}	
			}
		});
		okPanel.add(ok);
	}
	
	/**
	 * Funcion que hace visible el panel
	 */
	public void open() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}