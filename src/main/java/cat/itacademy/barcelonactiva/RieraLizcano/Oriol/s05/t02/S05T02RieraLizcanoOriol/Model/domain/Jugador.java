package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "jugadores")
public class Jugador {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id_jugador;
    private String nombre;
    @Column(name = "fecha de registro")
    private LocalDateTime fechaRegistro;
    @OneToMany(mappedBy = "jugador", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Juego> tiradas;
    private String username;

    @Builder
    public Jugador (){
        this.nombre = "ANÓNIMO";
        this.fechaRegistro = LocalDateTime.now();
        this.tiradas = new ArrayList<>();
    }

}
