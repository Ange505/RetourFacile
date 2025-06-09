<template>
  <Base>
    <div class="container" v-if="commandeLoaded">
      <h2 class="title">📄 Détail de la Commande</h2>

      <div class="detail-item">
        <strong>Date de la commande :</strong> {{ formatDate(commande.dateCommande) }}
      </div>

      <div class="detail-item">
        <strong>Montant :</strong> {{ formatMontant(commande.montant) }}
      </div>
    </div>

    <div v-else class="loading-container">
      Chargement de la commande...
    </div>
  </Base>
</template>

<script>
import { ref, onMounted } from "vue";
import Base from "@/views/User/Base.vue";
import CommandeService from "@/services/commandeService";
import { useRoute } from "vue-router";

export default {
  components: { Base },
  setup() {
    const route = useRoute();
    const commande = ref({});
    const commandeLoaded = ref(false);

    const fetchCommande = async () => {
      try {
        const data = await CommandeService.get(route.params.commandeId);
        commande.value = data;
        commandeLoaded.value = true;
      } catch (error) {
        console.error(error);
      }
    };

    const formatDate = (dateStr) => {
      return new Date(dateStr).toLocaleDateString("fr-FR");
    };

    const formatMontant = (montant) => {
      return `${montant.toFixed(2)} €`;
    };

    onMounted(fetchCommande);

    return { commande, commandeLoaded, formatDate, formatMontant };
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

.detail-item {
  margin-bottom: 12px;
  font-size: 16px;
}

.loading-container {
  text-align: center;
  margin-top: 20px;
  font-size: 18px;
}
</style>
