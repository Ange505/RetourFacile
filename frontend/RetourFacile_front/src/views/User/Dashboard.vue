<script setup>
import Base from "@/views/User/Base.vue";
import { ref } from "vue";
import { Pie, Bar } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  CategoryScale,
  LinearScale,
  BarElement
} from "chart.js";

ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale, LinearScale, BarElement);

// Données fictives pour la démonstration
const totalReclamations = ref(250);
const enAttente = ref(80);
const enCours = ref(50);
const resolues = ref(120);

const statuts = ref([
  { name: "En attente", count: enAttente.value },
  { name: "En cours de traitement", count: enCours.value },
  { name: "Résolue", count: resolues.value },
]);

const typesReclamation = ref([
  { name: "Produit défectueux", count: 100 },
  { name: "Erreur de commande", count: 60 },
  { name: "Rétractation client", count: 50 },
  { name: "Autre", count: 40 },
]);

const pieChartData = {
  labels: statuts.value.map((s) => s.name),
  datasets: [{
    data: statuts.value.map((s) => s.count),
    backgroundColor: ["#FF6384", "#36A2EB", "#4BC0C0"],
  }]
};

const barChartData = {
  labels: typesReclamation.value.map((t) => t.name),
  datasets: [{
    label: "Nombre de réclamations",
    data: typesReclamation.value.map((t) => t.count),
    backgroundColor: "#36A2EB"
  }]
};
</script>

<template>
  <Base>
    <div class="dashboard-container">
      <div class="card total-reclamations">
        <h5>Total des réclamations</h5>
        <h2>{{ totalReclamations }}</h2>
        <p>Nombre total de réclamations enregistrées</p>
      </div>

      <div class="card statut en-attente">
        <h5>En attente</h5>
        <h2>{{ enAttente }}</h2>
        <p>Réclamations non traitées</p>
      </div>

      <div class="card statut en-cours">
        <h5>En cours de traitement</h5>
        <h2>{{ enCours }}</h2>
        <p>Réclamations en cours de résolution</p>
      </div>

      <div class="card statut resolues">
        <h5>Résolues</h5>
        <h2>{{ resolues }}</h2>
        <p>Réclamations traitées avec succès</p>
      </div>

      <div class="card chart-container">
        <h5>Répartition par statut</h5>
        <Pie :data="pieChartData" />
      </div>

      <div class="card chart-container">
        <h5>Réclamations par type</h5>
        <Bar :data="barChartData" />
      </div>
    </div>
  </Base>
</template>

<style scoped>
.dashboard-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  padding: 20px;
  background: linear-gradient(135deg, #f6f7fc, #e9eff8);
  border-radius: 10px;
}

.card {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
  border: 2px solid #ddd;
}

.card h5 {
  color: #333;
  font-size: 1.1rem;
}

.card h2 {
  font-size: 2rem;
  color: #2e3a59;
  margin-bottom: 10px;
}

.card p {
  color: #6c757d;
}

.chart-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.card:hover {
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  transform: translateY(-5px);
  transition: all 0.3s ease;
}

/* Optionnel : couleurs personnalisées pour les cards */
.total-reclamations {
  border-color: #4BC0C0;
}

.en-attente {
  border-color: #FF6384;
}

.en-cours {
  border-color: #FFCE56;
}

.resolues {
  border-color: #36A2EB;
}
</style>
