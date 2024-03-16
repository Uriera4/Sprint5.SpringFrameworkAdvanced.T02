package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Controllers;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.domain.Jugador;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.dto.JuegoDTO;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.dto.JugadorDTO;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.dto.TotalWinRate;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.Role;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.User;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.services.JugadorService;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.services.TokenService;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.services.UserDetailsServiceImpl;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.utils.Constante;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = JugadorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class JugadorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JugadorService jugadorService;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private TotalWinRate totalWinRate;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    private User user;
    private Jugador jugador;
    private Principal principal;

    @BeforeEach
    public void setUp(){
        this.user = User.builder().name("Oriol").username("oriol").password("oriol").role(Role.USER).build();
        this.principal = new UsernamePasswordAuthenticationToken(this.user.getUsername(), this.user.getPassword());
        this.jugador = new Jugador();
        this.jugador.setId_jugador(1);
        this.jugador.setNombre("Oriol");
        this.jugador.setUsername(user.getUsername());
    }
    @AfterEach
    public void reset(){
        this.user=null;
        this.jugador=null;
    }

    @Test
    public void JugadorController_createJugador () throws Exception {
        doNothing().when(jugadorService).createJugador(any(Jugador.class), any(User.class));

        mockMvc.perform(post(Constante.mainUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(principal)
                        .flashAttr(Constante.jugador, this.jugador)
                        .flashAttr(Constante.usuario, this.user))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(Constante.mainPage));
    }
    @Test
    public void JugadorController_updateJugador () throws Exception {
        doNothing().when(jugadorService).updateJugador(any(Jugador.class));

        mockMvc.perform(put(Constante.mainUrl+"/"+this.jugador.getId_jugador())
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr(Constante.jugador, this.jugador))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(Constante.mainUrl+"/"+this.jugador.getId_jugador()+Constante.games));
    }
    @Test
    public void JugadorController_getJugador () throws Exception {
        List<JugadorDTO> jugadoresDTO = new ArrayList<>();
        jugadoresDTO.add(JugadorDTO.builder().id(this.jugador.getId_jugador())
                        .nombre(this.jugador.getNombre())
                        .username(this.jugador.getUsername())
                .build());
        when(jugadorService.getAllJugadores()).thenReturn(jugadoresDTO);
        when(jugadorService.findJugador(this.jugador.getId_jugador())).thenReturn(this.jugador);

        mockMvc.perform(get(Constante.mainUrl+"/"+this.jugador.getId_jugador()+Constante.games)
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name(Constante.showJugador));
    }
    @Test
    public void JugadorController_createTirada () throws Exception {
        doNothing().when(jugadorService).createTirada(this.jugador.getId_jugador());

        mockMvc.perform(post(Constante.mainUrl + "/"+this.jugador.getId_jugador()+Constante.games)
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr(Constante.id, this.jugador.getId_jugador()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(Constante.mainUrl+"/"+this.jugador.getId_jugador()+Constante.games));
    }
    @Test
    public void JugadorController_deleteTiradas () throws Exception {
        doNothing().when(jugadorService).deleteJugador(this.jugador.getId_jugador());

        mockMvc.perform(delete(Constante.mainUrl + "/"+this.jugador.getId_jugador()+Constante.games)
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr(Constante.id, this.jugador.getId_jugador()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(Constante.mainUrl+"/"+this.jugador.getId_jugador()+Constante.games));
    }
    @Test
    public void getJugadores () throws Exception {
        List<JugadorDTO> jugadoresDTO = new ArrayList<>();
        List<JuegoDTO> tiradas = new ArrayList<>();
        tiradas.add(JuegoDTO.builder().build());
        jugadoresDTO.add(JugadorDTO.builder().id(this.jugador.getId_jugador())
                        .nombre(this.jugador.getNombre())
                        .tiradas(tiradas)
                        .username(this.jugador.getUsername())
                        .build());

        when(jugadorService.getAllJugadores()).thenReturn(jugadoresDTO);
        when(totalWinRate.calculaTotalWinRate(any(List.class))).thenReturn(100.0);

        mockMvc.perform(get(Constante.mainPage)
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name(Constante.showJugadores));
    }

}
