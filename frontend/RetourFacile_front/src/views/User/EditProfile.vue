<template>
  <Base>
    <div class="container d-flex justify-content-center align-items-center min-vh-100">
      <div class="card shadow-lg p-4" style="max-width: 500px; width: 100%;">
        <div class="card-body text-center">
          <!-- Chargement -->
          <div v-if="isLoading" class="spinner-border text-primary mb-3" role="status">
            <span class="visually-hidden">Chargement...</span>
          </div>

          <!-- Message d'erreur -->
          <div v-else-if="errorMessage" class="alert alert-danger">
            {{ errorMessage }}
          </div>

          <!-- Formulaire -->
          <div v-else>
            <div class="mb-3 position-relative">
              <img :src="photoProfil" alt="Photo de profil" class="rounded-circle profile-img" />

              <input type="file" @change="handleFileUpload" class="form-control mt-2">
            </div>

            <div class="mb-3">
              <label class="form-label fw-bold">Nom d'utilisateur</label>
              <input type="text" v-model="username" class="form-control">
            </div>

            <div class="mb-3">
              <label class="form-label fw-bold">Email</label>
              <input type="email" v-model="email" class="form-control">
            </div>

            <div class="mb-3">
              <label class="form-label fw-bold">Numéro de téléphone</label>
              <input type="tel" v-model="numTel" class="form-control">
            </div>

            <div class="d-grid gap-2">
              <button @click="updateProfile" class="btn btn-primary">Enregistrer</button>
              <button @click="logout" class="btn btn-danger">Déconnexion</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Base>
</template>

<script>
import { ref } from "vue";
import UserService from "@/services/UserService";
import { useRouter } from "vue-router";
import Swal from "sweetalert2";
import defaultAvatar from "@/assets/admin/images/profile/default_avatar.jpg";
import Base from "@/views/User/Base.vue";

export default {
  components: { Base },
  data() {
    return {
      username: "",
      email: "",
      numTel: "",
      photoProfil: "",
      previewImage: null,
      selectedFile: null, // fichier image
      isLoading: true,
      errorMessage: "",
      defaultAvatar,
    };
  },
  mounted() {
    this.fetchUserData();
  },
  methods: {
    async fetchUserData() {
      this.isLoading = true;
      try {
        const response = await UserService.getProfile();
        this.username = response.username;
        this.email = response.email;
        this.numTel = response.numTel;
        this.photoProfil = response.photoProfil || this.defaultAvatar;
      } catch (error) {
        this.errorMessage = "Impossible de récupérer les informations.";
      } finally {
        this.isLoading = false;
      }
    },

    handleFileUpload(event) {
      const file = event.target.files[0];
      if (file) {
        this.previewImage = URL.createObjectURL(file);
        this.selectedFile = file;
      }
    },

    async updateProfile() {
      this.isLoading = true;
      this.errorMessage = "";

      try {
        const data = {
          username: this.username,
          email: this.email,
          numTel: this.numTel,
        };

        await UserService.updateProfile(data, this.selectedFile);

        await Swal.fire({
          icon: "success",
          title: "Profil mis à jour",
          text: "Vos informations ont bien été enregistrées.",
          timer: 2000,
          showConfirmButton: false,
        });

        this.$router.push("/profile");

      } catch (error) {
        this.errorMessage = error.message;
        await Swal.fire({
          icon: "error",
          title: "Erreur",
          text: this.errorMessage,
        });
      } finally {
        this.isLoading = false;
      }
    },

    logout() {
      localStorage.removeItem("authToken");
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
.container {
  height: 100vh;
}
.profile-img {
  width: 150px;
  height: 150px;
  object-fit: cover; /* Permet de garder les proportions de l'image */
}

</style>
