package es.ubu.lsi.hollowflame.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import es.ubu.lsi.hollowflame.dto.GameInfoDTO;
import es.ubu.lsi.hollowflame.dto.GameDetailsDTO;
import es.ubu.lsi.hollowflame.exception.GameInfoException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de obtener y extraer la información y los detalles
 * de los juegos desde la API.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 01/06/2025.
 */

@Service
public class GameInfoService {

    /**
     * Gestiona una solicitud a la API para obtener y extraer la información y los
     * detalles de un juego concreto.
     *
     * @param gameID Identificador único del juego que queremos obtener.
     * @throws GameInfoException Si ocurre algún error al obtener los detalles del juego
     *                           desde la API.
     * @return Información y detalles del juego solicitado.
     */
    public static GameInfoDTO getGameInfo(String gameID) {
        // Definimos la URL de la API para realizar la correspondiente solicitud.
        String API_URL = "http://localhost:5000/api/games/" + gameID;

        // Instanciamos los objetos necesarios para trabajar con la API y procesar sus respuestas.
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Realizamos esa solicitud a la API y obtenemos los detalles de los juegos.
            String response = restTemplate.getForObject(API_URL, String.class);
            JsonNode responseJSON = objectMapper.readTree(response);

            // Comprobamos que no habido ningun error a la hora de obtener los últimos lanzamientos desde la API.
            if (responseJSON.has("error")) {
                throw new GameInfoException();
            }

            // Obtenemos la información y detalles del juego desde la respuesta obtenida.
            JsonNode gameInfoNode = responseJSON;

            String rawName = gameInfoNode.get("raw_name").asText("");
            String name = gameInfoNode.get("name").asText("");
            String description = gameInfoNode.get("description").asText();
            int reviewsCount = gameInfoNode.get("reviews_count").asInt();
            float rating = (float) gameInfoNode.get("rating").asDouble();
            String minRequirements = gameInfoNode.get("requirements_min").asText();
            String recommendedRequirements = gameInfoNode.get("requirements_recommended").asText();
            String trailer = gameInfoNode.get("trailer").asText();

            ArrayNode screenshots = (ArrayNode) gameInfoNode.get("screenshots");
            List<String> screenshotUrls = new ArrayList<>();
            for (JsonNode screenshot : screenshots) {
                screenshotUrls.add(screenshot.asText());
            }

            // Creamos un objeto para trabajar mejor con los detalles del juego obtenido.
            GameDetailsDTO gameDetails = new GameDetailsDTO(reviewsCount, rating, description,
                    trailer, screenshotUrls, minRequirements, recommendedRequirements);

            GameInfoDTO gameInfo = new GameInfoDTO(rawName, name, null, "$50.00", gameDetails);
            return gameInfo;
        } catch (GameInfoException e) {
            throw e;
        } catch (Exception e) {
            /* Si no hemos podido procesar correctamente la respuesta de la API, mandaremos
            GameInfoException como señal de error. */
            throw new GameInfoException();
        }
    }
}