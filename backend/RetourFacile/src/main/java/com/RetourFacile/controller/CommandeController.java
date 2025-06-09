package com.RetourFacile.controller;

import com.RetourFacile.dto.Request.CommandeRequest;
import com.RetourFacile.dto.Response.CommandeResponse;
import com.RetourFacile.services.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
public class CommandeController {

    private final CommandeService commandeService;

    @PostMapping
    public CommandeResponse createCommande(@RequestBody CommandeRequest request) {
        return commandeService.createCommande(request);
    }

    @GetMapping
    public List<CommandeResponse> getAllCommandes() {
        return commandeService.getAllCommandes();
    }

    @GetMapping("/{trackingId}")
    public CommandeResponse getCommandeById(@PathVariable UUID trackingId) {
        return commandeService.getCommandeById(trackingId);
    }


    @GetMapping("/client/{clientTrackingId}")
    public List<CommandeResponse> getCommandesByClient(@PathVariable UUID clientTrackingId) {
        return commandeService.getCommandesByClient(clientTrackingId);
    }

    @PutMapping("/{trackingId}")
    public CommandeResponse updateCommande(@PathVariable UUID trackingId, @RequestBody CommandeRequest request) {
        return commandeService.updateCommande(trackingId, request);
    }

    @DeleteMapping("/{trackingId}")
    public void deleteCommande(@PathVariable UUID trackingId) {
        commandeService.deleteCommande(trackingId);
    }
}
