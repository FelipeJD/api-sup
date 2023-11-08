package br.com.sup.api.service;

import br.com.sup.api.models.Credencial;
import br.com.sup.api.models.Token;
import br.com.sup.api.models.Usuario;
import br.com.sup.api.repository.UsuarioRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Value("${jwt.secret}")
    String secret;

    public Token generateToken(@Valid Credencial credencial, Long userId) {
        Algorithm alg = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withSubject(credencial.email())
                .withIssuer("Sup")
                .withExpiresAt(Instant.now().plus(1, ChronoUnit.HOURS))
                .sign(alg);
        return new Token(token, "JWT", "Bearer", userId);
    }

    public Usuario getValidateUser(String token) {
        Algorithm alg = Algorithm.HMAC256(secret);
        var email = JWT.require(alg)
                .withIssuer("Sup")
                .build()
                .verify(token)
                .getSubject();

        return usuarioRepository.findByEmail(email).orElseThrow(() -> new JWTVerificationException("Usuario invalido"));
    }
}
