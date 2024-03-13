package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JugadorDTO {

    private Integer id;
    private String nombre;
    private List<JuegoDTO> tiradas;
    private double porcentajeVictorias;
    private String username;

    public void calculaPorcentajeVictorias (){
        porcentajeVictorias = tiradas.isEmpty() ? 0 : Math.round((calculoPorcentajeVictorias())*100.0)/100.0;
    }
    private double calculoPorcentajeVictorias (){
        return cantidadVictorias()*100/cantidadPartidas();
    }
    private double cantidadVictorias (){
        return (double) tiradas.stream().filter(JuegoDTO::isVictoria).count();
    }
    private int cantidadPartidas (){
        return tiradas.size();
    }

}
