package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Controllers;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Exceptions.JugadorNotFoundException;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.domain.Jugador;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.dto.JugadorDTO;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.dto.TotalWinRate;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.User;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.services.JugadorService;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.utils.Constante;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(Constante.mainUrl)
public class JugadorController {

    @Autowired
    JugadorService jugadorService;
    @Autowired
    TotalWinRate totalWinRate;

    @GetMapping(Constante.crearJugador)
    public String showRegisterFormulary (@ModelAttribute(Constante.usuario) User user, Model model){
        model.addAttribute(Constante.usuario, user);
        model.addAttribute(Constante.jugador, new Jugador());
        return Constante.createJugador;
    }
    @Operation(summary = "Crear nuevo jugador", description = "Sirve para añadir un jugador nuevo al sistema")
    @PostMapping(Constante.crearJugador)
    public String createJugador (Principal p, @ModelAttribute(Constante.jugador) Jugador jugador, @ModelAttribute(Constante.usuario) User user){
        user.setUsername(p.getName());
        jugadorService.createJugador(jugador, user);
        return Constante.redirectMainPage;
    }
    @GetMapping(Constante.actualizarJugador)
    public String showUpdateFormulary (@ModelAttribute(Constante.id) Integer id, Model model){
        model.addAttribute(Constante.jugador, jugadorService.findJugador(id));
        return Constante.updateJugador;
    }
    @Operation(summary = "Modificar nombre jugador", description = "Sirve para modificar el nombre de un jugador")
    @PutMapping(Constante.actualizarJugador)
    public String updateJugador (@PathVariable(Constante.id) Integer id, @ModelAttribute(Constante.jugador) Jugador jugador){
        jugador.setId_jugador(id);
        jugadorService.updateJugador(jugador);
        return Constante.redirectPlayerPage;
    }
    @PostMapping(Constante.redirecciona)
    public String redirectToGames(@RequestParam(Constante.id) String id) {
        String endpoint;
        if (id.isEmpty()) endpoint = Constante.redirectMainPage;
        else endpoint = Constante.redirectMainPage + id + Constante.games;
        return endpoint;
    }
    @Operation(summary = "Mostrar jugador", description = "Sirve para mostrar un jugador del usuario activo")
    @GetMapping(Constante.playerPage)
    public String getJugador (Principal p, @PathVariable(Constante.id) Integer id, Model model){
        if (id>jugadorService.getAllJugadores().getLast().getId() || id==0 || !jugadorService.findJugador(id).getUsername().equalsIgnoreCase(p.getName())) {
            throw new JugadorNotFoundException(Constante.jugadorNotFound);
        }
        model.addAttribute(Constante.id,id);
        model.addAttribute(Constante.jugador, jugadorService.findJugador(id));
        model.addAttribute(Constante.tiradas, jugadorService.getTiradasJugador(id));
        return Constante.showJugador;
    }
    @Operation(summary = "Realizar nueva tirada", description = "Sirve para añadir una tirada nueva a un jugador del usuario activo")
    @PostMapping(Constante.playerPage)
    public String createTirada (@ModelAttribute(Constante.id) Integer id){
        jugadorService.createTirada(id);
        return Constante.redirectPlayerPage;
    }
    @Operation(summary = "Eliminar tiradas", description = "Sirve para eliminar las tiradas de un jugador del usuario activo")
    @DeleteMapping(Constante.playerPage)
    public String deleteTiradasJugador (@ModelAttribute(Constante.id) Integer id){
        jugadorService.deleteTiradasJugador(id);
        return Constante.redirectPlayerPage;
    }
    @Operation(summary = "Mostrar jugadores", description = "Sirve para mostrar los jugadores del usuario activo")
    @GetMapping(Constante.listaJugadores)
    public String getJugadores (Principal p, Model model){
        List<JugadorDTO> jugadores = jugadorService.getAllJugadores().stream().filter(jugador -> jugador.getUsername().equals(p.getName())).toList();
        model.addAttribute(Constante.winrate, totalWinRate.calculaTotalWinRate(jugadores));
        model.addAttribute(Constante.jugadores, jugadores);
        return Constante.showJugadores;
    }
    @Operation(summary = "Mostrar ranking", description = "Sirve para mostrar el ranking de jugadores del usuario activo")
    @GetMapping(Constante.ranking)
    public String getRanking (Principal p, Model model){
        List<JugadorDTO> jugadores = jugadorService.getRankingJugadores().stream().filter(jugador -> jugador.getUsername().equals(p.getName())).toList();
        model.addAttribute(Constante.winrate, totalWinRate.calculaTotalWinRate(jugadores));
        model.addAttribute(Constante.jugadores, jugadores);
        return Constante.showRanking;
    }
    @Operation(summary = "Mostrar ranking global", description = "Sirve para mostrar el ranking de todos los jugadores del sistema")
    @GetMapping(Constante.rankingGlobal)
    public String getRankingGlobal (Principal p, Model model){
        List<JugadorDTO> jugadores = jugadorService.getRankingJugadores();
        model.addAttribute(Constante.winrate, totalWinRate.calculaTotalWinRate(jugadores));
        model.addAttribute(Constante.jugadores, jugadores);
        return Constante.showRanking;
    }
    @Operation(summary = "Mostrar perdedor", description = "Sirve para mostrar el jugador con peor puntuación del sistema")
    @GetMapping(Constante.ranking + Constante.loser)
    public String getLoser (Model model){
        model.addAttribute(Constante.loser, jugadorService.getWorstJugador());
        return Constante.showLoser;
    }
    @Operation(summary = "Mostrar ganador", description = "Sirve para mostrar el jugador con mejor puntuación del sistema")
    @GetMapping(Constante.ranking + Constante.winner)
    public String getWinner (Model model){
        model.addAttribute(Constante.winner, jugadorService.getBestJugador());
        return Constante.showWinner;
    }

}
