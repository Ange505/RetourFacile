package com.RetourFacile.dto.Response;

import lombok.Data;

import com.RetourFacile.entity.StatutReclamation;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ReclamationResponse {

    private UUID trackingId;

    private String description;

    private StatutReclamation statut;

    private UUID commandeTrackingId;

    private LocalDate dateReclamation;

    private UUID clientTrackingId;

}
