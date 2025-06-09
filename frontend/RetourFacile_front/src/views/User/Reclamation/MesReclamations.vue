<template>
  <Base>
    <div class="container">
      <h2 class="title">📋 Mes Réclamations</h2>

      <div v-if="isLoading" class="loading-container">
        <div class="spinner"></div>
        <p>Chargement de vos réclamations...</p>
      </div>

      <div v-else-if="errorMessage" class="alert alert-danger">
        {{ errorMessage }}
      </div>

      <div v-else>
        <div class="add-btn-container">
          <router-link :to="{ name: 'AddReclamation' }" class="btn add-btn">
            ➕ Nouvelle Réclamation
          </router-link>
        </div>

        <div v-if="reclamations.length === 0" class="alert alert-info">
          Vous n'avez soumis aucune réclamation.
        </div>

        <div class="reclamation-list" v-else>
          <div
              v-for="reclamation in reclamations"
              :key="reclamation.trackingId"
              class="reclamation-item"
          >
            <div class="reclamation-info">
              <p>📅 <strong>Date :</strong> {{ formatDate(reclamation.dateReclamation) }}</p>
              <p>📝 <strong>Description :</strong> {{ reclamation.description }}</p>
              <p>📦 <strong>Date Commande :</strong> {{ formatDate(commandesDetails[reclamation.commandeTrackingId]?.dateCommande) }}</p>
              <p>💰 <strong>Montant Commande :</strong> {{ formatPrice(commandesDetails[reclamation.commandeTrackingId]?.montant) }}</p>
              <p>🔄 <strong>Statut :</strong> {{ reclamation.statut }}</p>
            </div>


            <div class="reclamation-actions">
              <router-link
                  :to="{ name: 'ReclamationDetails', params: { reclamationId: reclamation.trackingId } }"
                  class="icon-btn detail"
                  title="Voir les détails"
              >
                👁️ Voir
              </router-link>

              <router-link
                  :to="{ name: 'EditReclamation', params: { reclamationId: reclamation.trackingId } }"
                  class="icon-btn edit"
                  title="Modifier la réclamation"
              >
                ✏️ Modifier
              </router-link>

              <button
                  @click="deleteReclamation(reclamation.trackingId)"
                  class="icon-btn delete"
                  title="Supprimer la réclamation"
              >
                🗑️ Supprimer
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
import ReclamationService from "@/services/ReclamationService";
import CommandeService from "@/services/CommandeService";
import UserService from "@/services/UserService";
import Swal from "sweetalert2";

const reclamations = ref([]);
const commandesDetails = ref({});
const isLoading = ref(true);
const errorMessage = ref("");

const fetchReclamations = async () => {
  try {
    const user = await UserService.getProfile();
    const userTrackingId = user.trackingId;

    const response = await ReclamationService.getAll();

    const userReclamations = response.filter(
        (reclamation) => reclamation.clientTrackingId === userTrackingId
    );

    reclamations.value = userReclamations;

    // On charge les commandes associées
    for (const reclamation of userReclamations) {
      const commandeTrackingId = reclamation.commandeTrackingId;

      if (commandeTrackingId /*&& !commandesDetails.value[commandeTrackingId]*/) {
        try {
          const commande = await CommandeService.get(commandeTrackingId);
          commandesDetails.value[commandeTrackingId] = {
            dateCommande: commande.dateCommande,
            montant: commande.montant,
          };
        } catch (error) {
          console.error("Erreur chargement commande", commandeTrackingId, error);
          commandesDetails.value[commandeTrackingId] = {
            dateCommande: null,
            montant: null,
          };
        }
      }
    }
  } catch (error) {
    console.error(error);
    errorMessage.value = "Erreur lors du chargement des réclamations.";
  } finally {
    isLoading.value = false;
  }
};

const deleteReclamation = async (trackingId) => {
  const result = await Swal.fire({
    title: "Êtes-vous sûr ?",
    text: "Cette réclamation sera supprimée définitivement !",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Oui, supprimer !",
  });

  if (result.isConfirmed) {
    try {
      await ReclamationService.delete(trackingId);
      reclamations.value = reclamations.value.filter(
          (reclamation) => reclamation.trackingId !== trackingId
      );
      await Swal.fire({
        icon: "success",
        title: "Réclamation supprimée avec succès !",
        showConfirmButton: false,
        timer: 1500,
      });
    } catch {
      Swal.fire({
        icon: "error",
        title: "Erreur",
        text: "Impossible de supprimer la réclamation.",
      });
    }
  }
};

const formatDate = (dateStr) =>
    dateStr ? new Date(dateStr).toLocaleDateString("fr-FR") : "N/A";

const formatPrice = (price) =>
    price != null
        ? price.toLocaleString("fr-FR", { style: "currency", currency: "EUR" })
        : "N/A";

onMounted(fetchReclamations);
</script>

<style scoped>
.container {
  max-width: 650px;
  margin: 40px auto;
  padding: 25px 30px;
  background-color: #e8f0fe;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgb(32 33 36 / 15%);
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.title {
  text-align: center;
  font-size: 28px;
  font-weight: 700;
  color: #1a2e52;
  margin-bottom: 30px;
  user-select: none;
}

.add-btn-container {
  text-align: right;
  margin-bottom: 20px;
}

.btn.add-btn {
  background: linear-gradient(135deg, #28a745, #218838);
  color: #fff;
  padding: 12px 20px;
  border-radius: 30px;
  font-weight: 600;
  font-size: 16px;
  box-shadow: 0 4px 15px rgb(40 167 69 / 0.4);
  text-decoration: none;
  display: inline-block;
  transition: all 0.3s ease;
  user-select: none;
}
.btn.add-btn:hover {
  background: linear-gradient(135deg, #218838, #19692c);
  box-shadow: 0 6px 20px rgb(33 136 56 / 0.6);
  transform: translateY(-2px);
}

.loading-container {
  text-align: center;
  font-size: 20px;
  color: #1a2e52;
  user-select: none;
}

.spinner {
  margin: 0 auto 15px;
  width: 42px;
  height: 42px;
  border: 5px solid #a0c4ff;
  border-top: 5px solid #1a2e52;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.reclamation-list {
  display: flex;
  flex-direction: column;
  gap: 18px;
}

.reclamation-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  border-radius: 14px;
  box-shadow: 0 4px 12px rgb(32 33 36 / 0.1);
  padding: 20px 25px;
  font-size: 15.5px;
  color: #2c3e50;
  transition: box-shadow 0.3s ease, transform 0.3s ease;
}
.reclamation-item:hover {
  box-shadow: 0 8px 24px rgb(32 33 36 / 0.15);
  transform: translateY(-4px);
}

.reclamation-info {
  display: flex;
  flex-direction: column;
  gap: 7px;
  max-width: 70%;
}

.reclamation-info p strong {
  color: #33475b;
}

.reclamation-actions {
  display: flex;
  flex-direction: column;
  gap: 12px;
  min-width: 110px;
  align-items: flex-end;
}

.icon-btn {
  font-size: 17px;
  font-weight: 600;
  padding: 10px 18px;
  border-radius: 30px;
  border: 2px solid transparent;
  cursor: pointer;
  transition: all 0.25s ease;
  user-select: none;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  white-space: nowrap;
  text-decoration: none;
  color: white;
  box-shadow: 0 4px 12px rgb(0 0 0 / 0.1);
}

/* Voir (Bleu) */
.icon-btn.detail {
  background-color: #3498db;
  border-color: #2980b9;
}
.icon-btn.detail:hover {
  background-color: #2980b9;
  box-shadow: 0 6px 16px rgba(41, 128, 185, 0.6);
  transform: translateY(-2px);
}

/* Modifier (Orange) */
.icon-btn.edit {
  background-color: #f39c12;
  border-color: #d68910;
}
.icon-btn.edit:hover {
  background-color: #d68910;
  box-shadow: 0 6px 16px rgba(214, 137, 16, 0.6);
  transform: translateY(-2px);
}

/* Supprimer (Rouge) */
.icon-btn.delete {
  background-color: #e74c3c;
  border-color: #c0392b;
}
.icon-btn.delete:hover {
  background-color: #c0392b;
  box-shadow: 0 6px 16px rgba(192, 57, 43, 0.6);
  transform: translateY(-2px);
}

.alert-info {
  text-align: center;
  background-color: #d0e7ff;
  color: #154580;
  padding: 14px 18px;
  border-radius: 12px;
  font-weight: 600;
  user-select: none;
}

.alert-danger {
  text-align: center;
  background-color: #fce8e8;
  color: #b42b2b;
  padding: 14px 18px;
  border-radius: 12px;
  font-weight: 600;
  user-select: none;
}
</style>
