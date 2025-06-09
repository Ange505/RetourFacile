<template>
  <Base>
    <div class="container mt-4">
      <h2 class="mb-4">Utilisateurs activés</h2>

      <div v-if="isLoading" class="text-center">
        <div class="spinner-border text-primary" role="status">
          <span class="visually-hidden">Chargement...</span>
        </div>
      </div>

      <div v-else-if="errorMessage" class="alert alert-danger">
        {{ errorMessage }}
      </div>

      <div v-else>
        <div v-if="users.length === 0" class="alert alert-info">
          Aucun utilisateur activé.
        </div>

        <div v-else class="table-responsive">
          <table class="table table-hover">
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
                    class="rounded-circle"
                    width="50"
                    height="50"
                    @click="goToProfile(user.trackingId)"
                    style="cursor: pointer;"
                />
              </td>
              <td>{{ user.username }}</td>
              <td>{{ user.email }}</td>
              <td>
                <span v-if="user.activated" class="badge bg-success">Activé</span>
                <span v-else class="badge bg-danger">Non activé</span>
              </td>
              <td>
                <button
                    @click="confirmDeactivation(user.trackingId, !user.activated)"
                    class="btn btn-danger btn-sm">
                  {{ user.activated ? "Désactiver" : "Activer" }}
                </button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
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
    const toastMessage = ref("");
    const router = useRouter();

    // Fonction pour récupérer les utilisateurs activés
    const fetchActivatedUsers = async () => {
      try {
        const response = await AdminService.getActivatedUsers(); // Récupérer la liste des utilisateurs activés
        users.value = response;
      } catch (error) {
        errorMessage.value = "Erreur lors du chargement des utilisateurs.";
      } finally {
        isLoading.value = false;
      }
    };

    // Fonction pour mettre à jour le statut de l'utilisateur
    const updateUserStatus = async (trackingId, activatedStatus) => {
      loadingUserId.value = trackingId;
      try {
        await AdminService.updateUserStatus(trackingId, activatedStatus); // Mise à jour du statut
        showToast(`Utilisateur ${activatedStatus ? 'activé' : 'désactivé'} avec succès !`);

        // Rafraîchir la liste des utilisateurs après modification
        fetchActivatedUsers();
      } catch (error) {
        console.error("Erreur lors de la mise à jour du statut :", error);
        errorMessage.value = `Erreur lors de la ${activatedStatus ? 'activation' : 'désactivation'} de l'utilisateur.`;
      } finally {
        loadingUserId.value = null;
      }
    };

    // Confirmer la mise à jour du statut de l'utilisateur avec SweetAlert2
    const confirmDeactivation = (trackingId, activatedStatus) => {
      const action = activatedStatus ? "activer" : "désactiver";

      // Affichage de la confirmation avec SweetAlert2
      Swal.fire({
        title: `Voulez-vous vraiment ${action} cet utilisateur ?`,
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Oui",
        cancelButtonText: "Annuler",
      }).then((result) => {
        if (result.isConfirmed) {
          updateUserStatus(trackingId, activatedStatus); // Appel à la fonction updateUserStatus
        }
      });
    };

    // Rediriger vers le profil de l'utilisateur
    const goToProfile = (trackingId) => {
      router.push(`/admin/user-profile/${trackingId}`);
    };

    // Afficher un message toast de confirmation
    const showToast = (message) => {
      Swal.fire({
        icon: 'success',
        title: 'Succès',
        text: message,
        timer: 3000,
        showConfirmButton: false,
      });
    };

    onMounted(fetchActivatedUsers);

    return {
      users,
      isLoading,
      errorMessage,
      confirmDeactivation,
      goToProfile,
      defaultAvatar,
      loadingUserId,
      toastMessage,
    };
  }
};
</script>
