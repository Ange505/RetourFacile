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
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID trackingId;

    private LocalDate dateCommande;

    private double montant;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @PrePersist
    public void prePersist() {
        if (trackingId == null) {
            trackingId = UUID.randomUUID();
        }
    }
}
