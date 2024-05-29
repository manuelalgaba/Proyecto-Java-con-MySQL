package Test;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class AppTest {

    private Connection conn;

    @BeforeEach
    public void setUp() throws SQLException {
        conn = DatabaseConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Equipos (ID_equipo INT PRIMARY KEY, nombre VARCHAR(50))")) {
            stmt.execute();
        }
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Equipos (ID_equipo, nombre) VALUES (1, 'Real Madrid')")) {
            stmt.execute();
        }
    }

    @AfterEach
    public void Borrar() throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("Borrar tabala si existe Equipos")) {
            stmt.execute();
        }
        conn.close();
    }

    @Test
    public void testObtenerIdEquipoPorNombre() {
        int id = App.obtenerIdEquipoPorNombre("Real Madrid");
        assertEquals(1, id, "El ID del equipo debería ser 1");
    }

    @Test
    public void testObtenerIdEquipoPorNombreNoExistente() {
        int id = App.obtenerIdEquipoPorNombre("FC Barcelona");
        assertEquals(-1, id, "El ID del equipo debería ser -1 para un equipo no existente");
    }
}
