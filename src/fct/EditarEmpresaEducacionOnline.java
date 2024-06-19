package fct;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EditarEmpresaEducacionOnline  implements WindowListener, ActionListener {
    JFrame ventana = new JFrame("Editar Empresa");
    JDialog dlgEdicion = new JDialog(ventana, "Edición", true);
    JDialog dlgMensaje = new JDialog(ventana, "Mensaje", true);

    JLabel lblElegir = new JLabel("Elegir la Empresa a editar:");
    JPanel panelEmpresas = new JPanel();
    JScrollPane scrollPane = new JScrollPane(panelEmpresas);

    List<String> empresas; // Lista de empresas obtenida de la base de datos
    JCheckBox[] checkBoxes; // Array de checkboxes para cada empresa

    JButton btnEditar = new JButton("Editar");
    JButton btnVolver = new JButton("Volver a Empresas");

    Conexion conexion = new Conexion();
    JPanel contentPane;

    String idEmpresa = "";

    public EditarEmpresaEducacionOnline() {

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setBounds(100, 100, 1200, 750); // Tamaño más grande
        ventana.addWindowListener(this);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        ventana.setLocationRelativeTo(null);

        ventana.setContentPane(contentPane);
        contentPane.setLayout(null); // Usamos un layout nulo para posicionar los componentes manualmente

        JLabel lblElegir = new JLabel("Elegir la Empresa a editar:");
        lblElegir.setBounds(20, 20, 200, 30);
        contentPane.add(lblElegir);

        panelEmpresas = new JPanel();
        panelEmpresas.setLayout(new BoxLayout(panelEmpresas, BoxLayout.Y_AXIS));
        empresas = conexion.obtenerListadoEmpresaEducacionOnline2();
        checkBoxes = new JCheckBox[empresas.size()];
        panelEmpresas.setPreferredSize(new Dimension(300, empresas.size() * 30)); // Ajusta las proporciones al tamaño de Alumna

        for (int i = 0; i < empresas.size(); i++) {
            checkBoxes[i] = new JCheckBox(empresas.get(i));
            panelEmpresas.add(checkBoxes[i]);
        }

        scrollPane = new JScrollPane(panelEmpresas);
        scrollPane.setBounds(20, 65, 1150, 550); // Ajusta el tamaño del scroll panel
        contentPane.add(scrollPane);

        btnVolver = new JButton("Volver a Empresa");
        btnVolver.addActionListener(this);
        btnVolver.setBounds(31, 639, 145, 53); // Posiciona el botón "Volver" abajo a la izquierda
        contentPane.add(btnVolver);

        btnEditar = new JButton("Editar");
        btnEditar.addActionListener(this);
        btnEditar.setBounds(938, 639, 152, 53); // Posiciona el botón "Editar" abajo a la derecha
        contentPane.add(btnEditar);

        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        if (dlgEdicion.isActive()) {
            dlgEdicion.setVisible(false);
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
        if (e.getSource().equals(btnEditar)) {
            int countSelected = 0; // Contador para el número de checkboxes seleccionados
            for (JCheckBox checkBox : checkBoxes) {
                if (checkBox.isSelected()) {
                    countSelected++;
                }
            }
            if (countSelected > 1) {
                JOptionPane.showMessageDialog(ventana, "Solo puedes editar una empresa a la vez.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método sin continuar con la edición
            }
            for (int i = 0; i < checkBoxes.length; i++) {
                if (checkBoxes[i].isSelected()) {
                    String[] empresaDatos = empresas.get(i).split(" \\| ");
                    if (empresaDatos.length < 2) {
                        JOptionPane.showMessageDialog(ventana, "Error en los datos de la empresa.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String id = empresaDatos[0];
                    String resultado = conexion.getDatosEdicionEmpresa(id);
                    String[] datos = resultado.split("\\|"); // Usar el nuevo delimitador
                    if (datos.length < 8) {
                        JOptionPane.showMessageDialog(ventana, "Error en los datos de la empresa.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    idEmpresa = datos[0];

                    JDialog dlgEdicion = new JDialog(ventana, "Edición", true);
                    dlgEdicion.getContentPane().setLayout(new BorderLayout());
                    dlgEdicion.setSize(600, 400);
                    dlgEdicion.addWindowListener(this);

                    JPanel panelDatos = new JPanel(new GridLayout(0, 2, 5, 5)); // 0 filas, 2 columnas
                    panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Agrega un borde
                    dlgEdicion.getContentPane().add(panelDatos, BorderLayout.CENTER);

                    JLabel lblTitulo = new JLabel("------- Edición de Empresa -------", SwingConstants.CENTER);
                    dlgEdicion.getContentPane().add(lblTitulo, BorderLayout.NORTH);

                    JLabel[] labels = {
                            new JLabel("Nombre:"),
                            new JLabel("CIF:"),
                            new JLabel("Dirección Postal:"),
                            new JLabel("Localidad:"),
                            new JLabel("Representante Legal:"),
                            new JLabel("DNI:"),
                            new JLabel("Email:"),
                            new JLabel("Telefono:")
                    };

                    JTextField[] fields = {
                            new JTextField(datos[1]),
                            new JTextField(datos[2]),
                            new JTextField(datos[3]),
                            new JTextField(datos[4]),
                            new JTextField(datos[5]),
                            new JTextField(datos[6]),
                            new JTextField(datos[7]),
                            new JTextField(datos[8])
                    };

                    for (int j = 0; j < labels.length; j++) {
                        panelDatos.add(labels[j]);
                        panelDatos.add(fields[j]);
                    }

                    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    dlgEdicion.getContentPane().add(panelBotones, BorderLayout.SOUTH);

                    JButton btnModificar = new JButton("Modificar");
                    btnModificar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Modificar la empresa con los datos del formulario
                            String sentencia = "UPDATE Empresas SET " +
                                    "nombreEmpresa='" + fields[0].getText() + "', " +
                                    "CIF='" + fields[1].getText() + "', " +
                                    "direccionPostal='" + fields[2].getText() + "', " +
                                    "localidad='" + fields[3].getText() + "', " +
                                    "representanteLegal='" + fields[4].getText() + "', " +
                                    "dniRepresentante='" + fields[5].getText() + "', " +
                                    "emailEmpresa='" + fields[6].getText() + "', " +
                                    "telefonoEmpresa='" + fields[7].getText() + "' " +
                                    "WHERE idEmpresa=" + idEmpresa + ";";

                            int respuesta = conexion.modificarAlumna(sentencia);
                            if (respuesta != 0) {
                                JOptionPane.showMessageDialog(dlgEdicion, "Error en la modificación");
                            } else {
                                JOptionPane.showMessageDialog(dlgEdicion, "Modificación de empresa correcta");
                                dlgEdicion.dispose(); // Cerrar el diálogo después de modificar
                            }
                        }
                    });

                    JButton btnCancelar = new JButton("Cancelar");
                    btnCancelar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dlgEdicion.dispose(); // Cerrar el diálogo sin modificar
                        }
                    });

                    panelBotones.add(btnModificar);
                    panelBotones.add(btnCancelar);

                    dlgEdicion.setResizable(false);
                    dlgEdicion.setLocationRelativeTo(ventana);
                    dlgEdicion.setVisible(true);
                }
            }
        } else if (e.getSource().equals(btnVolver)) {
            EmpresasEducacionOnline eEO = new EmpresasEducacionOnline();
            eEO.setVisible(true);
            ventana.dispose();
        }
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EditarEmpresaEducacionOnline();
        });
    }
}
