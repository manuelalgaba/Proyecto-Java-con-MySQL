/**
 * Clase que representa a una persona.
 */
public class Persona {
    protected int id;
    protected String nombre;
    protected String nacionalidad;
    protected String fechaNacimiento;

    /**
     * Constructor de la clase Persona.
     * 
     * @param id              El ID de la persona.
     * @param nombre          El nombre de la persona.
     * @param nacionalidad    La nacionalidad de la persona.
     * @param fechaNacimiento La fecha de nacimiento de la persona.
     */
    public Persona(int id, String nombre, String nacionalidad, String fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene el ID de la persona.
     * 
     * @return El ID de la persona.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID de la persona.
     * 
     * @param id El ID de la persona.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la persona.
     * 
     * @return El nombre de la persona.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la persona.
     * 
     * @param nombre El nombre de la persona.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la nacionalidad de la persona.
     * 
     * @return La nacionalidad de la persona.
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * Establece la nacionalidad de la persona.
     * 
     * @param nacionalidad La nacionalidad de la persona.
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /**
     * Obtiene la fecha de nacimiento de la persona.
     * 
     * @return La fecha de nacimiento de la persona.
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento de la persona.
     * 
     * @param fechaNacimiento La fecha de nacimiento de la persona.
     */
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
