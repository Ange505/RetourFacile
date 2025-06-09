<script setup>
import { ref, onMounted } from 'vue';
import UserService from '@/services/UserService';

const username = ref('');
const email = ref('');
const numTel = ref('');
const isLoading = ref(true);
const errorMessage = ref('');
const isAdmin = ref(false);  // Déterminera si l'utilisateur est un administrateur

// Récupère les données de l'utilisateur au montage du composant
onMounted(() => {
  fetchUserData();
});

// Méthode pour récupérer les données de l'utilisateur
async function fetchUserData() {
  isLoading.value = true;
  errorMessage.value = '';

  try {
    const response = await UserService.getProfile();
    //console.log(response);  // Ajoute un log pour vérifier la réponse
    username.value = response.username;
    email.value = response.email;
    numTel.value = response.numTel;

    // Vérifie le rôle de l'utilisateur pour afficher la section admin
    if (response.role === 'ADMIN') {
      isAdmin.value = true;
    }
  } catch (error) {
    errorMessage.value = "Impossible de récupérer les informations. Veuillez réessayer.";
  } finally {
    isLoading.value = false;
  }
}

</script>

<template>
  <aside class="left-sidebar">
    <div>
      <!-- Logo E-VoteX -->
      <div class="brand-logo d-flex align-items-center justify-content-between">
        <router-link to="/" class="text-nowrap logo-img">
          <span class="animated-e">Retour</span>Facile
        </router-link>
        <div class="close-btn d-xl-none d-block sidebartoggler cursor-pointer" id="sidebarCollapse">
          <i class="ti ti-x fs-8"></i>
        </div>
      </div>

      <!-- Navigation de la sidebar -->
      <!-- Navigation de la sidebar -->
      <nav class="sidebar-nav scroll-sidebar" data-simplebar="">
        <ul id="sidebarnav">
          <!-- Section Accueil -->
          <li class="nav-small-cap">
            <i class="ti ti-dots nav-small-cap-icon fs-4"></i>
            <span class="hide-menu">Accueil</span>
          </li>
          <li class="sidebar-item">
            <router-link class="sidebar-link" to="/dashboard" aria-expanded="false">
        <span>
          <i class="ti ti-layout-dashboard"></i>
        </span>
              <span class="hide-menu" style="color: #ffffff;">Tableau de bord</span>
            </router-link>
          </li>

          <!-- Section Client (disponible pour tous les utilisateurs) -->
          <li class="nav-small-cap">
            <i class="ti ti-dots nav-small-cap-icon fs-4"></i>
            <span class="hide-menu">Mes actions</span>
          </li>
          <li class="sidebar-item">
            <router-link class="sidebar-link" to="/mes-commandes" aria-expanded="false">
        <span>
          <i class="ti ti-shopping-cart"></i>
        </span>
              <span class="hide-menu">Mes commandes</span>
            </router-link>
          </li>
          <li class="sidebar-item">
            <router-link class="sidebar-link" to="/mes-reclamations" aria-expanded="false">
        <span>
          <i class="ti ti-alert-circle"></i>
        </span>
              <span class="hide-menu">Mes réclamations</span>
            </router-link>
          </li>

          <!-- Section Administration (visible uniquement pour l'admin) -->
          <li v-if="isAdmin" class="nav-small-cap">
            <i class="ti ti-dots nav-small-cap-icon fs-4"></i>
            <span class="hide-menu">Administration</span>
          </li>
          <li v-if="isAdmin" class="sidebar-item">
            <router-link class="sidebar-link" to="/toutes-commandes" aria-expanded="false">
        <span>
          <i class="ti ti-shopping-cart"></i>
        </span>
              <span class="hide-menu">Toutes les commandes</span>
            </router-link>
          </li>
          <li v-if="isAdmin" class="sidebar-item">
            <router-link class="sidebar-link" to="/toutes-reclamations" aria-expanded="false">
        <span>
          <i class="ti ti-alert-circle"></i>
        </span>
              <span class="hide-menu">Toutes les réclamations</span>
            </router-link>
          </li>
        </ul>
      </nav>
      <!-- Fin de la navigation -->

    </div>
  </aside>
</template>



<style scoped>
/* Styles pour la sidebar */
.left-sidebar {
  width: 260px;
  background: #dfe9ec; /* Couleur de fond claire */
  color: #333; /* Texte sombre pour contraster avec le fond clair */
  height: 100vh;
  position: fixed;
  top: 0;
  left: 0;
  overflow-y: auto;
  transition: all 0.3s ease;
}

/* Logo E-VoteX */
.brand-logo {
  padding: 20px;
  border-bottom: 1px solid #ff7300; /* Bordure avec la nouvelle couleur */
}

.logo-img {
  font-size: 24px;
  font-weight: 700;
  color: #333; /* Texte sombre */
  text-decoration: none;
}

.animated-e {
  color: #FF7300FF; /* Nouvelle couleur pour le "E" */
  font-size: 28px;
  font-weight: 800;
  animation: bounce 1.5s infinite;
}

@keyframes bounce {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-5px);
  }
}

/* Bouton de fermeture */
.close-btn {
  color: #333; /* Texte sombre */
  font-size: 20px;
}

/* Navigation */
.sidebar-nav {
  padding: 20px;
}

.nav-small-cap {
  font-size: 12px;
  color: #FF7300FF; /* Nouvelle couleur pour les titres de section */
  text-transform: uppercase;
  margin-top: 20px;
  margin-bottom: 10px;
}

.sidebar-item {
  margin-bottom: 10px;
}

.sidebar-link {
  display: flex;
  align-items: center;
  padding: 10px;
  color: #333; /* Texte sombre */
  text-decoration: none;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.sidebar-link:hover {
  background: #FF7300FF; /* Nouvelle couleur au survol */
  color: #ffffff; /* Texte blanc au survol */
  transform: translateX(10px); /* Animation de déplacement */
}

.sidebar-link i {
  margin-right: 10px;
  font-size: 18px;
  transition: transform 0.3s ease;
}

.sidebar-link:hover i {
  transform: scale(1.2); /* Animation d'agrandissement des icônes */
}

.hide-menu {
  font-size: 14px;
}

/* Effet de survol pour les éléments actifs */
.router-link-active {
  background: #FF7300FF; /* Nouvelle couleur pour les éléments actifs */
  color: #ffffff; /* Texte blanc pour les éléments actifs */
}

.router-link-active:hover {
  background: #FF7300FF; /* Nouvelle couleur au survol */
}

/* Animation automatique pour les éléments de la sidebar */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateX(-20px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

.sidebar-item {
  animation: fadeIn 0.5s ease-in-out;
  animation-fill-mode: both;
}

.sidebar-item:nth-child(1) { animation-delay: 0.1s; }
.sidebar-item:nth-child(2) { animation-delay: 0.2s; }
.sidebar-item:nth-child(3) { animation-delay: 0.3s; }
.sidebar-item:nth-child(4) { animation-delay: 0.4s; }
.sidebar-item:nth-child(5) { animation-delay: 0.5s; }
.sidebar-item:nth-child(6) { animation-delay: 0.6s; }
.sidebar-item:nth-child(7) { animation-delay: 0.7s; }
.sidebar-item:nth-child(8) { animation-delay: 0.8s; }
.sidebar-item:nth-child(9) { animation-delay: 0.9s; }
.sidebar-item:nth-child(10) { animation-delay: 1s; }
</style>