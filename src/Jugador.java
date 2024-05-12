public class Jugador extends Persona {
    private String posicion;
    private int equipoId;

    public Jugador(){

    }

    public Jugador(String nombre, String nacionalidad, String fechaNacimiento, String posicion, int equipoId) {
        super(nombre, nacionalidad, fechaNacimiento);
        this.posicion = posicion;
        this.equipoId = equipoId;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public int getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(int equipoId) {
        this.equipoId = equipoId;
    }

    
}