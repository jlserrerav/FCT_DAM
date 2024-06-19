package fct;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AltaSedeAsis extends JFrame implements ActionListener {
    private JTextField txtNombre;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextArea txtObservaciones;

    private JButton btnAlta = new JButton("Dar Alta");
    private JButton btnVolver = new JButton("Volver");
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Conexion conexion = new Conexion();
    private JComboBox<String> comboBox = new JComboBox<>();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AltaSedeAsis frame = new AltaSedeAsis();
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
    public AltaSedeAsis() {
        setTitle("Alta de Sede de Asistencia"); // Título de la ventana

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);

        setContentPane(contentPane);
        contentPane.setLayout(null);

        int labelWidth = 100;
        int labelHeight = 13;
        int fieldX = 138;
        int fieldWidth = 401;
        int fieldHeight = 19;

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(23, 35, labelWidth, labelHeight);
        contentPane.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(fieldX, 35, fieldWidth, fieldHeight);
        contentPane.add(txtNombre);
        txtNombre.setColumns(10);

        JLabel lblDireccion = new JLabel("Direccion:");
        lblDireccion.setBounds(23, 70, labelWidth, labelHeight);
        contentPane.add(lblDireccion);

        txtDireccion = new JTextField();
        txtDireccion.setBounds(fieldX, 70, fieldWidth, fieldHeight);
        txtDireccion.setColumns(10);
        contentPane.add(txtDireccion);

        JLabel lblTelefono = new JLabel("Telefono:");
        lblTelefono.setBounds(23, 105, labelWidth, labelHeight);
        contentPane.add(lblTelefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(fieldX, 105, fieldWidth, fieldHeight);
        txtTelefono.setColumns(10);
        contentPane.add(txtTelefono);

        JLabel lblObservaciones = new JLabel("Observaciones:");
        lblObservaciones.setBounds(23, 140, labelWidth, labelHeight);
        contentPane.add(lblObservaciones);

        txtObservaciones = new JTextArea();
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtObservaciones);
        scrollPane.setBounds(fieldX, 140, fieldWidth, 60);
        contentPane.add(scrollPane);

        comboBox = new JComboBox<>();
        comboBox.setBounds(fieldX, 220, fieldWidth, 32);
        conexion = new Conexion();
        conexion.rellenarChoiceEmpresaAsis(comboBox);
        contentPane.add(comboBox);

        JLabel lblEmpresa = new JLabel("Empresa:");
        lblEmpresa.setBounds(23, 220, labelWidth, labelHeight);
        contentPane.add(lblEmpresa);

        btnAlta = new JButton("Alta");
        btnAlta.setBounds(400, 280, 100, 50);
        btnAlta.addActionListener(this); // Registrar el ActionListener
        contentPane.add(btnAlta);
        
     // Agregar el botón de "Volver"
        btnVolver = new JButton("Volver");
        btnVolver.setBounds(50, 280, 100, 50);
        btnVolver.addActionListener(this); // Registrar el ActionListener
        contentPane.add(btnVolver);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnAlta)) {
            boolean hasEmptyFields = false;

            if (txtNombre.getText().isEmpty()) {
                System.out.println("Campo 'Nombre' vacío");
                hasEmptyFields = true;
            }
            if (txtDireccion.getText().isEmpty()) {
                System.out.println("Campo 'Direccion' vacío");
                hasEmptyFields = true;
            }
            if (txtTelefono.getText().isEmpty()) {
                System.out.println("Campo 'Telefono' vacío");
                hasEmptyFields = true;
            }
            if (comboBox.getSelectedIndex() == -1) {
                System.out.println("Campo 'Empresa' vacío");
                hasEmptyFields = true;
            }

            if (hasEmptyFields) {
                JOptionPane.showMessageDialog(this, "Campos vacios.", "Alta", JOptionPane.ERROR_MESSAGE);
            } else if (txtTelefono.getText().length() != 9) {
                JOptionPane.showMessageDialog(this, "Teléfono no válido.", "Alta", JOptionPane.ERROR_MESSAGE);
            } else {
                // Dar de alta
                String selectedEmpresa = (String) comboBox.getSelectedItem();
                int idEmpresa = Integer.parseInt(selectedEmpresa.split("-")[0]);

                String sentencia = "INSERT INTO SedesEmpresa (nombreSede, direccion, telefono, observaciones, idEmpresaFK) VALUES ('"
                        + txtNombre.getText() + "', '" + txtDireccion.getText() + "', '" + txtTelefono.getText() + "', '"
                        + txtObservaciones.getText() + "', '" + idEmpresa + "')";
                int respuesta = conexion.altaSede(sentencia);
                JOptionPane.showMessageDialog(this, "Alta Realizada.", "Alta", JOptionPane.INFORMATION_MESSAGE);

                System.out.println(sentencia);
                if (respuesta != 0) {
                    // Mostrar Mensaje Error
                    System.out.println("Error en Alta");
                } else {
                    System.out.println("Alta realizada con éxito");
                }
            }
        }
        if(e.getSource().equals(btnVolver))
		{
			SedeAsistencia a = new SedeAsistencia();
			a.setVisible(true);
			dispose();  // Cierra la ventana actual
		}
    }
}
