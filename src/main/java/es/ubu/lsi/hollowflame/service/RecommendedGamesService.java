package es.ubu.lsi.hollowflame.service;

import es.ubu.lsi.hollowflame.dto.GameInfoDTO;
import es.ubu.lsi.hollowflame.exception.RecommendedGamesException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de recopilar toda la información sobre las recomendaciones personalizadas
 * para cada uno de los usuarios desde la API y pasar esa información para actualizar la vista de la tienda.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 30/05/2025.
 */

@Service
public class RecommendedGamesService {

    // Instanciamos los objetos necesarios para trabajar con la API y procesar sus respuestas.
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Realiza una petición a la API para obtener la información sobre las recomendaciones personalizadas
     * para cada uno de los usuarios.
     *
     * @throws RecommendedGamesException Lanza una excepción si falla al obtener los datos de la API.
     * @return La información sobre las recomendaciones personalizadas para cada uno de los usuarios.
     */
    public List<GameInfoDTO> getUserRecommendations() {
        // Definimos la URL de la API para realizar la correspondiente petición.
        String API_URL = "http://localhost:5000/api/recommend-games";

        // Lista que se encarga de recoger las recomendaciones personalizadas para cada uno de los usuarios.
        List<GameInfoDTO> userRecommendations = new ArrayList<>();

        try {
            // Obtenemos y procesamos la respuesta para que sea más fácil trabajar con ella.
            String response = restTemplate.getForObject(API_URL, String.class);
            JsonNode responseJSON = objectMapper.readTree(response);

            // Comprobamos que no ha habido ningún error a la hora de obtener los últimos lanzamientos desde la API.
            if (responseJSON.has("error")) {
                throw new RecommendedGamesException();
            }

            // Obtenemos todas las recomendaciones, pero nos limitamos a 4 y extraemos su información.
            for (int i = 0; i < Math.min(4, responseJSON.size()); i++) {
                JsonNode gameInfoNode = responseJSON.get(i);

                String rawName = gameInfoNode.get("raw_name").asText("");
                String name = gameInfoNode.get("name").asText("");
                String coverURL = gameInfoNode.get("cover").asText("");
                String price = gameInfoNode.get("price").asText("");

                GameInfoDTO gameInfo = new GameInfoDTO(rawName, name, coverURL, price);
                userRecommendations.add(gameInfo);
            }

            return userRecommendations;
        } catch (RecommendedGamesException e) {
            throw e;
        } catch (Exception e) {
            /* Si no hemos podido procesar correctamente la respuesta de la API, mandaremos
             * RecommendedGamesException como señal de error. */
            throw new RecommendedGamesException();
        }
    }
}