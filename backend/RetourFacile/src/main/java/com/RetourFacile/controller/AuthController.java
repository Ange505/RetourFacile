package com.RetourFacile.controller;

import com.RetourFacile.dto.Request.LoginRequest;
import com.RetourFacile.entity.User;
import com.RetourFacile.services.AuthService;
import com.RetourFacile.services.JwtService;
import com.RetourFacile.services.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;
    private final TokenBlacklistService tokenBlacklistService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, TokenBlacklistService tokenBlacklistService, JwtService jwtService) {
        this.authService = authService;
        this.tokenBlacklistService = tokenBlacklistService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        return authService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklistService.addToBlacklist(token);
        }

        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Déconnexion réussie");
    }

    /**
     * Vérifie si un utilisateur est connecté via son token
     */
    @GetMapping("/is-connected")
    public ResponseEntity<Boolean> isUserConnected(@RequestHeader("Authorization") String token) {
        // Vérifier si le token commence par "Bearer "
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        boolean isConnected = jwtService.validateToken(token, null); // null car pas de UserDetails ici
        return ResponseEntity.ok(isConnected);
    }
}
