/**
 * Clase que representa un equipo de fútbol.
 */
public class Equipo {
    private int id;
    private String nombre;
    private String pais;
    private String fundacion;
    private String estadio;
    private int entrenadorId;

    /**
     * Constructor de la clase Equipo.
     * 
     * @param id           El ID del equipo.
     * @param nombre       El nombre del equipo.
     * @param pais         El país del equipo.
     * @param fundacion    La fecha de fundación del equipo.
     * @param estadio      El nombre del estadio del equipo.
     * @param entrenadorId El ID del entrenador del equipo.
     */
    public Equipo(int id, String nombre, String pais, String fundacion, String estadio, int entrenadorId) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
        this.fundacion = fundacion;
        this.estadio = estadio;
        this.entrenadorId = entrenadorId;
    }

    /**
     * Obtiene el ID del equipo.
     * 
     * @return El ID del equipo.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del equipo.
     * 
     * @param id El ID del equipo.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del equipo.
     * 
     * @return El nombre del equipo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del equipo.
     * 
     * @param nombre El nombre del equipo.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el país del equipo.
     * 
     * @return El país del equipo.
     */
    public String getPais() {
        return pais;
    }

    /**
     * Establece el país del equipo.
     * 
     * @param pais El país del equipo.
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * Obtiene la fecha de fundación del equipo.
     * 
     * @return La fecha de fundación del equipo.
     */
    public String getFundacion() {
        return fundacion;
    }

    /**
     * Establece la fecha de fundación del equipo.
     * 
     * @param fundacion La fecha de fundación del equipo.
     */
    public void setFundacion(String fundacion) {
        this.fundacion = fundacion;
    }

    /**
     * Obtiene el nombre del estadio del equipo.
     * 
     * @return El nombre del estadio del equipo.
     */
    public String getEstadio() {
        return estadio;
    }

    /**
     * Establece el nombre del estadio del equipo.
     * 
     * @param estadio El nombre del estadio del equipo.
     */
    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    /**
     * Obtiene el ID del entrenador del equipo.
     * 
     * @return El ID del entrenador del equipo.
     */
    public int getEntrenadorId() {
        return entrenadorId;
    }

    /**
     * Establece el ID del entrenador del equipo.
     * 
     * @param entrenadorId El ID del entrenador del equipo.
     */
    public void setEntrenadorId(int entrenadorId) {
        this.entrenadorId = entrenadorId;
    }
}
