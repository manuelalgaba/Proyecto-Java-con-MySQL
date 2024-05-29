/**
 * Clase que representa a un entrenador de fútbol, heredando de la clase Persona.
 */
public class Entrenador extends Persona {
    private String email;
    private String telefono;

    /**
     * Constructor de la clase Entrenador.
     * 
     * @param id              El ID del entrenador.
     * @param nombre          El nombre del entrenador.
     * @param nacionalidad    La nacionalidad del entrenador.
     * @param fechaNacimiento La fecha de nacimiento del entrenador.
     * @param email           El correo electrónico del entrenador.
     * @param telefono        El número de teléfono del entrenador.
     */
    public Entrenador(int id, String nombre, String nacionalidad, String fechaNacimiento, String email, String telefono) {
        super(id, nombre, nacionalidad, fechaNacimiento);
        this.email = email;
        this.telefono = telefono;
    }

    /**
     * Obtiene el correo electrónico del entrenador.
     * 
     * @return El correo electrónico del entrenador.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del entrenador.
     * 
     * @param email El correo electrónico del entrenador.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el número de teléfono del entrenador.
     * 
     * @return El número de teléfono del entrenador.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del entrenador.
     * 
     * @param telefono El número de teléfono del entrenador.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
