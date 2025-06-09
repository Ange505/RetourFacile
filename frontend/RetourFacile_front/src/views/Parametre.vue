<template>
  <Base>
    <div class="settings-container">
      <h2 class="title">Paramètres</h2>

      <!-- Section Profil -->
      <div class="card">
        <h3>Profil utilisateur</h3>
        <div class="field">
          <label>Nom :</label>
          <span>Jean Dupont</span>
        </div>
        <div class="field">
          <label>Email :</label>
          <span>jean.dupont@example.com</span>
        </div>
        <div class="field">
          <label>Langue :</label>
          <select v-model="settings.langue" class="input">
            <option value="fr">Français</option>
            <option value="en">Anglais</option>
          </select>
        </div>
      </div>

      <!-- Section Préférences -->
      <div class="card">
        <h3>Préférences d'affichage</h3>
        <div class="toggle">
          <label>Mode sombre :</label>
          <input type="checkbox" v-model="settings.darkMode">
        </div>
        <div class="toggle">
          <label>Recevoir des notifications :</label>
          <input type="checkbox" v-model="settings.notifications">
        </div>
      </div>

      <!-- Section Sécurité -->
      <div class="card">
        <h3>Sécurité</h3>
        <button class="btn">Modifier le mot de passe</button>
        <button class="btn secondary">Gérer les sessions actives</button>
      </div>
    </div>
  </Base>
</template>

<script>
import { ref } from "vue";
import Base from "@/views/User/Base.vue";

export default {
  components: { Base },
  setup() {
    const settings = ref({
      langue: "fr",
      darkMode: false,
      notifications: true,
    });

    return { settings };
  }
};
</script>

<style scoped>
.settings-container {
  max-width: 600px;
  margin: 40px auto;
  padding: 20px;
}

.title {
  text-align: center;
  color: #d46e6e;
  font-size: 26px;
  margin-bottom: 20px;
}

.card {
  background: #fff8f2;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

h3 {
  color: #d46e6e;
  margin-bottom: 15px;
}

.field {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.input {
  border: 1px solid #ddd;
  padding: 5px;
  border-radius: 8px;
}

.toggle {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.btn {
  background: #ff9800;
  color: white;
  padding: 10px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  width: 100%;
  margin-top: 10px;
}

.btn.secondary {
  background: #f57c00;
}
</style>
