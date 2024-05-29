package Test;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test; 

public class DatabaseConnectionTest {

    /**
     * Prueba para verificar que se puede establecer una conexión con la base de datos.
     */
    @Test
    public void testGetConnection() {
        try {
            // Intenta obtener una conexión a la base de datos
            Connection conn = DatabaseConnection.getConnection();
            
            // Verifica que la conexión no sea nula
            assertNotNull(conn, "La conexión no debería ser nula");
            
            // Cierra la conexión para liberar recursos
            conn.close();
        } catch (SQLException e) {
            // Si ocurre una excepción SQL, la prueba falla y muestra el mensaje de error
            fail("No se pudo establecer la conexión a la base de datos: " + e.getMessage());
        }
    }
}
