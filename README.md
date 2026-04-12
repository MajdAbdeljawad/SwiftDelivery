🍽️ FOOD-ORDERING SYSTEM - Plateforme de Gestion
Description du Projet 🗒️

FOOD-ORDERING SYSTEM est une application distribuée basée sur une architecture microservices, développée avec Spring Boot et Symfony, visant à simplifier la gestion d’un système complet de restauration (utilisateurs, restaurants, commandes, paiements, promotions).

Contexte : ✨

Avec l’évolution des systèmes distribués, les architectures monolithiques deviennent difficiles à maintenir et à scaler.
Ce projet a été conçu pour répondre à ces problématiques en adoptant une architecture microservices permettant :

une meilleure scalabilité 📈
une maintenance simplifiée 🔧
une répartition claire des responsabilités 🧩

La plateforme permet de gérer efficacement l’ensemble du cycle d’un service de restauration, depuis l’inscription des utilisateurs jusqu’au paiement et aux promotions.

Fonctionnalités principales :
🔐 Gestion des utilisateurs
Authentification sécurisée via Keycloak
Gestion des rôles (admin / client)
CRUD utilisateurs
🍽️ Gestion des restaurants
CRUD restaurants
Consultation des menus
Recherche et filtrage
📦 Gestion des produits
CRUD produits
Association avec restaurants
Gestion des catégories
🛒 Gestion des commandes
Création et suivi des commandes
Statuts (en attente, validée, livrée)
Historique des commandes
💳 Gestion des paiements
Traitement des paiements
Suivi des transactions
Sécurisation des opérations
🎁 Gestion des promotions & offres
CRUD promotions
Gestion des réductions
Application automatique sur commandes
🏗️ Architecture du Projet

Le système est composé de plusieurs services indépendants :

🔹 Backend
API Gateway : point d’entrée unique pour toutes les requêtes
Server Config : centralisation des configurations
Eureka Service : découverte des microservices
Keycloak : authentification et gestion des accès
🔹 Microservices
Service Utilisateurs → Spring Boot + MySQL
Service Restaurants → Spring Boot + MySQL
Service Produits → Spring Boot + MySQL
Service Commandes → Spring Boot + H2
Service Paiements → Spring Boot + MySQL
Service Promotions → Symfony + MySql
🔹 Frontend
Interface utilisateur connectée via API Gateway
🐳 Dockerisation

Le projet a été entièrement conteneurisé avec Docker afin de faciliter :

le déploiement 🚀
la portabilité 📦
l’isolation des services 🔒
📁 Contenu Docker
Un Dockerfile pour chaque microservice
Un fichier docker-compose.yml pour orchestrer tous les services
Installation
Prérequis 📦
Docker
Docker Compose
Git

Fonctionnalités clés
🔐 Authentification

Authentification centralisée avec Keycloak utilisant des tokens JWT pour sécuriser les communications entre services.

🔄 Communication inter-services
Communication REST entre microservices
Découverte dynamique via Eureka
Routage via API Gateway
🛒 Processus de commande
L’utilisateur passe une commande
Le service commande traite la demande
Le service paiement valide la transaction
Les promotions sont appliquées automatiquement

Technologies
Backend
Spring Boot
Symfony
Spring Cloud (Eureka, Gateway, Config)
Sécurité
Keycloak
JWT
Base de données
MySQL
MongoDB
H2
DevOps
Docker
Docker Compose
Contributeurs 👥
Mohamed Benalija — Gestion des utilisateurs
Yasmine El Amri — Gestion des restaurants
Amal Trad — Gestion des produits
Majd Abdeljaoued — Gestion des commandes
Baya Khouini — Gestion des paiements
Ashkinez Jamaleddin — Gestion deslivraison
groupe: — Gestion ddes offres
🚀 Améliorations futures
CI/CD (GitHub Actions)
Monitoring (Prometheus / Grafana)
Logging centralisé (ELK)
Déploiement Kubernetes
