<template>
  <Base>
    <div class="container">
      <h2 class="title">📝 Détail de la Réclamation</h2>

      <div v-if="isLoading" class="loading-container">
        <div class="spinner"></div>
        <p>Chargement...</p>
      </div>

      <div v-else-if="errorMessage" class="alert alert-danger">
        {{ errorMessage }}
      </div>

      <div v-else>
        <p><strong>📄 Description :</strong> {{ reclamation.description }}</p>
        <p><strong>📅 Date :</strong> {{ formatDate(reclamation.dateReclamation) }}</p>
        <p><strong>🚚 Commande liée :</strong> {{ reclamation.commandeTrackingId }}</p>
        <p><strong>👤 Client :</strong> {{ reclamation.clientTrackingId }}</p>
        <p><strong>⚙️ Statut :</strong> {{ reclamation.statut }}</p>
      </div>
    </div>
  </Base>
</template>

<script setup>
import { ref, onMounted } from "vue";
import Base from "@/views/User/Base.vue";
import ReclamationService from "@/services/ReclamationService";
import { useRoute } from "vue-router";

const route = useRoute();
const reclamation = ref({});
const isLoading = ref(true);
const errorMessage = ref("");

const fetchReclamation = async () => {
  try {
    reclamation.value = await ReclamationService.get(route.params.reclamationId);
  } catch (error) {
    console.error(error);
    errorMessage.value = "Erreur lors du chargement de la réclamation.";
  } finally {
    isLoading.value = false;
  }
};

const formatDate = (dateStr) => new Date(dateStr).toLocaleDateString("fr-FR");

onMounted(fetchReclamation);
</script>

<style scoped>
.container {
  max-width: 600px;
  margin: auto;
  padding: 20px;
  background-color: #f5f5f5;
}
.title {
  text-align: center;
  font-size: 26px;
  font-weight: bold;
  margin-bottom: 20px;
}
.alert-danger {
  text-align: center;
  background-color: #f8d7da;
  color: #721c24;
  padding: 10px;
  border-radius: 8px;
}
</style>
