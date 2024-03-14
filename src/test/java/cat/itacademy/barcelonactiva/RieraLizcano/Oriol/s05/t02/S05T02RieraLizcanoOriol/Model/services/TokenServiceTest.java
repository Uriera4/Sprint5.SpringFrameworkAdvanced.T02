package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.services;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.repositories.TokenRepository;
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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @Mock
    private TokenRepository tokenRepository;
    @InjectMocks
    private TokenService tokenService;

    private User user;
    private String token;

    @BeforeEach
    public void setUp(){
        tokenRepository.deleteAll();
        this.user = User.builder().username("oriol").password("oriol").build();
        this.token = Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Constante.tokenExpirationTime))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode("uisdh89hw89eh127he9jd1jd8j3f09r09irnvuans0jn2")))
                .compact();
    }
    @AfterEach
    public void reset(){
        this.user=null;
        this.token=null;
    }

    @Test
    public void TokenService_generateToken (){
        String token = tokenService.generateToken(this.user);
        assertThat(token).isNotNull();
    }
    @Test
    public void TokenService_isValid (){
        Token token = Token.builder().isLoggedOut(false).build();
        when(tokenRepository.findByToken(any(String.class))).thenReturn(Optional.ofNullable(token));
        boolean isValid = tokenService.isValid(this.token, this.user);
        assertThat(isValid).isTrue();
    }
    @Test
    public void TokenService_extractExpiration (){
        Date date = tokenService.extractExpiration(this.token);
        assertThat(date).isNotNull();
    }
    @Test
    public void TokenService_extractUsername (){
        String username = tokenService.extractUsername(this.token);
        assertThat(username).isNotNull();
    }

}
