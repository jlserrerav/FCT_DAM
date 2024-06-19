package fct;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
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

public class EmpresasEnfermeria extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtBuscar;
    private JTextArea textArea = new JTextArea();

    Conexion conexion;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EmpresasEnfermeria frame = new EmpresasEnfermeria();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public EmpresasEnfermeria() {
    	setTitle("Empresa Enfermeria"); // Título de la ventana

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600); // Ajuste de las dimensiones del JFrame
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocationRelativeTo(null);

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        conexion = new Conexion();


        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 986, 23); // Ajuste de las dimensiones de la barra de menú
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
        
        // Crear JScrollPane y configurar JTextArea
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 65, 959, 380); // Ajuste de las dimensiones del JScrollPane
        contentPane.add(scrollPane);
        
        textArea.setEditable(false);
        textArea.setFont(textArea.getFont().deriveFont(14.0f)); // Aumentar el tamaño de la fuente
        scrollPane.setViewportView(textArea);

        // Mostrar inicialmente el listado ordenado por nombre
        conexion.rellenarListadoEmpresaEnfermeria(textArea);
        
        JComboBox<String> choiceOrdenar = new JComboBox<>();
        choiceOrdenar.setBounds(87, 28, 109, 18);
        choiceOrdenar.addItem("Nombre");
        choiceOrdenar.addItem("CIF");
        choiceOrdenar.addItem("Dirección");
        choiceOrdenar.addItem("Localidad");
        choiceOrdenar.addItem("Representante");
        choiceOrdenar.addItem("DNI Representante");
        choiceOrdenar.addItem("Email");
        choiceOrdenar.addItem("Teléfono");
        contentPane.add(choiceOrdenar);
       
        choiceOrdenar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener el índice del elemento seleccionado
                int selectedIndex = choiceOrdenar.getSelectedIndex();
                switch (selectedIndex) {
                    case 0: // Nombre
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaEnfermeria(textArea);
                        break;
                    case 1: // CIF
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaEnfermeriaPorCIF(textArea);
                        break;
                    case 2: // Dirección
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaEnfermeriaPorDireccion(textArea);
                        break;
                    case 3: // Localidad
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaEnfermeriaPorLocalidad(textArea);
                        break;
                    case 4: // Representante
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaEnfermeriaPorRepresentante(textArea);
                        break;
                    case 5: // DNI Representante
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaEnfermeriaPorDNIRepresentante(textArea);
                        break;
                    case 6: // Email
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaEnfermeriaPorEmail(textArea);
                        break;
                    case 7: // Teléfono
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaEnfermeriaPorTelefono(textArea);
                        break;
                    default:
                        break;
                }
            }
        });
        
        

        
        
        JButton btnNewButton_1 = new JButton("Volver");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuPrincipal menuPrincipal = new MenuPrincipal();
                menuPrincipal.setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });
        btnNewButton_1.setBounds(10, 470, 89, 23); // Ajuste de posición y tamaño del botón
        contentPane.add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("Alta");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AltaEmpresaEnfermeria menuPrincipal = new AltaEmpresaEnfermeria();
                menuPrincipal.setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });
        btnNewButton_2.setBounds(375, 450, 95, 37); // Ajuste de posición y tamaño del botón
        contentPane.add(btnNewButton_2);
        
        JButton btnNewButton_3 = new JButton("Eliminar");
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EliminarEmpresaEnfermeria menuPrincipal = new EliminarEmpresaEnfermeria();
                menuPrincipal.ventana.setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });
        btnNewButton_3.setBounds(645, 450, 141, 35); // Ajuste de posición y tamaño del botón
        contentPane.add(btnNewButton_3);
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditarEmpresaEnfermeria menuPrincipal = new EditarEmpresaEnfermeria();
                menuPrincipal.ventana.setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });
        btnEditar.setBounds(512, 450, 109, 37); // Ajuste de posición y tamaño del botón
        contentPane.add(btnEditar);
    }
}
