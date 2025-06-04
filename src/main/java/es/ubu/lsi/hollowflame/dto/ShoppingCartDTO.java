package es.ubu.lsi.hollowflame.dto;

import lombok.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Data Transfer Object (DTO) para controlar y gestionar el carrito de
 * compra de un usuario dentro de la tienda.
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDTO {

    // Lista de los juegos presentes en el carrito del usuarios.
    private List<GameInfoDTO> games = new ArrayList<>();

    /**
     * Lógica para añadir un juego dentro del carrito de compra
     * del usuario.
     *
     * @param game Nuevo juego que se desea añadir al carrito de la compra.
     */
    public void addGame(GameInfoDTO game) {
        // Comprobamos que ese juego no ha sido ya añadido al carrito.
        boolean exists = games.stream()
                .anyMatch(g -> g.getRawName().equalsIgnoreCase(game.getRawName()));

        // Si no existe ya, le añadimos al carrito de compra correspondiente.
        if (!exists) games.add(game);
    }

    /**
     * Lógica para eliminar un juego dentro del carrito de compra
     * del usuario.
     *
     * @param gameID Identificador único del juego que se desea eliminar.
     */
    public void removeGame(String gameID) {
        games.removeIf(
                // Comprobamos si ese juego ya existe dentro del la lista.
                g -> g.getRawName().equals(gameID)
        );
    }

    /**
     * Lógica para eliminar todos los juegos dentro del carrito de compra
     * del usuario.
     */
    public void clear() {
        games.clear();
    }

    /**
     * Lógica para calcular el subtotal dentro del carrito de compra
     * que implica el total a pagar por el usuario.
     */
    public String getTotal() {
        // Calculamos el total para el carrito a partir de los precios individuales de los juegos.
        Double total = games.stream()
                .mapToDouble(g -> {
                        // Obtenemos el precio del juego y hacemos un cast para hacer la suma.
                        try {
                            return Double.parseDouble(g.getPrice().replace("$", "").trim());
                        } catch (Exception e) {
                            // Si falla, interpretaremos que el precio es 0.00.
                            return 0.00;
                        }
                }).sum();
        return String.format("$%.2f", total);
    }

    /**
     * Lógica para comprobar si un juego ya se encuentra dentro del carrito
     * de compra de un usuario.
     *
     * @param gameID Identificador único del juego dentro del sistema.
     */
    public boolean contains(String gameID) {
        return games.stream()
                // Comprobamos si el juego ya se encuentra dentro del carrito de la compra.
                .anyMatch(g -> g.getRawName().equals(gameID));
    }

    /**
     * Obtiene la lista de juegos que se encuentra en estos momentos
     * en el carrito de compra del usuario.
     */
    public List<GameInfoDTO> getGameList() {
        return games;
    }
}