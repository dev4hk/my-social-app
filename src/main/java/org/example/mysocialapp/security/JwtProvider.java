package org.example.mysocialapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.mysocialapp.config.AppConfig;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {

    private static SecretKey key = Keys.hmacShaKeyFor(JwtContant.SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth) {

        return Jwts.builder()
                .setIssuer("JwtIssuer")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .claim("email", auth.getName())
                .signWith(key)
                .compact();


    }

    public static String getEmailFromJwtToken(String jwt) {

        System.out.println("Secret Key = "  + JwtContant.SECRET_KEY);

        jwt = jwt.substring(7);
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        String email = String.valueOf(claims.get("email"));
        return email;
    }
}
