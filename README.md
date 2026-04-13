# FOOD-ORDERING SYSTEM - Plateforme de Gestion

Description du projet

FOOD-ORDERING SYSTEM est une application distribuee basee sur une architecture microservices, developpee avec Spring Boot et Symfony, visant a simplifier la gestion d'un systeme complet de restauration (utilisateurs, restaurants, commandes, paiements, promotions).

## Contexte

Avec l'evolution des systemes distribues, les architectures monolithiques deviennent difficiles a maintenir et a faire evoluer.
Ce projet adopte une architecture microservices permettant:

- une meilleure scalabilite
- une maintenance simplifiee
- une repartition claire des responsabilites

La plateforme couvre l'ensemble du cycle d'un service de restauration, de l'inscription des utilisateurs jusqu'au paiement et aux promotions.

## Fonctionnalites principales

- Gestion des utilisateurs: authentification securisee via Keycloak, gestion des roles, CRUD utilisateurs
- Gestion des restaurants: CRUD restaurants, consultation des menus, recherche et filtrage
- Gestion des produits: CRUD produits, association avec restaurants, gestion des categories
- Gestion des commandes: creation et suivi, statuts, historique
-Service Marchandise :crud , rechrche ,tri ,
- Gestion des offres: CRUD promotions, gestion des reductions, application automatique

## Architecture du projet

Le systeme est compose de plusieurs services independants.

- Backend: API Gateway, Server Config, Eureka Service, Keycloak
- Microservices:
	- Service Utilisateurs (Spring Boot + MySQL)
	- Service Restaurants (Spring Boot + MySQL)
	- Service Produits (Spring Boot + MySQL)
	- Service Commandes (Spring Boot + H2)
	-Service Marchandise (spring boot+ mySQL)
	- Service Promotions (Symfony + H2)
- Frontend: interface utilisateur connectee via API Gateway

## Frontend Angular (order-microservice)

Template Angular inspire d'une plateforme de livraison type Glovo.

Pages admin ajoutees:
- Gestion des utilisateurs
- Gestion des restaurants
- Gestion des produits
- Gestion des commandes
	-Service Marchandise (spring boot+ mySQL)
- Gestion des livraisons
- Gestion des  offres

### Lancer le frontend

```bash
npm install
npm start
```

## Dockerisation

Le projet est conteneurise avec Docker pour faciliter le deploiement, la portabilite et l'isolation des services.

- Un Dockerfile pour chaque microservice
- Un fichier docker-compose.yml pour orchestrer tous les services

## Prerequis

- Docker
- Docker Compose
- Git

## Technologies

- Backend: Spring Boot, Symfony, Spring Cloud (Eureka, Gateway, Config)
- Securite: Keycloak, JWT
- Bases de donnees: MySQL, MongoDB, H2
- DevOps: Docker, Docker Compose

## Contributeurs

- Mohamed Benalija - Gestion des utilisateurs
- Yasmine El Amri - Gestion des restaurants
- Amal Trad - Gestion des produits
- Majd Abdeljaoued - Gestion des commandes
- Baya Khouini - Service Marchandise 

- Ashkinez Jamaleddin - Gestion des livraisons
- Groupe - Gestion des offres

## Ameliorations futures

- CI/CD (GitHub Actions)
- Monitoring (Prometheus / Grafana)
- Logging centralise (ELK)
- Deploiement Kubernetes
