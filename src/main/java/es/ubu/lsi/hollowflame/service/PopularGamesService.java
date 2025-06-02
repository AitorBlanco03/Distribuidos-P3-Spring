package es.ubu.lsi.hollowflame.service;

import es.ubu.lsi.hollowflame.dto.GameInfoDTO;
import es.ubu.lsi.hollowflame.exception.PopularGamesException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de recopilar toda la información sobre los juegos más populares
 * desde la API y pasar esa información para actualizar la vista de la tienda.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 30/05/2025.
 */

@Service
public class PopularGamesService {

    // Instanciamos los objetos necesarios para trabajar con la API y procesar sus respuestas.
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Realiza una petición a la API para obtener la información de los juegos más populares
     * dentro de la tienda.
     */
    public List<GameInfoDTO> getPopularGames() {
        // Definimos la URL de la API para realizar la petición correspondiente.
        String API_URL = "http://flask-api:5000/api/popular-games";

        // Lista encargada de registrar la información de los juegos más populares.
        List<GameInfoDTO> popularGames = new ArrayList<>();

        try {
            // Obtenemos y procesamos la respuesta para que sea más fácil trabajar con ella.
            String response = restTemplate.getForObject(API_URL, String.class);
            JsonNode responseJSON = objectMapper.readTree(response);

            // Comprobamos que no habiado problemas al obtener los juegos más populares desde la API.
            if (responseJSON.has("error")) {
                throw new PopularGamesException();
            }

            // Obtenemos de la respuesta, los diferentes campos que nos resultan interesante mostrar.
            for (JsonNode gameInfoNode : responseJSON) {
                String rawName = gameInfoNode.get("raw_name").asText("");
                String name = gameInfoNode.get("name").asText("");
                String coverURL = gameInfoNode.get("cover").asText("");
                String price = gameInfoNode.get("price").asText("");

                GameInfoDTO gameInfo = new GameInfoDTO(rawName, name, coverURL, price, null);
                popularGames.add(gameInfo);
            }
            return  popularGames;
        } catch (PopularGamesException e) {
            throw e;
        } catch (Exception e) {
            /* Si no hemos podido procesar correctamente la respuesta de la API, mandaremos
             * PopularGamesException como señal de error. */
            throw new PopularGamesException();
        }
    }
}