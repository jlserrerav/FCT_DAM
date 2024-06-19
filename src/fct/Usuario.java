package fct;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Usuario implements WindowListener, ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame ventana = new JFrame("Usuario");
    JDialog dlgConfirmacion = new JDialog(ventana, "Confirmación", true);
    JDialog dlgMensaje = new JDialog(ventana, "Mensaje", true);

    JPanel panelSedes = new JPanel();
    JScrollPane scrollPane = new JScrollPane(panelSedes);


    List<String> Usuarios; // Lista de Sedes obtenida de la base de datos
    JCheckBox[] checkBoxes; // Array de checkboxes para cada Sede

    JButton btnEliminar = new JButton("Eliminar");
    JButton btnVolver = new JButton("Volver a Menú");
    JButton btnAlta = new JButton("Alta");


    Conexion conexion = new Conexion();
    JPanel contentPane;

    public Usuario() {

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setBounds(100, 100, 1200, 750); // Tamaño más grande
        ventana.addWindowListener(this);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        ventana.setLocationRelativeTo(null);

        ventana.setContentPane(contentPane);
        contentPane.setLayout(null); // Usamos un layout nulo para posicionar los componentes manualmente


        panelSedes.setLayout(new BoxLayout(panelSedes, BoxLayout.Y_AXIS));
        cargarSedes();

        scrollPane.setBounds(20, 65, 1150, 550); // Ajusta el tamaño del scroll panel
        contentPane.add(scrollPane);

        btnVolver.addActionListener(this);
        btnVolver.setBounds(31, 639, 145, 53); // Posiciona el botón "Volver" abajo a la izquierda
        contentPane.add(btnVolver);

        btnEliminar.addActionListener(this);
        btnEliminar.setBounds(938, 639, 152, 53); // Posiciona el botón "Eliminar" abajo a la derecha
        contentPane.add(btnEliminar);
        
        btnAlta.addActionListener(this);
        btnAlta.setBounds(700, 639, 152, 53); // Posiciona el botón "Eliminar" abajo a la derecha
        contentPane.add(btnAlta);

        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    private void cargarSedes() {
        panelSedes.removeAll();
        Usuarios = conexion.obtenerListadoUsuarios();
        checkBoxes = new JCheckBox[Usuarios.size()];
        panelSedes.setPreferredSize(new Dimension(300, Usuarios.size() * 30)); // Ajusta las proporciones al tamaño de Sede

        for (int i = 0; i < Usuarios.size(); i++) {
            checkBoxes[i] = new JCheckBox(Usuarios.get(i));
            panelSedes.add(checkBoxes[i]);
        }

        panelSedes.revalidate();
        panelSedes.repaint();
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
                JOptionPane.showMessageDialog(ventana, "Solo puedes eliminar un usuario a la vez.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método sin continuar con la eliminación
            }
            for (int i = 0; i < checkBoxes.length; i++) {
                if (checkBoxes[i].isSelected()) {
                    String[] UsuarioDatos = Usuarios.get(i).split(" \\| ");
                    if (UsuarioDatos.length < 2) {
                        JOptionPane.showMessageDialog(ventana, "Error en los datos del Sede.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String id = UsuarioDatos[0];

                    

                    int confirm = JOptionPane.showConfirmDialog(
                            ventana,
                            "¿Estás seguro de que deseas eliminar este usuario?",
                            "Confirmación",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        int respuesta = conexion.eliminarUsuario(id);
                        if (respuesta != -1) {
                            JOptionPane.showMessageDialog(ventana, "Usuario eliminado correctamente");
                            cargarSedes();
                        } else {
                            JOptionPane.showMessageDialog(ventana, "Error al eliminar el Sede.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        } else if (e.getSource().equals(btnVolver)) {
            MenuPrincipal mp = new MenuPrincipal();
            mp.setVisible(true);
            ventana.dispose();
        }
        else if (e.getSource().equals(btnAlta)) {
            AltaUsuario mp = new AltaUsuario();
            mp.setVisible(true);
            ventana.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Usuario();
        });
    }
}
