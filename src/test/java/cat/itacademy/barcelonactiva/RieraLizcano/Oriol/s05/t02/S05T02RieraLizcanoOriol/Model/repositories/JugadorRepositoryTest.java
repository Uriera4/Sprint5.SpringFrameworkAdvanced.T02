package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.repositories;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.domain.Jugador;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
public class JugadorRepositoryTest {

    @Autowired
    private JugadorRepository jugadorRepository;
    private Jugador jugador;
    private final String nombre = "Nombre";

    @BeforeEach
    public void setUp(){
        jugadorRepository.deleteAll();
        this.jugador = new Jugador();
        jugador.setNombre(this.nombre);
    }
    @AfterEach
    public void reset(){
        this.jugador=null;
    }

    @Test
    public void JugadorRepository_saveJugador(){
        Jugador savedJugador = jugadorRepository.save(this.jugador);

        assertThat(savedJugador).isNotNull();
        assertThat(savedJugador.getId_jugador()).isGreaterThan(0);
        assertThat(savedJugador.getNombre()).isEqualTo(this.nombre);
    }
    @Test
    public void JugadorRepository_findAllAllJugadores(){
        Jugador jugador2 = new Jugador();

        jugadorRepository.save(this.jugador);
        jugadorRepository.save(jugador2);
        List<Jugador> jugadorList = jugadorRepository.findAll();

        assertThat(jugadorList).isNotNull();
        assertThat(jugadorList.size()).isEqualTo(2);
    }
    @Test
    public void JugadorRepository_findById(){
        jugadorRepository.save(this.jugador);
        Jugador findJugador = jugadorRepository.findById(this.jugador.getId_jugador()).get();

        assertThat(findJugador).isNotNull();
    }
    @Test
    public void JugadorRepository_findByNombre(){
        jugadorRepository.save(this.jugador);
        Jugador findJugador = jugadorRepository.findByNombre(this.jugador.getNombre()).get();

        assertThat(findJugador).isNotNull();
    }
    @Test
    public void JugadorRepository_deleteById(){
        jugadorRepository.save(this.jugador);
        assertThat(jugadorRepository.findByNombre(this.jugador.getNombre())).get().isEqualTo(this.jugador);

        jugadorRepository.deleteById(this.jugador.getId_jugador());
        assertThat(jugadorRepository.findByNombre(this.jugador.getNombre())).isEmpty();
    }
    @Test
    public void JugadorRepository_updateJugador(){
        jugadorRepository.save(this.jugador);
        Jugador findJugador = jugadorRepository.findById(this.jugador.getId_jugador()).get();
        findJugador.setNombre("NuevoNombre");
        Jugador updatedJugador = jugadorRepository.save(findJugador);

        assertThat(updatedJugador.getNombre()).isNotEqualTo(this.nombre);
        assertThat(updatedJugador).isEqualTo(this.jugador);
    }

}
