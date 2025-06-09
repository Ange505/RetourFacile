import http from "../http-common"; // Importation de l'instance HTTP configurée

class UserService {
    // Récupérer les informations du profil utilisateur
    getProfile() {
        const authToken = localStorage.getItem("authToken"); // Vérifier si le token est présent

        if (!authToken) {
            return Promise.reject(new Error("Token non trouvé. Veuillez vous connecter."));
        }

        const apiRoute = "/users/profile"; // Route de l'API

        return http.get(apiRoute, {
            headers: {
                Authorization: `Bearer ${authToken}`, // Envoie le token dans l'en-tête
            },
        })
            .then(response => {

                // Afficher un message de succès
                console.log("Profil récupéré avec succès!");
                return response.data;
            })
            .catch(error => {
                console.error("Erreur lors de la récupération du profil:", error);

                // Si l'erreur est due à une réponse du serveur
                if (error.response) {
                    const errorCode = error.response.status; // Code d'erreur HTTP
                    const errorDetails = error.response.data; // Détails de l'erreur, si disponibles

                    console.log("Code d'erreur HTTP:", errorCode); // Affiche le code d'erreur HTTP
                    console.log("Détails de l'erreur:", errorDetails); // Affiche les détails de l'erreur

                    // Gestion spécifique des erreurs HTTP
                    if (errorCode === 401) {
                        console.log("Erreur 401 : Non autorisé, token invalide ou expiré.");
                        throw new Error("Token invalide ou expiré. Veuillez vous reconnecter.");
                    } else if (errorCode === 403) {
                        console.log("Erreur 403 : Accès refusé.");
                        throw new Error("Accès refusé. Vous n'avez pas les permissions nécessaires.");
                    } else if (errorCode === 404) {
                        console.log("Erreur 404 : Ressource non trouvée.");
                        throw new Error("Utilisateur non trouvé.");
                    } else {
                        console.log(`Erreur serveur : ${errorCode}`);
                        throw new Error(`Erreur serveur: ${errorCode}`);
                    }
                } else {
                    console.log("Détails de l'erreur réseau:", error); // Affiche les erreurs réseau
                    throw new Error("Problème de connexion. Veuillez réessayer plus tard.");
                }
            });
    }

    // Mettre à jour les informations du profil utilisateur
    updateProfile(data, photo = null) {
        const authToken = localStorage.getItem("authToken"); // Vérifier si le token est présent
        console.log("Token récupéré depuis localStorage:", authToken); // Affiche le token dans la console

        if (!authToken) {
            return Promise.reject(new Error("Token non trouvé. Veuillez vous connecter."));
        }

        const formData = new FormData();

        // Ajouter les données textuelles (à ajuster selon les propriétés de l'objet data)
        for (const key in data) {
            if (data[key] !== null && data[key] !== undefined) {
                formData.append(key, data[key]);
            }
        }

        // Ajouter la photo si elle est présente
        if (photo) {
            formData.append("photo", photo);
        }

        // Vérification du contenu du formData
        console.log("Données envoyées :", formData);
        for (let pair of formData.entries()) {
            console.log(pair[0] + ', ' + pair[1]);
        }

        const apiRoute = "/users/profile"; // Route de l'API
        console.log("Route de l'API: ", `${http.defaults.baseURL}${apiRoute}`); // Affiche la route complète

        return http.put(apiRoute, formData, {
            headers: {
                "Content-Type": "multipart/form-data",
                Authorization: `Bearer ${authToken}`, // Envoie le token dans l'en-tête
            },
        })
        .then(response => {
            console.log("Réponse de l'API:", response); // Affiche la réponse de l'API
            console.log("Profil mis à jour avec succès!"); // Affiche le message de succès
            return response.data;
        })
        .catch(error => {
            console.error("Erreur lors de la mise à jour du profil:", error);
            if (error.response) {
                const errorCode = error.response.status; // Code d'erreur HTTP
                const errorDetails = error.response.data; // Détails de l'erreur, si disponibles

                console.log("Code d'erreur HTTP:", errorCode); // Affiche le code d'erreur HTTP
                console.log("Détails de l'erreur:", errorDetails); // Affiche les détails de l'erreur

                // Gestion spécifique des erreurs HTTP
                if (errorCode === 401) {
                    console.log("Erreur 401 : Non autorisé, token invalide ou expiré.");
                    throw new Error("Token invalide ou expiré. Veuillez vous reconnecter.");
                } else if (errorCode === 403) {
                    console.log("Erreur 403 : Accès refusé.");
                    throw new Error("Accès refusé. Vous n'avez pas les permissions nécessaires.");
                } else if (errorCode === 404) {
                    console.log("Erreur 404 : Ressource non trouvée.");
                    throw new Error("Utilisateur non trouvé.");
                } else {
                    console.log(`Erreur serveur : ${errorCode}`);
                    throw new Error(`Erreur serveur: ${errorCode}`);
                }
            } else {
                console.log("Détails de l'erreur réseau:", error); // Affiche les erreurs réseau
                throw new Error("Problème de connexion. Veuillez réessayer plus tard.");
            }
        });
    }
}

export default new UserService();
