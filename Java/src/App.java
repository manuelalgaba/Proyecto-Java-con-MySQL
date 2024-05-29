import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Clase principal para la gestion de una aplicacion de fútbol.
 */
public class App {
    /**
     * Metodo principal para iniciar la aplicación.
     *
     * @param args Argumentos de la linea de comandos.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean salir = false;

        while (!salir) {
            System.out.println("");
            System.out.println("Bienvenido al programa de gestión de fútbol");
            System.out.println("Por favor, seleccione una opción:");
            System.out.println("1. Buscar");
            System.out.println("2. Añadir");
            System.out.println("3. Borrar");
            System.out.println("4. Modificar");
            System.out.println("5. Ver clasificacion ligas");
            System.out.println("6. Ver plantilla Ordenada");
            System.out.println("7. Crear torneo");
            System.out.println("8. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    buscar(scanner);
                    break;
                case 2:
                    agregar(scanner);
                    break;
                case 3:
                    borrar(scanner);
                    break;
                case 4:
                    modificar(scanner);
                    break;
                case 5:
                    verClasificacion(scanner);
                    break;
                case 6:
                    verPlantillaOrdenada(scanner);
                    break;
                case 7:
                    crearTorneo(scanner);
                    break;
                case 8:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
        scanner.close();
    }

    // Metodos de busqueda
    /**
     * Busca una entidad en la base de datos.
     * 
     * @param scanner El escaner para la entrada del usuario
     */
    // Metodo para buscar
    public static void buscar(Scanner scanner) {
        System.out.println("Seleccione la entidad a buscar:");
        System.out.println("1. Entrenador");
        System.out.println("2. Jugador");
        System.out.println("3. Equipo");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Ingrese el nombre:");
        String nombre = scanner.nextLine();

        switch (opcion) {
            case 1:
                buscarEntrenador(nombre);
                break;
            case 2:
                buscarJugador(nombre);
                break;
            case 3:
                buscarEquipo(nombre);
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    /**
     * Busca un entrenador por su nombre.
     * 
     * @param nombre Nombre del entrenador
     */
    // Metodos para buscar entidades
    private static void buscarEntrenador(String nombre) {
        String sql = "SELECT t.*, e.nombre AS equipo_nombre " +
                "FROM Entrenadores t " +
                "LEFT JOIN Equipos e ON t.ID_entrenador = e.entrenador_id " +
                "WHERE t.nombre = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID_entrenador"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Nacionalidad: " + rs.getString("nacionalidad"));
                System.out.println("Fecha de Nacimiento: " + rs.getDate("fecha_nacimiento"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Telefono: " + rs.getString("telefono"));
                System.out.println("Equipo: " + rs.getString("equipo_nombre"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Busca un jugador por su nombre.
     * 
     * @param nombre Nombre del jugador
     */
    private static void buscarJugador(String nombre) {
        String sql = "SELECT j.*, e.nombre AS equipo_nombre, t.nombre AS entrenador_nombre " +
                "FROM Jugadores j " +
                "JOIN Equipos e ON j.equipo_id = e.ID_equipo " +
                "JOIN Entrenadores t ON j.entrenador_id = t.ID_entrenador " +
                "WHERE j.nombre = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID_jugador"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Nacionalidad: " + rs.getString("nacionalidad"));
                System.out.println("Fecha de Nacimiento: " + rs.getDate("fecha_nacimiento"));
                System.out.println("Posición: " + rs.getString("posicion"));
                System.out.println("Equipo: " + rs.getString("equipo_nombre"));
                System.out.println("Entrenador: " + rs.getString("entrenador_nombre"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Busca un equipo por su nombre.
     * 
     * @param nombre Nombre del equipo
     */
    private static void buscarEquipo(String nombre) {
        String sql = "SELECT e.*, t.nombre AS entrenador_nombre " +
                "FROM Equipos e " +
                "LEFT JOIN Entrenadores t ON e.entrenador_id = t.ID_entrenador " +
                "WHERE e.nombre = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int equipoId = rs.getInt("ID_equipo");
                System.out.println("ID: " + equipoId);
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("País: " + rs.getString("pais"));
                System.out.println("Fundación: " + rs.getDate("fundacion"));
                System.out.println("Estadio: " + rs.getString("estadio"));
                System.out.println("Entrenador: " + rs.getString("entrenador_nombre"));

                // Obtener y mostrar la plantilla del equipo
                List<Jugador> jugadores = obtenerJugadoresPorEquipo(equipoId);
                System.out.println("\nPlantilla del equipo:");
                for (Jugador jugador : jugadores) {
                    System.out.println(jugador);
                }
            } else {
                System.out.println("Equipo no encontrado.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Metodo para aniadir
    /**
     * Aniade una nueva entidad a la base de datos.
     * 
     * @param scanner El escaner para la entrada del usuario
     */
    public static void agregar(Scanner scanner) {
        System.out.println("Seleccione la entidad a añadir:");
        System.out.println("1. Entrenador");
        System.out.println("2. Jugador");
        System.out.println("3. Equipo");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (opcion) {
            case 1:
                agregarEntrenador(scanner);
                break;
            case 2:
                agregarJugador(scanner);
                break;
            case 3:
                agregarEquipo(scanner);
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    // Metodos para aniadir entidades
    /**
     * Aniade un nuevo entrenador a la base de datos.
     * 
     * @param scanner El escaner para la entrada del usuario
     */
    private static void agregarEntrenador(Scanner scanner) {
        System.out.println("Ingrese nombre:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese nacionalidad:");
        String nacionalidad = scanner.nextLine();
        System.out.println("Ingrese fecha de nacimiento (YYYY-MM-DD):");
        String fechaNacimiento = scanner.nextLine();
        System.out.println("Ingrese email:");
        String email = scanner.nextLine();
        System.out.println("Ingrese telefono:");
        String telefono = scanner.nextLine();

        String sql = "INSERT INTO Entrenadores (nombre, nacionalidad, fecha_nacimiento, email, telefono) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, nacionalidad);
            pstmt.setString(3, fechaNacimiento);
            pstmt.setString(4, email);
            pstmt.setString(5, telefono);
            pstmt.executeUpdate();
            System.out.println("Entrenador añadido correctamente");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Aniade un nuevo jugador a la base de datos.
     * 
     * @param scanner El escaner para la entrada del usuario
     */
    private static void agregarJugador(Scanner scanner) {
        System.out.println("Ingrese nombre:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese nacionalidad:");
        String nacionalidad = scanner.nextLine();
        System.out.println("Ingrese fecha de nacimiento (YYYY-MM-DD):");
        String fechaNacimiento = scanner.nextLine();
        System.out.println("Ingrese posición:");
        String posicion = scanner.nextLine();
        System.out.println("Ingrese nombre del equipo:");
        String nombreEquipo = scanner.nextLine();
        System.out.println("Ingrese nombre del entrenador:");
        String nombreEntrenador = scanner.nextLine();

        // Buscar el ID del equipo y del entrenador a partir de sus nombres
        int equipoId = obtenerIdEquipoPorNombre(nombreEquipo);
        int entrenadorId = obtenerIdEntrenadorPorNombre(nombreEntrenador);

        if (equipoId == -1) {
            System.out.println("Equipo no encontrado. Por favor, añádalo primero.");
            return;
        }

        if (entrenadorId == -1) {
            System.out.println("Entrenador no encontrado. Por favor, añádalo primero.");
            return;
        }

        String sql = "INSERT INTO Jugadores (nombre, nacionalidad, fecha_nacimiento, posicion, equipo_id, entrenador_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, nacionalidad);
            pstmt.setString(3, fechaNacimiento);
            pstmt.setString(4, posicion);
            pstmt.setInt(5, equipoId);
            pstmt.setInt(6, entrenadorId);
            pstmt.executeUpdate();
            System.out.println("Jugador añadido correctamente");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Aniade un nuevo equipo a la base de datos.
     * 
     * @param scanner El escaner para la entrada del usuario
     */
    private static int obtenerIdEquipoPorNombre(String nombre) {
        String sql = "SELECT ID_equipo FROM Equipos WHERE nombre = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID_equipo");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1; // Retorna -1 si el equipo no fue encontrado
    }
/**
     * Aniade un nuevo equipo a la base de datos.
     * 
     * @param scanner El escaner para la entrada del usuario
     */
    private static void agregarEquipo(Scanner scanner) {
        System.out.println("Ingrese nombre del equipo:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese país:");
        String pais = scanner.nextLine();
        System.out.println("Ingrese fundación (YYYY-MM-DD):");
        String fundacion = scanner.nextLine();
        System.out.println("Ingrese estadio:");
        String estadio = scanner.nextLine();
        System.out.println("Ingrese nombre del entrenador:");
        String nombreEntrenador = scanner.nextLine();

        // Buscar el ID del entrenador a partir del nombre
        int entrenadorId = obtenerIdEntrenadorPorNombre(nombreEntrenador);

        if (entrenadorId == -1) {
            System.out.println("Entrenador no encontrado. Por favor, añádalo primero.");
            return;
        }

        String sql = "INSERT INTO Equipos (nombre, pais, fundacion, estadio, entrenador_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, pais);
            pstmt.setString(3, fundacion);
            pstmt.setString(4, estadio);
            pstmt.setInt(5, entrenadorId);
            pstmt.executeUpdate();
            System.out.println("Equipo añadido correctamente");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Obtiene el ID del entrenador por su nombre.
     * 
     * @param nombre Nombre del entrenador
     * @return El ID del entrenador o -1 si no se encuentra
     */
    private static int obtenerIdEntrenadorPorNombre(String nombre) {
        String sql = "SELECT ID_entrenador FROM Entrenadores WHERE nombre = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID_entrenador");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1; // Retorna -1 si el entrenador no fue encontrado
    }

    /**
     * Borra una entidad en la base de datos.
     * 
     * @param scanner El escaner para la entrada del usuario
     */
    public static void borrar(Scanner scanner) {
        System.out.println("Seleccione la entidad a borrar:");
        System.out.println("1. Entrenador");
        System.out.println("2. Jugador");
        System.out.println("3. Equipo");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                borrarEntrenador(scanner);
                ;
                break;
            case 2:
                borrarJugador(scanner);
                break;
            case 3:
                borrarEquipo(scanner);
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    // Metodos para borrar entidades
    /**
     * Borra un entrenador en la base de datos.
     * 
     * @param nombre Nombre del entrenador
     */
    private static void borrarEntrenador(Scanner scanner) {
        System.out.println("Ingrese el nombre del entrenador:");
        String nombre = scanner.nextLine();

        int id = obtenerIdEntrenadorPorNombre(nombre);
        if (id == -1) {
            System.out.println("Entrenador no encontrado.");
            return;
        }

        String sql = "DELETE FROM Entrenadores WHERE ID_entrenador = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Entrenador borrado correctamente");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Borra un jugador en la base de datos.
     * 
     * @param nombre Nombre del jugador
     */
    private static void borrarJugador(Scanner scanner) {
        System.out.println("Ingrese el nombre del jugador:");
        String nombre = scanner.nextLine();

        int id = obtenerIdJugadorPorNombre(nombre);
        if (id == -1) {
            System.out.println("Jugador no encontrado.");
            return;
        }

        String sql = "DELETE FROM Jugadores WHERE ID_jugador = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Jugador borrado correctamente");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int obtenerIdJugadorPorNombre(String nombre) {
        String sql = "SELECT ID_jugador FROM Jugadores WHERE nombre = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID_jugador");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1; // Retorna -1 si el jugador no fue encontrado
    }

    /**
     * Borra un equipo en la base de datos.
     * 
     * @param nombre Nombre del equipo
     */
    private static void borrarEquipo(Scanner scanner) {
        System.out.println("Ingrese el nombre del equipo:");
        String nombre = scanner.nextLine();

        int id = obtenerIdEquipoPorNombre(nombre);
        if (id == -1) {
            System.out.println("Equipo no encontrado.");
            return;
        }

        String sql = "DELETE FROM Equipos WHERE ID_equipo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Equipo borrado correctamente");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Metodos de modificación
    /**
     * Modifica una entidad en la base de datos.
     * 
     * @param scanner El escaner para la entrada del usuario
     */
    public static void modificar(Scanner scanner) {
        System.out.println("Seleccione la entidad a modificar:");
        System.out.println("1. Entrenador");
        System.out.println("2. Jugador");
        System.out.println("3. Equipo");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (opcion) {
            case 1:
                modificarEntrenador(scanner);
                break;
            case 2:
                modificarJugador(scanner);
                break;
            case 3:
                modificarEquipo(scanner);
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    // Metodos para modificar entidades
    /**
     * Modifica un entrenador en la base de datos.
     * 
     * @param nombreOriginal Nombre original del entrenador
     * @param scanner        El escaner para la entrada del usuario
     */
    private static void modificarEntrenador(Scanner scanner) {
        System.out.println("Ingrese el nombre del entrenador a modificar:");
        String nombreOriginal = scanner.nextLine();

        int id = obtenerIdEntrenadorPorNombre(nombreOriginal);
        if (id == -1) {
            System.out.println("Entrenador no encontrado.");
            return;
        }

        System.out.println("Ingrese nuevo nombre:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese nueva nacionalidad:");
        String nacionalidad = scanner.nextLine();
        System.out.println("Ingrese nueva fecha de nacimiento (YYYY-MM-DD):");
        String fechaNacimiento = scanner.nextLine();
        System.out.println("Ingrese nuevo email:");
        String email = scanner.nextLine();
        System.out.println("Ingrese nuevo telefono:");
        String telefono = scanner.nextLine();

        String sql = "UPDATE Entrenadores SET nombre = ?, nacionalidad = ?, fecha_nacimiento = ?, email = ?, telefono = ? WHERE ID_entrenador = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, nacionalidad);
            pstmt.setString(3, fechaNacimiento);
            pstmt.setString(4, email);
            pstmt.setString(5, telefono);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            System.out.println("Entrenador modificado correctamente");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Modifica un jugador en la base de datos.
     * 
     * @param nombreOriginal Nombre original del jugador
     * @param scanner        El escaner para la entrada del usuario
     */
    private static void modificarJugador(Scanner scanner) {
        System.out.println("Ingrese el nombre del jugador a modificar:");
        String nombreOriginal = scanner.nextLine();

        int id = obtenerIdJugadorPorNombre(nombreOriginal);
        if (id == -1) {
            System.out.println("Jugador no encontrado.");
            return;
        }

        System.out.println("Ingrese nuevo nombre:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese nueva nacionalidad:");
        String nacionalidad = scanner.nextLine();
        System.out.println("Ingrese nueva fecha de nacimiento (YYYY-MM-DD):");
        String fechaNacimiento = scanner.nextLine();
        System.out.println("Ingrese nueva posición:");
        String posicion = scanner.nextLine();
        System.out.println("Ingrese nuevo nombre del equipo:");
        String nombreEquipo = scanner.nextLine();
        System.out.println("Ingrese nuevo nombre del entrenador:");
        String nombreEntrenador = scanner.nextLine();

        // Buscar el ID del equipo y del entrenador a partir de sus nombres
        int equipoId = obtenerIdEquipoPorNombre(nombreEquipo);
        int entrenadorId = obtenerIdEntrenadorPorNombre(nombreEntrenador);

        if (equipoId == -1) {
            System.out.println("Equipo no encontrado. Por favor, añádalo primero.");
            return;
        }

        if (entrenadorId == -1) {
            System.out.println("Entrenador no encontrado. Por favor, añádalo primero.");
            return;
        }

        String sql = "UPDATE Jugadores SET nombre = ?, nacionalidad = ?, fecha_nacimiento = ?, posicion = ?, equipo_id = ?, entrenador_id = ? WHERE ID_jugador = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, nacionalidad);
            pstmt.setString(3, fechaNacimiento);
            pstmt.setString(4, posicion);
            pstmt.setInt(5, equipoId);
            pstmt.setInt(6, entrenadorId);
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
            System.out.println("Jugador modificado correctamente");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Modifica un equipo en la base de datos.
     * 
     * @param nombreOriginal Nombre original del equipo
     * @param scanner        El escaner para la entrada del usuario
     */
    private static void modificarEquipo(Scanner scanner) {
        System.out.println("Ingrese el nombre del equipo a modificar:");
        String nombreOriginal = scanner.nextLine();

        int id = obtenerIdEquipoPorNombre(nombreOriginal);
        if (id == -1) {
            System.out.println("Equipo no encontrado.");
            return;
        }

        System.out.println("Ingrese nuevo nombre del equipo:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese nuevo país:");
        String pais = scanner.nextLine();
        System.out.println("Ingrese nueva fundación (YYYY-MM-DD):");
        String fundacion = scanner.nextLine();
        System.out.println("Ingrese nuevo estadio:");
        String estadio = scanner.nextLine();
        System.out.println("Ingrese nuevo nombre del entrenador:");
        String nombreEntrenador = scanner.nextLine();

        // Buscar el ID del entrenador a partir del nombre
        int entrenadorId = obtenerIdEntrenadorPorNombre(nombreEntrenador);

        if (entrenadorId == -1) {
            System.out.println("Entrenador no encontrado. Por favor, añádalo primero.");
            return;
        }

        String sql = "UPDATE Equipos SET nombre = ?, pais = ?, fundacion = ?, estadio = ?, entrenador_id = ? WHERE ID_equipo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, pais);
            pstmt.setString(3, fundacion);
            pstmt.setString(4, estadio);
            pstmt.setInt(5, entrenadorId);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            System.out.println("Equipo modificado correctamente");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Muestra la clasificacion
     * 
     * @param scanner Un objeto {@link Scanner} para la entrada de datos del usuario.
     */
    public static void verClasificacion(Scanner scanner) {
        System.out.println("Ingrese el nombre del torneo que desea ver su clasificación:");
        String nombreTorneo = scanner.nextLine();

        int idTorneo = obtenerIdTorneoPorNombre(nombreTorneo);
        if (idTorneo == -1) {
            System.out.println("Torneo no encontrado.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT equipos.nombre AS Equipo, clasificacion.posicion AS Posicion, clasificacion.puntos AS Puntos, clasificacion.partidos_jugados AS PJ, clasificacion.partidos_ganados AS PG, clasificacion.partidos_empatados AS PE, clasificacion.partidos_perdidos AS PP FROM Participacion_Y_Clasificacion clasificacion INNER JOIN Equipos equipos ON clasificacion.equipo_id = equipos.ID_equipo WHERE clasificacion.torneo_id = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, idTorneo);
                ResultSet resultSet = statement.executeQuery();

                System.out.println("Pos | Equipo | Puntos | PJ | PG | PE | PP");
                System.out.println("------------------------------------------");

                while (resultSet.next()) {
                    String equipo = resultSet.getString("Equipo");
                    int posicion = resultSet.getInt("Posicion");
                    int puntos = resultSet.getInt("Puntos");
                    int pj = resultSet.getInt("PJ");
                    int pg = resultSet.getInt("PG");
                    int pe = resultSet.getInt("PE");
                    int pp = resultSet.getInt("PP");

                    System.out.println(posicion + " | " + equipo + " | " + puntos + " | " + pj + " | " + pg + " | " + pe
                            + " | " + pp);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    /**
     * Obtiene el ID del torneo por su nombre.
     * 
     * @param nombre Nombre del torneo
     * @return El ID del torneo o -1 si no se encuentra
     */
    private static int obtenerIdTorneoPorNombre(String nombre) {
        String sql = "SELECT ID_torneo FROM Torneos WHERE nombre = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID_torneo");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1; // Retorna -1 si el torneo no fue encontrado
    }

    /**
     * Muestra la plantilla ordenada de un equipo.
     * 
     * @param scanner El escaner para la entrada del usuario
     */
    public static void verPlantillaOrdenada(Scanner scanner) {
        System.out.println("Ingrese el nombre del equipo:");
        String nombreEquipo = scanner.nextLine();

        System.out.println("Seleccione el criterio de ordenamiento:");
        System.out.println("1. Por fecha de nacimiento");
        System.out.println("2. Por nombre");
        System.out.println("3. Por posición");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        Comparator<Jugador> comparator = null;

        switch (opcion) {
            case 1:
                comparator = Comparator.comparing(Jugador::getFechaNacimiento);
                break;
            case 2:
                comparator = Comparator.comparing(Jugador::getNombre);
                break;
            case 3:
                comparator = Comparator.comparing(Jugador::getPosicion);
                break;
            default:
                System.out.println("Opción no válida");
                return;
        }

        int equipoId = obtenerIdEquipoPorNombre(nombreEquipo);
        if (equipoId == -1) {
            System.out.println("El equipo especificado no existe.");
            return;
        }

        String sql = "SELECT nombre, nacionalidad, fecha_nacimiento, posicion " +
                "FROM Jugadores " +
                "WHERE equipo_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, equipoId);
            ResultSet resultSet = statement.executeQuery();

            List<Jugador> jugadores = new ArrayList<>();
            while (resultSet.next()) {
                Jugador jugador = new Jugador(
                        resultSet.getString("nombre"),
                        resultSet.getString("nacionalidad"),
                        resultSet.getString("fecha_nacimiento"),
                        resultSet.getString("posicion"),
                        nombreEquipo);
                jugadores.add(jugador);
            }

            jugadores.stream()
                    .sorted(comparator)
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Error al obtener la plantilla del equipo: " + e.getMessage());
        }

        System.out.println("Presione Enter para volver al menú principal...");
        scanner.nextLine();
    }

    /**
     * Obtiene la lista de jugadores por el ID del equipo.
     * 
     * @param equipoId ID del equipo
     * @return Lista de jugadores del equipo
     */
    private static List<Jugador> obtenerJugadoresPorEquipo(int equipoId) {
        List<Jugador> jugadores = new ArrayList<>();
        String sql = "SELECT nombre, nacionalidad, fecha_nacimiento, posicion FROM Jugadores WHERE equipo_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, equipoId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Jugador jugador = new Jugador(
                        rs.getString("nombre"),
                        rs.getString("nacionalidad"),
                        rs.getString("fecha_nacimiento"),
                        rs.getString("posicion"),
                        null // El nombre del equipo no es necesario aquí
                );
                jugadores.add(jugador);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jugadores;
    }

    /**
     * Crea un nuevo torneo en la base de datos.
     * 
     * @param scanner El escaner para la entrada del usuario
     */
    public static void crearTorneo(Scanner scanner) {
        System.out.println("Ingrese el nombre del torneo:");
        String nombreTorneo = scanner.nextLine();

        System.out.println("Ingrese la descripción del torneo:");
        String descripcionTorneo = scanner.nextLine();

        System.out.println("Ingrese la fecha de inicio del torneo (YYYY-MM-DD):");
        String fechaInicio = scanner.nextLine();

        System.out.println("Ingrese la fecha de fin del torneo (YYYY-MM-DD):");
        String fechaFin = scanner.nextLine();

        String sql = "INSERT INTO Torneos (nombre, descripcion, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, nombreTorneo);
            pstmt.setString(2, descripcionTorneo);
            pstmt.setString(3, fechaInicio);
            pstmt.setString(4, fechaFin);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int torneoId = rs.getInt(1);
                System.out.println("Torneo creado con ID: " + torneoId);
                agregarEquiposATorneo(scanner, torneoId);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Agrega equipos a un torneo en la base de datos.
     * 
     * @param scanner  El escaner para la entrada del usuario
     * @param torneoId El ID del torneo
     */
    public static void agregarEquiposATorneo(Scanner scanner, int torneoId) {
        List<Integer> equipoIds = new ArrayList<>();
        while (true) {
            System.out.println("Ingrese el nombre del equipo que desea agregar al torneo (o 'fin' para terminar):");
            String nombreEquipo = scanner.nextLine();
            if (nombreEquipo.equalsIgnoreCase("fin")) {
                break;
            }

            int equipoId = obtenerIdEquipoPorNombre(nombreEquipo);
            if (equipoId == -1) {
                System.out.println("Equipo no encontrado. Por favor, inténtelo de nuevo.");
            } else {
                equipoIds.add(equipoId);
                System.out.println("Equipo añadido al torneo.");
            }
        }

        if (equipoIds.isEmpty()) {
            System.out.println("No se han añadido equipos al torneo.");
            return;
        }

        generarClasificacionAleatoria(torneoId, equipoIds);
    }

    /**
     * Genera una clasificacion aleatoria para un torneo y la guarda en la base de
     * datos.
     * 
     * @param torneoId  El ID del torneo
     * @param equipoIds La lista de IDs de los equipos
     */
    public static void generarClasificacionAleatoria(int torneoId, List<Integer> equipoIds) {
        Random rand = new Random();
        List<Clasificacion> clasificaciones = new ArrayList<>();

        for (int i = 0; i < equipoIds.size(); i++) {
            int equipoId = equipoIds.get(i);
            int puntos = rand.nextInt(100); // Puntos aleatorios
            int partidosJugados = 38; // Suponemos que se jugaron 38 partidos
            int partidosGanados = rand.nextInt(partidosJugados);
            int partidosEmpatados = rand.nextInt(partidosJugados - partidosGanados);
            int partidosPerdidos = partidosJugados - partidosGanados - partidosEmpatados;

            clasificaciones.add(new Clasificacion(equipoId, torneoId, i + 1, puntos, partidosJugados, partidosGanados,
                    partidosEmpatados, partidosPerdidos));
        }

        clasificaciones.sort(Comparator.comparingInt(Clasificacion::getPuntos).reversed());

        String sql = "INSERT INTO Participacion_Y_Clasificacion (equipo_id, torneo_id, posicion, puntos, partidos_jugados, partidos_ganados, partidos_empatados, partidos_perdidos) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < clasificaciones.size(); i++) {
                Clasificacion clasificacion = clasificaciones.get(i);
                pstmt.setInt(1, clasificacion.getEquipoId());
                pstmt.setInt(2, clasificacion.getTorneoId());
                pstmt.setInt(3, i + 1); // Posición en la clasificación
                pstmt.setInt(4, clasificacion.getPuntos());
                pstmt.setInt(5, clasificacion.getPartidosJugados());
                pstmt.setInt(6, clasificacion.getPartidosGanados());
                pstmt.setInt(7, clasificacion.getPartidosEmpatados());
                pstmt.setInt(8, clasificacion.getPartidosPerdidos());
                pstmt.executeUpdate();
            }
            System.out.println("Clasificación generada aleatoriamente y guardada en la base de datos.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
