<script setup>
// Importation des styles
import '@/assets/admin/images/logos/favicon.png';
import '@/assets/admin/css/styles.min.css';

// Importation des fichiers JavaScript
import '@/assets/admin/libs/jquery/dist/jquery.min.js';        // jQuery
import '@/assets/admin/libs/bootstrap/dist/js/bootstrap.bundle.min.js'; // Bootstrap JS
import '@/assets/admin/js/sidebarmenu.js';                       // Sidebarmenu
import '@/assets/admin/js/app.min.js';                           // App JS
import '@/assets/admin/libs/apexcharts/dist/apexcharts.min.js'; // ApexCharts
import '@/assets/admin/libs/simplebar/dist/simplebar.js';       // Simplebar
import '@/assets/admin/js/dashboard.js';
//import '@/assets/admin/js/custom.js';             // Custom JS

import Sidebar from "@/components/admin/Sidebar.vue";
import Header from "@/components/admin/Header.vue";
</script>

<template>
  <div class="page-wrapper" id="main-wrapper" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
       data-sidebar-position="fixed" data-header-position="fixed">
    <!-- Sidebar Start -->
    <aside class="left-sidebar">
      <!-- Sidebar scroll-->
      <Sidebar/>
      <!-- End Sidebar scroll-->
    </aside>
    <!--  Sidebar End -->
    <!--  Main wrapper -->
    <div class="body-wrapper">
      <!--  Header Start -->
      <Header/>
      <div class="container-fluid">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>