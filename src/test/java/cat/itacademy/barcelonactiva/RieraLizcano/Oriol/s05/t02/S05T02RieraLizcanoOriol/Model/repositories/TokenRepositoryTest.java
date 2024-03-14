package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.repositories;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.Token;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
public class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;
    @BeforeEach
    public void reset(){
        tokenRepository.deleteAll();
    }

    @Test
    public void TokenRepository_findByToken(){
        String jwtToken = "token";
        User user = User.builder().username("oriol").password("oriol").build();
        Token token = Token.builder().token(jwtToken).isLoggedOut(false).username(user.getUsername()).build();

        tokenRepository.save(token);

        assertThat(tokenRepository.findByToken(jwtToken)).isPresent();
    }

}
