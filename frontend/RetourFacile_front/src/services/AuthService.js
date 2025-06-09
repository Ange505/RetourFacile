import http from "../http-common";
import router from "@/router/index.js";  // Si tu utilises http-common pour toutes les requêtes

class AuthService {
    // Méthode pour l'inscription de l'utilisateur
    register(data) {
        return http.post("/auth/register", data);
    }

    // Méthode pour la connexion de l'utilisateur
    login(data) {
        return http.post("/auth/login", data);
    }

    // Méthode pour déconnecter l'utilisateur
    // Méthode logout modifiée
    logout() {
        // Récupérer le token actuel du localStorage
        const authToken = localStorage.getItem('authToken');

        if (!authToken) {
            console.warn("Aucun token trouvé, l'utilisateur est déjà déconnecté.");
            return;
        }

        // Envoi de la requête de déconnexion au backend avec le token en Authorization
        http.post('/auth/logout', {}, {
            headers: {
                Authorization: `Bearer ${authToken}`
            }
        })
            .then(async () => {
                // Supprimer les informations utilisateur et le token du localStorage
                localStorage.removeItem('user');
                localStorage.removeItem('authToken');

                // Attendre la redirection avant de continuer
                await router.push('/');
            })
            .catch(error => {
                console.error("Erreur lors de la déconnexion", error);
            });
    }

}

export default new AuthService();
