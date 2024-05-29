import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos.
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/Base_De_Datos_APP";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789";

    /**
     * Obtiene una conexión a la base de datos.
     * 
     * @return La conexión a la base de datos.
     * @throws SQLException Si ocurre un error al conectar.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
