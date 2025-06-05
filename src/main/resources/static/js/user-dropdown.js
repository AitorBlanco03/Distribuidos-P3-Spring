/**
 * Módulo JS que se encarga de mostrar/ocultar el menú del usuario dentro
 * del menú de navegación.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 05/06/2025.
 */

document.addEventListener('DOMContentLoaded', function () {

    // Obtenemos el botón para mostrar el menú y el menú del usuario.
    const button = document.getElementById('user-menu-button');
    const dropdown = document.getElementById('user-dropdown');

    if (button && dropdown) {

        // Agregamos un listener para mostrar/ocultar el menú del usuario.
        button.addEventListener('click', function (event) {
            dropdown.classList.toggle('hidden');
            event.stopPropagation();
        });

        // Agregamos otro listener para ocultar el menú si se hace click fuera de él.
        document.addEventListener('click', function (event) {
            if (!button.contains(event.target) && !dropdown.contains(event.target)) {
                dropdown.classList.add('hidden');
            }
        });
    }
})