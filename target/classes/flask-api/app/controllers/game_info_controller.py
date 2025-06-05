"""
Controlador que se encarga de obtener la información y los detalles
de un juego desde la API.

    - Autor: Aitor Blanco Fernández, abf1005@alu.ubu.es
    - Versión: 1.0.0, 31/05/2025
"""

import requests

# Definimos la URL base de la API para obtener la información y detalles de los juegos.
BASE_URL = 'https://api.rawg.io/api'


def search_game(api_key, gameID):
    """
    Busca el juego solicitado en la API y se encarga de obtener su información
    y detalles de ese juego.

    Parámetros:
    ------------
    api_key: str
        Clave de acceso a la API.
    gameID: str
        Identificador único del juego que se quiere buscar y extraer su información.

    Returns:
    ---------
    List
        Información y detalles de un juego obtenido desde la API.
    """
    # Definimos la URL y los parámetros para realizar esa petición.
    url = f"{BASE_URL}/games/{gameID}"
    params = {"key": api_key}

    # Obtenemos y procesamos la respuesta obtenida de la API.
    response = requests.get(url, params=params)
    if response.status_code != 200:
        return {"error": "No se puedo obtener los datos del juego."}
    gameInfo = response.json()

    # Obtenemos los detalles más generales de ese juego.
    raw_name = gameInfo['slug']
    name = gameInfo['name']
    description = clean_description(gameInfo['description_raw'])
    rating = gameInfo['rating']
    num_reviews = gameInfo['reviews_count']

    # Obtenemos los requisitos mínimos y recomendados para jugar el juego.
    minimum_req, recommend_req = get_game_requirements(gameInfo)

    # Obtenemos el tráiler y las capturas de pantallas del juego.
    if gameInfo['movies_count'] > 0:
        trailer = get_game_trailer(gameID, api_key)
        screenshots = get_game_screenshots(gameID, api_key, len=4)
    else:
        trailer = ''
        screenshots = get_game_screenshots(gameID, api_key, len=5)

    return {
        'raw_name': raw_name,
        'name': name,
        'description': description,
        'rating': rating,
        'reviews_count': num_reviews,
        'requirements_min': minimum_req,
        'requirements_recommended': recommend_req,
        'trailer': trailer,
        'screenshots': screenshots
    }


def get_game_requirements(gameInfo):
    """
    Obtiene los requisitos mínimos y recomendados del juego para
    poder jugarlo desde PC.

    Parámetros:
    ------------
    gameInfo: dict
        Información y detalles del juego.
    
    Returns:
    ---------
    tuple
        Requisitos mínimos y recomendados del juego para poder
        jugarlo desde PC.
    """
    pc_requierements = dict()

    # Seleccionamos, entre todos sus requisitos, aquellos relacionados con los de PC.
    for platform in gameInfo.get('platforms'):
        if platform['platform']['name'] == 'PC':
            pc_requierements = platform['requirements']
            break
    
    return (
        pc_requierements.get('minimum', ''),
        pc_requierements.get('recommended', '')
    )


def get_game_trailer(gameID, api_key):
    """
    Obtiene la URL del primer trailer del juego para mostrarlo
    a los usuarios.

    Parámetros:
    ------------
    gameID: str
        Identificador único asociado al juego que queremos obtener.
    api_key: str
        Clave de acceso a la API.
    """
    # Definimos la URL y sus paramétros para obtener su información.
    url = f"{BASE_URL}/games/{gameID}/movies"
    params = {'key': api_key}

    # Obtenemos la respuesta y la procesamos para obtener la información que nos interesa.
    response = requests.get(url, params=params)
    if response.status_code != 200:
        return ''
    
    # Obtenemos y extraemos el primer trailer asociado a este juego.
    gameInfo = response.json()
    if gameInfo['results']:
        return gameInfo['results'][0]['data']['480']
    

def get_game_screenshots(gameID, api_key, len=3):
    """
    Obtenemos el conjunto de capturas del juego para ser mostrada a
    los usuarios.

    Parámetros:
    ------------
    gameID: str
        Identificador único asociado al juego que queremos obtener.
    api_key: str
        Clave de acceso a la API.
    len: int, Default=3
        Número de capturas del juego que se quieren obtener.

    Returns:
    ---------
    list
        Conjunto de capturas del juego para ser mostrada a los usuarios.
    """
    # Definimos la URL y sus parámetros para realizar esta petición.
    url = f"{BASE_URL}/games/{gameID}/screenshots"
    params = {'key': api_key}

    # Obtenemos la respuesta y la procesamos para obtener la información que nos interesa.
    response = requests.get(url, params=params)
    if response.status_code != 200:
        return ['']
    
    # De todos las capturas, solo obtenemos el número de capturas solicitido.
    gameInfo = response.json()
    screenshots = [s['image'] for s in gameInfo['results'][:len]]
    return screenshots or ['']


def clean_description(text):
    """
    Trata la descripción obtenida de la API para obtener únicamente
    las partes que nos interesa.

    Paramétros:
    ------------
    text: str
        Descripción completa recibida desde la API.
    
    Returns:
    ---------
    str
        Descripción con las partes que nos resultan útiles e interesantes.
    """
    index = text.find("Español")
    if index != -1:
        return text[:index].strip()
    return text.strip()