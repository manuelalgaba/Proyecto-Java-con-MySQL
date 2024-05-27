public abstract class Persona {
    protected String nombre;
    protected String nacionalidad;
    protected String fechaNacimiento;

    public Persona(String nombre, String nacionalidad, String fechaNacimiento) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Método abstracto para obtener una descripción de la persona
    public abstract String getDescripcion();



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
}
