package fct;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EliminarSedeAsistencia extends JFrame implements WindowListener, ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame ventana = new JFrame("Eliminar Sede");
    JDialog dlgConfirmacion = new JDialog(ventana, "Confirmación", true);
    JDialog dlgMensaje = new JDialog(ventana, "Mensaje", true);

    JLabel lblElegir = new JLabel("Elegir el Sede a eliminar:");
    JPanel panelSedes = new JPanel();
    JScrollPane scrollPane = new JScrollPane(panelSedes);

    List<String> Sedes; // Lista de Sedes obtenida de la base de datos
    JCheckBox[] checkBoxes; // Array de checkboxes para cada Sede

    JButton btnEliminar = new JButton("Eliminar");
    JButton btnVolver = new JButton("Volver a Sedes");

    Conexion conexion = new Conexion();
    JPanel contentPane;

    public EliminarSedeAsistencia() {
    	setTitle("Eliminar Sede de Asistencia"); // Título de la ventana

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setBounds(100, 100, 1200, 750); // Tamaño más grande
        ventana.addWindowListener(this);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);

        ventana.setContentPane(contentPane);
        contentPane.setLayout(null); // Usamos un layout nulo para posicionar los componentes manualmente

        lblElegir.setBounds(20, 20, 200, 30);
        contentPane.add(lblElegir);

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

        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    private void cargarSedes() {
        panelSedes.removeAll();
        Sedes = conexion.obtenerListadoSedesEmpresaAsis();
        checkBoxes = new JCheckBox[Sedes.size()];
        panelSedes.setPreferredSize(new Dimension(300, Sedes.size() * 30)); // Ajusta las proporciones al tamaño de Sede

        for (int i = 0; i < Sedes.size(); i++) {
            checkBoxes[i] = new JCheckBox(Sedes.get(i));
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
                JOptionPane.showMessageDialog(ventana, "Solo puedes eliminar un Sede a la vez.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método sin continuar con la eliminación
            }
            for (int i = 0; i < checkBoxes.length; i++) {
                if (checkBoxes[i].isSelected()) {
                    String[] SedeDatos = Sedes.get(i).split(" \\| ");
                    if (SedeDatos.length < 2) {
                        JOptionPane.showMessageDialog(ventana, "Error en los datos del Sede.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String id = SedeDatos[0];

                    // Verificar si el Sede tiene prácticas asociadas
                    if (conexion.tienePracticasAsociadas2(id)) {
                        JOptionPane.showMessageDialog(ventana, "No puedes eliminar este Sede porque está asociado a una práctica.", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // No continuar con la eliminación
                    }

                    int confirm = JOptionPane.showConfirmDialog(
                            ventana,
                            "¿Estás seguro de que deseas eliminar este Sede?",
                            "Confirmación",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        int respuesta = conexion.eliminarSede(id);
                        if (respuesta != -1) {
                            JOptionPane.showMessageDialog(ventana, "Sede eliminado correctamente");
                            cargarSedes();
                        } else {
                            JOptionPane.showMessageDialog(ventana, "Error al eliminar el Sede.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        } else if (e.getSource().equals(btnVolver)) {
            // Cerrar la ventana actual y volver a la ventana anterior (Menú Sede)
            ventana.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EliminarSedeAsistencia();
        });
    }
}
