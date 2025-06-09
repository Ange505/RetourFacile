// src/services/reclamationService.js

import http from "../http-common"; // Import de l'instance HTTP configurée

class ReclamationService {
    // 🔸 Gestion des erreurs
    handleError(error) {
        if (error.response) {
            const errorCode = error.response.status;
            const errorDetails = error.response.data;

            console.log("Code d'erreur HTTP:", errorCode);
            console.log("Détails de l'erreur:", errorDetails);

            if (errorCode === 401) {
                throw new Error("Token invalide ou expiré. Veuillez vous reconnecter.");
            } else if (errorCode === 403) {
                throw new Error("Accès refusé. Vous n'avez pas les permissions nécessaires.");
            } else if (errorCode === 404) {
                throw new Error("Reclamation non trouvée.");
            } else {
                throw new Error(`Erreur serveur: ${errorCode}`);
            }
        } else {
            throw new Error("Problème de connexion. Veuillez réessayer plus tard.");
        }
    }

    // 🔹 Récupérer toutes les réclamations
    async getAll() {
        const authToken = localStorage.getItem("authToken");
        if (!authToken) throw new Error("Token non trouvé. Veuillez vous connecter.");

        try {
            const response = await http.get("/reclamations", {
                headers: { Authorization: `Bearer ${authToken}` },
            });
            console.log("Toutes les réclamations récupérées avec succès !");
            return response.data;
        } catch (error) {
            this.handleError(error);
        }
    }

    // 🔹 Récupérer les réclamations par commande (trackingId commande)
    async getByCommande(commandeTrackingId) {
        const authToken = localStorage.getItem("authToken");
        if (!authToken) throw new Error("Token non trouvé. Veuillez vous connecter.");

        try {
            const response = await http.get(`/reclamations/commande/${commandeTrackingId}`, {
                headers: { Authorization: `Bearer ${authToken}` },
            });
            console.log("Réclamations de la commande récupérées avec succès !");
            return response.data;
        } catch (error) {
            this.handleError(error);
        }
    }

    // 🔹 Récupérer une réclamation par son trackingId
    async get(trackingId) {
        const authToken = localStorage.getItem("authToken");
        if (!authToken) throw new Error("Token non trouvé. Veuillez vous connecter.");

        try {
            const response = await http.get(`/reclamations/${trackingId}`, {
                headers: { Authorization: `Bearer ${authToken}` },
            });
            console.log("Réclamation récupérée avec succès !");
            return response.data;
        } catch (error) {
            this.handleError(error);
        }
    }


    // 🔹 Créer une réclamation
    async create(data) {
        const authToken = localStorage.getItem("authToken");
        if (!authToken) throw new Error("Token non trouvé. Veuillez vous connecter.");

        try {
            const response = await http.post("/reclamations", data, {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${authToken}`,
                },
            });
            console.log("Réclamation créée avec succès !");
            return response.data;
        } catch (error) {
            this.handleError(error);
        }
    }

    // 🔹 Mettre à jour une réclamation
    async update(trackingId, data) {
        const authToken = localStorage.getItem("authToken");
        if (!authToken) throw new Error("Token non trouvé. Veuillez vous connecter.");

        try {
            const response = await http.put(`/reclamations/${trackingId}`, data, {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${authToken}`,
                },
            });
            console.log("Réclamation mise à jour avec succès !");
            return response.data;
        } catch (error) {
            this.handleError(error);
        }
    }

    // 🔹 Supprimer une réclamation
    async delete(trackingId) {
        const authToken = localStorage.getItem("authToken");
        if (!authToken) throw new Error("Token non trouvé. Veuillez vous connecter.");

        try {
            await http.delete(`/reclamations/${trackingId}`, {
                headers: { Authorization: `Bearer ${authToken}` },
            });
            console.log("Réclamation supprimée avec succès !");
        } catch (error) {
            this.handleError(error);
        }
    }
}

export default new ReclamationService();
