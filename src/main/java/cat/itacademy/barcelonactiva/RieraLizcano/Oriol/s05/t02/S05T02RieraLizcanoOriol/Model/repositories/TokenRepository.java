package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.repositories;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

    Optional<Token> findByToken (String token);

}
