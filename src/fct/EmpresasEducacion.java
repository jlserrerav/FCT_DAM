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

public class EmpresasEducacion extends JFrame {

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
                    EmpresasEducacion frame = new EmpresasEducacion();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public EmpresasEducacion() {
    	setTitle("Empresa Educación"); // Título de la ventana

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600); // Ajuste de las dimensiones de la ventana
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
                EmpresasEducacion cEmprAdmin = new EmpresasEducacion();
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
                ContactoEmpresaEducacion cEmprAdmin = new ContactoEmpresaEducacion();
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
                SedeEducacion sedeAdmin = new SedeEducacion();
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
                PracticaEduc practicaAdmin = new PracticaEduc();
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
        conexion.rellenarListadoEmpresaEducacion(textArea);
        
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
                        conexion.rellenarListadoEmpresaEducacion(textArea);
                        break;
                    case 1: // CIF
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaEducacionPorCIF(textArea);
                        break;
                    case 2: // Dirección
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaEducacionPorDireccion(textArea);
                        break;
                    case 3: // Localidad
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaEducacionPorLocalidad(textArea);
                        break;
                    case 4: // Representante
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaEducacionPorRepresentante(textArea);
                        break;
                    case 5: // DNI Representante
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaEducacionPorDNIRepresentante(textArea);
                        break;
                    case 6: // Email
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaEducacionPorEmail(textArea);
                        break;
                    case 7: // Teléfono
                        textArea.setText("");
                        conexion.rellenarListadoEmpresaEducacionPorTelefono(textArea);
                        break;
                    default:
                        break;
                }
            }
        });
        
    
        
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(20, 450, 960, 60); // Ajuste de las dimensiones del panel de botones
        contentPane.add(buttonPanel);
        buttonPanel.setLayout(null);
        
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuPrincipal menuPrincipal = new MenuPrincipal();
                menuPrincipal.setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });
        btnVolver.setBounds(20, 10, 150, 40);
        buttonPanel.add(btnVolver);
        
        JButton btnAlta = new JButton("Alta");
        btnAlta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AltaEmpresaEducacion menuPrincipal = new AltaEmpresaEducacion();
                menuPrincipal.setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });
        btnAlta.setBounds(200, 10, 150, 40);
        buttonPanel.add(btnAlta);
        
        JButton btnCrearExcel = new JButton("Eliminar");
        btnCrearExcel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EliminarEmpresaEducacion menuPrincipal = new EliminarEmpresaEducacion();
                menuPrincipal.ventana.setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });
        btnCrearExcel.setBounds(380, 10, 150, 40);
        buttonPanel.add(btnCrearExcel);
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditarEmpresaEducacion menuPrincipal = new EditarEmpresaEducacion();
                menuPrincipal.ventana.setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });
        btnEditar.setBounds(560, 10, 150, 40);
        buttonPanel.add(btnEditar);
    }
}
