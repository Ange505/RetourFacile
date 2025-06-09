package com.RetourFacile.services;


import com.RetourFacile.dto.Request.CommandeRequest;
import com.RetourFacile.dto.Response.CommandeResponse;

import java.util.List;
import java.util.UUID;

public interface CommandeService {

    CommandeResponse createCommande(CommandeRequest request);

    List<CommandeResponse> getAllCommandes();

    CommandeResponse getCommandeById(UUID trackingId);  // <-- méthode ajoutée

    List<CommandeResponse> getCommandesByClient(UUID clientTrackingId);

    CommandeResponse updateCommande(UUID trackingId, CommandeRequest request);

    void deleteCommande(UUID trackingId);
}
