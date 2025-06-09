import axios from "axios";

// Crée une instance d'axios avec la configuration de base
const http = axios.create({
    baseURL: "http://localhost:8083/api", // Ton URL de base
    headers: {
        "Content-Type": "application/json",
    },
});

// Intercepteur pour gérer les erreurs globalement
http.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.status === 401) {
            // Rediriger l'utilisateur vers la page de connexion si non autorisé
            window.location.href = "/login";
        }
        return Promise.reject(error);
    }
);

export default http;
