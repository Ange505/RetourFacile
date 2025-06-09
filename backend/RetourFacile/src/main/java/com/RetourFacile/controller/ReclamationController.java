package com.RetourFacile.controller;

import com.RetourFacile.dto.ReclamationRequest;
import com.RetourFacile.dto.Response.ReclamationResponse;
import com.RetourFacile.services.ReclamationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reclamations")
@RequiredArgsConstructor
public class ReclamationController {

    private final ReclamationService reclamationService;

    @PostMapping
    public ReclamationResponse createReclamation(@RequestBody ReclamationRequest request) {
        return reclamationService.createReclamation(request);
    }

    @GetMapping
    public List<ReclamationResponse> getAllReclamations() {
        return reclamationService.getAllReclamations();
    }

    @GetMapping("/commande/{commandeTrackingId}")
    public List<ReclamationResponse> getReclamationsByCommande(@PathVariable UUID commandeTrackingId) {
        return reclamationService.getReclamationsByCommande(commandeTrackingId);
    }

    @GetMapping("/{trackingId}")
    public ReclamationResponse getReclamationById(@PathVariable UUID trackingId) {
        return reclamationService.getReclamationById(trackingId);
    }


    @PutMapping("/{trackingId}")
    public ReclamationResponse updateReclamation(@PathVariable UUID trackingId, @RequestBody ReclamationRequest request) {
        return reclamationService.updateReclamation(trackingId, request);
    }

    @DeleteMapping("/{trackingId}")
    public void deleteReclamation(@PathVariable UUID trackingId) {
        reclamationService.deleteReclamation(trackingId);
    }
}
