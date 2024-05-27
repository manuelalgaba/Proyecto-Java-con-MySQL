import java.util.List;
import java.util.ArrayList;

public class Equipo {
    private int id;
    private String nombre;
    private String pais;
    private String fundacion;
    private String estadio;
    private String entrenador;
    private List<String> plantilla;

    public Equipo(String nombre, String pais, String fundacion, String estadio, String entrenador, String plantilla) {
        this.nombre = nombre;
        this.pais = pais;
        this.fundacion = fundacion;
        this.estadio = estadio;
        this.entrenador = entrenador;
        this.plantilla = new ArrayList<String>();
    }

    
    @Override
    public String toString() {
        return "Nombre = " + nombre + ", pais = " + pais + ", fundacion = " + fundacion + ", estadio  = " + estadio
                + ", entrenador = " + entrenador + ", plantilla = " + plantilla;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getFundacion() {
        return fundacion;
    }

    public void setFundacion(String fundacion) {
        this.fundacion = fundacion;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(String entrenador) {
        this.entrenador = entrenador;
    }

    public List<String> getPlantilla() {
        return plantilla;
    }

    public void agregarJugador(String jugador) {
        this.plantilla.add(jugador);
    }

    public void setPlantilla(List<String> plantilla) {
        this.plantilla = plantilla;
    }

}
