package cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.services;

import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.repositories.TokenRepository;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.security.User;
import cat.itacademy.barcelonactiva.RieraLizcano.Oriol.s05.t02.S05T02RieraLizcanoOriol.Model.utils.Constante;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public String generateToken(User user){
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Constante.tokenExpirationTime))
                .signWith(getSigningKey())
                .compact();
    }
    public boolean isValid (String token, UserDetails user){
        String username = extractUsername(token);
        boolean isTokenValid = tokenRepository.findByToken(token)
                                .map(t -> !t.isLoggedOut())
                                .orElse(false);
        return username.equals(user.getUsername()) && isTokenValid && !isTokenExpired(token);
    }
    private boolean isTokenExpired (String token){
        return extractExpiration(token).before(new Date());
    }
    public Date extractExpiration (String token){
        return extractClaim(token, Claims::getExpiration);
    }
    public String extractUsername (String token){
        return extractClaim(token, Claims::getSubject);
    }
    private <T> T extractClaim (String token, Function<Claims, T> resolver){
        final Claims claims = extractAllClaims((token));
        return resolver.apply(claims);
    }
    private Claims extractAllClaims (String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private SecretKey getSigningKey(){
        byte[] keyByte = Decoders.BASE64URL.decode(Constante.secretKeyValue);
        return Keys.hmacShaKeyFor(keyByte);
    }

}
