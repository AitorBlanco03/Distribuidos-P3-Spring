package es.ubu.lsi.hollowflame.exception;

/**
 * Excepción personalizada para controlar y gestionar los posibles errores
 * al obtener la información de los últimos lanzamientos de videojuegos.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 30/05/2025
 */

public class LatestReleasesException extends RuntimeException {

    public LatestReleasesException() {
        super();
    }

    public LatestReleasesException(String message) {
        super(message);
    }

    public LatestReleasesException(String message, Throwable cause) {
        super(message, cause);
    }
}