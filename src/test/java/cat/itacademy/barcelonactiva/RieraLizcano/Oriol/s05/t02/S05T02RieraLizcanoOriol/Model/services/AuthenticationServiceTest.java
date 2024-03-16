package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.services;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.repositories.TokenRepository;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.repositories.UserRepository;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.Token;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.User;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.utils.Constante;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    TokenService tokenService;
    @Mock
    TokenRepository tokenRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    AuthenticationManager authenticationManager;
    @InjectMocks
    AuthenticationService authenticationService;

    private User user;
    private String jwtToken;

    @BeforeEach
    public void setUp(){
        this.user = User.builder().name("Oriol").username("oriol").password("oriol").tokens(new ArrayList<>()).build();
        this.jwtToken = Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Constante.tokenExpirationTime))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode("uisdh89hw89eh127he9jd1jd8j3f09r09irnvuans0jn2")))
                .compact();
        this.user.addToken(Token.builder().token(this.jwtToken).build());
    }
    @AfterEach
    public void reset(){
        this.user=null;
        this.jwtToken=null;
    }

    @Test
    public void AuthenticationService_authenticate (){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(this.user.getUsername(), this.user.getPassword());
        List<Token> tokenList = Mockito.mock(List.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(usernamePasswordAuthenticationToken);
        when(userRepository.findByUsername(this.user.getUsername())).thenReturn(Optional.ofNullable(this.user));

        when(tokenService.generateToken(this.user)).thenReturn(this.jwtToken);
        when(tokenRepository.findByToken(this.jwtToken)).thenReturn(Optional.of(new Token()));
        when(tokenRepository.saveAll(any(List.class))).thenReturn(tokenList);
        when(userRepository.save(any(User.class))).thenReturn(this.user);
        when(tokenRepository.save(any(Token.class))).thenReturn(new Token());

        String token = authenticationService.authenticate(this.user);

        assertThat(token).isNotNull();
    }
    @Test
    public void AuthenticationService_registerUserRequest (){
        when(userRepository.findByUsername(this.user.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(this.user.getPassword())).thenReturn(this.user.getPassword());

        when(tokenService.generateToken(any(User.class))).thenReturn(this.jwtToken);
        when(userRepository.save(any(User.class))).thenReturn(this.user);
        when(tokenRepository.save(any(Token.class))).thenReturn(new Token());

        String token = authenticationService.registerUserRequest(this.user);
        assertThat(token).isNotNull();
    }

}
