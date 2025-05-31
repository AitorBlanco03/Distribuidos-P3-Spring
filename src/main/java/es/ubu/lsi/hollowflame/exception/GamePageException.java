package es.ubu.lsi.hollowflame.exception;

/**
 * Excepción personaliza para controlar y gestionar los posibles errores
 * al obtener los juegos de forma páginada.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 30/05/2025
 */

public class GamePageException extends RuntimeException {

    public GamePageException() {
        super();
    }

    public GamePageException(String message) {
        super(message);
    }

    public GamePageException(String message, Throwable cause) {
        super(message, cause);
    }
}