package fct;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AntiguasAlumnas extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtBuscar;
    private JPanel panelAlumnas;
    private JScrollPane scrollPane;
    private List<JCheckBox> checkBoxes;

    Conexion conexion;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AntiguasAlumnas frame = new AntiguasAlumnas();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AntiguasAlumnas() {
    	setTitle("Alumnas Antiguas"); // Título de la ventana
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Aumenta el tamaño de la ventana
        setBounds(100, 100, 1200, 800); // Tamaño más grande
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        conexion = new Conexion();

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 786, 23);
        contentPane.add(menuBar);

        JMenu mnAlumnas = new JMenu("Alumnas");
        menuBar.add(mnAlumnas);
        JMenuItem mniVerAlumnas = new JMenuItem("Ver Alumnas");
        mniVerAlumnas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Alumnas alumna = new Alumnas();
                alumna.setVisible(true);
            }
        });
        mnAlumnas.add(mniVerAlumnas);

        JMenu mniAntiguasAlumnas = new JMenu("Antiguas Alumnas");
        menuBar.add(mniAntiguasAlumnas);
        JMenuItem mniVerAntiguasAlumnas = new JMenuItem("Ver Antiguas Alumnas");
        mniVerAntiguasAlumnas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AntiguasAlumnas antiguaAlumna = new AntiguasAlumnas();
                antiguaAlumna.setVisible(true);
            }
        });
        mniAntiguasAlumnas.add(mniVerAntiguasAlumnas);

        JMenu mniAlumnasErasmus = new JMenu("Alumnas en Erasmus");
        menuBar.add(mniAlumnasErasmus);
        JMenuItem mniVerAlumnasdeErasmus = new JMenuItem("Ver Alumnas de Erasmus");
        mniVerAlumnasdeErasmus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AlumnasErasmus alumnasDeErasmus = new AlumnasErasmus();
                alumnasDeErasmus.setVisible(true);
            }
        });
        mniAlumnasErasmus.add(mniVerAlumnasdeErasmus);


        JMenu mniAlumnasBaja = new JMenu("Alumnas de Baja");
        menuBar.add(mniAlumnasBaja);
        JMenuItem mniVerAlumnasdeBaja = new JMenuItem("Ver Alumnas de Baja");
        mniVerAlumnasdeBaja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AlumnasBaja alumnasDeBaja = new AlumnasBaja();
                alumnasDeBaja.setVisible(true);
            }
        });
        mniAlumnasBaja.add(mniVerAlumnasdeBaja);
        
        JMenu mniPadres = new JMenu("Padres");
        menuBar.add(mniPadres);
        JMenuItem mniVerPadres = new JMenuItem("Ver Padres");
        mniVerPadres.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Padres padres = new Padres();
                padres.setVisible(true);
            }
        });
        mniPadres.add(mniVerPadres);
        
        JLabel lblNewLabel = new JLabel("Ordenar por:");
        lblNewLabel.setBounds(10, 30, 66, 14);
        contentPane.add(lblNewLabel);



        JComboBox<String> choiceOrdenar = new JComboBox<>();
        choiceOrdenar.setBounds(87, 28, 109, 18);
        choiceOrdenar.addItem("Nombre");
        choiceOrdenar.addItem("Apellido");
        choiceOrdenar.addItem("Dni");
        choiceOrdenar.addItem("Dirección");
        choiceOrdenar.addItem("Teléfono");
        choiceOrdenar.addItem("Email Personal");
        choiceOrdenar.addItem("Email Centro");
        choiceOrdenar.addItem("Ciclo");
        choiceOrdenar.addItem("Curso");
        choiceOrdenar.addItem("Año de Inicio");
        choiceOrdenar.addItem("Año de Fin");
        choiceOrdenar.addItem("Fecha de Nacimiento");
        contentPane.add(choiceOrdenar);

        choiceOrdenar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = choiceOrdenar.getSelectedIndex();
                panelAlumnas.removeAll();
                checkBoxes.clear();
                if (selectedIndex == 0) {
                	rellenarListadoAlumnasConCheckBox();
                } else if (selectedIndex == 1) {
                    rellenarListadoAlumnasConCheckBoxPorApellido();
                } else if (selectedIndex == 2) {
                    rellenarListadoAlumnasConCheckBoxPorDni();
                } else if (selectedIndex == 3) {
                    rellenarListadoAlumnasConCheckBoxPorDireccion();
                } else if (selectedIndex == 4) {
                    rellenarListadoAlumnasConCheckBoxPorTelefono();
                } else if (selectedIndex == 5) {
                    rellenarListadoAlumnasConCheckBoxPorEmailPersonal();
                } else if (selectedIndex == 6) {
                    rellenarListadoAlumnasConCheckBoxPorEmailCentro();
                } else if (selectedIndex == 7) {
                    rellenarListadoAlumnasConCheckBoxPorCiclo();
                } else if (selectedIndex == 8) {
                    rellenarListadoAlumnasConCheckBoxPorCurso();
                } else if (selectedIndex == 9) {
                    rellenarListadoAlumnasConCheckBoxPorAñoInicio();
                } else if (selectedIndex == 10) {
                    rellenarListadoAlumnasConCheckBoxPorAñoFin();
                } else if (selectedIndex == 11) {
                    rellenarListadoAlumnasConCheckBoxPorFechaNacimiento();
                }
                panelAlumnas.revalidate();
                panelAlumnas.repaint();
            }
        });

     
    

        JButton btnNewButton_1 = new JButton("Volver");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuPrincipal mnuVolver = new MenuPrincipal();
                mnuVolver.setVisible(true);
            }
        });
        btnNewButton_1.setBounds(10, 690, 117, 53); // Ajusta la posición del botón

        contentPane.add(btnNewButton_1);


     // Panel para las alumnas con scroll
        panelAlumnas = new JPanel();
        panelAlumnas.setLayout(new BoxLayout(panelAlumnas, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(panelAlumnas);
        scrollPane.setBounds(20, 65, 1150, 550); // Ajusta el tamaño del scroll panel
        contentPane.add(scrollPane);

        checkBoxes = new ArrayList<>();
        rellenarListadoAlumnasConCheckBox();

        JButton btnMoverAlumnas = new JButton("Mover a Alumnas Activas");
        btnMoverAlumnas.setBounds(905, 690, 221, 53); // Ajusta la posición del botón
        btnMoverAlumnas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botón 'Mover a Alumnas Activas' presionado.");
                moverAlumnas();
            }
        });
        contentPane.add(btnMoverAlumnas);

        JButton btnNewButton_4_1 = new JButton("Mover a Alumnas de Baja");
        btnNewButton_4_1.setBounds(612, 385, 174, 19);
        contentPane.add(btnNewButton_4_1);
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
                EditarAlumnaAntigua mnuVolver = new EditarAlumnaAntigua();
                mnuVolver.setVisible(true);
            }
        });
        btnEditar.setBounds(658, 690, 221, 53);
        contentPane.add(btnEditar);
    }

    private void rellenarListadoAlumnasConCheckBox() {
        List<String> alumnas = conexion.obtenerListadoAntiguasAlumnas();
        for (String alumna : alumnas) {
            JCheckBox checkBox = new JCheckBox(alumna);
            String[] datosAlumna = alumna.split(" ");
            String idAlumna = datosAlumna[0];
            checkBox.setActionCommand(idAlumna);
            checkBoxes.add(checkBox);
            panelAlumnas.add(checkBox);
        }
    }

    private void rellenarListadoAlumnasConCheckBoxPorApellido() {
        List<String> alumnas = conexion.obtenerListadoAlumnasAntiguasOrdenadasPorApellido();
        for (String alumna : alumnas) {
            JCheckBox checkBox = new JCheckBox(alumna);
            String[] datosAlumna = alumna.split(" ");
            String idAlumna = datosAlumna[0];
            checkBox.setActionCommand(idAlumna);
            checkBoxes.add(checkBox);
            panelAlumnas.add(checkBox);
        }
    }

    private void rellenarListadoAlumnasConCheckBoxPorDni() {
        List<String> alumnas = conexion.obtenerListadoAlumnasAntiguasOrdenadasPorDni();
        for (String alumna : alumnas) {
            JCheckBox checkBox = new JCheckBox(alumna);
            String[] datosAlumna = alumna.split(" ");
            String idAlumna = datosAlumna[0];
            checkBox.setActionCommand(idAlumna);
            checkBoxes.add(checkBox);
            panelAlumnas.add(checkBox);
        }
    }

    private void rellenarListadoAlumnasConCheckBoxPorDireccion() {
        List<String> alumnas = conexion.obtenerListadoAlumnasAntiguasOrdenadasPorDireccion();
        for (String alumna : alumnas) {
            JCheckBox checkBox = new JCheckBox(alumna);
            String[] datosAlumna = alumna.split(" ");
            String idAlumna = datosAlumna[0];
            checkBox.setActionCommand(idAlumna);
            checkBoxes.add(checkBox);
            panelAlumnas.add(checkBox);
        }
    }

    private void rellenarListadoAlumnasConCheckBoxPorTelefono() {
        List<String> alumnas = conexion.obtenerListadoAlumnasAntiguasOrdenadasPorTelefono();
        for (String alumna : alumnas) {
            JCheckBox checkBox = new JCheckBox(alumna);
            String[] datosAlumna = alumna.split(" ");
            String idAlumna = datosAlumna[0];
            checkBox.setActionCommand(idAlumna);
            checkBoxes.add(checkBox);
            panelAlumnas.add(checkBox);
        }
    }

    private void rellenarListadoAlumnasConCheckBoxPorEmailPersonal() {
        List<String> alumnas = conexion.obtenerListadoAlumnasAntiguasOrdenadasPorEmailPersonal();
        for (String alumna : alumnas) {
            JCheckBox checkBox = new JCheckBox(alumna);
            String[] datosAlumna = alumna.split(" ");
            String idAlumna = datosAlumna[0];
            checkBox.setActionCommand(idAlumna);
            checkBoxes.add(checkBox);
            panelAlumnas.add(checkBox);
        }
    }

    private void rellenarListadoAlumnasConCheckBoxPorEmailCentro() {
        List<String> alumnas = conexion.obtenerListadoAlumnasAntiguasOrdenadasPorEmailCentro();
        for (String alumna : alumnas) {
            JCheckBox checkBox = new JCheckBox(alumna);
            String[] datosAlumna = alumna.split(" ");
            String idAlumna = datosAlumna[0];
            checkBox.setActionCommand(idAlumna);
            checkBoxes.add(checkBox);
            panelAlumnas.add(checkBox);
        }
    }

    private void rellenarListadoAlumnasConCheckBoxPorCiclo() {
        List<String> alumnas = conexion.obtenerListadoAlumnasAntiguasOrdenadasPorCiclo();
        for (String alumna : alumnas) {
            JCheckBox checkBox = new JCheckBox(alumna);
            String[] datosAlumna = alumna.split(" ");
            String idAlumna = datosAlumna[0];
            checkBox.setActionCommand(idAlumna);
            checkBoxes.add(checkBox);
            panelAlumnas.add(checkBox);
        }
    }

    private void rellenarListadoAlumnasConCheckBoxPorCurso() {
        List<String> alumnas = conexion.obtenerListadoAlumnasAntiguasOrdenadasPorCurso();
        for (String alumna : alumnas) {
            JCheckBox checkBox = new JCheckBox(alumna);
            String[] datosAlumna = alumna.split(" ");
            String idAlumna = datosAlumna[0];
            checkBox.setActionCommand(idAlumna);
            checkBoxes.add(checkBox);
            panelAlumnas.add(checkBox);
        }
    }

    private void rellenarListadoAlumnasConCheckBoxPorAñoInicio() {
        List<String> alumnas = conexion.obtenerListadoAlumnasAntiguaOrdenadasPorAñoInicio();
        for (String alumna : alumnas) {
            JCheckBox checkBox = new JCheckBox(alumna);
            String[] datosAlumna = alumna.split(" ");
            String idAlumna = datosAlumna[0];
            checkBox.setActionCommand(idAlumna);
            checkBoxes.add(checkBox);
            panelAlumnas.add(checkBox);
        }
    }

    private void rellenarListadoAlumnasConCheckBoxPorAñoFin() {
        List<String> alumnas = conexion.obtenerListadoAlumnasAntiguasOrdenadasPorAñoFin();
        for (String alumna : alumnas) {
            JCheckBox checkBox = new JCheckBox(alumna);
            String[] datosAlumna = alumna.split(" ");
            String idAlumna = datosAlumna[0];
            checkBox.setActionCommand(idAlumna);
            checkBoxes.add(checkBox);
            panelAlumnas.add(checkBox);
        }
    }

    private void rellenarListadoAlumnasConCheckBoxPorFechaNacimiento() {
        List<String> alumnas = conexion.obtenerListadoAlumnasAntiguasOrdenadasPorFechaNacimiento();
        for (String alumna : alumnas) {
            JCheckBox checkBox = new JCheckBox(alumna);
            String[] datosAlumna = alumna.split(" ");
            String idAlumna = datosAlumna[0];
            checkBox.setActionCommand(idAlumna);
            checkBoxes.add(checkBox);
            panelAlumnas.add(checkBox);
        }
    }

    private void refrescarPanelAlumnas() {
        panelAlumnas.removeAll();
        checkBoxes.clear();
        rellenarListadoAlumnasConCheckBox();
        panelAlumnas.revalidate();
        panelAlumnas.repaint();
    }

    private void moverAlumnas() {
        System.out.println("Verificando checkboxes seleccionados...");
        for (JCheckBox checkBox : checkBoxes) {
            System.out.println("Checkbox: " + checkBox.getText() + " seleccionado: " + checkBox.isSelected());
            if (checkBox.isSelected()) {
                String idAlumna = checkBox.getActionCommand();
                System.out.println("Alumna seleccionada con ID: " + idAlumna);
                int resultado = conexion.moverAlumna(idAlumna);
                if (resultado == 0) {
                    System.out.println("Alumna con ID " + idAlumna + " movida a activas correctamente.");
                    refrescarPanelAlumnas();
                } else {
                    System.out.println("Error al mover alumna con ID " + idAlumna);
                }
            }
        }
    }
}
