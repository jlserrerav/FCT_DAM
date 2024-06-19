package fct;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Login implements WindowListener, ActionListener {
    Frame ventanaLogin = new Frame("Login");
    Dialog dlgMensaje = new Dialog(ventanaLogin, "Error", true);

    Label lblUsuario = new Label("Usuario:");
    Label lblClave = new Label("Clave:");
    Label lblMensaje = new Label("Credenciales incorrectas");

    TextField txtUsuario = new TextField(10);
    TextField txtClave = new TextField(10);

    Button btnAcceder = new Button("Acceder");

    Conexion conexion = new Conexion();

    Login() {
        ventanaLogin.setLayout(new FlowLayout());
        ventanaLogin.addWindowListener(this);

        ventanaLogin.add(lblUsuario);
        ventanaLogin.add(txtUsuario);
        ventanaLogin.add(lblClave);
        txtClave.setEchoChar('*');
        ventanaLogin.add(txtClave);
        btnAcceder.addActionListener(this);
        ventanaLogin.add(btnAcceder);

        ventanaLogin.setSize(220, 130);
        ventanaLogin.setResizable(false);
        ventanaLogin.setLocationRelativeTo(null);
        ventanaLogin.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowClosing(WindowEvent e) {
        if (dlgMensaje.isActive()) {
            dlgMensaje.setVisible(false);
        } else {
            System.exit(0);
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
        if (e.getSource().equals(btnAcceder)) {
            String usuario = txtUsuario.getText();
            String clave = txtClave.getText();
            if (conexion.autenticarUsuario(usuario, clave)) {
                MenuPrincipal menu = new MenuPrincipal();
                menu.setVisible(true);
                ventanaLogin.dispose();
            } else {
                dlgMensaje.setLayout(new FlowLayout());
                dlgMensaje.addWindowListener(this);
                dlgMensaje.add(lblMensaje);
                dlgMensaje.setSize(210, 80);
                dlgMensaje.setResizable(false);
                dlgMensaje.setLocationRelativeTo(null);
                dlgMensaje.setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
