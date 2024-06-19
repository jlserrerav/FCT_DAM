package fct;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EliminarEmpresaEnfermeria implements WindowListener, ActionListener {
    JFrame ventana = new JFrame("Eliminar Empresa");
    JDialog dlgConfirmacion = new JDialog(ventana, "Confirmación", true);
    JDialog dlgMensaje = new JDialog(ventana, "Mensaje", true);

    JLabel lblElegir = new JLabel("Elegir la Empresa a eliminar:");
    JPanel panelEmpresas = new JPanel();
    JScrollPane scrollPane = new JScrollPane(panelEmpresas);

    List<String> empresas; // Lista de empresas obtenida de la base de datos
    JCheckBox[] checkBoxes; // Array de checkboxes para cada empresa

    JButton btnEliminar = new JButton("Eliminar");
    JButton btnVolver = new JButton("Volver a Empresas");

    Conexion conexion = new Conexion();
    JPanel contentPane;

    public EliminarEmpresaEnfermeria() {

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setBounds(100, 100, 1200, 750); // Tamaño más grande
        ventana.addWindowListener(this);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        ventana.setLocationRelativeTo(null);

        ventana.setContentPane(contentPane);
        contentPane.setLayout(null); // Usamos un layout nulo para posicionar los componentes manualmente

        JLabel lblElegir = new JLabel("Elegir la Empresa a eliminar:");
        lblElegir.setBounds(20, 20, 200, 30);
        contentPane.add(lblElegir);

        panelEmpresas = new JPanel();
        panelEmpresas.setLayout(new BoxLayout(panelEmpresas, BoxLayout.Y_AXIS));
        cargarEmpresas();

        scrollPane = new JScrollPane(panelEmpresas);
        scrollPane.setBounds(20, 65, 1150, 550); // Ajusta el tamaño del scroll panel
        contentPane.add(scrollPane);

        btnVolver = new JButton("Volver a Empresa");
        btnVolver.addActionListener(this);
        btnVolver.setBounds(31, 639, 145, 53); // Posiciona el botón "Volver" abajo a la izquierda
        contentPane.add(btnVolver);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(this);
        btnEliminar.setBounds(938, 639, 152, 53); // Posiciona el botón "Eliminar" abajo a la derecha
        contentPane.add(btnEliminar);

        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    private void cargarEmpresas() {
        panelEmpresas.removeAll();
        empresas = conexion.obtenerListadoEmpresaEnfermeria2();
        checkBoxes = new JCheckBox[empresas.size()];
        panelEmpresas.setPreferredSize(new Dimension(300, empresas.size() * 30)); // Ajusta las proporciones al tamaño de Alumna

        for (int i = 0; i < empresas.size(); i++) {
            checkBoxes[i] = new JCheckBox(empresas.get(i));
            panelEmpresas.add(checkBoxes[i]);
        }

        panelEmpresas.revalidate();
        panelEmpresas.repaint();
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
                JOptionPane.showMessageDialog(ventana, "Solo puedes eliminar una empresa a la vez.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método sin continuar con la eliminación
            }
            for (int i = 0; i < checkBoxes.length; i++) {
                if (checkBoxes[i].isSelected()) {
                    String[] empresaDatos = empresas.get(i).split(" \\| ");
                    if (empresaDatos.length < 2) {
                        JOptionPane.showMessageDialog(ventana, "Error en los datos de la empresa.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String id = empresaDatos[0];

                    int confirm = JOptionPane.showConfirmDialog(
                            ventana,
                            "¿Estás seguro de que deseas eliminar esta empresa?",
                            "Confirmación",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        String sentencia = "DELETE FROM Empresas WHERE idEmpresa=" + id + ";";
                        int respuesta = conexion.eliminarEmpresa(sentencia);
                        if (respuesta != 0) {
                            JOptionPane.showMessageDialog(ventana, "Empresa eliminada correctamente");
                            cargarEmpresas();

                        } 
                    }
                }
            }
        } else if (e.getSource().equals(btnVolver)) {
            EmpresasEnfermeria ee = new EmpresasEnfermeria();
            ee.setVisible(true);
            ventana.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EliminarEmpresaEnfermeria();
        });
    }
}
