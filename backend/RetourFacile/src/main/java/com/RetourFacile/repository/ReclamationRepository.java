package com.RetourFacile.repository;

import com.RetourFacile.entity.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {

    Optional<Reclamation> findByTrackingId(UUID trackingId);

    List<Reclamation> findByCommandeTrackingId(UUID commandeTrackingId);
}
