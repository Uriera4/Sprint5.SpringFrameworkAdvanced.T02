package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.repositories;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.domain.Jugador;
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
    @BeforeEach
    public void reset(){
        jugadorRepository.deleteAll();
    }

    @Test
    public void JugadorRepository_saveJugador(){
        String nombre = "Nombre";
        Jugador jugador = new Jugador();
        jugador.setNombre(nombre);

        Jugador savedJugador = jugadorRepository.save(jugador);

        assertThat(savedJugador).isNotNull();
        assertThat(savedJugador.getId_jugador()).isGreaterThan(0);
        assertThat(savedJugador.getNombre()).isEqualTo(nombre);
    }
    @Test
    public void JugadorRepository_findAllAllJugadores(){
        Jugador jugador = new Jugador();
        Jugador jugador2 = new Jugador();

        jugadorRepository.save(jugador);
        jugadorRepository.save(jugador2);
        List<Jugador> jugadorList = jugadorRepository.findAll();

        assertThat(jugadorList).isNotNull();
        assertThat(jugadorList.size()).isEqualTo(2);
    }
    @Test
    public void JugadorRepository_findById(){
        Jugador jugador = new Jugador();

        jugadorRepository.save(jugador);
        Jugador findJugador = jugadorRepository.findById(jugador.getId_jugador()).get();

        assertThat(findJugador).isNotNull();
    }
    @Test
    public void JugadorRepository_findByNombre(){
        String nombre = "Nombre";
        Jugador jugador = new Jugador();
        jugador.setNombre(nombre);

        jugadorRepository.save(jugador);
        Jugador findJugador = jugadorRepository.findByNombre(jugador.getNombre()).get();

        assertThat(findJugador).isNotNull();
    }
    @Test
    public void JugadorRepository_deleteById(){
        String nombre = "Nombre";
        Jugador jugador = new Jugador();
        jugador.setNombre(nombre);

        jugadorRepository.save(jugador);
        assertThat(jugadorRepository.findByNombre(jugador.getNombre())).get().isEqualTo(jugador);

        jugadorRepository.deleteById(jugador.getId_jugador());
        assertThat(jugadorRepository.findByNombre(jugador.getNombre())).isEmpty();
    }
    @Test
    public void JugadorRepository_updateJugador(){
        String nombre = "Nombre";
        Jugador jugador = new Jugador();
        jugador.setNombre(nombre);

        jugadorRepository.save(jugador);
        Jugador findJugador = jugadorRepository.findById(jugador.getId_jugador()).get();
        findJugador.setNombre("NuevoNombre");
        Jugador updatedJugador = jugadorRepository.save(findJugador);

        assertThat(updatedJugador.getNombre()).isNotEqualTo(nombre);
        assertThat(updatedJugador).isEqualTo(jugador);
    }


}
