public class Entrenador extends Persona {
    private String experiencia;

    public Entrenador(){

    }

    public Entrenador(String nombre, String nacionalidad, String fechaNacimiento, String experiencia) {
        super(nombre, nacionalidad, fechaNacimiento);
        this.experiencia = experiencia;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    
}