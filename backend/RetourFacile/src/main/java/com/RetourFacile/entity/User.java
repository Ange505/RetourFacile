package com.RetourFacile.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements UserDetails { // Implémente UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String numTel;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID trackingId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.CLIENT; // Rôle obligatoire, par défaut USER

    @Column(nullable = false)
    private boolean isConnected = false; // Statut de connexion

    @Column(nullable = false)
    private boolean activated = false; // Activation du compte

    private String photoProfil; // Peut être null

    @PrePersist
    public void prePersist() {
        if (this.trackingId == null) {
            this.trackingId = UUID.randomUUID();
        }
    }


    // 🚀 Implémentation de UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + role.name()); // Retourne ROLE_ADMIN ou ROLE_USER
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activated; // L’utilisateur est actif s’il est activé
    }
}
