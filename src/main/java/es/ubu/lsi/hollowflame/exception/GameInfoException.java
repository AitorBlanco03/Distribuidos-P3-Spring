package es.ubu.lsi.hollowflame.exception;

/**
 * Excepción personalizada para controlar y gestionar posibles errores
 * al obtener y extraer la información y detalles de los juegos.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 01/06/2025
 */

public class GameInfoException extends RuntimeException {

    public GameInfoException() {
        super();
    }

    public GameInfoException(String message) {
        super(message);
    }

    public GameInfoException(String message, Throwable cause) {
        super(message, cause);
    }
}