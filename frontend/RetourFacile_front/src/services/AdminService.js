import http from "../http-common"; // Importation de l'instance HTTP configurée

class AdminService {
    // Récupérer tous les utilisateurs avec le rôle USER
    getAllUsers() {
        const authToken = localStorage.getItem("authToken"); // Vérifier si le token est présent
        console.log("Token récupéré:", authToken); // Afficher le token récupéré

        if (!authToken) {
            return Promise.reject(new Error("Token non trouvé. Veuillez vous connecter."));
        }

        const apiRoute = "/admin/users"; // Route de l'API pour récupérer les utilisateurs
        console.log("Requête envoyée à l'API route:", apiRoute); // Afficher la route de la requête

        return http.get(apiRoute, {
            headers: {
                Authorization: `Bearer ${authToken}`, // Envoie le token dans l'en-tête
            },
        })
            .then(response => {
                console.log("Réponse de l'API:", response); // Afficher la réponse
                console.log("Utilisateurs récupérés avec succès!");
                return response.data; // Retourner les données des utilisateurs
            })
            .catch(error => {
                console.error("Erreur lors de la récupération des utilisateurs:", error);

                if (error.response) {
                    const errorCode = error.response.status; // Code d'erreur HTTP
                    const errorDetails = error.response.data; // Détails de l'erreur

                    console.log("Code d'erreur HTTP:", errorCode);
                    console.log("Détails de l'erreur:", errorDetails);

                    // Gestion spécifique des erreurs HTTP
                    if (errorCode === 401) {
                        throw new Error("Token invalide ou expiré. Veuillez vous reconnecter.");
                    } else if (errorCode === 403) {
                        throw new Error("Accès refusé. Vous n'avez pas les permissions nécessaires.");
                    } else {
                        throw new Error(`Erreur serveur: ${errorCode} - ${errorDetails.message || "Veuillez réessayer plus tard."}`);
                    }
                } else {
                    throw new Error("Problème de connexion. Veuillez réessayer plus tard.");
                }
            });
    }

    // Récupérer les utilisateurs activés
    getActivatedUsers() {
        const authToken = localStorage.getItem("authToken"); // Vérifier si le token est présent
        console.log("Token récupéré pour les utilisateurs activés:", authToken); // Afficher le token récupéré

        if (!authToken) {
            return Promise.reject(new Error("Token non trouvé. Veuillez vous connecter."));
        }

        const apiRoute = "/admin/users/activated"; // Route de l'API pour récupérer les utilisateurs activés
        console.log("Requête envoyée à l'API route pour les utilisateurs activés:", apiRoute); // Afficher la route de la requête

        return http.get(apiRoute, {
            headers: {
                Authorization: `Bearer ${authToken}`, // Envoie le token dans l'en-tête
            },
        })
            .then(response => {
                console.log("Réponse de l'API pour les utilisateurs activés:", response); // Afficher la réponse
                console.log("Utilisateurs activés récupérés avec succès!");
                return response.data; // Retourner les données des utilisateurs activés
            })
            .catch(error => {
                console.error("Erreur lors de la récupération des utilisateurs activés:", error);

                if (error.response) {
                    const errorCode = error.response.status; // Code d'erreur HTTP
                    const errorDetails = error.response.data; // Détails de l'erreur

                    console.log("Code d'erreur HTTP:", errorCode);
                    console.log("Détails de l'erreur:", errorDetails);

                    // Gestion spécifique des erreurs HTTP
                    if (errorCode === 401) {
                        throw new Error("Token invalide ou expiré. Veuillez vous reconnecter.");
                    } else if (errorCode === 403) {
                        throw new Error("Accès refusé. Vous n'avez pas les permissions nécessaires.");
                    } else {
                        throw new Error(`Erreur serveur: ${errorCode} - ${errorDetails.message || "Veuillez réessayer plus tard."}`);
                    }
                } else {
                    throw new Error("Problème de connexion. Veuillez réessayer plus tard.");
                }
            });
    }

    // Récupérer les utilisateurs en attente
    getPendingUsers() {
        const authToken = localStorage.getItem("authToken"); // Vérifier si le token est présent
        console.log("Token récupéré pour les utilisateurs en attente:", authToken); // Afficher le token récupéré

        if (!authToken) {
            return Promise.reject(new Error("Token non trouvé. Veuillez vous connecter."));
        }

        const apiRoute = "/admin/users/pending"; // Route de l'API pour récupérer les utilisateurs en attente
        console.log("Requête envoyée à l'API route pour les utilisateurs en attente:", apiRoute); // Afficher la route de la requête

        return http.get(apiRoute, {
            headers: {
                Authorization: `Bearer ${authToken}`, // Envoie le token dans l'en-tête
            },
        })
            .then(response => {
                console.log("Réponse de l'API pour les utilisateurs en attente:", response); // Afficher la réponse
                console.log("Utilisateurs en attente récupérés avec succès!");
                return response.data; // Retourner les données des utilisateurs en attente
            })
            .catch(error => {
                console.error("Erreur lors de la récupération des utilisateurs en attente:", error);

                if (error.response) {
                    const errorCode = error.response.status; // Code d'erreur HTTP
                    const errorDetails = error.response.data; // Détails de l'erreur

                    console.log("Code d'erreur HTTP:", errorCode);
                    console.log("Détails de l'erreur:", errorDetails);

                    // Gestion spécifique des erreurs HTTP
                    if (errorCode === 401) {
                        throw new Error("Token invalide ou expiré. Veuillez vous reconnecter.");
                    } else if (errorCode === 403) {
                        throw new Error("Accès refusé. Vous n'avez pas les permissions nécessaires.");
                    } else {
                        throw new Error(`Erreur serveur: ${errorCode} - ${errorDetails.message || "Veuillez réessayer plus tard."}`);
                    }
                } else {
                    throw new Error("Problème de connexion. Veuillez réessayer plus tard.");
                }
            });
    }

    // Modifier le statut (actif/inactif) d'un utilisateur
    updateUserStatus(trackingId, activatedStatus) {
        const authToken = localStorage.getItem("authToken");
        console.log("Token récupéré pour la mise à jour du statut:", authToken); // Afficher le token récupéré

        if (!authToken) {
            return Promise.reject(new Error("Token non trouvé. Veuillez vous connecter."));
        }

        const apiRoute = `/admin/toggle-user/${trackingId}`; // Route de l'API pour modifier le statut de l'utilisateur
        console.log("Requête envoyée à l'API route:", apiRoute); // Afficher la route de la requête

        return http.put(apiRoute, { activated: activatedStatus }, {
            headers: {
                Authorization: `Bearer ${authToken}`, // Envoie le token dans l'en-tête
            },
        })
            .then(response => {
                console.log("Réponse de l'API:", response); // Afficher la réponse
                console.log("Statut de l'utilisateur mis à jour avec succès!");
                return response.data; // Retourner la réponse
            })
            .catch(error => {
                console.error("Erreur lors de la mise à jour du statut de l'utilisateur:", error);

                if (error.response) {
                    const errorCode = error.response.status; // Code d'erreur HTTP
                    const errorDetails = error.response.data; // Détails de l'erreur

                    console.log("Code d'erreur HTTP:", errorCode);
                    console.log("Détails de l'erreur:", errorDetails);

                    if (errorCode === 401) {
                        throw new Error("Token invalide ou expiré. Veuillez vous reconnecter.");
                    } else if (errorCode === 403) {
                        throw new Error("Accès refusé. Vous n'avez pas les permissions nécessaires.");
                    } else {
                        throw new Error(`Erreur serveur: ${errorCode} - ${errorDetails.message || "Veuillez réessayer plus tard."}`);
                    }
                } else {
                    throw new Error("Problème de connexion. Veuillez réessayer plus tard.");
                }
            });
    }
}

export default new AdminService();
