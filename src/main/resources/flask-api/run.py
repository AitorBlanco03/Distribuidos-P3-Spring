"""
Punto de arranque de la API, inicializa e ejecuta la aplicación Flask 
para lanzar nuestra API.

    - Autor: Aitor Blanco Fernández, abf1005@alu.ubu.es
    - Version: 1.0.0, 30/05/2025
"""

from app import create_app

# Inicializamos una aplicación Flask para que actué como base de nuestra API.
app = create_app()

# Ejecutamos al aplicación Flask para poder lanzar nuestra API.
if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)