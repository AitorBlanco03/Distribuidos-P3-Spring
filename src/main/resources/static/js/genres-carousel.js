/**
 * Encargado de controlar y gestionar el movimiento del carrusel de la tienda, cuyó próposito
 * principal es mostrar al usuario una selección dinámica de los géneros más disponibles.
 *
 * @author Aitor Blanco Fernández - abf1005@alu.ubu.es
 * @version 1.0.0 - 29/05/2025.
 */

function scrollGenresCarousel(direction) {
    // Obtenemos el carrusel de la tienda dentro de la página.
    const carousel = document.getElementById('genres-carousel');

    // Controlamos y gestionamos el carrusel según la dirección pasada por el usuario.
    const scrollAmount = 250;
    carousel.scrollBy({
        left: direction * scrollAmount, behavior: 'smooth'
    })
}