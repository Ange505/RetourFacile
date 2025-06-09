<template>
  <Base>
    <div class="container">
      <h2 class="title">📦 Liste des Commandes</h2>

      <div v-if="isLoading" class="loading-container">
        <div class="spinner"></div>
        <p>Chargement des commandes...</p>
      </div>

      <div v-else-if="errorMessage" class="alert alert-danger">
        {{ errorMessage }}
      </div>

      <div v-else>
        <div v-if="commandes.length === 0" class="alert alert-info">
          Aucune commande trouvée.
        </div>

        <div class="commande-list">
          <div v-for="commande in commandes" :key="commande.trackingId" class="commande-item">
            <div class="commande-info">
              <p>📅 <strong>Date :</strong> {{ formatDate(commande.dateCommande) }}</p>
              <p>💰 <strong>Montant :</strong> {{ formatMontant(commande.montant) }}</p>
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

const commandes = ref([]);
const isLoading = ref(true);
const errorMessage = ref("");

const fetchCommandes = async () => {
  try {
    const response = await CommandeService.getAll();
    commandes.value = response.data;
  } catch (error) {
    console.error(error);
    errorMessage.value = "Erreur lors du chargement des commandes.";
  } finally {
    isLoading.value = false;
  }
};

const formatDate = (dateStr) => {
  return new Date(dateStr).toLocaleDateString("fr-FR");
};

const formatMontant = (montant) => {
  return `${montant.toFixed(2)} €`;
};

onMounted(fetchCommandes);
</script>

<style scoped>
.container {
  max-width: 600px;
  margin: auto;
  padding: 20px;
  background-color: #f0f4ff;
}

.title {
  text-align: center;
  font-size: 26px;
  font-weight: bold;
  color: #34495e;
  margin-bottom: 20px;
}

.loading-container {
  text-align: center;
  font-size: 18px;
  color: #34495e;
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
</style>
