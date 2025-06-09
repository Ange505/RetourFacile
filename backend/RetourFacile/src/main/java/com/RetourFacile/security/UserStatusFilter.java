package com.RetourFacile.security;

import com.RetourFacile.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserStatusFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            // Récupérer l'utilisateur actuel
            User user = (User) authentication.getPrincipal();
            if (user != null) {
                System.out.println("Utilisateur récupéré : " + user.getUsername() + " | Activé : " + user.isActivated());
            }

            // Vérifier si l'utilisateur est activé
            if (user != null && !user.isActivated()) {
                System.out.println("Compte désactivé pour l'utilisateur " + user.getUsername());
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Compte désactivé.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
