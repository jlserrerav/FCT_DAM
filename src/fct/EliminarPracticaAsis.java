package fct;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EliminarPracticaAsis implements WindowListener, ActionListener {

    JFrame ventana = new JFrame("Eliminar Practica");
    private JDialog dlgConfirmacion = new JDialog(ventana, "Confirmación", true);
    private JDialog dlgMensaje = new JDialog(ventana, "Mensaje", true);

    private JLabel lblElegir = new JLabel("Elegir la Practica a eliminar:");
    private JPanel panelPracticas = new JPanel();
    private JScrollPane scrollPane = new JScrollPane(panelPracticas);

    private List<String> Practicas; // Lista de Practicas obtenida de la base de datos
    private JCheckBox[] checkBoxes; // Array de checkboxes para cada Practica

    private JButton btnEliminar = new JButton("Eliminar");
    private JButton btnVolver = new JButton("Volver a Practicas");

    private Conexion conexion = new Conexion();
    private JPanel contentPane;

    public EliminarPracticaAsis() {

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setBounds(100, 100, 1200, 750); // Tamaño más grande
        ventana.addWindowListener(this);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        ventana.setLocationRelativeTo(null);

        ventana.setContentPane(contentPane);
        contentPane.setLayout(null); // Usamos un layout nulo para posicionar los componentes manualmente

        lblElegir.setBounds(20, 20, 250, 30);
        contentPane.add(lblElegir);

        panelPracticas.setLayout(new BoxLayout(panelPracticas, BoxLayout.Y_AXIS));
        cargarPracticas();

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

    private void cargarPracticas() {
        panelPracticas.removeAll();
        Practicas = conexion.obtenerListadoPracticaAsis();
        checkBoxes = new JCheckBox[Practicas.size()];
        panelPracticas.setPreferredSize(new Dimension(300, Practicas.size() * 30)); // Ajusta las proporciones al tamaño de Practica

        for (int i = 0; i < Practicas.size(); i++) {
            checkBoxes[i] = new JCheckBox(Practicas.get(i));
            panelPracticas.add(checkBoxes[i]);
        }

        panelPracticas.revalidate();
        panelPracticas.repaint();
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
                JOptionPane.showMessageDialog(ventana, "Solo puedes eliminar una Practica a la vez.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método sin continuar con la eliminación
            }
            for (int i = 0; i < checkBoxes.length; i++) {
                if (checkBoxes[i].isSelected()) {
                    String[] PracticaDatos = Practicas.get(i).split(" - ");
                    if (PracticaDatos.length < 2) {
                        JOptionPane.showMessageDialog(ventana, "Error en los datos de la Practica.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String id = PracticaDatos[0].trim();

                    // Verificar si la Practica tiene prácticas asociadas
                    if (conexion.tienePracticasAsociadas(id)) {
                        JOptionPane.showMessageDialog(ventana, "No puedes eliminar esta Practica porque está asociada a una práctica.", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // No continuar con la eliminación
                    }

                    int confirm = JOptionPane.showConfirmDialog(
                            ventana,
                            "¿Estás seguro de que deseas eliminar esta Practica?",
                            "Confirmación",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        int respuesta = conexion.eliminarPractica(id);
                        if (respuesta != -1) {
                            JOptionPane.showMessageDialog(ventana, "Practica eliminada correctamente");
                            cargarPracticas();
                        } else {
                            JOptionPane.showMessageDialog(ventana, "Error al eliminar la Practica.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        } else if (e.getSource().equals(btnVolver)) {
            PracticaAsis pa = new PracticaAsis();
            pa.setVisible(true);
            ventana.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EliminarPracticaAsis();
        });
    }
}
