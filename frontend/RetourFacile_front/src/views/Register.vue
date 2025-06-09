<template>
  <div class="register-container">
    <div class="register-card">
      <div class="form-section">
        <div class="logo">
          <span class="e-letter">E</span>-VoteX
        </div>
        <form @submit.prevent="register" class="register-form">
          <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
          <div v-if="successMessage" class="success-message">{{ successMessage }}</div>
          <div class="input-group">
            <label for="name">Nom</label>
            <input type="text" id="name" v-model="name" required>
          </div>
          <div class="input-group">
            <label for="email">Email</label>
            <input type="email" id="email" v-model="email" required>
          </div>
          <div class="input-group">
            <label for="numTel">Numéro de téléphone</label>
            <input type="tel" id="numTel" v-model="numTel" required>
          </div>
          <div class="input-group">
            <label for="password">Mot de passe</label>
            <input type="password" id="password" v-model="password" required>
            <ul class="text-danger small mt-1">
              <li v-if="password.length > 0 && password.length < 8">🔴 Minimum 8 caractères&nbsp;</li>
              <li v-if="password.length > 0 && !/[A-Z]/.test(password)">🔴 Une majuscule requise</li>
              <li v-if="password.length > 0 && !/[a-z]/.test(password)">🔴 Une minuscule requise</li>
              <li v-if="password.length > 0 && !/[0-9]/.test(password)">🔴 Un chiffre requis &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
            </ul>
            <!--p class="small text-muted">Votre mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule et un chiffre.</p-->
          </div>
          <div class="input-group">
            <label for="confirmPassword">Confirmer le mot de passe</label>
            <input type="password" id="confirmPassword" v-model="confirmPassword" required />
            <p v-if="confirmPassword.length > 0 && !passwordsMatch" class="text-danger">❌ Les mots de passe ne correspondent pas.</p>
          </div>
          <button type="submit" class="register-button" :disabled="!isPasswordValid">S'inscrire</button>
        </form>
        <div class="login-link">
          Déjà un compte ? <router-link to="/login">Connexion</router-link>
        </div>
      </div>
      <div class="image-section">
        <img src="../assets/accueil/images/register1.jpg" alt="Register Image" class="register-image" />
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import AuthService from "@/services/authService";

export default {
  name: "RegisterPage",
  setup() {
    const router = useRouter();
    const name = ref("");
    const email = ref("");
    const numTel = ref("");
    const password = ref("");
    const confirmPassword = ref("");
    const errorMessage = ref("");
    const successMessage = ref("");

    const passwordsMatch = computed(() => password.value === confirmPassword.value);

    const isPasswordValid = computed(() => {
      return (
          password.value.length >= 8 &&
          /[A-Z]/.test(password.value) &&
          /[a-z]/.test(password.value) &&
          /[0-9]/.test(password.value) &&
          passwordsMatch.value
      );
    });

    const register = async () => {
      if (!passwordsMatch.value) {
        errorMessage.value = "Les mots de passe ne correspondent pas";
        return;
      }
      if (!isPasswordValid.value) {
        errorMessage.value = "Le mot de passe ne respecte pas les critères";
        return;
      }
      try {
        const userData = {
          username: name.value,
          email: email.value,
          numTel: numTel.value,
          password: password.value,
        };
        await AuthService.register(userData);
        successMessage.value = "Inscription réussie ! Redirection...";
        setTimeout(() => router.push("/login"), 2000);
      } catch (error) {
        errorMessage.value = "Erreur lors de l'inscription";
        console.error(error);
      }
    };

    return {
      name,
      email,
      numTel,
      password,
      confirmPassword,
      errorMessage,
      successMessage,
      register,
      passwordsMatch,
      isPasswordValid
    };
  },
};
</script>

<style scoped>
.container {
  max-width: 600px;
}
.input-group {
  margin-bottom: 1rem;
}
.error-message {
  color: red;
}
.success-message {
  color: green;
}
.text-danger {
  color: red;
}
.small {
  font-size: 0.875rem;
}
.register-container {
  display: flex;
  height: 100vh;
  justify-content: center;
  align-items: center;
  background: #f0f0f0;
  padding: 20px;
}

.register-card {
  display: flex;
  width: 80%;
  max-width: 1200px;
  background: white;
  border-radius: 12px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  animation: fadeIn 0.5s ease-in-out;
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
  margin-bottom: 15px;
}

.input-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #555;
}

.input-group input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 20px;
  font-size: 16px;
}

.register-button {
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

.register-button:hover {
  background: #ff7f00;
}

.image-section {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}

.register-image {
  width: 100%;
  height: auto;
  max-height: 100%;
  object-fit: cover;
}

.login-link {
  margin-top: 15px;
  font-size: 14px;
}

.login-link a {
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
