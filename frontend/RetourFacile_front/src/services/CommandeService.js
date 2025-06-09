import http from "../http-common"; // Import de l'instance HTTP configurée

class CommandeService {
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
                throw new Error("Commande non trouvée.");
            } else {
                throw new Error(`Erreur serveur: ${errorCode}`);
            }
        } else {
            throw new Error("Problème de connexion. Veuillez réessayer plus tard.");
        }
    }

    // 🔹 Récupérer toutes les commandes
    async getAll() {
        const authToken = localStorage.getItem("authToken");
        if (!authToken) throw new Error("Token non trouvé. Veuillez vous connecter.");

        try {
            const response = await http.get("/commandes", {
                headers: { Authorization: `Bearer ${authToken}` },
            });
            console.log("Toutes les commandes récupérées avec succès !");
            return response.data;
        } catch (error) {
            this.handleError(error);
        }
    }

    // 🔹 Récupérer les commandes par client (trackingId client)
    async getByClient(clientTrackingId) {
        const authToken = localStorage.getItem("authToken");
        if (!authToken) throw new Error("Token non trouvé. Veuillez vous connecter.");

        try {
            const response = await http.get(`/commandes/client/${clientTrackingId}`, {
                headers: { Authorization: `Bearer ${authToken}` },
            });
            console.log("Commandes du client récupérées avec succès !");
            return response.data;
        } catch (error) {
            this.handleError(error);
        }
    }

    // 🔹 Récupérer une commande par trackingId
    async get(trackingId) {
        const authToken = localStorage.getItem("authToken");
        if (!authToken) throw new Error("Token non trouvé. Veuillez vous connecter.");

        try {
            const response = await http.get(`/commandes/${trackingId}`, {
                headers: { Authorization: `Bearer ${authToken}` },
            });
            console.log("Commande récupérée avec succès !");
            return response.data;
        } catch (error) {
            this.handleError(error);
        }
    }

    // 🔹 Créer une commande
    async create(data) {
        const authToken = localStorage.getItem("authToken");
        if (!authToken) throw new Error("Token non trouvé. Veuillez vous connecter.");

        try {
            const response = await http.post("/commandes", data, {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${authToken}`,
                },
            });
            console.log("Commande créée avec succès !");
            return response.data;
        } catch (error) {
            this.handleError(error);
        }
    }

    // 🔹 Mettre à jour une commande
    async update(trackingId, data) {
        const authToken = localStorage.getItem("authToken");
        if (!authToken) throw new Error("Token non trouvé. Veuillez vous connecter.");

        try {
            const response = await http.put(`/commandes/${trackingId}`, data, {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    Authorization: `Bearer ${authToken}`,
                },
            });
            console.log("Commande mise à jour avec succès !");
            return response.data;
        } catch (error) {
            this.handleError(error);
        }
    }

    // 🔹 Supprimer une commande
    async delete(trackingId) {
        const authToken = localStorage.getItem("authToken");
        if (!authToken) throw new Error("Token non trouvé. Veuillez vous connecter.");

        try {
            await http.delete(`/commandes/${trackingId}`, {
                headers: { Authorization: `Bearer ${authToken}` },
            });
            console.log("Commande supprimée avec succès !");
        } catch (error) {
            this.handleError(error);
        }
    }
}

export default new CommandeService();
