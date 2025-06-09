package com.RetourFacile.mappers;

import com.RetourFacile.dto.Response.CommandeResponse;
import com.RetourFacile.entity.Commande;
import org.springframework.stereotype.Component;

@Component
public class CommandeMapper {

    public CommandeResponse toResponse(Commande commande) {
        CommandeResponse response = new CommandeResponse();
        response.setTrackingId(commande.getTrackingId());
        response.setDateCommande(commande.getDateCommande());
        response.setMontant(commande.getMontant());
        response.setClientTrackingId(commande.getClient().getTrackingId());
        return response;
    }
}
