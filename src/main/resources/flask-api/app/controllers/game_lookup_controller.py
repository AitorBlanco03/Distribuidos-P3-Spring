"""
Controladores que se encargan de controlar y gestionar grandes cantidades
de datos de videojuegos desde la API para su posterior representación en
las vistas de nuestra aplicación.

    - Autor: Aitor Blanco Fernández, abf1005@alu.ubu.es
    - Versión: 1.1.0, 31/05/2025.
"""

import requests
import random
from datetime import datetime, timedelta

def get_latest_releases(api_key, len=4):
    """
    Obtiene de la API, los últimos lanzamientos en PC en los últimos 30
    días

    Parámetros:
    ------------
    api_key: str
        Clave de acceso a la API.
    len: int, Default=4
        Número de juegos que se quieren obtener

    Returns:
    ---------
    list
        Lista con la información de los últimos lanzamientos en PC en los
        30 días.
    """
    # Calculamos el rango de fechas, teniendo como base el día actual.
    current_day = datetime.today().strftime("%Y-%m-%d")
    start_day = (datetime.today()- timedelta(days=30)).strftime("%Y-%m-%d")

    # Definimos la URL y los parámetros necesarios para realizar la petición.
    url = "https://api.rawg.io/api/games"
    params = {
        "key": api_key,
        "dates": f"{start_day},{current_day}",
        "plataforms": 4,
        "ordering": "-released",
        "page_size": len
    }

    # Obtenemos y procesamos la respuesta obtenida para obtener los campos que nos interesa mostrar.
    response = requests.get(url, params=params)
    if response.status_code == 200:
        games = response.json()["results"]
        return [{
            "raw_name": game["slug"],
            "name": game["name"],
            "cover": game["background_image"],
            "price": "$50.00"
        } for game in games]
    else:
        # En caso de error, devolvemos un mensaje informativo.
        return {"error": "Error al obtener los últimos lanzamientos."}


def get_popular_games(api_key, len=4):
    """
    Obtiene de la API, los juegos más populares de PC a día de hoy.

    Parámetros:
    ------------
    api_key: str
        Clave de acceso a la API.
    len: int
        Número de juegos que se quieren obtener y devolver.

    Returns:
    ---------
    list
        Lista con la información de los juegos obtenidos desde la API.
    """
    # Definimos la URL y los parámetros necesarios para realizar la petición.
    url = "https://api.rawg.io/api/games"
    params = {
        "key": api_key,
        "plataforms": 4,
        "ordering": "-added",
        "page_size": len
    }

    # Obtenemos y procesamos la respuesta obtenida para obtener los campos que nos interesa mostrar.
    response = requests.get(url, params=params)
    if response.status_code == 200:
        games = response.json()["results"]
        games_list = [{ "raw_name": game["slug"],
                        "name": game["name"],
                        "cover": game["background_image"],
                        "price": "$50.00"
                        } for game in games]
        random.shuffle(games_list)
        return games_list
    else:
        # En caso de error, devolvemos un mensaje informativo.
        return {"error": "Error al obtener las recomendaciones del usuario."}


def get_user_recommendations(api_key, len=50):
    """
    Genera nuevas recomendaciones de videojuegos para el usuario de forma
    aleatorio (por defecto, no se aplica ninguna lógica o sistema de recomendación
    para obtener las nuevas recomendaciones).

    Parámetros:
    ------------
    api_key: str
        Clave de acceso a la API.
    len: int, Default=50
        Número de recomendaciones que se desean obtener.
    """
    # Definimos la URL y los parámetros necesarios para realizar la petición.
    url = "https://api.rawg.io/api/games"
    params = {
        "key": api_key,
        "platforms": 4,
        "page_size": len
    }

    # Obtenemos y procesamos la respuesta para obtener los campos que nos interesa mostrar.
    response = requests.get(url, params=params)
    if response.status_code == 200:
        games = response.json()["results"]
        games_list = [{ "raw_name": game["slug"],
                    "name": game["name"],
                    "cover": game["background_image"],
                    "price": "$50.00"
                    } for game in games]
        random.shuffle(games_list)
        return games_list
    else:
        # En caso de error, devolvemos un mensaje informativo.
        return {"error": "Error al obtener las recomendaciones del usuario."}
        
def get_games_paginated(api_key, page=1, len=50):
    """
    Obtiene desde la API, una página con un conjunto de datos de videojuegos.
    
    En los resultados también incluiran la información necesaria para navegar
    entre las diferentes páginas, como las referencias a la página anterior y
    a la siguiente.

    Paramétros:
    ------------
    api_key : str
        Clave de acceso a la API.
    page : int, Default=1
        Número de la página que se desea obtener.
    len : int, Default=50
        Número de juegos que se quieren mostrar por página.

    Returns:
    ---------
    Información completa de la página solicitda junto a las referencias a la 
    página anterior y a la siguiente.
    """
    # Definimos la URL y los parámetros necesarios para realizar la petición.
    url = "https://api.rawg.io/api/games"
    params = {
        "key": api_key,
        "platforms": 4,
        "page": page,
        "page_size": len
    }

    # Obtenemos y procesamos la respuesta para obtener los campos que nos interesa mostrar.
    response = requests.get(url, params=params)
    if response.status_code != 200:
        return {"error": "Error al obtener juegos de la página."}
    
    # Extraemos los campos de los videojuegos que queremos llegar a mostrar.
    games = response.json()["results"]
    games_list = [{ "raw_name": game["slug"],
                    "name": game["name"],
                    "cover": game["background_image"],
                    "price": "$50.00"} for game in games]
    
    return {
        "previous": response.json()["previous"],
        "games_list": games_list,
        "next": response.json()["next"]
    }