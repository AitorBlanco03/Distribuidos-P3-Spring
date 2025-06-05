package es.ubu.lsi.hollowflame.controller;

import org.springframework.ui.ModelMap;
import es.ubu.lsi.hollowflame.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import es.ubu.lsi.hollowflame.service.GenrePageService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import es.ubu.lsi.hollowflame.dto.GenrePageDTO;

/**
 * Controlador que se encarga de gestionar y actualizar la vista que permite a los usuarios
 * explorar y buscar nuevos juegos a partir de su género dentro de la tienda.
 */
@Controller
public class ExploreGenresController {

    // Conectados el controlador al servicio para buscar nuevos juegos a partir de su género.
    @Autowired
    public GenrePageService genrePageService;

    // Contador interno para controlar y gestionar la página a mostrar.
    private int currentPage = 1;

    /**
     * Muestra la página actual a partir de los juegos y su género.
     *
     * @param pageNumber Número de página que se desea mostrar.
     * @param genreName Nombre del género que se desea filtrar.
     * @return Nombre de la página HTML que se debe de renderizar.
     */
    @GetMapping("/genres/{genreName}")
    public String showGenrePage(@PathVariable String genreName,
                                @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                ModelMap exploreGenrePage, HttpSession session) {

        try {
            // Actualizamos la página y obtenemos su información para mostrarla en la vista.
            currentPage = pageNumber;
            GenrePageDTO pageData = genrePageService.getGenrePage(genreName, pageNumber);

            exploreGenrePage.addAttribute("previousPage", pageData.getPreviousPage());
            exploreGenrePage.addAttribute("games", pageData.getGamesInfo());
            exploreGenrePage.addAttribute("nextPage", pageData.getNextPage());
            exploreGenrePage.addAttribute("genreName", genreName);
            exploreGenrePage.addAttribute("genre", pageData.getGenreName());

            // Obtenemos de la sesión, el usuario actual en estos momentos.
            User user = (User) session.getAttribute("user");
            exploreGenrePage.addAttribute("user", user);

            return "explore-genres";
        } catch (Exception e) {
            return "500-error";
        }
    }

    /**
     * Se encarga de obtener la información de la página anterior al actual.
     *
     * @param genreName Nombre del género que se desea filtrar.
     */
    @GetMapping("/genres/{genreName}/previous")
    public String showPreviousPage(
            @PathVariable String genreName,
            ModelMap exploreGamePage, HttpSession session) {
        return showGenrePage(genreName, currentPage - 1, exploreGamePage, session);
    }

    /**
     * Se encarga de obtener la información de la página siguiente a la actual.
     *
     * @param genreName Nombre del género que se desea filtrar.
     */
    @GetMapping("/genres/{genreName}/next")
    public String showNextPage(
            @PathVariable String genreName,
            ModelMap exploreGamePage, HttpSession session) {
        return showGenrePage(genreName, currentPage + 1, exploreGamePage, session);
    }
}