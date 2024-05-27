public class Jugador extends Persona {
    private String posicion;
    private String equipo;

    public Jugador(String nombre, String nacionalidad, String fechaNacimiento, String posicion, String equipo) {
        super(nombre, nacionalidad, fechaNacimiento);
        this.posicion = posicion;
        this.equipo = equipo;
    }

    @Override
    public String getDescripcion() {
        return "Jugador: Nombre=" + nombre + ", Posición=" + posicion + ", Equipo=" + equipo;
    }

    




    @Override
    public String toString() {
        return "Nombre = " + nombre + ", posicion = " + posicion + ", nacionalidad = " + nacionalidad + ", equipo = "
                + equipo + ", fechaNacimiento = " + fechaNacimiento;
    }

    public String getPosicion() {
        return posicion;
    }



    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }



    public String getEquipo() {
        return equipo;
    }



    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }
}
