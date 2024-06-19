package fct;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class EmpresasAdministracion extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtBuscar;
    JTextArea textArea = new JTextArea();

    Conexion conexion;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EmpresasAdministracion frame = new EmpresasAdministracion();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public EmpresasAdministracion() {
    	setTitle("Empresa Administración"); // Título de la ventana

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 575); // Ajuste de las dimensiones de la ventana
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
                EmpresasAdministracion cEmprAdmin = new EmpresasAdministracion();
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
                ContactoEmpresaAdministracion cEmprAdmin = new ContactoEmpresaAdministracion();
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
                SedeEmpresaAdministracion sedeAdmin = new SedeEmpresaAdministracion();
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
                PracticaAdmin practicaAdmin = new PracticaAdmin();
                practicaAdmin.setVisible(true);
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
        conexion.rellenarListadoEmpresaAdministracion(textArea);
        
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
                        conexion.rellenarListadoEmpresaAdministracion(textArea);
                        break;
                    case 1: // CIF
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaAdministracionPorCIF(textArea);
                        break;
                    case 2: // Dirección
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaAdministracionPorDireccion(textArea);
                        break;
                    case 3: // Localidad
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaAdministracionPorLocalidad(textArea);
                        break;
                    case 4: // Representante
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaAdministracionPorRepresentante(textArea);
                        break;
                    case 5: // DNI Representante
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaAdministracionPorDNIRepresentante(textArea);
                        break;
                    case 6: // Email
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaAdministracionPorEmail(textArea);
                        break;
                    case 7: // Teléfono
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaAdministracionPorTelefono(textArea);
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
                AltaEmpresaAdministracion menuPrincipal = new AltaEmpresaAdministracion();
                menuPrincipal.setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });
        btnNewButton_2.setBounds(511, 455, 95, 37); // Ajuste de posición y tamaño del botón
        contentPane.add(btnNewButton_2);
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EliminarEmpresaAdmin menuPrincipal = new EliminarEmpresaAdmin();
                menuPrincipal.setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });
        btnEliminar.setBounds(801, 455, 141, 35); // Ajuste de posición y tamaño del botón
        contentPane.add(btnEliminar);
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditarEmpresaAdministracion menuPrincipal = new EditarEmpresaAdministracion();
                menuPrincipal.ventana.setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });
        btnEditar.setBounds(653, 455, 109, 37); // Ajuste de posición y tamaño del botón
        contentPane.add(btnEditar);
    }
}
