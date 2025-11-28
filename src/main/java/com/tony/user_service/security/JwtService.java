package com.tony.user_service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {
    
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpiration;

    private Key cachedSigningKey = null;


     public String generateJwtToken(UUID userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        Key key = getSigningKey();
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512) 
                .compact();
      }

     private Key getSigningKey() {
        if (cachedSigningKey == null) {
            try {
                byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
                cachedSigningKey = Keys.hmacShaKeyFor(keyBytes);
            } catch (Exception e) {
                System.err.println("ERROR FATAL: " + e.getMessage());
                throw new RuntimeException("Falla inicializacion de llava jwt.", e);
            }
        }
        return cachedSigningKey;
    }
}
