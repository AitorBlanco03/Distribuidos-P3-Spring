package es.ubu.lsi.hollowflame.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) que se encarga de representar una página con los juegos asociados
 * a un género dentro de la tienda. Se utiliza para pasar y transferir datos desde el controlador
 * hasta la vista.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 01/06/2025
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GenrePageDTO {

    /** Campo que representa el nombre en claro del género de todos juegos de la página. */
    private String genreName;

    /** Campo que representa el enlace a la página anterior, o null si no existe enlace. */
    private String previousPage;

    /** Campo que representa el enlace a la página siguiente, o null si no existe enlace. */
    private String nextPage;

    /** Campo que representa la información básica de los juegos asociados a ese página. */
    private List<GameInfoDTO> gamesInfo;
}