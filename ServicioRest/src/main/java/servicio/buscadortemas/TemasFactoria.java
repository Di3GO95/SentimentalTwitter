package servicio.buscadortemas;

import java.util.List;

import servicio.buscadortemas.archms.ArchMSConnection;
import servicio.modelo.Tema;

public class TemasFactoria {
	
	private static TemasFactoria unicaInstancia;
	
	protected static String user;
	protected static String pass;
	protected static String url;
	
	// Declaracion como constantes de los tipos de factoria
	public final static int ARCH_MS = 1;
	
	protected TemasFactoria() {}
	
	// Método factoría
	public List<Tema> getTemas(){return null;}
	
	/**
	 * Inicializa los datos necesarios para establecer una conexión con una base de datos que tenga los temas
	 * @param user Usuario
	 * @param pass Contraseña
	 * @param url URL
	 */
	public static void inicializar(String user, String pass, String url) {
		TemasFactoria.user = user;
		TemasFactoria.pass = pass;
		TemasFactoria.url = url;
	}
	
	public static TemasFactoria getUnicaInstancia() {
		if (unicaInstancia == null) {
			unicaInstancia = new TemasFactoria();
		}
			
		return unicaInstancia;
	}
	
	public static void setDAOFactoria(int tipo) {
		switch(tipo) {
			case ARCH_MS:
				unicaInstancia = new ArchMSConnection();
				break;
			default:
				System.err.println("Tipo de factoria de temas no encontrado.");
				break;
		}
	}
}
