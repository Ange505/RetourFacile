package com.RetourFacile.services.ServicesImpl;

import com.RetourFacile.Exceptions.UserNotFoundException;
import com.RetourFacile.Exceptions.InvalidRequestException;
import com.RetourFacile.dto.Response.UserResponse;
import com.RetourFacile.dto.Request.UserUpdateRequest;
import com.RetourFacile.entity.User;
import com.RetourFacile.mappers.UserMappers;
import com.RetourFacile.repository.UserRepository;
import com.RetourFacile.services.JwtService;
import com.RetourFacile.services.UserService;
import com.RetourFacile.services.FileService;  // Importation du FileService existant
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMappers userMappers;
    private final JwtService jwtService;
    private final FileService fileService;  // Utilisation de FileService pour gérer les fichiers

    public UserServiceImpl(UserRepository userRepository, UserMappers userMappers, JwtService jwtService, FileService fileService) {
        this.userRepository = userRepository;
        this.userMappers = userMappers;
        this.jwtService = jwtService;
        this.fileService = fileService;  // Injection de FileService
    }

    @Override
    public UserResponse getUserProfile(HttpServletRequest request) {
        String trackingId = extractTrackingIdFromRequest(request);
        User user = userRepository.findByTrackingId(UUID.fromString(trackingId))
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé avec le trackingId : " + trackingId));
        return userMappers.toResponse(user);
    }

    @Transactional
    @Override
    public UserResponse updateUserProfile(HttpServletRequest request, UserUpdateRequest userUpdateRequest, MultipartFile photo) {
        String trackingId = extractTrackingIdFromRequest(request);
        User user = userRepository.findByTrackingId(UUID.fromString(trackingId))
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé avec le trackingId : " + trackingId));

        if (userUpdateRequest.getUsername() != null && !userUpdateRequest.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(userUpdateRequest.getUsername())) {
                throw new InvalidRequestException("Ce nom d'utilisateur est déjà utilisé.");
            }
            user.setUsername(userUpdateRequest.getUsername());
        }

        if (userUpdateRequest.getEmail() != null && !userUpdateRequest.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userUpdateRequest.getEmail())) {
                throw new InvalidRequestException("Cet e-mail est déjà utilisé.");
            }
            user.setEmail(userUpdateRequest.getEmail());
        }

        if (userUpdateRequest.getNumTel() != null) {
            user.setNumTel(userUpdateRequest.getNumTel());
        }

        if (photo != null && !photo.isEmpty()) {
            try {
                // Supprimer l'ancienne photo si elle existe
                if (user.getPhotoProfil() != null) {
                    fileService.deleteFile(user.getPhotoProfil());  // Utilisation du FileService pour supprimer l'ancienne photo
                }

                // Enregistrer la nouvelle photo
                String fileName = fileService.saveFile(photo);  // Utilisation du FileService pour enregistrer la nouvelle photo
                user.setPhotoProfil(fileName);

            } catch (IOException e) {
                throw new InvalidRequestException("Erreur lors du traitement de la photo de profil : " + e.getMessage());
            }
        }

        userRepository.save(user);
        return userMappers.toResponse(user);
    }

    private String extractTrackingIdFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidRequestException("Token JWT manquant ou invalide.");
        }

        String token = authHeader.substring(7);
        String trackingId = jwtService.extractTrackingId(token);
        if (trackingId == null) {
            throw new InvalidRequestException("Impossible d'extraire le trackingId du token.");
        }

        return trackingId;
    }
}
