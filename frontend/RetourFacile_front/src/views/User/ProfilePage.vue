<template>
  <Base>
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
              <img :src="photoProfil" alt="Photo de profil" class="rounded-circle profile-img" />

            </div>

            <h3 v-if="username" class="fw-bold text-dark">{{ username }}</h3>
            <p v-if="email" class="text-muted">{{ email }}</p>
            <p v-else class="text-danger">Email non renseigné</p>
            <p v-if="numTel" class="text-muted">Numéro de téléphone: {{ numTel }}</p>
            <p v-else class="text-danger">Numéro de téléphone non renseigné</p>

            <!-- Boutons -->
            <div class="mt-4">
              <button @click="goToEditProfile" class="btn btn-primary">Modifier le profil</button>
              <button @click="logout" class="btn btn-danger ms-2">Déconnexion</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Base>
</template>

<script>
import { ref, onMounted } from "vue";
import UserService from "@/services/UserService"; // Assurez-vous d'importer UserService
import { useRouter } from "vue-router";  // Pour la gestion des redirections
import defaultAvatar from "@/assets/admin/images/profile/default_avatar.jpg";
import Header from "@/components/admin/Header.vue";
import Sidebar from "@/components/admin/Sidebar.vue";
import Base from "@/views/User/Base.vue"; // Votre avatar par défaut

export default {
  components: {Base, Sidebar, Header},
  data() {
    return {
      username: "",
      email: "",
      numTel: "",
      photoProfil: "",
      connected: false,  // Cette valeur ne sera plus utilisée
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
        // La ligne ci-dessous peut être supprimée si tu ne veux plus afficher le statut de connexion
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
.profile-img {
  width: 150px;
  height: 150px;
  object-fit: cover; /* Permet de garder les proportions de l'image */
}

</style>
