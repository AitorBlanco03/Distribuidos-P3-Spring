package es.ubu.lsi.hollowflame.controller;

import org.springframework.ui.ModelMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import es.ubu.lsi.hollowflame.service.RecommendedGamesService;
import es.ubu.lsi.hollowflame.service.LatestReleasesService;
import es.ubu.lsi.hollowflame.service.PopularGamesService;
import es.ubu.lsi.hollowflame.dto.GameInfoDTO;

import java.util.List;

/**
 * Controlador web encargado de gestionar la vista y página principal de la tienda y actualiza
 * de acuerdo a los datos e información recibida por parte de la API.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 30/05/2025.
 */

@Controller
public class IndexController {

    // Conectamos el controlador cada uno de los servicios encargados de recopilar información desde la API.
    @Autowired
    private LatestReleasesService latestReleasesService;

    @Autowired
    private RecommendedGamesService recommendedGamesService;

    @Autowired
    private PopularGamesService popularGamesService;

    /**
     * Muestra por pantalla la vista y la página principal de la tienda, actualizando su
     * información de acuerdo a los datos e información recibida por parte de la API.
     */
    @GetMapping("/")
    public String showMainPage(ModelMap mainPage) {
        try {
            // Obtenemos los datos de la vista a partir de la API del servicio.
            List<GameInfoDTO> latestReleases = latestReleasesService.getLatestReleases();
            List<GameInfoDTO> userRecommendations = recommendedGamesService.getUserRecommendations();
            List<GameInfoDTO> popularGames = popularGamesService.getPopularGames();

            // Mostramos la página principal actualizado en el proceso su información.
            mainPage.addAttribute("latestReleases", latestReleases);
            mainPage.addAttribute("userRecommendations", userRecommendations);
            mainPage.addAttribute("popularGames", popularGames);
            return "index";
        } catch (Exception e) {
            return "game-info";
        }
    }
}