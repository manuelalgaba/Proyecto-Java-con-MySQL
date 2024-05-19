public class Entrenador {
    private int id;
    private String nombre;
    private String nacionalidad;
    private String fechaNacimiento;
    private String nombreEquipo;

    public Entrenador(String nombre, String nacionalidad, String fechaNacimiento, String nombreEquipo) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.nombreEquipo = nombreEquipo;
    }

    @Override
    public String toString() {
        return "Entrenador [nombre=" + nombre + ", nacionalidad=" + nacionalidad + ", fechaNacimiento="
                + fechaNacimiento + ", nombreEquipo=" + nombreEquipo + "]";
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

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

}
