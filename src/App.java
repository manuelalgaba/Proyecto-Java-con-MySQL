import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        switch (opcion) {
            case 1:
                buscarEnBaseDeDatos(scanner);
                break;
            case 2:
                añadirEnBaseDeDatos(scanner);
                break;
            case 3:
                borrarEnBaseDeDatos(scanner);
                break;
            case 4:
                modificarEnBaseDeDatos(scanner);
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

        switch (opcion) {
            // Código para mostrar un Jugador.
            case 1:
                System.out.println("Ingrese el nombre del jugador:");
                String nombreJugador = scanner.nextLine();
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Base_De_Datos_APP",
                            "root", "123456789");
                    String sql = "SELECT * FROM Jugadores WHERE nombre LIKE ?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, "%" + nombreJugador + "%");
                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        String nombre = resultSet.getString("nombre");
                        String nacionalidad = resultSet.getString("nacionalidad");
                        String fechaNacimiento = resultSet.getString("fecha_nacimiento");
                        String posicion = resultSet.getString("posicion");
                        System.out.println("Nombre: " + nombre + ", Nacionalidad: " + nacionalidad
                                + ", Fecha de nacimiento: " + fechaNacimiento + ", Posición: " + posicion);
                    }

                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            // Código para mostrar un Entrenador.
            case 2:
                System.out.println("Ingrese el nombre del entrenador:");
                String nombreEntrenador = scanner.nextLine();
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Base_De_Datos_APP",
                            "root", "123456789");
                    String sql = "SELECT * FROM Entrenadores WHERE nombre LIKE ?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, "%" + nombreEntrenador + "%");
                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        String nombre = resultSet.getString("nombre");
                        String nacionalidad = resultSet.getString("nacionalidad");
                        String fechaNacimiento = resultSet.getString("fecha_nacimiento");
                        System.out.println("Nombre: " + nombre + ", Nacionalidad: " + nacionalidad
                                + ", Fecha de nacimiento: " + fechaNacimiento);
                    }

                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            // Código para mostrar un Equipo.
            case 3:
                System.out.println("Ingrese el nombre del equipo:");
                String nombreEquipo = scanner.nextLine();
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Base_De_Datos_APP",
                            "root", "123456789");
                    String sql = "SELECT * FROM Equipos WHERE nombre LIKE ?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, "%" + nombreEquipo + "%");
                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        String nombre = resultSet.getString("nombre");
                        String pais = resultSet.getString("pais");
                        String fundacion = resultSet.getString("fundacion");
                        String estadio = resultSet.getString("estadio");
                        System.out.println("Nombre: " + nombre + ", País: " + pais + ", Fundación: " + fundacion
                                + ", Estadio: " + estadio);
                    }

                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
    }

    public static void añadirEnBaseDeDatos(Scanner scanner) {
        System.out.println("¿Qué desea añadir?");
        System.out.println("1. Jugador");
        System.out.println("2. Entrenador");
        System.out.println("3. Equipo");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        switch (opcion) {
            // Código para añadir un Jugador.
            case 1:
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Base_De_Datos_APP",
                            "root", "123456789");

                    System.out.println("Ingrese el nombre del jugador:");
                    String nombreJugador = scanner.nextLine();
                    System.out.println("Ingrese la nacionalidad del jugador:");
                    String nacionalidad = scanner.nextLine();
                    System.out.println("Ingrese la fecha de nacimiento del jugador (YYYY-MM-DD):");
                    String fechaNacimiento = scanner.nextLine();
                    System.out.println("Ingrese la posición del jugador:");
                    String posicion = scanner.nextLine();

                    String sql = "INSERT INTO Jugadores (nombre, nacionalidad, fecha_nacimiento, posicion) VALUES (?, ?, ?, ?)";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, nombreJugador);
                    statement.setString(2, nacionalidad);
                    statement.setString(3, fechaNacimiento);
                    statement.setString(4, posicion);

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Jugador agregado correctamente");
                    } else {
                        System.out.println("No se pudo agregar el jugador");
                    }
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            // Código para añadir un Entrenador.
            case 2:
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Base_De_Datos_APP",
                            "root", "123456789");
                    System.out.println("Ingrese el nombre del entrenador:");
                    String nombreEntrenador = scanner.nextLine();
                    System.out.println("Ingrese la nacionalidad del entrenador:");
                    String nacionalidadEntrenador = scanner.nextLine();
                    System.out.println("Ingrese la fecha de nacimiento del entrenador (YYYY-MM-DD):");
                    String fechaNacimientoEntrenador = scanner.nextLine();

                    String sqlEntrenador = "INSERT INTO Entrenadores (nombre, nacionalidad, fecha_nacimiento) VALUES (?, ?, ?)";
                    PreparedStatement statementEntrenador = conn.prepareStatement(sqlEntrenador);
                    statementEntrenador.setString(1, nombreEntrenador);
                    statementEntrenador.setString(2, nacionalidadEntrenador);
                    statementEntrenador.setString(3, fechaNacimientoEntrenador);

                    int rowsInsertedEntrenador = statementEntrenador.executeUpdate();
                    if (rowsInsertedEntrenador > 0) {
                        System.out.println("Entrenador agregado correctamente");
                    } else {
                        System.out.println("No se pudo agregar el entrenador");
                    }
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            // Código para añadir un Equipo.
            case 3:
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Base_De_Datos_APP",
                            "root", "123456789");

                    System.out.println("Ingrese el nombre del equipo:");
                    String nombreEquipo = scanner.nextLine();
                    System.out.println("Ingrese el país del equipo:");
                    String paisEquipo = scanner.nextLine();
                    System.out.println("Ingrese la fecha de fundación del equipo (YYYY-MM-DD):");
                    String fundacionEquipo = scanner.nextLine();
                    System.out.println("Ingrese el estadio del equipo:");
                    String estadioEquipo = scanner.nextLine();

                    String sqlEquipo = "INSERT INTO Equipos (nombre, pais, fundacion, estadio) VALUES (?, ?, ?, ?)";
                    PreparedStatement statementEquipo = conn.prepareStatement(sqlEquipo);
                    statementEquipo.setString(1, nombreEquipo);
                    statementEquipo.setString(2, paisEquipo);
                    statementEquipo.setString(3, fundacionEquipo);
                    statementEquipo.setString(4, estadioEquipo);

                    int rowsInsertedEquipo = statementEquipo.executeUpdate();
                    if (rowsInsertedEquipo > 0) {
                        System.out.println("Equipo agregado correctamente");
                    } else {
                        System.out.println("No se pudo agregar el equipo");
                    }
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
    }

    public static void borrarEnBaseDeDatos(Scanner scanner) {
        System.out.println("¿Qué desea borrar?");
        System.out.println("1. Jugador");
        System.out.println("2. Entrenador");
        System.out.println("3. Equipo");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Base_De_Datos_APP", "root",
                    "123456789");

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nombre del jugador que desea borrar:");
                    String nombreJugador = scanner.nextLine();
                    scanner.nextLine(); // Limpiar el buffer

                    String sqlJugador = "DELETE FROM Jugadores WHERE nombre = ?";
                    PreparedStatement statementJugador = conn.prepareStatement(sqlJugador);
                    statementJugador.setString(1, nombreJugador);

                    int rowsDeletedJugador = statementJugador.executeUpdate();
                    if (rowsDeletedJugador > 0) {
                        System.out.println("Jugador borrado correctamente");
                    } else {
                        System.out.println("No se encontró el jugador con el ID especificado");
                    }
                    break;

                case 2:
                    System.out.println("Ingrese el nombre del entrenador que desea borrar:");
                    String nombreEntrenador = scanner.nextLine();
                    scanner.nextLine(); // Limpiar el buffer

                    String sqlEntrenador = "DELETE FROM Entrenadores WHERE nombre = ?";
                    PreparedStatement statementEntrenador = conn.prepareStatement(sqlEntrenador);
                    statementEntrenador.setString(1, nombreEntrenador);

                    int rowsDeletedEntrenador = statementEntrenador.executeUpdate();
                    if (rowsDeletedEntrenador > 0) {
                        System.out.println("Entrenador borrado correctamente");
                    } else {
                        System.out.println("No se encontró el entrenador con el ID especificado");
                    }
                    break;

                case 3:
                    System.out.println("Ingrese el nombre del equipo que desea borrar:");
                    String nombreEquipo = scanner.nextLine();
                    scanner.nextLine(); // Limpiar el buffer

                    String sqlEquipo = "DELETE FROM Equipos WHERE nombre = ?";
                    PreparedStatement statementEquipo = conn.prepareStatement(sqlEquipo);
                    statementEquipo.setString(1, nombreEquipo);

                    int rowsDeletedEquipo = statementEquipo.executeUpdate();
                    if (rowsDeletedEquipo > 0) {
                        System.out.println("Equipo borrado correctamente");
                    } else {
                        System.out.println("No se encontró el equipo con el ID especificado");
                    }
                    break;

                default:
                    System.out.println("Opción no válida");
                    break;
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void modificarEnBaseDeDatos(Scanner scanner) {
        System.out.println("¿Qué desea modificar?");
        System.out.println("1. Jugador");
        System.out.println("2. Entrenador");
        System.out.println("3. Equipo");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Base_De_Datos_APP", "root",
                    "123456789");

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nombre del jugador que desea modificar:");
                    String nombreJugador = scanner.nextLine();
                    scanner.nextLine(); // Limpiar el buffer

                    System.out.println("¿Qué atributo desea modificar?");
                    System.out.println("1. Nombre");
                    System.out.println("2. Nacionalidad");
                    System.out.println("3. Fecha de nacimiento");
                    System.out.println("4. Posición");

                    int opcionAtributoJugador = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer

                    String nombreColumnaJugador = "";
                    String nuevoValorJugador = "";

                    switch (opcionAtributoJugador) {
                        case 1:
                            nombreColumnaJugador = "nombre";
                            System.out.println("Ingrese el nuevo nombre del jugador:");
                            nuevoValorJugador = scanner.nextLine();
                            break;
                        case 2:
                            nombreColumnaJugador = "nacionalidad";
                            System.out.println("Ingrese la nueva nacionalidad del jugador:");
                            nuevoValorJugador = scanner.nextLine();
                            break;
                        case 3:
                            nombreColumnaJugador = "fecha_nacimiento";
                            System.out.println("Ingrese la nueva fecha de nacimiento del jugador (YYYY-MM-DD):");
                            nuevoValorJugador = scanner.nextLine();
                            break;
                        case 4:
                            nombreColumnaJugador = "posicion";
                            System.out.println("Ingrese la nueva posición del jugador:");
                            nuevoValorJugador = scanner.nextLine();
                            break;
                        default:
                            System.out.println("Opción no válida");
                            conn.close();
                            return;
                    }

                    String sqlJugador = "UPDATE Jugadores SET " + nombreColumnaJugador + " = ? WHERE nombre = ?";
                    PreparedStatement statementJugador = conn.prepareStatement(sqlJugador);
                    statementJugador.setString(1, nuevoValorJugador);
                    statementJugador.setString(2, nombreJugador);

                    int rowsUpdatedJugador = statementJugador.executeUpdate();
                    if (rowsUpdatedJugador > 0) {
                        System.out.println("Jugador modificado correctamente");
                    } else {
                        System.out.println("No se encontró el jugador con el ID especificado");
                    }
                    break;

                case 2:
                    System.out.println("Ingrese el nombre del Entrenador que desea modificar:");
                    String nombreEntrenador = scanner.nextLine();
                    scanner.nextLine(); // Limpiar el buffer

                    System.out.println("¿Qué atributo desea modificar?");
                    System.out.println("1. Nombre");
                    System.out.println("2. Nacionalidad");
                    System.out.println("3. Fecha de nacimiento");

                    int opcionAtributoEntrenador = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer

                    String nombreColumnaEntrenador = "";
                    String nuevoValorEntrenador = "";

                    switch (opcionAtributoEntrenador) {
                        case 1:
                            nombreColumnaEntrenador = "nombre";
                            System.out.println("Ingrese el nuevo nombre del jugador:");
                            nuevoValorEntrenador = scanner.nextLine();
                            break;
                        case 2:
                            nombreColumnaEntrenador = "nacionalidad";
                            System.out.println("Ingrese la nueva nacionalidad del jugador:");
                            nuevoValorEntrenador = scanner.nextLine();
                            break;
                        case 3:
                            nombreColumnaEntrenador = "fecha_nacimiento";
                            System.out.println("Ingrese la nueva fecha de nacimiento del jugador (YYYY-MM-DD):");
                            nuevoValorEntrenador = scanner.nextLine();
                            break;
                        default:
                            System.out.println("Opción no válida");
                            conn.close();
                            return;
                    }
                    String sqlEntrenador = "UPDATE Entrenadores SET " + nombreColumnaEntrenador
                            + " = ? WHERE nombre = ?";
                    PreparedStatement statementEntrenador = conn.prepareStatement(sqlEntrenador);
                    statementEntrenador.setString(1, nuevoValorEntrenador);
                    statementEntrenador.setString(2, nombreEntrenador);

                    int rowsUpdatedEntrenador = statementEntrenador.executeUpdate();
                    if (rowsUpdatedEntrenador > 0) {
                        System.out.println("Jugador modificado correctamente");
                    } else {
                        System.out.println("No se encontró el jugador con el ID especificado");
                    }
                    break;
                case 3:
                    System.out.println("Ingrese el nombre del Equipo que desea modificar:");
                    String nombreEquipo = scanner.nextLine();
                    scanner.nextLine(); // Limpiar el buffer

                    System.out.println("¿Qué atributo desea modificar?");
                    System.out.println("1. Nombre");
                    System.out.println("2. Nacionalidad");
                    System.out.println("3. Fecha de nacimiento");

                    int opcionAtributoEquipo = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer

                    String nombreColumnaEquipo = "";
                    String nuevoValorEquipo = "";

                    switch (opcionAtributoEquipo) {
                        case 1:
                            nombreColumnaEquipo = "nombre";
                            System.out.println("Ingrese el nuevo nombre del Equipo:");
                            nuevoValorEquipo = scanner.nextLine();
                            break;
                        case 2:
                            nombreColumnaEquipo = "pais";
                            System.out.println("Ingrese el nuevo pais del Equipo:");
                            nuevoValorEquipo = scanner.nextLine();
                            break;
                        case 3:
                            nombreColumnaEquipo = "fundación";
                            System.out.println("Ingrese la nueva fecha de fundación del equipo (YYYY-MM-DD):");
                            nuevoValorEquipo = scanner.nextLine();
                            break;
                        case 4:
                            nombreColumnaEquipo = "estadio";
                            System.out.println("Ingrese el nuevo nombre del estadio del equipo:");
                            nuevoValorEquipo = scanner.nextLine();
                            break;
                        default:
                            System.out.println("Opción no válida");
                            conn.close();
                            return;
                    }
                    String sqlEquipo = "UPDATE Entrenadores SET " + nombreColumnaEquipo + " = ? WHERE nombre = ?";
                    PreparedStatement statementEquipo = conn.prepareStatement(sqlEquipo);
                    statementEquipo.setString(1, nuevoValorEquipo);
                    statementEquipo.setString(2, nombreEquipo);

                    int rowsUpdatedEquipo = statementEquipo.executeUpdate();
                    if (rowsUpdatedEquipo > 0) {
                        System.out.println("Jugador modificado correctamente");
                    } else {
                        System.out.println("No se encontró el jugador con el ID especificado");
                    }
                    break;

                default:
                    System.out.println("Opción no válida");
                    break;
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
