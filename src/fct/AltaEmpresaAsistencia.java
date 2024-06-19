package fct;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AltaEmpresaAsistencia extends JFrame implements ActionListener {
	private JTextField txtNombre;
	private JTextField txtCIF;
	private JTextField txtDireccionPostal;
	private JTextField txtLocalidad;
	private JTextField txtRepresentante;
	private JTextField txtDNI;
	private JTextField txtEmail;
	JButton btnAlta = new JButton("Dar Alta");
    JComboBox<String> choiceErasmus = new JComboBox<>();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTelefono;
	Conexion conexion = new Conexion();
	JButton btnVolver = new JButton("Volver");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaEmpresaAsistencia frame = new AltaEmpresaAsistencia();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AltaEmpresaAsistencia() {
        setTitle("Alta de Empresa de Asistencia"); // Título de la ventana

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 588, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		
		JLabel lblCIF = new JLabel("CIF:");
		GridBagConstraints gbc_lblCIF = new GridBagConstraints();
		gbc_lblCIF.anchor = GridBagConstraints.EAST;
		gbc_lblCIF.insets = new Insets(0, 0, 5, 5);
		gbc_lblCIF.gridx = 1;
		gbc_lblCIF.gridy = 2;
		contentPane.add(lblCIF, gbc_lblCIF);
		
		txtCIF = new JTextField();
		txtCIF.setColumns(10);
		GridBagConstraints gbc_txtCIF = new GridBagConstraints();
		gbc_txtCIF.insets = new Insets(0, 0, 5, 5);
		gbc_txtCIF.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCIF.gridx = 2;
		gbc_txtCIF.gridy = 2;
		contentPane.add(txtCIF, gbc_txtCIF);
		
		JLabel lblDireccionPostal = new JLabel("Dirección Postal:");
		GridBagConstraints gbc_lblDireccionPostal = new GridBagConstraints();
		gbc_lblDireccionPostal.anchor = GridBagConstraints.EAST;
		gbc_lblDireccionPostal.insets = new Insets(0, 0, 5, 5);
		gbc_lblDireccionPostal.gridx = 1;
		gbc_lblDireccionPostal.gridy = 3;
		contentPane.add(lblDireccionPostal, gbc_lblDireccionPostal);
		
		txtDireccionPostal = new JTextField();
		txtDireccionPostal.setColumns(10);
		GridBagConstraints gbc_txtDireccionPostal = new GridBagConstraints();
		gbc_txtDireccionPostal.insets = new Insets(0, 0, 5, 5);
		gbc_txtDireccionPostal.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDireccionPostal.gridx = 2;
		gbc_txtDireccionPostal.gridy = 3;
		contentPane.add(txtDireccionPostal, gbc_txtDireccionPostal);
		
		
		
		JLabel lblLocalidad = new JLabel("Localidad:");
		GridBagConstraints gbc_lblLocalidad = new GridBagConstraints();
		gbc_lblLocalidad.anchor = GridBagConstraints.EAST;
		gbc_lblLocalidad.insets = new Insets(0, 0, 5, 5);
		gbc_lblLocalidad.gridx = 1;
		gbc_lblLocalidad.gridy = 4;
		contentPane.add(lblLocalidad, gbc_lblLocalidad);
		
		txtLocalidad = new JTextField();
		txtLocalidad.setColumns(10);
		GridBagConstraints gbc_txtLocalidad = new GridBagConstraints();
		gbc_txtLocalidad.insets = new Insets(0, 0, 5, 5);
		gbc_txtLocalidad.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLocalidad.gridx = 2;
		gbc_txtLocalidad.gridy = 4;
		contentPane.add(txtLocalidad, gbc_txtLocalidad);
		
		
		
		JLabel lblRepresentante = new JLabel("Representante Legal:");
		GridBagConstraints gbc_lblRepresentante = new GridBagConstraints();
		gbc_lblRepresentante.anchor = GridBagConstraints.EAST;
		gbc_lblRepresentante.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepresentante.gridx = 1;
		gbc_lblRepresentante.gridy = 5;
		contentPane.add(lblRepresentante, gbc_lblRepresentante);
		
		txtRepresentante = new JTextField();
		txtRepresentante.setColumns(10);
		GridBagConstraints gbc_txtRepresentante = new GridBagConstraints();
		gbc_txtRepresentante.insets = new Insets(0, 0, 5, 5);
		gbc_txtRepresentante.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRepresentante.gridx = 2;
		gbc_txtRepresentante.gridy = 5;
		contentPane.add(txtRepresentante, gbc_txtRepresentante);
		
		
		
		JLabel lbldni = new JLabel("DNI Representante:");
		GridBagConstraints gbc_lbldni = new GridBagConstraints();
		gbc_lbldni.anchor = GridBagConstraints.EAST;
		gbc_lbldni.insets = new Insets(0, 0, 5, 5);
		gbc_lbldni.gridx = 1;
		gbc_lbldni.gridy = 6;
		contentPane.add(lbldni, gbc_lbldni);
		
		txtDNI = new JTextField();
		txtDNI.setColumns(10);
		GridBagConstraints gbc_txtDNI = new GridBagConstraints();
		gbc_txtDNI.anchor = GridBagConstraints.NORTH;
		gbc_txtDNI.insets = new Insets(0, 0, 5, 5);
		gbc_txtDNI.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDNI.gridx = 2;
		gbc_txtDNI.gridy = 6;
		contentPane.add(txtDNI, gbc_txtDNI);
		

		JLabel lblEmail = new JLabel("Email Empresa:");
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
		
		JLabel lblTelefono = new JLabel("Teléfono:");
		GridBagConstraints gbc_lblTelefono = new GridBagConstraints();
		gbc_lblTelefono.anchor = GridBagConstraints.EAST;
		gbc_lblTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefono.gridx = 1;
		gbc_lblTelefono.gridy = 8;
		contentPane.add(lblTelefono, gbc_lblTelefono);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		GridBagConstraints gbc_txtTelefono = new GridBagConstraints();
		gbc_txtTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelefono.gridx = 2;
		gbc_txtTelefono.gridy = 8;
		contentPane.add(txtTelefono, gbc_txtTelefono);
		
		
		
		btnAlta.setPreferredSize(new Dimension(120, 60)); // Establece el ancho y alto preferidos
		GridBagConstraints gbc_btnAlta = new GridBagConstraints();
		gbc_btnAlta.gridwidth = 2;
		gbc_btnAlta.gridheight = 3;
		gbc_btnAlta.insets = new Insets(0, 0, 5, 5);
		gbc_btnAlta.gridx = 2;
		gbc_btnAlta.gridy = 10;
		contentPane.add(btnAlta, gbc_btnAlta);
		btnAlta.addActionListener(this);
		
		btnVolver.setPreferredSize(new Dimension(120, 60)); // Establece el ancho y alto preferidos
		btnVolver.addActionListener(this);
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmpresaAsistencia a = new EmpresaAsistencia();
				a.setVisible(true);
				dispose(); 
			}
		});
		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.insets = new Insets(0, 0, 5, 5);
		gbc_btnVolver.gridx = 2;
		gbc_btnVolver.gridy = 13;
		contentPane.add(btnVolver, gbc_btnVolver);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnAlta))
		{
			if(txtNombre.getText().length()==0 || txtCIF.getText().length()==0 || txtTelefono.getText().length()==0 || txtDireccionPostal.getText().length()==0 || txtLocalidad.getText().length()==0 || txtEmail.getText().length()==0 || txtRepresentante.getText().length()==0 || txtDNI.getText().length()==0 || txtTelefono.getText().length()==0)
			{
                JOptionPane.showMessageDialog(this, "Campos vacios.", "Alta", JOptionPane.ERROR_MESSAGE);
			}
			else if(txtTelefono.getText().length()!=9)
			{
                JOptionPane.showMessageDialog(this, "Teléfono no válido.", "Alta", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				// Dar de alta
				String sentencia = "INSERT INTO Empresas VALUES (null, '" + txtNombre.getText() + "', '" + txtCIF.getText() + "', '" + txtDireccionPostal.getText() + "', '" + txtLocalidad.getText() + "', '" + txtRepresentante.getText() + "', '" + txtDNI.getText() + "', '" + txtEmail.getText() + "', '" + txtTelefono.getText() + "', '"+ 5 + "');";
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
	}
	

}
