package es.ubu.lsi.hollowflame.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) que se encarga de representar una página con juegos dentro de
 * la tienda. Se utiliza para pasar y transferir datos desde el controlador hasta la vista.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 30/05/2025.
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GamePageDTO {

    /** Campo que representa el enlace a la página anterior, o null si no hay enlace. */
    private String previousPage;

    /** Campo que representa el enlace a la página siguiente, o null si no hay enlace. */
    private String nextPage;

    /** Campo que representa la información básica de los juegos asociados a esa página. */
    private List<GameInfoDTO> gamesInfo;
}