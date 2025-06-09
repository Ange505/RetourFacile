package com.RetourFacile.controller;

import com.RetourFacile.dto.Response.UserResponse;
import com.RetourFacile.dto.Request.UserUpdateRequest;
import com.RetourFacile.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private static final String BASE_URL = "http://localhost:8083/api/images/";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        try {
            UserResponse response = userService.getUserProfile(request);
            if (response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
            }

            // Construction de l'URL complète de la photo
            if (response.getPhotoProfil() != null) {
                response.setPhotoProfil(BASE_URL + response.getPhotoProfil());
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur serveur : " + e.getMessage());
        }
    }

    @PutMapping(value = "/profile", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateProfile(
            HttpServletRequest request,
            @ModelAttribute UserUpdateRequest userUpdateRequest,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {
        try {
            UserResponse response = userService.updateUserProfile(request, userUpdateRequest, photo);

            // Construction de l'URL complète de la photo
            if (response.getPhotoProfil() != null) {
                response.setPhotoProfil(BASE_URL + response.getPhotoProfil());
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur serveur : " + e.getMessage());
        }
    }
}
