package com.RetourFacile.mappers;

import com.RetourFacile.dto.Response.ReclamationResponse;
import com.RetourFacile.entity.Reclamation;
import org.springframework.stereotype.Component;

@Component
public class ReclamationMapper {

    public ReclamationResponse toResponse(Reclamation reclamation) {
        ReclamationResponse response = new ReclamationResponse();
        response.setTrackingId(reclamation.getTrackingId());
        response.setDescription(reclamation.getDescription());
        response.setStatut(reclamation.getStatut());
        response.setCommandeTrackingId(reclamation.getCommande().getTrackingId());
        response.setDateReclamation(reclamation.getDateReclamation());
        response.setClientTrackingId(reclamation.getCommande().getClient().getTrackingId());
        return response;
    }
}
