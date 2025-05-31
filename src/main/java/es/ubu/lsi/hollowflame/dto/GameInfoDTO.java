package es.ubu.lsi.hollowflame.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) que se encarga de representar la información
 * básica disponible de un juego dentro de nuestra tienda.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.1.0, 31/05/2025
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameInfoDTO {

    // Identificador único del juego, usado como referencia para obtener detalles más concretos.
    private String rawName;

    // Nombre del juego dentro de la tienda.
    private String name;

    // Url asociada a la portada del juego dentro de la tienda.
    private String coverURL;

    // Precio actual del juego dentro de la tienda.
    private String price;

    // Detalles más especificos del juego dentro de la tienda.
    private GameDetailsDTO details;
}