<template>
  <Base>
    <div class="container">
      <h2 class="title">✏️ Modifier Réclamation</h2>

      <form @submit.prevent="updateReclamation" v-if="!isLoading && !errorMessage" class="form">
        <div class="form-group">
          <label>Description</label>
          <textarea v-model="description" required></textarea>
        </div>

        <div class="form-group">
          <label>Statut</label>
          <select v-model="statut" required>
            <option value="EN_COURS">En cours</option>
            <option value="RESOLUE">Résolue</option>
            <option value="REJETEE">Rejetée</option>
          </select>
        </div>

        <button type="submit" class="btn submit-btn">Mettre à jour</button>
      </form>

      <div v-if="isLoading" class="loading-container">
        <div class="spinner"></div>
        <p>Chargement...</p>
      </div>

      <div v-else-if="errorMessage" class="alert alert-danger">
        {{ errorMessage }}
      </div>
    </div>
  </Base>
</template>

<script setup>
import { ref, onMounted } from "vue";
import Base from "@/views/User/Base.vue";
import ReclamationService from "@/services/ReclamationService";
import Swal from "sweetalert2";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();

const description = ref("");
const statut = ref("");
const isLoading = ref(true);
const errorMessage = ref("");

const fetchReclamation = async () => {
  try {
    const data = await ReclamationService.get(route.params.reclamationId);
    description.value = data.description;
    statut.value = data.statut;
  } catch (error) {
    console.error(error);
    errorMessage.value = "Erreur lors du chargement de la réclamation.";
  } finally {
    isLoading.value = false;
  }
};

const updateReclamation = async () => {
  try {
    const updatedData = {
      description: description.value,
      statut: statut.value,
    };

    await ReclamationService.update(route.params.reclamationId, updatedData);

    Swal.fire({
      icon: "success",
      title: "Réclamation mise à jour avec succès !",
      showConfirmButton: false,
      timer: 1500,
    });

    router.push({ name: "MesReclamations" });
  } catch (error) {
    console.error(error);
    Swal.fire({
      icon: "error",
      title: "Erreur",
      text: "Impossible de mettre à jour la réclamation.",
    });
  }
};

onMounted(fetchReclamation);
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
  background-color: #ffc107;
  color: black;
  padding: 10px 14px;
  border-radius: 6px;
  border: none;
  cursor: pointer;
  width: 100%;
}
.submit-btn:hover {
  background-color: #e0a800;
}
.alert-danger {
  text-align: center;
  background-color: #f8d7da;
  color: #721c24;
  padding: 10px;
  border-radius: 8px;
}
</style>
