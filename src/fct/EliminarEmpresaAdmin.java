package fct;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EliminarEmpresaAdmin extends JFrame implements WindowListener, ActionListener {
    JPanel panelEmpresas = new JPanel();
    JScrollPane scrollPane = new JScrollPane(panelEmpresas);

    List<String> empresas; // Lista de empresas obtenida de la base de datos
    JCheckBox[] checkBoxes; // Array de checkboxes para cada empresa

    JButton btnEliminar = new JButton("Eliminar");
    JButton btnVolver = new JButton("Volver a Empresas");

    Conexion conexion = new Conexion();
    JPanel contentPane;

    public EliminarEmpresaAdmin() {
        setTitle("Eliminar Empresa de Administración"); // Título de la ventana

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 750); // Tamaño más grande
        addWindowListener(this);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);

        setContentPane(contentPane);
        contentPane.setLayout(null); // Usamos un layout nulo para posicionar los componentes manualmente

        JLabel lblElegir = new JLabel("Elegir la Empresa a eliminar:");
        lblElegir.setBounds(20, 20, 200, 30);
        contentPane.add(lblElegir);

        panelEmpresas.setLayout(new BoxLayout(panelEmpresas, BoxLayout.Y_AXIS));
        cargarEmpresas();

        scrollPane.setBounds(20, 65, 1150, 550); // Ajusta el tamaño del scroll panel
        contentPane.add(scrollPane);

        btnVolver.addActionListener(this);
        btnVolver.setBounds(31, 639, 145, 53); // Posiciona el botón "Volver" abajo a la izquierda
        contentPane.add(btnVolver);

        btnEliminar.addActionListener(this);
        btnEliminar.setBounds(938, 639, 152, 53); // Posiciona el botón "Eliminar" abajo a la derecha
        contentPane.add(btnEliminar);

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cargarEmpresas() {
        panelEmpresas.removeAll();
        empresas = conexion.obtenerListadoEmpresaAdministracion();
        checkBoxes = new JCheckBox[empresas.size()];
        panelEmpresas.setPreferredSize(new Dimension(300, empresas.size() * 30)); // Ajusta las proporciones al tamaño de Empresa

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
        setVisible(false);
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
                JOptionPane.showMessageDialog(this, "Solo puedes eliminar una empresa a la vez.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método sin continuar con la eliminación
            }
            for (int i = 0; i < checkBoxes.length; i++) {
                if (checkBoxes[i].isSelected()) {
                    String[] empresaDatos = empresas.get(i).split(" \\| ");
                    if (empresaDatos.length < 2) {
                        JOptionPane.showMessageDialog(this, "Error en los datos de la empresa.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String id = empresaDatos[0];

                    // Verificar si la empresa tiene contactos o sedes asociadas
                    if (conexion.tieneContactosAsociados(id)) {
                        JOptionPane.showMessageDialog(this, "No puedes eliminar esta empresa porque tiene contactos asociados.", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // No continuar con la eliminación
                    }

                    if (conexion.tieneSedesAsociadas(id)) {
                        JOptionPane.showMessageDialog(this, "No puedes eliminar esta empresa porque tiene sedes asociadas.", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // No continuar con la eliminación
                    }

                    int confirm = JOptionPane.showConfirmDialog(
                            this,
                            "¿Estás seguro de que deseas eliminar esta empresa?",
                            "Confirmación",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        String sentencia = "DELETE FROM Empresas WHERE idEmpresa=" + id + ";";
                        int respuesta = conexion.eliminarEmpresa(sentencia);
                        if (respuesta != 0) {
                            JOptionPane.showMessageDialog(this, "Empresa eliminada correctamente");
                            cargarEmpresas();
                        }
                    }
                }
            }
        } else if (e.getSource().equals(btnVolver)) {
            EmpresasAdministracion ea = new EmpresasAdministracion();
            ea.setVisible(true);
        	dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EliminarEmpresaAdmin();
        });
    }
}
