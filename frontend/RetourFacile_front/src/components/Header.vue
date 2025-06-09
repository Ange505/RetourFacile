<script setup>
</script>

<template>
  <div class="header_section">
    <div class="container-fluid">
      <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="index.html">
          <img src="@/assets/accueil/images/logo.png" />
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item active">
              <a class="nav-link" href="index.html">Accueil</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="retours.html">Faire un retour</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="suivi.html">Suivi de réclamation</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="faq.html">FAQ</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="contact.html">Contact</a>
            </li>
          </ul>
          <form class="form-inline my-2 my-lg-0">
            <div class="login_bt d-flex align-items-center">
              <a href="/login" class="d-flex align-items-center mr-3">
                <span>Connexion</span>
                <i class="fa fa-user ml-2" aria-hidden="true"></i>
              </a>
              <a href="/register" class="d-flex align-items-center mr-3">
                <span>S'inscrire</span>
                <i class="fa fa-user ml-2" aria-hidden="true"></i>
              </a>
              <button class="btn my-2 my-sm-0 nav_search-btn" type="submit">
                <i class="fa fa-search" aria-hidden="true"></i>
              </button>
            </div>
          </form>
        </div>
      </nav>
    </div>

    <!-- banner section start -->
    <div class="banner_section layout_padding">
      <div class="container">
        <div class="row">
          <div class="col-md-6">
            <h1 class="banner_taital">GÉREZ VOS <span style="color: #2b2b2b;">RETOURS & RÉCLAMATIONS</span></h1>
            <p class="banner_text">Simplifiez vos démarches de retour et suivez l’état de vos réclamations en ligne.</p>
            <div class="started_text">
              <a href="retours.html">Faire un retour</a>
            </div>
            <div class="play_icon">
              <img src="@/assets/accueil/images/play-icon.png" />
            </div>
          </div>
          <div class="col-md-6">
            <div id="banner_slider" class="carousel slide" data-ride="carousel">
              <div class="carousel-inner">
                <div class="carousel-item active">
                  <div class="banner_img">
                    <img src="@/assets/accueil/images/banner-img.png" />
                  </div>
                </div>
                <div class="carousel-item">
                  <div class="banner_img">
                    <img src="@/assets/accueil/images/banner-img.png" alt="" />
                  </div>
                </div>
                <div class="carousel-item">
                  <div class="banner_img">
                    <img src="@/assets/accueil/images/banner-img.png" alt="" />
                  </div>
                </div>
              </div>
              <a class="carousel-control-prev" href="#banner_slider" role="button" data-slide="prev">
                <i class="fa fa-angle-left"></i>
              </a>
              <a class="carousel-control-next" href="#banner_slider" role="button" data-slide="next">
                <i class="fa fa-angle-right"></i>
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- banner section end -->
  </div>
</template>

<style scoped>
.login_bt {
  display: flex;
  align-items: center;
}

.login_bt a {
  display: flex;
  align-items: center;
  background-color: #2b2b2b;
  color: #fff;
  padding: 8px 16px;
  margin-right: 10px;
  border-radius: 4px;
  text-decoration: none;
  transition: background-color 0.3s ease;
  font-weight: 500;
}

.login_bt a i {
  margin-left: 8px;
}

.login_bt a:hover {
  background-color: #444;
}

.nav_search-btn {
  background-color: transparent;
  border: none;
  color: #2b2b2b;
  font-size: 18px;
  cursor: pointer;
  transition: color 0.3s ease;
}

.nav_search-btn:hover {
  color: #000;
}

</style>
