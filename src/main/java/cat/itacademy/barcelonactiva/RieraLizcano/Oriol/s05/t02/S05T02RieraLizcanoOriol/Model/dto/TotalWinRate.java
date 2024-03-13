package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.dto;

import java.util.List;

public class TotalWinRate {

    public static double calculaTotalWinRate (List<JugadorDTO> jugadores){
        return Math.round((jugadores.stream()
                .mapToDouble(JugadorDTO::getPorcentajeVictorias).sum()/jugadores.size())*100.0)/100.0;
    }

}
