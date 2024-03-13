package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Random;

@Data
@NoArgsConstructor
@Entity
@Table(name = "partidas")
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_juego;
    @Column(name = "primer dado")
    private int dado1 = (new Random()).nextInt(6)+1;
    @Column(name = "segundo dado")
    private int dado2 = (new Random()).nextInt(6)+1;
    private boolean victoria = (dado1 + dado2 ==7);
    @ManyToOne
    @JoinColumn(name = "id_jugador")
    private Jugador jugador;

    @Builder(builderMethodName = "tiradaDeDados")
    public Juego (Jugador jugador){
        Random r = new Random();
        this.dado1 = r.nextInt(6)+1;
        this.dado2 = r.nextInt(6)+1;
        this.victoria = this.dado1 + this.dado2 == 7;
        this.jugador = jugador;
    }

}
