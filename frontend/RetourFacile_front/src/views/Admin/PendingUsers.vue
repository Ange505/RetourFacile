<template>
  <Base>
    <div class="container mt-4">
      <h2 class="mb-4 text-center">Utilisateurs en attente d'activation</h2>

      <!-- Spinner de chargement -->
      <div v-if="isLoading" class="d-flex justify-content-center my-4">
        <div class="spinner-border text-primary" role="status">
          <span class="visually-hidden">Chargement...</span>
        </div>
      </div>

      <!-- Message d'erreur -->
      <div v-else-if="errorMessage" class="alert alert-danger text-center">
        {{ errorMessage }}
      </div>

      <!-- Aucun utilisateur en attente -->
      <div v-else-if="users.length === 0" class="alert alert-info text-center">
        Aucun utilisateur en attente d'activation.
      </div>

      <!-- Tableau des utilisateurs -->
      <div v-else class="table-responsive">
        <table class="table table-hover text-center">
          <thead class="table-dark">
          <tr>
            <th>Photo</th>
            <th>Nom</th>
            <th>Email</th>
            <th>Vérification</th>
            <th>Action</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="user in users" :key="user.trackingId">
            <td>
              <img
                  :src="user.photoProfil || defaultAvatar"
                  alt="Profil"
                  class="rounded-circle border"
                  width="50"
                  height="50"
                  @click="goToProfile(user.trackingId)"
                  style="cursor: pointer;"
              />
            </td>
            <td>{{ user.username }}</td>
            <td>{{ user.email }}</td>
            <td>
              <span class="badge bg-danger">Non activé</span>
            </td>
            <td>
              <button
                  @click="confirmActivation(user.trackingId)"
                  class="btn btn-success btn-sm"
                  :disabled="loadingUserId === user.trackingId"
              >
                <span v-if="loadingUserId === user.trackingId" class="spinner-border spinner-border-sm"></span>
                <span v-else>Activer</span>
              </button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>

    </div>
  </Base>
</template>

<script>
import { onMounted, ref } from "vue";
import AdminService from "@/services/AdminService";
import defaultAvatar from "@/assets/admin/images/profile/default_avatar.jpg";
import Base from "@/views/User/Base.vue";
import { useRouter } from "vue-router";
import Swal from "sweetalert2"; // Import SweetAlert2

export default {
  components: { Base },
  setup() {
    const users = ref([]);
    const isLoading = ref(true);
    const errorMessage = ref("");
    const loadingUserId = ref(null);
    const router = useRouter();

    const fetchPendingUsers = async () => {
      try {
        const response = await AdminService.getPendingUsers();
        users.value = response;
        console.log("Utilisateurs en attente :", response);
      } catch (error) {
        errorMessage.value = "Erreur lors du chargement des utilisateurs.";
        console.error("Erreur fetchPendingUsers :", error);
      } finally {
        isLoading.value = false;
      }
    };

    const activateUser = async (trackingId) => {
      console.log("Activation de l'utilisateur avec Tracking ID :", trackingId);
      loadingUserId.value = trackingId;
      try {
        await AdminService.updateUserStatus(trackingId);

        // Afficher un toast de succès avec SweetAlert2
        Swal.fire({
          icon: "success",
          title: "Utilisateur activé",
          text: "L'utilisateur a été activé avec succès.",
          timer: 3000,
          showConfirmButton: false
        });

        // Rafraîchir la liste des utilisateurs après l'activation
        users.value = users.value.filter(user => user.trackingId !== trackingId);
      } catch (error) {
        console.error("Erreur lors de l'activation :", error);
        errorMessage.value = "Erreur lors de l'activation de l'utilisateur.";
      } finally {
        loadingUserId.value = null;
      }
    };

    const confirmActivation = (trackingId) => {
      // Utiliser SweetAlert2 pour la confirmation de l'activation
      Swal.fire({
        title: "Voulez-vous vraiment activer cet utilisateur ?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Oui, activer",
        cancelButtonText: "Annuler"
      }).then((result) => {
        if (result.isConfirmed) {
          activateUser(trackingId);
        }
      });
    };

    const goToProfile = (trackingId) => {
      router.push(`/admin/user-profile/${trackingId}`);
    };

    onMounted(fetchPendingUsers);

    return {
      users,
      isLoading,
      errorMessage,
      confirmActivation,
      goToProfile,
      defaultAvatar,
      loadingUserId
    };
  }
};
</script>

<style>
.toast {
  opacity: 1 !important;
}

.table td, .table th {
  vertical-align: middle;
}
</style>
