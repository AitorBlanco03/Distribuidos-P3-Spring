package es.ubu.lsi.hollowflame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import es.ubu.lsi.hollowflame.service.GameInfoService;
import es.ubu.lsi.hollowflame.dto.GameInfoDTO;
import org.springframework.ui.ModelMap;

/**
 * Controlador encargado de controlar y gestionar los datos de los juegos desde
 * la API. Además se encarga de pasar esos datos a la vista para su posterior
 * renderizado.
 */

@Controller
public class GameInfoController {

    // Conectamos el controlador a la vista para obtener la información del juego.
    @Autowired
    private GameInfoService gameInfoService;

    /**
     * Obtiene los detalles específicos y concreto de un juego utilizando
     * su identificador recibido a través del URL.
     *
     * @param gameID Identificador único del juego recibido a través
     *               del URL.
     * @return La vista de la página con los detalles del juego.
     */
    @GetMapping("/games/{gameID}")
    public String showGameInfo(@PathVariable String gameID, ModelMap gameInfoPage) {
        try {
            // Obtenemos la información y los detalles del juego recibido.
            GameInfoDTO gameInfo = GameInfoService.getGameInfo(gameID);
            gameInfoPage.addAttribute("gameInfo", gameInfo);
            return "game-info";
        } catch (Exception e) {
            return "404-error";
        }
    }
}