# RetourFacile
systeme permettant aux clients de soumettre une reclamation ou un demande de retour produit, et a l'admin de les traiter
Nom du projet : RetourFacile

Description :
RetourFacile est une application web pour gérer les réclamations clients liées aux commandes.
Les utilisateurs peuvent créer, consulter, modifier et supprimer des réclamations associées à leurs commandes.

Technologies utilisées :

    Backend : Spring Boot (Java)

    Base de données : PostgreSQL

    Frontend : Vue.js

    Autres : Lombok, JWT pour l’authentification, Maven

Instructions pour exécuter le projet :

Lancer le backend :

    Cloner le dépôt :
    git clone https://github.com/Ange505/RetourFacile.git
    cd RetourFacile/backend

    Configurer PostgreSQL :

        Créer une base de données nommée "retourfacile"

        Modifier le fichier src/main/resources/application.properties pour renseigner l’URL, utilisateur et mot de passe

    Compiler et lancer le backend :
    mvn clean install
    mvn spring-boot:run
    Le backend sera disponible sur http://localhost:8083

Lancer le frontend :

    Aller dans le dossier frontend :
    cd ../frontend

    Installer les dépendances :
    npm install

    Lancer le serveur de développement :
    npm run serve
    Le frontend sera accessible sur http://localhost:8083 (si configuré) ou sur le port par défaut (souvent 8080).

Remarques / Blocages éventuels :

    Tout fonctionne correctement côté backend.

    Je n’ai pas pu finaliser la fonctionnalité de modification des réclamations côté frontend.
