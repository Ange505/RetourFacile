import { createRouter, createWebHistory } from 'vue-router'

// Importer tes composants de page
import Home from '@/views/Home.vue'
import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";
import Dashboard from '@/views/User/Dashboard.vue'
import ProfilePage from '@/views/User/ProfilePage.vue';
import EditProfile from "@/views/User/EditProfile.vue";
import PendingUsers from "@/views/Admin/PendingUsers.vue";
import ActivatedUsers from "@/views/Admin/ActivatedUsers.vue";
import AllUsers from "@/views/Admin/AllUsers.vue";


import CommandeList from "@/views/Admin/CommandeList.vue";
import MesCommandes from "@/views/User/commande/MesCommandes.vue";
import AddCommande from "@/views/User/commande/AddCommande.vue";
import CommandeDetails from "@/views/User/commande/CommandeDetails.vue";
import EditCommande from "@/views/User/commande/EditCommande.vue";
import MesReclamations from "@/views/User/Reclamation/MesReclamations.vue";
import AddReclamation from "@/views/User/Reclamation/AddReclamation.vue";
import ReclamationDetails from "@/views/User/Reclamation/ReclamationDetails.vue";
import EditReclamation from "@/views/User/Reclamation/EditReclamation.vue";
//import ToutesReclamationsPage from "@/views/Admin/ToutesReclamationsPage.vue";


// 📌 Importation des composants pour la gestion des équipes

const routes = [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    {
        path: '/dashboard',
        name: 'Dashboard',
        component: Dashboard,
        meta: { requiresAuth: true }
    },
    {
        path: '/profile',
        name: 'ProfilePage',
        component: ProfilePage,
        meta: { requiresAuth: true }
    },
    {
        path: '/edit-profile',
        name: 'EditProfile',
        component: EditProfile,
        meta: { requiresAuth: true }
    },
    {
        path: '/users/pending',
        name: 'PendingUsers',
        component: PendingUsers,
        meta: { requiresAuth: true }
    },
    {
        path: '/users/activated',
        name: 'ActivatedUsers',
        component: ActivatedUsers,
        meta: { requiresAuth: true }
    },
    {
        path: '/users',
        name: 'AllUsers',
        component: AllUsers,
        meta: { requiresAuth: true }
    },


    // Routes pour ADMIN
    { path: "/toutes-commandes", component: CommandeList, meta: { requiresAuth: true, role: "admin" } },
    { path: '/mes-commandes', name: 'MesCommandes', component: MesCommandes },
    { path: '/mes-commandes/add', name: 'AddCommande', component: AddCommande },
    { path: '/mes-commandes/:commandeId', name: 'CommandeDetails', component: CommandeDetails },
    { path: '/mes-commandes/:commandeId/edit', name: 'EditCommande', component: EditCommande },

    { path: "/toutes-reclamations", component: MesReclamations, meta: { requiresAuth: true, role: "admin" } },
    { path: '/mes-reclamations', name: 'MesReclamations', component: MesReclamations },
    { path: '/mes-reclamations/add', name: 'AddReclamation', component: AddReclamation },
    { path: '/mes-reclamations/:reclamationId', name: 'ReclamationDetails', component: ReclamationDetails },
    { path: '/mes-reclamations/:reclamationId/edit', name: 'EditReclamation', component: EditReclamation },

];

const router = createRouter({
    history: createWebHistory(),
    routes
});

// 🔐 Vérifier l'authentification avant chaque changement de route
router.beforeEach((to, from, next) => {
    const isAuthenticated = localStorage.getItem('authToken');
    if (to.meta.requiresAuth && !isAuthenticated) {
        next('/login');
    } else {
        next();
    }
});

export default router;
