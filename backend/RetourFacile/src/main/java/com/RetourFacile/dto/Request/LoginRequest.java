package com.RetourFacile.dto.Request;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginRequest {
    private String email;
    private String password;
    private UUID trackingId; // ✅ Correction de "trakingId" en "trackingId"

    // Constructeur vide pour la désérialisation JSON
    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
