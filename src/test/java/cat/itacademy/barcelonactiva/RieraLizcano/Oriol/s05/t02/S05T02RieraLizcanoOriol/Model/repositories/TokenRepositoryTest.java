package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.repositories;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.Token;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenRepositoryTest {

    @Mock
    private TokenRepository tokenRepository;

    @Test
    public void TokenRepository_findByToken(){
        String jwtToken = "token";
        User user = User.builder().username("oriol").password("oriol").build();
        Token token = Token.builder().token(jwtToken).isLoggedOut(false).username(user.getUsername()).build();

        when(tokenRepository.findByToken(jwtToken)).thenReturn(Optional.of(token));

        Optional<Token> foundToken = tokenRepository.findByToken(jwtToken);

        assertThat(foundToken).isPresent();
        assertThat(foundToken.get().getToken()).isEqualTo(jwtToken);
    }

}
