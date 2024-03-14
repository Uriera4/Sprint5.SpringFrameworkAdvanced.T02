package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.utils;

import org.springframework.stereotype.Component;

@Component
public class Constante {

    //URL'S Controller
    public static final String login = "/login";
    public static final String registro = "/register";
    public static final String logout = "/logout";
    public static final String admin = "/admin";
    public static final String mainUrl = "/players";
    public static final String crearJugador = "";
    public static final String listaJugadores = "/";
    public static final String mainPage = mainUrl+listaJugadores;
    public static final String actualizarJugador = "/{id}";
    public static final String redirecciona = "/redirect";
    public static final String games = "/games";
    public static final String playerPage = actualizarJugador + games;
    public static final String ranking = "/ranking/";
    public static final String rankingGlobal = "/rankingGlobal/";
    public static final String loser = "loser";
    public static final String winner = "winner";
    public static final String deleteJugador = "/delete/{id}";
    private static final String redirect = "redirect:";
    public static final String redirectMainPage = redirect + mainPage;
    public static final String redirectPlayerPage = redirect + mainUrl + playerPage;

    //Queries Repository
    public static final String querieDeleteByJugadorId = "DELETE FROM Juego j WHERE jugador.id_jugador = :id_jugador";

    // JWTService
    public static final String secretKeyValue = "uisdh89hw89eh127he9jd1jd8j3f09r09irnvuans0jn2";
    public static final int tokenExpirationTime = 24*60*60*1000;

    //Errores del sistema
    public static final String jugadorNotFound = "Este jugador no está registrado.";
    public static final String nombreEnUso = "Este nombre ya está en uso.";
    public static final String listaJugadoresVacia = "No se encuentran registros de jugadores en la base de datos.";
    public static final String userNotFound = "Usuario no encontrado.";
    public static final String userAlreadyExists = "Usuario ya registrado.";
    public static final String loginUnsuccessful = "El nombre de usuario o contraseña no són correctos";
    public static final String emptyValues = "No pueden haber campos vacíos en el formulario";

    //Vistas Thymeleaf
    public static final String loginUser = "loginUser";
    public static final String registroUser = "registroUser";
    public static final String createJugador = "createJugador";
    public static final String updateJugador = "updateJugador";
    public static final String showJugador = "showJugador";
    public static final String showJugadores = "showJugadores";
    public static final String showRanking = "showRanking";
    public static final String showLoser = "showLoser";
    public static final String showWinner = "showWinner";
    public static final String internalServerError = "internalServerError";
    public static final String jugadorNotFoundError = "jugadorNotFoundError";
    public static final String nameAlreadyExistsError = "nameAlreadyExistsError";
    public static final String listaJugadoresVaciaError = "listaJugadoresVaciaError";
    public static final String usernameNotFoundError = "usernameNotFoundError";
    public static final String userAlreadyExistsError = "userAlreadyExistsError";
    public static final String emptyValuesError = "emptyValuesError";

    //Parámetros Controller
    public static final String id = "id";
    public static final String jugador = "jugador";
    public static final String jugadores = "jugadores";
    public static final String tiradas = "tiradas";
    public static final String winrate = "winrate";
    public static final String mensaje = "mensaje";
    public static final String usuario = "usuario";
    public static final String token = "token";

}
