package es.ubu.lsi.hollowflame.exception;

/**
 * Excepción personalizada para controlar y gestionar los posibles errores
 * al obtener los juegos por género de forma páginada.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 01/06/2025.
 */

public class GenrePageException extends RuntimeException {

    public GenrePageException() {
        super();
    }

    public GenrePageException(String message) {
        super(message);
    }

    public GenrePageException(String message, Throwable cause) {
        super(message, cause);
    }
}