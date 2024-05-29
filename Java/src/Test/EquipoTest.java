package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EquipoTest {

    private Equipo equipo;  // Variable para la clase Equipo

    /**
     * Configuración inicial antes de cada prueba.
     * 
     * <p>Este método se ejecuta antes de cada caso de prueba para inicializar
     * un objeto Equipo con valores predeterminados.</p>
     */
    @BeforeEach
    public void setUp() {
        equipo = new Equipo(1, "Real Madrid", "España", "1902-03-06", "Santiago Bernabéu", 10);
    }

    /**
     * Prueba el método getNombre.
     * 
     * <p>Verifica que el método getNombre de la clase Equipo devuelva el
     * nombre correcto del equipo.</p>
     */
    @Test
    public void testGetNombre() {
        assertEquals("Real Madrid", equipo.getNombre(), "El nombre del equipo debería ser Real Madrid");
    }

    /**
     * Prueba el método setNombre.
     * 
     * <p>Verifica que el método setNombre de la clase Equipo establezca
     * correctamente el nombre del equipo.</p>
     */
    @Test
    public void testSetNombre() {
        equipo.setNombre("FC Barcelona");
        assertEquals("FC Barcelona", equipo.getNombre(), "El nombre del equipo debería ser FC Barcelona");
    }

    /**
     * Prueba el método getEntrenadorId.
     * 
     * <p>Verifica que el método getEntrenadorId de la clase Equipo devuelva
     * el ID correcto del entrenador.</p>
     */
    @Test
    public void testGetEntrenadorId() {
        assertEquals(10, equipo.getEntrenadorId(), "El ID del entrenador debería ser 10");
    }

    /**
     * Prueba el método setEntrenadorId.
     * 
     * <p>Verifica que el método setEntrenadorId de la clase Equipo establezca
     * correctamente el ID del entrenador.</p>
     */
    @Test
    public void testSetEntrenadorId() {
        equipo.setEntrenadorId(20);
        assertEquals(20, equipo.getEntrenadorId(), "El ID del entrenador debería ser 20");
    }
}
