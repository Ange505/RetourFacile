package com.RetourFacile.services;

import com.RetourFacile.entity.User;
import com.RetourFacile.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String trackingId) throws UsernameNotFoundException {
        logger.info("Tentative d'authentification pour le trackingId : {}", trackingId);

        // Convertir le trackingId en UUID
        UUID uuid;
        try {
            uuid = UUID.fromString(trackingId);
        } catch (IllegalArgumentException e) {
            logger.warn("Format de trackingId invalide : {}", trackingId);
            throw new UsernameNotFoundException("Format de trackingId invalide : " + trackingId);
        }

        // Rechercher l'utilisateur par trackingId
        User user = userRepository.findByTrackingId(uuid)
                .orElseThrow(() -> {
                    logger.warn("Utilisateur non trouvé avec le trackingId : {}", trackingId);
                    return new UsernameNotFoundException("Utilisateur non trouvé avec le trackingId : " + trackingId);
                });

        // Définir le rôle de l'utilisateur
        String role = (user.getRole() != null) ? "ROLE_" + user.getRole().name() : "ROLE_USER";
        logger.info("Utilisateur {} trouvé avec rôle : {}", trackingId, role);

        // Retourner l'objet UserDetails
        return new org.springframework.security.core.userdetails.User(
                user.getTrackingId().toString(), // Utiliser le trackingId comme identifiant
                user.getPassword(), // Mot de passe chiffré
                List.of(new SimpleGrantedAuthority(role)) // Rôle de l'utilisateur
        );
    }
}