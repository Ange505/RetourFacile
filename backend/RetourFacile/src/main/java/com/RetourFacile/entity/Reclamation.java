package com.RetourFacile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID trackingId;

    private String description;

    @Enumerated(EnumType.STRING)
    private StatutReclamation statut;

    @Column(nullable = false)
    private LocalDate dateReclamation;


    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    @PrePersist
    public void prePersist() {
        if (trackingId == null) {
            trackingId = UUID.randomUUID();
        }
    }
}
