package es.ubu.lsi.hollowflame.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) encargado de representar detalles más concretos
 * y especificos de los juegos.
 *
 * @author Aitor Blanco Fernández - abf1005@alu.ubu.es
 * @version 1.0.0, 31/05/2025
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameDetailsDTO {

    // Campo que representa el número de reviews que tiene el juego en la tienda.
    private int numReviews;

    // Campo que representa la puntuación media del juego por parte de los usuarios.
    private float averageRating;

    // Campo que representa la descripción del juego dentro de la tienda.
    private String gameDescription;

    // Campo que representa el trailer del juego dentro de la tienda.
    private String trailerURL;

    // Campo que representa las capturas del juego dentro de la tienda.
    private List<String> imagesURLs;

    // Campo que representa los requisitos mínimos para poder jugar el juego.
    private String minimumRequirements;

    // Campo que representa los requisitos recomendados para poder jugar el juego.
    private String recommendedRequierements;
}