package com.RetourFacile.services;

import com.RetourFacile.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    private final SecretKey key;
    private final long jwtExpiration;

    public JwtService(@Value("${app.secret-key}") String secretKey,
                      @Value("${jwt.expiration-time}") long jwtExpiration) {
        if (secretKey.length() < 32) {
            throw new IllegalArgumentException("La clé secrète doit contenir au moins 32 caractères !");
        }
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.jwtExpiration = jwtExpiration;
    }

    /**
     * Génère un JWT pour un utilisateur donné
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole().name());
        claims.put("email", user.getEmail()); // Optionnel : ajouter l'e-mail comme claim supplémentaire

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getTrackingId().toString()) // Utiliser le trackingId comme sujet
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key)
                .compact();
    }

    /**
     * Vérifie la validité du token
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String trackingId = extractTrackingId(token);

            if (!trackingId.equals(userDetails.getUsername())) {
                logger.warn("⛔ Tentative d'accès non autorisée : Tracking ID non correspondant.");
                return false;
            }

            if (isTokenExpired(token)) {
                logger.warn("⚠️ Le token JWT a expiré pour le tracking ID : {}", trackingId);
                return false;
            }

            logger.info("✅ JWT valide pour l'utilisateur avec le tracking ID : {}", trackingId);
            return true;
        } catch (Exception e) {
            logger.warn("❌ Problème avec le token : {}", e.getMessage());
            return false;
        }
    }

    /**
     * Vérifie si un utilisateur est connecté via son token
     */
    public boolean isUserConnected(String token) {
        try {
            if (token == null || token.isEmpty()) {
                return false;
            }

            return !isTokenExpired(token);
        } catch (Exception e) {
            logger.warn("⚠️ Impossible de vérifier la connexion de l'utilisateur : {}", e.getMessage());
            return false;
        }
    }

    /**
     * Récupère le trackingId à partir du token JWT
     */
    public String extractTrackingId(String token) {
        return extractClaim(token, Claims::getSubject); // Le sujet est maintenant le trackingId
    }

    /**
     * Vérifie si le token a expiré
     */
    private boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    /**
     * Récupère une information spécifique du token JWT
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Récupère tous les claims du token JWT
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
