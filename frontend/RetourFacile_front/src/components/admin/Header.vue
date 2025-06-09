<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import AuthService from '@/services/AuthService';
import UserService from '@/services/UserService';
import defaultAvatar from '@/assets/admin/images/profile/default_avatar.jpg';

const router = useRouter();
const photoProfil = ref(defaultAvatar);

// Récupérer la photo de profil depuis UserService
const fetchUserProfile = async () => {
  try {
    const response = await UserService.getProfile();
    photoProfil.value = response.photoProfil || defaultAvatar;
  } catch (error) {
    photoProfil.value = defaultAvatar;
  }
};

onMounted(() => {
  fetchUserProfile();
});

// Déconnexion
const logout = () => {
  AuthService.logout();
  router.push('/');
};
</script>

<template>
  <header class="app-header">
    <nav class="navbar navbar-expand-lg navbar-light">
      <ul class="navbar-nav">
        <li class="nav-item d-block d-xl-none">
          <a class="nav-link sidebartoggler nav-icon-hover" id="headerCollapse" href="javascript:void(0)">
            <i class="ti ti-menu-2"></i>
          </a>
        </li>
      </ul>
      <div class="navbar-collapse justify-content-end px-0" id="navbarNav">
        <ul class="navbar-nav flex-row ms-auto align-items-center justify-content-end">
          <li class="nav-item dropdown">
            <a class="nav-link nav-icon-hover" href="javascript:void(0)" id="drop2" data-bs-toggle="dropdown"
               aria-expanded="false">
              <img :src="photoProfil" alt="Photo de profil" class="rounded-circle profile-img" />
            </a>
            <div class="dropdown-menu dropdown-menu-end dropdown-menu-animate-up" aria-labelledby="drop2">
              <div class="message-body">
                <router-link to="/profile" class="d-flex align-items-center gap-2 dropdown-item">
                  <i class="ti ti-user fs-6"></i>
                  <p class="mb-0 fs-3">Mon Profil</p>
                </router-link>
                <a href="javascript:void(0)" @click="logout" class="btn btn-outline-primary mx-3 mt-2 d-block">Déconnexion</a>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </nav>
  </header>
</template>

<style scoped>
.profile-img {
  width: 35px;
  height: 35px;
  object-fit: cover; /* Permet de garder les proportions de l'image */
}
</style>
