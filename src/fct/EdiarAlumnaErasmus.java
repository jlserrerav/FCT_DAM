package fct;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EdiarAlumnaErasmus extends JFrame implements WindowListener, ActionListener {
	JFrame ventana = new JFrame("Editar Alumna");
	JDialog dlgEdicion = new JDialog(ventana, "Edición", true);
	JDialog dlgMensaje = new JDialog(ventana, "Mensaje", true);

	JLabel lblElegir = new JLabel("Elegir la Alumna a editar:");
	JPanel panelAlumnas = new JPanel();
	JScrollPane scrollPane = new JScrollPane(panelAlumnas);

	List<String> alumnas; // Lista de alumnas obtenida de la base de datos
	JCheckBox[] checkBoxes; // Array de checkboxes para cada alumna

	JButton btnEditar = new JButton("Editar");

	Conexion conexion = new Conexion();

	JLabel lblTitulo = new JLabel("------- Edición de Alumna -------");
	JLabel lblNombre = new JLabel("Nombre:");
	JLabel lblPrimerApellido = new JLabel("Primer Apellido:");
	JLabel lblSegundoApellido = new JLabel("Segundo Apellido:");
	JLabel lblDni = new JLabel("DNI:");
	JLabel lblDireccion = new JLabel("Dirección:");
	JLabel lblTelefono = new JLabel("Teléfono:");
	JLabel lblEmailPersonal = new JLabel("Email Personal:");
	JLabel lblEmailCentro = new JLabel("Email Centro:");
	JLabel lblCicloFormativo = new JLabel("Ciclo Formativo:");
	JLabel lblCurso = new JLabel("Curso:");
	JLabel lblAñoInicio = new JLabel("Año Inicio:");
	JLabel lblAñoFin = new JLabel("Año Fin:");
	JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento:");
	JLabel lblMensaje = new JLabel("Modificación de Alumna Correcta");

	JTextField txtNombre = new JTextField(50);
	JTextField txtPrimerApellido = new JTextField(50);
	JTextField txtSegundoApellido = new JTextField(50);
	JTextField txtDni = new JTextField(50);
	JTextField txtDireccion = new JTextField(50);
	JTextField txtTelefono = new JTextField(50);
	JTextField txtEmailPersonal = new JTextField(50);
	JTextField txtEmailCentro = new JTextField(50);
	JTextField txtCicloFormativo = new JTextField(50);
	JTextField txtCurso = new JTextField(50);
	JTextField txtAñoInicio = new JTextField(50);
	JTextField txtAñoFin = new JTextField(50);
	JTextField txtFechaNacimiento = new JTextField(50);

	JButton btnModificar = new JButton("Modificar");
	JButton btnCancelar = new JButton("Cancelar");
	JButton btnVolver = new JButton("Volver a Alumna");
	
    private JPanel contentPane;



	String idAlumna = "";

	public EdiarAlumnaErasmus() {
    	setTitle("Editar Alumnas en Erasmus"); // Título de la ventana

		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setBounds(100, 100, 1200, 750); // Tamaño más grande
		ventana.addWindowListener(this);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);

		ventana.setContentPane(contentPane);
		contentPane.setLayout(null); // Usamos un layout nulo para posicionar los componentes manualmente

		JLabel lblElegir = new JLabel("Elegir la Alumna a editar:");
		lblElegir.setBounds(20, 20, 200, 30);
		contentPane.add(lblElegir);

		panelAlumnas = new JPanel();
		panelAlumnas.setLayout(new BoxLayout(panelAlumnas, BoxLayout.Y_AXIS));
		alumnas = conexion.obtenerListadoAlumnasErasmus();
		checkBoxes = new JCheckBox[alumnas.size()];
		panelAlumnas.setPreferredSize(new Dimension(300, alumnas.size() * 30)); // Ajusta las proporciones al tamaño de Alumna

		for (int i = 0; i < alumnas.size(); i++) {
			checkBoxes[i] = new JCheckBox(alumnas.get(i));
			panelAlumnas.add(checkBoxes[i]);
		}

		scrollPane = new JScrollPane(panelAlumnas);
		scrollPane.setBounds(20, 65, 1150, 550); // Ajusta el tamaño del scroll panel
		contentPane.add(scrollPane);

		btnVolver = new JButton("Volver a Alumna");
		btnVolver.addActionListener(this);
		btnVolver.setBounds(31, 639, 145, 53); // Posiciona el botón "Volver" abajo a la izquierda
		contentPane.add(btnVolver);

		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(this);
		btnEditar.setBounds(938, 639, 152, 53); // Posiciona el botón "Editar" abajo a la derecha
		contentPane.add(btnEditar);

		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		if (dlgEdicion.isActive()) {
			dlgEdicion.setVisible(false);
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
		if (e.getSource().equals(btnEditar)) {
			int countSelected = 0; // Contador para el número de checkboxes seleccionados
	        for (JCheckBox checkBox : checkBoxes) {
	            if (checkBox.isSelected()) {
	                countSelected++;
	            }
	        }
	        if (countSelected > 1) {
	            JOptionPane.showMessageDialog(ventana, "Solo puedes editar una alumna a la vez.", "Error", JOptionPane.ERROR_MESSAGE);
	            return; // Salir del método sin continuar con la edición
	        }
			for (int i = 0; i < checkBoxes.length; i++) {
				if (checkBoxes[i].isSelected()) {
					String id = alumnas.get(i).split(" | ")[0];
					String resultado = conexion.getDatosEdicionAlumna(id);
					String[] datos = resultado.split("\\|"); // Usar el nuevo delimitador
					idAlumna = datos[0];

					JDialog dlgEdicion = new JDialog(this, "Edición", true);
					dlgEdicion.getContentPane().setLayout(new BorderLayout());
					dlgEdicion.setSize(600, 400);
					dlgEdicion.addWindowListener(this);

					JPanel panelDatos = new JPanel(new GridLayout(0, 2, 5, 5)); // 0 filas, 2 columnas
					panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Agrega un borde
					dlgEdicion.getContentPane().add(panelDatos, BorderLayout.CENTER);

					JLabel lblTitulo = new JLabel("------- Edición de Alumna -------", SwingConstants.CENTER);
					dlgEdicion.getContentPane().add(lblTitulo, BorderLayout.NORTH);

					JLabel[] labels = {
							new JLabel("Nombre:"),
							new JLabel("Primer Apellido:"),
							new JLabel("Segundo Apellido:"),
							new JLabel("DNI:"),
							new JLabel("Dirección:"),
							new JLabel("Teléfono:"),
							new JLabel("Email Personal:"),
							new JLabel("Email Centro:"),
							new JLabel("Ciclo Formativo:"),
							new JLabel("Curso:"),
							new JLabel("Año Inicio:"),
							new JLabel("Año Fin:"),
							new JLabel("Fecha de Nacimiento:")
					};

					JTextField[] fields = {
							new JTextField(datos[1]),
							new JTextField(datos[2]),
							new JTextField(datos[3]),
							new JTextField(datos[4]),
							new JTextField(datos[5]),
							new JTextField(datos[6]),
							new JTextField(datos[7]),
							new JTextField(datos[8]),
							new JTextField(datos[9]),
							new JTextField(datos[10]),
							new JTextField(datos[11]),
							new JTextField(datos[12]),
							new JTextField(datos[13])
					};

					for (int j = 0; j < labels.length; j++) {
						panelDatos.add(labels[j]);
						panelDatos.add(fields[j]);
					}

					JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
					dlgEdicion.getContentPane().add(panelBotones, BorderLayout.SOUTH);

					JButton btnModificar = new JButton("Modificar");
					btnModificar.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// Modificar la alumna con los datos del formulario
							String sentencia = "UPDATE Alumnas SET " +
									"nombreAlumna='" + fields[0].getText() + "', " +
									"primerApellidoAlumna='" + fields[1].getText() + "', " +
									"segundoApellidoAlumna='" + fields[2].getText() + "', " +
									"dniAlumna='" + fields[3].getText() + "', " +
									"direccionAlumna='" + fields[4].getText() + "', " +
									"telefonoAlumna='" + fields[5].getText() + "', " +
									"emailPersonalAlumna='" + fields[6].getText() + "', " +
									"emailCentroAlumna='" + fields[7].getText() + "', " +
									"cicloFormativoAlumna='" + fields[8].getText() + "', " +
									"cursoAlumna=" + fields[9].getText() + ", " +
									"añoInicioAlumna=" + fields[10].getText() + ", " +
									"añoFinAlumna=" + fields[11].getText() + ", " +
									"fechaNacimientoAlumna='" + fields[12].getText() + "' " +
									"WHERE idAlumna=" + idAlumna + ";";

							int respuesta = conexion.modificarAlumna(sentencia);
							if (respuesta != 0) {
								JOptionPane.showMessageDialog(dlgEdicion, "Error en la modificación");
							} else {
								JOptionPane.showMessageDialog(dlgEdicion, "Modificación de alumna correcta");
								dlgEdicion.dispose(); // Cerrar el diálogo después de modificar
							}
						}
					});

					JButton btnCancelar = new JButton("Cancelar");
					btnCancelar.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							dlgEdicion.dispose(); // Cerrar el diálogo sin modificar
						}
					});

					panelBotones.add(btnModificar);
					panelBotones.add(btnCancelar);

					dlgEdicion.setResizable(false);
					dlgEdicion.setLocationRelativeTo(this);
					dlgEdicion.setVisible(true);
				}
			}
		}else if (e.getSource().equals(btnVolver)) {
			// Cerrar la ventana actual y abrir la clase Alumna
			ventana.dispose();
			Alumnas alumna = new Alumnas();
			alumna.setVisible(true);
		}
	}
	public static void main(String[] args) {
		new EdiarAlumnaErasmus();
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub

	}

}

