package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.services;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.domain.Juego;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.domain.Jugador;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.repositories.JuegoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JuegoServiceTest {

    @Mock
    private JuegoRepository juegoRepository;
    @InjectMocks
    private JuegoService juegoService;

    private Jugador jugador;
    private Juego juego;

    @BeforeEach
    public void setUp(){
        juegoRepository.deleteAll();
        this.jugador = new Jugador();
        this.jugador.setNombre("Nombre");
        this.jugador.setTiradas(new ArrayList<>());
        this.juego = new Juego(this.jugador);
    }
    @AfterEach
    public void reset(){
        this.jugador=null;
        this.juego=null;
    }

    @Test
    public void JuegoService_createJuego(){
        when(juegoRepository.save(any(Juego.class))).thenReturn(this.juego);

        assertAll(() -> juegoService.createJuego(this.jugador));
    }
    @Test
    public void JuegoService_deleteJuegos(){
        Integer id = 1;
        assertAll(() -> juegoRepository.deleteByJugadorId(id));

    }

}
