package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.services;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Exceptions.EmptyValuesException;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Exceptions.UserAlreadyExistsException;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.repositories.TokenRepository;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.repositories.UserRepository;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.Role;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.Token;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.User;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.utils.Constante;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public String authenticate (User request){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException(Constante.userNotFound));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return updateUserAndToken(user).getTokens().getLast();
    }
    public String registerUserRequest(User request){
        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new UserAlreadyExistsException(Constante.userAlreadyExists);
        }
        User user = createUser(request);
        return updateUserAndToken(user).getTokens().getLast();
    }
    private User createUser (User request){
        if (request.getName().isEmpty() || request.getUsername().isEmpty() || request.getPassword().isEmpty()){
            throw new EmptyValuesException(Constante.emptyValues);
        }
        return User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .tokens(new ArrayList<>())
                .build();
    }
    private User updateUserAndToken(User user){
        String jwtToken = tokenService.generateToken(user);
        Token token = createToken(jwtToken, user);
        revokeAllTokenByUser(user);
        user.addToken(token);
        userRepository.save(user);
        tokenRepository.save(token);
        return user;
    }
    private Token createToken(String jwtToken, User user) {
        return Token.builder()
                .token(jwtToken)
                .isLoggedOut(false)
                .username(user.getUsername())
                .build();
    }
    public void revokeAllTokenByUser (User user){
        List<String> tokens = user.getTokens();
        if (!tokens.isEmpty()){
            List<Token> tokenList = new ArrayList<>();
            for (String token : tokens){
                tokenList.add(tokenRepository.findByToken(token).get());
            }
            tokenList.forEach(t -> t.setLoggedOut(true));
            tokenRepository.saveAll(tokenList);
        }
    }

}
