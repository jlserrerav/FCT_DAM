package fct;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AltaUsuario extends JFrame implements ActionListener {
    private JTextField txtNombre;
    private JTextField txtClave;
    JButton btnAlta = new JButton("Dar Alta");
    JButton btnVolver = new JButton("Volver");

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    Conexion conexion = new Conexion();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AltaUsuario frame = new AltaUsuario();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AltaUsuario() {
        setTitle("Alta de Usuario"); // Título de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 220, 160);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);

        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 1, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 1, 0};
        gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
        gbc_txtNombre.insets = new Insets(0, 0, 5, 0);
        gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNombre.gridx = 2;
        gbc_txtNombre.gridy = 1;
        contentPane.add(txtNombre, gbc_txtNombre);
        txtNombre.setColumns(10);

        JLabel lblClave = new JLabel("Clave:");
        GridBagConstraints gbc_lblClave = new GridBagConstraints();
        gbc_lblClave.anchor = GridBagConstraints.EAST;
        gbc_lblClave.insets = new Insets(0, 0, 5, 5);
        gbc_lblClave.gridx = 1;
        gbc_lblClave.gridy = 2;
        contentPane.add(lblClave, gbc_lblClave);

        txtClave = new JTextField();
        GridBagConstraints gbc_txtClave = new GridBagConstraints();
        gbc_txtClave.insets = new Insets(0, 0, 5, 0);
        gbc_txtClave.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtClave.gridx = 2;
        gbc_txtClave.gridy = 2;
        contentPane.add(txtClave, gbc_txtClave);
        txtClave.setColumns(10);

        btnAlta.setPreferredSize(new Dimension(120, 60));
        GridBagConstraints gbc_btnAlta = new GridBagConstraints();
        gbc_btnAlta.gridwidth = 2;
        gbc_btnAlta.insets = new Insets(0, 0, 0, 5);
        gbc_btnAlta.gridx = 1;
        gbc_btnAlta.gridy = 3;
        contentPane.add(btnAlta, gbc_btnAlta);
        btnAlta.addActionListener(this);
        
        btnVolver.setPreferredSize(new Dimension(120, 60));
        GridBagConstraints gbc_btnVolver = new GridBagConstraints();
        gbc_btnVolver.gridwidth = 2;
        gbc_btnVolver.insets = new Insets(0, 0, 0, 5);
        gbc_btnVolver.gridx = 1;
        gbc_btnVolver.gridy = 4;
        contentPane.add(btnVolver, gbc_btnVolver);
        btnVolver.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnAlta)) {
            String nombre = txtNombre.getText();
            String clave = txtClave.getText();
            if (nombre.isEmpty() || clave.isEmpty()) {
                System.out.println("Campos vacíos");
            } else {
                int respuesta = conexion.altaUsuario(nombre, clave);
                JOptionPane.showMessageDialog(this, "Alta Realizada.", "Alta", JOptionPane.INFORMATION_MESSAGE);

                if (respuesta != 1) {
                    System.out.println("Error en Alta");
                } else {
                    System.out.println("Usuario dado de alta exitosamente");
                }
            }
        }
        else if (e.getSource().equals(btnVolver)) {
            // Implementa la lógica para volver a la ventana anterior
        	// Por ejemplo, si tienes una ventana principal llamada `VentanaPrincipal`
            Usuario ventanaPrincipal = new Usuario();
            ventanaPrincipal.ventana.setVisible(true);
            dispose(); // Cierra la ventana actual
        }
    }
}
