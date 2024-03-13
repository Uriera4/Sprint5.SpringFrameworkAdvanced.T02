package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.utils;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.domain.Jugador;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.dto.JuegoDTO;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.dto.JugadorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JugadorConverter {

    @Autowired
    JuegoConverter juegoConverter;

    public JugadorDTO convertToDTO (Jugador jugador){
        JugadorDTO jugadorDTO = null;
        if (jugador!=null) jugadorDTO = createDTO(jugador);
        if (jugadorDTO!=null) jugadorDTO.calculaPorcentajeVictorias();
        return jugadorDTO;
    }
    private JugadorDTO createDTO (Jugador jugador){
        List<JuegoDTO> tiradas = jugador.getTiradas().stream().map(juego -> juegoConverter.convertToDTO(juego)).toList();
        return JugadorDTO.builder()
                .id(jugador.getId_jugador())
                .nombre(jugador.getNombre())
                .tiradas(tiradas)
                .username(jugador.getUsername())
                .build();
    }

}
