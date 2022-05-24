package view;

import java.awt.Component;
import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import control.Controller;

public class PointsDialog extends JDialog{

	private static final long serialVersionUID = 1L;
	private String[] colores = { "VERDE", "ROJO", "AMARILLO", "AZUL" };
	private PointsTableModel _pointsTableModel;
	private JTable pointsTable;
	private ArrayList<Integer> points;
	private JPanel mainPanel;
	private JScrollPane tableScroll;
	
	/**
	 * Constructor del dialogo de puntos
	 * @param parent
	 * @param ctrl
	 * @param points
	 */
	public PointsDialog(Frame parent, Controller ctrl, ArrayList<Integer> points) {
		super(parent, "PUNTUACIONES", true);
		this.points = points;
		initGUI();
	}
	
	/**
	 * Inicializa la GUI del dialogo de puntos
	 */
	public void initGUI() {
		this.setBounds(60, 40, 1000, 600);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		_pointsTableModel = new PointsTableModel(points);
		pointsTable = new JTable(_pointsTableModel) {
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}
		};
		tableScroll = new JScrollPane(pointsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(tableScroll);
	}
	
	private class PointsTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		
		ArrayList<Integer> points;
		ArrayList<String> data;
		String[] _header = { "JUGADORES", "PUNTOS" };
		
		/**
		 * Constructor de la tabla de puntos
		 * @param points
		 */
		public PointsTableModel(ArrayList<Integer> points) {
			data = new ArrayList<String>();
			this.points = points;
			writeData();
		}
		
		/**
		 * Guarda los valores en data
		 */
		public void writeData() {
			for (int i = 0; i < points.size(); ++i) {
				data.add(colores[i] + ":");
				data.add(Integer.toString(points.get(i)));
			}
		}

		@Override
		public String getColumnName(int column) { return _header[column]; }
		@Override
		public int getRowCount() { return points.size(); }
		@Override
		public int getColumnCount() { return 2; }
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (columnIndex == 0) return data.get(2 * rowIndex);
			else return data.get(2 * rowIndex + 1);
		}
	}
	
	/**
	 * Abre el Dialog de puntos
	 */
	public void open() {
		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);
		setVisible(true);
	}
}