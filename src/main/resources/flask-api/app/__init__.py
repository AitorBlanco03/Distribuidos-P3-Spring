"""
Paquete que se encarga de inicializar y configurar la aplicación
Flask, donde se definirán y registrán los diferentes controladores y
rutas de la API a llegar a ser consumidas.

    - Autor: Aitor Blanco Fernández, abf1005@alu.ubu.es
    - Version: 1.0.0, 30/05/2025
"""

from flask import Flask
from app.routes import register_routes

def create_app():
    """
    Crea, inicializa y configura la aplicación Flask, y registra las
    diferentes rutas de la API que se podrán a llegar ser consumidas.

    Returns:
    ---------
    Aplicación Flask con las diferentes rutas de la API que podrán a
    llegar a ser consumidas.
    """
    # Inicializamos la aplicación Flask junto a la clave de la API.
    app = Flask(__name__)
    app.config["API_KEY"] = "f98547177f194a3e9c62e24294796c0f"

    # Registramos las diferentes rutas de la API a llegar a ser consumidas.
    register_routes(app)

    return app