package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.repositories;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.domain.Juego;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.domain.Jugador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
public class JuegoRepositoryTest {

    @Autowired
    private JugadorRepository jugadorRepository;
    @Autowired
    private JuegoRepository juegoRepository;
    @BeforeEach
    public void reset(){
        jugadorRepository.deleteAll();
        juegoRepository.deleteAll();
    }

    @Test
    public void JuegoRepository_deleteByJugadorID(){
        Jugador jugador = new Jugador();
        Juego juego = new Juego(jugador);

        jugadorRepository.save(jugador);
        juegoRepository.save(juego);
        juegoRepository.deleteByJugadorId(jugador.getId_jugador());

        assertThat(juegoRepository.findAll()).isEmpty();
    }

}
