public class Entrenador extends Persona {
    private String equipo;

    public Entrenador(String nombre, String nacionalidad, String fechaNacimiento, String equipo) {
        super(nombre, nacionalidad, fechaNacimiento);
        this.equipo = equipo;
    }

    @Override
    public String getDescripcion() {
        return "Entrenador: Nombre=" + nombre + ", Equipo=" + equipo;
    }

    

    @Override
    public String toString() {
        return "Nombre = " + nombre + ", equipo = " + equipo + ", nacionalidad = " + nacionalidad
                + ", fechaNacimiento = " + fechaNacimiento;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }
    
}
