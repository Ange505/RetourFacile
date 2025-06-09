<template>
  <div class="profile-container">
    <div class="card shadow-lg profile-card">
      <div class="card-body text-center">
        <!-- Affichage du chargement -->
        <div v-if="isLoading" class="spinner-border text-primary" role="status">
          <span class="visually-hidden">Chargement...</span>
        </div>

        <!-- Affichage des erreurs -->
        <div v-else-if="errorMessage" class="alert alert-danger mt-3">
          {{ errorMessage }}
        </div>

        <!-- Affichage des informations du profil -->
        <div v-else>
          <div class="mb-4">
            <img
                :src="photoProfil || defaultAvatar"
                alt="Photo de profil"
                class="rounded-circle border border-4 border-light shadow"
                width="150"
                height="150"
            />
          </div>

          <h3 v-if="username" class="fw-bold text-dark">{{ username }}</h3>
          <p v-if="email" class="text-muted">{{ email }}</p>
          <p v-else class="text-danger">Email non renseigné</p>
          <p v-if="numTel" class="text-muted">Numéro de téléphone: {{ numTel }}</p>
          <p v-else class="text-danger">Numéro de téléphone non renseigné</p>

          <!-- Statut de connexion -->
          <div class="verification-status mt-3">
            <strong>Statut de connexion:</strong>
            <span v-if="connected" class="text-success">
              <i class="fas fa-check-circle"></i> Connecté
            </span>
            <span v-else class="text-danger">
              <i class="fas fa-times-circle"></i> Déconnecté
            </span>
          </div>

          <!-- Boutons -->
          <div class="mt-4">
            <button @click="goToEditProfile" class="btn btn-primary">Modifier le profil</button>
            <button @click="logout" class="btn btn-danger ms-2">Déconnexion</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import UserService from "@/services/UserService"; // Assurez-vous d'importer UserService
import { useRouter } from "vue-router";  // Pour la gestion des redirections
import defaultAvatar from "@/assets/admin/images/profile/default_avatar.jpg"; // Votre avatar par défaut

export default {
  data() {
    return {
      username: "",
      email: "",
      numTel: "",
      photoProfil: "",
      connected: false,
      isLoading: true,
      errorMessage: "",
      defaultAvatar: defaultAvatar,
    };
  },
  mounted() {
    this.fetchUserData(); // Récupère les données de l'utilisateur au montage du composant
  },
  methods: {
    // Méthode pour récupérer les données de l'utilisateur
    async fetchUserData() {
      this.isLoading = true;
      this.errorMessage = "";
      try {
        const response = await UserService.getProfile(); // Appel au service pour récupérer les données de l'utilisateur
        this.username = response.username;
        this.email = response.email;
        this.numTel = response.numTel;
        this.photoProfil = response.photoProfil || this.defaultAvatar;  // Utilisation de l'avatar par défaut si pas de photo
        this.connected = response.connected;
      } catch (error) {
        this.errorMessage = "Impossible de récupérer les informations. Veuillez réessayer.";
      } finally {
        this.isLoading = false;
      }
    },

    // Méthode pour se déconnecter
    logout() {
      localStorage.removeItem("authToken"); // Supprime le token de l'utilisateur
      this.$router.push("/login"); // Redirection vers la page de connexion
    },

    // Méthode pour aller à la page d'édition du profil
    goToEditProfile() {
      this.$router.push("/edit-profile"); // Redirection vers la page d'édition du profil
    },
  },
};
</script>

<style scoped>
/* Style pour la carte de profil */
.profile-container {
  max-width: 600px;
  margin: 0 auto;
  padding-top: 20px;
}

.profile-card {
  border-radius: 10px;
}

.verification-status {
  font-size: 1.2rem;
}
</style>
