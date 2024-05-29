/**
 * Clase que representa la clasificación de un equipo en un torneo.
 */
public class Clasificacion {
    private int equipoId;
    private int torneoId;
    private int posicion;
    private int puntos;
    private int partidosJugados;
    private int partidosGanados;
    private int partidosEmpatados;
    private int partidosPerdidos;

    /**
     * Constructor de la clase Clasificacion.
     * 
     * @param equipoId          El ID del equipo.
     * @param torneoId          El ID del torneo.
     * @param posicion          La posición del equipo en la clasificación.
     * @param puntos            Los puntos obtenidos por el equipo.
     * @param partidosJugados   La cantidad de partidos jugados por el equipo.
     * @param partidosGanados   La cantidad de partidos ganados por el equipo.
     * @param partidosEmpatados La cantidad de partidos empatados por el equipo.
     * @param partidosPerdidos  La cantidad de partidos perdidos por el equipo.
     */
    public Clasificacion(int equipoId, int torneoId, int posicion, int puntos, int partidosJugados, int partidosGanados, int partidosEmpatados, int partidosPerdidos) {
        this.equipoId = equipoId;
        this.torneoId = torneoId;
        this.posicion = posicion;
        this.puntos = puntos;
        this.partidosJugados = partidosJugados;
        this.partidosGanados = partidosGanados;
        this.partidosEmpatados = partidosEmpatados;
        this.partidosPerdidos = partidosPerdidos;
    }

    /**
     * Obtiene el ID del equipo.
     * 
     * @return El ID del equipo.
     */
    public int getEquipoId() {
        return equipoId;
    }

    /**
     * Obtiene el ID del torneo.
     * 
     * @return El ID del torneo.
     */
    public int getTorneoId() {
        return torneoId;
    }

    /**
     * Obtiene la posición del equipo en la clasificación.
     * 
     * @return La posición del equipo en la clasificación.
     */
    public int getPosicion() {
        return posicion;
    }

    /**
     * Obtiene los puntos obtenidos por el equipo.
     * 
     * @return Los puntos obtenidos por el equipo.
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Obtiene la cantidad de partidos jugados por el equipo.
     * 
     * @return La cantidad de partidos jugados por el equipo.
     */
    public int getPartidosJugados() {
        return partidosJugados;
    }

    /**
     * Obtiene la cantidad de partidos ganados por el equipo.
     * 
     * @return La cantidad de partidos ganados por el equipo.
     */
    public int getPartidosGanados() {
        return partidosGanados;
    }

    /**
     * Obtiene la cantidad de partidos empatados por el equipo.
     * 
     * @return La cantidad de partidos empatados por el equipo.
     */
    public int getPartidosEmpatados() {
        return partidosEmpatados;
    }

    /**
     * Obtiene la cantidad de partidos perdidos por el equipo.
     * 
     * @return La cantidad de partidos perdidos por el equipo.
     */
    public int getPartidosPerdidos() {
        return partidosPerdidos;
    }
}
