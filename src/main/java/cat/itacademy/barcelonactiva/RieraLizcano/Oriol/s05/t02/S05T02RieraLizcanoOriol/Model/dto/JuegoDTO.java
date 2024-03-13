package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JuegoDTO {

    private int dado1;
    private int dado2;
    private boolean victoria;

}
