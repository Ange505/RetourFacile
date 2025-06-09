<template>
  <Base>
    <div class="container">
      <h2 class="title">➕ Nouvelle Réclamation</h2>

      <form @submit.prevent="addReclamation" class="form">
        <div class="form-group">
          <label>Description</label>
          <textarea v-model="description" required></textarea>
        </div>

        <div class="form-group">
          <label>Commande liée</label>
          <select v-model="selectedCommandeId" required>
            <option value="" disabled>-- Choisissez une commande --</option>
            <option v-for="commande in commandes" :key="commande.trackingId" :value="commande.trackingId">
              Commande du {{ formatDate(commande.dateCommande) }} - {{ formatMontant(commande.montant) }}
            </option>
          </select>
        </div>

        <button type="submit" class="btn submit-btn">Ajouter</button>
      </form>

      <div v-if="isLoading" class="loading-container">
        <div class="spinner"></div>
        <p>Chargement de vos commandes...</p>
      </div>

      <div v-if="errorMessage" class="alert alert-danger">
        {{ errorMessage }}
      </div>
    </div>
  </Base>
</template>

<script setup>
import { ref, onMounted } from "vue";
import Base from "@/views/User/Base.vue";
import ReclamationService from "@/services/ReclamationService";
import CommandeService from "@/services/commandeService";
import UserService from "@/services/UserService";
import Swal from "sweetalert2";
import { useRouter } from "vue-router";

const description = ref("");
const selectedCommandeId = ref("");
const commandes = ref([]);
const isLoading = ref(true);
const errorMessage = ref("");
const router = useRouter();

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

const addReclamation = async () => {
  try {
    const user = await UserService.getProfile();

    const newReclamation = {
      description: description.value,
      commandeTrackingId: selectedCommandeId.value,
      clientTrackingId: user.trackingId,
      dateReclamation: new Date().toISOString().split('T')[0],
      statut: 'EN_COURS',
    };

    console.log("Réclamation envoyée :", newReclamation);

    await ReclamationService.create(newReclamation);

    Swal.fire({
      icon: "success",
      title: "Réclamation ajoutée avec succès !",
      showConfirmButton: false,
      timer: 1500,
    });

    router.push({ name: "MesReclamations" });
  } catch (error) {
    console.error(error);
    Swal.fire({
      icon: "error",
      title: "Erreur",
      text: "Impossible d'ajouter la réclamation.",
    });
  }
};

const formatDate = (dateStr) => new Date(dateStr).toLocaleDateString("fr-FR");
const formatMontant = (montant) => `${montant.toFixed(2)} €`;

onMounted(fetchCommandes);
</script>

<style scoped>
.container {
  max-width: 500px;
  margin: auto;
  padding: 20px;
}
.title {
  text-align: center;
  margin-bottom: 20px;
}
.form-group {
  margin-bottom: 15px;
}
textarea, select {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}
.submit-btn {
  background-color: #28a745;
  color: white;
  padding: 10px 14px;
  border-radius: 6px;
  border: none;
  cursor: pointer;
  width: 100%;
}
.submit-btn:hover {
  background-color: #218838;
}
.loading-container {
  text-align: center;
  font-size: 18px;
  color: #2c3e50;
}
.alert-danger {
  text-align: center;
  background-color: #f8d7da;
  color: #721c24;
  padding: 10px;
  border-radius: 8px;
}
</style>
