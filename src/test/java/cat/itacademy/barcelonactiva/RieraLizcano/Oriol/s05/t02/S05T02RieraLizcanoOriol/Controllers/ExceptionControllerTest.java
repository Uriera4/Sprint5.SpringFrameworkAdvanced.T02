package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Controllers;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Exceptions.*;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.services.TokenService;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.services.UserDetailsServiceImpl;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.utils.Constante;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = ExceptionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ExceptionControllerTest {

    @Mock
    private Model model;
    @InjectMocks
    private ExceptionController exceptionController;
    @MockBean
    private TokenService tokenService;
    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @Test
    public void ExceptionController_handleRuntimeException (){
        RuntimeException exception = new RuntimeException("message");

        when(model.addAttribute(eq(Constante.mensaje), any(String.class))).thenReturn(null);

        String endpoint = exceptionController.handleRuntimeException(exception, this.model);

        verify(model).addAttribute(Constante.mensaje, exception.getMessage());
        assertThat(endpoint).isEqualTo(Constante.internalServerError);
    }
    @Test
    public void ExceptionController_handleNotFoundException (){
        JugadorNotFoundException exception = new JugadorNotFoundException("message");

        when(model.addAttribute(eq(Constante.mensaje), any(String.class))).thenReturn(null);

        String endpoint = exceptionController.handleNotFoundException(exception, this.model);

        verify(model).addAttribute(Constante.mensaje, exception.getMessage());
        assertThat(endpoint).isEqualTo(Constante.jugadorNotFoundError);
    }
    @Test
    public void ExceptionController_handleNameAlreadyExistsException (){
        NameAlreadyExistsException exception = new NameAlreadyExistsException("message");

        when(model.addAttribute(eq(Constante.mensaje), any(String.class))).thenReturn(null);

        String endpoint = exceptionController.handleNameAlreadyExistsException(exception, this.model);

        verify(model).addAttribute(Constante.mensaje, exception.getMessage());
        assertThat(endpoint).isEqualTo(Constante.nameAlreadyExistsError);
    }
    @Test
    public void ExceptionController_handleListaJugadoresVaciaException (){
        ListaJugadoresVaciaException exception = new ListaJugadoresVaciaException("message");

        when(model.addAttribute(eq(Constante.mensaje), any(String.class))).thenReturn(null);

        String endpoint = exceptionController.handleListaJugadoresVaciaException(exception, this.model);

        verify(model).addAttribute(Constante.mensaje, exception.getMessage());
        assertThat(endpoint).isEqualTo(Constante.listaJugadoresVaciaError);
    }
    @Test
    public void ExceptionController_handleUsernameNotFoundException (){
        UsernameNotFoundException exception = new UsernameNotFoundException("message");

        when(model.addAttribute(eq(Constante.mensaje), any(String.class))).thenReturn(null);

        String endpoint = exceptionController.handleUsernameNotFoundException(exception, this.model);

        verify(model).addAttribute(Constante.mensaje, exception.getMessage());
        assertThat(endpoint).isEqualTo(Constante.usernameNotFoundError);
    }
    @Test
    public void ExceptionController_handleAuthenticationException (){
        when(model.addAttribute(eq(Constante.mensaje), any(String.class))).thenReturn(null);

        String endpoint = exceptionController.handleAuthenticationException(this.model);

        verify(model).addAttribute(Constante.mensaje, Constante.loginUnsuccessful);
        assertThat(endpoint).isEqualTo(Constante.usernameNotFoundError);
    }
    @Test
    public void ExceptionController_handleUserAlreadyExistsException (){
        UserAlreadyExistsException exception = new UserAlreadyExistsException("message");

        when(model.addAttribute(eq(Constante.mensaje), any(String.class))).thenReturn(null);

        String endpoint = exceptionController.handleUserAlreadyExistsException(exception, this.model);

        verify(model).addAttribute(Constante.mensaje, exception.getMessage());
        assertThat(endpoint).isEqualTo(Constante.userAlreadyExistsError);
    }
    @Test
    public void ExceptionController_handleEmptyValuesException (){
        EmptyValuesException exception = new EmptyValuesException("message");

        when(model.addAttribute(eq(Constante.mensaje), any(String.class))).thenReturn(null);

        String endpoint = exceptionController.handleEmptyValuesException(exception, this.model);

        verify(model).addAttribute(Constante.mensaje, exception.getMessage());
        assertThat(endpoint).isEqualTo(Constante.emptyValuesError);
    }
}
