package es.ubu.lsi.hollowflame.service;

import es.ubu.lsi.hollowflame.dto.GameInfoDTO;
import es.ubu.lsi.hollowflame.dto.GamePageDTO;
import es.ubu.lsi.hollowflame.exception.GamePageException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que se encarga de obtener los juegos de forma organizada
 * y páginada desde la API.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 30/05/2025
 */

@Slf4j
@Service
public class GamePageService {

    // Instanciamos los objetos necesarios para trabajar con la API y procesar sus respuestas.
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Gestiona y solicita a la API la página actual junto a sus juegos a representar
     * dentro de la tienda.
     *
     * @param pageNumber Número de la página que se desea obtener de la API.
     * @throws GamePageException Si hay algún error al obtener esa página desde la API.
     * @return La página y su información obtenida desde la API.
     */
    public GamePageDTO getGamePage(int pageNumber) {
        // Definimos la URL de la API para realizar la correspondiente petición.
        String API_URL = "http://localhost:5000/api/games?page=" + pageNumber;

        try {
            // Realizamos una petición a la API para obtener los datos de esa página.
            String response = restTemplate.getForObject(API_URL, String.class);
            JsonNode responseJSON = objectMapper.readTree(response);

            // Comprobamos que no habido ningun error a la hora de obtener los últimos lanzamientos desde la API.
            if (responseJSON.has("error")) {
                throw new GamePageException();
            }

            // Obtenemos la página anterior y la página siguiente de la información obtenida.
            String previousPage = responseJSON.get("previous").asText(null);
            String nextPage = responseJSON.get("next").asText(null);

            // Obtenemos los datos de los juegos y extraemos los datos relevantes a mostrar.
            List<GameInfoDTO> gamesInfo = new ArrayList<>();
            JsonNode gamesList = responseJSON.get("games_list");

            for (JsonNode gameNode : gamesList) {
                String rawName = gameNode.get("raw_name").asText("");
                String name = gameNode.get("name").asText("");
                String coverURL = gameNode.get("cover").asText("");
                String price = gameNode.get("price").asText("");

                GameInfoDTO gameInfo = new GameInfoDTO(rawName, name, coverURL, price);
                gamesInfo.add(gameInfo);
            }
            return new GamePageDTO(previousPage, nextPage, gamesInfo);
        } catch (GamePageException e) {
            throw e;
        } catch (Exception e) {
            /* Si no hemos podido procesar correctamente la respuesta de la API, mandaremos
             * GamePageException como señal de error. */
            throw new GamePageException();
        }
    }
}