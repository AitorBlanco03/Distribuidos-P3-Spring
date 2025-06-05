package es.ubu.lsi.hollowflame.exception;

/**
 * Excepción personalizada para controlar y gestionar los posibles errores
 * al obtener la información de los juegos más populares.
 */

public class PopularGamesException extends RuntimeException {

    public PopularGamesException() {
        super();
    }

    public PopularGamesException(String message) {
        super(message);
    }

    public PopularGamesException(String message, Throwable cause) {
        super(message, cause);
    }
}