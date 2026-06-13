package com.gamehub.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utilidad para generar y validar tokens JWT.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // Genera la clave de firma a partir del secret
    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Genera un token JWT para el usuario autenticado.
     * @param correo  correo del usuario (subject)
     * @param rol     rol del usuario (claim adicional)
     */
    public String generarToken(String correo, String rol) {
        return Jwts.builder()
                .setSubject(correo)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrae el correo (subject) del token.
     */
    public String obtenerCorreo(String token) {
        return parsear(token).getBody().getSubject();
    }

    /**
     * Extrae el rol del token.
     */
    public String obtenerRol(String token) {
        return (String) parsear(token).getBody().get("rol");
    }

    /**
     * Valida que el token sea correcto y no haya expirado.
     */
    public boolean validarToken(String token) {
        try {
            parsear(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> parsear(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);
    }
}