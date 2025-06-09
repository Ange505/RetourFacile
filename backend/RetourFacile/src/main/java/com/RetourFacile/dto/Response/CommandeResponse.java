package com.RetourFacile.dto.Response;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CommandeResponse {

    private UUID trackingId;

    private LocalDate dateCommande;

    private double montant;

    private UUID clientTrackingId;
}
