<template>
  <Base>
    <div class="container">
      <h2 class="title">📋 Mes Commandes</h2>

      <div v-if="isLoading" class="loading-container">
        <div class="spinner"></div>
        <p>Chargement de vos commandes...</p>
      </div>

      <div v-else-if="errorMessage" class="alert alert-danger">
        {{ errorMessage }}
      </div>

      <div v-else>
        <div class="add-btn-container">
          <router-link :to="{ name: 'AddCommande' }" class="btn add-btn">
            ➕ Nouvelle Commande
          </router-link>
        </div>

        <div v-if="commandes.length === 0" class="alert alert-info">
          Vous n'avez passé aucune commande.
        </div>

        <div class="commande-list" v-else>
          <div
              v-for="commande in commandes"
              :key="commande.trackingId"
              class="commande-item"
          >
            <div class="commande-info">
              <p>📅 <strong>Date :</strong> {{ formatDate(commande.dateCommande) }}</p>
              <p>💰 <strong>Montant :</strong> {{ formatMontant(commande.montant) }}</p>
            </div>

            <div class="commande-actions">
              <router-link
                  :to="{ name: 'CommandeDetails', params: { commandeId: commande.trackingId } }"
                  class="icon-btn detail"
                  title="Voir les détails"
              >
                <font-awesome-icon :icon="['fas', 'eye']" class="icon icon-yellow" />
              </router-link>

              <router-link
                  :to="{ name: 'EditCommande', params: { commandeId: commande.trackingId } }"
                  class="icon-btn edit"
                  title="Modifier la commande"
              >
                <font-awesome-icon :icon="['fas', 'edit']" class="icon icon-green" />
              </router-link>

              <button
                  @click="deleteCommande(commande.trackingId)"
                  class="icon-btn delete"
                  title="Supprimer la commande"
              >
                <font-awesome-icon :icon="['fas', 'trash']" class="icon icon-red" />
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Base>
</template>

<script setup>
import { ref, onMounted } from "vue";
import Base from "@/views/User/Base.vue";
import CommandeService from "@/services/commandeService";
import UserService from "@/services/UserService";
import Swal from "sweetalert2";

const commandes = ref([]);
const isLoading = ref(true);
const errorMessage = ref("");

const fetchCommandes = async () => {
  try {
    const user = await UserService.getProfile();
    const userTrackingId = user.trackingId;

    const response = await CommandeService.getAll();
    commandes.value = response.filter(
        (commande) => commande.clientTrackingId === userTrackingId
    );
  } catch (error) {
    console.error(error);
    errorMessage.value = "Erreur lors du chargement des commandes.";
  } finally {
    isLoading.value = false;
  }
};

const deleteCommande = async (trackingId) => {
  const result = await Swal.fire({
    title: "Êtes-vous sûr ?",
    text: "Cette commande sera supprimée définitivement !",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Oui, supprimer !",
  });

  if (result.isConfirmed) {
    try {
      await CommandeService.delete(trackingId);
      commandes.value = commandes.value.filter(
          (commande) => commande.trackingId !== trackingId
      );
      await Swal.fire({
        icon: "success",
        title: "Commande supprimée avec succès !",
        showConfirmButton: false,
        timer: 1500,
      });
    } catch {
      Swal.fire({
        icon: "error",
        title: "Erreur",
        text: "Impossible de supprimer la commande.",
      });
    }
  }
};

const formatDate = (dateStr) => new Date(dateStr).toLocaleDateString("fr-FR");

const formatMontant = (montant) => `${montant.toFixed(2)} €`;

onMounted(fetchCommandes);
</script>

<style scoped>
.container {
  max-width: 600px;
  margin: auto;
  padding: 20px;
  background-color: #e6f0ff;
}

.title {
  text-align: center;
  font-size: 26px;
  font-weight: bold;
  color: #2c3e50;
  margin-bottom: 20px;
}

.add-btn-container {
  text-align: right;
  margin-bottom: 15px;
}

.btn.add-btn {
  background-color: #28a745;
  color: white;
  padding: 10px 14px;
  border-radius: 6px;
  text-decoration: none;
  font-weight: bold;
}

.btn.add-btn:hover {
  background-color: #218838;
}

.loading-container {
  text-align: center;
  font-size: 18px;
  color: #2c3e50;
}

.commande-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.commande-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #ffffff;
  border-radius: 10px;
  box-shadow: 0px 3px 6px rgba(0, 0, 0, 0.2);
  padding: 18px;
  font-size: 15px;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.commande-item:hover {
  transform: scale(1.02);
  box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.25);
  background: #f9f9f9;
}

.commande-info {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.commande-actions {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.icon {
  font-size: 22px;
}

.icon-green {
  color: #218838;
}

.icon-yellow {
  color: #d39e00;
}

.icon-red {
  color: #c82333;
}

.icon-btn {
  background: none;
  border: none;
  cursor: pointer;
  padding: 6px;
  transition: background 0.2s ease;
}

.icon-btn:hover {
  background: rgba(0, 0, 0, 0.1);
  border-radius: 5px;
}

.alert-info {
  text-align: center;
  background-color: #d1ecf1;
  color: #0c5460;
  padding: 10px;
  border-radius: 8px;
}

.alert-danger {
  text-align: center;
  background-color: #f8d7da;
  color: #721c24;
  padding: 10px;
  border-radius: 8px;
}
</style>
