package es.ubu.lsi.hollowflame.exception;

/**
 * Excepción personalizada para controlar y gestionar los posibles errores
 * al obtener la información de las recomendaciones de los usuarios.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 30/05/2025
 */

public class RecommendedGamesException extends RuntimeException {

    public RecommendedGamesException() {
        super();
    }

    public RecommendedGamesException(String message) {
        super(message);
    }

    public RecommendedGamesException(String message, Throwable cause) {
        super(message, cause);
    }
}