package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Controllers;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Exceptions.*;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.utils.Constante;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException (RuntimeException e, Model model){
        model.addAttribute(Constante.mensaje, e.getMessage());
        return Constante.internalServerError;
    }
    @ExceptionHandler(JugadorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException (JugadorNotFoundException e, Model model){
        model.addAttribute(Constante.mensaje, e.getMessage());
        return Constante.jugadorNotFoundError;
    }
    @ExceptionHandler(NameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleNameAlreadyExistsException (NameAlreadyExistsException e, Model model){
        model.addAttribute(Constante.mensaje, e.getMessage());
        return Constante.nameAlreadyExistsError;
    }
    @ExceptionHandler(ListaJugadoresVaciaException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String handleListaJugadoresVaciaException (ListaJugadoresVaciaException e, Model model){
        model.addAttribute(Constante.mensaje, e.getMessage());
        return Constante.listaJugadoresVaciaError;
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUsernameNotFoundException (UsernameNotFoundException e, Model model){
        model.addAttribute(Constante.mensaje, e.getMessage());
        return Constante.usernameNotFoundError;
    }
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleAuthenticationException(Model model){
        model.addAttribute(Constante.mensaje, Constante.loginUnsuccessful);
        return Constante.usernameNotFoundError;
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserAlreadyExistsException(UserAlreadyExistsException e, Model model){
        model.addAttribute(Constante.mensaje, e.getMessage());
        return Constante.userAlreadyExistsError;
    }
    @ExceptionHandler(EmptyValuesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmptyValuesException (EmptyValuesException e, Model model){
        model.addAttribute(Constante.mensaje, e.getMessage());
        return Constante.emptyValuesError;
    }

}
