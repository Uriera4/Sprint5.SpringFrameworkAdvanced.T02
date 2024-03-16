package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.dto;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TotalWinRate {

    public double calculaTotalWinRate (List<JugadorDTO> jugadores){
        return Math.round((jugadores.stream()
                .mapToDouble(JugadorDTO::getPorcentajeVictorias).sum()/jugadores.size())*100.0)/100.0;
    }

}
