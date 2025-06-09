package com.RetourFacile.repository;

import com.RetourFacile.entity.Role;
import com.RetourFacile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
    Optional<User> findByTrackingId(UUID trackingId);

    List<User> findAllByRole(Role role); // Récupérer uniquement les 'USER'

    // Ajouter ces deux méthodes pour filtrer par rôle et activation
    List<User> findAllByRoleAndActivated(Role role, boolean activated);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
