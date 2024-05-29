package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EquipoTest {

    private Equipo equipo;

    @BeforeEach
    public void setUp() {
        equipo = new Equipo(1, "Real Madrid", "España", "1902-03-06", "Santiago Bernabéu", 10);
    }

    @Test
    public void testGetNombre() {
        assertEquals("Real Madrid", equipo.getNombre(), "El nombre del equipo debería ser Real Madrid");
    }

    @Test
    public void testSetNombre() {
        equipo.setNombre("FC Barcelona");
        assertEquals("FC Barcelona", equipo.getNombre(), "El nombre del equipo debería ser FC Barcelona");
    }

    @Test
    public void testGetEntrenadorId() {
        assertEquals(10, equipo.getEntrenadorId(), "El ID del entrenador debería ser 10");
    }

    @Test
    public void testSetEntrenadorId() {
        equipo.setEntrenadorId(20);
        assertEquals(20, equipo.getEntrenadorId(), "El ID del entrenador debería ser 20");
    }
}
