package fct;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EditarPracticaEnf implements WindowListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame ventana = new JFrame("Editar Práctica");
	JPanel contentPane;

	List<String> practicas; // Lista de prácticas obtenida de la base de datos
	JCheckBox[] checkBoxes; // Array de checkboxes para cada práctica

	JButton btnEditar = new JButton("Editar");
	JButton btnVolver = new JButton("Volver a Sede");

	Conexion conexion = new Conexion();
	String idPractica = "";

	public EditarPracticaEnf() {

		ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		ventana.setBounds(100, 100, 1200, 750);
		ventana.addWindowListener(this);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        ventana.setLocationRelativeTo(null);

		ventana.setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblElegir = new JLabel("Elegir la Práctica a editar:");
		lblElegir.setBounds(20, 20, 200, 30);
		contentPane.add(lblElegir);

		JPanel panelPractica = new JPanel();
		panelPractica.setLayout(new BoxLayout(panelPractica, BoxLayout.Y_AXIS));
		practicas = conexion.obtenerListadoPracticaEnf(); // Método para obtener las prácticas de la base de datos
		checkBoxes = new JCheckBox[practicas.size()];
		panelPractica.setPreferredSize(new Dimension(300, practicas.size() * 30));

		for (int i = 0; i < practicas.size(); i++) {
			checkBoxes[i] = new JCheckBox(practicas.get(i));
			panelPractica.add(checkBoxes[i]);
		}

		JScrollPane scrollPane = new JScrollPane(panelPractica);
		scrollPane.setBounds(20, 65, 1150, 550);
		contentPane.add(scrollPane);

		btnVolver.addActionListener(this);
		btnVolver.setBounds(31, 639, 145, 53);
		contentPane.add(btnVolver);

		btnEditar.addActionListener(this);
		btnEditar.setBounds(938, 639, 152, 53);
		contentPane.add(btnEditar);

		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		ventana.setVisible(false);
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
	    if (e.getSource().equals(btnEditar)) {
	        int countSelected = 0;
	        for (JCheckBox checkBox : checkBoxes) {
	            if (checkBox.isSelected()) {
	                countSelected++;
	            }
	        }
	        if (countSelected != 1) {
	            JOptionPane.showMessageDialog(ventana, "Seleccione una práctica para editar.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	        for (int i = 0; i < checkBoxes.length; i++) {
	            if (checkBoxes[i].isSelected()) {
	                String[] practicaDatos = practicas.get(i).split(" - ");
	                if (practicaDatos.length < 5) {
	                    JOptionPane.showMessageDialog(ventana, "Error en los datos de la práctica.", "Error", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	                String id = practicaDatos[0];
	                String resultado = conexion.getDatosEdicionPractica(id);
	                String[] datos = resultado.split("-");

	                idPractica = datos[0];

	                JDialog dlgEdicion = new JDialog(ventana, "Edición de Práctica", true);
	                dlgEdicion.getContentPane().setLayout(new BorderLayout());
	                dlgEdicion.setSize(600, 400);

	                JPanel panelDatos = new JPanel(new GridLayout(0, 2, 5, 5));
	                panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	                dlgEdicion.getContentPane().add(panelDatos, BorderLayout.CENTER);

	                JLabel lblTitulo = new JLabel("Edición de Práctica", SwingConstants.CENTER);
	                dlgEdicion.getContentPane().add(lblTitulo, BorderLayout.NORTH);

	                JLabel[] labels = {
	                        new JLabel("Horario:"),
	                        new JLabel("Alumna ID:"),
	                        new JLabel("Sede ID:"),
	                        new JLabel("Contacto ID:")
	                };

	                JTextField[] fields = {
	                        new JTextField(datos[1]),  // Horario
	                        new JTextField(datos[2]),  // Sede ID
	                        new JTextField(datos[3]),  // Contacto ID
	                        new JTextField(datos[4])   // No estoy seguro del último campo, asumo es Contacto ID
	                };

	                // ComboBox para 'idAlumna'
	                JComboBox<String> comboAlumna = new JComboBox<>();
	                conexion.rellenarChoiceAlumna(comboAlumna); // Método para rellenar el combo con alumnas
	                comboAlumna.setSelectedItem(datos[1]); // Seleccionar el item correspondiente al ID de alumna

	                // ComboBox para 'idSede'
	                JComboBox<String> comboSedes = new JComboBox<>();
	                conexion.rellenarChoiceSedesEnf(comboSedes); // Método para rellenar el combo con sedes
	                comboSedes.setSelectedItem(datos[2]); // Seleccionar el item correspondiente al ID de sede

	                // ComboBox para 'idContacto'
	                JComboBox<String> comboContactos = new JComboBox<>();
	                conexion.rellenarChoiceContactosEnf(comboContactos); // Método para rellenar el combo con contactos
	                comboContactos.setSelectedItem(datos[3]); // Seleccionar el item correspondiente al ID de contacto

	                for (int j = 0; j < labels.length; j++) {
	                    panelDatos.add(labels[j]);
	                    if (j == 1) { // Si es el campo de Alumna, añadir el combo de Alumna
	                        panelDatos.add(comboAlumna);
	                    } else if (j == 2) { // Si es el campo de Sedes, añadir el combo de Sedes
	                        panelDatos.add(comboSedes);
	                    } else if (j == 3) { // Si es el campo de Contactos, añadir el combo de Contactos
	                        panelDatos.add(comboContactos);
	                    } else { // Para los demás campos, añadir los campos de texto
	                        panelDatos.add(fields[j]);
	                    }
	                }

	                JButton btnModificar = new JButton("Modificar");
	                btnModificar.addActionListener(new ActionListener() {
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                        // Modificar la práctica con los datos del formulario
	                        String horario = fields[0].getText();
	                        int idAlumna = Integer.parseInt(((String) comboAlumna.getSelectedItem()).split("-")[0]);
	                        int idSede = Integer.parseInt(((String) comboSedes.getSelectedItem()).split("-")[0]);
	                        int idContacto = Integer.parseInt(((String) comboContactos.getSelectedItem()).split("-")[0]);

	                        String sentencia = "UPDATE Practicas SET " +
	                                "horario='" + horario + "', " +
	                                "idAlumna=" + idAlumna + ", " +
	                                "idSede=" + idSede + ", " +
	                                "idContacto=" + idContacto + " " +
	                                "WHERE idPractica=" + idPractica;

	                        try {
	                            conexion.modificarAlumna(sentencia); // Método para modificar en la base de datos

	                            JOptionPane.showMessageDialog(dlgEdicion, "Práctica modificada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	                            dlgEdicion.setVisible(false);
	                        } catch (Exception ex) {
	                            ex.printStackTrace();
	                            JOptionPane.showMessageDialog(dlgEdicion, "Error al modificar la práctica.", "Error", JOptionPane.ERROR_MESSAGE);
	                        }
	                    }
	                });

	                JButton btnCancelar = new JButton("Cancelar");
	                btnCancelar.addActionListener(new ActionListener() {
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                        dlgEdicion.setVisible(false);
	                    }
	                });

	                JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
	                panelBotones.add(btnModificar);
	                panelBotones.add(btnCancelar);
	                dlgEdicion.getContentPane().add(panelBotones, BorderLayout.SOUTH);

	                dlgEdicion.setLocationRelativeTo(null);
	                dlgEdicion.setVisible(true);
	            }
	        }
	    } else if (e.getSource().equals(btnVolver)) {
	        ventana.setVisible(false);
	        // Aquí decides qué acción tomar al volver
	    }
	}


	public static void main(String[] args) {
		new EditarPracticaEnf();
	}
}
