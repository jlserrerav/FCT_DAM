package fct;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AltaAlumna extends JFrame implements WindowListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtSegundoApellidos;
	private JTextField txtTelefono;
	private JTextField txtDni;
	private JTextField txtDireccion;
	private JTextField txtEmail;
	private JTextField txtEmailCentro;
	private JTextField txtCurso;
	private JTextField txtAnoInicio;
	private JTextField txtAnoFin;
	private JTextField txtFechaNaci;
	private JTextField txtPrimerApellido;
	private JButton btnAlta;
	private JButton btnVolver;
	private JComboBox<String> choiceCiclo;
	private JComboBox<String> choiceErasmus;

	private Conexion conexion = new Conexion();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaAlumna frame = new AltaAlumna();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AltaAlumna() {
		setTitle("Alta de Alumna"); // Título de la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 748, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		contentPane.add(lblNombre, gbc_lblNombre);

		txtNombre = new JTextField();
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.gridx = 2;
		gbc_txtNombre.gridy = 1;
		contentPane.add(txtNombre, gbc_txtNombre);
		txtNombre.setColumns(10);

		JLabel lblEmailCentro = new JLabel("Email del Centro:");
		GridBagConstraints gbc_lblEmailCentro = new GridBagConstraints();
		gbc_lblEmailCentro.anchor = GridBagConstraints.EAST;
		gbc_lblEmailCentro.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmailCentro.gridx = 6;
		gbc_lblEmailCentro.gridy = 1;
		contentPane.add(lblEmailCentro, gbc_lblEmailCentro);

		txtEmailCentro = new JTextField();
		txtEmailCentro.setColumns(10);
		GridBagConstraints gbc_txtEmailCentro = new GridBagConstraints();
		gbc_txtEmailCentro.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmailCentro.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmailCentro.gridx = 7;
		gbc_txtEmailCentro.gridy = 1;
		contentPane.add(txtEmailCentro, gbc_txtEmailCentro);

		JLabel lblPrimerApellidos = new JLabel("Primer Apellidos:");
		GridBagConstraints gbc_lblPrimerApellidos = new GridBagConstraints();
		gbc_lblPrimerApellidos.anchor = GridBagConstraints.EAST;
		gbc_lblPrimerApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrimerApellidos.gridx = 1;
		gbc_lblPrimerApellidos.gridy = 2;
		contentPane.add(lblPrimerApellidos, gbc_lblPrimerApellidos);

		txtPrimerApellido = new JTextField();
		txtPrimerApellido.setColumns(10);
		GridBagConstraints gbc_txtPrimerApellido = new GridBagConstraints();
		gbc_txtPrimerApellido.insets = new Insets(0, 0, 5, 5);
		gbc_txtPrimerApellido.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrimerApellido.gridx = 2;
		gbc_txtPrimerApellido.gridy = 2;
		contentPane.add(txtPrimerApellido, gbc_txtPrimerApellido);

		JLabel lblCiclo = new JLabel("Ciclo Formativo:");
		GridBagConstraints gbc_lblCiclo = new GridBagConstraints();
		gbc_lblCiclo.anchor = GridBagConstraints.EAST;
		gbc_lblCiclo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCiclo.gridx = 6;
		gbc_lblCiclo.gridy = 2;
		contentPane.add(lblCiclo, gbc_lblCiclo);

		choiceCiclo = new JComboBox<>();
		GridBagConstraints gbc_choiceCiclo = new GridBagConstraints();
		gbc_choiceCiclo.insets = new Insets(0, 0, 5, 5);
		gbc_choiceCiclo.fill = GridBagConstraints.HORIZONTAL;
		gbc_choiceCiclo.gridx = 7;
		gbc_choiceCiclo.gridy = 2;
		choiceCiclo.addItem("Ciclo de Enfermería");
		choiceCiclo.addItem("Ciclo de Gestión Administrativa");
		choiceCiclo.addItem("Ciclo de Educación Infantil");
		choiceCiclo.addItem("Ciclo de Educación Infantil Online");
		choiceCiclo.addItem("Ciclo de Asistencia a la Dirección");
		contentPane.add(choiceCiclo, gbc_choiceCiclo);

		JLabel lblSegundoApellidos = new JLabel("Segundo Apellidos:");
		GridBagConstraints gbc_lblSegundoApellidos = new GridBagConstraints();
		gbc_lblSegundoApellidos.anchor = GridBagConstraints.EAST;
		gbc_lblSegundoApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_lblSegundoApellidos.gridx = 1;
		gbc_lblSegundoApellidos.gridy = 3;
		contentPane.add(lblSegundoApellidos, gbc_lblSegundoApellidos);

		txtSegundoApellidos = new JTextField();
		txtSegundoApellidos.setColumns(10);
		GridBagConstraints gbc_txtSegundoApellidos = new GridBagConstraints();
		gbc_txtSegundoApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_txtSegundoApellidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSegundoApellidos.gridx = 2;
		gbc_txtSegundoApellidos.gridy = 3;
		contentPane.add(txtSegundoApellidos, gbc_txtSegundoApellidos);

		JLabel lblCurso = new JLabel("Curso:");
		GridBagConstraints gbc_lblCurso = new GridBagConstraints();
		gbc_lblCurso.anchor = GridBagConstraints.EAST;
		gbc_lblCurso.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurso.gridx = 6;
		gbc_lblCurso.gridy = 3;
		contentPane.add(lblCurso, gbc_lblCurso);

		txtCurso = new JTextField();
		txtCurso.setColumns(10);
		GridBagConstraints gbc_txtCurso = new GridBagConstraints();
		gbc_txtCurso.insets = new Insets(0, 0, 5, 5);
		gbc_txtCurso.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCurso.gridx = 7;
		gbc_txtCurso.gridy = 3;
		contentPane.add(txtCurso, gbc_txtCurso);

		JLabel lblTelefono = new JLabel("Teléfono:");
		GridBagConstraints gbc_lblTelefono = new GridBagConstraints();
		gbc_lblTelefono.anchor = GridBagConstraints.EAST;
		gbc_lblTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefono.gridx = 1;
		gbc_lblTelefono.gridy = 4;
		contentPane.add(lblTelefono, gbc_lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		GridBagConstraints gbc_txtTelefono = new GridBagConstraints();
		gbc_txtTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelefono.gridx = 2;
		gbc_txtTelefono.gridy = 4;
		contentPane.add(txtTelefono, gbc_txtTelefono);

		JLabel lblAnoInicio = new JLabel("Año de Inicio:");
		GridBagConstraints gbc_lblAnoInicio = new GridBagConstraints();
		gbc_lblAnoInicio.anchor = GridBagConstraints.EAST;
		gbc_lblAnoInicio.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnoInicio.gridx = 6;
		gbc_lblAnoInicio.gridy = 4;
		contentPane.add(lblAnoInicio, gbc_lblAnoInicio);

		txtAnoInicio = new JTextField();
		txtAnoInicio.setColumns(10);
		GridBagConstraints gbc_txtAnoInicio = new GridBagConstraints();
		gbc_txtAnoInicio.insets = new Insets(0, 0, 5, 5);
		gbc_txtAnoInicio.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAnoInicio.gridx = 7;
		gbc_txtAnoInicio.gridy = 4;
		contentPane.add(txtAnoInicio, gbc_txtAnoInicio);

		JLabel lblDni = new JLabel("DNI:");
		GridBagConstraints gbc_lblDni = new GridBagConstraints();
		gbc_lblDni.anchor = GridBagConstraints.EAST;
		gbc_lblDni.insets = new Insets(0, 0, 5, 5);
		gbc_lblDni.gridx = 1;
		gbc_lblDni.gridy = 5;
		contentPane.add(lblDni, gbc_lblDni);

		txtDni = new JTextField();
		txtDni.setColumns(10);
		GridBagConstraints gbc_txtDni = new GridBagConstraints();
		gbc_txtDni.insets = new Insets(0, 0, 5, 5);
		gbc_txtDni.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDni.gridx = 2;
		gbc_txtDni.gridy = 5;
		contentPane.add(txtDni, gbc_txtDni);

		JLabel lblAnoFin = new JLabel("Año de Fin:");
		GridBagConstraints gbc_lblAnoFin = new GridBagConstraints();
		gbc_lblAnoFin.anchor = GridBagConstraints.EAST;
		gbc_lblAnoFin.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnoFin.gridx = 6;
		gbc_lblAnoFin.gridy = 5;
		contentPane.add(lblAnoFin, gbc_lblAnoFin);

		txtAnoFin = new JTextField();
		txtAnoFin.setColumns(10);
		GridBagConstraints gbc_txtAnoFin = new GridBagConstraints();
		gbc_txtAnoFin.insets = new Insets(0, 0, 5, 5);
		gbc_txtAnoFin.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAnoFin.gridx = 7;
		gbc_txtAnoFin.gridy = 5;
		contentPane.add(txtAnoFin, gbc_txtAnoFin);

		JLabel lblDireccion = new JLabel("Dirección:");
		GridBagConstraints gbc_lblDireccion = new GridBagConstraints();
		gbc_lblDireccion.anchor = GridBagConstraints.EAST;
		gbc_lblDireccion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDireccion.gridx = 1;
		gbc_lblDireccion.gridy = 6;
		contentPane.add(lblDireccion, gbc_lblDireccion);

		txtDireccion = new JTextField();
		txtDireccion.setColumns(10);
		GridBagConstraints gbc_txtDireccion = new GridBagConstraints();
		gbc_txtDireccion.insets = new Insets(0, 0, 5, 5);
		gbc_txtDireccion.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDireccion.gridx = 2;
		gbc_txtDireccion.gridy = 6;
		contentPane.add(txtDireccion, gbc_txtDireccion);

		JLabel lblFechaNaci = new JLabel("Fecha de Nacimiento:");
		GridBagConstraints gbc_lblFechaNaci = new GridBagConstraints();
		gbc_lblFechaNaci.anchor = GridBagConstraints.EAST;
		gbc_lblFechaNaci.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaNaci.gridx = 6;
		gbc_lblFechaNaci.gridy = 6;
		contentPane.add(lblFechaNaci, gbc_lblFechaNaci);

		txtFechaNaci = new JTextField();
		txtFechaNaci.setColumns(10);
		GridBagConstraints gbc_txtFechaNaci = new GridBagConstraints();
		gbc_txtFechaNaci.insets = new Insets(0, 0, 5, 5);
		gbc_txtFechaNaci.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFechaNaci.gridx = 7;
		gbc_txtFechaNaci.gridy = 6;
		contentPane.add(txtFechaNaci, gbc_txtFechaNaci);

		JLabel lblEmail = new JLabel("Email:");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 7;
		contentPane.add(lblEmail, gbc_lblEmail);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.gridx = 2;
		gbc_txtEmail.gridy = 7;
		contentPane.add(txtEmail, gbc_txtEmail);

		JLabel lblErasmus = new JLabel("Erasmus:");
		GridBagConstraints gbc_lblErasmus = new GridBagConstraints();
		gbc_lblErasmus.anchor = GridBagConstraints.EAST;
		gbc_lblErasmus.insets = new Insets(0, 0, 5, 5);
		gbc_lblErasmus.gridx = 6;
		gbc_lblErasmus.gridy = 7;
		contentPane.add(lblErasmus, gbc_lblErasmus);

		choiceErasmus = new JComboBox<>();
		choiceErasmus.addItem("Sí");
		choiceErasmus.addItem("No");
		GridBagConstraints gbc_choiceErasmus = new GridBagConstraints();
		gbc_choiceErasmus.insets = new Insets(0, 0, 5, 5);
		gbc_choiceErasmus.fill = GridBagConstraints.HORIZONTAL;
		gbc_choiceErasmus.gridx = 7;
		gbc_choiceErasmus.gridy = 7;
		contentPane.add(choiceErasmus, gbc_choiceErasmus);

		btnAlta = new JButton("Dar Alta");
		btnAlta.addActionListener(this);
		GridBagConstraints gbc_btnAlta = new GridBagConstraints();
		gbc_btnAlta.insets = new Insets(0, 0, 0, 5);
		gbc_btnAlta.gridx = 2;
		gbc_btnAlta.gridy = 15;
		contentPane.add(btnAlta, gbc_btnAlta);

		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(this);
		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.insets = new Insets(0, 0, 0, 5);
		gbc_btnVolver.gridx = 7;
		gbc_btnVolver.gridy = 15;
		contentPane.add(btnVolver, gbc_btnVolver);
	}
	private String convertirFechaEuropeaAAmericana(String fechaEuropea) {
		SimpleDateFormat formatoEuropeo = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatoAmericano = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date fecha = formatoEuropeo.parse(fechaEuropea);
			return formatoAmericano.format(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
			return null; // Manejar adecuadamente el error
		}
	}

	private boolean validarFecha(String fecha) {
	    try {
	        // Intenta parsear la fecha con el formato dd/MM/yyyy
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        sdf.setLenient(false);
	        sdf.parse(fecha);
	        return true; // Si se parsea correctamente, la fecha es válida
	    } catch (ParseException e) {
	        return false; // Si hay una excepción, la fecha no es válida
	    }
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnAlta))
		{
			if(txtNombre.getText().length()==0 || txtSegundoApellidos.getText().length()==0 || txtTelefono.getText().length()==0 || txtDni.getText().length()==0 || txtDireccion.getText().length()==0 || txtEmail.getText().length()==0 || txtEmailCentro.getText().length()==0 || txtCurso.getText().length()==0 || txtAnoInicio.getText().length()==0 || txtAnoFin.getText().length()==0 || txtFechaNaci.getText().length()==0)
			{
				JOptionPane.showMessageDialog(this, "Campos Vacios.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if(txtTelefono.getText().length()!=9)
			{
				JOptionPane.showMessageDialog(this, "Teléfono no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if (!validarFecha(txtFechaNaci.getText())) {
	            JOptionPane.showMessageDialog(this, "Fecha de nacimiento no válida.", "Error", JOptionPane.ERROR_MESSAGE);
	        } 
			else if (!esNumero(txtTelefono.getText())) {
                JOptionPane.showMessageDialog(this, "Teléfono no válido (solo números).", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!esNumero(txtCurso.getText())) {
                JOptionPane.showMessageDialog(this, "Curso no válido (solo números).", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!esNumero(txtAnoInicio.getText())) {
                JOptionPane.showMessageDialog(this, "Año Inicio no válido (solo números).", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!esNumero(txtAnoFin.getText())) {
                JOptionPane.showMessageDialog(this, "Año Fin no válido (solo números).", "Error", JOptionPane.ERROR_MESSAGE);
            } 
			else
			{
				String fechaNaci = txtFechaNaci.getText();
				// Dar de alta
				String fechaNaciAmericana = convertirFechaEuropeaAAmericana(fechaNaci);
				String cicloSeleccionado = choiceCiclo.getSelectedItem().toString();
				String erasmusSeleccionado = choiceErasmus.getSelectedItem().toString();
				int erasmus = erasmusSeleccionado.equals("Sí") ? 1 : 0;

				String sentencia = "INSERT INTO Alumnas VALUES (null, '" + txtNombre.getText() + "', '" + txtPrimerApellido.getText() + "', '" + txtSegundoApellidos.getText() + "', '" + txtDni.getText() + "', '" + txtDireccion.getText() + "', '" + txtTelefono.getText() + "', '" + txtEmail.getText() + "', '" + txtEmailCentro.getText() + "','" + cicloSeleccionado + "', '" + txtCurso.getText() + "', '" + txtAnoInicio.getText() + "', '" + txtAnoFin.getText() + "', '" + fechaNaciAmericana +  "', '" + erasmus + "', '"+ 0 +  "', '"+ 0 + "');";
				int respuesta = conexion.altaAlumna(sentencia);
                JOptionPane.showMessageDialog(this, "Alta Realizada.", "Alta", JOptionPane.INFORMATION_MESSAGE);
				System.out.println(sentencia);
				if(respuesta!=0)
				{
					// Mostrar Mensaje Error
					System.out.println("Error en Alta");
				}
			}

		}
		if(e.getSource().equals(btnVolver))
		{
			Alumnas a = new Alumnas();
			a.setVisible(true);
			dispose();  // Cierra la ventana actual
		}

	} private boolean esNumero(String texto) {
        // Verifica si el texto contiene solo dígitos
        return texto.matches("\\d+");
    }

	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) {}
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
}