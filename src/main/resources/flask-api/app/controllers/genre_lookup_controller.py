"""
Controlores encargados de filtrar y obtener los videojuegos a partir
de su género.

    - Autor: Aitor Blanco Fernández, abf1005@alu.ubu.es
    - Versión: 1.0.0, 01/06/2025.
"""
import requests

def games_by_genre(api_key, genreID, page=1, len=50):
    """
    Filtra y obtiene los videojuegos a partir de su género desde
    la API.

    Parámetros:
    ------------
    api_key: str
        Clave de acceso a la API.
    genreID: str
        Identificador único del género para obtener los videojuegos
        desde la API.
    page: int, Default=1
        Página que se debe obtener.
    len: int, Default
        Número de videojuegos que se desean mostrar por página.

    Returns:
    ---------
    dict
        Información completa de la página junto a referencias del
        género solicitado así como referencias la página anterior
        y siguiente.
    """
    # Obtenemos el nombre en claro del género.
    name = get_genre_name(api_key, genreID)

    # Definimos la URL y los parámetros para hacer esa petición.
    url = "https://api.rawg.io/api/games"
    params = {
        "key": api_key,
        "genres": genreID,
        "page": page,
        "page_size": len
    }

    try:
        # Realizamos la petición e interpretamos los resultados obtenidos.
        response = requests.get(url, params=params)
        response.raise_for_status()
    except requests.RequestException as e:
        return {"error": "Error al obtener los juegos desde la API"}
    
    # Obtenemos la respuesta y obtenemos la información de esos juegos.
    gameInfo = response.json()
    games_list = [{
        "raw_name": game['slug'],                 
        "name": game["name"],
        "cover": game["background_image"],
        "price": "$50.00"
    } for game in gameInfo['results']]

    # Devolvemos la página obtenida junto a las referencias del
    # género solicitado así como referencias la página anterior y siguiente.
    return {
        "genre": name,
        "previous": gameInfo['previous'],
        "games": games_list,
        "next": gameInfo['next']
    }


def get_genre_name(api_key, genreID):
    """
    Obtiene el nombre en claro del género a partir de su identificador único.

    Parámetros:
    ------------
    api_key: str
        Clave de acceso a la API.
    genreID: str
        Identificador único del género dentro de la API.

    Returns:
    ---------
    str
        Nombre en claro del género, sino se encuentra se devuelve su identificador
        único.
    """
    # Definimos la URL y los parámetros para hacer esa petición.
    url = "https://api.rawg.io/api/genres"
    params = {"key": api_key}

    # Procesamos la respuesta obtenida de la API e intepretamos su resultado.
    try:
        response = requests.get(url, params=params)
        response.raise_for_status()
    except requests.RequestException:
        return genreID
    
    # Buscamos el género a partir de su identificador.
    for genre in response.json()['results']:
        if genre["slug"] == genreID:
            return genre.get("name", genreID)
    return genreID