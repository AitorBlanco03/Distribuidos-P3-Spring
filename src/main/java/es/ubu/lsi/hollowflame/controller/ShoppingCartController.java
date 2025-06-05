package es.ubu.lsi.hollowflame.controller;

import es.ubu.lsi.hollowflame.dto.GameInfoDTO;
import es.ubu.lsi.hollowflame.dto.ShoppingCartDTO;
import es.ubu.lsi.hollowflame.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador encargado de controlar y gestionar el carrito de compra de
 * los usuarios dentro de la tienda.
 */

@Controller
public class ShoppingCartController {


    /**
     * Muestra por pantalla el carrito de la compra de un usuario de la tienda.
     */
    @GetMapping("/shopping-cart")
    public String showShoppingCart(ModelMap shoppingCart, HttpSession session) {
        // Obtenemos de la sesión, el usuario actual que ha entrado al sistema.
        User user = (User) session.getAttribute("user");

        // Si no hay usuario registrado, le mandamos al login para que inicie sesión.
        if (user == null) return "redirect:/login";

        // Obtenemos el carrito de la compra y se lo pasamos a la vista.
        ShoppingCartDTO cart = (ShoppingCartDTO) session.getAttribute("cart");
        shoppingCart.addAttribute("cart", cart);

        // Si hay un usuario registrado, le enseñamos su carrito de la compra.
        return "shopping-cart";
    }

    /**
     * Gestiona y define la lógica para añadir un nuevo juego al carrito de la
     * compra del usuario.
     */
    @PostMapping("/shopping-cart/add")
    public String addGame(GameInfoDTO gameInfo, HttpSession session) {
        // Obtenemos de la sesión, el usuario actual que ha entrado al sistema.
        User user = (User) session.getAttribute("user");

        // Si no hay usuario registrado, le mandamos al login para que inicie sesión.
        if (user == null) return "redirect:/login";

        // Obtenemos el carrito de la compra del usuario desde la sesión.
        ShoppingCartDTO cart = (ShoppingCartDTO) session.getAttribute("cart");

        // Si el juego no se encuentra ya presente, le añadimos.
        if (!cart.contains(gameInfo.getRawName())) cart.addGame(gameInfo);

        // Actualizamos el carrito y lo mostramos al usuario.
        session.setAttribute("cart", cart);
        return "redirect:/shopping-cart";
    }

    /**
     * Gestiona y define la lógica para eliminar un juego dentro del carrito de
     * la compra del usuario.
     */
    @PostMapping("/shopping-cart/remove")
    public String removeGame(@RequestParam("gameName") String gameID, HttpSession session) {
        // Obtenemos de la sesión, el usuario actual que ha entrado al sistema.
        User user = (User) session.getAttribute("user");

        // Si no hay usuario registrado, le mandamos al login para que inicie sesión.
        if (user == null) return "redirect:/login";

        // Obtenemos el carrito de compra del usuario desde la sesión.
        ShoppingCartDTO cart = (ShoppingCartDTO) session.getAttribute("cart");

        // Intentamos eliminar el juego del carrito de compra del usuario actual.
        cart.removeGame(gameID);

        // Actualizamos el carrito y lo mostramos al usuario.
        session.setAttribute("cart", cart);
        return "redirect:/shopping-cart";
    }

    /**
     * Gestiona y define la lógica para procesar la lógica de los diferentes juegos
     * que se encuentran en el carrito de la compra del usuario en estos momentos.
     */
    @PostMapping("/shopping-cart/purchase")
    public String purchaseCart(HttpSession session) {
        // Obtenemos de la sesión, el usuario actual que ha entrado al sistema.
        User user = (User) session.getAttribute("user");

        // Si no hay usuario registrado, le mandamos al login para que inicie sesión.
        if (user == null) return "redirect:/login";

        // Obtenemos el carrito de compra del usuario desde la sesión.
        ShoppingCartDTO cart = (ShoppingCartDTO) session.getAttribute("cart");

        // Si el carrito esta vacio, le mandamos directamente a la página principal.
        if (cart.getGameList().isEmpty()) return "redirect:/";

        // Pero si el carrito esta con juegos, simulamos la compra y le mandamos a la página principal.
        cart.clear();
        return "redirect:/";
    }
}