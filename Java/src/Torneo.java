import java.sql.Date;

/**
 * Clase que representa un torneo de fútbol.
 */
public class Torneo {
    private String nombre;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;

    /**
     * Constructor de la clase Torneo.
     * 
     * @param nombre       El nombre del torneo.
     * @param descripcion  La descripción del torneo.
     * @param fechaInicio  La fecha de inicio del torneo.
     * @param fechaFin     La fecha de fin del torneo.
     */
    public Torneo(String nombre, String descripcion, Date fechaInicio, Date fechaFin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    /**
     * Obtiene el nombre del torneo.
     * 
     * @return El nombre del torneo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del torneo.
     * 
     * @param nombre El nombre del torneo.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del torneo.
     * 
     * @return La descripción del torneo.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del torneo.
     * 
     * @param descripcion La descripción del torneo.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la fecha de inicio del torneo.
     * 
     * @return La fecha de inicio del torneo.
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha de inicio del torneo.
     * 
     * @param fechaInicio La fecha de inicio del torneo.
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha de fin del torneo.
     * 
     * @return La fecha de fin del torneo.
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha de fin del torneo.
     * 
     * @param fechaFin La fecha de fin del torneo.
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}
