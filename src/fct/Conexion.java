package fct;

import java.awt.Choice;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextArea;

public class Conexion {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/Practicas";
	String login = "root";
	String password = "usuario";

	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	Conexion() {
		connection = this.conectar();
	}

	public Connection conectar() {
		try {
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			// Establecer la conexión con la BD Empresa
			Connection conn = DriverManager.getConnection(url, login, password);
			System.out.println("Conexión exitosa a la base de datos.");
			return conn;
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error 1-" + cnfe.getMessage());
		} catch (SQLException sqle) {
			System.out.println("Error 2-" + sqle.getMessage());
		}
		return null;
	}
	public int comprobarCredenciales(String usuario, String clave)
	{
		String cadena = "SELECT * FROM usuarios WHERE nombreUsuario = '"+ usuario + "' AND claveUsuario = SHA2('" + clave + "',256);";
		try
		{
			// Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sentencia SQL
			rs = statement.executeQuery(cadena);
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 3-"+sqle.getMessage());
		}
		return -1;
	}

	public int altaAlumna(String sentencia) {
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
			System.out.println("Alta de alumna ejecutada correctamente.");
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error 3-" + sqle.getMessage());
			return 1;
		}
	}
	public int altaUsuario(String nombre, String clave) {
		String sql = "INSERT INTO Usuarios (nombreUsuario, claveUsuario) VALUES (?, ?)";
		try (Connection con = conectar();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, nombre);
			pstmt.setString(2, clave);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public boolean autenticarUsuario(String nombre, String clave) {
		String query = "SELECT * FROM Usuarios WHERE nombreUsuario = ? AND claveUsuario = ?";
		try (Connection con = conectar();
				PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, nombre);
			pstmt.setString(2, clave);
			return pstmt.executeQuery().next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public String getDatosEdicionAlumna(String idAlumna) {
		String resultado = "";
		String sql = "SELECT * FROM Alumnas WHERE idAlumna = " + idAlumna;
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				resultado = rs.getString("idAlumna") + "|" +
						rs.getString("nombreAlumna") + "|" +
						rs.getString("primerApellidoAlumna") + "|" +
						rs.getString("segundoApellidoAlumna") + "|" +
						rs.getString("dniAlumna") + "|" +
						rs.getString("direccionAlumna") + "|" +
						rs.getString("telefonoAlumna") + "|" +
						rs.getString("emailPersonalAlumna") + "|" +
						rs.getString("emailCentroAlumna") + "|" +
						rs.getString("cicloFormativoAlumna") + "|" +
						rs.getInt("cursoAlumna") + "|" +
						rs.getInt("añoInicioAlumna") + "|" +
						rs.getInt("añoFinAlumna") + "|" +
						rs.getDate("fechaNacimientoAlumna").toString();
			}
		} catch (SQLException e) {
			System.out.println("Error al obtener datos de la alumna: " + e.getMessage());
		}
		return resultado;
	}



	public List<String> obtenerListadoAlumnas() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 order by nombreAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasOrdenadasPorApellido() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 ORDER BY primerApellidoAlumna, segundoApellidoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " " +
						resultado.getString("primerApellidoAlumna") + " | " +
						resultado.getString("segundoApellidoAlumna") + " " +
						resultado.getString("nombreAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por apellido obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasOrdenadasPorDni() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 ORDER BY dniAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasOrdenadasPorDireccion() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 ORDER BY direccionAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasOrdenadasPorTelefono() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 ORDER BY telefonoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasOrdenadasPorEmailPersonal() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 ORDER BY emailPersonalAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasOrdenadasPorEmailCentro() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 ORDER BY emailCentroAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasOrdenadasPorCiclo() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 ORDER BY cicloFormativoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasOrdenadasPorCurso() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 ORDER BY cursoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasOrdenadasPorAñoInicio() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 ORDER BY añoInicioAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasOrdenadasPorAñoFin() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 ORDER BY añoFinAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasOrdenadasPorFechaNacimiento() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 ORDER BY fechaNacimientoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("fechaNacimientoAlumna") + " | " +
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}


	public List<String> obtenerListadoAlumnasErasmus() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 and erasmusAlumna = 1 order by nombreAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasErasmusOrdenadasPorApellido() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 and erasmusAlumna = 1 ORDER BY primerApellidoAlumna, segundoApellidoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " " +
						resultado.getString("primerApellidoAlumna") + " | " +
						resultado.getString("segundoApellidoAlumna") + " " +
						resultado.getString("nombreAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por apellido obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasErasmusOrdenadasPorDni() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 and erasmusAlumna = 1 ORDER BY dniAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasErasmusOrdenadasPorDireccion() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 and erasmusAlumna = 1 ORDER BY direccionAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasErasmusOrdenadasPorTelefono() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 and erasmusAlumna = 1 ORDER BY telefonoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasErasmusOrdenadasPorEmailPersonal() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 and erasmusAlumna = 1 ORDER BY emailPersonalAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasErasmusOrdenadasPorEmailCentro() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 and erasmusAlumna = 1 ORDER BY emailCentroAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasErasmusOrdenadasPorCiclo() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 and erasmusAlumna = 1 ORDER BY cicloFormativoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasErasmusOrdenadasPorCurso() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 and erasmusAlumna = 1 ORDER BY cursoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasErasmusOrdenadasPorAñoInicio() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 and erasmusAlumna = 1 ORDER BY añoInicioAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasErasmusOrdenadasPorAñoFin() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 and erasmusAlumna = 1 ORDER BY añoFinAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasErasmusOrdenadasPorFechaNacimiento() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 0 and erasmusAlumna = 1 ORDER BY fechaNacimientoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("fechaNacimientoAlumna") + " | " +
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	} 
	public List<String> obtenerListadoAlumnasBaja() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 0 and bajaAlumna = 1 ORDER BY nombreAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasBajaOrdenadasPorApellido() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 0 and bajaAlumna=1 ORDER BY primerApellidoAlumna, segundoApellidoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " " +
						resultado.getString("primerApellidoAlumna") + " | " +
						resultado.getString("segundoApellidoAlumna") + " " +
						resultado.getString("nombreAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por apellido obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasBajaOrdenadasPorDni() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 0 and bajaAlumna = 1 ORDER BY dniAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por DNI obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasBajaOrdenadasPorDireccion() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 0 and bajaAlumna = 1 ORDER BY direccionAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dirección obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasBajaOrdenadasPorTelefono() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 0 and bajaAlumna = 1 ORDER BY telefonoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por teléfono obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasBajaOrdenadasPorEmailPersonal() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 0 and bajaAlumna = 1 ORDER BY emailPersonalAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por email personal obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasBajaOrdenadasPorEmailCentro() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 0 and bajaAlumna = 1 ORDER BY emailCentroAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por email del centro obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasBajaOrdenadasPorCiclo() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 0 and bajaAlumna = 1 ORDER BY cicloFormativoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por ciclo formativo obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasBajaOrdenadasPorCurso() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 0 and bajaAlumna = 1 ORDER BY cursoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por curso obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasBajaOrdenadasPorAñoInicio() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 1 ORDER BY añoInicioAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasBajaOrdenadasPorAñoFin() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 1 ORDER BY añoFinAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasBajaOrdenadasPorFechaNacimiento() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 0 and bajaAlumna = 1 ORDER BY fechaNacimientoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("fechaNacimientoAlumna") + " | " +
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAntiguasAlumnas() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 1 and bajaAlumna = 0 ORDER BY nombreAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasAntiguasOrdenadasPorApellido() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 1 and bajaAlumna = 0 ORDER BY primerApellidoAlumna, segundoApellidoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " " +
						resultado.getString("primerApellidoAlumna") + " | " +
						resultado.getString("segundoApellidoAlumna") + " " +
						resultado.getString("nombreAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por apellido obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasAntiguasOrdenadasPorDni() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 1 and bajaAlumna = 0 ORDER BY dniAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por DNI obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasAntiguasOrdenadasPorDireccion() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 1 and bajaAlumna = 0 ORDER BY direccionAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dirección obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasAntiguasOrdenadasPorTelefono() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 1 and bajaAlumna = 0 ORDER BY telefonoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por teléfono obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasAntiguasOrdenadasPorEmailPersonal() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 1 and bajaAlumna = 0 ORDER BY emailPersonalAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por email personal obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasAntiguasOrdenadasPorEmailCentro() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 1 and bajaAlumna = 0 ORDER BY emailCentroAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por email del centro obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasAntiguasOrdenadasPorCiclo() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 1 and bajaAlumna = 0 ORDER BY cicloFormativoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por ciclo formativo obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}

	public List<String> obtenerListadoAlumnasAntiguasOrdenadasPorCurso() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas WHERE antiguaAlumna = 1 and bajaAlumna = 0 ORDER BY cursoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por curso obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasAntiguaOrdenadasPorAñoInicio() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 1 and bajaAlumna = 0 ORDER BY añoInicioAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasAntiguasOrdenadasPorAñoFin() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 1 and bajaAlumna = 0 ORDER BY añoFinAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("añoFinAlumna") + " | " + 
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("fechaNacimientoAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}
	public List<String> obtenerListadoAlumnasAntiguasOrdenadasPorFechaNacimiento() {
		List<String> alumnas = new ArrayList<>();
		String sentencia = "SELECT * FROM Alumnas where antiguaAlumna = 1 and bajaAlumna = 0 ORDER BY fechaNacimientoAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String alumna = resultado.getString("idAlumna") + " | " +
						resultado.getString("fechaNacimientoAlumna") + " | " +
						resultado.getString("nombreAlumna") + " | " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna") + " | " + 
						resultado.getString("dniAlumna") + " | " + 
						resultado.getString("direccionAlumna") + " | " + 
						resultado.getString("telefonoAlumna") + " | " + 
						resultado.getString("emailPersonalAlumna") + " | " + 
						resultado.getString("emailCentroAlumna") + " | " + 
						resultado.getString("cicloFormativoAlumna") + " | " + 
						resultado.getString("cursoAlumna") + " | " + 
						resultado.getString("añoInicioAlumna") + " | " + 
						resultado.getString("añoFinAlumna");
				alumnas.add(alumna);
			}
			System.out.println("Listado de alumnas ordenadas por dni obtenido correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return alumnas;
	}


	public String getDatosEdicion(String idAlumna) {
		String resultado = "";
		String sentencia = "SELECT * FROM Alumnas WHERE idAlumna = " + idAlumna;
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery(sentencia);
			resultSet.next();
			resultado = (resultSet.getString("idAlumna") + "-" +
					resultSet.getString("nombreAlumna") + "-" +
					resultSet.getString("primerApellidoAlumna") + "-" +
					resultSet.getString("segundoApellidoAlumna") + "-" +
					resultSet.getString("dniAlumna") + "-" +
					resultSet.getString("direccionAlumna") + "-" +
					resultSet.getString("telefonoAlumna") + "-" +
					resultSet.getString("emailPersonalAlumna") + "-" +
					resultSet.getString("emailCentroAlumna") + "-" +
					resultSet.getString("cicloFormativoAlumna") + "-" +
					resultSet.getString("cursoAlumna") + "-" +
					resultSet.getString("añoInicioAlumna") + "-" +
					resultSet.getString("añoFinAlumna") + "-" +
					resultSet.getString("fechaNacimientoAlumna") + "-" +
					resultSet.getString("erasmusAlumna") + "-" +
					resultSet.getString("antiguaAlumna") + "-" +
					resultSet.getString("bajaAlumna"));
			System.out.println("Datos de edición obtenidos correctamente para idAlumna: " + idAlumna);
		} catch (SQLException sqle) {
			System.out.println("Error 8-" + sqle.getMessage());
		}
		return resultado;
	}

	public int modificarAlumna(String sentencia) {
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
			System.out.println("Modificación de alumna ejecutada correctamente.");
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error 9-" + sqle.getMessage());
			return 1;
		}
	}
	public int moverAlumnaAntigua(String idAlumna) {
		String sql = "UPDATE Alumnas SET antiguaAlumna = 1 WHERE idAlumna = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, idAlumna);
			int rowsAffected = pstmt.executeUpdate();
			System.out.println("Actualización ejecutada para idAlumna: " + idAlumna + ", filas afectadas: " + rowsAffected);
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error 10-" + sqle.getMessage());
			return 1;
		}
	}
	public int moverAlumna(String idAlumna) {
		String sql = "UPDATE Alumnas SET antiguaAlumna = 0 WHERE idAlumna = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, idAlumna);
			int rowsAffected = pstmt.executeUpdate();
			System.out.println("Actualización ejecutada para idAlumna: " + idAlumna + ", filas afectadas: " + rowsAffected);
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error 11-" + sqle.getMessage());
			return 1;
		}
	}

	public int moverAlumnaDeBaja(String idAlumna) {
		String sql = "UPDATE Alumnas SET bajaAlumna = 1 WHERE idAlumna = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, idAlumna);
			int rowsAffected = pstmt.executeUpdate();
			System.out.println("Actualización ejecutada para idAlumna: " + idAlumna + ", filas afectadas: " + rowsAffected);
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error 10-" + sqle.getMessage());
			return 1;
		}
	}
	public int moverAlumnaDeErasmus(String idAlumna) {
		String sql = "UPDATE Alumnas SET erasmusAlumna = 1 WHERE idAlumna = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, idAlumna);
			int rowsAffected = pstmt.executeUpdate();
			System.out.println("Actualización ejecutada para idAlumna: " + idAlumna + ", filas afectadas: " + rowsAffected);
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error 10-" + sqle.getMessage());
			return 1;
		}
	}
	public void rellenarListadoPadres(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.idPadre, " +
	            "    p.nombrePadre, " +
	            "    p.primerApellidoPadre, " +
	            "    p.segundoApellidoPadre, " +
	            "    p.telefonoPadre, " +
	            "    p.nombreMadre, " +
	            "    p.primerApellidoMadre, " +
	            "    p.segundoApellidoMadre, " +
	            "    p.telefonoMadre, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna " +
	            "FROM " +
	            "    Padres p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumnaFK = a.idAlumna " +
	            "ORDER BY p.nombrePadre"; // Puedes cambiar el criterio de ordenamiento según tus necesidades

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String idPadre = resultado.getString("idPadre");
	            String nombrePadre = resultado.getString("nombrePadre");
	            String primerApellidoPadre = resultado.getString("primerApellidoPadre");
	            String segundoApellidoPadre = resultado.getString("segundoApellidoPadre");
	            String telefonoPadre = resultado.getString("telefonoPadre");
	            String nombreMadre = resultado.getString("nombreMadre");
	            String primerApellidoMadre = resultado.getString("primerApellidoMadre");
	            String segundoApellidoMadre = resultado.getString("segundoApellidoMadre");
	            String telefonoMadre = resultado.getString("telefonoMadre");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidoAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidoAlumna = resultado.getString("segundoApellidoAlumna");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPadres = 
	                    "Padre: " + nombrePadre + " " + primerApellidoPadre + " " + segundoApellidoPadre + "\n" +
	                    "Teléfono Padre: " + telefonoPadre + "\n" +
	                    "Madre: " + nombreMadre + " " + primerApellidoMadre + " " + segundoApellidoMadre + "\n" +
	                    "Teléfono Madre: " + telefonoMadre + "\n" +
	                    "Alumna: " + nombreAlumna + " " + primerApellidoAlumna + " " + segundoApellidoAlumna + "\n\n";

	            textArea.append(infoPadres);
	        }

	        System.out.println("Listado de padres rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de padres: " + sqle.getMessage());
	    }
	}
	public void rellenarListadoPadresPorMadre(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.idPadre, " +
	            "    p.nombrePadre, " +
	            "    p.primerApellidoPadre, " +
	            "    p.segundoApellidoPadre, " +
	            "    p.telefonoPadre, " +
	            "    p.nombreMadre, " +
	            "    p.primerApellidoMadre, " +
	            "    p.segundoApellidoMadre, " +
	            "    p.telefonoMadre, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna " +
	            "FROM " +
	            "    Padres p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumnaFK = a.idAlumna " +
	            "ORDER BY p.nombreMadre"; // Puedes cambiar el criterio de ordenamiento según tus necesidades

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String idPadre = resultado.getString("idPadre");
	            String nombrePadre = resultado.getString("nombrePadre");
	            String primerApellidoPadre = resultado.getString("primerApellidoPadre");
	            String segundoApellidoPadre = resultado.getString("segundoApellidoPadre");
	            String telefonoPadre = resultado.getString("telefonoPadre");
	            String nombreMadre = resultado.getString("nombreMadre");
	            String primerApellidoMadre = resultado.getString("primerApellidoMadre");
	            String segundoApellidoMadre = resultado.getString("segundoApellidoMadre");
	            String telefonoMadre = resultado.getString("telefonoMadre");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidoAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidoAlumna = resultado.getString("segundoApellidoAlumna");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPadres = 
	            		 "Madre: " + nombreMadre + " " + primerApellidoMadre + " " + segundoApellidoMadre + "\n" +
	     	             "Teléfono Madre: " + telefonoMadre + "\n" +
	                    "Padre: " + nombrePadre + " " + primerApellidoPadre + " " + segundoApellidoPadre + "\n" +
	                    "Teléfono Padre: " + telefonoPadre + "\n" +
	                    "Alumna: " + nombreAlumna + " " + primerApellidoAlumna + " " + segundoApellidoAlumna + "\n\n";

	            textArea.append(infoPadres);
	        }

	        System.out.println("Listado de padres rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de padres: " + sqle.getMessage());
	    }
	}
	public void rellenarListadoPadresPorAlumna(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.idPadre, " +
	            "    p.nombrePadre, " +
	            "    p.primerApellidoPadre, " +
	            "    p.segundoApellidoPadre, " +
	            "    p.telefonoPadre, " +
	            "    p.nombreMadre, " +
	            "    p.primerApellidoMadre, " +
	            "    p.segundoApellidoMadre, " +
	            "    p.telefonoMadre, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna " +
	            "FROM " +
	            "    Padres p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumnaFK = a.idAlumna " +
	            "ORDER BY p.idPadre"; // Puedes cambiar el criterio de ordenamiento según tus necesidades

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String idPadre = resultado.getString("idPadre");
	            String nombrePadre = resultado.getString("nombrePadre");
	            String primerApellidoPadre = resultado.getString("primerApellidoPadre");
	            String segundoApellidoPadre = resultado.getString("segundoApellidoPadre");
	            String telefonoPadre = resultado.getString("telefonoPadre");
	            String nombreMadre = resultado.getString("nombreMadre");
	            String primerApellidoMadre = resultado.getString("primerApellidoMadre");
	            String segundoApellidoMadre = resultado.getString("segundoApellidoMadre");
	            String telefonoMadre = resultado.getString("telefonoMadre");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidoAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidoAlumna = resultado.getString("segundoApellidoAlumna");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPadres = 
	                    "Alumna: " + nombreAlumna + " " + primerApellidoAlumna + " " + segundoApellidoAlumna + "\n"+
	                    "Padre: " + nombrePadre + " " + primerApellidoPadre + " " + segundoApellidoPadre + "\n" +
	                    "Teléfono Padre: " + telefonoPadre + "\n" +
	                    "Madre: " + nombreMadre + " " + primerApellidoMadre + " " + segundoApellidoMadre + "\n" +
	                    "Teléfono Madre: " + telefonoMadre + "\n\n";

	            textArea.append(infoPadres);
	        }

	        System.out.println("Listado de padres rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de padres: " + sqle.getMessage());
	    }
	}
public List<String> obtenerListadoPadres() {
        List<String> padres = new ArrayList<>();
        String query = "SELECT p.idPadre, p.nombrePadre, p.primerApellidoPadre, p.segundoApellidoPadre, p.telefonoPadre, " +
                "p.nombreMadre, p.primerApellidoMadre, p.segundoApellidoMadre, p.telefonoMadre, " +
                "a.nombreAlumna, a.primerApellidoAlumna, a.segundoApellidoAlumna " +
                "FROM Padres p " +
                "JOIN Alumnas a ON p.idAlumnaFK = a.idAlumna " +
                "ORDER BY p.idPadre";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String padre = rs.getInt("idPadre") + " | " +
                        rs.getString("nombrePadre") + " | " +
                        rs.getString("primerApellidoPadre") + " | " +
                        rs.getString("segundoApellidoPadre") + " | " +
                        rs.getString("telefonoPadre") + " | " +
                        rs.getString("nombreMadre") + " | " +
                        rs.getString("primerApellidoMadre") + " | " +
                        rs.getString("segundoApellidoMadre") + " | " +
                        rs.getString("telefonoMadre") + " | " +
                        rs.getString("nombreAlumna") + " " +
                        rs.getString("primerApellidoAlumna") + " " +
                        rs.getString("segundoApellidoAlumna");
                padres.add(padre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return padres;
    }

	    public String getDatosEdicionPadre(String id) {
	        String query = "SELECT * FROM Padres WHERE idPadre = ?";
	        StringBuilder resultado = new StringBuilder();

	        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	            pstmt.setString(1, id);
	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    resultado.append(rs.getInt("idPadre")).append("-");
	                    resultado.append(rs.getString("nombrePadre")).append("-");
	                    resultado.append(rs.getString("primerApellidoPadre")).append("-");
	                    resultado.append(rs.getString("segundoApellidoPadre")).append("-");
	                    resultado.append(rs.getString("telefonoPadre")).append("-");
	                    resultado.append(rs.getString("nombreMadre")).append("-");
	                    resultado.append(rs.getString("primerApellidoMadre")).append("-");
	                    resultado.append(rs.getString("segundoApellidoMadre")).append("-");
	                    resultado.append(rs.getString("telefonoMadre")).append("-");
	                    resultado.append(rs.getInt("idAlumnaFK"));
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return resultado.toString();
	    }

	public void rellenarListadoEmpresaAdministracion(JTextArea textArea) {
		String sentencia = "SELECT * FROM Empresas where tipoEmpresa = 1;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				textArea.append(resultado.getString("nombreEmpresa") + " | " +
                        resultado.getString("CIF") + " | " +
                        resultado.getString("direccionPostal") + " | " +
                        resultado.getString("localidad") + " | " +
                        resultado.getString("representanteLegal") + " | " +
                        resultado.getString("dniRepresentante") + " | " +
                        resultado.getString("emailEmpresa") + " | " +
                        resultado.getString("telefonoEmpresa") + "\n");
			}
			System.out.println("Listado de empresas de administración rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
	}
    public void rellenarListadoEmpresaAdministracionPorCIF(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 1 ORDER BY CIF;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(resultado.getString("CIF") + " | " +
                        resultado.getString("nombreEmpresa") + " | " +
                        resultado.getString("direccionPostal") + " | " +
                        resultado.getString("localidad") + " | " +
                        resultado.getString("representanteLegal") + " | " +
                        resultado.getString("dniRepresentante") + " | " +
                        resultado.getString("emailEmpresa") + " | " +
                        resultado.getString("telefonoEmpresa") + "\n");
            }
            System.out.println("Listado de empresas de administración ordenado por CIF rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas ordenado por CIF: " + sqle.getMessage());
        }
    }

    public void rellenarListadoEmpresaAdministracionPorDireccion(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 1 ORDER BY direccionPostal;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(resultado.getString("direccionPostal") + " | " +
                        resultado.getString("nombreEmpresa") + " | " +
                        resultado.getString("CIF") + " | " +
                        resultado.getString("localidad") + " | " +
                        resultado.getString("representanteLegal") + " | " +
                        resultado.getString("dniRepresentante") + " | " +
                        resultado.getString("emailEmpresa") + " | " +
                        resultado.getString("telefonoEmpresa") + "\n");
            }
            System.out.println("Listado de empresas de administración ordenado por dirección postal rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas ordenado por dirección postal: " + sqle.getMessage());
        }
    }

    public void rellenarListadoEmpresaAdministracionPorLocalidad(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 1 ORDER BY localidad;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(resultado.getString("localidad") + " | " +
                        resultado.getString("nombreEmpresa") + " | " +
                        resultado.getString("CIF") + " | " +
                        resultado.getString("direccionPostal") + " | " +
                        resultado.getString("representanteLegal") + " | " +
                        resultado.getString("dniRepresentante") + " | " +
                        resultado.getString("emailEmpresa") + " | " +
                        resultado.getString("telefonoEmpresa") + "\n");
            }
            System.out.println("Listado de empresas de administración ordenado por localidad rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas ordenado por localidad: " + sqle.getMessage());
        }
    }

    public void rellenarListadoEmpresaAdministracionPorRepresentante(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 1 ORDER BY representanteLegal;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(resultado.getString("representanteLegal") + " | " +
                        resultado.getString("nombreEmpresa") + " | " +
                        resultado.getString("CIF") + " | " +
                        resultado.getString("direccionPostal") + " | " +
                        resultado.getString("localidad") + " | " +
                        resultado.getString("dniRepresentante") + " | " +
                        resultado.getString("emailEmpresa") + " | " +
                        resultado.getString("telefonoEmpresa") + "\n");
            }
            System.out.println("Listado de empresas de administración ordenado por representante legal rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas ordenado por representante legal: " + sqle.getMessage());
        }
    }

    public void rellenarListadoEmpresaAdministracionPorDNIRepresentante(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 1 ORDER BY dniRepresentante;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(resultado.getString("dniRepresentante") + " | " +
                        resultado.getString("nombreEmpresa") + " | " +
                        resultado.getString("CIF") + " | " +
                        resultado.getString("direccionPostal") + " | " +
                        resultado.getString("localidad") + " | " +
                        resultado.getString("representanteLegal") + " | " +
                        resultado.getString("emailEmpresa") + " | " +
                        resultado.getString("telefonoEmpresa") + "\n");
            }
            System.out.println("Listado de empresas de administración ordenado por DNI del representante rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas ordenado por DNI del representante: " + sqle.getMessage());
        }
    }

    public void rellenarListadoEmpresaAdministracionPorEmail(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 1 ORDER BY emailEmpresa;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(resultado.getString("emailEmpresa") + " | " +
                        resultado.getString("nombreEmpresa") + " | " +
                        resultado.getString("CIF") + " | " +
                        resultado.getString("direccionPostal") + " | " +
                        resultado.getString("localidad") + " | " +
                        resultado.getString("representanteLegal") + " | " +
                        resultado.getString("dniRepresentante") + " | " +
                        resultado.getString("telefonoEmpresa") + "\n");
            }
            System.out.println("Listado de empresas de administración ordenado por email rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas ordenado por email: " + sqle.getMessage());
        }
    }

    public void rellenarListadoEmpresaAdministracionPorTelefono(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 1 ORDER BY telefonoEmpresa;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(resultado.getString("telefonoEmpresa") + " | " +
                        resultado.getString("nombreEmpresa") + " | " +
                        resultado.getString("CIF") + " | " +
                        resultado.getString("direccionPostal") + " | " +
                        resultado.getString("localidad") + " | " +
                        resultado.getString("representanteLegal") + " | " +
                        resultado.getString("dniRepresentante") + " | " +
                        resultado.getString("emailEmpresa") + "\n");
            }
            System.out.println("Listado de empresas de administración ordenado por teléfono rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas ordenado por teléfono: " + sqle.getMessage());
        }
    }
	public List<String> obtenerListadoEmpresaAdministracion() {
		List<String> empresas = new ArrayList<>();
		String sentencia = "SELECT * FROM Empresas where tipoEmpresa = 1;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String empresa = resultado.getString("idEmpresa") + " | " +
						resultado.getString("nombreEmpresa") + " | " +
						resultado.getString("CIF") + " | " +
						resultado.getString("direccionPostal") + " | " + 
						resultado.getString("localidad") + " | " + 
						resultado.getString("representanteLegal") + " | " + 
						resultado.getString("dniRepresentante") + " | " + 
						resultado.getString("emailEmpresa") + " | " + 
						resultado.getString("telefonoEmpresa");
				empresas.add(empresa);
			}
			System.out.println("Listado de empresas de administración rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return empresas;
	}
	public List<String> obtenerListadoEmpresaAdministracionPorCIF() {
	    List<String> empresas = new ArrayList<>();
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 1 ORDER BY CIF;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);
	        while (resultado.next()) {
	            String empresa =resultado.getString("CIF") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("telefonoEmpresa") + " | " +
	                    resultado.getString("idEmpresa");
	            empresas.add(empresa);
	        }
	        System.out.println("Listado de empresas de administración ordenado por CIF.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al ordenar por CIF: " + sqle.getMessage());
	    }
	    return empresas;
	}
	public List<String> obtenerListadoEmpresaAdministracionPorDireccionPostal() {
	    List<String> empresas = new ArrayList<>();
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 1 ORDER BY direccionPostal;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);
	        while (resultado.next()) {
	            String empresa =resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("telefonoEmpresa") + " | " +
	                    resultado.getString("idEmpresa");
	            empresas.add(empresa);
	        }
	        System.out.println("Listado de empresas de administración ordenado por direccionPostal.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al ordenar por direccionPostal: " + sqle.getMessage());
	    }
	    return empresas;
	}
	public List<String> obtenerListadoEmpresaAdministracionPorLocalidad() {
	    List<String> empresas = new ArrayList<>();
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 1 ORDER BY localidad;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);
	        while (resultado.next()) {
	            String empresa = resultado.getString("localidad") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("telefonoEmpresa") + " | " +
	                    resultado.getString("idEmpresa");
	            empresas.add(empresa);
	        }
	        System.out.println("Listado de empresas de administración ordenado por localidad.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al ordenar por localidad: " + sqle.getMessage());
	    }
	    return empresas;
	}
	public List<String> obtenerListadoEmpresaAdministracionPorRepresentanteLegal() {
	    List<String> empresas = new ArrayList<>();
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 1 ORDER BY representanteLegal;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);
	        while (resultado.next()) {
	            String empresa = resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("telefonoEmpresa") + " | " +
	                    resultado.getString("idEmpresa");
	            empresas.add(empresa);
	        }
	        System.out.println("Listado de empresas de administración ordenado por representanteLegal.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al ordenar por representanteLegal: " + sqle.getMessage());
	    }
	    return empresas;
	}
	public List<String> obtenerListadoEmpresaAdministracionPorDniRepresentante() {
	    List<String> empresas = new ArrayList<>();
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 1 ORDER BY dniRepresentante;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);
	        while (resultado.next()) {
	            String empresa = resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("telefonoEmpresa") + " | " +
	                    resultado.getString("idEmpresa");
	            empresas.add(empresa);
	        }
	        System.out.println("Listado de empresas de administración ordenado por dniRepresentante.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al ordenar por dniRepresentante: " + sqle.getMessage());
	    }
	    return empresas;
	}
	public List<String> obtenerListadoEmpresaAdministracionPorEmailEmpresa() {
	    List<String> empresas = new ArrayList<>();
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 1 ORDER BY emailEmpresa;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);
	        while (resultado.next()) {
	            String empresa = resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("telefonoEmpresa") + " | " +
	                    resultado.getString("idEmpresa");
	            empresas.add(empresa);
	        }
	        System.out.println("Listado de empresas de administración ordenado por emailEmpresa.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al ordenar por emailEmpresa: " + sqle.getMessage());
	    }
	    return empresas;
	}
	public List<String> obtenerListadoEmpresaAdministracionPorTelefonoEmpresa() {
	    List<String> empresas = new ArrayList<>();
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 1 ORDER BY telefonoEmpresa;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);
	        while (resultado.next()) {
	            String empresa = resultado.getString("telefonoEmpresa") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("idEmpresa");
	            empresas.add(empresa);
	        }
	        System.out.println("Listado de empresas de administración ordenado por telefonoEmpresa.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al ordenar por telefonoEmpresa: " + sqle.getMessage());
	    }
	    return empresas;
	}






	public List<String> obtenerListadoEmpresaEnfermeria2() {
		List<String> empresas = new ArrayList<>();
		String sentencia = "SELECT * FROM Empresas where tipoEmpresa = 2;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String empresa = resultado.getString("idEmpresa") + " | " +
						resultado.getString("nombreEmpresa") + " | " +
						resultado.getString("CIF") + " | " +
						resultado.getString("direccionPostal") + " | " + 
						resultado.getString("localidad") + " | " + 
						resultado.getString("representanteLegal") + " | " + 
						resultado.getString("dniRepresentante") + " | " + 
						resultado.getString("emailEmpresa") + " | " + 
						resultado.getString("telefonoEmpresa");
				empresas.add(empresa);
			}
			System.out.println("Listado de empresas de administración rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return empresas;
	}
	public List<String> obtenerListadoEmpresaEnfermeriaPorCIF() {
	    List<String> empresas = new ArrayList<>();
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 2 ORDER BY CIF;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);
	        while (resultado.next()) {
	            String empresa =resultado.getString("CIF") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("telefonoEmpresa") + " | " +
	                    resultado.getString("idEmpresa");
	            empresas.add(empresa);
	        }
	        System.out.println("Listado de empresas de administración ordenado por CIF.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al ordenar por CIF: " + sqle.getMessage());
	    }
	    return empresas;
	}
	public List<String> obtenerListadoEmpresaEnfermeriaPorDireccionPostal() {
	    List<String> empresas = new ArrayList<>();
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 2 ORDER BY direccionPostal;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);
	        while (resultado.next()) {
	            String empresa =resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("telefonoEmpresa") + " | " +
	                    resultado.getString("idEmpresa");
	            empresas.add(empresa);
	        }
	        System.out.println("Listado de empresas de administración ordenado por direccionPostal.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al ordenar por direccionPostal: " + sqle.getMessage());
	    }
	    return empresas;
	}
	public List<String> obtenerListadoEmpresaEnfermeriaPorLocalidad() {
	    List<String> empresas = new ArrayList<>();
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 2 ORDER BY localidad;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);
	        while (resultado.next()) {
	            String empresa = resultado.getString("localidad") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("telefonoEmpresa") + " | " +
	                    resultado.getString("idEmpresa");
	            empresas.add(empresa);
	        }
	        System.out.println("Listado de empresas de administración ordenado por localidad.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al ordenar por localidad: " + sqle.getMessage());
	    }
	    return empresas;
	}
	public List<String> obtenerListadoEmpresaEnfermeriaPorRepresentanteLegal() {
	    List<String> empresas = new ArrayList<>();
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 2 ORDER BY representanteLegal;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);
	        while (resultado.next()) {
	            String empresa = resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("telefonoEmpresa") + " | " +
	                    resultado.getString("idEmpresa");
	            empresas.add(empresa);
	        }
	        System.out.println("Listado de empresas de administración ordenado por representanteLegal.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al ordenar por representanteLegal: " + sqle.getMessage());
	    }
	    return empresas;
	}
	public List<String> obtenerListadoEmpresaEnfermeriaPorDniRepresentante() {
	    List<String> empresas = new ArrayList<>();
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 2 ORDER BY dniRepresentante;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);
	        while (resultado.next()) {
	            String empresa = resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("telefonoEmpresa") + " | " +
	                    resultado.getString("idEmpresa");
	            empresas.add(empresa);
	        }
	        System.out.println("Listado de empresas de administración ordenado por dniRepresentante.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al ordenar por dniRepresentante: " + sqle.getMessage());
	    }
	    return empresas;
	}
	public List<String> obtenerListadoEmpresaEnfermeriaPorEmailEmpresa() {
	    List<String> empresas = new ArrayList<>();
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 2 ORDER BY emailEmpresa;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);
	        while (resultado.next()) {
	            String empresa = resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("telefonoEmpresa") + " | " +
	                    resultado.getString("idEmpresa");
	            empresas.add(empresa);
	        }
	        System.out.println("Listado de empresas de administración ordenado por emailEmpresa.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al ordenar por emailEmpresa: " + sqle.getMessage());
	    }
	    return empresas;
	}
	public List<String> obtenerListadoEmpresaEnfermeriaPorTelefonoEmpresa() {
	    List<String> empresas = new ArrayList<>();
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 2 ORDER BY telefonoEmpresa;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);
	        while (resultado.next()) {
	            String empresa = resultado.getString("telefonoEmpresa") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("idEmpresa");
	            empresas.add(empresa);
	        }
	        System.out.println("Listado de empresas de administración ordenado por telefonoEmpresa.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al ordenar por telefonoEmpresa: " + sqle.getMessage());
	    }
	    return empresas;
	}
	public List<String> obtenerListadoEmpresaEducacion2() {
		List<String> empresas = new ArrayList<>();
		String sentencia = "SELECT * FROM Empresas where tipoEmpresa = 3;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String empresa = resultado.getString("idEmpresa") + " | " +
						resultado.getString("nombreEmpresa") + " | " +
						resultado.getString("CIF") + " | " +
						resultado.getString("direccionPostal") + " | " + 
						resultado.getString("localidad") + " | " + 
						resultado.getString("representanteLegal") + " | " + 
						resultado.getString("dniRepresentante") + " | " + 
						resultado.getString("emailEmpresa") + " | " + 
						resultado.getString("telefonoEmpresa");
				empresas.add(empresa);
			}
			System.out.println("Listado de empresas de administración rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return empresas;
	}public List<String> obtenerListadoEmpresaEducacionOnline2() {
		List<String> empresas = new ArrayList<>();
		String sentencia = "SELECT * FROM Empresas where tipoEmpresa = 4;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String empresa = resultado.getString("idEmpresa") + " | " +
						resultado.getString("nombreEmpresa") + " | " +
						resultado.getString("CIF") + " | " +
						resultado.getString("direccionPostal") + " | " + 
						resultado.getString("localidad") + " | " + 
						resultado.getString("representanteLegal") + " | " + 
						resultado.getString("dniRepresentante") + " | " + 
						resultado.getString("emailEmpresa") + " | " + 
						resultado.getString("telefonoEmpresa");
				empresas.add(empresa);
			}
			System.out.println("Listado de empresas de administración rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return empresas;
	}public List<String> obtenerListadoEmpresaAsistencia2() {
		List<String> empresas = new ArrayList<>();
		String sentencia = "SELECT * FROM Empresas where tipoEmpresa = 5;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String empresa = resultado.getString("idEmpresa") + " | " +
						resultado.getString("nombreEmpresa") + " | " +
						resultado.getString("CIF") + " | " +
						resultado.getString("direccionPostal") + " | " + 
						resultado.getString("localidad") + " | " + 
						resultado.getString("representanteLegal") + " | " + 
						resultado.getString("dniRepresentante") + " | " + 
						resultado.getString("emailEmpresa") + " | " + 
						resultado.getString("telefonoEmpresa");
				empresas.add(empresa);
			}
			System.out.println("Listado de empresas de administración rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return empresas;
	}


	public int altaEmpresaAdministracion(String sentencia) {
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
			System.out.println("Alta de empresa de administración ejecutada correctamente.");
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error 3-" + sqle.getMessage());
			return 1;
		}
	}

	public void rellenarListadoEmpresaEnfermeria(JTextArea textArea) {
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 2;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);
	        while (resultado.next()) {
	            textArea.append(resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("telefonoEmpresa") + "\n");
	        }
	        System.out.println("Listado de empresas de enfermería rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de empresas de enfermería: " + sqle.getMessage());
	    }
	}

	public void rellenarListadoEmpresaEnfermeriaPorCIF(JTextArea textArea) {
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 2 ORDER BY CIF;";
	    try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	        ResultSet resultado = statement.executeQuery();
	        while (resultado.next()) {
	            textArea.append(resultado.getString("CIF") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("telefonoEmpresa") + "\n");
	        }
	        System.out.println("Listado de empresas de enfermería ordenado por CIF rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de empresas de enfermería ordenado por CIF: " + sqle.getMessage());
	    }
	}

	public void rellenarListadoEmpresaEnfermeriaPorDireccion(JTextArea textArea) {
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 2 ORDER BY direccionPostal;";
	    try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	        ResultSet resultado = statement.executeQuery();
	        while (resultado.next()) {
	            textArea.append(resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("telefonoEmpresa") + "\n");
	        }
	        System.out.println("Listado de empresas de enfermería ordenado por dirección postal rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de empresas de enfermería ordenado por dirección postal: " + sqle.getMessage());
	    }
	}

	public void rellenarListadoEmpresaEnfermeriaPorLocalidad(JTextArea textArea) {
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 2 ORDER BY localidad;";
	    try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	        ResultSet resultado = statement.executeQuery();
	        while (resultado.next()) {
	            textArea.append(resultado.getString("localidad") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("telefonoEmpresa") + "\n");
	        }
	        System.out.println("Listado de empresas de enfermería ordenado por localidad rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de empresas de enfermería ordenado por localidad: " + sqle.getMessage());
	    }
	}

	public void rellenarListadoEmpresaEnfermeriaPorRepresentante(JTextArea textArea) {
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 2 ORDER BY representanteLegal;";
	    try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	        ResultSet resultado = statement.executeQuery();
	        while (resultado.next()) {
	            textArea.append(resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("telefonoEmpresa") + "\n");
	        }
	        System.out.println("Listado de empresas de enfermería ordenado por representante legal rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de empresas de enfermería ordenado por representante legal: " + sqle.getMessage());
	    }
	}

	public void rellenarListadoEmpresaEnfermeriaPorDNIRepresentante(JTextArea textArea) {
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 2 ORDER BY dniRepresentante;";
	    try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	        ResultSet resultado = statement.executeQuery();
	        while (resultado.next()) {
	            textArea.append(resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("telefonoEmpresa") + "\n");
	        }
	        System.out.println("Listado de empresas de enfermería ordenado por DNI del representante rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de empresas de enfermería ordenado por DNI del representante: " + sqle.getMessage());
	    }
	}

	public void rellenarListadoEmpresaEnfermeriaPorEmail(JTextArea textArea) {
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 2 ORDER BY emailEmpresa;";
	    try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	        ResultSet resultado = statement.executeQuery();
	        while (resultado.next()) {
	            textArea.append(resultado.getString("emailEmpresa") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("telefonoEmpresa") + "\n");
	        }
	        System.out.println("Listado de empresas de enfermería ordenado por email rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de empresas de enfermería ordenado por email: " + sqle.getMessage());
	    }
	}

	public void rellenarListadoEmpresaEnfermeriaPorTelefono(JTextArea textArea) {
	    String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 2 ORDER BY telefonoEmpresa;";
	    try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	        ResultSet resultado = statement.executeQuery();
	        while (resultado.next()) {
	            textArea.append(resultado.getString("telefonoEmpresa") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("CIF") + " | " +
	                    resultado.getString("direccionPostal") + " | " +
	                    resultado.getString("localidad") + " | " +
	                    resultado.getString("representanteLegal") + " | " +
	                    resultado.getString("dniRepresentante") + " | " +
	                    resultado.getString("emailEmpresa") + "\n");
	        }
	        System.out.println("Listado de empresas de enfermería ordenado por teléfono rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de empresas de enfermería ordenado por teléfono: " + sqle.getMessage());
	    }
	}

	public int altaEmpresaEnfermeria(String sentencia) {
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
			System.out.println("Alta de empresa de administración ejecutada correctamente.");
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error 3-" + sqle.getMessage());
			return 1;
		}
	}
	  public void rellenarListadoEmpresaEducacion(JTextArea textArea) {
	        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 3;";
	        try {
	            statement = connection.prepareStatement(sentencia);
	            ResultSet resultado = statement.executeQuery(sentencia);
	            while (resultado.next()) {
	                textArea.append(resultado.getString("nombreEmpresa") + " | " +
	                        resultado.getString("CIF") + " | " +
	                        resultado.getString("direccionPostal") + " | " +
	                        resultado.getString("localidad") + " | " +
	                        resultado.getString("representanteLegal") + " | " +
	                        resultado.getString("dniRepresentante") + " | " +
	                        resultado.getString("emailEmpresa") + " | " +
	                        resultado.getString("telefonoEmpresa") + "\n");
	            }
	            System.out.println("Listado de empresas de educación rellenado correctamente.");
	        } catch (SQLException sqle) {
	            System.out.println("Error al rellenar listado de empresas de educación: " + sqle.getMessage());
	        }
	    }

	    public void rellenarListadoEmpresaEducacionPorCIF(JTextArea textArea) {
	        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 3 ORDER BY CIF;";
	        try {
	            statement = connection.prepareStatement(sentencia);
	            ResultSet resultado = statement.executeQuery(sentencia);
	            while (resultado.next()) {
	                textArea.append(resultado.getString("CIF") + " | " +
	                        resultado.getString("nombreEmpresa") + " | " +
	                        resultado.getString("direccionPostal") + " | " +
	                        resultado.getString("localidad") + " | " +
	                        resultado.getString("representanteLegal") + " | " +
	                        resultado.getString("dniRepresentante") + " | " +
	                        resultado.getString("emailEmpresa") + " | " +
	                        resultado.getString("telefonoEmpresa") + "\n");
	            }
	            System.out.println("Listado de empresas de educación ordenado por CIF rellenado correctamente.");
	        } catch (SQLException sqle) {
	            System.out.println("Error al rellenar listado de empresas de educación ordenado por CIF: " + sqle.getMessage());
	        }
	    }

	    public void rellenarListadoEmpresaEducacionPorDireccion(JTextArea textArea) {
	        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 3 ORDER BY direccionPostal;";
	        try {
	            statement = connection.prepareStatement(sentencia);
	            ResultSet resultado = statement.executeQuery(sentencia);
	            while (resultado.next()) {
	                textArea.append(resultado.getString("direccionPostal") + " | " +
	                        resultado.getString("nombreEmpresa") + " | " +
	                        resultado.getString("CIF") + " | " +
	                        resultado.getString("localidad") + " | " +
	                        resultado.getString("representanteLegal") + " | " +
	                        resultado.getString("dniRepresentante") + " | " +
	                        resultado.getString("emailEmpresa") + " | " +
	                        resultado.getString("telefonoEmpresa") + "\n");
	            }
	            System.out.println("Listado de empresas de educación ordenado por dirección rellenado correctamente.");
	        } catch (SQLException sqle) {
	            System.out.println("Error al rellenar listado de empresas de educación ordenado por dirección: " + sqle.getMessage());
	        }
	    }

	    public void rellenarListadoEmpresaEducacionPorLocalidad(JTextArea textArea) {
	        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 3 ORDER BY localidad;";
	        try {
	            statement = connection.prepareStatement(sentencia);
	            ResultSet resultado = statement.executeQuery(sentencia);
	            while (resultado.next()) {
	                textArea.append(resultado.getString("localidad") + " | " +
	                        resultado.getString("nombreEmpresa") + " | " +
	                        resultado.getString("CIF") + " | " +
	                        resultado.getString("direccionPostal") + " | " +
	                        resultado.getString("representanteLegal") + " | " +
	                        resultado.getString("dniRepresentante") + " | " +
	                        resultado.getString("emailEmpresa") + " | " +
	                        resultado.getString("telefonoEmpresa") + "\n");
	            }
	            System.out.println("Listado de empresas de educación ordenado por localidad rellenado correctamente.");
	        } catch (SQLException sqle) {
	            System.out.println("Error al rellenar listado de empresas de educación ordenado por localidad: " + sqle.getMessage());
	        }
	    }

	    public void rellenarListadoEmpresaEducacionPorRepresentante(JTextArea textArea) {
	        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 3 ORDER BY representanteLegal;";
	        try {
	            statement = connection.prepareStatement(sentencia);
	            ResultSet resultado = statement.executeQuery(sentencia);
	            while (resultado.next()) {
	                textArea.append(resultado.getString("representanteLegal") + " | " +
	                        resultado.getString("nombreEmpresa") + " | " +
	                        resultado.getString("CIF") + " | " +
	                        resultado.getString("direccionPostal") + " | " +
	                        resultado.getString("localidad") + " | " +
	                        resultado.getString("dniRepresentante") + " | " +
	                        resultado.getString("emailEmpresa") + " | " +
	                        resultado.getString("telefonoEmpresa") + "\n");
	            }
	            System.out.println("Listado de empresas de educación ordenado por representante rellenado correctamente.");
	        } catch (SQLException sqle) {
	            System.out.println("Error al rellenar listado de empresas de educación ordenado por representante: " + sqle.getMessage());
	        }
	    }

	    public void rellenarListadoEmpresaEducacionPorDNIRepresentante(JTextArea textArea) {
	        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 3 ORDER BY dniRepresentante;";
	        try {
	            statement = connection.prepareStatement(sentencia);
	            ResultSet resultado = statement.executeQuery(sentencia);
	            while (resultado.next()) {
	                textArea.append(resultado.getString("dniRepresentante") + " | " +
	                        resultado.getString("representanteLegal") + " | " +
	                        resultado.getString("nombreEmpresa") + " | " +
	                        resultado.getString("CIF") + " | " +
	                        resultado.getString("direccionPostal") + " | " +
	                        resultado.getString("localidad") + " | " +
	                        resultado.getString("emailEmpresa") + " | " +
	                        resultado.getString("telefonoEmpresa") + "\n");
	            }
	            System.out.println("Listado de empresas de educación ordenado por DNI del representante rellenado correctamente.");
	        } catch (SQLException sqle) {
	            System.out.println("Error al rellenar listado de empresas de educación ordenado por DNI del representante: " + sqle.getMessage());
	        }
	    }

	    public void rellenarListadoEmpresaEducacionPorEmail(JTextArea textArea) {
	        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 3 ORDER BY emailEmpresa;";
	        try {
	            statement = connection.prepareStatement(sentencia);
	            ResultSet resultado = statement.executeQuery(sentencia);
	            while (resultado.next()) {
	                textArea.append(resultado.getString("emailEmpresa") + " | " +
	                        resultado.getString("nombreEmpresa") + " | " +
	                        resultado.getString("CIF") + " | " +
	                        resultado.getString("direccionPostal") + " | " +
	                        resultado.getString("localidad") + " | " +
	                        resultado.getString("representanteLegal") + " | " +
	                        resultado.getString("dniRepresentante") + " | " +
	                        resultado.getString("telefonoEmpresa") + "\n");
	            }
	            System.out.println("Listado de empresas de educación ordenado por email rellenado correctamente.");
	        } catch (SQLException sqle) {
	            System.out.println("Error al rellenar listado de empresas de educación ordenado por email: " + sqle.getMessage());
	        }
	    }

	    public void rellenarListadoEmpresaEducacionPorTelefono(JTextArea textArea) {
	        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 3 ORDER BY telefonoEmpresa;";
	        try {
	            statement = connection.prepareStatement(sentencia);
	            ResultSet resultado = statement.executeQuery(sentencia);
	            while (resultado.next()) {
	                textArea.append(resultado.getString("telefonoEmpresa") + " | " +
	                        resultado.getString("nombreEmpresa") + " | " +
	                        resultado.getString("CIF") + " | " +
	                        resultado.getString("direccionPostal") + " | " +
	                        resultado.getString("localidad") + " | " +
	                        resultado.getString("representanteLegal") + " | " +
	                        resultado.getString("dniRepresentante") + " | " +
	                        resultado.getString("emailEmpresa") + "\n");
	            }
	            System.out.println("Listado de empresas de educación ordenado por teléfono rellenado correctamente.");
	        } catch (SQLException sqle) {
	            System.out.println("Error al rellenar listado de empresas de educación ordenado por teléfono: " + sqle.getMessage());
	        }
	    }

	public int altaEmpresaEducacion(String sentencia) {
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
			System.out.println("Alta de empresa de administración ejecutada correctamente.");
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error 3-" + sqle.getMessage());
			return 1;
		}
	}
	public void rellenarListadoEmpresaEducacionOnline(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 4;";
        try {
            PreparedStatement statement = connection.prepareStatement(sentencia);
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("CIF") + " | " +
                    resultado.getString("direccionPostal") + " | " +
                    resultado.getString("localidad") + " | " +
                    resultado.getString("representanteLegal") + " | " +
                    resultado.getString("dniRepresentante") + " | " +
                    resultado.getString("emailEmpresa") + " | " +
                    resultado.getString("telefonoEmpresa") + "\n"
                );
            }
            System.out.println("Listado de empresas de educación online rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas de educación online: " + sqle.getMessage());
        }
    }

    public void rellenarListadoEmpresaEducacionOnlinePorCIF(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 4 ORDER BY CIF;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("CIF") + " | " +
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("direccionPostal") + " | " +
                    resultado.getString("localidad") + " | " +
                    resultado.getString("representanteLegal") + " | " +
                    resultado.getString("dniRepresentante") + " | " +
                    resultado.getString("emailEmpresa") + " | " +
                    resultado.getString("telefonoEmpresa") + "\n"
                );
            }
            System.out.println("Listado de empresas de educación online ordenado por CIF rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas de educación online ordenado por CIF: " + sqle.getMessage());
        }
    }

    public void rellenarListadoEmpresaEducacionOnlinePorDireccion(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 4 ORDER BY direccionPostal;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("direccionPostal") + " | " +
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("CIF") + " | " +
                    resultado.getString("localidad") + " | " +
                    resultado.getString("representanteLegal") + " | " +
                    resultado.getString("dniRepresentante") + " | " +
                    resultado.getString("emailEmpresa") + " | " +
                    resultado.getString("telefonoEmpresa") + "\n"
                );
            }
            System.out.println("Listado de empresas de educación online ordenado por dirección postal rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas de educación online ordenado por dirección postal: " + sqle.getMessage());
        }
    }

    public void rellenarListadoEmpresaEducacionOnlinePorLocalidad(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 4 ORDER BY localidad;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("localidad") + " | " +
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("CIF") + " | " +
                    resultado.getString("direccionPostal") + " | " +
                    resultado.getString("representanteLegal") + " | " +
                    resultado.getString("dniRepresentante") + " | " +
                    resultado.getString("emailEmpresa") + " | " +
                    resultado.getString("telefonoEmpresa") + "\n"
                );
            }
            System.out.println("Listado de empresas de educación online ordenado por localidad rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas de educación online ordenado por localidad: " + sqle.getMessage());
        }
    }

    public void rellenarListadoEmpresaEducacionOnlinePorRepresentante(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 4 ORDER BY representanteLegal;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("representanteLegal") + " | " +
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("CIF") + " | " +
                    resultado.getString("direccionPostal") + " | " +
                    resultado.getString("localidad") + " | " +
                    resultado.getString("dniRepresentante") + " | " +
                    resultado.getString("emailEmpresa") + " | " +
                    resultado.getString("telefonoEmpresa") + "\n"
                );
            }
            System.out.println("Listado de empresas de educación online ordenado por representante legal rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas de educación online ordenado por representante legal: " + sqle.getMessage());
        }
    }

    public void rellenarListadoEmpresaEducacionOnlinePorDNIRepresentante(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 4 ORDER BY dniRepresentante;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("dniRepresentante") + " | " +
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("CIF") + " | " +
                    resultado.getString("direccionPostal") + " | " +
                    resultado.getString("localidad") + " | " +
                    resultado.getString("representanteLegal") + " | " +
                    resultado.getString("emailEmpresa") + " | " +
                    resultado.getString("telefonoEmpresa") + "\n"
                );
            }
            System.out.println("Listado de empresas de educación online ordenado por DNI del representante rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas de educación online ordenado por DNI del representante: " + sqle.getMessage());
        }
    }

    public void rellenarListadoEmpresaEducacionOnlinePorEmail(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 4 ORDER BY emailEmpresa;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("emailEmpresa") + " | " +
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("CIF") + " | " +
                    resultado.getString("direccionPostal") + " | " +
                    resultado.getString("localidad") + " | " +
                    resultado.getString("representanteLegal") + " | " +
                    resultado.getString("dniRepresentante") + " | " +
                    resultado.getString("telefonoEmpresa") + "\n"
                );
            }
            System.out.println("Listado de empresas de educación online ordenado por email rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas de educación online ordenado por email: " + sqle.getMessage());
        }
    }

    public void rellenarListadoEmpresaEducacionOnlinePorTelefono(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 4 ORDER BY telefonoEmpresa;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("telefonoEmpresa") + " | " +
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("CIF") + " | " +
                    resultado.getString("direccionPostal") + " | " +
                    resultado.getString("localidad") + " | " +
                    resultado.getString("representanteLegal") + " | " +
                    resultado.getString("dniRepresentante") + " | " +
                    resultado.getString("emailEmpresa") + "\n"
                );
            }
            System.out.println("Listado de empresas de educación online ordenado por teléfono rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas de educación online ordenado por teléfono: " + sqle.getMessage());
        }
    }
	public int altaEmpresaEducacionOnline(String sentencia) {
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
			System.out.println("Alta de empresa de administración ejecutada correctamente.");
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error 3-" + sqle.getMessage());
			return 1;
		}
	}
	public void rellenarListadoEmpresaAsistencia(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 5;";
        try {
            PreparedStatement statement = connection.prepareStatement(sentencia);
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("CIF") + " | " +
                    resultado.getString("direccionPostal") + " | " +
                    resultado.getString("localidad") + " | " +
                    resultado.getString("representanteLegal") + " | " +
                    resultado.getString("dniRepresentante") + " | " +
                    resultado.getString("emailEmpresa") + " | " +
                    resultado.getString("telefonoEmpresa") + "\n"
                );
            }
            System.out.println("Listado de empresas de asistencia rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas de asistencia: " + sqle.getMessage());
        }
    }

    // Método para rellenar el listado de empresas de tipo Asistencia ordenado por CIF
    public void rellenarListadoEmpresaAsistenciaPorCIF(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 5 ORDER BY CIF;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("CIF") + " | " +
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("direccionPostal") + " | " +
                    resultado.getString("localidad") + " | " +
                    resultado.getString("representanteLegal") + " | " +
                    resultado.getString("dniRepresentante") + " | " +
                    resultado.getString("emailEmpresa") + " | " +
                    resultado.getString("telefonoEmpresa") + "\n"
                );
            }
            System.out.println("Listado de empresas de asistencia ordenado por CIF rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas de asistencia ordenado por CIF: " + sqle.getMessage());
        }
    }

    // Método para rellenar el listado de empresas de tipo Asistencia ordenado por dirección postal
    public void rellenarListadoEmpresaAsistenciaPorDireccion(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 5 ORDER BY direccionPostal;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("direccionPostal") + " | " +
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("CIF") + " | " +
                    resultado.getString("localidad") + " | " +
                    resultado.getString("representanteLegal") + " | " +
                    resultado.getString("dniRepresentante") + " | " +
                    resultado.getString("emailEmpresa") + " | " +
                    resultado.getString("telefonoEmpresa") + "\n"
                );
            }
            System.out.println("Listado de empresas de asistencia ordenado por dirección postal rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas de asistencia ordenado por dirección postal: " + sqle.getMessage());
        }
    }

    // Método para rellenar el listado de empresas de tipo Asistencia ordenado por localidad
    public void rellenarListadoEmpresaAsistenciaPorLocalidad(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 5 ORDER BY localidad;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("localidad") + " | " +
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("CIF") + " | " +
                    resultado.getString("direccionPostal") + " | " +
                    resultado.getString("representanteLegal") + " | " +
                    resultado.getString("dniRepresentante") + " | " +
                    resultado.getString("emailEmpresa") + " | " +
                    resultado.getString("telefonoEmpresa") + "\n"
                );
            }
            System.out.println("Listado de empresas de asistencia ordenado por localidad rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas de asistencia ordenado por localidad: " + sqle.getMessage());
        }
    }

    // Método para rellenar el listado de empresas de tipo Asistencia ordenado por representante legal
    public void rellenarListadoEmpresaAsistenciaPorRepresentante(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 5 ORDER BY representanteLegal;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("representanteLegal") + " | " +
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("CIF") + " | " +
                    resultado.getString("direccionPostal") + " | " +
                    resultado.getString("localidad") + " | " +
                    resultado.getString("dniRepresentante") + " | " +
                    resultado.getString("emailEmpresa") + " | " +
                    resultado.getString("telefonoEmpresa") + "\n"
                );
            }
            System.out.println("Listado de empresas de asistencia ordenado por representante legal rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas de asistencia ordenado por representante legal: " + sqle.getMessage());
        }
    }

    // Método para rellenar el listado de empresas de tipo Asistencia ordenado por DNI del representante
    public void rellenarListadoEmpresaAsistenciaPorDNIRepresentante(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 5 ORDER BY dniRepresentante;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("dniRepresentante") + " | " +
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("CIF") + " | " +
                    resultado.getString("direccionPostal") + " | " +
                    resultado.getString("localidad") + " | " +
                    resultado.getString("representanteLegal") + " | " +
                    resultado.getString("emailEmpresa") + " | " +
                    resultado.getString("telefonoEmpresa") + "\n"
                );
            }
            System.out.println("Listado de empresas de asistencia ordenado por DNI del representante rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas de asistencia ordenado por DNI del representante: " + sqle.getMessage());
        }
    }

    // Método para rellenar el listado de empresas de tipo Asistencia ordenado por email
    public void rellenarListadoEmpresaAsistenciaPorEmail(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 5 ORDER BY emailEmpresa;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("emailEmpresa") + " | " +
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("CIF") + " | " +
                    resultado.getString("direccionPostal") + " | " +
                    resultado.getString("localidad") + " | " +
                    resultado.getString("representanteLegal") + " | " +
                    resultado.getString("dniRepresentante") + " | " +
                    resultado.getString("telefonoEmpresa") + "\n"
                );
            }
            System.out.println("Listado de empresas de asistencia ordenado por email rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas de asistencia ordenado por email: " + sqle.getMessage());
        }
    }

    // Método para rellenar el listado de empresas de tipo Asistencia ordenado por teléfono
    public void rellenarListadoEmpresaAsistenciaPorTelefono(JTextArea textArea) {
        String sentencia = "SELECT * FROM Empresas WHERE tipoEmpresa = 5 ORDER BY telefonoEmpresa;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("telefonoEmpresa") + " | " +
                    		 resultado.getString("nombreEmpresa") + " | " +
                             resultado.getString("CIF") + " | " +
                             resultado.getString("direccionPostal") + " | " +
                             resultado.getString("localidad") + " | " +
                             resultado.getString("representanteLegal") + " | " +
                             resultado.getString("dniRepresentante") + "\n"
                		  );
            }
            System.out.println("Listado de empresas de asistencia ordenado por email rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de empresas de asistencia ordenado por email: " + sqle.getMessage());
        }
    }
	public int altaEmpresaAsistencia(String sentencia) {
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
			System.out.println("Alta de empresa de administración ejecutada correctamente.");
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error 3-" + sqle.getMessage());
			return 1;
		}
	}
	public int altaContactoEmpresaAdministracion(String sentencia) {
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
			System.out.println("Alta de empresa de ContactoEmpresaAdmini ejecutada correctamente.");
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error 13-" + sqle.getMessage());
			return 1;
		}
	}
	public int altaSede(String sentencia) {
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
			System.out.println("Alta de empresa de ContactoEmpresaAdmini ejecutada correctamente.");
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error 13-" + sqle.getMessage());
			return 1;
		}
	}
	public void rellenarListadoContactoEmpresaAdministracion(JTextArea textArea) {
		 String sentencia = "SELECT "
	                + "    c.idContacto,"
	                + "    c.nombreContacto,"
	                + "    c.primerApellidoContacto,"
	                + "    c.segundoApellidoContacto,"
	                + "    c.dniContacto,"
	                + "    c.telefonoContacto,"
	                + "    c.emailContacto,"
	                + "    c.esDual,"
	                + "    c.idEmpresaFK,"
	                + "    e.nombreEmpresa,"
	                + "    e.CIF,"
	                + "    e.direccionPostal,"
	                + "    e.localidad,"
	                + "    e.representanteLegal,"
	                + "    e.dniRepresentante,"
	                + "    e.emailEmpresa,"
	                + "    e.telefonoEmpresa,"
	                + "    e.tipoEmpresa "
	                + "FROM "
	                + "    ContactosEmpresa c "
	                + "JOIN "
	                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
	                + "WHERE "
	                + "    e.tipoEmpresa = 1 "
	                + "ORDER BY c.nombreContacto;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				textArea.append(resultado.getString("nombreContacto") + " | ");
				textArea.append(resultado.getString("primerApellidoContacto") + " ");
				textArea.append(resultado.getString("segundoApellidoContacto") + " | ");
				textArea.append(resultado.getString("dniContacto") + " | ");
				textArea.append(resultado.getString("telefonoContacto") +" | ");
				textArea.append(resultado.getString("emailContacto") +" | ");
				textArea.append(resultado.getString("esDual") + " | ");
				textArea.append(resultado.getString("nombreEmpresa") + "\n");
			}
			System.out.println("Listado de empresas de administración rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}

	}
	public void rellenarListadoContactoEmpresaAdministracionPorApellido(JTextArea textArea) {
		 String sentencia = "SELECT "
	                + "    c.idContacto,"
	                + "    c.nombreContacto,"
	                + "    c.primerApellidoContacto,"
	                + "    c.segundoApellidoContacto,"
	                + "    c.dniContacto,"
	                + "    c.telefonoContacto,"
	                + "    c.emailContacto,"
	                + "    c.esDual,"
	                + "    c.idEmpresaFK,"
	                + "    e.nombreEmpresa,"
	                + "    e.CIF,"
	                + "    e.direccionPostal,"
	                + "    e.localidad,"
	                + "    e.representanteLegal,"
	                + "    e.dniRepresentante,"
	                + "    e.emailEmpresa,"
	                + "    e.telefonoEmpresa,"
	                + "    e.tipoEmpresa "
	                + "FROM "
	                + "    ContactosEmpresa c "
	                + "JOIN "
	                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
	                + "WHERE "
	                + "    e.tipoEmpresa = 1 "
	                + "ORDER BY c.primerApellidoContacto;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				textArea.append(resultado.getString("primerApellidoContacto") + " ");
				textArea.append(resultado.getString("segundoApellidoContacto") + " ");
				textArea.append(resultado.getString("nombreContacto") +" | ");
				textArea.append(resultado.getString("dniContacto") + " | ");
				textArea.append(resultado.getString("telefonoContacto") + " | ");
				textArea.append(resultado.getString("emailContacto") + " | ");
				textArea.append(resultado.getString("esDual") + " | ");
				textArea.append(resultado.getString("nombreEmpresa") + "\n");
			}
			System.out.println("Listado de empresas de administración rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}

	}public void rellenarListadoContactoEmpresaAdministracionPorDni(JTextArea textArea) {
		String sentencia = "SELECT "
                + "    c.idContacto,"
                + "    c.nombreContacto,"
                + "    c.primerApellidoContacto,"
                + "    c.segundoApellidoContacto,"
                + "    c.dniContacto,"
                + "    c.telefonoContacto,"
                + "    c.emailContacto,"
                + "    c.esDual,"
                + "    c.idEmpresaFK,"
                + "    e.nombreEmpresa,"
                + "    e.CIF,"
                + "    e.direccionPostal,"
                + "    e.localidad,"
                + "    e.representanteLegal,"
                + "    e.dniRepresentante,"
                + "    e.emailEmpresa,"
                + "    e.telefonoEmpresa,"
                + "    e.tipoEmpresa "
                + "FROM "
                + "    ContactosEmpresa c "
                + "JOIN "
                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
                + "WHERE "
                + "    e.tipoEmpresa = 1 "
                + "ORDER BY c.dniContacto;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				textArea.append(resultado.getString("dniContacto") + " | ");
				textArea.append(resultado.getString("nombreContacto") + " | ");
				textArea.append(resultado.getString("primerApellidoContacto") + " ");
				textArea.append(resultado.getString("segundoApellidoContacto") + " | ");
				textArea.append(resultado.getString("telefonoContacto") + " | ");
				textArea.append(resultado.getString("emailContacto") + " | ");
				textArea.append(resultado.getString("esDual") + " | ");
				textArea.append(resultado.getString("nombreEmpresa") + "\n");
			}
			System.out.println("Listado de empresas de administración rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}

	}public void rellenarListadoContactoEmpresaAdministracionPorTelefono(JTextArea textArea) {
		String sentencia = "SELECT "
                + "    c.idContacto,"
                + "    c.nombreContacto,"
                + "    c.primerApellidoContacto,"
                + "    c.segundoApellidoContacto,"
                + "    c.dniContacto,"
                + "    c.telefonoContacto,"
                + "    c.emailContacto,"
                + "    c.esDual,"
                + "    c.idEmpresaFK,"
                + "    e.nombreEmpresa,"
                + "    e.CIF,"
                + "    e.direccionPostal,"
                + "    e.localidad,"
                + "    e.representanteLegal,"
                + "    e.dniRepresentante,"
                + "    e.emailEmpresa,"
                + "    e.telefonoEmpresa,"
                + "    e.tipoEmpresa "
                + "FROM "
                + "    ContactosEmpresa c "
                + "JOIN "
                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
                + "WHERE "
                + "    e.tipoEmpresa = 1 "
                + "ORDER BY c.telefonoContacto;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				textArea.append(resultado.getString("telefonoContacto") + " | ");
				textArea.append(resultado.getString("nombreContacto") + " | ");
				textArea.append(resultado.getString("primerApellidoContacto") + " ");
				textArea.append(resultado.getString("segundoApellidoContacto") + " | ");
				textArea.append(resultado.getString("dniContacto") + " | ");
				textArea.append(resultado.getString("emailContacto") + " | ");
				textArea.append(resultado.getString("esDual") + " ");
				textArea.append(resultado.getString("nombreEmpresa") + "\n");
			}
			System.out.println("Listado de empresas de administración rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}

	}
	public void rellenarListadoContactoEmpresaAdministracionPorEmail(JTextArea textArea) {
		String sentencia = "SELECT "
                + "    c.idContacto,"
                + "    c.nombreContacto,"
                + "    c.primerApellidoContacto,"
                + "    c.segundoApellidoContacto,"
                + "    c.dniContacto,"
                + "    c.telefonoContacto,"
                + "    c.emailContacto,"
                + "    c.esDual,"
                + "    c.idEmpresaFK,"
                + "    e.nombreEmpresa,"
                + "    e.CIF,"
                + "    e.direccionPostal,"
                + "    e.localidad,"
                + "    e.representanteLegal,"
                + "    e.dniRepresentante,"
                + "    e.emailEmpresa,"
                + "    e.telefonoEmpresa,"
                + "    e.tipoEmpresa "
                + "FROM "
                + "    ContactosEmpresa c "
                + "JOIN "
                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
                + "WHERE "
                + "    e.tipoEmpresa = 1 "
                + "ORDER BY e.emailContacto;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				textArea.append(resultado.getString("emailContacto") + " | ");
				textArea.append(resultado.getString("nombreEmpresa") + " | ");
				textArea.append(resultado.getString("nombreContacto") + " | ");
				textArea.append(resultado.getString("primerApellidoContacto") + " | ");
				textArea.append(resultado.getString("segundoApellidoContacto") + " | ");
				textArea.append(resultado.getString("dniContacto") + " | ");
				textArea.append(resultado.getString("telefonoContacto") + " | ");
				textArea.append(resultado.getString("esDual") + "\n");
			}
			System.out.println("Listado de empresas de administración rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}

	}
	public void rellenarListadoContactoEmpresaAdministracionPorEmpresa(JTextArea textArea) {
		String sentencia = "SELECT "
                + "    c.idContacto,"
                + "    c.nombreContacto,"
                + "    c.primerApellidoContacto,"
                + "    c.segundoApellidoContacto,"
                + "    c.dniContacto,"
                + "    c.telefonoContacto,"
                + "    c.emailContacto,"
                + "    c.esDual,"
                + "    c.idEmpresaFK,"
                + "    e.nombreEmpresa,"
                + "    e.CIF,"
                + "    e.direccionPostal,"
                + "    e.localidad,"
                + "    e.representanteLegal,"
                + "    e.dniRepresentante,"
                + "    e.emailEmpresa,"
                + "    e.telefonoEmpresa,"
                + "    e.tipoEmpresa "
                + "FROM "
                + "    ContactosEmpresa c "
                + "JOIN "
                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
                + "WHERE "
                + "    e.tipoEmpresa = 1 "
                + "ORDER BY e.nombreEmpresa;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				textArea.append(resultado.getString("nombreEmpresa") + " | ");
				textArea.append(resultado.getString("nombreContacto") + " | ");
				textArea.append(resultado.getString("primerApellidoContacto") + " | ");
				textArea.append(resultado.getString("segundoApellidoContacto") + " | ");
				textArea.append(resultado.getString("dniContacto") + " | ");
				textArea.append(resultado.getString("telefonoContacto") + " | ");
				textArea.append(resultado.getString("emailContacto") + " | ");
				textArea.append(resultado.getString("esDual") + "\n");
			}
			System.out.println("Listado de empresas de administración rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}

	}
	public int altaContactoEmpresaEnfermeria(String sentencia) {
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
			System.out.println("Alta de empresa de ContactoEmpresaAdmini ejecutada correctamente.");
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error 13-" + sqle.getMessage());
			return 1;
		}
	}
	public void rellenarListadoContactoEmpresaEnfermeria(JTextArea textArea) {
        String sentencia = "SELECT "
                + "    c.idContacto,"
                + "    c.nombreContacto,"
                + "    c.primerApellidoContacto,"
                + "    c.segundoApellidoContacto,"
                + "    c.dniContacto,"
                + "    c.telefonoContacto,"
                + "    c.emailContacto,"
                + "    c.esDual,"
                + "    c.idEmpresaFK,"
                + "    e.nombreEmpresa,"
                + "    e.CIF,"
                + "    e.direccionPostal,"
                + "    e.localidad,"
                + "    e.representanteLegal,"
                + "    e.dniRepresentante,"
                + "    e.emailEmpresa,"
                + "    e.telefonoEmpresa,"
                + "    e.tipoEmpresa "
                + "FROM "
                + "    ContactosEmpresa c "
                + "JOIN "
                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
                + "WHERE "
                + "    e.tipoEmpresa = 2 "
                + "ORDER BY c.nombreContacto;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("nombreContacto") + " | " +
                    resultado.getString("primerApellidoContacto") + " " +
                    resultado.getString("segundoApellidoContacto") + " | " +
                    resultado.getString("dniContacto") + " | " +
                    resultado.getString("telefonoContacto") + " | " +
                    resultado.getString("emailContacto") + " | " +
                    resultado.getString("esDual") + " | " +
                    resultado.getString("nombreEmpresa") + "\n"
                );
            }
            System.out.println("Listado de contactos de empresas de enfermería rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de contactos de empresas de enfermería: " + sqle.getMessage());
        }
    }

    // Método para rellenar el listado de contactos de empresas de enfermería ordenado por apellido
    public void rellenarListadoContactoEmpresaEnfermeriaPorApellido(JTextArea textArea) {
        String sentencia = "SELECT "
                + "    c.idContacto,"
                + "    c.nombreContacto,"
                + "    c.primerApellidoContacto,"
                + "    c.segundoApellidoContacto,"
                + "    c.dniContacto,"
                + "    c.telefonoContacto,"
                + "    c.emailContacto,"
                + "    c.esDual,"
                + "    c.idEmpresaFK,"
                + "    e.nombreEmpresa,"
                + "    e.CIF,"
                + "    e.direccionPostal,"
                + "    e.localidad,"
                + "    e.representanteLegal,"
                + "    e.dniRepresentante,"
                + "    e.emailEmpresa,"
                + "    e.telefonoEmpresa,"
                + "    e.tipoEmpresa "
                + "FROM "
                + "    ContactosEmpresa c "
                + "JOIN "
                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
                + "WHERE "
                + "    e.tipoEmpresa = 2 "
                + "ORDER BY c.primerApellidoContacto, c.segundoApellidoContacto;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("primerApellidoContacto") + " " +
                    resultado.getString("segundoApellidoContacto") + " | " +
                    resultado.getString("nombreContacto") + " | " +
                    resultado.getString("dniContacto") + " | " +
                    resultado.getString("telefonoContacto") + " | " +
                    resultado.getString("emailContacto") + " | " +
                    resultado.getString("esDual") + " | " +
                    resultado.getString("nombreEmpresa") + "\n"
                );
            }
            System.out.println("Listado de contactos de empresas de enfermería ordenado por apellido rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de contactos de empresas de enfermería ordenado por apellido: " + sqle.getMessage());
        }
    }

    // Método para rellenar el listado de contactos de empresas de enfermería ordenado por DNI
    public void rellenarListadoContactoEmpresaEnfermeriaPorDNI(JTextArea textArea) {
        String sentencia = "SELECT "
                + "    c.idContacto,"
                + "    c.nombreContacto,"
                + "    c.primerApellidoContacto,"
                + "    c.segundoApellidoContacto,"
                + "    c.dniContacto,"
                + "    c.telefonoContacto,"
                + "    c.emailContacto,"
                + "    c.esDual,"
                + "    c.idEmpresaFK,"
                + "    e.nombreEmpresa,"
                + "    e.CIF,"
                + "    e.direccionPostal,"
                + "    e.localidad,"
                + "    e.representanteLegal,"
                + "    e.dniRepresentante,"
                + "    e.emailEmpresa,"
                + "    e.telefonoEmpresa,"
                + "    e.tipoEmpresa "
                + "FROM "
                + "    ContactosEmpresa c "
                + "JOIN "
                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
                + "WHERE "
                + "    e.tipoEmpresa = 2 "
                + "ORDER BY c.dniContacto;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("dniContacto") + " | " +
                    resultado.getString("nombreContacto") + " | " +
                    resultado.getString("primerApellidoContacto") + " " +
                    resultado.getString("segundoApellidoContacto") + " | " +
                    resultado.getString("telefonoContacto") + " | " +
                    resultado.getString("emailContacto") + " | " +
                    resultado.getString("esDual") + " | " +
                    resultado.getString("nombreEmpresa") + "\n"
                );
            }
            System.out.println("Listado de contactos de empresas de enfermería ordenado por DNI rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de contactos de empresas de enfermería ordenado por DNI: " + sqle.getMessage());
        }
    }

    // Método para rellenar el listado de contactos de empresas de enfermería ordenado por teléfono
    public void rellenarListadoContactoEmpresaEnfermeriaPorTelefono(JTextArea textArea) {
        String sentencia = "SELECT "
                + "    c.idContacto,"
                + "    c.nombreContacto,"
                + "    c.primerApellidoContacto,"
                + "    c.segundoApellidoContacto,"
                + "    c.dniContacto,"
                + "    c.telefonoContacto,"
                + "    c.emailContacto,"
                + "    c.esDual,"
                + "    c.idEmpresaFK,"
                + "    e.nombreEmpresa,"
                + "    e.CIF,"
                + "    e.direccionPostal,"
                + "    e.localidad,"
                + "    e.representanteLegal,"
                + "    e.dniRepresentante,"
                + "    e.emailEmpresa,"
                + "    e.telefonoEmpresa,"
                + "    e.tipoEmpresa "
                + "FROM "
                + "    ContactosEmpresa c "
                + "JOIN "
                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
                + "WHERE "
                + "    e.tipoEmpresa = 2 "
                + "ORDER BY c.telefonoContacto;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("telefonoContacto") + " | " +
                    resultado.getString("nombreContacto") + " | " +
                    resultado.getString("primerApellidoContacto") + " " +
                    resultado.getString("segundoApellidoContacto") + " | " +
                    resultado.getString("dniContacto") + " | " +
                    resultado.getString("emailContacto") + " | " +
                    resultado.getString("esDual") + " | " +
                    resultado.getString("nombreEmpresa") + "\n"
                );
            }
            System.out.println("Listado de contactos de empresas de enfermería ordenado por teléfono rellenado correctamente");
        } catch (SQLException sqle) {
        	System.out.println("Error al rellenar listado de contactos de empresas de enfermería ordenado por teléfono: " + sqle.getMessage());
        }
    }
 // Método para rellenar el listado de contactos de empresas de enfermería ordenado por email
    public void rellenarListadoContactoEmpresaEnfermeriaPorEmail(JTextArea textArea) {
        String sentencia = "SELECT "
                + "    c.idContacto,"
                + "    c.nombreContacto,"
                + "    c.primerApellidoContacto,"
                + "    c.segundoApellidoContacto,"
                + "    c.dniContacto,"
                + "    c.telefonoContacto,"
                + "    c.emailContacto,"
                + "    c.esDual,"
                + "    c.idEmpresaFK,"
                + "    e.nombreEmpresa,"
                + "    e.CIF,"
                + "    e.direccionPostal,"
                + "    e.localidad,"
                + "    e.representanteLegal,"
                + "    e.dniRepresentante,"
                + "    e.emailEmpresa,"
                + "    e.telefonoEmpresa,"
                + "    e.tipoEmpresa "
                + "FROM "
                + "    ContactosEmpresa c "
                + "JOIN "
                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
                + "WHERE "
                + "    e.tipoEmpresa = 2 "
                + "ORDER BY e.emailEmpresa;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("emailContacto") + " | " +
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("nombreContacto") + " | " +
                    resultado.getString("primerApellidoContacto") + " | " +
                    resultado.getString("segundoApellidoContacto") + " | " +
                    resultado.getString("dniContacto") + " | " +
                    resultado.getString("telefonoContacto") + " | " +
                    resultado.getString("esDual") + "\n"
                );
            }
            System.out.println("Listado de contactos de empresas de enfermería ordenado por email rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de contactos de empresas de enfermería ordenado por email: " + sqle.getMessage());
        }
    }

    // Método para rellenar el listado de contactos de empresas de enfermería ordenado por nombre de empresa
    public void rellenarListadoContactoEmpresaEnfermeriaPorEmpresa(JTextArea textArea) {
        String sentencia = "SELECT "
                + "    c.idContacto,"
                + "    c.nombreContacto,"
                + "    c.primerApellidoContacto,"
                + "    c.segundoApellidoContacto,"
                + "    c.dniContacto,"
                + "    c.telefonoContacto,"
                + "    c.emailContacto,"
                + "    c.esDual,"
                + "    c.idEmpresaFK,"
                + "    e.nombreEmpresa,"
                + "    e.CIF,"
                + "    e.direccionPostal,"
                + "    e.localidad,"
                + "    e.representanteLegal,"
                + "    e.dniRepresentante,"
                + "    e.emailEmpresa,"
                + "    e.telefonoEmpresa,"
                + "    e.tipoEmpresa "
                + "FROM "
                + "    ContactosEmpresa c "
                + "JOIN "
                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
                + "WHERE "
                + "    e.tipoEmpresa = 2 "
                + "ORDER BY e.nombreEmpresa;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("nombreContacto") + " | " +
                    resultado.getString("primerApellidoContacto") + " | " +
                    resultado.getString("segundoApellidoContacto") + " | " +
                    resultado.getString("dniContacto") + " | " +
                    resultado.getString("telefonoContacto") + " | " +
                    resultado.getString("emailContacto") + " | " +
                    resultado.getString("esDual") + "\n"
                );
            }
            System.out.println("Listado de contactos de empresas de enfermería ordenado por nombre de empresa rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de contactos de empresas de enfermería ordenado por nombre de empresa: " + sqle.getMessage());
        }
    }

	public int altaContactoEmpresaEduc(String sentencia) {
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
			System.out.println("Alta de empresa de ContactoEmpresaAdmini ejecutada correctamente.");
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error 13-" + sqle.getMessage());
			return 1;
		}
	}
	   // Método para rellenar el listado de contactos de empresas de educación ordenado por nombre
    public void rellenarListadoContactoEmpresaEducacion(JTextArea textArea) {
        String sentencia = "SELECT "
                + "    c.idContacto,"
                + "    c.nombreContacto,"
                + "    c.primerApellidoContacto,"
                + "    c.segundoApellidoContacto,"
                + "    c.dniContacto,"
                + "    c.telefonoContacto,"
                + "    c.emailContacto,"
                + "    c.esDual,"
                + "    c.idEmpresaFK,"
                + "    e.nombreEmpresa,"
                + "    e.CIF,"
                + "    e.direccionPostal,"
                + "    e.localidad,"
                + "    e.representanteLegal,"
                + "    e.dniRepresentante,"
                + "    e.emailEmpresa,"
                + "    e.telefonoEmpresa,"
                + "    e.tipoEmpresa "
                + "FROM "
                + "    ContactosEmpresa c "
                + "JOIN "
                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
                + "WHERE "
                + "    e.tipoEmpresa = 3 "
                + "ORDER BY c.nombreContacto;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("nombreContacto") + " | " +
                    resultado.getString("primerApellidoContacto") + " " +
                    resultado.getString("segundoApellidoContacto") + " | " +
                    resultado.getString("dniContacto") + " | " +
                    resultado.getString("telefonoContacto") + " | " +
                    resultado.getString("emailContacto") + " | " +
                    resultado.getString("esDual") + " | " +
                    resultado.getString("nombreEmpresa") + "\n"
                );
            }
            System.out.println("Listado de contactos de empresas de educación ordenado por nombre rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de contactos de empresas de educación ordenado por nombre: " + sqle.getMessage());
        }
    }

    // Método para rellenar el listado de contactos de empresas de educación ordenado por apellido
    public void rellenarListadoContactoEmpresaEducacionPorApellido(JTextArea textArea) {
        String sentencia = "SELECT "
                + "    c.idContacto,"
                + "    c.nombreContacto,"
                + "    c.primerApellidoContacto,"
                + "    c.segundoApellidoContacto,"
                + "    c.dniContacto,"
                + "    c.telefonoContacto,"
                + "    c.emailContacto,"
                + "    c.esDual,"
                + "    c.idEmpresaFK,"
                + "    e.nombreEmpresa,"
                + "    e.CIF,"
                + "    e.direccionPostal,"
                + "    e.localidad,"
                + "    e.representanteLegal,"
                + "    e.dniRepresentante,"
                + "    e.emailEmpresa,"
                + "    e.telefonoEmpresa,"
                + "    e.tipoEmpresa "
                + "FROM "
                + "    ContactosEmpresa c "
                + "JOIN "
                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
                + "WHERE "
                + "    e.tipoEmpresa = 3 "
                + "ORDER BY c.primerApellidoContacto;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("primerApellidoContacto") + " " +
                    resultado.getString("segundoApellidoContacto") + " | " +
                    resultado.getString("nombreContacto") + " | " +
                    resultado.getString("dniContacto") + " | " +
                    resultado.getString("telefonoContacto") + " | " +
                    resultado.getString("emailContacto") + " | " +
                    resultado.getString("esDual") + " | " +
                    resultado.getString("nombreEmpresa") + "\n"
                );
            }
            System.out.println("Listado de contactos de empresas de educación ordenado por apellido rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de contactos de empresas de educación ordenado por apellido: " + sqle.getMessage());
        }
    }

    // Método para rellenar el listado de contactos de empresas de educación ordenado por DNI
    public void rellenarListadoContactoEmpresaEducacionPorDni(JTextArea textArea) {
        String sentencia = "SELECT "
                + "    c.idContacto,"
                + "    c.nombreContacto,"
                + "    c.primerApellidoContacto,"
                + "    c.segundoApellidoContacto,"
                + "    c.dniContacto,"
                + "    c.telefonoContacto,"
                + "    c.emailContacto,"
                + "    c.esDual,"
                + "    c.idEmpresaFK,"
                + "    e.nombreEmpresa,"
                + "    e.CIF,"
                + "    e.direccionPostal,"
                + "    e.localidad,"
                + "    e.representanteLegal,"
                + "    e.dniRepresentante,"
                + "    e.emailEmpresa,"
                + "    e.telefonoEmpresa,"
                + "    e.tipoEmpresa "
                + "FROM "
                + "    ContactosEmpresa c "
                + "JOIN "
                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
                + "WHERE "
                + "    e.tipoEmpresa = 3 "
                + "ORDER BY c.dniContacto;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("dniContacto") + " | " +
                    resultado.getString("nombreContacto") + " | " +
                    resultado.getString("primerApellidoContacto") + " " +
                    resultado.getString("segundoApellidoContacto") + " | " +
                    resultado.getString("telefonoContacto") + " | " +
                    resultado.getString("emailContacto") + " | " +
                    resultado.getString("esDual") + " | " +
                    resultado.getString("nombreEmpresa") + "\n"
                );
            }
            System.out.println("Listado de contactos de empresas de educación ordenado por DNI rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de contactos de empresas de educación ordenado por DNI: " + sqle.getMessage());
        }
    }

    // Método para rellenar el listado de contactos de empresas de educación ordenado por teléfono
    public void rellenarListadoContactoEmpresaEducacionPorTelefono(JTextArea textArea) {
        String sentencia = "SELECT "
                + "    c.idContacto,"
                + "    c.nombreContacto,"
                + "    c.primerApellidoContacto,"
                + "    c.segundoApellidoContacto,"
                + "    c.dniContacto,"
                + "    c.telefonoContacto,"
                + "    c.emailContacto,"
                + "    c.esDual,"
                + "    c.idEmpresaFK,"
                + "    e.nombreEmpresa,"
                + "    e.CIF,"
                + "    e.direccionPostal,"
                + "    e.localidad,"
                + "    e.representanteLegal,"
                + "    e.dniRepresentante,"
                + "    e.emailEmpresa,"
                + "    e.telefonoEmpresa,"
                + "    e.tipoEmpresa "
                + "FROM "
                + "    ContactosEmpresa c "
                + "JOIN "
                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
                + "WHERE "
                + "    e.tipoEmpresa = 3 "
                + "ORDER BY c.telefonoContacto;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("telefonoContacto") + " | " +
                    resultado.getString("nombreContacto") + " | " +
                    resultado.getString("primerApellidoContacto") + " " +
                    resultado.getString ("segundoApellidoContacto") + " | " +
                    resultado.getString("dniContacto") + " | " +
                    resultado.getString("emailContacto") + " | " +
                    resultado.getString("esDual") + " | " +
                    resultado.getString("nombreEmpresa") + "\n"
                );
            }
            System.out.println("Listado de contactos de empresas de educación ordenado por teléfono rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de contactos de empresas de educación ordenado por teléfono: " + sqle.getMessage());
        }
    }

    // Método para rellenar el listado de contactos de empresas de educación ordenado por email
    public void rellenarListadoContactoEmpresaEducacionPorEmail(JTextArea textArea) {
        String sentencia = "SELECT "
                + "    c.idContacto,"
                + "    c.nombreContacto,"
                + "    c.primerApellidoContacto,"
                + "    c.segundoApellidoContacto,"
                + "    c.dniContacto,"
                + "    c.telefonoContacto,"
                + "    c.emailContacto,"
                + "    c.esDual,"
                + "    c.idEmpresaFK,"
                + "    e.nombreEmpresa,"
                + "    e.CIF,"
                + "    e.direccionPostal,"
                + "    e.localidad,"
                + "    e.representanteLegal,"
                + "    e.dniRepresentante,"
                + "    e.emailEmpresa,"
                + "    e.telefonoEmpresa,"
                + "    e.tipoEmpresa "
                + "FROM "
                + "    ContactosEmpresa c "
                + "JOIN "
                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
                + "WHERE "
                + "    e.tipoEmpresa = 3 "
                + "ORDER BY c.emailContacto;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("emailContacto") + " | " +
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("nombreContacto") + " | " +
                    resultado.getString("primerApellidoContacto") + " | " +
                    resultado.getString("segundoApellidoContacto") + " | " +
                    resultado.getString("dniContacto") + " | " +
                    resultado.getString("telefonoContacto") + " | " +
                    resultado.getString("esDual") + "\n"
                );
            }
            System.out.println("Listado de contactos de empresas de educación ordenado por email rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de contactos de empresas de educación ordenado por email: " + sqle.getMessage());
        }
    }

    // Método para rellenar el listado de contactos de empresas de educación ordenado por empresa
    public void rellenarListadoContactoEmpresaEducacionPorEmpresa(JTextArea textArea) {
        String sentencia = "SELECT "
                + "    c.idContacto,"
                + "    c.nombreContacto,"
                + "    c.primerApellidoContacto,"
                + "    c.segundoApellidoContacto,"
                + "    c.dniContacto,"
                + "    c.telefonoContacto,"
                + "    c.emailContacto,"
                + "    c.esDual,"
                + "    c.idEmpresaFK,"
                + "    e.nombreEmpresa,"
                + "    e.CIF,"
                + "    e.direccionPostal,"
                + "    e.localidad,"
                + "    e.representanteLegal,"
                + "    e.dniRepresentante,"
                + "    e.emailEmpresa,"
                + "    e.telefonoEmpresa,"
                + "    e.tipoEmpresa "
                + "FROM "
                + "    ContactosEmpresa c "
                + "JOIN "
                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
                + "WHERE "
                + "    e.tipoEmpresa = 3 "
                + "ORDER BY e.nombreEmpresa;";
        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
            ResultSet resultado = statement.executeQuery();
            while (resultado.next()) {
                textArea.append(
                    resultado.getString("nombreEmpresa") + " | " +
                    resultado.getString("nombreContacto") + " | " +
                    resultado.getString("primerApellidoContacto") + " | " +
                    resultado.getString("segundoApellidoContacto") + " | " +
                    resultado.getString("dniContacto") + " | " +
                    resultado.getString("telefonoContacto") + " | " +
                    resultado.getString("emailContacto") + " | " +
                    resultado.getString("esDual") + "\n"
                );
            }
            System.out.println("Listado de contactos de empresas de educación ordenado por empresa rellenado correctamente.");
        } catch (SQLException sqle) {
            System.out.println("Error al rellenar listado de contactos de empresas de educación ordenado por empresa: " + sqle.getMessage());
        }
    }



	public int altaContactoEmpresaEducOnline(String sentencia) {
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
			System.out.println("Alta de empresa de ContactoEmpresaEducOnline ejecutada correctamente.");
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error 13-" + sqle.getMessage());
			return 1;
		}
	}
	 public void rellenarListadoContactoEmpresaEducacionOnline(JTextArea textArea) {
	        String sentencia = "SELECT "
	                + "    c.idContacto,"
	                + "    c.nombreContacto,"
	                + "    c.primerApellidoContacto,"
	                + "    c.segundoApellidoContacto,"
	                + "    c.dniContacto,"
	                + "    c.telefonoContacto,"
	                + "    c.emailContacto,"
	                + "    c.esDual,"
	                + "    c.idEmpresaFK,"
	                + "    e.nombreEmpresa,"
	                + "    e.CIF,"
	                + "    e.direccionPostal,"
	                + "    e.localidad,"
	                + "    e.representanteLegal,"
	                + "    e.dniRepresentante,"
	                + "    e.emailEmpresa,"
	                + "    e.telefonoEmpresa,"
	                + "    e.tipoEmpresa "
	                + "FROM "
	                + "    ContactosEmpresa c "
	                + "JOIN "
	                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
	                + "WHERE "
	                + "    e.tipoEmpresa = 4 "
	                + "ORDER BY c.nombreContacto;";
	        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	            ResultSet resultado = statement.executeQuery();
	            while (resultado.next()) {
	                textArea.append(
	                    resultado.getString("nombreContacto") + " | " +
	                    resultado.getString("primerApellidoContacto") + " | " +
	                    resultado.getString("segundoApellidoContacto") + " | " +
	                    resultado.getString("dniContacto") + " | " +
	                    resultado.getString("telefonoContacto") + " | " +
	                    resultado.getString("emailContacto") + " | " +
	                    resultado.getString("esDual") + " | " +
	                    resultado.getString("nombreEmpresa") + "\n"
	                );
	            }
	            System.out.println("Listado de contactos de empresas de educación online ordenado por nombre rellenado correctamente.");
	        } catch (SQLException sqle) {
	            System.out.println("Error al rellenar listado de contactos de empresas de educación online ordenado por nombre: " + sqle.getMessage());
	        }
	    }

	    // Método para rellenar el listado de contactos de empresas de educación online ordenado por apellido
	    public void rellenarListadoContactoEmpresaEducacionOnlinePorApellido(JTextArea textArea) {
	        String sentencia = "SELECT "
	                + "    c.idContacto,"
	                + "    c.nombreContacto,"
	                + "    c.primerApellidoContacto,"
	                + "    c.segundoApellidoContacto,"
	                + "    c.dniContacto,"
	                + "    c.telefonoContacto,"
	                + "    c.emailContacto,"
	                + "    c.esDual,"
	                + "    c.idEmpresaFK,"
	                + "    e.nombreEmpresa,"
	                + "    e.CIF,"
	                + "    e.direccionPostal,"
	                + "    e.localidad,"
	                + "    e.representanteLegal,"
	                + "    e.dniRepresentante,"
	                + "    e.emailEmpresa,"
	                + "    e.telefonoEmpresa,"
	                + "    e.tipoEmpresa "
	                + "FROM "
	                + "    ContactosEmpresa c "
	                + "JOIN "
	                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
	                + "WHERE "
	                + "    e.tipoEmpresa = 4 "
	                + "ORDER BY c.primerApellidoContacto;";
	        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	            ResultSet resultado = statement.executeQuery();
	            while (resultado.next()) {
	                textArea.append(
	                    resultado.getString("primerApellidoContacto") + " | " +
	                    resultado.getString("segundoApellidoContacto") + " | " +
	                    resultado.getString("nombreContacto") + " | " +
	                    resultado.getString("dniContacto") + " | " +
	                    resultado.getString("telefonoContacto") + " | " +
	                    resultado.getString("emailContacto") + " | " +
	                    resultado.getString("esDual") + " | " +
	                    resultado.getString("nombreEmpresa") + "\n"
	                );
	            }
	            System.out.println("Listado de contactos de empresas de educación online ordenado por apellido rellenado correctamente.");
	        } catch (SQLException sqle) {
	            System.out.println("Error al rellenar listado de contactos de empresas de educación online ordenado por apellido: " + sqle.getMessage());
	        }
	    }

	    // Método para rellenar el listado de contactos de empresas de educación online ordenado por DNI
	    public void rellenarListadoContactoEmpresaEducacionOnlinePorDni(JTextArea textArea) {
	        String sentencia = "SELECT "
	                + "    c.idContacto,"
	                + "    c.nombreContacto,"
	                + "    c.primerApellidoContacto,"
	                + "    c.segundoApellidoContacto,"
	                + "    c.dniContacto,"
	                + "    c.telefonoContacto,"
	                + "    c.emailContacto,"
	                + "    c.esDual,"
	                + "    c.idEmpresaFK,"
	                + "    e.nombreEmpresa,"
	                + "    e.CIF,"
	                + "    e.direccionPostal,"
	                + "    e.localidad,"
	                + "    e.representanteLegal,"
	                + "    e.dniRepresentante,"
	                + "    e.emailEmpresa,"
	                + "    e.telefonoEmpresa,"
	                + "    e.tipoEmpresa "
	                + "FROM "
	                + "    ContactosEmpresa c "
	                + "JOIN "
	                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
	                + "WHERE "
	                + "    e.tipoEmpresa = 4 "
	                + "ORDER BY c.dniContacto;";
	        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	            ResultSet resultado = statement.executeQuery();
	            while (resultado.next()) {
	                textArea.append(
	                    resultado.getString("dniContacto") + " | " +
	                    resultado.getString("nombreContacto") + " | " +
	                    resultado.getString("primerApellidoContacto") + " | " +
	                    resultado.getString("segundoApellidoContacto") + " | " +
	                    resultado.getString("telefonoContacto") + " | " +
	                    resultado.getString("emailContacto") + " | " +
	                    resultado.getString("esDual") + " | " +
	                    resultado.getString("nombreEmpresa") + "\n"
	                );
	            }
	            System.out.println("Listado de contactos de empresas de educación online ordenado por DNI rellenado correctamente.");
	        } catch (SQLException sqle) {
	            System.out.println("Error al rellenar listado de contactos de empresas de educación online ordenado por DNI: " + sqle.getMessage());
	        }
	    }

	    // Método para rellenar el listado de contactos de empresas de educación online ordenado por teléfono
	    public void rellenarListadoContactoEmpresaEducacionOnlinePorTelefono(JTextArea textArea) {
	        String sentencia = "SELECT "
	                + "    c.idContacto,"
	                + "    c.nombreContacto,"
	                + "    c.primerApellidoContacto,"
	                + "    c.segundoApellidoContacto,"
	                + "    c.dniContacto,"
	                + "    c.telefonoContacto,"
	                + "    c.emailContacto,"
	                + "    c.esDual,"
	                + "    c.idEmpresaFK,"
	                + "    e.nombreEmpresa,"
	                + "    e.CIF,"
	                + "    e.direccionPostal,"
	                + "    e.localidad,"
	                + "    e.representanteLegal,"
	                + "    e.dniRepresentante,"
	                + "    e.emailEmpresa,"
	                + "    e.telefonoEmpresa,"
	                + "    e.tipoEmpresa "
	                + "FROM "
	                + "    ContactosEmpresa c "
	                + "JOIN "
	                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
	                + "WHERE "
	                + "    e.tipoEmpresa = 4 "
	                + "ORDER BY c.telefonoContacto;";
	        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	            ResultSet resultado = statement.executeQuery();
	            while (resultado.next()) {
	                textArea.append(
	                    resultado.getString("telefonoContacto") + " | " +
	                    resultado.getString("nombreContacto") + " | " +
	                    resultado.getString("primerApellidoContacto") + " | " +
	                    resultado.getString("segundoApellidoContacto") + " | " +
	                    resultado.getString("dniContacto") + " | " +
	                    resultado.getString("emailContacto") + " | " +
	                    resultado.getString("esDual") + " | " +
	                    resultado.getString("nombreEmpresa") + "\n"
	                );
	            }
	            System.out.println("Listado de contactos de empresas de educación ordenado por teléfono rellenado correctamente.");
	        } catch (SQLException sqle) {
	            System.out.println("Error al rellenar listado de contactos de empresas de educación ordenado por teléfono: " + sqle.getMessage());
	        }
	    }

	    // Método para rellenar el listado de contactos de empresas de educación ordenado por email
	    public void rellenarListadoContactoEmpresaEducacionOnlinePorEmail(JTextArea textArea) {
	        String sentencia = "SELECT "
	                + "    c.idContacto,"
	                + "    c.nombreContacto,"
	                + "    c.primerApellidoContacto,"
	                + "    c.segundoApellidoContacto,"
	                + "    c.dniContacto,"
	                + "    c.telefonoContacto,"
	                + "    c.emailContacto,"
	                + "    c.esDual,"
	                + "    c.idEmpresaFK,"
	                + "    e.nombreEmpresa,"
	                + "    e.CIF,"
	                + "    e.direccionPostal,"
	                + "    e.localidad,"
	                + "    e.representanteLegal,"
	                + "    e.dniRepresentante,"
	                + "    e.emailEmpresa,"
	                + "    e.telefonoEmpresa,"
	                + "    e.tipoEmpresa "
	                + "FROM "
	                + "    ContactosEmpresa c "
	                + "JOIN "
	                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
	                + "WHERE "
	                + "    e.tipoEmpresa = 4 "
	                + "ORDER BY c.emailContacto;";
	        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	            ResultSet resultado = statement.executeQuery();
	            while (resultado.next()) {
	                textArea.append(
	                    resultado.getString("emailContacto") + " | " +
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("nombreContacto") + " | " +
	                    resultado.getString("primerApellidoContacto") + " | " +
	                    resultado.getString("segundoApellidoContacto") + " | " +
	                    resultado.getString("dniContacto") + " | " +
	                    resultado.getString("telefonoContacto") + " | " +
	                    resultado.getString("esDual") + "\n"
	                );
	            }
	            System.out.println("Listado de contactos de empresas de educación ordenado por email rellenado correctamente.");
	        } catch (SQLException sqle) {
	            System.out.println("Error al rellenar listado de contactos de empresas de educación ordenado por email: " + sqle.getMessage());
	        }
	    }

	    // Método para rellenar el listado de contactos de empresas de educación ordenado por empresa
	    public void rellenarListadoContactoEmpresaEducacionOnlinePorEmpresa(JTextArea textArea) {
	        String sentencia = "SELECT "
	                + "    c.idContacto,"
	                + "    c.nombreContacto,"
	                + "    c.primerApellidoContacto,"
	                + "    c.segundoApellidoContacto,"
	                + "    c.dniContacto,"
	                + "    c.telefonoContacto,"
	                + "    c.emailContacto,"
	                + "    c.esDual,"
	                + "    c.idEmpresaFK,"
	                + "    e.nombreEmpresa,"
	                + "    e.CIF,"
	                + "    e.direccionPostal,"
	                + "    e.localidad,"
	                + "    e.representanteLegal,"
	                + "    e.dniRepresentante,"
	                + "    e.emailEmpresa,"
	                + "    e.telefonoEmpresa,"
	                + "    e.tipoEmpresa "
	                + "FROM "
	                + "    ContactosEmpresa c "
	                + "JOIN "
	                + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
	                + "WHERE "
	                + "    e.tipoEmpresa = 4 "
	                + "ORDER BY e.nombreEmpresa;";
	        try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	            ResultSet resultado = statement.executeQuery();
	            while (resultado.next()) {
	                textArea.append(
	                    resultado.getString("nombreEmpresa") + " | " +
	                    resultado.getString("nombreContacto") + " | " +
	                    resultado.getString("primerApellidoContacto") + " | " +
	                    resultado.getString("segundoApellidoContacto") + " | " +
	                    resultado.getString("dniContacto") + " | " +
	                    resultado.getString("telefonoContacto") + " | " +
	                    resultado.getString("emailContacto") + " | " +
	                    resultado.getString("esDual") + "\n"
	                );
	            }
	            System.out.println("Listado de contactos de empresas de educación ordenado por empresa rellenado correctamente.");
	        } catch (SQLException sqle) {
	            System.out.println("Error al rellenar listado de contactos de empresas de educación ordenado por empresa: " + sqle.getMessage());
	        }
	    }
	
	                    
	     public int altaContactoEmpresaAsis(String sentencia) {
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(sentencia);
			System.out.println("Alta de empresa de ContactoEmpresaAdmini ejecutada correctamente.");
			return 0;
		} catch (SQLException sqle) {
			System.out.println("Error 13-" + sqle.getMessage());
			return 1;
		}
	}
	     public void rellenarListadoContactoEmpresaAsistencia(JTextArea textArea) {
	         String sentencia = "SELECT "
	                 + "    c.idContacto,"
	                 + "    c.nombreContacto,"
	                 + "    c.primerApellidoContacto,"
	                 + "    c.segundoApellidoContacto,"
	                 + "    c.dniContacto,"
	                 + "    c.telefonoContacto,"
	                 + "    c.emailContacto,"
	                 + "    c.esDual,"
	                 + "    c.idEmpresaFK,"
	                 + "    e.nombreEmpresa,"
	                 + "    e.CIF,"
	                 + "    e.direccionPostal,"
	                 + "    e.localidad,"
	                 + "    e.representanteLegal,"
	                 + "    e.dniRepresentante,"
	                 + "    e.emailEmpresa,"
	                 + "    e.telefonoEmpresa,"
	                 + "    e.tipoEmpresa "
	                 + "FROM "
	                 + "    ContactosEmpresa c "
	                 + "JOIN "
	                 + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
	                 + "WHERE "
	                 + "    e.tipoEmpresa = 5 "
	                 + "ORDER BY c.nombreContacto;";
	         try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	             ResultSet resultado = statement.executeQuery();
	             while (resultado.next()) {
	                 textArea.append(
	                     resultado.getString("nombreContacto") + " | " +
	                     resultado.getString("primerApellidoContacto") + " " +
	                     resultado.getString("segundoApellidoContacto") + " | " +
	                     resultado.getString("dniContacto") + " | " +
	                     resultado.getString("telefonoContacto") + " | " +
	                     resultado.getString("emailContacto") + " | " +
	                     resultado.getString("esDual") + " | " +
	                     resultado.getString("nombreEmpresa") + "\n"
	                 );
	             }
	             System.out.println("Listado de contactos de empresas de educación ordenado por nombre rellenado correctamente.");
	         } catch (SQLException sqle) {
	             System.out.println("Error al rellenar listado de contactos de empresas de educación ordenado por nombre: " + sqle.getMessage());
	         }
	     }

	     // Método para rellenar el listado de contactos de empresas de educación ordenado por apellido
	     public void rellenarListadoContactoEmpresaAsistenciaPorApellido(JTextArea textArea) {
	         String sentencia = "SELECT "
	                 + "    c.idContacto,"
	                 + "    c.nombreContacto,"
	                 + "    c.primerApellidoContacto,"
	                 + "    c.segundoApellidoContacto,"
	                 + "    c.dniContacto,"
	                 + "    c.telefonoContacto,"
	                 + "    c.emailContacto,"
	                 + "    c.esDual,"
	                 + "    c.idEmpresaFK,"
	                 + "    e.nombreEmpresa,"
	                 + "    e.CIF,"
	                 + "    e.direccionPostal,"
	                 + "    e.localidad,"
	                 + "    e.representanteLegal,"
	                 + "    e.dniRepresentante,"
	                 + "    e.emailEmpresa,"
	                 + "    e.telefonoEmpresa,"
	                 + "    e.tipoEmpresa "
	                 + "FROM "
	                 + "    ContactosEmpresa c "
	                 + "JOIN "
	                 + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
	                 + "WHERE "
	                 + "    e.tipoEmpresa = 5 "
	                 + "ORDER BY c.primerApellidoContacto;";
	         try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	             ResultSet resultado = statement.executeQuery();
	             while (resultado.next()) {
	                 textArea.append(
	                     resultado.getString("primerApellidoContacto") + " " +
	                     resultado.getString("segundoApellidoContacto") + " | " +
	                     resultado.getString("nombreContacto") + " | " +
	                     resultado.getString("dniContacto") + " | " +
	                     resultado.getString("telefonoContacto") + " | " +
	                     resultado.getString("emailContacto") + " | " +
	                     resultado.getString("esDual") + " | " +
	                     resultado.getString("nombreEmpresa") + "\n"
	                 );
	             }
	             System.out.println("Listado de contactos de empresas de educación ordenado por apellido rellenado correctamente.");
	         } catch (SQLException sqle) {
	             System.out.println("Error al rellenar listado de contactos de empresas de educación ordenado por apellido: " + sqle.getMessage());
	         }
	     }

	     // Método para rellenar el listado de contactos de empresas de educación ordenado por DNI
	     public void rellenarListadoContactoEmpresaAsistenciaPorDni(JTextArea textArea) {
	         String sentencia = "SELECT "
	                 + "    c.idContacto,"
	                 + "    c.nombreContacto,"
	                 + "    c.primerApellidoContacto,"
	                 + "    c.segundoApellidoContacto,"
	                 + "    c.dniContacto,"
	                 + "    c.telefonoContacto,"
	                 + "    c.emailContacto,"
	                 + "    c.esDual,"
	                 + "    c.idEmpresaFK,"
	                 + "    e.nombreEmpresa,"
	                 + "    e.CIF,"
	                 + "    e.direccionPostal,"
	                 + "    e.localidad,"
	                 + "    e.representanteLegal,"
	                 + "    e.dniRepresentante,"
	                 + "    e.emailEmpresa,"
	                 + "    e.telefonoEmpresa,"
	                 + "    e.tipoEmpresa "
	                 + "FROM "
	                 + "    ContactosEmpresa c "
	                 + "JOIN "
	                 + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
	                 + "WHERE "
	                 + "    e.tipoEmpresa = 5 "
	                 + "ORDER BY c.dniContacto;";
	         try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	             ResultSet resultado = statement.executeQuery();
	             while (resultado.next()) {
	                 textArea.append(
	                     resultado.getString("dniContacto") + " | " +
	                     resultado.getString("nombreContacto") + " | " +
	                     resultado.getString("primerApellidoContacto") + " " +
	                     resultado.getString("segundoApellidoContacto") + " | " +
	                     resultado.getString("telefonoContacto") + " | " +
	                     resultado.getString("emailContacto") + " | " +
	                     resultado.getString("esDual") + " | " +
	                     resultado.getString("nombreEmpresa") + "\n"
	                 );
	             }
	             System.out.println("Listado de contactos de empresas de educación ordenado por DNI rellenado correctamente.");
	         } catch (SQLException sqle) {
	             System.out.println("Error al rellenar listado de contactos de empresas de educación ordenado por DNI: " + sqle.getMessage());
	         }
	     }

	     // Método para rellenar el listado de contactos de empresas de educación ordenado por teléfono
	     public void rellenarListadoContactoEmpresaAsistenciaPorTelefono(JTextArea textArea) {
	         String sentencia = "SELECT "
	                 + "    c.idContacto,"
	                 + "    c.nombreContacto,"
	                 + "    c.primerApellidoContacto,"
	                 + "    c.segundoApellidoContacto,"
	                 + "    c.dniContacto,"
	                 + "    c.telefonoContacto,"
	                 + "    c.emailContacto,"
	                 + "    c.esDual,"
	                 + "    c.idEmpresaFK,"
	                 + "    e.nombreEmpresa,"
	                 + "    e.CIF,"
	                 + "    e.direccionPostal,"
	                 + "    e.localidad,"
	                 + "    e.representanteLegal,"
	                 + "    e.dniRepresentante,"
	                 + "    e.emailEmpresa,"
	                 + "    e.telefonoEmpresa,"
	                 + "    e.tipoEmpresa "
	                 + "FROM "
	                 + "    ContactosEmpresa c "
	                 + "JOIN "
	                 + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
	                 + "WHERE "
	                 + "    e.tipoEmpresa = 5 "
	                 + "ORDER BY c.telefonoContacto;";
	         try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	             ResultSet resultado = statement.executeQuery();
	             while (resultado.next()) {
	                 textArea.append(
	                     resultado.getString("telefonoContacto") + " | " +
	                     resultado.getString("nombreContacto") + " | " +
	                     resultado.getString("primerApellidoContacto") + " " +
	                     resultado.getString ("segundoApellidoContacto") + " | " +
	                     resultado.getString("dniContacto") + " | " +
	                     resultado.getString("emailContacto") + " | " +
	                     resultado.getString("esDual") + " | " +
	                     resultado.getString("nombreEmpresa") + "\n"
	                 );
	             }
	             System.out.println("Listado de contactos de empresas de educación ordenado por teléfono rellenado correctamente.");
	         } catch (SQLException sqle) {
	             System.out.println("Error al rellenar listado de contactos de empresas de educación ordenado por teléfono: " + sqle.getMessage());
	         }
	     }

	     // Método para rellenar el listado de contactos de empresas de educación ordenado por email
	     public void rellenarListadoContactoEmpresaAsistenciaPorEmail(JTextArea textArea) {
	         String sentencia = "SELECT "
	                 + "    c.idContacto,"
	                 + "    c.nombreContacto,"
	                 + "    c.primerApellidoContacto,"
	                 + "    c.segundoApellidoContacto,"
	                 + "    c.dniContacto,"
	                 + "    c.telefonoContacto,"
	                 + "    c.emailContacto,"
	                 + "    c.esDual,"
	                 + "    c.idEmpresaFK,"
	                 + "    e.nombreEmpresa,"
	                 + "    e.CIF,"
	                 + "    e.direccionPostal,"
	                 + "    e.localidad,"
	                 + "    e.representanteLegal,"
	                 + "    e.dniRepresentante,"
	                 + "    e.emailEmpresa,"
	                 + "    e.telefonoEmpresa,"
	                 + "    e.tipoEmpresa "
	                 + "FROM "
	                 + "    ContactosEmpresa c "
	                 + "JOIN "
	                 + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
	                 + "WHERE "
	                 + "    e.tipoEmpresa = 5 "
	                 + "ORDER BY c.emailContacto;";
	         try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	             ResultSet resultado = statement.executeQuery();
	             while (resultado.next()) {
	                 textArea.append(
	                     resultado.getString("emailContacto") + " | " +
	                     resultado.getString("nombreEmpresa") + " | " +
	                     resultado.getString("nombreContacto") + " | " +
	                     resultado.getString("primerApellidoContacto") + " | " +
	                     resultado.getString("segundoApellidoContacto") + " | " +
	                     resultado.getString("dniContacto") + " | " +
	                     resultado.getString("telefonoContacto") + " | " +
	                     resultado.getString("esDual") + "\n"
	                 );
	             }
	             System.out.println("Listado de contactos de empresas de educación ordenado por email rellenado correctamente.");
	         } catch (SQLException sqle) {
	             System.out.println("Error al rellenar listado de contactos de empresas de educación ordenado por email: " + sqle.getMessage());
	         }
	     }

	     // Método para rellenar el listado de contactos de empresas de educación ordenado por empresa
	     public void rellenarListadoContactoEmpresaAsistenciaPorEmpresa(JTextArea textArea) {
	         String sentencia = "SELECT "
	                 + "    c.idContacto,"
	                 + "    c.nombreContacto,"
	                 + "    c.primerApellidoContacto,"
	                 + "    c.segundoApellidoContacto,"
	                 + "    c.dniContacto,"
	                 + "    c.telefonoContacto,"
	                 + "    c.emailContacto,"
	                 + "    c.esDual,"
	                 + "    c.idEmpresaFK,"
	                 + "    e.nombreEmpresa,"
	                 + "    e.CIF,"
	                 + "    e.direccionPostal,"
	                 + "    e.localidad,"
	                 + "    e.representanteLegal,"
	                 + "    e.dniRepresentante,"
	                 + "    e.emailEmpresa,"
	                 + "    e.telefonoEmpresa,"
	                 + "    e.tipoEmpresa "
	                 + "FROM "
	                 + "    ContactosEmpresa c "
	                 + "JOIN "
	                 + "    Empresas e ON c.idEmpresaFK = e.idEmpresa "
	                 + "WHERE "
	                 + "    e.tipoEmpresa = 5 "
	                 + "ORDER BY e.nombreEmpresa;";
	         try (PreparedStatement statement = connection.prepareStatement(sentencia)) {
	             ResultSet resultado = statement.executeQuery();
	             while (resultado.next()) {
	                 textArea.append(
	                     resultado.getString("nombreEmpresa") + " | " +
	                     resultado.getString("nombreContacto") + " | " +
	                     resultado.getString("primerApellidoContacto") + " | " +
	                     resultado.getString("segundoApellidoContacto") + " | " +
	                     resultado.getString("dniContacto") + " | " +
	                     resultado.getString("telefonoContacto") + " | " +
	                     resultado.getString("emailContacto") + " | " +
	                     resultado.getString("esDual") + "\n"
	                 );
	             }
	             System.out.println("Listado de contactos de empresas de educación ordenado por empresa rellenado correctamente.");
	         } catch (SQLException sqle) {
	             System.out.println("Error al rellenar listado de contactos de empresas de educación ordenado por empresa: " + sqle.getMessage());
	         }
	     }

	public void rellenarChoiceEmpresaAdmin(JComboBox<String> comboEmpresas) {
		String sentencia = "SELECT idEmpresa, nombreEmpresa FROM Empresas WHERE tipoEmpresa = 1;";
		try {
			// Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Crear un objeto ResultSet para guardar lo obtenido
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				comboEmpresas.addItem(resultado.getString("idEmpresa") + "-" + resultado.getString("nombreEmpresa"));
			}
		} catch (SQLException sqle) {
			System.out.println("Error 12-" + sqle.getMessage());
		}
	}

	public void rellenarChoiceEmpresaEnfermeria(JComboBox<String> comboEmpresas) {
		String sentencia = "SELECT idEmpresa, nombreEmpresa FROM Empresas WHERE tipoEmpresa = 2;";
		try {
			// Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Crear un objeto ResultSet para guardar lo obtenido
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				comboEmpresas.addItem(resultado.getString("idEmpresa") + "-" + resultado.getString("nombreEmpresa"));
			}
		} catch (SQLException sqle) {
			System.out.println("Error 12-" + sqle.getMessage());
		}
	}

	public void rellenarChoiceEmpresaEduc(JComboBox<String> comboEmpresas) {
		String sentencia = "SELECT idEmpresa, nombreEmpresa FROM Empresas WHERE tipoEmpresa = 3;";
		try {
			// Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Crear un objeto ResultSet para guardar lo obtenido
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				comboEmpresas.addItem(resultado.getString("idEmpresa") + "-" + resultado.getString("nombreEmpresa"));
			}
		} catch (SQLException sqle) {
			System.out.println("Error 12-" + sqle.getMessage());
		}
	}

	public void rellenarChoiceEmpresaEducOnline(JComboBox<String> comboEmpresas) {
		String sentencia = "SELECT idEmpresa, nombreEmpresa FROM Empresas WHERE tipoEmpresa = 4;";
		try {
			// Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Crear un objeto ResultSet para guardar lo obtenido
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				comboEmpresas.addItem(resultado.getString("idEmpresa") + "-" + resultado.getString("nombreEmpresa"));
			}
		} catch (SQLException sqle) {
			System.out.println("Error 12-" + sqle.getMessage());
		}
	}

	public void rellenarChoiceEmpresaAsis(JComboBox<String> comboEmpresas) {
		String sentencia = "SELECT idEmpresa, nombreEmpresa FROM Empresas WHERE tipoEmpresa = 5;";
		try {
			// Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			// Crear un objeto ResultSet para guardar lo obtenido
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				comboEmpresas.addItem(resultado.getString("idEmpresa") + "-" + resultado.getString("nombreEmpresa"));
			}
		} catch (SQLException sqle) {
			System.out.println("Error 12-" + sqle.getMessage());
		}
	}
	public void rellenarChoiceAlumna(JComboBox<String> comboAlumnas) {
		String sentencia = "SELECT idAlumna, nombreAlumna, primerApellidoAlumna, segundoApellidoAlumna " +
				"FROM Alumnas WHERE antiguaAlumna = 0 AND bajaAlumna = 0 ORDER BY nombreAlumna;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				comboAlumnas.addItem(resultado.getInt("idAlumna") + "-" +
						resultado.getString("nombreAlumna") + " " +
						resultado.getString("primerApellidoAlumna") + " " +
						resultado.getString("segundoApellidoAlumna"));
			}
		} catch (SQLException sqle) {
			System.out.println("Error: " + sqle.getMessage());
		}
	}


	public void rellenarChoiceSedesAdmin(JComboBox<String> comboSedes) {
		String sentencia = "SELECT s.idSede, s.nombreSede, e.nombreEmpresa " +
				"FROM SedesEmpresa s " +
				"JOIN Empresas e ON s.idEmpresaFK = e.idEmpresa " +
				"WHERE e.tipoEmpresa = 1 ORDER BY s.nombreSede";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				comboSedes.addItem(resultado.getInt("idSede") + "-" + resultado.getString("nombreSede") + "-" + resultado.getString("nombreEmpresa"));
			}
		} catch (SQLException sqle) {
			System.out.println("Error: " + sqle.getMessage());
		}
	}

	public void rellenarChoiceContactosAdmin(JComboBox<String> comboContactos) {
		String sentencia = "SELECT c.idContacto, c.nombreContacto, c.primerApellidoContacto, c.segundoApellidoContacto, e.nombreEmpresa " +
				"FROM ContactosEmpresa c " +
				"JOIN Empresas e ON c.idEmpresaFK = e.idEmpresa " +
				"WHERE e.tipoEmpresa = 1 ORDER BY c.nombreContacto";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				comboContactos.addItem(resultado.getInt("idContacto") + "-" + 
						resultado.getString("nombreContacto") + "-" + 
						resultado.getString("primerApellidoContacto") + "-" + 
						resultado.getString("segundoApellidoContacto") + "-" + 
						resultado.getString("nombreEmpresa"));
			}
		} catch (SQLException sqle) {
			System.out.println("Error: " + sqle.getMessage());
		}
	}
	public void rellenarChoiceSedesEnf(JComboBox<String> comboSedes) {
		String sentencia = "SELECT s.idSede, s.nombreSede, e.nombreEmpresa " +
				"FROM SedesEmpresa s " +
				"JOIN Empresas e ON s.idEmpresaFK = e.idEmpresa " +
				"WHERE e.tipoEmpresa = 2 ORDER BY s.nombreSede";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				comboSedes.addItem(resultado.getInt("idSede") + "-" + resultado.getString("nombreSede") + "-" + resultado.getString("nombreEmpresa"));
			}
		} catch (SQLException sqle) {
			System.out.println("Error: " + sqle.getMessage());
		}
	}

	public void rellenarChoiceContactosEnf(JComboBox<String> comboContactos) {
		String sentencia = "SELECT c.idContacto, c.nombreContacto, c.primerApellidoContacto, c.segundoApellidoContacto, e.nombreEmpresa " +
				"FROM ContactosEmpresa c " +
				"JOIN Empresas e ON c.idEmpresaFK = e.idEmpresa " +
				"WHERE e.tipoEmpresa = 2 ORDER BY c.nombreContacto";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				comboContactos.addItem(resultado.getInt("idContacto") + "-" + 
						resultado.getString("nombreContacto") + "-" + 
						resultado.getString("primerApellidoContacto") + "-" + 
						resultado.getString("segundoApellidoContacto") + "-" + 
						resultado.getString("nombreEmpresa"));
			}
		} catch (SQLException sqle) {
			System.out.println("Error: " + sqle.getMessage());
		}
	}public void rellenarChoiceSedesEduc(JComboBox<String> comboSedes) {
		String sentencia = "SELECT s.idSede, s.nombreSede, e.nombreEmpresa " +
				"FROM SedesEmpresa s " +
				"JOIN Empresas e ON s.idEmpresaFK = e.idEmpresa " +
				"WHERE e.tipoEmpresa = 3 ORDER BY s.nombreSede";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				comboSedes.addItem(resultado.getInt("idSede") + "-" + resultado.getString("nombreSede") + "-" + resultado.getString("nombreEmpresa"));
			}
		} catch (SQLException sqle) {
			System.out.println("Error: " + sqle.getMessage());
		}
	}

	public void rellenarChoiceContactosEduc(JComboBox<String> comboContactos) {
		String sentencia = "SELECT c.idContacto, c.nombreContacto, c.primerApellidoContacto, c.segundoApellidoContacto, e.nombreEmpresa " +
				"FROM ContactosEmpresa c " +
				"JOIN Empresas e ON c.idEmpresaFK = e.idEmpresa " +
				"WHERE e.tipoEmpresa = 3 ORDER BY c.nombreContacto";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				comboContactos.addItem(resultado.getInt("idContacto") + "-" + 
						resultado.getString("nombreContacto") + "-" + 
						resultado.getString("primerApellidoContacto") + "-" + 
						resultado.getString("segundoApellidoContacto") + "-" + 
						resultado.getString("nombreEmpresa"));
			}
		} catch (SQLException sqle) {
			System.out.println("Error: " + sqle.getMessage());
		}
	}public void rellenarChoiceSedesEducOnline(JComboBox<String> comboSedes) {
		String sentencia = "SELECT s.idSede, s.nombreSede, e.nombreEmpresa " +
				"FROM SedesEmpresa s " +
				"JOIN Empresas e ON s.idEmpresaFK = e.idEmpresa " +
				"WHERE e.tipoEmpresa = 4 ORDER BY s.nombreSede";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				comboSedes.addItem(resultado.getInt("idSede") + "-" + resultado.getString("nombreSede") + "-" + resultado.getString("nombreEmpresa"));
			}
		} catch (SQLException sqle) {
			System.out.println("Error: " + sqle.getMessage());
		}
	}

	public void rellenarChoiceContactosEducOnline(JComboBox<String> comboContactos) {
		String sentencia = "SELECT c.idContacto, c.nombreContacto, c.primerApellidoContacto, c.segundoApellidoContacto, e.nombreEmpresa " +
				"FROM ContactosEmpresa c " +
				"JOIN Empresas e ON c.idEmpresaFK = e.idEmpresa " +
				"WHERE e.tipoEmpresa = 4 ORDER BY c.nombreContacto";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				comboContactos.addItem(resultado.getInt("idContacto") + "-" + 
						resultado.getString("nombreContacto") + "-" + 
						resultado.getString("primerApellidoContacto") + "-" + 
						resultado.getString("segundoApellidoContacto") + "-" + 
						resultado.getString("nombreEmpresa"));
			}
		} catch (SQLException sqle) {
			System.out.println("Error: " + sqle.getMessage());
		}
	}public void rellenarChoiceSedesAsis(JComboBox<String> comboSedes) {
		String sentencia = "SELECT s.idSede, s.nombreSede, e.nombreEmpresa " +
				"FROM SedesEmpresa s " +
				"JOIN Empresas e ON s.idEmpresaFK = e.idEmpresa " +
				"WHERE e.tipoEmpresa = 5 ORDER BY s.nombreSede";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				comboSedes.addItem(resultado.getInt("idSede") + "-" + resultado.getString("nombreSede") + "-" + resultado.getString("nombreEmpresa"));
			}
		} catch (SQLException sqle) {
			System.out.println("Error: " + sqle.getMessage());
		}
	}

	public void rellenarChoiceContactosAsis(JComboBox<String> comboContactos) {
		String sentencia = "SELECT c.idContacto, c.nombreContacto, c.primerApellidoContacto, c.segundoApellidoContacto, e.nombreEmpresa " +
				"FROM ContactosEmpresa c " +
				"JOIN Empresas e ON c.idEmpresaFK = e.idEmpresa " +
				"WHERE e.tipoEmpresa = 5 ORDER BY c.nombreContacto";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				comboContactos.addItem(resultado.getInt("idContacto") + "-" + 
						resultado.getString("nombreContacto") + "-" + 
						resultado.getString("primerApellidoContacto") + "-" + 
						resultado.getString("segundoApellidoContacto") + "-" + 
						resultado.getString("nombreEmpresa"));
			}
		} catch (SQLException sqle) {
			System.out.println("Error: " + sqle.getMessage());
		}
	}





	public List<String> obtenerListadoContactosEmpresaAdmin() {
		List<String> contactos = new ArrayList<>();
		String sentencia = "SELECT CE.idContacto, CE.nombreContacto, CE.primerApellidoContacto, CE.segundoApellidoContacto, " +
				"CE.dniContacto, CE.telefonoContacto, CE.emailContacto, " +
				"CASE WHEN CE.esDual = 0 THEN 'Es Dual' ELSE 'No es Dual' END AS esDualText, " +
				"E.nombreEmpresa " +
				"FROM ContactosEmpresa CE " +
				"INNER JOIN Empresas E ON CE.idEmpresaFK = E.idEmpresa " +
				"WHERE E.tipoEmpresa = 1;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String contacto = resultado.getInt("idContacto") + " | " +
						resultado.getString("nombreContacto") + " | " +
						resultado.getString("primerApellidoContacto") + " | " +
						resultado.getString("segundoApellidoContacto") + " | " +
						resultado.getString("dniContacto") + " | " +
						resultado.getString("telefonoContacto") + " | " +
						resultado.getString("emailContacto") + " | " +
						resultado.getString("esDualText") + " | " +
						resultado.getString("nombreEmpresa"); // Agrega el nombre de la empresa al resultado
				contactos.add(contacto);
			}
			System.out.println("Listado de contactos de empresa rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return contactos;
	}

	public List<String> obtenerListadoContactosEmpresaEnfermeria() {
		List<String> contactos = new ArrayList<>();
		String sentencia = "SELECT CE.idContacto, CE.nombreContacto, CE.primerApellidoContacto, CE.segundoApellidoContacto, " +
				"CE.dniContacto, CE.telefonoContacto, CE.emailContacto, " +
				"CASE WHEN CE.esDual = 0 THEN 'Es Dual' ELSE 'No es Dual' END AS esDualText, " +
				"E.nombreEmpresa " +
				"FROM ContactosEmpresa CE " +
				"INNER JOIN Empresas E ON CE.idEmpresaFK = E.idEmpresa " +
				"WHERE E.tipoEmpresa = 2;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String contacto = resultado.getInt("idContacto") + " | " +
						resultado.getString("nombreContacto") + " | " +
						resultado.getString("primerApellidoContacto") + " | " +
						resultado.getString("segundoApellidoContacto") + " | " +
						resultado.getString("dniContacto") + " | " +
						resultado.getString("telefonoContacto") + " | " +
						resultado.getString("emailContacto") + " | " +
						resultado.getString("esDualText") + " | " +
						resultado.getString("nombreEmpresa"); // Agrega el nombre de la empresa al resultado
				contactos.add(contacto);
			}
			System.out.println("Listado de contactos de empresa rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return contactos;
	}
	public List<String> obtenerListadoContactosEmpresaEducacion() {
		List<String> contactos = new ArrayList<>();
		String sentencia = "SELECT CE.idContacto, CE.nombreContacto, CE.primerApellidoContacto, CE.segundoApellidoContacto, " +
				"CE.dniContacto, CE.telefonoContacto, CE.emailContacto, " +
				"CASE WHEN CE.esDual = 0 THEN 'Es Dual' ELSE 'No es Dual' END AS esDualText, " +
				"E.nombreEmpresa " +
				"FROM ContactosEmpresa CE " +
				"INNER JOIN Empresas E ON CE.idEmpresaFK = E.idEmpresa " +
				"WHERE E.tipoEmpresa = 3;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String contacto = resultado.getInt("idContacto") + " | " +
						resultado.getString("nombreContacto") + " | " +
						resultado.getString("primerApellidoContacto") + " | " +
						resultado.getString("segundoApellidoContacto") + " | " +
						resultado.getString("dniContacto") + " | " +
						resultado.getString("telefonoContacto") + " | " +
						resultado.getString("emailContacto") + " | " +
						resultado.getString("esDualText") + " | " +
						resultado.getString("nombreEmpresa"); // Agrega el nombre de la empresa al resultado
				contactos.add(contacto);
			}
			System.out.println("Listado de contactos de empresa rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return contactos;
	}
	public List<String> obtenerListadoContactosEmpresaEducacionOnline() {
		List<String> contactos = new ArrayList<>();
		String sentencia = "SELECT CE.idContacto, CE.nombreContacto, CE.primerApellidoContacto, CE.segundoApellidoContacto, " +
				"CE.dniContacto, CE.telefonoContacto, CE.emailContacto, " +
				"CASE WHEN CE.esDual = 0 THEN 'Es Dual' ELSE 'No es Dual' END AS esDualText, " +
				"E.nombreEmpresa " +
				"FROM ContactosEmpresa CE " +
				"INNER JOIN Empresas E ON CE.idEmpresaFK = E.idEmpresa " +
				"WHERE E.tipoEmpresa = 4;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String contacto = resultado.getInt("idContacto") + " | " +
						resultado.getString("nombreContacto") + " | " +
						resultado.getString("primerApellidoContacto") + " | " +
						resultado.getString("segundoApellidoContacto") + " | " +
						resultado.getString("dniContacto") + " | " +
						resultado.getString("telefonoContacto") + " | " +
						resultado.getString("emailContacto") + " | " +
						resultado.getString("esDualText") + " | " +
						resultado.getString("nombreEmpresa"); // Agrega el nombre de la empresa al resultado
				contactos.add(contacto);
			}
			System.out.println("Listado de contactos de empresa rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return contactos;
	}
	public List<String> obtenerListadoContactosEmpresaAsis() {
		List<String> contactos = new ArrayList<>();
		String sentencia = "SELECT CE.idContacto, CE.nombreContacto, CE.primerApellidoContacto, CE.segundoApellidoContacto, " +
				"CE.dniContacto, CE.telefonoContacto, CE.emailContacto, " +
				"CASE WHEN CE.esDual = 0 THEN 'Es Dual' ELSE 'No es Dual' END AS esDualText, " +
				"E.nombreEmpresa " +
				"FROM ContactosEmpresa CE " +
				"INNER JOIN Empresas E ON CE.idEmpresaFK = E.idEmpresa " +
				"WHERE E.tipoEmpresa = 5;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String contacto = resultado.getInt("idContacto") + " | " +
						resultado.getString("nombreContacto") + " | " +
						resultado.getString("primerApellidoContacto") + " | " +
						resultado.getString("segundoApellidoContacto") + " | " +
						resultado.getString("dniContacto") + " | " +
						resultado.getString("telefonoContacto") + " | " +
						resultado.getString("emailContacto") + " | " +
						resultado.getString("esDualText") + " | " +
						resultado.getString("nombreEmpresa"); // Agrega el nombre de la empresa al resultado
				contactos.add(contacto);
			}
			System.out.println("Listado de contactos de empresa rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return contactos;
	}
	public void rellenarListadoSedeAdministracionPorNombreSede(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 1 " +
	            "ORDER BY s.nombreSede;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por nombre de sede rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeAdministracionPorDireccion(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 1 " +
	            "ORDER BY s.direccion;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por dirección rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeAdministracionPorTelefono(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 1 " +
	            "ORDER BY s.telefono;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por teléfono rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeAdministracionPorObservaciones(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 1 " +
	            "ORDER BY s.observaciones;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por observaciones rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeAdministracionPorEmpresa(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 1 " +
	            "ORDER BY e.nombreEmpresa;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("nombreEmpresa") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("observaciones") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por observaciones rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}

	public void rellenarListadoSedeEnfermeriaPorNombreSede(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 2 " +
	            "ORDER BY s.nombreSede;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por nombre de sede rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeEnfermeriaPorDireccion(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 2 " +
	            "ORDER BY s.direccion;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por dirección rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeEnfermeriaPorTelefono(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 2 " +
	            "ORDER BY s.telefono;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por teléfono rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeEnfermeriaPorObservaciones(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 2 " +
	            "ORDER BY s.observaciones;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por observaciones rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeEnfermeriaPorEmpresa(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 2 " +
	            "ORDER BY e.nombreEmpresa;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("nombreEmpresa") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("observaciones") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por observaciones rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}public void rellenarListadoSedeEducacionPorNombreSede(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 3 " +
	            "ORDER BY s.nombreSede;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por nombre de sede rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeEducacionPorDireccion(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 3 " +
	            "ORDER BY s.direccion;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por dirección rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeEducacionPorTelefono(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 3 " +
	            "ORDER BY s.telefono;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por teléfono rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeEducacionPorObservaciones(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 3 " +
	            "ORDER BY s.observaciones;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por observaciones rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeEducacionPorEmpresa(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 3 " +
	            "ORDER BY e.nombreEmpresa;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("nombreEmpresa") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("observaciones") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por observaciones rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeEducacionOnlinePorNombreSede(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 4 " +
	            "ORDER BY s.nombreSede;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por nombre de sede rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeEducacionOnlinePorDireccion(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 4 " +
	            "ORDER BY s.direccion;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por dirección rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeEducacionOnlinePorTelefono(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 4 " +
	            "ORDER BY s.telefono;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por teléfono rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeEducacionOnlinePorObservaciones(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 4 " +
	            "ORDER BY s.observaciones;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por observaciones rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeEducacionOnlinePorEmpresa(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 4 " +
	            "ORDER BY e.nombreEmpresa;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("nombreEmpresa") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("observaciones") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por observaciones rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeAsistenciaPorNombreSede(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 5 " +
	            "ORDER BY s.nombreSede;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por nombre de sede rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeAsistenciaPorDireccion(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 5 " +
	            "ORDER BY s.direccion;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por dirección rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeAsistenciaPorTelefono(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 5 " +
	            "ORDER BY s.telefono;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por teléfono rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeAsistenciaPorObservaciones(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 5 " +
	            "ORDER BY s.observaciones;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("observaciones") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("nombreEmpresa") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por observaciones rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}
	public void rellenarListadoSedeAsistenciaPorEmpresa(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    s.idSede, " +
	            "    s.nombreSede, " +
	            "    s.direccion, " +
	            "    s.telefono, " +
	            "    s.observaciones, " +
	            "    e.nombreEmpresa, " +
	            "    e.CIF, " +
	            "    e.direccionPostal, " +
	            "    e.localidad, " +
	            "    e.representanteLegal, " +
	            "    e.dniRepresentante, " +
	            "    e.emailEmpresa, " +
	            "    e.telefonoEmpresa, " +
	            "    e.tipoEmpresa " +
	            "FROM " +
	            "    SedesEmpresa s " +
	            "JOIN " +
	            "    Empresas e ON s.idEmpresaFK = e.idEmpresa " +
	            "WHERE " +
	            "    e.tipoEmpresa = 5 " +
	            "ORDER BY e.nombreEmpresa;";
	    try {
	        statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        ResultSet resultado = statement.executeQuery(sentencia);

	        if (!resultado.isBeforeFirst()) {  // Si no hay resultados
	            System.out.println("No se encontraron registros para tipoEmpresa = 1.");
	        }

	        while (resultado.next()) {
	            System.out.println("Procesando registro: " + resultado.getString("nombreEmpresa")); // Debug
	            textArea.append(resultado.getString("nombreEmpresa") + " | ");
	            textArea.append(resultado.getString("nombreSede") + " | ");
	            textArea.append(resultado.getString("direccion") + " | ");
	            textArea.append(resultado.getString("telefono") + " | ");
	            textArea.append(resultado.getString("observaciones") + "\n");
	        }
	        System.out.println("Listado de sede de administración ordenado por observaciones rellenado correctamente.");
	    } catch (SQLException sqle) {
	        System.out.println("Error 4-" + sqle.getMessage());
	    }
	}


	public List<String> obtenerListadoSedesEmpresaAdmin() {
		List<String> sedes = new ArrayList<>();
		String sentencia = "SELECT SE.idSede, SE.nombreSede, SE.direccion, SE.telefono, SE.observaciones, " +
				"E.nombreEmpresa, E.CIF " +
				"FROM SedesEmpresa SE " +
				"INNER JOIN Empresas E ON SE.idEmpresaFK = E.idEmpresa " +
				"WHERE E.tipoEmpresa = 1;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String sede = resultado.getInt("idSede") + " | " +
						resultado.getString("nombreSede") + " | " +
						resultado.getString("direccion") + " | " +
						resultado.getString("telefono") + " | " +
						resultado.getString("observaciones") + " | " +
						resultado.getString("nombreEmpresa") + " | " +
						resultado.getString("CIF");
				sedes.add(sede);
			}
			System.out.println("Listado de sedes de empresas de administración rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return sedes;
	}
	public List<String> obtenerListadoSedesEmpresaEnf() {
		List<String> sedes = new ArrayList<>();
		String sentencia = "SELECT SE.idSede, SE.nombreSede, SE.direccion, SE.telefono, SE.observaciones, " +
				"E.nombreEmpresa, E.CIF " +
				"FROM SedesEmpresa SE " +
				"INNER JOIN Empresas E ON SE.idEmpresaFK = E.idEmpresa " +
				"WHERE E.tipoEmpresa = 2;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String sede = resultado.getInt("idSede") + " | " +
						resultado.getString("nombreSede") + " | " +
						resultado.getString("direccion") + " | " +
						resultado.getString("telefono") + " | " +
						resultado.getString("observaciones") + " | " +
						resultado.getString("nombreEmpresa") + " | " +
						resultado.getString("CIF");
				sedes.add(sede);
			}
			System.out.println("Listado de sedes de empresas de administración rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return sedes;
	}public List<String> obtenerListadoSedesEmpresaEdu() {
		List<String> sedes = new ArrayList<>();
		String sentencia = "SELECT SE.idSede, SE.nombreSede, SE.direccion, SE.telefono, SE.observaciones, " +
				"E.nombreEmpresa, E.CIF " +
				"FROM SedesEmpresa SE " +
				"INNER JOIN Empresas E ON SE.idEmpresaFK = E.idEmpresa " +
				"WHERE E.tipoEmpresa = 3;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String sede = resultado.getInt("idSede") + " | " +
						resultado.getString("nombreSede") + " | " +
						resultado.getString("direccion") + " | " +
						resultado.getString("telefono") + " | " +
						resultado.getString("observaciones") + " | " +
						resultado.getString("nombreEmpresa") + " | " +
						resultado.getString("CIF");
				sedes.add(sede);
			}
			System.out.println("Listado de sedes de empresas de administración rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return sedes;
	}public List<String> obtenerListadoSedesEmpresaEduOnline() {
		List<String> sedes = new ArrayList<>();
		String sentencia = "SELECT SE.idSede, SE.nombreSede, SE.direccion, SE.telefono, SE.observaciones, " +
				"E.nombreEmpresa, E.CIF " +
				"FROM SedesEmpresa SE " +
				"INNER JOIN Empresas E ON SE.idEmpresaFK = E.idEmpresa " +
				"WHERE E.tipoEmpresa = 4;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String sede = resultado.getInt("idSede") + " | " +
						resultado.getString("nombreSede") + " | " +
						resultado.getString("direccion") + " | " +
						resultado.getString("telefono") + " | " +
						resultado.getString("observaciones") + " | " +
						resultado.getString("nombreEmpresa") + " | " +
						resultado.getString("CIF");
				sedes.add(sede);
			}
			System.out.println("Listado de sedes de empresas de administración rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return sedes;
	}public List<String> obtenerListadoSedesEmpresaAsis() {
		List<String> sedes = new ArrayList<>();
		String sentencia = "SELECT SE.idSede, SE.nombreSede, SE.direccion, SE.telefono, SE.observaciones, " +
				"E.nombreEmpresa, E.CIF " +
				"FROM SedesEmpresa SE " +
				"INNER JOIN Empresas E ON SE.idEmpresaFK = E.idEmpresa " +
				"WHERE E.tipoEmpresa = 5;";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			while (resultado.next()) {
				String sede = resultado.getInt("idSede") + " | " +
						resultado.getString("nombreSede") + " | " +
						resultado.getString("direccion") + " | " +
						resultado.getString("telefono") + " | " +
						resultado.getString("observaciones") + " | " +
						resultado.getString("nombreEmpresa") + " | " +
						resultado.getString("CIF");
				sedes.add(sede);
			}
			System.out.println("Listado de sedes de empresas de administración rellenado correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error 4-" + sqle.getMessage());
		}
		return sedes;
	}



	public String getDatosEdicionContacto(String idContacto) {
		String resultado = "";
		String sentencia = "SELECT * FROM ContactosEmpresa WHERE idContacto = " + idContacto;
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery(sentencia);
			resultSet.next();
			resultado = (resultSet.getString("idContacto") + "-" +
					resultSet.getString("nombreContacto") + "-" +
					resultSet.getString("primerApellidoContacto") + "-" +
					resultSet.getString("segundoApellidoContacto") + "-" +
					resultSet.getString("dniContacto") + "-" +
					resultSet.getString("telefonoContacto") + "-" +
					resultSet.getString("emailContacto") + "-" +
					resultSet.getString("esDual") + "-" +
					resultSet.getString("idEmpresaFK"));

			// Imprimir los datos recuperados para verificar
			System.out.println("Datos recuperados de edición de contacto para idContacto: " + idContacto);
			System.out.println("Resultado: " + resultado);
		} catch (SQLException sqle) {
			System.out.println("Error: " + sqle.getMessage());
		}
		return resultado;
	}

	public String getDatosEdicionSede(String idSede) {
		String resultado = "";
		String sentencia = "SELECT * FROM SedesEmpresa WHERE idSede = " + idSede;
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery(sentencia);
			if (resultSet.next()) {
				resultado = (resultSet.getString("idSede") + "-" +
						resultSet.getString("nombreSede") + "-" +
						resultSet.getString("direccion") + "-" +
						resultSet.getString("telefono") + "-" +
						resultSet.getString("observaciones") + "-" +
						resultSet.getString("idEmpresaFK"));

				// Imprimir los datos recuperados para verificar
				System.out.println("Datos recuperados de edición de sede para idSede: " + idSede);
				System.out.println("Resultado: " + resultado);
			} else {
				System.out.println("No se encontraron datos para idSede: " + idSede);
			}
		} catch (SQLException sqle) {
			System.out.println("Error: " + sqle.getMessage());
		}
		return resultado;
	}
	public String getDatosEdicionPractica(String idPractica) {
	    String resultado = "";
	    String sentencia = "SELECT * FROM Practicas WHERE idPractica = " + idPractica;

	    try (PreparedStatement statement = connection.prepareStatement(sentencia);
	         ResultSet resultSet = statement.executeQuery()) {

	        if (resultSet.next()) {
	            resultado = resultSet.getString("idPractica") + "-" +
	                        resultSet.getString("horario") + "-" +
	                        resultSet.getString("idAlumna") + "-" +
	                        resultSet.getString("idSede") + "-" +
	                        resultSet.getString("idContacto");
	        }

	    } catch (SQLException sqle) {
	        System.out.println("Error al obtener datos de edición de práctica: " + sqle.getMessage());
	    }

	    return resultado;
	}





	public String getDatosEdicionEmpresa(String idEmpresa) {
		String datos = "";
		String sentencia = "SELECT * FROM Empresas WHERE idEmpresa = " + idEmpresa + ";";
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultado = statement.executeQuery(sentencia);
			if (resultado.next()) {
				datos = resultado.getString("idEmpresa") + "|" +
						resultado.getString("nombreEmpresa") + "|" +
						resultado.getString("CIF") + "|" +
						resultado.getString("direccionPostal") + "|" +
						resultado.getString("localidad") + "|" +
						resultado.getString("representanteLegal") + "|" +
						resultado.getString("dniRepresentante") + "|" +
						resultado.getString("emailEmpresa") + "|" +
						resultado.getString("telefonoEmpresa");
			}
			System.out.println("Datos de la empresa obtenidos correctamente.");
		} catch (SQLException sqle) {
			System.out.println("Error al obtener datos de la empresa: " + sqle.getMessage());
		}
		return datos;
	}

	public void rellenarListadoPracticasAdminPorHorario(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 1 " +
	            "    AND eContacto.tipoEmpresa = 1 " +
	            "ORDER BY p.horario";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica = "Horario: " + horario + "\n" +
	                    "Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	                    "Sede: " + nombreSede + "\n" +
	                    "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por horario rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}

	public void rellenarListadoPracticasAdminPorNombreAlumna(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 1 " +
	            "    AND eContacto.tipoEmpresa = 1 " +
	            "ORDER BY a.nombreAlumna";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica ="Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	            		"Horario: " + horario + "\n" +
	                    "Sede: " + nombreSede + "\n" +
	                    "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por nombre de alumna rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}
	public void rellenarListadoPracticasAdminPorNombreSede(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 1 " +
	            "    AND eContacto.tipoEmpresa = 1 " +
	            "ORDER BY se.nombreSede";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica = "Sede: " + nombreSede + "\n" +
	            		"Horario: " + horario + "\n" +
	                    "Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	                    "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por nombre de sede rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}
	public void rellenarListadoPracticasAdminPorNombreContacto(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 1 " +
	            "    AND eContacto.tipoEmpresa = 1 " +
	            "ORDER BY ce.nombreContacto";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica = "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n"+
	            		"Horario: " + horario + "\n" +
	                    "Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	                    "Sede: " + nombreSede + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por nombre de contacto rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}


	public void rellenarListadoPracticasEnfermeriaPorHorario(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 2 " +
	            "    AND eContacto.tipoEmpresa = 2 " +
	            "ORDER BY p.horario";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica = "Horario: " + horario + "\n" +
	                    "Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	                    "Sede: " + nombreSede + "\n" +
	                    "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por horario rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}

	public void rellenarListadoPracticasEnfermeriaPorNombreAlumna(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 2 " +
	            "    AND eContacto.tipoEmpresa = 2 " +
	            "ORDER BY a.nombreAlumna";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica ="Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	            		"Horario: " + horario + "\n" +
	                    "Sede: " + nombreSede + "\n" +
	                    "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por nombre de alumna rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}
	public void rellenarListadoPracticasEnfermeriaPorNombreSede(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 2 " +
	            "    AND eContacto.tipoEmpresa = 2 " +
	            "ORDER BY se.nombreSede";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica = "Sede: " + nombreSede + "\n" +
	            		"Horario: " + horario + "\n" +
	                    "Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	                    "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por nombre de sede rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}
	public void rellenarListadoPracticasEnfermeriaPorNombreContacto(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 2 " +
	            "    AND eContacto.tipoEmpresa = 2 " +
	            "ORDER BY ce.nombreContacto";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica = "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n"+
	            		"Horario: " + horario + "\n" +
	                    "Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	                    "Sede: " + nombreSede + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por nombre de contacto rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}
	public void rellenarListadoPracticasEducacionPorHorario(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 3 " +
	            "    AND eContacto.tipoEmpresa = 3 " +
	            "ORDER BY p.horario";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica = "Horario: " + horario + "\n" +
	                    "Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	                    "Sede: " + nombreSede + "\n" +
	                    "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por horario rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}

	public void rellenarListadoPracticasEducacionPorNombreAlumna(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 3 " +
	            "    AND eContacto.tipoEmpresa = 3 " +
	            "ORDER BY a.nombreAlumna";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica ="Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	            		"Horario: " + horario + "\n" +
	                    "Sede: " + nombreSede + "\n" +
	                    "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por nombre de alumna rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}
	public void rellenarListadoPracticasEducacionPorNombreSede(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 3 " +
	            "    AND eContacto.tipoEmpresa = 3 " +
	            "ORDER BY se.nombreSede";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica = "Sede: " + nombreSede + "\n" +
	            		"Horario: " + horario + "\n" +
	                    "Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	                    "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por nombre de sede rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}
	public void rellenarListadoPracticasEducacionPorNombreContacto(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 3 " +
	            "    AND eContacto.tipoEmpresa = 3 " +
	            "ORDER BY ce.nombreContacto";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica = "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n"+
	            		"Horario: " + horario + "\n" +
	                    "Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	                    "Sede: " + nombreSede + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por nombre de contacto rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}
	public void rellenarListadoPracticasEducacionOnlinePorHorario(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 4 " +
	            "    AND eContacto.tipoEmpresa = 4 " +
	            "ORDER BY p.horario";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica = "Horario: " + horario + "\n" +
	                    "Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	                    "Sede: " + nombreSede + "\n" +
	                    "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por horario rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}

	public void rellenarListadoPracticasEducacionOnlinePorNombreAlumna(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 4 " +
	            "    AND eContacto.tipoEmpresa = 4 " +
	            "ORDER BY a.nombreAlumna";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica ="Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	            		"Horario: " + horario + "\n" +
	                    "Sede: " + nombreSede + "\n" +
	                    "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por nombre de alumna rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}
	public void rellenarListadoPracticasEducacionOnlinePorNombreSede(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 4 " +
	            "    AND eContacto.tipoEmpresa = 4 " +
	            "ORDER BY se.nombreSede";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica = "Sede: " + nombreSede + "\n" +
	            		"Horario: " + horario + "\n" +
	                    "Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	                    "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por nombre de sede rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}
	public void rellenarListadoPracticasEducacionOnlinePorNombreContacto(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 4 " +
	            "    AND eContacto.tipoEmpresa = 4 " +
	            "ORDER BY ce.nombreContacto";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica = "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n"+
	            		"Horario: " + horario + "\n" +
	                    "Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	                    "Sede: " + nombreSede + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por nombre de contacto rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}
	public void rellenarListadoPracticasAsistenciaPorHorario(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 5 " +
	            "    AND eContacto.tipoEmpresa = 5 " +
	            "ORDER BY p.horario";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica = "Horario: " + horario + "\n" +
	                    "Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	                    "Sede: " + nombreSede + "\n" +
	                    "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por horario rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}

	public void rellenarListadoPracticasAsistenciaPorNombreAlumna(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 5 " +
	            "    AND eContacto.tipoEmpresa = 5 " +
	            "ORDER BY a.nombreAlumna";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica ="Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	            		"Horario: " + horario + "\n" +
	                    "Sede: " + nombreSede + "\n" +
	                    "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por nombre de alumna rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}
	public void rellenarListadoPracticasAsistenciaPorNombreSede(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 5 " +
	            "    AND eContacto.tipoEmpresa = 5 " +
	            "ORDER BY se.nombreSede";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica = "Sede: " + nombreSede + "\n" +
	            		"Horario: " + horario + "\n" +
	                    "Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	                    "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por nombre de sede rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}
	public void rellenarListadoPracticasAsistenciaPorNombreContacto(JTextArea textArea) {
	    String sentencia = "SELECT " +
	            "    p.horario, " +
	            "    a.nombreAlumna, " +
	            "    a.primerApellidoAlumna, " +
	            "    a.segundoApellidoAlumna, " +
	            "    se.nombreSede, " +
	            "    ce.nombreContacto, " +
	            "    ce.primerApellidoContacto, " +
	            "    ce.segundoApellidoContacto " +
	            "FROM " +
	            "    Practicas p " +
	            "JOIN " +
	            "    Alumnas a ON p.idAlumna = a.idAlumna " +
	            "JOIN " +
	            "    SedesEmpresa se ON p.idSede = se.idSede " +
	            "JOIN " +
	            "    ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
	            "JOIN " +
	            "    Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
	            "JOIN " +
	            "    Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
	            "WHERE " +
	            "    eSede.tipoEmpresa = 5 " +
	            "    AND eContacto.tipoEmpresa = 5 " +
	            "ORDER BY ce.nombreContacto";

	    try {
	        PreparedStatement statement = connection.prepareStatement(sentencia);
	        ResultSet resultado = statement.executeQuery();

	        // Limpiar el área de texto antes de mostrar nuevos datos
	        textArea.setText("");

	        // Recorrer el resultado y agregarlo al área de texto
	        while (resultado.next()) {
	            String horario = resultado.getString("horario");
	            String nombreAlumna = resultado.getString("nombreAlumna");
	            String primerApellidosAlumna = resultado.getString("primerApellidoAlumna");
	            String segundoApellidosAlumna = resultado.getString("segundoApellidoAlumna");
	            String nombreSede = resultado.getString("nombreSede");
	            String nombreContacto = resultado.getString("nombreContacto");
	            String primerApellidoContacto = resultado.getString("primerApellidoContacto");
	            String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

	            // Formatear la información para mostrar en el JTextArea
	            String infoPractica = "Contacto: " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto + "\n"+
	            		"Horario: " + horario + "\n" +
	                    "Alumna: " + nombreAlumna + " " + primerApellidosAlumna + " " + segundoApellidosAlumna + "\n" +
	                    "Sede: " + nombreSede + "\n\n";

	            textArea.append(infoPractica);
	        }

	        System.out.println("Listado de prácticas ordenado por nombre de contacto rellenado correctamente.");

	        // Cerrar recursos
	        resultado.close();
	        statement.close();

	    } catch (SQLException sqle) {
	        System.out.println("Error al rellenar listado de prácticas: " + sqle.getMessage());
	    }
	}
	public List<String> obtenerListadoPracticaAdmin() {
		List<String> practicas = new ArrayList<>();
		String sentencia = "SELECT p.idPractica, p.horario, a.nombreAlumna, a.primerApellidoAlumna, a.segundoApellidoAlumna, " +
				"se.nombreSede, ce.nombreContacto, ce.primerApellidoContacto, ce.segundoApellidoContacto " +
				"FROM Practicas p " +
				"JOIN Alumnas a ON p.idAlumna = a.idAlumna " +
				"JOIN SedesEmpresa se ON p.idSede = se.idSede " +
				"JOIN ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
				"JOIN Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
				"JOIN Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
				"WHERE eSede.tipoEmpresa = 1 AND eContacto.tipoEmpresa = 1";

		try (PreparedStatement statement = connection.prepareStatement(sentencia);
				ResultSet resultado = statement.executeQuery()) {

			while (resultado.next()) {
				String idPractica = resultado.getString("idPractica");
				String horario = resultado.getString("horario");
				String nombreAlumna = resultado.getString("nombreAlumna");
				String primerApellidoAlumna = resultado.getString("primerApellidoAlumna");
				String segundoApellidoAlumna = resultado.getString("segundoApellidoAlumna");
				String nombreSede = resultado.getString("nombreSede");
				String nombreContacto = resultado.getString("nombreContacto");
				String primerApellidoContacto = resultado.getString("primerApellidoContacto");
				String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

				String practicaString = idPractica + " - " + horario + " - " + nombreAlumna + " " + primerApellidoAlumna + " " + segundoApellidoAlumna +
						" - " + nombreSede + " - " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto;
				practicas.add(practicaString);
			}

		} catch (SQLException sqle) {
			System.out.println("Error al obtener listado de prácticas: " + sqle.getMessage());
		}

		return practicas;
	}
	public List<String> obtenerListadoPracticaEnf() {
		List<String> practicas = new ArrayList<>();
		String sentencia = "SELECT p.idPractica, p.horario, a.nombreAlumna, a.primerApellidoAlumna, a.segundoApellidoAlumna, " +
				"se.nombreSede, ce.nombreContacto, ce.primerApellidoContacto, ce.segundoApellidoContacto " +
				"FROM Practicas p " +
				"JOIN Alumnas a ON p.idAlumna = a.idAlumna " +
				"JOIN SedesEmpresa se ON p.idSede = se.idSede " +
				"JOIN ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
				"JOIN Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
				"JOIN Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
				"WHERE eSede.tipoEmpresa = 2 AND eContacto.tipoEmpresa = 2";

		try (PreparedStatement statement = connection.prepareStatement(sentencia);
				ResultSet resultado = statement.executeQuery()) {

			while (resultado.next()) {
				String idPractica = resultado.getString("idPractica");
				String horario = resultado.getString("horario");
				String nombreAlumna = resultado.getString("nombreAlumna");
				String primerApellidoAlumna = resultado.getString("primerApellidoAlumna");
				String segundoApellidoAlumna = resultado.getString("segundoApellidoAlumna");
				String nombreSede = resultado.getString("nombreSede");
				String nombreContacto = resultado.getString("nombreContacto");
				String primerApellidoContacto = resultado.getString("primerApellidoContacto");
				String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

				String practicaString = idPractica + " - " + horario + " - " + nombreAlumna + " " + primerApellidoAlumna + " " + segundoApellidoAlumna +
						" - " + nombreSede + " - " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto;
				practicas.add(practicaString);
			}

		} catch (SQLException sqle) {
			System.out.println("Error al obtener listado de prácticas: " + sqle.getMessage());
		}

		return practicas;
	}
	public List<String> obtenerListadoPracticaEduc() {
		List<String> practicas = new ArrayList<>();
		String sentencia = "SELECT p.idPractica, p.horario, a.nombreAlumna, a.primerApellidoAlumna, a.segundoApellidoAlumna, " +
				"se.nombreSede, ce.nombreContacto, ce.primerApellidoContacto, ce.segundoApellidoContacto " +
				"FROM Practicas p " +
				"JOIN Alumnas a ON p.idAlumna = a.idAlumna " +
				"JOIN SedesEmpresa se ON p.idSede = se.idSede " +
				"JOIN ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
				"JOIN Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
				"JOIN Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
				"WHERE eSede.tipoEmpresa = 3 AND eContacto.tipoEmpresa = 3";

		try (PreparedStatement statement = connection.prepareStatement(sentencia);
				ResultSet resultado = statement.executeQuery()) {

			while (resultado.next()) {
				String idPractica = resultado.getString("idPractica");
				String horario = resultado.getString("horario");
				String nombreAlumna = resultado.getString("nombreAlumna");
				String primerApellidoAlumna = resultado.getString("primerApellidoAlumna");
				String segundoApellidoAlumna = resultado.getString("segundoApellidoAlumna");
				String nombreSede = resultado.getString("nombreSede");
				String nombreContacto = resultado.getString("nombreContacto");
				String primerApellidoContacto = resultado.getString("primerApellidoContacto");
				String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

				String practicaString = idPractica + " - " + horario + " - " + nombreAlumna + " " + primerApellidoAlumna + " " + segundoApellidoAlumna +
						" - " + nombreSede + " - " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto;
				practicas.add(practicaString);
			}

		} catch (SQLException sqle) {
			System.out.println("Error al obtener listado de prácticas: " + sqle.getMessage());
		}

		return practicas;
	}
	public List<String> obtenerListadoPracticaEducOn() {
		List<String> practicas = new ArrayList<>();
		String sentencia = "SELECT p.idPractica, p.horario, a.nombreAlumna, a.primerApellidoAlumna, a.segundoApellidoAlumna, " +
				"se.nombreSede, ce.nombreContacto, ce.primerApellidoContacto, ce.segundoApellidoContacto " +
				"FROM Practicas p " +
				"JOIN Alumnas a ON p.idAlumna = a.idAlumna " +
				"JOIN SedesEmpresa se ON p.idSede = se.idSede " +
				"JOIN ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
				"JOIN Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
				"JOIN Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
				"WHERE eSede.tipoEmpresa = 4 AND eContacto.tipoEmpresa = 4";

		try (PreparedStatement statement = connection.prepareStatement(sentencia);
				ResultSet resultado = statement.executeQuery()) {

			while (resultado.next()) {
				String idPractica = resultado.getString("idPractica");
				String horario = resultado.getString("horario");
				String nombreAlumna = resultado.getString("nombreAlumna");
				String primerApellidoAlumna = resultado.getString("primerApellidoAlumna");
				String segundoApellidoAlumna = resultado.getString("segundoApellidoAlumna");
				String nombreSede = resultado.getString("nombreSede");
				String nombreContacto = resultado.getString("nombreContacto");
				String primerApellidoContacto = resultado.getString("primerApellidoContacto");
				String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

				String practicaString = idPractica + " - " + horario + " - " + nombreAlumna + " " + primerApellidoAlumna + " " + segundoApellidoAlumna +
						" - " + nombreSede + " - " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto;
				practicas.add(practicaString);
			}

		} catch (SQLException sqle) {
			System.out.println("Error al obtener listado de prácticas: " + sqle.getMessage());
		}

		return practicas;
	}
	public List<String> obtenerListadoPracticaAsis() {
		List<String> practicas = new ArrayList<>();
		String sentencia = "SELECT p.idPractica, p.horario, a.nombreAlumna, a.primerApellidoAlumna, a.segundoApellidoAlumna, " +
				"se.nombreSede, ce.nombreContacto, ce.primerApellidoContacto, ce.segundoApellidoContacto " +
				"FROM Practicas p " +
				"JOIN Alumnas a ON p.idAlumna = a.idAlumna " +
				"JOIN SedesEmpresa se ON p.idSede = se.idSede " +
				"JOIN ContactosEmpresa ce ON p.idContacto = ce.idContacto " +
				"JOIN Empresas eSede ON se.idEmpresaFK = eSede.idEmpresa " +
				"JOIN Empresas eContacto ON ce.idEmpresaFK = eContacto.idEmpresa " +
				"WHERE eSede.tipoEmpresa = 5 AND eContacto.tipoEmpresa = 5";

		try (PreparedStatement statement = connection.prepareStatement(sentencia);
				ResultSet resultado = statement.executeQuery()) {

			while (resultado.next()) {
				String idPractica = resultado.getString("idPractica");
				String horario = resultado.getString("horario");
				String nombreAlumna = resultado.getString("nombreAlumna");
				String primerApellidoAlumna = resultado.getString("primerApellidoAlumna");
				String segundoApellidoAlumna = resultado.getString("segundoApellidoAlumna");
				String nombreSede = resultado.getString("nombreSede");
				String nombreContacto = resultado.getString("nombreContacto");
				String primerApellidoContacto = resultado.getString("primerApellidoContacto");
				String segundoApellidoContacto = resultado.getString("segundoApellidoContacto");

				String practicaString = idPractica + " - " + horario + " - " + nombreAlumna + " " + primerApellidoAlumna + " " + segundoApellidoAlumna +
						" - " + nombreSede + " - " + nombreContacto + " " + primerApellidoContacto + " " + segundoApellidoContacto;
				practicas.add(practicaString);
			}

		} catch (SQLException sqle) {
			System.out.println("Error al obtener listado de prácticas: " + sqle.getMessage());
		}

		return practicas;
	}
	 public int eliminarEmpresa(String sentencia) {
	        try (Statement statement = connection.createStatement()) {
	            return statement.executeUpdate(sentencia);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return -1; // Devolver un valor distinto de cero para indicar error
	        }
	    }

	 public boolean tieneContactosAsociados(String idEmpresa) {
		    String query = "SELECT COUNT(*) AS count FROM ContactosEmpresa WHERE idEmpresa = ?";
		    try (PreparedStatement statement = connection.prepareStatement(query)) {
		        statement.setString(1, idEmpresa);
		        ResultSet rs = statement.executeQuery();
		        if (rs.next()) {
		            int count = rs.getInt("count");
		            return count > 0;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return false;
		}

		public boolean tieneSedesAsociadas(String idEmpresa) {
		    String query = "SELECT COUNT(*) AS count FROM SedesEmpresa WHERE idEmpresa = ?";
		    try (PreparedStatement statement = connection.prepareStatement(query)) {
		        statement.setString(1, idEmpresa);
		        ResultSet rs = statement.executeQuery();
		        if (rs.next()) {
		            int count = rs.getInt("count");
		            return count > 0;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return false;
		}


	 public boolean tienePracticasAsociadas(String idContacto) {
	        try (Statement statement = connection.createStatement()) {
	            String query = "SELECT COUNT(*) AS count FROM practicas WHERE idContacto=" + idContacto;
	            ResultSet rs = statement.executeQuery(query);
	            if (rs.next()) {
	                int count = rs.getInt("count");
	                return count > 0;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	 public boolean tienePracticasAsociadas2(String idSede) {
	        try (Statement statement = connection.createStatement()) {
	            String query = "SELECT COUNT(*) AS count FROM practicas WHERE idSede=" + idSede;
	            ResultSet rs = statement.executeQuery(query);
	            if (rs.next()) {
	                int count = rs.getInt("count");
	                return count > 0;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	    public int eliminarContacto(String idContacto) {
	        try (Statement statement = connection.createStatement()) {
	            String query = "DELETE FROM ContactosEmpresa WHERE idContacto=" + idContacto;
	            return statement.executeUpdate(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return -1; // Devolver un valor distinto de cero para indicar error
	        }
	    }
	    public int eliminarSede(String idSede) {
	        try (Statement statement = connection.createStatement()) {
	            String query = "DELETE FROM SedesEmpresa WHERE idSede=" + idSede;
	            return statement.executeUpdate(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return -1; // Devolver un valor distinto de cero para indicar error
	        }
	    }
	    public int eliminarPractica(String idPractica) {
	        try (Statement statement = connection.createStatement()) {
	            String query = "DELETE FROM Practicas WHERE idPractica=" + idPractica;
	            return statement.executeUpdate(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return -1; // Devolver un valor distinto de cero para indicar error
	        }
	    }
	    public int eliminarPadre(String idPadre) {
	        try (Statement statement = connection.createStatement()) {
	            String query = "DELETE FROM Padres WHERE idPadre=" + idPadre;
	            return statement.executeUpdate(query);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return -1; // Devolver un valor distinto de cero para indicar error
	        }
	    }
	    public int eliminarUsuario(String idUsuario) {
	        String query = "DELETE FROM usuarios WHERE idUsuario = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setString(1, idUsuario);
	            return preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return -1; // Devolver un valor distinto de cero para indicar error
	        }
	    }
	    public List<String> obtenerListadoUsuarios() {
	        List<String> usuarios = new ArrayList<>();
	        String sentencia = "SELECT idUsuario, nombreUsuario, claveUsuario FROM usuarios;";
	        
	        try (Statement statement = connection.createStatement(
	                ResultSet.TYPE_SCROLL_SENSITIVE, 
	                ResultSet.CONCUR_READ_ONLY);
	             ResultSet resultado = statement.executeQuery(sentencia)) {
	            
	            while (resultado.next()) {
	            	 String usuario = String.format("Nombre de Usuario: %s | Clave: %s",
	                         resultado.getString("nombreUsuario"),
	                         resultado.getString("claveUsuario"));                                       
	                usuarios.add(usuario);
	            }
	            System.out.println("Listado de usuarios rellenado correctamente.");
	        } catch (SQLException sqle) {
	            System.out.println("Error 4-" + sqle.getMessage());
	        }
	        
	        return usuarios;
	    }


}
