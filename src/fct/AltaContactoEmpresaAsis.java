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

public class AltaContactoEmpresaAsis extends JFrame implements ActionListener {
	private JTextField txtNombre;
	private JTextField txtPrimerApellido;
	private JTextField txtSegundoApellido;
	private JTextField txtDni;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	JComboBox<String> comboEmpresas = new JComboBox<>();
	String[] opcionesDual = {"Es Dual", "No es Dual"};
	JComboBox<String> comboDual = new JComboBox<>(opcionesDual);


	JButton btnVolver = new JButton("Volver");

	JButton btnAlta = new JButton("Dar Alta");
    JComboBox<String> choiceErasmus = new JComboBox<>();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	Conexion conexion = new Conexion();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaContactoEmpresaAsis frame = new AltaContactoEmpresaAsis();
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
	public AltaContactoEmpresaAsis() {
        setTitle("Alta de Contacto"); // Título de la ventana
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
		
		JLabel lblCIF = new JLabel("Primer Apellido:");
		GridBagConstraints gbc_lblCIF = new GridBagConstraints();
		gbc_lblCIF.anchor = GridBagConstraints.EAST;
		gbc_lblCIF.insets = new Insets(0, 0, 5, 5);
		gbc_lblCIF.gridx = 1;
		gbc_lblCIF.gridy = 2;
		contentPane.add(lblCIF, gbc_lblCIF);
		
		txtPrimerApellido = new JTextField();
		txtPrimerApellido.setColumns(10);
		GridBagConstraints gbc_txtCIF = new GridBagConstraints();
		gbc_txtCIF.insets = new Insets(0, 0, 5, 5);
		gbc_txtCIF.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCIF.gridx = 2;
		gbc_txtCIF.gridy = 2;
		contentPane.add(txtPrimerApellido, gbc_txtCIF);
		
		JLabel lblDireccionPostal = new JLabel("Segundo Apellido:");
		GridBagConstraints gbc_lblDireccionPostal = new GridBagConstraints();
		gbc_lblDireccionPostal.anchor = GridBagConstraints.EAST;
		gbc_lblDireccionPostal.insets = new Insets(0, 0, 5, 5);
		gbc_lblDireccionPostal.gridx = 1;
		gbc_lblDireccionPostal.gridy = 3;
		contentPane.add(lblDireccionPostal, gbc_lblDireccionPostal);
		
		txtSegundoApellido = new JTextField();
		txtSegundoApellido.setColumns(10);
		GridBagConstraints gbc_txtDireccionPostal = new GridBagConstraints();
		gbc_txtDireccionPostal.insets = new Insets(0, 0, 5, 5);
		gbc_txtDireccionPostal.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDireccionPostal.gridx = 2;
		gbc_txtDireccionPostal.gridy = 3;
		contentPane.add(txtSegundoApellido, gbc_txtDireccionPostal);
		
		
		
		JLabel lblLocalidad = new JLabel("DNI:");
		GridBagConstraints gbc_lblLocalidad = new GridBagConstraints();
		gbc_lblLocalidad.anchor = GridBagConstraints.EAST;
		gbc_lblLocalidad.insets = new Insets(0, 0, 5, 5);
		gbc_lblLocalidad.gridx = 1;
		gbc_lblLocalidad.gridy = 4;
		contentPane.add(lblLocalidad, gbc_lblLocalidad);
		
		txtDni = new JTextField();
		txtDni.setColumns(10);
		GridBagConstraints gbc_txtLocalidad = new GridBagConstraints();
		gbc_txtLocalidad.insets = new Insets(0, 0, 5, 5);
		gbc_txtLocalidad.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLocalidad.gridx = 2;
		gbc_txtLocalidad.gridy = 4;
		contentPane.add(txtDni, gbc_txtLocalidad);
		
		
		
		JLabel lblRepresentante = new JLabel("Telefono:");
		GridBagConstraints gbc_lblRepresentante = new GridBagConstraints();
		gbc_lblRepresentante.anchor = GridBagConstraints.EAST;
		gbc_lblRepresentante.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepresentante.gridx = 1;
		gbc_lblRepresentante.gridy = 5;
		contentPane.add(lblRepresentante, gbc_lblRepresentante);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		GridBagConstraints gbc_txtRepresentante = new GridBagConstraints();
		gbc_txtRepresentante.insets = new Insets(0, 0, 5, 5);
		gbc_txtRepresentante.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtRepresentante.gridx = 2;
		gbc_txtRepresentante.gridy = 5;
		contentPane.add(txtTelefono, gbc_txtRepresentante);
		
		
		
		JLabel lbldni = new JLabel("Email");
		GridBagConstraints gbc_lbldni = new GridBagConstraints();
		gbc_lbldni.anchor = GridBagConstraints.EAST;
		gbc_lbldni.insets = new Insets(0, 0, 5, 5);
		gbc_lbldni.gridx = 1;
		gbc_lbldni.gridy = 6;
		contentPane.add(lbldni, gbc_lbldni);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		GridBagConstraints gbc_txtDNI = new GridBagConstraints();
		gbc_txtDNI.anchor = GridBagConstraints.NORTH;
		gbc_txtDNI.insets = new Insets(0, 0, 5, 5);
		gbc_txtDNI.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDNI.gridx = 2;
		gbc_txtDNI.gridy = 6;
		contentPane.add(txtEmail, gbc_txtDNI);
		
		
		JLabel lblTelefono = new JLabel("Dual:");
		GridBagConstraints gbc_lblTelefono = new GridBagConstraints();
		gbc_lblTelefono.anchor = GridBagConstraints.EAST;
		gbc_lblTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefono.gridx = 1;
		gbc_lblTelefono.gridy = 8;
		contentPane.add(lblTelefono, gbc_lblTelefono);
		
		
		
		
		
		btnAlta.setPreferredSize(new Dimension(120, 60)); // Establece el ancho y alto preferidos
		GridBagConstraints gbc_btnAlta = new GridBagConstraints();
		gbc_btnAlta.gridwidth = 2;
		gbc_btnAlta.gridheight = 3;
		gbc_btnAlta.insets = new Insets(0, 0, 5, 5);
		gbc_btnAlta.gridx = 2;
		gbc_btnAlta.gridy = 10;
		contentPane.add(btnAlta, gbc_btnAlta);
		btnAlta.addActionListener(this);
		
		// JComboBox para Dual
		GridBagConstraints gbc_comboDual = new GridBagConstraints();
		gbc_comboDual.insets = new Insets(0, 0, 5, 5);
		gbc_comboDual.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboDual.gridx = 2;
		gbc_comboDual.gridy = 8;
		contentPane.add(comboDual, gbc_comboDual);

		// JComboBox para Empresas
		conexion.rellenarChoiceEmpresaAsis(comboEmpresas); // Método para rellenar las empresas
		GridBagConstraints gbc_comboEmpresas = new GridBagConstraints();
		gbc_comboEmpresas.insets = new Insets(0, 0, 5, 5);
		gbc_comboEmpresas.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboEmpresas.gridx = 2;
		gbc_comboEmpresas.gridy = 9;
		contentPane.add(comboEmpresas, gbc_comboEmpresas);
		
		btnVolver.setPreferredSize(new Dimension(120, 60)); // Establece el ancho y alto preferidos
		btnVolver.addActionListener(this);
		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.insets = new Insets(0, 0, 5, 5);
		gbc_btnVolver.gridx = 2;
		gbc_btnVolver.gridy = 13;
		contentPane.add(btnVolver, gbc_btnVolver);
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
	    if (e.getSource().equals(btnAlta)) {
	    	if (txtNombre.getText().isEmpty() || txtPrimerApellido.getText().isEmpty() || txtSegundoApellido.getText().isEmpty() || txtDni.getText().isEmpty() || txtTelefono.getText().isEmpty() || txtEmail.getText().isEmpty() || comboDual.getSelectedIndex() == -1 || comboEmpresas.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Campos vacios.", "Alta", JOptionPane.ERROR_MESSAGE);
	        } else if (txtTelefono.getText().length() != 9) {
                JOptionPane.showMessageDialog(this, "Teléfono no válido.", "Alta", JOptionPane.ERROR_MESSAGE);
	        } else {
	            // Dar de alta
	            int esDual = comboDual.getSelectedIndex() == 0 ? 0 : 1;
	            
	            // Obtener el idEmpresa desde el comboEmpresas
	            String selectedEmpresa = (String) comboEmpresas.getSelectedItem();
	            int idEmpresa = Integer.parseInt(selectedEmpresa.split("-")[0]);
	            
	            String sentencia = "INSERT INTO ContactosEmpresa (nombreContacto, primerApellidoContacto, segundoApellidoContacto, dniContacto, telefonoContacto, emailContacto, esDual, idEmpresaFK) VALUES ('" + txtNombre.getText() + "', '" + txtPrimerApellido.getText() + "', '" + txtSegundoApellido.getText() + "', '" + txtDni.getText() + "', '" + txtTelefono.getText() + "', '" + txtEmail.getText() + "', " + esDual + ", " + idEmpresa + ")";
	            int respuesta = conexion.altaContactoEmpresaAsis	(sentencia);
                JOptionPane.showMessageDialog(this, "Alta Realizada.", "Alta", JOptionPane.INFORMATION_MESSAGE);

	            System.out.println(sentencia);
	            if (respuesta != 0) {
	                // Mostrar Mensaje Error
	                System.out.println("Error en Alta");
	            }
	        }
	    }
	    if(e.getSource().equals(btnVolver))
	    {
	    	ContactoEmpresaAsis a = new ContactoEmpresaAsis();
			a.setVisible(true);
			dispose();  // Cierra la ventana actual
	    }
	}

	

}
