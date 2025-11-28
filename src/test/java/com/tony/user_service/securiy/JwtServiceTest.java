package com.tony.user_service.securiy;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils; 

import com.tony.user_service.security.JwtService;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("JwtService - Test JwtService")
public class JwtServiceTest {
    
    private static final String MOCK_SECRET = "w5fV6yJ2rL4nM8hG0oP7qD3rF9sE4tY0uA2bX1cZ3eD5f7h9j1kL3mO5pQ7rS9tU1vW3xY5zB7cD9eF0gH2iJ4kL6mN8pP0qR2sT4uV6wX8yZ0aC2dE4f6h8j";
    private static final long MOCK_EXPIRATION_MS = 60000; // 60 segundos
    private static final UUID MOCK_USER_ID = UUID.randomUUID();
    private static final String MOCK_USER_EMAIL = "test.user@example.com";

    // Injeta Mocks (se houvesse) e cria a instância real
    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
       
        ReflectionTestUtils.setField(jwtService, "jwtSecret", MOCK_SECRET);
        ReflectionTestUtils.setField(jwtService, "secretKey", MOCK_SECRET);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", MOCK_EXPIRATION_MS);
        ReflectionTestUtils.setField(jwtService, "cachedSigningKey", null);
    }

    private Key getSigningKeyForValidation() {
        byte[] keyBytes = Decoders.BASE64.decode(MOCK_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Test
    @DisplayName("1. generateToken: Deber generar un token valido")
    void generateToken_ShouldGenerateValidToken() {

        String token = jwtService.generateJwtToken(MOCK_USER_ID);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKeyForValidation())
                .build()
                .parseClaimsJws(token)
                .getBody();

         assertEquals(MOCK_USER_ID.toString(), claims.getSubject().toString(), "Subject debe id del usuario");
         assertTrue(claims.getExpiration().getTime() > new Date().getTime(), "El token no debe estar expirado");
    }

    @Test
    @DisplayName("2. Al llamar al metodo generateJwtToken debe generar token diferentes para cada entrada")
    void generateToken_ShouldGenerateUniqueTokens() {
        String token1 = jwtService.generateJwtToken(MOCK_USER_ID);
        UUID otheId =  UUID.randomUUID();
        String token2 = jwtService.generateJwtToken(otheId);
        assertNotEquals(token1.toString(), token2.toString(), "Token deben de ser diferentes");
    }

    @Test
    @DisplayName("3. generateJwtToken debe generar token jwt válido (HS512) com subject=id")
    void generateJwtToken_ShouldGenerateValidTokenHS512() {
        String token = jwtService.generateJwtToken(MOCK_USER_ID);
        assertNotNull(token);
        
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKeyForValidation())
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals(MOCK_USER_ID.toString(), claims.getSubject(), "Subject debe ser el id del usuário");
        assertTrue(claims.getExpiration().getTime() > new Date().getTime(), "El token no debe estar expirado");
    }
}