<template>
  <Base>
    <div class="container">
      <h2 class="title">✏️ Modifier la Commande</h2>

      <form @submit.prevent="updateCommande" class="form" v-if="commandeLoaded">
        <div class="form-group">
          <label>Date de la commande</label>
          <input type="date" v-model="dateCommande" required />
        </div>

        <div class="form-group">
          <label>Montant</label>
          <input type="number" step="0.01" v-model="montant" required />
        </div>

        <button type="submit" class="btn submit-btn">Mettre à jour</button>
      </form>

      <div v-else class="loading-container">
        Chargement de la commande...
      </div>
    </div>
  </Base>
</template>

<script>
import { ref, onMounted } from "vue";
import Base from "@/views/User/Base.vue";
import CommandeService from "@/services/commandeService";
import Swal from "sweetalert2";
import { useRouter, useRoute } from "vue-router";

export default {
  components: { Base },
  setup() {
    const router = useRouter();
    const route = useRoute();

    const dateCommande = ref("");
    const montant = ref("");
    const commandeLoaded = ref(false);

    const fetchCommande = async () => {
      try {
        const commande = await CommandeService.getCommande(route.params.commandeId);
        dateCommande.value = commande.dateCommande;
        montant.value = commande.montant;
        commandeLoaded.value = true;
      } catch (error) {
        Swal.fire({
          icon: "error",
          title: "Erreur",
          text: "Impossible de charger la commande.",
        });
      }
    };

    const updateCommande = async () => {
      try {
        const updatedCommande = {
          dateCommande: dateCommande.value,
          montant: parseFloat(montant.value),
        };

        await CommandeService.updateCommande(route.params.commandeId, updatedCommande);

        Swal.fire({
          icon: "success",
          title: "Commande mise à jour avec succès !",
          showConfirmButton: false,
          timer: 1500,
        });
        router.push({ name: "MesCommandes" });
      } catch (error) {
        Swal.fire({
          icon: "error",
          title: "Erreur",
          text: "Impossible de mettre à jour la commande.",
        });
      }
    };

    onMounted(fetchCommande);

    return { dateCommande, montant, updateCommande, commandeLoaded };
  },
};
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

input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}

.submit-btn {
  background-color: #ffc107;
  color: white;
  padding: 10px 14px;
  border-radius: 6px;
  border: none;
  cursor: pointer;
  width: 100%;
}

.submit-btn:hover {
  background-color: #e0a800;
}

.loading-container {
  text-align: center;
  margin-top: 20px;
  font-size: 18px;
}
</style>
