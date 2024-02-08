package com.billbalancers.service;

import com.billbalancers.authenticatorapi.model.User;
import com.billbalancers.authenticatorapi.model.UserLogin;
import com.billbalancers.service.config.JWTGeneratorInterface;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;


@Service
public class JWTGeneratorService implements JWTGeneratorInterface {


    private static final String SECRET_KEY = "yourSecretKeyrebgvwvkljbvnqeovjbqehvqovbqoehkvjvrbiqubeviwuovbwouebwovjwov";
    @Override
    public String generateToken(UserLogin userLogin) {
        Date now = new Date();

        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .claim("email",userLogin.getEmail())
                .claim("password",userLogin.getPassword())
                .id(UUID.randomUUID().toString())
                .issuedAt(Date.from(now.toInstant()))
                .expiration(Date.from(Instant.now().plus(10L, ChronoUnit.HOURS)))
                .signWith(key)
                .compact();

    }

    @Override
    public Claims parseJwt(String jwtString) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

            Jws<Claims> claimsJws = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtString);

            return claimsJws.getPayload();
        }
        catch (ExpiredJwtException e){
            throw new ExpiredJwtException(null,e.getClaims(),"JWT Token Expired",e.getCause());
        }

    }
}
