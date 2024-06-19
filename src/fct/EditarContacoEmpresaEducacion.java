package fct;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EditarContacoEmpresaEducacion extends JFrame implements WindowListener, ActionListener {
    JFrame ventana = new JFrame("Editar Empresa");
    JDialog dlgEdicion = new JDialog(ventana, "Edición", true);
    JDialog dlgMensaje = new JDialog(ventana, "Mensaje", true);

    JLabel lblElegir = new JLabel("Elegir la Empresa a editar:");
    JPanel panelContactos = new JPanel();
    JScrollPane scrollPane = new JScrollPane(panelContactos);

    List<String> contactos; // Lista de empresas obtenida de la base de datos
    JCheckBox[] checkBoxes; // Array de checkboxes para cada empresa

    JButton btnEditar = new JButton("Editar");
    JButton btnVolver = new JButton("Volver a Contacto");

    Conexion conexion = new Conexion();
    JPanel contentPane;

    String idContacto = "";

    public EditarContacoEmpresaEducacion() {
    	setTitle("Editar Contacto de Educación"); // Título de la ventana

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setBounds(100, 100, 1200, 750); // Tamaño más grande
        ventana.addWindowListener(this);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);

        ventana.setContentPane(contentPane);
        contentPane.setLayout(null); // Usamos un layout nulo para posicionar los componentes manualmente

        JLabel lblElegir = new JLabel("Elegir el Contacto a editar:");
        lblElegir.setBounds(20, 20, 200, 30);
        contentPane.add(lblElegir);

        panelContactos = new JPanel();
        panelContactos.setLayout(new BoxLayout(panelContactos, BoxLayout.Y_AXIS));
        contactos = conexion.obtenerListadoContactosEmpresaEducacion();
        checkBoxes = new JCheckBox[contactos.size()];
        panelContactos.setPreferredSize(new Dimension(300, contactos.size() * 30)); // Ajusta las proporciones al tamaño de Alumna

        for (int i = 0; i < contactos.size(); i++) {
            checkBoxes[i] = new JCheckBox(contactos.get(i));
            panelContactos.add(checkBoxes[i]);
        }

        scrollPane = new JScrollPane(panelContactos);
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
    
    private void cargarContactos() {
        panelContactos.removeAll();
        contactos = conexion.obtenerListadoContactosEmpresaEducacion();
        checkBoxes = new JCheckBox[contactos.size()];
        panelContactos.setPreferredSize(new Dimension(300, contactos.size() * 30)); // Ajusta las proporciones al tamaño de Contacto

        for (int i = 0; i < contactos.size(); i++) {
            checkBoxes[i] = new JCheckBox(contactos.get(i));
            panelContactos.add(checkBoxes[i]);
        }
        

        panelContactos.revalidate();
        panelContactos.repaint();
    }
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
                JOptionPane.showMessageDialog(ventana, "Solo puedes editar un contacto a la vez.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método sin continuar con la edición
            }
            for (int i = 0; i < checkBoxes.length; i++) {
                if (checkBoxes[i].isSelected()) {
                    String[] contactoDatos = contactos.get(i).split(" \\| ");
                    if (contactoDatos.length < 9) { // Comprobar que hay 9 elementos
                        JOptionPane.showMessageDialog(ventana, "Error en los datos del contacto.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String id = contactoDatos[0];
                    String resultado = conexion.getDatosEdicionContacto(id);
                    String[] datos = resultado.split("-"); // Usar el nuevo delimitador
                    if (datos.length < 9) {
                        JOptionPane.showMessageDialog(ventana, "Error en los datos del contacto.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    idContacto = datos[0];

                    JDialog dlgEdicion = new JDialog(this, "Edición", true);
                    dlgEdicion.getContentPane().setLayout(new BorderLayout());
                    dlgEdicion.setSize(600, 400);
                    dlgEdicion.addWindowListener(this);

                    JPanel panelDatos = new JPanel(new GridLayout(0, 2, 5, 5)); // 0 filas, 2 columnas
                    panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Agrega un borde
                    dlgEdicion.getContentPane().add(panelDatos, BorderLayout.CENTER);

                    JLabel lblTitulo = new JLabel("------- Edición de Contacto -------", SwingConstants.CENTER);
                    dlgEdicion.getContentPane().add(lblTitulo, BorderLayout.NORTH);

                    JLabel[] labels = {
                            new JLabel("Nombre:"),
                            new JLabel("Primer Apellido:"),
                            new JLabel("Segundo Apellido:"),
                            new JLabel("DNI:"),
                            new JLabel("Telefono:"),
                            new JLabel("Email:"),
                            new JLabel("Dual:"),
                            new JLabel("Empresa:")
                    };

                    JTextField[] fields = {
                            new JTextField(datos[1]),
                            new JTextField(datos[2]),
                            new JTextField(datos[3]),
                            new JTextField(datos[4]),
                            new JTextField(datos[5]),
                            new JTextField(datos[6])
                    };

                    // ComboBox para 'esDual'
                    JComboBox<String> comboDual = new JComboBox<>(new String[]{"Es Dual", "No es Dual"});
                    comboDual.setSelectedIndex(datos[7].equals("0") ? 0 : 1);

                    // ComboBox para 'idEmpresaFK'
                    JComboBox<String> comboEmpresas = new JComboBox<>();
                    conexion.rellenarChoiceEmpresaEduc(comboEmpresas);
                    comboEmpresas.setSelectedItem(datos[8]);

                    for (int j = 0; j < labels.length; j++) {
                        panelDatos.add(labels[j]);
                        if (j < fields.length) {
                            panelDatos.add(fields[j]);
                        } else if (j == fields.length) {
                            panelDatos.add(comboDual);
                        } else {
                            panelDatos.add(comboEmpresas);
                        }
                    }

                    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    dlgEdicion.getContentPane().add(panelBotones, BorderLayout.SOUTH);

                    JButton btnModificar = new JButton("Modificar");
                    btnModificar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Modificar el contacto con los datos del formulario
                            int esDual = comboDual.getSelectedIndex() == 0 ? 0 : 1;
                            String selectedEmpresa = (String) comboEmpresas.getSelectedItem();
                            int idEmpresaFK = Integer.parseInt(selectedEmpresa.split("-")[0]);

                            String sentencia = "UPDATE ContactosEmpresa SET " +
                                    "nombreContacto='" + fields[0].getText() + "', " +
                                    "primerApellidoContacto='" + fields[1].getText() + "', " +
                                    "segundoApellidoContacto='" + fields[2].getText() + "', " +
                                    "dniContacto='" + fields[3].getText() + "', " +
                                    "telefonoContacto='" + fields[4].getText() + "', " +
                                    "emailContacto='" + fields[5].getText() + "', " +
                                    "esDual=" + esDual + ", " +
                                    "idEmpresaFK=" + idEmpresaFK + " " +
                                    "WHERE idContacto=" + idContacto;

                            try {
                                conexion.modificarAlumna(sentencia);
                                cargarContactos();
                                JOptionPane.showMessageDialog(dlgEdicion, "Contacto modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                dlgEdicion.setVisible(false);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(dlgEdicion, "Error al modificar el contacto.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });

                    JButton btnCancelar = new JButton("Cancelar");
                    btnCancelar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dlgEdicion.setVisible(false);
                        }
                    });

                    panelBotones.add(btnModificar);
                    panelBotones.add(btnCancelar);

                    dlgEdicion.setLocationRelativeTo(null);
                    dlgEdicion.setVisible(true);
                }
            }
        } else if (e.getSource().equals(btnVolver)) {
            ventana.setVisible(false);
            ContactoEmpresaEducacion c = new ContactoEmpresaEducacion();
            c.setVisible(true);
        }
    }


    public static void main(String[] args) {
        new EditarContacoEmpresaEducacion();
    }
}
