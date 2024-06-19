package fct;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PracticaEnf extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtBuscar;
    private JTextArea textArea = new JTextArea();
    Conexion conexion;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PracticaEnf frame = new PracticaEnf();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PracticaEnf() {
    	setTitle("Prácticas de Enfermeria"); // Título de la ventana

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

        JMenu mnEmpresa = new JMenu("Empresa");
        menuBar.add(mnEmpresa);
        JMenuItem mniVerEmAdmin = new JMenuItem("Ver Empresas");
        mniVerEmAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                EmpresasEnfermeria cEmprAdmin = new EmpresasEnfermeria();
                cEmprAdmin.setVisible(true);
            }
        });
        mnEmpresa.add(mniVerEmAdmin);
        JMenu mnuContacto = new JMenu("Contactos Empresa");
        menuBar.add(mnuContacto);
        JMenuItem mniVercEmprAdmin = new JMenuItem("Ver Contactos Empresa");
        mniVercEmprAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ContacoEmpresaEnfermeria cEmprAdmin = new ContacoEmpresaEnfermeria();
                cEmprAdmin.setVisible(true);
            }
        });
        mnuContacto.add(mniVercEmprAdmin);

        JMenu mnuSede = new JMenu("Sede");
        menuBar.add(mnuSede);
        JMenuItem mniVerSedeAdmin = new JMenuItem("Ver Sedes");
        mniVerSedeAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SedeEnfermeria sedeAdmin = new SedeEnfermeria();
                sedeAdmin.setVisible(true);
            }
        });
        mnuSede.add(mniVerSedeAdmin);
        
        JMenu mniPractica = new JMenu("Prácticas");
        menuBar.add(mniPractica);
        JMenuItem mniVerPractica = new JMenuItem("Ver Practicas");
        mniVerPractica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                PracticaEnf cEmprAdmin = new PracticaEnf();
                cEmprAdmin.setVisible(true);
            }
        });
        mniPractica.add(mniVerPractica);
        JLabel lblNewLabel = new JLabel("Ordenar por:");
        lblNewLabel.setBounds(10, 30, 66, 14);
        contentPane.add(lblNewLabel);

        // Creación del JScrollPane
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 65, 759, 280); // Establecer dimensiones del JScrollPane
        contentPane.add(scrollPane);
        textArea.setFont(textArea.getFont().deriveFont(14.0f)); // Aumentar el tamaño de la fuente

        // Asignación del JTextArea al JScrollPane
        textArea.setEditable(false);
        scrollPane.setViewportView(textArea);

        // Llenar inicialmente el JTextArea con datos de prácticas
     // Llenar inicialmente el JTextArea con datos de prácticas
        conexion.rellenarListadoPracticasEnfermeriaPorNombreAlumna(textArea);

        JComboBox<String> choiceOrdenar = new JComboBox<>();
        choiceOrdenar.setBounds(87, 28, 109, 18);
        choiceOrdenar.addItem("Alumna");
        choiceOrdenar.addItem("Sede");
        choiceOrdenar.addItem("Contacto");
        choiceOrdenar.addItem("Horario");
        contentPane.add(choiceOrdenar);
       
        choiceOrdenar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener el índice del elemento seleccionado
                int selectedIndex = choiceOrdenar.getSelectedIndex();
                switch (selectedIndex) {
                    case 0: // Nombre
                        textArea.setText("");
                        conexion.rellenarListadoPracticasEnfermeriaPorNombreAlumna(textArea);
                        break;
                    case 1: // CIF
                        textArea.setText("");
                        conexion.rellenarListadoPracticasEnfermeriaPorNombreSede(textArea);
                        break;
                    case 2: // Dirección
                        textArea.setText("");
                        conexion.rellenarListadoPracticasEnfermeriaPorNombreContacto(textArea);
                        break;
                    case 3: // Localidad
                        textArea.setText("");
                        conexion.rellenarListadoPracticasEnfermeriaPorHorario(textArea);
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
            AltaPracticaEnfermeria menuPrincipal = new AltaPracticaEnfermeria();
            menuPrincipal.setVisible(true);
            dispose();  // Cierra la ventana actual
        });
        btnNewButton_2.setBounds(375, 355, 95, 37);
        contentPane.add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("Eliminar");
        btnNewButton_3.addActionListener(e -> {
            EliminarPracticaEnf menuPrincipal = new EliminarPracticaEnf();
            menuPrincipal.ventana.setVisible(true);
            dispose();  // Cierra la ventana actual
        });
        btnNewButton_3.setBounds(645, 355, 141, 35);
        contentPane.add(btnNewButton_3);

        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> {
            EditarPracticaEnf menuPrincipal = new EditarPracticaEnf();
            menuPrincipal.ventana.setVisible(true);
            dispose();  // Cierra la ventana actual
        });
        btnEditar.setBounds(512, 355, 109, 37);
        contentPane.add(btnEditar);
    }
}
