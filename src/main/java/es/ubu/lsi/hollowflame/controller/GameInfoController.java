package es.ubu.lsi.hollowflame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

/**
 * Controlador encargado de controlar y gestionar los datos de los juegos desde
 * la API. Además se encarga de pasar esos datos a la vista para su posterior
 * renderizado.
 */

@Controller
public class GameInfoController {

    /**
     * Obtiene los detalles específicos y concreto de un juego utilizando
     * su identificador recibido a través del URL.
     *
     * @param gameID Identificador único del juego recibido a través
     *               del URL.
     * @return La vista de la página con los detalles del juego.
     */
    @GetMapping("/games/{gameID}")
    public String getGameInfo(@PathVariable String gameID, Model gameInfoPage) {
        return "game-info";
    }
}