package com.RetourFacile.services;

import com.RetourFacile.dto.Response.UserResponse;
import com.RetourFacile.dto.Request.UserUpdateRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    /**
     * Récupère le profil de l'utilisateur connecté à partir du token JWT.
     *
     * @param request La requête HTTP contenant le token JWT dans le header Authorization.
     * @return Les informations de l'utilisateur.
     */
    UserResponse getUserProfile(HttpServletRequest request);

    /**
     * Met à jour le profil de l'utilisateur connecté à partir du token JWT.
     *
     * @param request La requête HTTP contenant le token JWT dans le header Authorization.
     * @param userUpdateRequest Les nouvelles informations de l'utilisateur.
     * @param photo La nouvelle photo de profil (optionnelle).
     * @return Les informations mises à jour de l'utilisateur.
     */
    UserResponse updateUserProfile(HttpServletRequest request, UserUpdateRequest userUpdateRequest, MultipartFile photo);
}