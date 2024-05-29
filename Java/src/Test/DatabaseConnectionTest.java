package Test;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class DatabaseConnectionTest {

    @Test
    public void testGetConnection() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            assertNotNull(conn, "La conexión no debería ser nula");
            conn.close();
        } catch (SQLException e) {
            fail("No se pudo establecer la conexión a la base de datos: " + e.getMessage());
        }
    }
}
