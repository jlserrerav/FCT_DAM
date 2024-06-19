package fct;

import java.awt.Dimension;
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

public class AltaPadres extends JFrame implements WindowListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombrePadre;
	private JTextField txtPrimerApellidoPadre;
	private JTextField txtSegundoApellidoPadre;
	private JTextField txtTelefonoPadre;
	private JTextField txtNombreMadre;
	private JTextField txtPrimerApellidoMadre;
	private JTextField txtSegundoApellidoMadre;
	private JTextField txtTelefonoMadre;
	JButton btnAlta = new JButton("Dar Alta");
	JButton btnVolver = new JButton("Volver");
	JComboBox<String> choiceAlumna = new JComboBox<>();

	Conexion conexion = new Conexion();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AltaPadres frame = new AltaPadres();
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
	public AltaPadres() {
        setTitle("Alta de Padres"); // Título de la ventana

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 827, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		JLabel lblDatosPadres = new JLabel("Datos de los Padres/Tutores Legales");
		GridBagConstraints gbc_lblDatosPadres = new GridBagConstraints();
		gbc_lblDatosPadres.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatosPadres.gridx = 4;
		gbc_lblDatosPadres.gridy = 0;
		contentPane.add(lblDatosPadres, gbc_lblDatosPadres);

		JLabel lblPadre = new JLabel("Padre:");
		GridBagConstraints gbc_lblPadre = new GridBagConstraints();
		gbc_lblPadre.insets = new Insets(0, 0, 5, 5);
		gbc_lblPadre.gridx = 4;
		gbc_lblPadre.gridy = 1;
		contentPane.add(lblPadre, gbc_lblPadre);

		JLabel lblNombrePadre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombrePadre = new GridBagConstraints();
		gbc_lblNombrePadre.anchor = GridBagConstraints.EAST;
		gbc_lblNombrePadre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombrePadre.gridx = 4;
		gbc_lblNombrePadre.gridy = 2;
		contentPane.add(lblNombrePadre, gbc_lblNombrePadre);

		txtNombrePadre = new JTextField();
		txtNombrePadre.setColumns(10);
		GridBagConstraints gbc_txtNombrePadre = new GridBagConstraints();
		gbc_txtNombrePadre.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombrePadre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombrePadre.gridx = 5;
		gbc_txtNombrePadre.gridy = 2;
		contentPane.add(txtNombrePadre, gbc_txtNombrePadre);

		JLabel lblPrimerApellidoPadre = new JLabel("Primer Apellido:");
		GridBagConstraints gbc_lblPrimerApellidoPadre = new GridBagConstraints();
		gbc_lblPrimerApellidoPadre.anchor = GridBagConstraints.EAST;
		gbc_lblPrimerApellidoPadre.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrimerApellidoPadre.gridx = 4;
		gbc_lblPrimerApellidoPadre.gridy = 3;
		contentPane.add(lblPrimerApellidoPadre, gbc_lblPrimerApellidoPadre);

		txtPrimerApellidoPadre = new JTextField();
		txtPrimerApellidoPadre.setColumns(10);
		GridBagConstraints gbc_txtPrimerApellidoPadre = new GridBagConstraints();
		gbc_txtPrimerApellidoPadre.insets = new Insets(0, 0, 5, 5);
		gbc_txtPrimerApellidoPadre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrimerApellidoPadre.gridx = 5;
		gbc_txtPrimerApellidoPadre.gridy = 3;
		contentPane.add(txtPrimerApellidoPadre, gbc_txtPrimerApellidoPadre);

		JLabel lblSegundoApellidoPadre = new JLabel("Segundo Apellido:");
		GridBagConstraints gbc_lblSegundoApellidoPadre = new GridBagConstraints();
		gbc_lblSegundoApellidoPadre.anchor = GridBagConstraints.EAST;
		gbc_lblSegundoApellidoPadre.insets = new Insets(0, 0, 5, 5);
		gbc_lblSegundoApellidoPadre.gridx = 4;
		gbc_lblSegundoApellidoPadre.gridy = 4;
		contentPane.add(lblSegundoApellidoPadre, gbc_lblSegundoApellidoPadre);

		txtSegundoApellidoPadre = new JTextField();
		txtSegundoApellidoPadre.setColumns(10);
		GridBagConstraints gbc_txtSegundoApellidoPadre = new GridBagConstraints();
		gbc_txtSegundoApellidoPadre.insets = new Insets(0, 0, 5, 5);
		gbc_txtSegundoApellidoPadre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSegundoApellidoPadre.gridx = 5;
		gbc_txtSegundoApellidoPadre.gridy = 4;
		contentPane.add(txtSegundoApellidoPadre, gbc_txtSegundoApellidoPadre);

		JLabel lblTelefonoPadre = new JLabel("Teléfono:");
		GridBagConstraints gbc_lblTelefonoPadre = new GridBagConstraints();
		gbc_lblTelefonoPadre.anchor = GridBagConstraints.EAST;
		gbc_lblTelefonoPadre.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefonoPadre.gridx = 4;
		gbc_lblTelefonoPadre.gridy = 5;
		contentPane.add(lblTelefonoPadre, gbc_lblTelefonoPadre);

		txtTelefonoPadre = new JTextField();
		txtTelefonoPadre.setColumns(10);
		GridBagConstraints gbc_txtTelefonoPadre = new GridBagConstraints();
		gbc_txtTelefonoPadre.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelefonoPadre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelefonoPadre.gridx = 5;
		gbc_txtTelefonoPadre.gridy = 5;
		contentPane.add(txtTelefonoPadre, gbc_txtTelefonoPadre);

		JLabel lblMadre = new JLabel("Madre:");
		GridBagConstraints gbc_lblMadre = new GridBagConstraints();
		gbc_lblMadre.insets = new Insets(0, 0, 5, 5);
		gbc_lblMadre.gridx = 4;
		gbc_lblMadre.gridy = 6;
		contentPane.add(lblMadre, gbc_lblMadre);

		JLabel lblNombreMadre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombreMadre = new GridBagConstraints();
		gbc_lblNombreMadre.anchor = GridBagConstraints.EAST;
		gbc_lblNombreMadre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreMadre.gridx = 4;
		gbc_lblNombreMadre.gridy = 7;
		contentPane.add(lblNombreMadre, gbc_lblNombreMadre);

		txtNombreMadre = new JTextField();
		txtNombreMadre.setColumns(10);
		GridBagConstraints gbc_txtNombreMadre = new GridBagConstraints();
		gbc_txtNombreMadre.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombreMadre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombreMadre.gridx = 5;
		gbc_txtNombreMadre.gridy = 7;
		contentPane.add(txtNombreMadre, gbc_txtNombreMadre);

		JLabel lblPrimerApellidoMadre = new JLabel("Primer Apellido:");
		GridBagConstraints gbc_lblPrimerApellidoMadre = new GridBagConstraints();
		gbc_lblPrimerApellidoMadre.anchor = GridBagConstraints.EAST;
		gbc_lblPrimerApellidoMadre.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrimerApellidoMadre.gridx = 4;
		gbc_lblPrimerApellidoMadre.gridy = 8;
		contentPane.add(lblPrimerApellidoMadre, gbc_lblPrimerApellidoMadre);

		txtPrimerApellidoMadre = new JTextField();
		txtPrimerApellidoMadre.setColumns(10);
		GridBagConstraints gbc_txtPrimerApellidoMadre = new GridBagConstraints();
		gbc_txtPrimerApellidoMadre.insets = new Insets(0, 0, 5, 5);
		gbc_txtPrimerApellidoMadre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrimerApellidoMadre.gridx = 5;
		gbc_txtPrimerApellidoMadre.gridy = 8;
		contentPane.add(txtPrimerApellidoMadre, gbc_txtPrimerApellidoMadre);

		JLabel lblSegundoApellidoMadre = new JLabel("Segundo Apellido:");
		GridBagConstraints gbc_lblSegundoApellidoMadre = new GridBagConstraints();
		gbc_lblSegundoApellidoMadre.anchor = GridBagConstraints.EAST;
		gbc_lblSegundoApellidoMadre.insets = new Insets(0, 0, 5, 5);
		gbc_lblSegundoApellidoMadre.gridx = 4;
		gbc_lblSegundoApellidoMadre.gridy = 9;
		contentPane.add(lblSegundoApellidoMadre, gbc_lblSegundoApellidoMadre);

		txtSegundoApellidoMadre = new JTextField();
		txtSegundoApellidoMadre.setColumns(10);
		GridBagConstraints gbc_txtSegundoApellidoMadre = new GridBagConstraints();
		gbc_txtSegundoApellidoMadre.insets = new Insets(0, 0, 5, 5);
		gbc_txtSegundoApellidoMadre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSegundoApellidoMadre.gridx = 5;
		gbc_txtSegundoApellidoMadre.gridy = 9;
		contentPane.add(txtSegundoApellidoMadre, gbc_txtSegundoApellidoMadre);

		JLabel lblTelefonoMadre = new JLabel("Teléfono:");
		GridBagConstraints gbc_lblTelefonoMadre = new GridBagConstraints();
		gbc_lblTelefonoMadre.anchor = GridBagConstraints.EAST;
		gbc_lblTelefonoMadre.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefonoMadre.gridx = 4;
		gbc_lblTelefonoMadre.gridy = 10;
		contentPane.add(lblTelefonoMadre, gbc_lblTelefonoMadre);

		txtTelefonoMadre = new JTextField();
		txtTelefonoMadre.setColumns(10);
		GridBagConstraints gbc_txtTelefonoMadre = new GridBagConstraints();
		gbc_txtTelefonoMadre.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelefonoMadre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelefonoMadre.gridx = 5;
		gbc_txtTelefonoMadre.gridy = 10;
		contentPane.add(txtTelefonoMadre, gbc_txtTelefonoMadre);

		JLabel lblAlumna = new JLabel("Alumna:");
		GridBagConstraints gbc_lblAlumna = new GridBagConstraints();
		gbc_lblAlumna.anchor = GridBagConstraints.EAST;
		gbc_lblAlumna.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlumna.gridx = 4;
		gbc_lblAlumna.gridy = 11;
		contentPane.add(lblAlumna, gbc_lblAlumna);

		conexion.rellenarChoiceAlumna(choiceAlumna); // Método para rellenar las empresas
		GridBagConstraints gbc_choiceAlumna = new GridBagConstraints();
		gbc_choiceAlumna.insets = new Insets(0, 0, 5, 5);
		gbc_choiceAlumna.fill = GridBagConstraints.HORIZONTAL;
		gbc_choiceAlumna.gridx = 5;
		gbc_choiceAlumna.gridy = 11;
		contentPane.add(choiceAlumna, gbc_choiceAlumna);
		
				btnAlta.setPreferredSize(new Dimension(120, 60)); // Establece el ancho y alto preferidos
				GridBagConstraints gbc_btnAlta = new GridBagConstraints();
				gbc_btnAlta.gridwidth = 2;
				gbc_btnAlta.gridheight = 3;
				gbc_btnAlta.insets = new Insets(0, 0, 0, 5);
				gbc_btnAlta.gridx = 5;
				gbc_btnAlta.gridy = 13;
				contentPane.add(btnAlta, gbc_btnAlta);
				btnAlta.addActionListener(this);
		
		btnVolver.setPreferredSize(new Dimension(100, 60)); // Establece el ancho y alto preferidos
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 15;
		btnVolver.addActionListener(this);
		contentPane.add(btnVolver, gbc_btnNewButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnAlta)) {
			if(txtNombrePadre.getText().length()==0 || txtPrimerApellidoPadre.getText().length()==0 || txtSegundoApellidoPadre.getText().length()==0 || txtTelefonoPadre.getText().length()==0 || txtNombreMadre.getText().length()==0 || txtPrimerApellidoMadre.getText().length()==0 || txtSegundoApellidoMadre.getText().length()==0 || txtTelefonoMadre.getText().length()==0)
			{
                JOptionPane.showMessageDialog(this, "Campos vacios.", "Alta", JOptionPane.ERROR_MESSAGE);
			}
			else if (txtTelefonoPadre.getText().length() != 9 || txtTelefonoMadre.getText().length() != 9) {
                JOptionPane.showMessageDialog(this, "Teléfono no válido.", "Alta", JOptionPane.ERROR_MESSAGE);
			} else {
				String idAlumnaSeleccionada  = (String) choiceAlumna.getSelectedItem();
				int idAlumna = Integer.parseInt(idAlumnaSeleccionada.split("-")[0]);
				String sentencia = "INSERT INTO Padres (nombrePadre, primerApellidoPadre, segundoApellidoPadre, telefonoPadre, nombreMadre, primerApellidoMadre, segundoApellidoMadre, telefonoMadre, idAlumnaFK) " +
					"VALUES ('" + txtNombrePadre.getText() + "', '" + txtPrimerApellidoPadre.getText() + "', '" + txtSegundoApellidoPadre.getText() + "', '" + txtTelefonoPadre.getText() + 
					"', '" + txtNombreMadre.getText() + "', '" + txtPrimerApellidoMadre.getText() + "', '" + txtSegundoApellidoMadre.getText() + "', '" + txtTelefonoMadre.getText() + 
					"', '" + idAlumna + "');";

				int respuesta = conexion.altaAlumna(sentencia);
				System.out.println(sentencia);
                JOptionPane.showMessageDialog(this, "Alta Realizada.", "Alta", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(this, "Alta Realizada.", "Alta", JOptionPane.INFORMATION_MESSAGE);
				if(respuesta != 0) {
					System.out.println("Error en Alta");
				}
			}
		}
		if(e.getSource().equals(btnVolver))
		{
			Padres a = new Padres();
			a.setVisible(true);
			dispose();  // Cierra la ventana actual
		}
	}
	
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
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
}
