/**
 * Clase que representa un jugador de fútbol.
 */
public class Jugador {
    private String nombre;
    private String nacionalidad;
    private String fechaNacimiento; // Usar String para simplificar
    private String posicion;
    private String equipoNombre; // Nombre del equipo

    /**
     * Constructor de la clase Jugador.
     * 
     * @param nombre           El nombre del jugador.
     * @param nacionalidad     La nacionalidad del jugador.
     * @param fechaNacimiento  La fecha de nacimiento del jugador.
     * @param posicion         La posición del jugador.
     * @param equipoNombre     El nombre del equipo del jugador.
     */
    public Jugador(String nombre, String nacionalidad, String fechaNacimiento, String posicion, String equipoNombre) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.posicion = posicion;
        this.equipoNombre = equipoNombre;
    }

    /**
     * Obtiene el nombre del jugador.
     * 
     * @return El nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del jugador.
     * 
     * @param nombre El nombre del jugador.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la nacionalidad del jugador.
     * 
     * @return La nacionalidad del jugador.
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * Establece la nacionalidad del jugador.
     * 
     * @param nacionalidad La nacionalidad del jugador.
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /**
     * Obtiene la fecha de nacimiento del jugador.
     * 
     * @return La fecha de nacimiento del jugador.
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del jugador.
     * 
     * @param fechaNacimiento La fecha de nacimiento del jugador.
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene la posición del jugador.
     * 
     * @return La posición del jugador.
     */
    public String getPosicion() {
        return posicion;
    }

    /**
     * Establece la posición del jugador.
     * 
     * @param posicion La posición del jugador.
     */
    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    /**
     * Obtiene el nombre del equipo del jugador.
     * 
     * @return El nombre del equipo del jugador.
     */
    public String getEquipoNombre() {
        return equipoNombre;
    }

    /**
     * Establece el nombre del equipo del jugador.
     * 
     * @param equipoNombre El nombre del equipo del jugador.
     */
    public void setEquipoNombre(String equipoNombre) {
        this.equipoNombre = equipoNombre;
    }

    /**
     * Devuelve una representación en formato de cadena de texto del jugador.
     * 
     * @return Una cadena de texto que representa al jugador.
     */
    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Nacionalidad: " + nacionalidad + ", Fecha de Nacimiento: " + fechaNacimiento + ", Posición: " + posicion;
    }
}
