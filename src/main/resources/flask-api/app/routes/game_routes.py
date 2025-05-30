"""
Define las rutas asociadas para obtener la información de los juegos.

    - Autor: Aitor Blanco Fernández, abf1005@alu.ubu.es.
    - Versión: 1.1.0, 30/05/2025
"""

from flask import Blueprint, jsonify, current_app
from app.controllers.game_controller import get_latest_releases, get_popular_games, get_user_recommendations

# Blueprint para obtener toda la información disponible de los juegos.
games_bp = Blueprint("games", __name__)

# Registramos la ruta para obtener los últimos lanzamientos.
@games_bp.route("/latest-releases", methods=["GET"])
def latest_releases():
    # Obtenemos la clave de la API para hacer las peticiones.
    api_key = current_app.config["API_KEY"]

    # Obtenemos toda la información disponible de los últimos lanzamientos.
    latest_releases = get_latest_releases(api_key)
    return jsonify(latest_releases)

# Registramos la ruta para obtener los juegos más populares.
@games_bp.route("/popular-games", methods=["GET"])
def popular_games():
    # Obtenemos la clave de la API para hacer las peticiones.
    api_key = current_app.config["API_KEY"]

    # Obtenemos toda la información disponible de los últimos lanzamientos.
    popular_games = get_popular_games(api_key)
    return jsonify(popular_games)

@games_bp.route("/recommend-games", methods=["GET"])
def recommend_games():
    # Obtenemos la clave de la API para hacer las peticiones.
    api_key = current_app.config["API_KEY"]

    # Obtenemos toda la información disponible de los últimos lanzamientos.
    recommended_games = get_user_recommendations(api_key)
    return jsonify(recommended_games)