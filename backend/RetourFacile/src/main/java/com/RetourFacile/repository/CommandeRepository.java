package com.RetourFacile.repository;

import com.RetourFacile.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

    Optional<Commande> findByTrackingId(UUID trackingId);

    List<Commande> findByClientTrackingId(UUID clientTrackingId);
}
