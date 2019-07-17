package servicio.archms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import servicio.modelo.Tema;
import servicio.utils.ProcesadorTexto;

public class ArchMSConnection {

	private static final String USER = "josef";
	private static final String PASS = "tfg2018pw";
	private static final String URL = "jdbc:mysql://sele.inf.um.es:3306/editor2";

	public static List<Tema> buscar() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			System.out.println("Error con driver JDBC.");
		}

		Properties props = new Properties();
		props.put("user", USER);
		props.put("password", PASS);
		props.put("useUnicode", "true");
		props.put("useServerPrepStmts", "false"); // use client-side prepared statement
		props.put("characterEncoding", "UTF-8"); // ensure charset is utf8 here

		try (Connection connection = DriverManager.getConnection(URL, props)) {

			List<Tema> temas = new LinkedList<Tema>();
			Statement s = connection.createStatement();
			String query = "select id, name, archetype_id from Archetype";
			ResultSet r = s.executeQuery(query);

			while (r.next()) {

				Statement s2 = connection.createStatement();
				String query2 = "select label_annotation from Annotation where id_archetype = " + r.getInt("id");
				ResultSet r2 = s2.executeQuery(query2);

				while (r2.next()) {

					Tema t = new Tema();
					t.setNombre(ProcesadorTexto.eliminarParentesis(r2.getString(1)));
					if(temas.isEmpty()) {
						temas.add(t);
					}else {
						
						boolean exists = false;
						for (Tema tema : temas) {
							if(t.getNombre().toLowerCase().equals(tema.getNombre().toLowerCase())) {
								exists = true;
								break;
							}	
						}
						
						if(!exists)
							temas.add(t);
					}
				}
			}

			connection.close();
			return temas;

		} catch (SQLException e) {
			System.out.println("Error al conectar con ArchMS");
			return null;
		}

	}
}
