package com.RetourFacile.services.ServicesImpl;

import com.RetourFacile.dto.Request.CommandeRequest;
import com.RetourFacile.dto.Response.CommandeResponse;
import com.RetourFacile.entity.Commande;
import com.RetourFacile.entity.User;
import com.RetourFacile.mappers.CommandeMapper;
import com.RetourFacile.repository.CommandeRepository;
import com.RetourFacile.repository.UserRepository;
import com.RetourFacile.services.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final UserRepository userRepository;
    private final CommandeMapper commandeMapper;

    @Override
    public CommandeResponse createCommande(CommandeRequest request) {

        User client = userRepository.findByTrackingId(request.getClientTrackingId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Commande commande = Commande.builder()
                .dateCommande(request.getDateCommande())
                .montant(request.getMontant())
                .client(client)
                .build();

        commandeRepository.save(commande);

        return commandeMapper.toResponse(commande);
    }

    @Override
    public List<CommandeResponse> getAllCommandes() {
        return commandeRepository.findAll().stream()
                .map(commandeMapper::toResponse)
                .toList();
    }

    @Override
    public List<CommandeResponse> getCommandesByClient(UUID clientTrackingId) {
        return commandeRepository.findByClientTrackingId(clientTrackingId).stream()
                .map(commandeMapper::toResponse)
                .toList();
    }

    @Override
    public CommandeResponse getCommandeById(UUID trackingId) {
        Commande commande = commandeRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new RuntimeException("Commande not found"));
        return commandeMapper.toResponse(commande);
    }



    @Override
    public CommandeResponse updateCommande(UUID trackingId, CommandeRequest request) {

        Commande commande = commandeRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new RuntimeException("Commande not found"));

        User client = userRepository.findByTrackingId(request.getClientTrackingId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        commande.setDateCommande(request.getDateCommande());
        commande.setMontant(request.getMontant());
        commande.setClient(client);

        commandeRepository.save(commande);

        return commandeMapper.toResponse(commande);
    }

    @Override
    public void deleteCommande(UUID trackingId) {

        Commande commande = commandeRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new RuntimeException("Commande not found"));

        commandeRepository.delete(commande);
    }
}
