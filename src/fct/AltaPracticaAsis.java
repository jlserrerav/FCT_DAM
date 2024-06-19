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

public class AltaPracticaAsis extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtHorario;
    JComboBox<String> comboAlumnas = new JComboBox<>();
    JComboBox<String> comboSedes = new JComboBox<>();
    JComboBox<String> comboContactos = new JComboBox<>();

    JButton btnAlta = new JButton("Dar Alta");
    JButton btnVolver = new JButton("Volver");

    private JPanel contentPane;
    Conexion conexion = new Conexion();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AltaPracticaAsis frame = new AltaPracticaAsis();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

public AltaPracticaAsis() {
		setTitle("Alta de Práctica de Enfermeria"); // Título de la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 654, 302);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		JLabel lblHorario = new JLabel("Horario:");
		GridBagConstraints gbc_lblHorario = new GridBagConstraints();
		gbc_lblHorario.anchor = GridBagConstraints.EAST;
		gbc_lblHorario.insets = new Insets(0, 0, 5, 5);
		gbc_lblHorario.gridx = 3;
		gbc_lblHorario.gridy = 1;
		contentPane.add(lblHorario, gbc_lblHorario);

		txtHorario = new JTextField();
		GridBagConstraints gbc_txtHorario = new GridBagConstraints();
		gbc_txtHorario.insets = new Insets(0, 0, 5, 5);
		gbc_txtHorario.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtHorario.gridx = 4;
		gbc_txtHorario.gridy = 1;
		contentPane.add(txtHorario, gbc_txtHorario);
		txtHorario.setColumns(10);

		JLabel lblAlumna = new JLabel("Alumna:");
		GridBagConstraints gbc_lblAlumna = new GridBagConstraints();
		gbc_lblAlumna.anchor = GridBagConstraints.EAST;
		gbc_lblAlumna.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlumna.gridx = 3;
		gbc_lblAlumna.gridy = 2;
		contentPane.add(lblAlumna, gbc_lblAlumna);

		// JComboBox para Alumnas
		conexion.rellenarChoiceAlumna(comboAlumnas); // Método para rellenar las alumnas
		GridBagConstraints gbc_comboAlumnas = new GridBagConstraints();
		gbc_comboAlumnas.insets = new Insets(0, 0, 5, 5);
		gbc_comboAlumnas.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboAlumnas.gridx = 4;
		gbc_comboAlumnas.gridy = 2;
		contentPane.add(comboAlumnas, gbc_comboAlumnas);

		JLabel lblSede = new JLabel("Sede:");
		GridBagConstraints gbc_lblSede = new GridBagConstraints();
		gbc_lblSede.anchor = GridBagConstraints.EAST;
		gbc_lblSede.insets = new Insets(0, 0, 5, 5);
		gbc_lblSede.gridx = 3;
		gbc_lblSede.gridy = 3;
		contentPane.add(lblSede, gbc_lblSede);

		// JComboBox para Sedes
		conexion.rellenarChoiceSedesAsis(comboSedes); // Método para rellenar las sedes
		GridBagConstraints gbc_comboSedes = new GridBagConstraints();
		gbc_comboSedes.insets = new Insets(0, 0, 5, 5);
		gbc_comboSedes.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboSedes.gridx = 4;
		gbc_comboSedes.gridy = 3;
		contentPane.add(comboSedes, gbc_comboSedes);

		JLabel lblContacto = new JLabel("Contacto:");
		GridBagConstraints gbc_lblContacto = new GridBagConstraints();
		gbc_lblContacto.anchor = GridBagConstraints.EAST;
		gbc_lblContacto.insets = new Insets(0, 0, 5, 5);
		gbc_lblContacto.gridx = 3;
		gbc_lblContacto.gridy = 4;
		contentPane.add(lblContacto, gbc_lblContacto);

		// JComboBox para Contactos
		conexion.rellenarChoiceContactosAsis(comboContactos); // Método para rellenar los contactos
		GridBagConstraints gbc_comboContactos = new GridBagConstraints();
		gbc_comboContactos.insets = new Insets(0, 0, 5, 5);
		gbc_comboContactos.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboContactos.gridx = 4;
		gbc_comboContactos.gridy = 4;
		contentPane.add(comboContactos, gbc_comboContactos);

		
		btnVolver.setPreferredSize(new Dimension(120, 60)); // Establece el ancho y alto preferidos
		btnVolver.addActionListener(this);
		GridBagConstraints gbc_btnVolver = new GridBagConstraints();
		gbc_btnVolver.gridheight = 2;
		gbc_btnVolver.insets = new Insets(0, 0, 0, 5);
		gbc_btnVolver.gridx = 3;
		gbc_btnVolver.gridy = 14;
		contentPane.add(btnVolver, gbc_btnVolver);

		btnAlta.setPreferredSize(new Dimension(120, 60)); // Establece el ancho y alto preferidos
		GridBagConstraints gbc_btnAlta = new GridBagConstraints();
		gbc_btnAlta.gridwidth = 2;
		gbc_btnAlta.gridheight = 3;
		gbc_btnAlta.insets = new Insets(0, 0, 0, 5);
		gbc_btnAlta.gridx = 6;
		gbc_btnAlta.gridy = 14;
		contentPane.add(btnAlta, gbc_btnAlta);
		btnAlta.addActionListener(this);
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnAlta)) {
            if (txtHorario.getText().isEmpty() || comboAlumnas.getSelectedIndex() == -1 || comboSedes.getSelectedIndex() == -1 || comboContactos.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Campos vacios.", "Alta", JOptionPane.ERROR_MESSAGE);
            } else {
            	 String horario = txtHorario.getText();
                 String alumnaSeleccionada = comboAlumnas.getSelectedItem().toString();
                 String sedeSeleccionada = comboSedes.getSelectedItem().toString();
                 String contactoSeleccionado = comboContactos.getSelectedItem().toString();

                 // Aquí extraes solo los IDs
                 int idAlumna = Integer.parseInt(alumnaSeleccionada.split("-")[0].trim());
                 int idSede = Integer.parseInt(sedeSeleccionada.split("-")[0].trim());
                 int idContacto = Integer.parseInt(contactoSeleccionado.split("-")[0].trim());
                String sentencia = "INSERT INTO Practicas (horario, idAlumna, idSede, idContacto) VALUES ('" +horario + "', " + idAlumna + ", " + idSede + ", " + idContacto + ")";
                int respuesta = conexion.altaAlumna(sentencia);
                JOptionPane.showMessageDialog(this, "Alta Realizada.", "Alta", JOptionPane.INFORMATION_MESSAGE);

                System.out.println(sentencia);
                if (respuesta != 0) {
                    System.out.println("Error en Alta");
                }
            }
        }
        if(e.getSource().equals(btnVolver))
		{
			PracticaAsis a = new PracticaAsis();
			a.setVisible(true);
			dispose();  // Cierra la ventana actual
		}
    }
}
