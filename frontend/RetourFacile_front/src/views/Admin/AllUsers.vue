<template>
  <Base>
    <div class="container mt-4">
      <h2 class="mb-4 text-center" style="color: #d46e6e; font-size: 2rem;">Tous les utilisateurs</h2>

      <div v-if="isLoading" class="text-center">
        <div class="spinner-border text-primary" role="status">
          <span class="visually-hidden">Chargement...</span>
        </div>
      </div>

      <div v-else-if="errorMessage" class="alert alert-danger text-center">
        {{ errorMessage }}
      </div>

      <div v-else>
        <div v-if="users.length === 0" class="alert alert-info text-center">
          Aucun utilisateur trouvé.
        </div>

        <div v-else class="table-responsive">
          <table class="table table-hover">
            <thead class="table-dark">
            <tr>
              <th>Photo</th>
              <th>Nom</th>
              <th>Email</th>
              <th>Numéro de téléphone</th>
              <th>Activé</th>
              <th>Connecté</th>
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
                    width="60"
                    height="60"
                    @click="goToProfile(user.trackingId)"
                    style="cursor: pointer; transition: transform 0.3s ease; border: 2px solid #d46e6e;"
                    @mouseover="hoverProfileImage = true"
                    @mouseleave="hoverProfileImage = false"
                />
              </td>
              <td class="text-lg">{{ user.username }}</td>
              <td>{{ user.email }}</td>
              <td>{{ user.numTel }}</td>
              <td>
                  <span :class="{'text-success': user.activated, 'text-danger': !user.activated}">
                    {{ user.activated ? "Oui" : "Non" }}
                  </span>
              </td>
              <td>
                  <span :class="{'text-success': user.connected, 'text-danger': !user.connected}">
                    {{ user.connected ? "Oui" : "Non" }}
                  </span>
              </td>
              <td>
                <button
                    @click="confirmDeactivation(user.trackingId, !user.activated)"
                    class="btn btn-sm"
                    :class="user.activated ? 'btn-warning' : 'btn-success'"
                    style="border-radius: 20px; padding: 8px 20px; font-size: 14px; transition: background-color 0.3s ease;"
                >
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
import Swal from "sweetalert2";

export default {
  components: { Base },
  setup() {
    const users = ref([]);
    const isLoading = ref(true);
    const errorMessage = ref("");
    const router = useRouter();

    const fetchAllUsers = async () => {
      try {
        const response = await AdminService.getAllUsers();
        users.value = response;
      } catch (error) {
        errorMessage.value = "Erreur lors du chargement des utilisateurs.";
      } finally {
        isLoading.value = false;
      }
    };

    const updateUserStatus = async (trackingId, activatedStatus) => {
      try {
        await AdminService.updateUserStatus(trackingId, activatedStatus);
        Swal.fire({
          icon: 'success',
          title: 'Succès',
          text: `Utilisateur ${activatedStatus ? 'activé' : 'désactivé'} avec succès !`,
          timer: 3000,
          showConfirmButton: false,
        });
        fetchAllUsers();
      } catch (error) {
        errorMessage.value = `Erreur lors de la mise à jour du statut de l'utilisateur.`;
      }
    };

    const confirmDeactivation = (trackingId, activatedStatus) => {
      const action = activatedStatus ? "activer" : "désactiver";
      Swal.fire({
        title: `Voulez-vous vraiment ${action} cet utilisateur ?`,
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Oui",
        cancelButtonText: "Annuler",
      }).then((result) => {
        if (result.isConfirmed) {
          updateUserStatus(trackingId, activatedStatus);
        }
      });
    };

    const goToProfile = (trackingId) => {
      router.push(`/admin/user-profile/${trackingId}`);
    };

    onMounted(fetchAllUsers);

    return {
      users,
      isLoading,
      errorMessage,
      confirmDeactivation,
      goToProfile,
      defaultAvatar,
    };
  }
};
</script>

<style scoped>
table th, table td {
  font-size: 1.1rem;
  padding: 15px;
  border: 1px solid #d46e6e;
}

.btn {
  transition: transform 0.3s ease;
}

.btn:hover {
  transform: scale(1.1);
}

.text-lg {
  font-size: 1.2rem;
}
</style>
