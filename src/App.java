import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al programa de gestión de fútbol");
        System.out.println("Por favor, seleccione una opción:");
        System.out.println("1. Buscar");
        System.out.println("2. Añadir");
        System.out.println("3. Borrar");
        System.out.println("4. Modificar");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

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
                // modificarEnBaseDeDatos(scanner);
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }

        scanner.close();
    }

    public static void buscarEnBaseDeDatos(Scanner scanner) {
        System.out.println("¿Qué desea buscar?");
        System.out.println("1. Jugador");
        System.out.println("2. Entrenador");
        System.out.println("3. Equipo");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

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
            if (tabla.equals("Jugadores")) { // Consulta para que me muestre el nombre del equipo al que pertenece
                sql = "SELECT j.*, e.nombre AS nombre_equipo " +
                        "FROM Jugadores j " +
                        "JOIN Equipos e ON j.equipo_id = e.ID_equipo " +
                        "WHERE j." + columna + " LIKE ?";
            } else if (tabla.equals("Entrenadores")) { // Consulta para que me muestre el nombre del equipo al que
                                                       // pertenece
                sql = "SELECT e.*, t.nombre AS nombre_equipo " +
                        "FROM Entrenadores e " +
                        "LEFT JOIN Equipos t ON e.ID_entrenador = t.entrenador_id " +
                        "WHERE e." + columna + " LIKE ?";
            } else if (tabla.equals("Equipos")) { // Consulta para que me muestre el nombre del entrenador y la
                                                  // plantilla
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

            while (resultSet.next()) {
                switch (tabla) {
                    case "Jugadores":
                        String nombreJugador = resultSet.getString("nombre");
                        String nacionalidadJugador = resultSet.getString("nacionalidad");
                        String fechaNacimientoJugador = resultSet.getString("fecha_nacimiento");
                        String posicionJugador = resultSet.getString("posicion");
                        String nombreEquipoJugador = resultSet.getString("nombre_equipo");

                        Jugador jugador = new Jugador(nombreJugador, nacionalidadJugador, fechaNacimientoJugador,
                                posicionJugador, nombreEquipoJugador);
                        System.out.println("Jugador: " + jugador.toString());
                        break;
                    case "Entrenadores":
                        String nombreEntrenador = resultSet.getString("nombre");
                        String nacionalidadEntrenador = resultSet.getString("nacionalidad");
                        String fechaNacimientoEntrenador = resultSet.getString("fecha_nacimiento");
                        String nombreEquipoEntrenador = resultSet.getString("nombre_equipo");

                        Entrenador entrenador = new Entrenador(nombreEntrenador, nacionalidadEntrenador,
                                fechaNacimientoEntrenador, nombreEquipoEntrenador);
                        System.out.println("Entrenador: " + entrenador.toString());
                        break;
                    case "Equipos":
                        String nombreEquipo = resultSet.getString("nombre");
                        String paisEquipo = resultSet.getString("pais");
                        String fundacionEquipo = resultSet.getString("fundacion");
                        String estadioEquipo = resultSet.getString("estadio");
                        String nombreEntrenadorEquipo = resultSet.getString("nombre_entrenador");
                        String plantilla = resultSet.getString("plantilla");

                        Equipo equipo = new Equipo(nombreEquipo, paisEquipo, fundacionEquipo, estadioEquipo,
                                nombreEntrenadorEquipo, plantilla);
                        equipo.setPlantilla(Arrays.asList(plantilla.split(", ")));
                        System.out.println("Equipo: " + equipo.toString());
                        break;
                    default:
                        System.out.println("Error. Introduzca la elección correctamente.");
                        break;
                }
            }

            conn.close();
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
        scanner.nextLine(); // Limpiar el buffer

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
                            statement = conn.prepareStatement(
                                    "INSERT INTO Jugadores (nombre, nacionalidad, fecha_nacimiento, posicion, equipo_id) VALUES (?, ?, ?, ?, ?)");
                            statement.setString(1, nombreJugador);
                            statement.setString(2, nacionalidadJugador);
                            statement.setString(3, fechaNacimientoJugador);
                            statement.setString(4, posicionJugador);
                            statement.setInt(5, equipoIdJugador);
                        } else {
                            System.out.println("El equipo especificado no existe.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
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
                            statement = conn.prepareStatement(
                                    "INSERT INTO Entrenadores (nombre, nacionalidad, fecha_nacimiento, equipo_id) VALUES (?, ?, ?, ?)");
                            statement.setString(1, nombreEntrenador);
                            statement.setString(2, nacionalidadEntrenador);
                            statement.setString(3, fechaNacimientoEntrenador);
                            statement.setInt(4, equipoIdEntrenador);
                        } else {
                            System.out.println("El equipo especificado no existe.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
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
                    System.out.println("¿El equipo tiene entrenador? (Sí/No):");
                    String tieneEntrenador = scanner.nextLine();

                    int entrenadorIdEquipo = 0;

                    if (tieneEntrenador.equalsIgnoreCase("Sí")) {
                        // Registro del entrenador
                        System.out.println("Registro del entrenador:");
                        System.out.println("Ingrese el nombre del entrenador:");
                        String nombreEntrenador2 = scanner.nextLine();
                        System.out.println("Ingrese la nacionalidad del entrenador:");
                        String nacionalidadEntrenador2 = scanner.nextLine();
                        System.out.println("Ingrese la fecha de nacimiento del entrenador (YYYY-MM-DD):");
                        String fechaNacimientoEntrenador2 = scanner.nextLine();
                        System.out.println("Ingrese el nombre del equipo al que pertenece el entrenador:");
                        String equipoNombreEntrenador2 = scanner.nextLine();

                        try {
                            int equipoIdEntrenador = obtenerIdEquipo(equipoNombreEntrenador2, conn);
                            if (equipoIdEntrenador != 0) {
                                statement = conn.prepareStatement(
                                        "INSERT INTO Entrenadores (nombre, nacionalidad, fecha_nacimiento, equipo_id) VALUES (?, ?, ?, ?)");
                                statement.setString(1, nombreEntrenador2);
                                statement.setString(2, nacionalidadEntrenador2);
                                statement.setString(3, fechaNacimientoEntrenador2);
                                statement.setInt(4, equipoIdEntrenador);
                            } else {
                                System.out.println("El equipo especificado no existe.");
                                return; // Terminamos la ejecución del método si el equipo no existe
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return;
                        }
                    }

                    try {
                        if (entrenadorIdEquipo != -1) {
                            statement = conn.prepareStatement(
                                    "INSERT INTO Equipos (nombre, pais, fundacion, estadio, entrenador_id) VALUES (?, ?, ?, ?, ?)");
                            statement.setString(1, nombreEquipo);
                            statement.setString(2, paisEquipo);
                            statement.setString(3, fundacionEquipo);
                            statement.setString(4, estadioEquipo);
                            statement.setInt(5, entrenadorIdEquipo);
                        } else {
                            statement = conn.prepareStatement(
                                    "INSERT INTO Equipos (nombre, pais, fundacion, estadio) VALUES (?, ?, ?, ?)");
                            statement.setString(1, nombreEquipo);
                            statement.setString(2, paisEquipo);
                            statement.setString(3, fundacionEquipo);
                            statement.setString(4, estadioEquipo);
                        }

                        int filasInsertadas = statement.executeUpdate();
                        if (filasInsertadas > 0) {
                            System.out.println("Registro del equipo agregado exitosamente.");
                        } else {
                            System.out.println("No se pudo agregar el registro del equipo.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
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

    public static void borrarEnBasedeDatos(Scanner scanner) {
        System.out.println("¿Qué desea borrar?");
        System.out.println("1. Jugador");
        System.out.println("2. Entrenador");
        System.out.println("3. Equipo");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Base_De_Datos_APP", "root",
                    "123456789");
            PreparedStatement statement = null;
            int filasBorradasEquipo = 0; // Variable para almacenar el número de filas eliminadas de la tabla Equipos

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nombre del jugador que desea borrar:");
                    String nombreJugador = scanner.nextLine();

                    statement = conn.prepareStatement("DELETE FROM Jugadores WHERE nombre = ?");
                    statement.setString(1, nombreJugador);
                    break;
                case 2:
                    System.out.println("Ingrese el nombre del entrenador que desea borrar:");
                    String nombreEntrenador = scanner.nextLine();

                    statement = conn.prepareStatement("DELETE FROM Entrenadores WHERE nombre = ?");
                    statement.setString(1, nombreEntrenador);
                    break;
                case 3:
                    System.out.println("Ingrese el nombre del equipo que desea borrar:");
                    String nombreEquipo = scanner.nextLine();

                    System.out.println("¿Desea borrar también el entrenador y la plantilla del equipo? (Sí/No):");
                    String borrarEntrenadorYPlantilla = scanner.nextLine();

                    // Primero, eliminamos el equipo
                    statement = conn.prepareStatement("DELETE FROM Equipos WHERE nombre = ?");
                    statement.setString(1, nombreEquipo);
                    filasBorradasEquipo = statement.executeUpdate(); // Actualizamos la variable con el número de filas
                                                                     // eliminadas

                    // Si se confirma el borrado del entrenador y la plantilla, procedemos a
                    // eliminarlos
                    if (borrarEntrenadorYPlantilla.equalsIgnoreCase("Sí")) {
                        // Resto del código para borrar entrenador y plantilla
                    }
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }

            if (statement != null) {
                int filasBorradas = statement.executeUpdate();
                if (filasBorradas > 0) {
                    System.out.println("Registro(s) borrado(s) exitosamente.");
                } else {
                    System.out.println("No se pudo borrar el registro.");
                }
            }

            // Verificar si se han borrado filas de la tabla Equipos
            if (filasBorradasEquipo > 0) {
                System.out.println("Se han borrado " + filasBorradasEquipo + " fila(s) de la tabla Equipos.");
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al borrar en la base de datos: " + e.getMessage());
        }
    }

}
