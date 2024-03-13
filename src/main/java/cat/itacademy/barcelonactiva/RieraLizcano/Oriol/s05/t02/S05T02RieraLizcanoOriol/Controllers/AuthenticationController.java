package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Controllers;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.User;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.services.AuthenticationService;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.utils.Constante;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping({"/",Constante.login})
    public String showLoginFormualry(Model model){
        model.addAttribute(Constante.usuario, new User());
        return Constante.loginUser;
    }
    @Operation(summary = "Iniciar sesión", description = "Sirve validar sesión de un usuario ya existente")
    @PostMapping(Constante.login)
    public String login(@ModelAttribute(Constante.usuario) User user, HttpServletResponse response){
        String token = authenticationService.authenticate(user);
        Cookie cookie = new Cookie(Constante.token, token);
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
        return Constante.redirectMainPage;
    }

}
