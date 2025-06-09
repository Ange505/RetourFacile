package com.RetourFacile.services.ServicesImpl;

import com.RetourFacile.dto.Request.LoginRequest;
import com.RetourFacile.entity.User;
import com.RetourFacile.repository.UserRepository;
import com.RetourFacile.services.AuthService;
import com.RetourFacile.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<?> registerUser(User user) {
        // Vérifier si le nom d'utilisateur existe déjà
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Ce nom d'utilisateur existe déjà"));
        }

        // Vérifier si l'e-mail existe déjà
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Cet email existe déjà"));
        }

        // Générer un trackingId unique pour l'utilisateur
        user.setTrackingId(UUID.randomUUID());

        // Chiffrer le mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Sauvegarder l'utilisateur dans la base de données
        userRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "message", "Inscription réussie",
                "trackingId", user.getTrackingId().toString() // Retourner le trackingId
        ));
    }

    @Override
    public ResponseEntity<?> loginUser(LoginRequest request) {
        // Rechercher l'utilisateur par e-mail
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Email ou mot de passe invalide"));
        }

        User user = optionalUser.get();

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Email ou mot de passe invalide"));
        }

        try {
            // Authentifier l'utilisateur avec le trackingId
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getTrackingId().toString(), request.getPassword())
            );

            // Générer un token JWT
            String token = jwtService.generateToken(user);

            // Retourner la réponse avec le token et les informations de l'utilisateur
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "user", Map.of(
                            "email", user.getEmail(),
                            "username", user.getUsername(),
                            "trackingId", user.getTrackingId().toString()
                    )
            ));
        } catch (BadCredentialsException ex) {
            log.error("Échec de l'authentification pour l'utilisateur : {}", request.getEmail(), ex);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Email ou mot de passe invalide"));
        } catch (Exception ex) {
            log.error("Erreur interne lors de l'authentification", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Une erreur est survenue, veuillez réessayer"));
        }
    }
}