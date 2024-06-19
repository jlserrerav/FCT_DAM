package fct;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EditarSedeEducOnline extends JFrame implements WindowListener, ActionListener {
    JFrame ventana = new JFrame("Editar Sede");
    JDialog dlgEdicion = new JDialog(ventana, "Edición", true);
    JDialog dlgMensaje = new JDialog(ventana, "Mensaje", true);

    JLabel lblElegir = new JLabel("Elegir la Sede a editar:");
    JPanel panelSede = new JPanel();
    JScrollPane scrollPane = new JScrollPane(panelSede);

    List<String> sedes; // Lista de empresas obtenida de la base de datos
    JCheckBox[] checkBoxes; // Array de checkboxes para cada empresa

    JButton btnEditar = new JButton("Editar");
    JButton btnVolver = new JButton("Volver a Sede");

    Conexion conexion = new Conexion();
    JPanel contentPane;

    String idSede = "";

    public EditarSedeEducOnline() {
    	setTitle("Editar Sede de Educación Online"); // Título de la ventana

        ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Evita que la aplicación se cierre al cerrar la ventana principal
        ventana.setBounds(100, 100, 1200, 750); // Tamaño más grande
        ventana.addWindowListener(this);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);

        ventana.setContentPane(contentPane);
        contentPane.setLayout(null); // Usamos un layout nulo para posicionar los componentes manualmente

        JLabel lblElegir = new JLabel("Elegir la Sede a editar:");
        lblElegir.setBounds(20, 20, 200, 30);
        contentPane.add(lblElegir);

        panelSede = new JPanel();
        panelSede.setLayout(new BoxLayout(panelSede, BoxLayout.Y_AXIS));
        sedes = conexion.obtenerListadoSedesEmpresaEduOnline();
        checkBoxes = new JCheckBox[sedes.size()];
        panelSede.setPreferredSize(new Dimension(300, sedes.size() * 30)); // Ajusta las proporciones al tamaño de Alumna

        for (int i = 0; i < sedes.size(); i++) {
            checkBoxes[i] = new JCheckBox(sedes.get(i));
            panelSede.add(checkBoxes[i]);
        }

        scrollPane = new JScrollPane(panelSede);
        scrollPane.setBounds(20, 65, 1150, 550); // Ajusta el tamaño del scroll panel
        contentPane.add(scrollPane);

        btnVolver = new JButton("Volver a Sede");
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
    private void cargarSedes() {
        panelSede.removeAll();
        sedes = conexion.obtenerListadoSedesEmpresaEduOnline();
        checkBoxes = new JCheckBox[sedes.size()];
        panelSede.setPreferredSize(new Dimension(300, sedes.size() * 30)); // Ajusta las proporciones al tamaño de Sede

        for (int i = 0; i < sedes.size(); i++) {
            checkBoxes[i] = new JCheckBox(sedes.get(i));
            panelSede.add(checkBoxes[i]);
        }

        panelSede.revalidate();
        panelSede.repaint();
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
                JOptionPane.showMessageDialog(ventana, "Solo puedes editar una Sede a la vez.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método sin continuar con la edición
            }
            for (int i = 0; i < checkBoxes.length; i++) {
                if (checkBoxes[i].isSelected()) {
                    String[] contactoDatos = sedes.get(i).split(" \\| ");
                    if (contactoDatos.length < 5) { // Comprobar que hay al menos 5 elementos
                        JOptionPane.showMessageDialog(ventana, "Error en los datos de la Sede.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String id = contactoDatos[0];
                    String resultado = conexion.getDatosEdicionSede(id);
                    String[] datos = resultado.split("-"); // Usar el nuevo delimitador
                    if (datos.length < 5) {
                        JOptionPane.showMessageDialog(ventana, "Error en los datos de la sede.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    idSede = datos[0];

                    JDialog dlgEdicion = new JDialog(this, "Edición", true);
                    dlgEdicion.getContentPane().setLayout(new BorderLayout());
                    dlgEdicion.setSize(600, 400);

                    JPanel panelDatos = new JPanel(new GridLayout(0, 2, 5, 5)); // 0 filas, 2 columnas
                    panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Agrega un borde
                    dlgEdicion.getContentPane().add(panelDatos, BorderLayout.CENTER);

                    JLabel lblTitulo = new JLabel("------- Edición de Sede -------", SwingConstants.CENTER);
                    dlgEdicion.getContentPane().add(lblTitulo, BorderLayout.NORTH);

                    JLabel[] labels = {
                            new JLabel("Nombre:"),
                            new JLabel("Direccion:"),
                            new JLabel("Teléfono:"),
                            new JLabel("Observaciones:"),
                            new JLabel("Empresa:")
                    };

                    JTextField[] fields = {
                            new JTextField(datos[1]),
                            new JTextField(datos[2]),
                            new JTextField(datos[3]),
                            new JTextField(datos[4])
                            };

                    // ComboBox para 'idEmpresaFK'
                    JComboBox<String> comboEmpresas = new JComboBox<>();
                    conexion.rellenarChoiceEmpresaEducOnline(comboEmpresas);
                    comboEmpresas.setSelectedItem(datos[4]);

                    for (int j = 0; j < labels.length; j++) {
                        panelDatos.add(labels[j]);
                        if (j < fields.length) {
                            panelDatos.add(fields[j]);
                        } 
                        else {
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
                            String selectedEmpresa = (String) comboEmpresas.getSelectedItem();
                            int idEmpresaFK = Integer.parseInt(selectedEmpresa.split("-")[0]);

                            String sentencia = "UPDATE SedesEmpresa SET " +
                                    "nombreSede='" + fields[0].getText() + "', " +
                                    "direccion='" + fields[1].getText() + "', " +
                                    "telefono='" + fields[2].getText() + "', " +
                                    "observaciones='" + fields[3].getText() + "', " +                                   
                                    "idEmpresaFK=" + idEmpresaFK + " " +
                                    "WHERE idSede=" + idSede;

                            try {
                                conexion.modificarAlumna(sentencia);
                                cargarSedes();
                                JOptionPane.showMessageDialog(dlgEdicion, "Sede modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                dlgEdicion.setVisible(false);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(dlgEdicion, "Error al modificar la Sede.", "Error", JOptionPane.ERROR_MESSAGE);
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
            SedeEducacionOnline c = new SedeEducacionOnline();
            c.setVisible(true);
        }
    }

    public static void main(String[] args) {
        new EditarSedeEducOnline();
    }
}
