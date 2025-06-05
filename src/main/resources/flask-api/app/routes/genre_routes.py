"""
Define las rutas asociadas para obtener la información de los
géneros de los juegos.

    - Autor: Aitor Blanco Fernández, abf1005@alu.ubu.es.
    - Versión: 1.0.0, 01/06/2025
"""

from flask import Blueprint, jsonify, current_app, request
from app.controllers.genre_lookup_controller import games_by_genre

# Blueprint para obtener toda la información disponible de los géneros.
genres_bp = Blueprint("genres", __name__)

# Definimos la ruta para obtener los videojuegos por género.
@genres_bp.route('by-genre', methods=['GET'])
def paginated_genres():
    try:
        # Obtenemos la clave de la API para hacer las peticiones.
        api_key = current_app.config["API_KEY"]

        # A partir de la URL deducimos la página y el género a obtener.
        genreID = request.args.get('genre')
        page = request.args.get('page', default=1, type=int)
        return games_by_genre(api_key, genreID, page)
    except:
        return {"error": "Error al obtener esa página del género"}