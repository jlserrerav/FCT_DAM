package fct;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Padres extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtBuscar;
    private JTextArea textArea = new JTextArea();
    Conexion conexion;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Padres frame = new Padres();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Padres() {
    	setTitle("Padres"); // Título de la ventana

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 828, 454);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);

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

        // Crear JScrollPane y configurar JTextArea
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 65, 759, 280);
        contentPane.add(scrollPane);
        
        textArea.setEditable(false);
        textArea.setFont(textArea.getFont().deriveFont(14.0f)); // Aumentar el tamaño de la fuente
        scrollPane.setViewportView(textArea);
        conexion.rellenarListadoPadres(textArea);

        JComboBox<String> choiceOrdenar = new JComboBox<>();
        choiceOrdenar.setBounds(87, 28, 109, 18);
        choiceOrdenar.addItem("Padre");
        choiceOrdenar.addItem("Madre");
        choiceOrdenar.addItem("Alumna");
        contentPane.add(choiceOrdenar);
       
        choiceOrdenar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener el índice del elemento seleccionado
                int selectedIndex = choiceOrdenar.getSelectedIndex();
                switch (selectedIndex) {
                    case 0: // Nombre
                        textArea.setText("");
                        conexion.rellenarListadoPadres(textArea);
                        break;
                    case 1: // CIF
                        textArea.setText("");
                        conexion.rellenarListadoPadresPorMadre(textArea);
                        break;
                    case 2: // Dirección
                        textArea.setText("");
                        conexion.rellenarListadoPadresPorAlumna(textArea);
                        break;
                    default:
                        break;
                }
            }
        });


        JButton btnNewButton_1 = new JButton("Volver");
        btnNewButton_1.addActionListener(e -> {
            MenuPrincipal menuPrincipal = new MenuPrincipal();
            menuPrincipal.setVisible(true);
            dispose();  // Cierra la ventana actual
        });
        btnNewButton_1.setBounds(10, 380, 89, 23);
        contentPane.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("Alta");
        btnNewButton_2.addActionListener(e -> {
            AltaPadres menuPrincipal = new AltaPadres();
            menuPrincipal.setVisible(true);
            dispose();  // Cierra la ventana actual
        });
        btnNewButton_2.setBounds(375, 355, 95, 37);
        contentPane.add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("Eliminar");
        btnNewButton_3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		EliminarPadre menuPrincipal = new EliminarPadre();
                menuPrincipal.ventana.setVisible(true);
                dispose();  // Cierra la ventana actual
        	}
        });
        btnNewButton_3.setBounds(645, 355, 141, 35);
        contentPane.add(btnNewButton_3);

        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> {
            EditarPadres menuPrincipal = new EditarPadres();
            menuPrincipal.setVisible(true);
            dispose();  // Cierra la ventana actual
        });
        btnEditar.setBounds(512, 355, 109, 37);
        contentPane.add(btnEditar);
    }
}
