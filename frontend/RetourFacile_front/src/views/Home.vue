<script setup>
  // Import CSS
  import '@/assets/accueil/css/bootstrap.min.css';
  import '@/assets/accueil/css/style.css';
  import '@/assets/accueil/css/responsive.css';
  import '@/assets/accueil/css/jquery.mCustomScrollbar.min.css';
  //import 'https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css';
  //import 'https://fonts.googleapis.com/css2?family=Sen:wght@400;700;800&display=swap';

  // Import JS
  import '@/assets/accueil/js/jquery.min.js';
  //import '@/assets/accueil/js/popper.min.js';
  //import '@/assets/accueil/js/bootstrap.bundle.min.js';
  import '@/assets/accueil/js/jquery-3.0.0.min.js';
  //import '@/assets/accueil/js/plugin.js';
  import '@/assets/accueil/js/jquery.mCustomScrollbar.concat.min.js';
  import '@/assets/accueil/js/custom.js';

import Footer from "@/components/Footer.vue";
import Header from "@/components/Header.vue";

// Initialisation de Material Select (si ton projet utilise jQuery + un plugin Material Select)
$(document).ready(function() {
  $('.mdb-select').materialSelect();
  $('.select-wrapper.md-form.md-outline input.select-dropdown').on('focus blur', function () {
    $(this).closest('.select-outline').find('label').toggleClass('active');
    $(this).closest('.select-outline').find('.caret').toggleClass('active');
  });
});

</script>

<template>
  <Header/>
  <!-- header section end -->
  <!-- select box section start -->
  <div class="container">
    <div class="select_box_section">
      <div class="select_box_main">
        <div class="row">
          <div class="col-md-4 select-outline">
            <input type="text" class="enter_bt" placeholder="Enter Keywords" name="Enter Keywords">
          </div>
          <div class="col-md-4 select-outline">
            <select class="mdb-select md-form md-outline colorful-select dropdown-primary">
              <option value="" disabled selected>All Categoris</option>
              <option value="1">Option 1</option>
              <option value="2">Option 2</option>
              <option value="3">Option 3</option>
            </select>
          </div>
          <div class="col-md-4 select-outline">
            <select class="mdb-select md-form md-outline colorful-select dropdown-primary">
              <option value="" disabled selected>Your Lucation</option>
              <option value="1">Option 1</option>
              <option value="2">Option 2</option>
              <option value="3">Option 3</option>
            </select>
          </div>
        </div>
      </div>
      <div class="search_btn"><a href="#">Search Now</a></div>
    </div>
  </div>
  <!-- services section start -->
  <div class="services_section layout_padding">
    <div class="container">
      <div class="row">
        <div class="col-md-4">
          <h1 class="services_taital">Ads Services</h1>
          <p class=" services_text">Page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to</p>
          <div class="readmore_btn"><a href="#">Read More</a></div>
        </div>
        <div class="col-md-8">
          <h1 class="services_taital">
            <div class="services_box">
              <div class="services_box_left">
                <h5 class="fashion_text">Automotive</h5>
                <div><img src="@/assets/accueil/images/icon-1.png"></div>
              </div>
              <div class="services_box_right">
                <h5 class="fashion_text">Fashion</h5>
                <div><img src="@/assets/accueil/images/icon-2.png"></div>
              </div>
            </div>
            <div class="services_box_2">
              <div class="services_box_left">
                <h5 class="fashion_text">Automotive</h5>
                <div><img src="@/assets/accueil/images/icon-3.png"></div>
              </div>
              <div class="services_box_right">
                <h5 class="fashion_text">Fashion</h5>
                <div><img src="@/assets/accueil/images/icon-4.png"></div>
              </div>
            </div>
          </h1>
        </div>
      </div>
    </div>
  </div>
  <!-- services section end -->
  <!-- shop section start -->
  <div class="blog_section layout_padding">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <h1 class="blog_taital">PREMIUM ADVERTISEMENT</h1>
        </div>
      </div>
      <div class="blog_section_2">
        <div id="main_slider" class="carousel slide" data-ride="carousel">
          <div class="carousel-inner">
            <div class="carousel-item active">
              <div class="row">
                <div class="col-md-4">
                  <div class="blog_img"><img src="@/assets/accueil/images/blog-img.png"></div>
                  <h6 class="number_text">01</h6>
                  <div class="callnow_btn">
                    <div class="chat_bt"><a href="#">Chat Now</a></div>
                    <div class="call_bt active"><a href="#">Call Now</a></div>
                  </div>
                </div>
                <div class="col-md-4">
                  <div class="blog_img"><img src="@/assets/accueil/images/blog-img1.png"></div>
                  <h6 class="number_text">02</h6>
                  <div class="callnow_btn">
                    <div class="chat_bt"><a href="#">Chat Now</a></div>
                    <div class="call_bt active"><a href="#">Call Now</a></div>
                  </div>
                </div>
                <div class="col-md-4">
                  <div class="blog_img"><img src="@/assets/accueil/images/blog-img2.png"></div>
                  <h6 class="number_text">03</h6>
                  <div class="callnow_btn">
                    <div class="chat_bt"><a href="#">Chat Now</a></div>
                    <div class="call_bt active"><a href="#">Call Now</a></div>
                  </div>
                </div>
              </div>
            </div>
            <div class="carousel-item">
              <div class="row">
                <div class="col-md-4">
                  <div class="blog_img"><img src="@/assets/accueil/images/blog-img.png"></div>
                  <h6 class="number_text">01</h6>
                  <div class="callnow_btn">
                    <div class="chat_bt"><a href="#">Chat Now</a></div>
                    <div class="call_bt active"><a href="#">Call Now</a></div>
                  </div>
                </div>
                <div class="col-md-4">
                  <div class="blog_img"><img src="@/assets/accueil/images/blog-img1.png"></div>
                  <h6 class="number_text">02</h6>
                  <div class="callnow_btn">
                    <div class="chat_bt"><a href="#">Chat Now</a></div>
                    <div class="call_bt active"><a href="#">Call Now</a></div>
                  </div>
                </div>
                <div class="col-md-4">
                  <div class="blog_img"><img src="@/assets/accueil/images/blog-img2.png"></div>
                  <h6 class="number_text">03</h6>
                  <div class="callnow_btn">
                    <div class="chat_bt"><a href="#">Chat Now</a></div>
                    <div class="call_bt active"><a href="#">Call Now</a></div>
                  </div>
                </div>
              </div>
            </div>
            <div class="carousel-item">
              <div class="row">
                <div class="col-md-4">
                  <div class="blog_img"><img src="@/assets/accueil/images/blog-img.png"></div>
                  <h6 class="number_text">01</h6>
                  <div class="callnow_btn">
                    <div class="chat_bt"><a href="#">Chat Now</a></div>
                    <div class="call_bt active"><a href="#">Call Now</a></div>
                  </div>
                </div>
                <div class="col-md-4">
                  <div class="blog_img"><img src="@/assets/accueil/images/blog-img1.png"></div>
                  <h6 class="number_text">02</h6>
                  <div class="callnow_btn">
                    <div class="chat_bt"><a href="#">Chat Now</a></div>
                    <div class="call_bt active"><a href="#">Call Now</a></div>
                  </div>
                </div>
                <div class="col-md-4">
                  <div class="blog_img"><img src="@/assets/accueil/images/blog-img2.png"></div>
                  <h6 class="number_text">03</h6>
                  <div class="callnow_btn">
                    <div class="chat_bt"><a href="#">Chat Now</a></div>
                    <div class="call_bt active"><a href="#">Call Now</a></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <a class="carousel-control-prev" href="#main_slider" role="button" data-slide="prev">
            <i class="fa fa-angle-left"></i>
          </a>
          <a class="carousel-control-next" href="#main_slider" role="button" data-slide="next">
            <i class="fa fa-angle-right"></i>
          </a>
        </div>
      </div>
    </div>
  </div>
  <!-- shop section end -->
  <!-- about section start -->
  <div class="about_section layout_padding">
    <div class="container-fluid">
      <div class="row">
        <div class="col-md-6 padding_left0">
          <div class="about_taital_main">
            <h1 class="about_taital">About Us</h1>
            <h6 class="about_text">ARE YOU READY FOR THE POSTING YOUR ADS</h6>
            <div class="btn_main">
              <div class="started_bt"><a href="#">Get Started</a></div>
              <div class="buy_bt active"><a href="#">Buy Now</a></div>
            </div>
          </div>
        </div>
        <div class="col-md-6">
          <div class="about_img"><img src="@/assets/accueil/images/about-img.png"></div>
        </div>
      </div>
    </div>
  </div>
  <!-- about section end -->
  <!-- pricing section start -->
  <div class="pricing_section layout_padding">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <h1 class="pricing_taital">FIND Price A PLAN</h1>
        </div>
      </div>
      <div class="pricing_section_2">
        <div class="row">
          <div class="col-md-4">
            <div class="pricing_box">
              <h6 class="regular_text">Regular</h6>
              <div class="taital_main">
                <h3 class="dolor_text">$</h3>
                <h1 class="dolor_text_1">30</h1>
                <p class="month_text">/Per Month</p>
              </div>
              <p class="using_text">using 'Content here, content here', making it look like readable</p>
              <div class="readmore_bt"><a  href="#">Read More</a></div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="pricing_box active">
              <h6 class="regular_text">Standra</h6>
              <div class="taital_main">
                <h3 class="dolor_text">$</h3>
                <h1 class="dolor_text_1">30</h1>
                <p class="month_text">/Per Month</p>
              </div>
              <p class="using_text">using 'Content here, content here', making it look like readable</p>
              <div class="readmore_bt active"><a  href="#">Read More</a></div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="pricing_box">
              <h6 class="regular_text">Prenium</h6>
              <div class="taital_main">
                <h3 class="dolor_text">$</h3>
                <h1 class="dolor_text_1">30</h1>
                <p class="month_text">/Per Month</p>
              </div>
              <p class="using_text">using 'Content here, content here', making it look like readable</p>
              <div class="readmore_bt"><a  href="#">Read More</a></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- pricing section end -->
  <!-- customer section end -->
  <div class="customer_section layout_padding">
    <div class="container">
      <div class="row">
        <div class="col-sm-12">
          <h1 class="customer_taital">What Is says Our customers</h1>
        </div>
      </div>
    </div>
    <div id="my_slider" class="carousel slide" data-ride="carousel">
      <div class="carousel-inner">
        <div class="carousel-item active">
          <div class="customer_section_2">
            <div class="container">
              <div class="row">
                <div class="col-md-12">
                  <div class="box_main">
                    <div class="customer_main">
                      <p class="enim_text">has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors </p>
                      <div class="customer_left">
                        <div class="customer_img"><img src="@/assets/accueil/images/customer-img.png"></div>
                      </div>
                      <div class="customer_right">
                        <h3 class="customer_name">Content</h3>
                        <p class="web_text">And web page </p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="carousel-item">
          <div class="customer_section_2">
            <div class="container">
              <div class="row">
                <div class="col-md-12">
                  <div class="box_main">
                    <div class="customer_main">
                      <p class="enim_text">has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors </p>
                      <div class="customer_left">
                        <div class="customer_img"><img src="@/assets/accueil/images/customer-img.png"></div>
                      </div>
                      <div class="customer_right">
                        <h3 class="customer_name">Content</h3>
                        <p class="web_text">And web page </p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="carousel-item">
          <div class="customer_section_2">
            <div class="container">
              <div class="row">
                <div class="col-md-12">
                  <div class="box_main">
                    <div class="customer_main">
                      <p class="enim_text">has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors </p>
                      <div class="customer_left">
                        <div class="customer_img"><img src="@/assets/accueil/images/customer-img.png"></div>
                      </div>
                      <div class="customer_right">
                        <h3 class="customer_name">Content</h3>
                        <p class="web_text">And web page </p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <a class="carousel-control-prev" href="#my_slider" role="button" data-slide="prev">
        <i class="fa fa-angle-left"></i>
      </a>
      <a class="carousel-control-next" href="#my_slider" role="button" data-slide="next">
        <i class="fa fa-angle-right"></i>
      </a>
    </div>
  </div>
  <!-- customer section end -->
  <!-- contact section start -->
  <div class="contact_section layout_padding">
    <div class="container">
      <div class="row">
        <div class="col-sm-12">
          <h1 class="contact_taital contact_heading">Contact Us</h1>
        </div>
      </div>
      <div class="contact_section_2">
        <div class="row">
          <div class="col-md-12">
            <div class="mail_section map_form_container">
              <form action="">
                <input type="text" class="mail_text" placeholder="Your Name" name="Your Name">
                <input type="text" class="mail_text" placeholder="Email" name="Email">
                <textarea class="massage-bt" placeholder="Massage" rows="5" id="comment" name="Massage"></textarea>
                <div class="contact_btn_main">
                  <div class="send_bt active"><a href="#">Send</a></div>
                  <div class="map_bt"><a href="#" id="showMap">Map</a></div>
                </div>
              </form>
              <div class="map_main map_container">
                <div class="map-responsive">
                  <iframe src="https://www.google.com/maps/embed/v1/place?key=AIzaSyA0s1a7phLN0iaD6-UE7m4qP-z21pH0eSc&amp;q=Eiffel+Tower+Paris+France" width="600" height="368" frameborder="0" style="border:0; width: 100%;" allowfullscreen=""></iframe>
                  <div class="btn_main">
                    <div class="map_bt d-flex justify-content-center w-100 map_center"><a href="#" id="showForm">Form</a></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- contact section end -->
  <!-- footer section start -->
  <Footer/>
  <!-- footer section end -->
  <!-- copyright section start -->
  <div class="copyright_section">
    <div class="container">
      <p class="copyright_text">2020 All Rights Reserved. Design by <a href="https://html.design">Free Html Templates</a> Distribution by <a href="https://themewagon.com">ThemeWagon</a></p>
    </div>
  </div>
</template>
<style scoped>
/* Vos styles locaux ici */
</style>
