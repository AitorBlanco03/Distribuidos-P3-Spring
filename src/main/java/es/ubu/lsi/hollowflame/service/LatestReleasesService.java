package es.ubu.lsi.hollowflame.service;

import es.ubu.lsi.hollowflame.dto.GameInfoDTO;
import es.ubu.lsi.hollowflame.exception.LatestReleasesException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de recopilar toda la información sobre los lanzamientos más recientes
 * de videojuegos desde la API y pasar esa información para actualizar la vista de la tienda.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 30/05/2025.
 */

@Service
public class LatestReleasesService {

    // Instanciamos los objetos necesarios para trabajar con la API y procesar sus respuestas.
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Realiza una petición a la API para obtener la información sobre los lanzamientos
     * más recientes de videojuegos.
     *
     * @throws LatestReleasesException Lanza una excepción si falla al obtener los datos de la API.
     * @return La información sobre los lanzamientos más recientes de videojuegos en forma de lista.
     */
    public List<GameInfoDTO> getLatestReleases() {
        // Definimos la URL de la API para realizar la correspondiente petición.
        String API_URL = "http://localhost:5000/api/latest-releases";

        // Lista que se encarga de registrar la información de los ultimos lanzamientos.
        List<GameInfoDTO> latestReleases = new ArrayList<>();

        try {
            // Obtenemos y procesamos la respuesta para que sea más fácil trabajar con ella.
            String response = restTemplate.getForObject(API_URL, String.class);
            JsonNode responseJSON = objectMapper.readTree(response);

            // Comprobamos que no habido ningun error a la hora de obtener los últimos lanzamientos desde la API.
            if (responseJSON.has("error")) {
                throw new LatestReleasesException();
            }

            // Obtenemos de la respuesta, los diferentes campos que nos resultan interesante mostrar.
            for (JsonNode gameInfoNode : responseJSON) {
                String rawName = gameInfoNode.get("raw_name").asText("");
                String name = gameInfoNode.get("name").asText("");
                String coverURL = gameInfoNode.get("cover").asText("");
                String price = gameInfoNode.get("price").asText("");

                GameInfoDTO gameInfo = new GameInfoDTO(rawName, name, coverURL, price);
                latestReleases.add(gameInfo);
            }

            return  latestReleases;
        } catch (LatestReleasesException e) {
            throw e;
        } catch (Exception e) {
            /* Si no hemos podido procesar correctamente la respuesta de la API, mandaremos
             * LatestReleasesException como señal de error. */
            throw new LatestReleasesException();
        }
    }
}