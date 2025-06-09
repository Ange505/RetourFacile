package com.RetourFacile.dto.Request;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CommandeRequest {

    private LocalDate dateCommande;

    private double montant;

    private UUID clientTrackingId;
}
