package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.services;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.domain.Juego;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.domain.Jugador;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.dto.JuegoDTO;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.dto.JugadorDTO;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.repositories.JugadorRepository;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.User;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.utils.JugadorConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JugadorServiceTest {

    @Mock
    private JugadorRepository jugadorRepository;
    @Mock
    private JuegoService juegoService;
    @Mock
    private JugadorConverter jugadorConverter;
    @InjectMocks
    private JugadorService jugadorService;

    private User user;
    private Jugador jugador;

    @BeforeEach
    public void setUp(){
        this.user = User.builder().username("oriol").build();
        this.jugador = new Jugador();
        this.jugador.setNombre("Nombre");
        this.jugador.setTiradas(new ArrayList<>());
    }
    @AfterEach
    public void reset(){
        this.user=null;
        this.jugador=null;
    }

    @Test
    public void JugadorService_findJugador(){
        Integer id = 1;
        when(jugadorRepository.findById(id)).thenReturn(Optional.ofNullable(this.jugador));

        Jugador findJugador = jugadorService.findJugador(id);

        assertThat(findJugador).isNotNull();
    }
    @Test
    public void JugadorService_createJugador(){
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(this.jugador);

        assertAll(() -> jugadorService.createJugador(this.jugador, this.user));
    }
    @Test
    public void JugadorService_updateJugador(){
        Jugador jugadorUpdated = new Jugador();
        jugadorUpdated.setNombre("NuevoNombre");

        when(jugadorRepository.findById(jugadorUpdated.getId_jugador())).thenReturn(Optional.ofNullable(this.jugador));
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(this.jugador);

        assertAll(() -> jugadorService.updateJugador(jugadorUpdated));
    }
    @Test
    public void JugadorService_createTirada(){
        Integer id = 1;
        Juego juego = new Juego();
        when(jugadorRepository.findById(id)).thenReturn(Optional.ofNullable(this.jugador));
        when(juegoService.createJuego(this.jugador)).thenReturn(juego);
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(this.jugador);

        assertAll(() -> jugadorService.createTirada(id));
    }
    @Test
    public void JugadorService_deleteTiradas(){
        Integer id = 1;
        when(jugadorRepository.findById(id)).thenReturn(Optional.ofNullable(this.jugador));
        doNothing().when(juegoService).deleteJuegos(id);
        when(jugadorRepository.save(any(Jugador.class))).thenReturn(this.jugador);

        assertAll(() -> jugadorService.deleteTiradasJugador(id));
    }
    @Test
    public void JugadorService_getTiradasJugador(){
        Integer id = 1;
        List<JuegoDTO> juegosDTO = new ArrayList<>();
        juegosDTO.add(JuegoDTO.builder().build());
        JugadorDTO jugadorDTO = JugadorDTO.builder().nombre(jugador.getNombre()).tiradas(juegosDTO).build();

        when(jugadorRepository.findById(id)).thenReturn(Optional.ofNullable(this.jugador));
        when(jugadorConverter.convertToDTO(this.jugador)).thenReturn(jugadorDTO);

        List<JuegoDTO> tiradasDTO = jugadorService.getTiradasJugador(id);

        assertThat(tiradasDTO).isNotNull();
    }
    @Test
    public void JugadorService_getAllJugadores(){
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(this.jugador);
        JugadorDTO jugadorDTO = JugadorDTO.builder().nombre(jugador.getNombre()).build();

        when(jugadorRepository.findAll()).thenReturn(jugadores);
        when(jugadorConverter.convertToDTO(any(Jugador.class))).thenReturn(jugadorDTO);

        List<JugadorDTO> jugadoresDTO = jugadorService.getAllJugadores();

        assertThat(jugadoresDTO).isNotNull();
    }
    @Test
    public void JugadorService_getFirstJugador(){
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(this.jugador);
        JugadorDTO jugadorDTO = JugadorDTO.builder().nombre(jugador.getNombre()).build();

        when(jugadorRepository.findAll()).thenReturn(jugadores);
        when(jugadorConverter.convertToDTO(any(Jugador.class))).thenReturn(jugadorDTO);

        JugadorDTO firstJugador = jugadorService.getBestJugador();

        assertThat(firstJugador).isNotNull();
    }
    @Test
    public void JugadorService_deleteJugador(){
        Integer id = 1;
        when(jugadorRepository.findById(id)).thenReturn(Optional.ofNullable(this.jugador));
        doNothing().when(juegoService).deleteJuegos(id);
        doNothing().when(jugadorRepository).deleteById(id);

        assertAll(() -> jugadorService.deleteJugador(id));
    }

}
