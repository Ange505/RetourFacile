package com.RetourFacile.dto;

import lombok.Data;

import com.RetourFacile.entity.StatutReclamation;

import java.util.UUID;

@Data
public class ReclamationRequest {

    private String description;

    private StatutReclamation statut;

    private UUID commandeTrackingId;
}
