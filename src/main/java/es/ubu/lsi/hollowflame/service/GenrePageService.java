package es.ubu.lsi.hollowflame.service;


import es.ubu.lsi.hollowflame.dto.GameInfoDTO;
import es.ubu.lsi.hollowflame.dto.GenrePageDTO;
import es.ubu.lsi.hollowflame.exception.GenrePageException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio responsable de obtener los juegos de manera estructurada
 * y paginada según su género desde la API.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 01/06/2025
 */

@Service
public class GenrePageService {

    // Instanciamos los objetos necesarios para trabajar con la API y procesar sus respuestas.
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Solicita la información de los juegos correspondientes a un género y devuelve la página
     * actual con los juegos que deben ser mostrados en la tienda.
     *
     * @param genreName Nombre del género de los juegos a recuperar.
     * @param pageNumber Número de la página que se desea obtener.
     * @throws GenrePageException Si hay algún error al obtener esa página desde la API.
     * @return La página y su información obtenida desde la API.
     */
    public GenrePageDTO getGenrePage(String genreName, int pageNumber) {
        // Definimos la URL de la API para realizar la correspondiente petición.
        String API_URL = "http://flask-api:5000/api/by-genre?genre=" +
                genreName + "&page=" + pageNumber;

        try {
            // Realizamos una petición a la API para obtener los datos de esa página.
            String response = restTemplate.getForObject(API_URL, String.class);
            JsonNode responseJSON = objectMapper.readTree(response);

            // Comprobamos que no habido ningun error a la hora de obtener desde la API.
            if (responseJSON.has("error")) {
                throw new GenrePageException();
            }

            // Obtenemos las referencias al géneros y a las páginas.
            String genre = responseJSON.get("genre").asText("");
            String previousPage = responseJSON.get("previous").asText(null);
            String nextPage = responseJSON.get("next").asText(null);

            // Obtenemos los datos de los juegos y extraemos los datos relevantes a mostrar.
            List<GameInfoDTO> gamesInfo = new ArrayList<>();
            JsonNode gamesList = responseJSON.get("games");

            for (JsonNode gameNode : gamesList) {
                String rawName = gameNode.get("raw_name").asText("");
                String name = gameNode.get("name").asText("");
                String coverURL = gameNode.get("cover").asText("");
                String price = gameNode.get("price").asText("");

                GameInfoDTO gameInfo = new GameInfoDTO(rawName, name, coverURL, price, null);
                gamesInfo.add(gameInfo);
            }
            return new GenrePageDTO(genre, previousPage, nextPage, gamesInfo);
        } catch (GenrePageException e) {
            throw e;
        }catch (Exception e) {
            /* Si no hemos podido procesar correctamente la respuesta de la API, mandaremos
             * GenrePageException como señal de error. */
            throw new GenrePageException();
        }
    }
}