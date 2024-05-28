import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class App {
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
            System.out.println("5. Gestionar torneos");
            System.out.println("6. Mostrar plantillas ordenadas");
            System.out.println("7. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    buscarEnBaseDeDatos(scanner);
                    break;
                case 2:
                    añadirEnBasedeDatos(scanner);
                    break;
                case 3:
                    borrarEnBasedeDatos(scanner);
                    break;
                case 4:
                    modificarEnBaseDeDatos(scanner);
                    break;
                case 5:
                    verClasificación(scanner);
                    break;
                case 6:
                    verPlantillaOrdenada(scanner);
                case 7:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }

        scanner.close();
    }

    public static void buscarEnBaseDeDatos(Scanner scanner) {
        System.out.println("¿Qué desea buscar?");
        System.out.println("1. Jugador");
        System.out.println("2. Entrenador");
        System.out.println("3. Equipo");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        String tabla = "";
        String columna = "";

        switch (opcion) {
            case 1:
                tabla = "Jugadores";
                columna = "nombre";
                break;
            case 2:
                tabla = "Entrenadores";
                columna = "nombre";
                break;
            case 3:
                tabla = "Equipos";
                columna = "nombre";
                break;
            default:
                System.out.println("Opción no válida");
                return;
        }

        System.out.println("Ingrese el nombre:");
        String valor = scanner.nextLine();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Base_De_Datos_APP", "root",
                    "123456789");
            String sql = "";
            if (tabla.equals("Jugadores")) {
                sql = "SELECT j.*, e.nombre AS nombre_equipo " +
                        "FROM Jugadores j " +
                        "JOIN Equipos e ON j.equipo_id = e.ID_equipo " +
                        "WHERE j." + columna + " LIKE ?";
            } else if (tabla.equals("Entrenadores")) {
                sql = "SELECT e.*, t.nombre AS nombre_equipo " +
                        "FROM Entrenadores e " +
                        "LEFT JOIN Equipos t ON e.ID_entrenador = t.entrenador_id " +
                        "WHERE e." + columna + " LIKE ?";
            } else if (tabla.equals("Equipos")) {
                sql = "SELECT e.*, t.nombre AS nombre_entrenador, " +
                        "GROUP_CONCAT(j.nombre SEPARATOR ', ') AS plantilla " +
                        "FROM Equipos e " +
                        "LEFT JOIN Entrenadores t ON e.entrenador_id = t.ID_entrenador " +
                        "LEFT JOIN Jugadores j ON e.ID_equipo = j.equipo_id " +
                        "WHERE e." + columna + " LIKE ? " +
                        "GROUP BY e.ID_equipo";
            } else {
                System.out.println("Error. Introduzca la elección correctamente.");
                return;
            }

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "%" + valor + "%");
            ResultSet resultSet = statement.executeQuery();

            ArrayList<Object> resultados = new ArrayList<>();

            while (resultSet.next()) {
                switch (tabla) {
                    case "Jugadores":
                        Jugador jugador = new Jugador(resultSet.getString("nombre"),
                                resultSet.getString("nacionalidad"),
                                resultSet.getString("fecha_nacimiento"), resultSet.getString("posicion"),
                                resultSet.getString("nombre_equipo"));
                        resultados.add(jugador);
                        break;
                    case "Entrenadores":
                        Entrenador entrenador = new Entrenador(
                                resultSet.getString("nombre"), resultSet.getString("nacionalidad"),
                                resultSet.getString("fecha_nacimiento"), resultSet.getString("nombre_equipo"));
                        resultados.add(entrenador);
                        break;
                    case "Equipos":
                        Equipo equipo = new Equipo(
                                resultSet.getString("nombre"), resultSet.getString("pais"),
                                resultSet.getString("fundacion"), resultSet.getString("estadio"),
                                resultSet.getString("nombre_entrenador"));
                        equipo.setPlantilla(Arrays.asList(resultSet.getString("plantilla").split(", ")));
                        resultados.add(equipo);
                        break;
                    default:
                        System.out.println("Error. Introduzca la elección correctamente.");
                        break;
                }
            }
            for (Object resultado : resultados) {
                if (resultado instanceof Jugador) {
                    Jugador jugador = (Jugador) resultado;
                    System.out.println("Jugador: " + jugador);
                } else if (resultado instanceof Entrenador) {
                    Entrenador entrenador = (Entrenador) resultado;
                    System.out.println("Entrenador: " + entrenador);
                } else if (resultado instanceof Equipo) {
                    Equipo equipo = (Equipo) resultado;
                    System.out.println("Equipo: " + equipo);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar en la base de datos: " + e.getMessage());
        }
    }

    public static void añadirEnBasedeDatos(Scanner scanner) {
        System.out.println("¿Qué desea agregar?");
        System.out.println("1. Jugador");
        System.out.println("2. Entrenador");
        System.out.println("3. Equipo");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Base_De_Datos_APP", "root",
                    "123456789");
            PreparedStatement statement = null;

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nombre del jugador:");
                    String nombreJugador = scanner.nextLine();
                    System.out.println("Ingrese la nacionalidad del jugador:");
                    String nacionalidadJugador = scanner.nextLine();
                    System.out.println("Ingrese la fecha de nacimiento del jugador (YYYY-MM-DD):");
                    String fechaNacimientoJugador = scanner.nextLine();
                    System.out.println("Ingrese la posición del jugador:");
                    String posicionJugador = scanner.nextLine();
                    System.out.println("Ingrese el nombre del equipo al que pertenece el jugador:");
                    String equipoNombreJugador = scanner.nextLine();

                    try {
                        int equipoIdJugador = obtenerIdEquipo(equipoNombreJugador, conn);
                        if (equipoIdJugador != 0) {
                            Jugador jugador = new Jugador(nombreJugador, nacionalidadJugador, fechaNacimientoJugador,
                                    posicionJugador, equipoNombreJugador);
                            statement = conn.prepareStatement(
                                    "INSERT INTO Jugadores (nombre, nacionalidad, fecha_nacimiento, posicion, equipo_id) VALUES (?, ?, ?, ?, ?)");
                            statement.setString(1, jugador.getNombre());
                            statement.setString(2, jugador.getNacionalidad());
                            statement.setString(3, jugador.getFechaNacimiento());
                            statement.setString(4, jugador.getPosicion());
                            statement.setInt(5, equipoIdJugador);
                        } else {
                            System.out.println("El equipo especificado no existe.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al obtener ID del equipo: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Ingrese el nombre del entrenador:");
                    String nombreEntrenador = scanner.nextLine();
                    System.out.println("Ingrese la nacionalidad del entrenador:");
                    String nacionalidadEntrenador = scanner.nextLine();
                    System.out.println("Ingrese la fecha de nacimiento del entrenador (YYYY-MM-DD):");
                    String fechaNacimientoEntrenador = scanner.nextLine();
                    System.out.println("Ingrese el nombre del equipo al que pertenece el entrenador:");
                    String equipoNombreEntrenador = scanner.nextLine();

                    try {
                        int equipoIdEntrenador = obtenerIdEquipo(equipoNombreEntrenador, conn);
                        if (equipoIdEntrenador != 0) {
                            Entrenador entrenador = new Entrenador(nombreEntrenador, nacionalidadEntrenador,
                                    fechaNacimientoEntrenador, equipoNombreEntrenador);
                            statement = conn.prepareStatement(
                                    "INSERT INTO Entrenadores (nombre, nacionalidad, fecha_nacimiento, equipo_id) VALUES (?, ?, ?, ?)");
                            statement.setString(1, entrenador.getNombre());
                            statement.setString(2, entrenador.getNacionalidad());
                            statement.setString(3, entrenador.getFechaNacimiento());
                            statement.setInt(4, equipoIdEntrenador);
                        } else {
                            System.out.println("El equipo especificado no existe.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al obtener ID del equipo: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Ingrese el nombre del equipo:");
                    String nombreEquipo = scanner.nextLine();
                    System.out.println("Ingrese el país del equipo:");
                    String paisEquipo = scanner.nextLine();
                    System.out.println("Ingrese la fecha de fundación del equipo (YYYY-MM-DD):");
                    String fundacionEquipo = scanner.nextLine();
                    System.out.println("Ingrese el estadio del equipo:");
                    String estadioEquipo = scanner.nextLine();

                    statement = conn.prepareStatement(
                            "INSERT INTO Equipos (nombre, pais, fundacion, estadio) VALUES (?, ?, ?, ?)");
                    statement.setString(1, nombreEquipo);
                    statement.setString(2, paisEquipo);
                    statement.setString(3, fundacionEquipo);
                    statement.setString(4, estadioEquipo);
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }

            if (statement != null) {
                int filasInsertadas = statement.executeUpdate();
                if (filasInsertadas > 0) {
                    System.out.println("Registro agregado exitosamente.");
                } else {
                    System.out.println("No se pudo agregar el registro.");
                }
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al añadir en la base de datos: " + e.getMessage());
        }
    }

    private static int obtenerIdEquipo(String nombreEquipo, Connection conn) throws SQLException {
        int idEquipo = 0;

        String sql = "SELECT ID_equipo FROM Equipos WHERE nombre = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, nombreEquipo);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            idEquipo = resultSet.getInt("ID_equipo");
        }

        return idEquipo;
    }

    public static void modificarEnBaseDeDatos(Scanner scanner) {
        System.out.println("¿Qué desea modificar?");
        System.out.println("1. Jugador");
        System.out.println("2. Entrenador");
        System.out.println("3. Equipo");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Base_De_Datos_APP", "root",
                    "123456789");
            PreparedStatement statement = null;

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nombre del jugador que desea modificar:");
                    String nombreJugador = scanner.nextLine();
                    System.out.println("Ingrese la nueva nacionalidad del jugador:");
                    String nuevaNacionalidadJugador = scanner.nextLine();
                    System.out.println("Ingrese la nueva fecha de nacimiento del jugador (YYYY-MM-DD):");
                    String nuevaFechaNacimientoJugador = scanner.nextLine();
                    System.out.println("Ingrese la nueva posición del jugador:");
                    String nuevaPosicionJugador = scanner.nextLine();

                    statement = conn.prepareStatement(
                            "UPDATE Jugadores SET nacionalidad = ?, fecha_nacimiento = ?, posicion = ? WHERE nombre = ?");
                    statement.setString(1, nuevaNacionalidadJugador);
                    statement.setString(2, nuevaFechaNacimientoJugador);
                    statement.setString(3, nuevaPosicionJugador);
                    statement.setString(4, nombreJugador);
                    break;
                case 2:
                    System.out.println("Ingrese el nombre del entrenador que desea modificar:");
                    String nombreEntrenador = scanner.nextLine();
                    System.out.println("Ingrese la nueva nacionalidad del entrenador:");
                    String nuevaNacionalidadEntrenador = scanner.nextLine();
                    System.out.println("Ingrese la nueva fecha de nacimiento del entrenador (YYYY-MM-DD):");
                    String nuevaFechaNacimientoEntrenador = scanner.nextLine();

                    statement = conn.prepareStatement(
                            "UPDATE Entrenadores SET nacionalidad = ?, fecha_nacimiento = ? WHERE nombre = ?");
                    statement.setString(1, nuevaNacionalidadEntrenador);
                    statement.setString(2, nuevaFechaNacimientoEntrenador);
                    statement.setString(3, nombreEntrenador);
                    break;
                case 3:
                    System.out.println("Ingrese el nombre del equipo que desea modificar:");
                    String nombreEquipo = scanner.nextLine();
                    System.out.println("Ingrese el nuevo país del equipo:");
                    String nuevoPaisEquipo = scanner.nextLine();
                    System.out.println("Ingrese la nueva fecha de fundación del equipo (YYYY-MM-DD):");
                    String nuevaFundacionEquipo = scanner.nextLine();
                    System.out.println("Ingrese el nuevo estadio del equipo:");
                    String nuevoEstadioEquipo = scanner.nextLine();

                    statement = conn.prepareStatement(
                            "UPDATE Equipos SET pais = ?, fundacion = ?, estadio = ? WHERE nombre = ?");
                    statement.setString(1, nuevoPaisEquipo);
                    statement.setString(2, nuevaFundacionEquipo);
                    statement.setString(3, nuevoEstadioEquipo);
                    statement.setString(4, nombreEquipo);
                    break;
                default:
                    System.out.println("Opción no válida");
                    return;
            }

            int filasActualizadas = statement.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Registro modificado exitosamente.");
            } else {
                System.out.println("No se pudo modificar el registro.");
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al modificar en la base de datos: " + e.getMessage());
        }
    }

    public static void borrarEnBasedeDatos(Scanner scanner) {
        System.out.println("¿Qué desea borrar?");
        System.out.println("1. Jugador");
        System.out.println("2. Entrenador");
        System.out.println("3. Equipo");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Base_De_Datos_APP", "root",
                    "123456789");
            PreparedStatement statement = null;
            String nombre = "";

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nombre del jugador que desea borrar:");
                    nombre = scanner.nextLine();
                    statement = conn.prepareStatement("DELETE FROM Jugadores WHERE nombre = ?");
                    break;
                case 2:
                    System.out.println("Ingrese el nombre del entrenador que desea borrar:");
                    nombre = scanner.nextLine();
                    statement = conn.prepareStatement("DELETE FROM Entrenadores WHERE nombre = ?");
                    break;
                case 3:
                    System.out.println("Ingrese el nombre del equipo que desea borrar:");
                    nombre = scanner.nextLine();
                    statement = conn.prepareStatement("DELETE FROM Equipos WHERE nombre = ?");
                    break;
                default:
                    System.out.println("Opción no válida");
                    return;
            }

            if (statement != null) {
                statement.setString(1, nombre);
                int filasBorradas = statement.executeUpdate();
                if (filasBorradas > 0) {
                    System.out.println("Registro borrado exitosamente.");
                } else {
                    System.out.println("No se pudo borrar el registro.");
                }
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al borrar en la base de datos: " + e.getMessage());
        }
    }

    public static void verClasificación(Scanner scanner) {
        System.out.println("Ingrese el ID del torneo que desea ver su clasificación:");
        int idTorneo = scanner.nextInt();
        scanner.nextLine();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Base_De_Datos_APP", "root",
                "123456789")) {
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

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Base_De_Datos_APP", "root", "123456789")) {
            int equipoId = obtenerIdEquipo(nombreEquipo, conn);
            if (equipoId == 0) {
                System.out.println("El equipo especificado no existe.");
                return;
            }

            String sql = "SELECT nombre, nacionalidad, fecha_nacimiento, posicion " +
                         "FROM Jugadores " +
                         "WHERE equipo_id = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, equipoId);
                ResultSet resultSet = statement.executeQuery();

                List<Jugador> jugadores = new ArrayList<>();
                while (resultSet.next()) {
                    Jugador jugador = new Jugador(
                        resultSet.getString("nombre"),
                        resultSet.getString("nacionalidad"),
                        resultSet.getString("fecha_nacimiento"),
                        resultSet.getString("posicion"),
                        nombreEquipo
                    );
                    jugadores.add(jugador);
                }

                jugadores.stream()
                        .sorted(comparator)
                        .forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la plantilla del equipo: " + e.getMessage());
        }
        
        System.out.println("Presione Enter para volver al menú principal...");
        scanner.nextLine();
    }

}