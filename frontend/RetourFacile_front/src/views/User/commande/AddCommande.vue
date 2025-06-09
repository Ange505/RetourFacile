<template>
  <Base>
    <div class="container">
      <h2 class="title">➕ Nouvelle Commande</h2>

      <form @submit.prevent="addCommande" class="form">
        <div class="form-group">
          <label>Date de la commande</label>
          <input
              type="date"
              v-model="dateCommande"
              :max="today"
              required
          />
        </div>

        <div class="form-group">
          <label>Montant</label>
          <input
              type="number"
              step="0.01"
              v-model="montant"
              required
          />
        </div>

        <button type="submit" class="btn submit-btn">Ajouter</button>
      </form>
    </div>
  </Base>
</template>

<script>
import { ref } from "vue";
import Base from "@/views/User/Base.vue";
import CommandeService from "@/services/commandeService";
import UserService from "@/services/UserService";
import Swal from "sweetalert2";
import { useRouter } from "vue-router";

export default {
  components: { Base },
  setup() {
    const router = useRouter();

    // Calcul de la date du jour au format YYYY-MM-DD
    const today = new Date().toISOString().slice(0, 10);

    const dateCommande = ref(today); // Initialisée avec la date du jour
    const montant = ref("");
    const userTrackingId = ref(null);

    const addCommande = async () => {
      try {
        const user = await UserService.getProfile();
        userTrackingId.value = user.trackingId;

        const newCommande = {
          dateCommande: dateCommande.value,
          montant: parseFloat(montant.value),
          clientTrackingId: userTrackingId.value,
        };

        console.log("Commande envoyée :", newCommande);

        await CommandeService.create(newCommande);

        Swal.fire({
          icon: "success",
          title: "Commande ajoutée avec succès !",
          showConfirmButton: false,
          timer: 1500,
        });

        router.push({ name: "MesCommandes" });
      } catch (error) {
        console.error(
            "Erreur lors de l'ajout de la commande :",
            error.response ? error.response.data : error.message
        );

        Swal.fire({
          icon: "error",
          title: "Erreur",
          text: "Impossible d'ajouter la commande.",
        });
      }
    };

    return { dateCommande, montant, today, addCommande };
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
</style>
