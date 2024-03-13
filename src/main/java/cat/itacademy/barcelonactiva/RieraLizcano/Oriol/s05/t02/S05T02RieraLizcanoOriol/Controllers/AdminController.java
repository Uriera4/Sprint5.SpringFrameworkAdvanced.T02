package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Controllers;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.services.JugadorService;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.utils.Constante;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(Constante.admin)
public class AdminController {

    @Autowired
    JugadorService jugadorService;

    @Operation(summary = "Eliminar jugador", description = "Sirve para elminar un jugador del sistema")
    @DeleteMapping(Constante.deleteJugador)
    public String deleteJugador (@PathVariable(Constante.id) Integer id){
        jugadorService.deleteJugador(id);
        return Constante.redirectMainPage;
    }

}
