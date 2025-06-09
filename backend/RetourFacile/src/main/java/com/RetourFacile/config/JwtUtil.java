package com.RetourFacile.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "MaSuperCleSecreteTresLongueEtSecuriseeMaSuperCleSecreteTresLongueEtSecurisee"; // Stocke cette clé en variable d’environnement

    // Encodage sécurisé de la clé secrète
    private SecretKey getSignKey() {
        byte[] keyBytes = Base64.getEncoder().encode(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Keys.hmacShaKeyFor(keyBytes);
    }



    // Méthode pour extraire le tracking ID
    public String extractTrackingId(String token) {
        return extractClaim(token, claims -> claims.get("trackingId", String.class)); // Récupération du trackingId
    }


    // Vérifier si le token est valide
    public boolean isTokenValid(String token, String username) {
        return extractTrackingId(token).equals(username) && !isTokenExpired(token);
    }

    // Récupérer les claims
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
