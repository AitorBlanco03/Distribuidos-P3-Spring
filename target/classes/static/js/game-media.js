/**
 * Gestiona y controla dinámicamente el contenido multimedia disponible
 * relacionado con el juego (tráilers o capturas de pantalla) dentro de
 * la tienda, ajustando lo que se muestra según la selección del usuario.
 *
 * @author Aitor Blanco Fernández - abf1005@alu.ubu.es
 * @version 1.0.0 - 27/05/2025.
 */

function changeMedia(element) {
    // Obtenemos el contenido de la página que queremos gestionar y controlar.
    const mainMedia = document.getElementById("mainMedia");

    // Si se quiere mostrar el tráiler, gestionamos y adaptamos el contenido para mostrarlo.
    if (element.tagName.toLowerCase() === "video") {
        const source = element.querySelector("source");
        if (!source) return;

        const video = document.createElement("video");
        video.setAttribute("controls", "");
        video.className = mainMedia.className;
        video.id = "mainMedia";

        const newSource = document.createElement("source");
        newSource.src = source.src;
        newSource.type = source.type || "video/mp4";

        video.appendChild(newSource);
        mainMedia.replaceWith(video);
    }
    // Pero si se quiere mostrar una captura de pantalla, gestionamos y adaptamos el contenido para mostrarla.
    else if (element.tagName.toLowerCase() === "img") {
        const img = document.createElement("img");
        img.src = element.src;
        img.alt = "Game";
        img.className = mainMedia.className;
        img.id = "mainMedia";
        mainMedia.replaceWith(img);
    }
}