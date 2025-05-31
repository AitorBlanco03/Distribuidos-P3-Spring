import requests
import random
from datetime import datetime, timedelta

def get_latest_releases(api_key, len=4):
    """
    Obtiene los últimos lanzamientos en PC en los últimos 30 días.

    Parámetros:
    ------------
    api_key: str
        Clave de acceso a la API.
    len: int
        Número de juegos que se quieren devolver.

    Returns:
    ---------
    list
        Lista con la información de los últimos lanzamientos.
    """
    # Calculamos el rango de precios a partir del día actual.
    current_day = datetime.today().strftime("%Y-%m-%d")
    start_day = (datetime.today() - timedelta(days=30)).strftime("%Y-%m-%d")

    # Definimos la url y los parámetros para obtener los últimos lanzamientos.
    url = "https://api.rawg.io/api/games"
    params = {
        "key": api_key,
        "dates": f"{start_day},{current_day}",
        "plataforms": 4,
        "ordering": "-released",
        "page_size": len
    }

    # Obtenemos la respuesta de la API y la procesamos para obtener los campos que nos interesa.
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
    Obtiene los juegos más populares de PC.

    Parámetros:
    -----------
    api_key: str
        Clave de acceso a la API.
    len: int
        Número de juego a devolver.

    Returns:
    ---------
    list
        Lista con la información de los juegos más populares.
    """
    # Definimos la url y los paramétros para realizar la petición a la API.
    url = "https://api.rawg.io/api/games"
    params = {
        "key": api_key,
        "plataforms": 4,
        "ordering": "-added",
        "page_size": len
    }

    # Obtenemos la respuesta de la API y la procesamos para obtener los campos que nos interesa.
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
        return {"error": "Error al obtener los juegos populares."}
    

def get_user_recommendations(api_key, len=50):
    """
    Obtiene y genera nuevas recomendaciones de juegos para el usuario
    de forma aleatoria (actualmente, no se aplica ningúna lógica o sistema
    de recomendación).

    Parámetros:
    ------------
    api_key: str
        Clave de acceso a la API.
    len: int
        Número de juegps que se desea obtener.
    """
    # Definimos la url y los paramétros para realizar la petición a la API.
    url = "https://api.rawg.io/api/games"
    params = {
        "key": api_key,
        "platforms": 4,
        "page_size": len
    }

    # Obtenemos la respuesta de la API y la procesamos para obtener los campos que nos interesa.
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
    Obtiene todos los juegos disponibles en la API de forma paginada, junto a
    la página anterior y la página siguiente.

    Parámetros:
    ------------
    api_key: str
        Clave de acceso a la API.
    page: int
        Número de la página que se desea obtener.
    len: int
        Número de páginas que se desea mostrar por página.
    
    Returns:
    ---------
    Información de la página solicitada junto a la información de la página anterior
    y la página siguiente.
    """
    # Definimos la URL y los parámetros para realizar la petición a la API.
    url = "https://api.rawg.io/api/games"
    params = {
        "key": api_key,
        "platforms": 4,
        "page": page,
        "page_size": len
    }

    # Obtenemos la respuesta de la API y la procesamos para obtener los campos que nos interesa.
    response = requests.get(url, params=params)
    if response.status_code != 200:
        return {"error": "Error al obtener juegos de la página."}
    
    # Obtenemos los datos de los juegos asociados a esa página.
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