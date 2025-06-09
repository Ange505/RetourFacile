<script>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import AuthService from "@/services/authService"; // Assurez-vous que le service Auth est bien importé

export default {
  name: "LoginPage",
  setup() {
    const router = useRouter();
    const email = ref("");
    const password = ref("");
    const errorMessage = ref("");
    const isLoading = ref(false);  // Gérer l'état de chargement du bouton

    // Vérifier si l'utilisateur est déjà connecté
    /*onMounted(() => {
      const token = localStorage.getItem("authToken");
      if (token) {
        router.push("/dashboard");
      }
    });*/

    const login = () => {
      isLoading.value = true;  // Désactiver le bouton lors de la soumission
      errorMessage.value = "";  // Réinitialiser les messages d'erreur

      AuthService.login({ email: email.value, password: password.value })
          .then((response) => {
            if (response.data.token) {
              localStorage.setItem("authToken", response.data.token); // Stocker le token dans localStorage
              router.push("/dashboard");
            } else {
              errorMessage.value = "Erreur de connexion : aucun token reçu.";
            }
          })
          .catch((error) => {
            console.error("Erreur de connexion :", error);
            errorMessage.value = "Échec de la connexion. Vérifiez vos identifiants.";
          })
          .finally(() => {
            isLoading.value = false;  // Réactiver le bouton après la soumission
          });
    };

    return { email, password, errorMessage, isLoading, login };
  }
};
</script>

<template>
  <div class="login-container">
    <div class="login-card">
      <div class="image-section">
        <img src="../assets/accueil/images/login.jpeg" alt="Login Image" class="login-image" />
      </div>
      <div class="form-section">
        <div class="logo">
          <span class="e-letter">E</span>-VoteX
        </div>
        <form @submit.prevent="login" class="login-form">
          <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
          <div class="input-group">
            <label for="email">Email</label>
            <div class="input-wrapper">
              <Mail class="input-icon" />
              <input type="email" id="email" v-model="email" required>
            </div>
          </div>
          <div class="input-group">
            <label for="password">Mot de passe</label>
            <div class="input-wrapper">
              <Lock class="input-icon" />
              <input type="password" id="password" v-model="password" required>
            </div>
          </div>
          <button :disabled="isLoading" type="submit" class="login-button">
            <span v-if="isLoading">Chargement...</span>
            <span v-else>Connexion</span>
          </button>
        </form>
        <div class="register-link">
          Pas encore de compte ? <router-link to="/register">Inscription</router-link>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped>
.login-container {
  display: flex;
  height: 100vh;
  justify-content: center;
  align-items: center;
  background: white;
  padding: 20px;
}

.login-card {
  display: flex;
  width: 80%;
  max-width: 1000px;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 12px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  animation: fadeIn 0.5s ease-in-out;
  align-items: center;
}

.form-section {
  flex: 1;
  padding: 50px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
}

.logo {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #007bff;
}

.e-letter {
  color: #ff7f00;
  font-size: 36px;
  animation: highlightE 2s infinite alternate;
}

.input-group {
  margin-bottom: 20px;
  text-align: left;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.input-group label {
  align-self: flex-start;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.input-wrapper {
  display: flex;
  align-items: center;
  background: #f0f0f0;
  padding: 10px;
  border-radius: 20px;
  width: 100%;
}

.input-icon {
  margin-right: 10px;
  color: #666;
}

.input-wrapper input {
  flex: 1;
  border: none;
  background: transparent;
  outline: none;
  font-size: 16px;
  padding: 5px;
}

.login-button {
  width: 100%;
  padding: 14px;
  background: #007bff;
  border: none;
  color: white;
  font-size: 18px;
  cursor: pointer;
  border-radius: 20px;
  transition: background 0.3s ease;
}

.login-button:hover {
  background: #ff7f00;
}

.image-section {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-image {
  width: 100%;
  height: auto;
  max-height: 100%;
  object-fit: cover;
}

.register-link {
  margin-top: 15px;
  font-size: 14px;
}

.register-link a {
  color: #007bff;
  text-decoration: none;
  font-weight: bold;
}

.error-message {
  color: red;
  text-align: center;
  margin-bottom: 10px;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes bounce {
  from {
    transform: translateY(0);
  }
  to {
    transform: translateY(-5px);
  }
}
</style>
