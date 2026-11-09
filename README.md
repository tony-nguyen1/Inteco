# Inteco

Application mobile Android d'agence d'intérim, développée en Java dans le cadre de l'UE Programmation Mobile du M1 Génie Logiciel (Faculté des Sciences, Université de Montpellier, 2024).

Inteco met en relation des chercheurs d'emploi et des entreprises. Chaque utilisateur s'inscrit avec son rôle et accède à une interface adaptée à ses besoins.

**Démonstration vidéo :** https://youtu.be/t7TYONoK8DU

**Rapport :** disponible dans le dossier [`rapport/`](rapport/)

## Fonctionnalités

### Comptes et authentification
- Inscription séparée pour les chercheurs d'emploi et pour les entreprises, en plusieurs étapes avec contrôle des champs obligatoires
- Connexion unique pour les deux types de comptes
- Réinitialisation du mot de passe par e-mail
- Authentification gérée avec Firebase Authentication

### Côté chercheur d'emploi
- Page d'accueil avec le suivi des candidatures (en attente, acceptées, refusées)
- Recherche d'offres par mots-clés du titre et par localisation (ville ou pays), même sans être connecté
- Remplissage automatique de la localisation grâce au GPS
- Sauvegarde et suppression d'offres favorites
- Candidature en quelques clics avec envoi du CV
- Modification du profil

### Côté entreprise
- Création d'offres d'emploi (type de poste, contrat, durée, expérience, qualification, localisation, catégorie)
- Liste des offres publiées avec leur statut (ouverte ou fermée) et le nombre de candidats
- Consultation du profil des candidats et téléchargement de leur CV
- Acceptation ou refus des candidatures
- Récapitulatif des candidats acceptés, avec contact par e-mail ou par téléphone
- Modification du profil de l'entreprise (coordonnées, site web, réseaux sociaux)

### Ergonomie
- Barre de navigation adaptée à chaque type d'utilisateur, avec une activité principale et des fragments
- Affichage du temps écoulé depuis la publication des offres (« il y a 1 jour », « il y a 1 mois »)

## Technologies
- Java
- Android SDK (Activities, Fragments, RecyclerView, LiveData, BottomNavigationView)
- Firebase Authentication
- Cloud Firestore
- Firebase Storage (envoi et téléchargement des CV)
- Localisation GPS
- Gradle (Kotlin DSL)
- Git et GitHub

## Structure du dépôt
- `app/` : code source de l'application
- `rapport/` : rapport du projet
- `ProjetAndroid2024.pdf` : sujet du projet

## Lancer le projet
1. Cloner le dépôt
   ```bash
   git clone https://github.com/tony-nguyen1/Inteco.git
   ```
2. Ouvrir le dossier dans Android Studio
3. Ajouter le fichier `google-services.json` d'un projet Firebase dans le dossier `app/`
4. Laisser Gradle synchroniser les dépendances
5. Lancer l'application sur un émulateur ou un appareil Android


## Auteurs
- Mohamad Satea Almallouhi
- Tony Nguyen
