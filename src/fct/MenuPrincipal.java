package fct;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

public class MenuPrincipal extends JFrame implements WindowListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JButton btnAlumnas = new JButton("Alumnas");
	JButton btnEmpresas = new JButton("Empresas");
	private final JButton btnUsuarios = new JButton("Usuarios");

	public MenuPrincipal() {
    	setTitle("Menú Principal"); // Título de la ventana

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
        setLocationRelativeTo(null);


		btnAlumnas.addActionListener(this);

		btnAlumnas.setBounds(144, 118, 131, 23);
		contentPane.add(btnAlumnas);

		btnEmpresas.setBounds(144, 202, 131, 23);
		btnEmpresas.addActionListener(this);
		contentPane.add(btnEmpresas);
		btnUsuarios.setBounds(144, 47, 131, 23);
		btnUsuarios.addActionListener(this);

		contentPane.add(btnUsuarios);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnAlumnas)) {
			dispose();
            Alumnas alumnasFrame = new Alumnas();
            alumnasFrame.setVisible(true);
        } else if (e.getSource().equals(btnEmpresas)) {
			dispose();
            MenuEmpresas empresasFrame = new MenuEmpresas();
            empresasFrame.setVisible(true);
        }
        else if (e.getSource().equals(btnUsuarios)) {
			dispose();
            Usuario u = new Usuario();
            u.ventana.setVisible(true);
        }
	}

	



	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}



	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);		
	}



	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}



	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}



	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}



	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}



	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
