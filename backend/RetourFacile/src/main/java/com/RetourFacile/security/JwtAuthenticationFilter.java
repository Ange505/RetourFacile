package com.RetourFacile.security;

import com.RetourFacile.services.CustomUserDetailsService;
import com.RetourFacile.services.JwtService;
import com.RetourFacile.services.TokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final TokenBlacklistService tokenBlacklistService;  // 🔥 Ajout du service de blacklist

    public JwtAuthenticationFilter(JwtService jwtService,
                                   CustomUserDetailsService userDetailsService,
                                   TokenBlacklistService tokenBlacklistService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.tokenBlacklistService = tokenBlacklistService; // 🔥 Injection de la blacklist
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.info("Aucun token JWT trouvé dans la requête.");
                filterChain.doFilter(request, response);
                return;
            }

            String token = authHeader.substring(7);
            logger.info("Token JWT reçu : {}", token);

            // 🔥 Vérifier si le token est blacklisté
            if (tokenBlacklistService.isBlacklisted(token)) {
                logger.warn("Tentative d'utilisation d'un token blacklisté !");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token invalide ou expiré");
                return;
            }

            String trackingId = jwtService.extractTrackingId(token);
            if (trackingId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(trackingId);

                if (jwtService.validateToken(token, userDetails)) {
                    logger.info("Token valide pour l'utilisateur avec le tracking ID : {}", trackingId);
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    logger.warn("Token JWT invalide pour l'utilisateur avec le tracking ID : {}", trackingId);
                }
            }
        } catch (Exception ex) {
            logger.error("Erreur d'authentification JWT : {}", ex.getMessage(), ex);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Erreur d'authentification : " + ex.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
