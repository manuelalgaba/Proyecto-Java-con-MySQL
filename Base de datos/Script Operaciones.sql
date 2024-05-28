/* SENTENCIAS MULTITABLAS */

/* Consultas */

/* Obtener el nombre del equipo, su entrenador y la cantidad de jugadores de cada equipo */
SELECT Equipos.nombre, Entrenadores.nombre AS nombre_entrenador, COUNT(Jugadores.ID_jugador) AS cantidad_jugadores
FROM Equipos INNER JOIN Entrenadores ON Equipos.entrenador_id = Entrenadores.ID_entrenador
LEFT JOIN Jugadores ON Equipos.ID_equipo = Jugadores.equipo_id GROUP BY Equipos.nombre;

/* Obtener el nombre y la fecha de nacimiento de los jugadores que son delanteros y pertenecen al Barcelona */
SELECT nombre, fecha_nacimiento FROM Jugadores WHERE posicion = 'Delantero' 
AND equipo_id = 2;

/* Mostrar el nombre del entrenador y el nombre del estadio de los equipos que participaron la Liga Española */
SELECT e.nombre AS nombre_equipo, en.nombre AS nombre_entrenador, e.estadio
FROM Equipos e INNER JOIN Entrenadores en ON e.entrenador_id = en.ID_entrenador
INNER JOIN Participacion_Y_Clasificacion pc ON e.ID_equipo = pc.equipo_id
INNER JOIN Torneos t ON pc.torneo_id = t.ID_torneo
WHERE t.nombre = 'Liga Española';

/* Obtener el nombre de los equipos que han ganado al menos un partido la Premier League junto con el número total de partidos ganados */
SELECT e.nombre, SUM(pc.partidos_ganados) AS total_partidos_ganados
FROM Equipos e JOIN Participacion_Y_Clasificacion pc ON e.ID_equipo = pc.equipo_id
JOIN Torneos t ON pc.torneo_id = t.ID_torneo WHERE t.nombre = 'Premier League' 
AND pc.partidos_ganados > 0 GROUP BY e.nombre;

/* Listar el nombre del equipo y la cantidad total de jugadores que tienen una fecha de nacimiento posterior al año 1995 */
SELECT e.nombre AS nombre_equipo, COUNT(*) AS total_jugadores_post_1995
FROM Equipos e INNER JOIN Jugadores j ON e.ID_equipo = j.equipo_id
WHERE YEAR(j.fecha_nacimiento) > 1995 GROUP BY e.nombre;

/* Actualizaciones */ 

/* Cambiar la nacionalidad del jugador con ID_jugador = 3 a Argentina */
UPDATE Jugadores SET nacionalidad = 'Argentina'
WHERE ID_jugador = 3;

/* Cambiar la fecha de fundación del equipo con ID_equipo = 1 a 1902-03-05 */
UPDATE Equipos SET fundacion = '1902-03-05'
WHERE ID_equipo = 1;

/* Eliminar todos los registros de la tabla "Jugadores" sean Defensas */
DELETE FROM Jugadores
WHERE posicion = 'Defensa';

/* VISTAS */

/* VIsta para el nombre de los equipos que han ganado al menos un partido la Premier League junto con el número total de partidos ganados */
CREATE VIEW vista_total_partidos_ganados AS
SELECT e.nombre, SUM(pc.partidos_ganados) AS total_partidos_ganados
FROM Equipos e JOIN Participacion_Y_Clasificacion pc ON e.ID_equipo = pc.equipo_id
JOIN Torneos t ON pc.torneo_id = t.ID_torneo 
WHERE t.nombre = 'Premier League' AND pc.partidos_ganados > 0 
GROUP BY e.nombre;


/* Vista para listar el nombre del equipo y la cantidad total de jugadores que tienen una fecha de nacimiento posterior al año 1995 */
CREATE VIEW vista_total_jugadores_post_1995 AS
SELECT e.nombre AS nombre_equipo, COUNT(*) AS total_jugadores_post_1995
FROM Equipos e 
INNER JOIN Jugadores j ON e.ID_equipo = j.equipo_id
WHERE YEAR(j.fecha_nacimiento) > 1995 
GROUP BY e.nombre;


/* FUNCION Y PROCEDIMIENTO */

/* Procedimiento almacenado que inserta un nuevo jugador en la tabla de jugadores */
DELIMITER $$
CREATE PROCEDURE InsertarJugador (
    IN nombre_jugador VARCHAR(100),
    IN nacionalidad_jugador VARCHAR(50),
    IN fecha_nacimiento_jugador DATE,
    IN posicion_jugador VARCHAR(50),
    IN equipo_id_jugador INT,
    IN entrenador_id_jugador INT
)
BEGIN
    INSERT INTO Jugadores (nombre, nacionalidad, fecha_nacimiento, posicion, equipo_id, entrenador_id)
    VALUES (nombre_jugador, nacionalidad_jugador, fecha_nacimiento_jugador, posicion_jugador, equipo_id_jugador, entrenador_id_jugador);
END $$
DELIMITER ;

/* Función que devuelve la edad promedio de los jugadores de un equipo */
DELIMITER $$
CREATE FUNCTION EdadPromedioJugadores (equipo_id_param INT)
RETURNS DECIMAL(10,2)
BEGIN
    DECLARE avg_age DECIMAL(10,2);
    SELECT AVG(TIMESTAMPDIFF(YEAR, fecha_nacimiento, CURDATE())) INTO avg_age
    FROM Jugadores WHERE equipo_id = equipo_id_param;
    RETURN avg_age;
END $$
DELIMITER ;

/* TRIGGERS */

/* Trigger que impide insertar un nuevo jugador si la fecha de nacimiento indica que es menor de 16 años */
DELIMITER $$
CREATE TRIGGER VerificarEdadJugador
BEFORE INSERT ON Jugadores
FOR EACH ROW
BEGIN
    DECLARE edad INT;
    SET edad = TIMESTAMPDIFF(YEAR, NEW.fecha_nacimiento, CURDATE());
    IF edad < 16 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El jugador debe ser mayor de 16 años';
    END IF;
END $$
DELIMITER ;

 /* Trigger que actualiza la fecha de fundación de un equipo cuando se actualiza la fecha de nacimiento del entrenador asociado a ese equipo */
 DELIMITER $$
CREATE TRIGGER ActualizarFechaFundacionEquipo
AFTER UPDATE ON Entrenadores FOR EACH ROW
BEGIN
    UPDATE Equipos SET fundacion = NEW.fecha_nacimiento WHERE entrenador_id = NEW.id;
END$$
DELIMITER ;

 
 
