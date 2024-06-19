package fct;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EliminarPadre implements WindowListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame ventana = new JFrame("Eliminar Padre");
	JDialog dlgConfirmacion = new JDialog(ventana, "Confirmación", true);
	JDialog dlgMensaje = new JDialog(ventana, "Mensaje", true);

	JLabel lblElegir = new JLabel("Elegir el Padre a eliminar:");
	JPanel panelPadres = new JPanel();
	JScrollPane scrollPane = new JScrollPane(panelPadres);

	List<String> Padres; // Lista de Padres obtenida de la base de datos
	JCheckBox[] checkBoxes; // Array de checkboxes para cada Padre

	JButton btnEliminar = new JButton("Eliminar");
	JButton btnVolver = new JButton("Volver a Padres");

	Conexion conexion = new Conexion();
	JPanel contentPane;

	public EliminarPadre() {

		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setBounds(100, 100, 1200, 750); // Tamaño más grande
		ventana.addWindowListener(this);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		ventana.setLocationRelativeTo(null);

		ventana.setContentPane(contentPane);
		contentPane.setLayout(null); // Usamos un layout nulo para posicionar los componentes manualmente

		lblElegir.setBounds(20, 20, 200, 30);
		contentPane.add(lblElegir);

		panelPadres.setLayout(new BoxLayout(panelPadres, BoxLayout.Y_AXIS));
		cargarPadres();

		scrollPane.setBounds(20, 65, 1150, 550); // Ajusta el tamaño del scroll panel
		contentPane.add(scrollPane);

		btnVolver.addActionListener(this);
		btnVolver.setBounds(31, 639, 145, 53); // Posiciona el botón "Volver" abajo a la izquierda
		contentPane.add(btnVolver);

		btnEliminar.addActionListener(this);
		btnEliminar.setBounds(938, 639, 152, 53); // Posiciona el botón "Eliminar" abajo a la derecha
		contentPane.add(btnEliminar);

		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	private void cargarPadres() {
		panelPadres.removeAll();
		Padres = conexion.obtenerListadoPadres();
		checkBoxes = new JCheckBox[Padres.size()];
		panelPadres.setPreferredSize(new Dimension(300, Padres.size() * 30)); // Ajusta las proporciones al tamaño de Padre

		for (int i = 0; i < Padres.size(); i++) {
			checkBoxes[i] = new JCheckBox(Padres.get(i));
			panelPadres.add(checkBoxes[i]);
		}


		panelPadres.revalidate();
		panelPadres.repaint();
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		if (dlgConfirmacion.isActive()) {
			dlgConfirmacion.setVisible(false);
			ventana.setVisible(false);
		} else if (dlgMensaje.isActive()) {
			dlgMensaje.setVisible(false);
		} else {
			ventana.setVisible(false);
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnEliminar)) {
			int countSelected = 0; // Contador para el número de checkboxes seleccionados
			for (JCheckBox checkBox : checkBoxes) {
				if (checkBox.isSelected()) {
					countSelected++;
				}
			}
			if (countSelected > 1) {
				JOptionPane.showMessageDialog(ventana, "Solo puedes eliminar un Padre a la vez.", "Error", JOptionPane.ERROR_MESSAGE);
				return; // Salir del método sin continuar con la eliminación
			}
			for (int i = 0; i < checkBoxes.length; i++) {
				if (checkBoxes[i].isSelected()) {
					String[] PadreDatos = Padres.get(i).split(" \\| ");
					if (PadreDatos.length < 2) {
						JOptionPane.showMessageDialog(ventana, "Error en los datos del Padre.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					String id = PadreDatos[0];

					int confirm = JOptionPane.showConfirmDialog(
							ventana,
							"¿Estás seguro de que deseas eliminar este Padre?",
							"Confirmación",
							JOptionPane.YES_NO_OPTION
							);

					if (confirm == JOptionPane.YES_OPTION) {
						int respuesta = conexion.eliminarPadre(id);
						if (respuesta != -1) {
							JOptionPane.showMessageDialog(ventana, "Padre eliminado correctamente");
							cargarPadres();
						} else {
							JOptionPane.showMessageDialog(ventana, "Error al eliminar el Padre.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		} else if (e.getSource().equals(btnVolver)) {
			Padres p = new Padres();
			p.setVisible(true);
			ventana.dispose();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new EliminarPadre();
		});
	}
}
