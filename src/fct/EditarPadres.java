package fct;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EditarPadres extends JFrame implements WindowListener, ActionListener {
    private static final long serialVersionUID = 1L;
    JDialog dlgEdicion = new JDialog(this, "Edición", true);
    JDialog dlgMensaje = new JDialog(this, "Mensaje", true);

    JLabel lblElegir = new JLabel("Elegir el Padre/Madre a editar:");
    JPanel panelPadres = new JPanel();
    JScrollPane scrollPane = new JScrollPane(panelPadres);

    List<String> Padres; // Lista de padres obtenida de la base de datos
    JCheckBox[] checkBoxes; // Array de checkboxes para cada padre

    JButton btnEditar = new JButton("Editar");
    JButton btnVolver = new JButton("Volver");

    Conexion conexion = new Conexion();
    JPanel contentPane;

    String idPadre = "";

    public EditarPadres() {
        setTitle("Editar Padres"); // Título de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 750); // Tamaño más grande
        addWindowListener(this);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);

        setContentPane(contentPane);
        contentPane.setLayout(null); // Usamos un layout nulo para posicionar los componentes manualmente

        JLabel lblElegir = new JLabel("Elegir el Padre/Madre a editar:");
        lblElegir.setBounds(20, 20, 200, 30);
        contentPane.add(lblElegir);

        panelPadres = new JPanel();
        panelPadres.setLayout(new BoxLayout(panelPadres, BoxLayout.Y_AXIS));
        Padres = conexion.obtenerListadoPadres();
        checkBoxes = new JCheckBox[Padres.size()];
        panelPadres.setPreferredSize(new Dimension(300, Padres.size() * 30)); // Ajusta las proporciones al tamaño de Alumna

        for (int i = 0; i < Padres.size(); i++) {
            checkBoxes[i] = new JCheckBox(Padres.get(i));
            panelPadres.add(checkBoxes[i]);
        }

        scrollPane = new JScrollPane(panelPadres);
        scrollPane.setBounds(20, 65, 1150, 550); // Ajusta el tamaño del scroll panel
        contentPane.add(scrollPane);

        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(this);
        btnVolver.setBounds(31, 639, 145, 53); // Posiciona el botón "Volver" abajo a la izquierda
        contentPane.add(btnVolver);

        btnEditar = new JButton("Editar");
        btnEditar.addActionListener(this);
        btnEditar.setBounds(938, 639, 152, 53); // Posiciona el botón "Editar" abajo a la derecha
        contentPane.add(btnEditar);

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cargarPadres() {
        panelPadres.removeAll();
        Padres = conexion.obtenerListadoPadres();
        checkBoxes = new JCheckBox[Padres.size()];
        panelPadres.setPreferredSize(new Dimension(300, Padres.size() * 30)); // Ajusta las proporciones al tamaño de Padre

        for (int i = 0; i < Padres.size(); i++) {
            checkBoxes[i] = new JCheckBox(Padres.get(i));
            panelPadres.add(checkBoxes[i]);
        }

        panelPadres.revalidate();
        panelPadres.repaint();
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        if (dlgEdicion.isActive()) {
            dlgEdicion.setVisible(false);
            setVisible(false);
        } else if (dlgMensaje.isActive()) {
            dlgMensaje.setVisible(false);
        } else {
            setVisible(false);
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
                JOptionPane.showMessageDialog(this, "Solo puedes editar un padre/madre a la vez.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método sin continuar con la edición
            }
            for (int i = 0; i < checkBoxes.length; i++) {
                if (checkBoxes[i].isSelected()) {
                    String[] padreDatos = Padres.get(i).split(" \\| ");
                    if (padreDatos.length < 10) { // Comprobar que hay 10 elementos
                        JOptionPane.showMessageDialog(this, "Error en los datos del padre/madre.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String id = padreDatos[0];
                    String resultado = conexion.getDatosEdicionPadre(id);
                    String[] datos = resultado.split("-"); // Usar el nuevo delimitador
                    if (datos.length < 10) {
                        JOptionPane.showMessageDialog(this, "Error en los datos del padre/madre.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    idPadre = datos[0];

                    JDialog dlgEdicion = new JDialog(this, "Edición", true);
                    dlgEdicion.getContentPane().setLayout(new BorderLayout());
                    dlgEdicion.setSize(600, 400);
                    dlgEdicion.addWindowListener(this);

                    JPanel panelDatos = new JPanel(new GridLayout(0, 2, 5, 5)); // 0 filas, 2 columnas
                    panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Agrega un borde
                    dlgEdicion.getContentPane().add(panelDatos, BorderLayout.CENTER);

                    JLabel lblTitulo = new JLabel("------- Edición de Padre/Madre -------", SwingConstants.CENTER);
                    dlgEdicion.getContentPane().add(lblTitulo, BorderLayout.NORTH);

                    JLabel[] labels = {
                            new JLabel("Nombre del Padre:"),
                            new JLabel("Primer Apellido del Padre:"),
                            new JLabel("Segundo Apellido del Padre:"),
                            new JLabel("Teléfono del Padre:"),
                            new JLabel("Nombre de la Madre:"),
                            new JLabel("Primer Apellido de la Madre:"),
                            new JLabel("Segundo Apellido de la Madre:"),
                            new JLabel("Teléfono de la Madre:"),
                            new JLabel("Alumna:")
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

                    // ComboBox para 'idAlumnaFK'
                    JComboBox<String> comboAlumnas = new JComboBox<>();
                    conexion.rellenarChoiceAlumna(comboAlumnas);
                    comboAlumnas.setSelectedItem(datos[9]);

                    for (int j = 0; j < labels.length; j++) {
                        panelDatos.add(labels[j]);
                        if (j < fields.length) {
                            panelDatos.add(fields[j]);
                        } else {
                            panelDatos.add(comboAlumnas);
                        }
                    }

                    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    dlgEdicion.getContentPane().add(panelBotones, BorderLayout.SOUTH);

                    JButton btnModificar = new JButton("Modificar");
                    btnModificar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Modificar el padre/madre con los datos del formulario
                            String selectedAlumna = (String) comboAlumnas.getSelectedItem();
                            int idAlumnaFK = Integer.parseInt(selectedAlumna.split("-")[0]);

                            String sentencia = "UPDATE Padres SET " +
                                    "nombrePadre='" + fields[0].getText() + "', " +
                                    "primerApellidoPadre='" + fields[1].getText() + "', " +
                                    "segundoApellidoPadre='" + fields[2].getText() + "', " +
                                    "telefonoPadre='" + fields[3].getText() + "', " +
                                    "nombreMadre='" + fields[4].getText() + "', " +
                                    "primerApellidoMadre='" + fields[5].getText() + "', " +
                                    "segundoApellidoMadre='" + fields[6].getText() + "', " +
                                    "telefonoMadre='" + fields[7].getText() + "', " +
                                    "idAlumnaFK=" + idAlumnaFK + " " +
                                    "WHERE idPadre=" + idPadre;

                            try {
                                conexion.modificarAlumna(sentencia);

                                JOptionPane.showMessageDialog(dlgEdicion, "Padre/Madre modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                cargarPadres();
                                dlgEdicion.setVisible(false);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(dlgEdicion, "Error al modificar el padre/madre.", "Error", JOptionPane.ERROR_MESSAGE);
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
            setVisible(false);
            Padres c = new Padres();
            c.setVisible(true);
        }
    }

    public static void main(String[] args) {
        new EditarPadres();
    }
}
