package view.Dialogs;

import java.awt.Dimension;
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

public class NumPlayersDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private Controller ctrl;
	private JPanel mainPanel;
	private DefaultComboBoxModel<Integer> numPlayersBoxModel;
	private JComboBox<Integer> numPlayersBox;
	private JButton ok;
	private JPanel boxPanel;
	private JPanel okPanel;
	
	/**
	 * Constructor
	 * @param parent
	 * @param controller
	 */
	public NumPlayersDialog(Frame parent, Controller controller) {
		super(parent, "Seleccion de numero de jugadores", true);
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
	 * Funcion que crea el panel para seleccionar el numero de jugadores
	 */
	private void boxPanel() {
		boxPanel = new JPanel(new FlowLayout());
		boxPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		numPlayersBoxModel = new DefaultComboBoxModel<Integer>();
		numPlayersBoxModel.addElement(2);
		numPlayersBoxModel.addElement(3);
		numPlayersBoxModel.addElement(4);
		numPlayersBox = new JComboBox<Integer>(numPlayersBoxModel);
		numPlayersBox.setPreferredSize(new Dimension(50, 20));
		
		boxPanel.add(new JLabel("Elige el numero de jugadores: "));
		boxPanel.add(numPlayersBox);
	}
	
	/**
	 * Funcion que contiene el panel con el boton 'OK'
	 */
	private void okPanel() {
		okPanel = new JPanel();
		ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NumPlayersDialog.this.setVisible(false);
				ctrl.setNumPlayers((int) numPlayersBox.getSelectedItem());
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