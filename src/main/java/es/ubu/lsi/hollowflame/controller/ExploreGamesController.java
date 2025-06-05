package es.ubu.lsi.hollowflame.controller;

import org.springframework.ui.ModelMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import es.ubu.lsi.hollowflame.service.GamePageService;
import org.springframework.web.bind.annotation.RequestParam;
import es.ubu.lsi.hollowflame.dto.GamePageDTO;


/**
 * Controlador que se encarga de gestionar y actualizar la vista que permite a los usuarios
 * explorar y buscar nuevos juegos dentro de la tienda.
 */
@Controller
public class ExploreGamesController {

    // Conectamos el controlador a la vista para obtener los detalles de cada página.
    @Autowired
    private GamePageService gamePageService;

    // Contador interno para controlar y gestionar la página actual a mostrar.
    int currentPage = 1;

    /**
     * Muestra por pantalla la página de juegos actual y la va actualizando a medida
     * que el usuario va moviendo adelante o hacia atrás.
     *
     * @param pageNumber Número de la página que se desea renderizar y mostrar (Por defecto, siempre
     *                   se muestra la primera).
     * @param exploreGamePage Plantilla/Modelo que recibirá los datos y se encarga de mostrarlo a los usuarios.
     * @return Nombre de la página HTML que se debe de renderizar.
     */
    @GetMapping("/games")
    public String showGamePage(@RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber, ModelMap exploreGamePage) {
        try {
            // Actualizamos el contador asociado a la página actual.
            currentPage = pageNumber;

            // Obtenemos los detalles concretos de la página actual y los mostramos al usuario.
            GamePageDTO pageData = gamePageService.getGamePage(pageNumber);
            exploreGamePage.addAttribute("previousPage", pageData.getPreviousPage());
            exploreGamePage.addAttribute("games", pageData.getGamesInfo());
            exploreGamePage.addAttribute("nextPage", pageData.getNextPage());
            return "explore-games";
        } catch (Exception e) {
            return "500-error";
        }
    }

    /**
     * Controlador que se encarga de actualizar la vista de la página
     * y cargar los datos asociados a la página anterior.
     *
     * @param exploreGamePage Vista que se usará para renderizar los
     *                        datos de la nueva página obtenida.
     */
    @GetMapping("/games/previous")
    public String showPreviousPage(ModelMap exploreGamePage) {
        return showGamePage(currentPage - 1, exploreGamePage);
    }

    /**
     * Controlador que se encarga de actualizar la vista de la página
     * y cargar los datos asociados a la página siguiente.
     *
     * @param exploreGamePage Vista que se usará para renderizar los
     *                        datos de la nueva página obtenida.
     */
    @GetMapping("/games/next")
    public String showNextPage(ModelMap exploreGamePage) {
        return showGamePage(currentPage + 1, exploreGamePage);
    }
}