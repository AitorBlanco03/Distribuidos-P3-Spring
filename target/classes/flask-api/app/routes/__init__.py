"""
Paquete que se encarga de definir y registrar las diferentes rutas
dentro de nuestra API.

    - Autor: Aitor Blanco Fernández, abf1005@alu.ubu.es
    - Versión: 1.0.0, 30/05/2025.
"""

from app.routes.game_routes import games_bp
from app.routes.genre_routes import genres_bp

def register_routes(app):
    """
    Registra las diferentes rutas de nuestra API que pueden llegar
    a ser consumidas.

    Parámetros:
    ------------
    app
        Aplicación Flask donde se registran las diferentes rutas de
        la API que podrán a llegar a ser consumidas.
    """
    app.register_blueprint(games_bp, url_prefix="/api")
    app.register_blueprint(genres_bp, url_prefix="/api")