package fct;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class MenuEmpresas extends JFrame implements WindowListener, ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    JButton btnEAdmin = new JButton("Gestión Administrativa");
    JButton btnEEnfermeria = new JButton("Enfermería");
    JButton btnEducacion = new JButton("Educación");
    JButton btnEOnline = new JButton("Educación Online");
    JButton btnAsisDirec = new JButton("Asistencia a la Dirección");
    JButton btnVolver = new JButton("Volver");

    public MenuEmpresas() {
    	setTitle("Menú Empresas"); // Título de la ventana

    	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setTitle("Menú de Empresas");
         setSize(600, 500); // Aumenta el tamaño de la ventana

         contentPane = new JPanel();
         setContentPane(contentPane);
         
	        setLocationRelativeTo(null);


         GroupLayout layout = new GroupLayout(contentPane);
         contentPane.setLayout(layout);
         layout.setAutoCreateGaps(true);
         layout.setAutoCreateContainerGaps(true);

         layout.setHorizontalGroup(
             layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                 .addComponent(btnEAdmin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                 .addComponent(btnEEnfermeria, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                 .addComponent(btnEducacion, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                 .addComponent(btnEOnline, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                 .addComponent(btnAsisDirec, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                 .addComponent(btnVolver)
         );

         layout.setVerticalGroup(
             layout.createSequentialGroup()
                 .addComponent(btnEAdmin, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                 .addComponent(btnEEnfermeria, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                 .addComponent(btnEducacion, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                 .addComponent(btnEOnline, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                 .addComponent(btnAsisDirec, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                 .addComponent(btnVolver)
         );

         btnEAdmin.addActionListener(this);
         btnEEnfermeria.addActionListener(this);
         btnEducacion.addActionListener(this);
         btnEOnline.addActionListener(this);
         btnAsisDirec.addActionListener(this);
         btnVolver.addActionListener(this);

         addWindowListener(this);
         setLocationRelativeTo(null); // Centra la ventana en la pantalla
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnEAdmin)) {
            dispose();
            EmpresasAdministracion admin = new EmpresasAdministracion();
            admin.setVisible(true);
        } else if (e.getSource().equals(btnEEnfermeria)) {
            dispose();
            EmpresasEnfermeria enfermeria = new EmpresasEnfermeria();
            enfermeria.setVisible(true);
        } else if (e.getSource().equals(btnEducacion)) {
            dispose();
            EmpresasEducacion educacion = new EmpresasEducacion();
            educacion.setVisible(true);
        } else if (e.getSource().equals(btnEOnline)) {
            dispose();
            EmpresasEducacionOnline educacionOnline = new EmpresasEducacionOnline();
            educacionOnline.setVisible(true);
        } else if (e.getSource().equals(btnAsisDirec)) {
            dispose();
            EmpresaAsistencia asistenciaDireccion = new EmpresaAsistencia();
            asistenciaDireccion.setVisible(true);
        }else if (e.getSource().equals(btnVolver)) {
            MenuPrincipal menuPrincipal = new MenuPrincipal();
            menuPrincipal.setVisible(true);
            dispose();
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // No se necesita implementar
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // No se necesita implementar
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // No se necesita implementar
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // No se necesita implementar
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // No se necesita implementar
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // No se necesita implementar
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MenuEmpresas frame = new MenuEmpresas();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
