package com.RetourFacile.dto.Response;

import com.RetourFacile.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {

    private UUID trackingId;      // Identifiant unique de l'utilisateur (UUID)
    private String username;      // Nom d'utilisateur
    private String email;         // Adresse e-mail
    private String numTel;        // Numéro de téléphone
    private String photoProfil;   // URL de la photo de profil (peut être null)
    private boolean isConnected;  // Statut de connexion (true si connecté)
    private boolean activated;
    private Role role;
}
