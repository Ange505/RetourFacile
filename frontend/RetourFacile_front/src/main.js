import { createApp } from 'vue';
import App from './App.vue';
import router from './router';  // Importer le fichier router


// Ajouter les icônes à la bibliothèque

const app = createApp(App);

app.use(router);  // Utiliser le routeur
app.mount('#app');
