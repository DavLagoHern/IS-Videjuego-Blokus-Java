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

public class IADifficultyDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private Controller ctrl;
	private JPanel mainPanel;
	private DefaultComboBoxModel<String> difficultyBoxModel;
	private JComboBox<String> difficultyBox;
	private JButton ok;
	private JPanel boxPanel;
	private JPanel okPanel;
	
	/**
	 * Constructor
	 * @param parent
	 * @param controller
	 */
	public IADifficultyDialog(Frame parent, Controller controller) {
		super(parent, "Seleccion de la dificultad de la IA", true);
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
	 * Funcion creadora del panel de seleccion de la dificultad
	 */
	private void boxPanel() {
		boxPanel = new JPanel(new FlowLayout());
		boxPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		difficultyBoxModel = new DefaultComboBoxModel<String>();
		difficultyBoxModel.addElement("FACIL");
		difficultyBoxModel.addElement("DIFICIL");
		difficultyBox = new JComboBox<String>(difficultyBoxModel);
		
		boxPanel.add(new JLabel("Elige la dificultad de la IA: "));
		boxPanel.add(difficultyBox);
	}
	
	/**
	 * Funcion creadora del panel con boton "OK"
	 */
	private void okPanel() {
		okPanel = new JPanel();
		ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IADifficultyDialog.this.setVisible(false);
				ctrl.setIAMode((String) difficultyBox.getSelectedItem());
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