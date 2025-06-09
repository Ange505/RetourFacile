package com.RetourFacile.services.ServicesImpl;

import com.RetourFacile.dto.ReclamationRequest;
import com.RetourFacile.dto.Response.ReclamationResponse;
import com.RetourFacile.entity.Commande;
import com.RetourFacile.entity.Reclamation;
import com.RetourFacile.mappers.ReclamationMapper;
import com.RetourFacile.repository.CommandeRepository;
import com.RetourFacile.repository.ReclamationRepository;
import com.RetourFacile.services.ReclamationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReclamationServiceImpl implements ReclamationService {

    private final ReclamationRepository reclamationRepository;
    private final CommandeRepository commandeRepository;
    private final ReclamationMapper reclamationMapper;

    @Override
    public ReclamationResponse createReclamation(ReclamationRequest request) {

        Commande commande = commandeRepository.findByTrackingId(request.getCommandeTrackingId())
                .orElseThrow(() -> new RuntimeException("Commande not found"));

        Reclamation reclamation = Reclamation.builder()
                .description(request.getDescription())
                .statut(request.getStatut())
                .commande(commande)
                .dateReclamation(LocalDate.now()) // on met la date du jour
                .build();

        reclamationRepository.save(reclamation);

        return reclamationMapper.toResponse(reclamation);
    }

    @Override
    public List<ReclamationResponse> getAllReclamations() {
        return reclamationRepository.findAll().stream()
                .map(reclamationMapper::toResponse)
                .toList();
    }

    @Override
    public List<ReclamationResponse> getReclamationsByCommande(UUID commandeTrackingId) {
        return reclamationRepository.findByCommandeTrackingId(commandeTrackingId).stream()
                .map(reclamationMapper::toResponse)
                .toList();
    }

    @Override
    public ReclamationResponse getReclamationById(UUID trackingId) {
        Reclamation reclamation = reclamationRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée"));
        return reclamationMapper.toResponse(reclamation);
    }


    @Override
    public ReclamationResponse updateReclamation(UUID trackingId, ReclamationRequest request) {

        Reclamation reclamation = reclamationRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new RuntimeException("Reclamation not found"));

        Commande commande = commandeRepository.findByTrackingId(request.getCommandeTrackingId())
                .orElseThrow(() -> new RuntimeException("Commande not found"));

        reclamation.setDescription(request.getDescription());
        reclamation.setStatut(request.getStatut());
        reclamation.setCommande(commande);

        reclamationRepository.save(reclamation);

        return reclamationMapper.toResponse(reclamation);
    }

    @Override
    public void deleteReclamation(UUID trackingId) {
        Reclamation reclamation = reclamationRepository.findByTrackingId(trackingId)
                .orElseThrow(() -> new RuntimeException("Reclamation not found"));

        reclamationRepository.delete(reclamation);
    }
}
