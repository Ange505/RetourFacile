package com.RetourFacile.services;


import com.RetourFacile.dto.ReclamationRequest;
import com.RetourFacile.dto.Response.ReclamationResponse;

import java.util.List;
import java.util.UUID;

public interface ReclamationService {

    ReclamationResponse createReclamation(ReclamationRequest request);

    List<ReclamationResponse> getAllReclamations();

    List<ReclamationResponse> getReclamationsByCommande(UUID commandeTrackingId);

    ReclamationResponse getReclamationById(UUID trackingId);

    ReclamationResponse updateReclamation(UUID trackingId, ReclamationRequest request);

    void deleteReclamation(UUID trackingId);
}
