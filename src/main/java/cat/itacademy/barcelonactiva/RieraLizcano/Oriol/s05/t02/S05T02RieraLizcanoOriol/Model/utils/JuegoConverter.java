package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.utils;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.domain.Juego;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.dto.JuegoDTO;
import org.springframework.stereotype.Component;

@Component
public class JuegoConverter {

    public JuegoDTO convertToDTO (Juego juego){
        JuegoDTO juegoDTO = null;
        if (juego!=null) juegoDTO = createDTO(juego);
        return juegoDTO;
    }
    private JuegoDTO createDTO (Juego juego){
        return JuegoDTO.builder()
                .dado1(juego.getDado1())
                .dado2(juego.getDado2())
                .victoria(juego.isVictoria())
                .build();
    }

}
