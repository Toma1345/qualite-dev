# TP Exercice 1 : Analyser l'application

## Tâche 1 : Ségrégation des responsabilités

### 1. Principaux domaines métiers de l'application **Order Flow**

L'architecture de l'application **Order Flow** se compose de plusieurs domaines métiers qui interagissent pour permettre la gestion des commandes, des produits, des stocks, et des comptes clients. Voici les sous-domaines du domaine **Online Shopping Domain** :

##### 1. **Shopping Cart Domain** *(Domaine du Panier)*
- Permet aux clients de gérer leur panier d'achat.

##### 2. **Order Processing Domain** *(Domaine de Traitement des Commandes)*
- Permet aux clients de passer une commande et de suivre son statut.

##### 3. **Product Catalog Domain** *(Domaine du Catalogue de Produits)*
- Gère le catalogue des produits disponibles à la vente.

##### 4. **Product Registry Domain** *(Domaine du Registre de Produits)*
- Gère la liste globale des produits enregistrés dans le système.

##### 5. **Stock Domain** *(Domaine des Stocks)*
- Permet de gérer les stocks des produits et la réservation des articles.

##### 6. **Customer Domain** *(Domaine Client)*
- Permet aux clients de gérer leurs comptes.

##### 7. **Customer Notification Domain** *(Domaine des Notifications Client)*
- Envoie des notifications liées aux événements de commande.

##### 8. **Notification Domain** *(Domaine des Notifications)*
- Envoie des notifications pour des événements généraux, autres que ceux relatifs aux commandes.

##### 9. **Billing Domain** *(Domaine de Facturation)*
- Gère la facturation et les paiements des commandes.

> **Principaux Domaines pour le Flux de Commande**  
> Les domaines les plus centraux dans le **flux de commande** incluent :  
> - **Shopping Cart Domain** : Gère les produits dans le panier.
> - **Order Processing Domain** : Permet le passage et le suivi des commandes.
> - **Stock Domain** : Gère la disponibilité et la réservation des produits.
> - **Customer Domain** : Permet la gestion des comptes clients.

Ces domaines interagissent pour offrir une expérience d'achat fluide et cohérente du panier à la commande, en passant par la gestion des stocks et des comptes clients.

---

### 2. Comment sont conçus les services pour implémenter les domaines métiers

Les services suivent une architecture **Microservices** appliquant le pattern **CQRS** (Command Query Responsibility Segregation). Chaque domaine métier est potentiellement scindé en deux types de services distincts :

1.  **Service de Commande (Write / Domain Service)** : Il héberge la logique métier et les **Agrégats** (ex: `Product` dans le Kernel). Il traite les commandes (actions de modification) et assure la cohérence transactionnelle de l'état.
2.  **Service de Requête (Read / Read Service)** : Il est optimisé pour la lecture. Il écoute les événements émis par le service de commande pour mettre à jour des **Projections** (Vues) spécifiques, offrant ainsi des performances de lecture élevées sans impacter le modèle d'écriture.

### 3. Quelles sont les responsabilités des modules

| Module | Responsabilité Principale | Détails techniques |
| :--- | :--- | :--- |
| **`apps/store-back`** | **BFF (Backend For Frontend)** | API Gateway qui expose les fonctionnalités au front-end et orchestre les appels vers les services métiers (via REST clients). |
| **`apps/store-front`** | **Frontend** | Application Angular fournissant l'interface utilisateur pour les clients. |
| **`libs/kernel`** | **Noyau Métier (Shared Kernel)** | Contient les objets purs du domaine (Agrégats, Value Objects, Identifiants) et les interfaces de repository, sans dépendance aux frameworks externes. |
| **`apps/product-registry-domain-service`** | **Service d'Écriture (Command)** | Gère le cycle de vie des produits (création, mise à jour). Persiste l'état des agrégats et publie les événements via l'Outbox. |
| **`apps/product-registry-read-service`** | **Service de Lecture (Query)** | Consomme les événements pour construire des vues de lecture (projections) et expose des API de recherche performantes. |
| **`libs/bom-platform`** | **Gestion des dépendances** | "Bill of Materials" qui centralise et aligne les versions des librairies (Quarkus, MapStruct, etc.) pour tout le projet. |
| **`libs/cqrs-support`** | **Infrastructure CQRS/ES** | Fournit les briques techniques pour l'Event Driven Architecture : entités `Outbox`, `EventLog` et interfaces `DomainEvent`, `Projector`. |
| **`libs/sql`** | **Migration de données** | Contient les scripts Liquibase pour la création et l'évolution des schémas de base de données. |

---

## Tâche 2 : Identifier les concepts principaux

### 1. Quels sont les concepts principaux utilisés dans l'application **Order Flow**

Les concepts architecturaux majeurs sont :
* **Domain-Driven Design (DDD)** : Utilisation d'Agrégats, Entités et Value Objects pour modéliser le métier au cœur de l'application (`libs/kernel`).
* **CQRS (Command Query Responsibility Segregation)** : Séparation stricte des modèles d'écriture (Command) et de lecture (Query).
* **Event-Driven Architecture (EDA)** : Communication asynchrone entre les services via des événements métiers.
* **Transactional Outbox Pattern** : Garantie de la fiabilité de la publication des événements.
* **Architecture Hexagonale / Oignon** : Isolation du noyau métier (`kernel`) des détails d'infrastructure.

### 2. Comment les concepts principaux sont-ils implémentés dans les différents modules

* **DDD** : Implémenté dans `libs/kernel`. Les agrégats (comme `Product`) encapsulent la logique métier et émettent des événements (`ProductEventV1`).
* **CQRS** : Matérialisé par la séparation physique des applications. `product-registry-domain-service` gère l'écriture et `product-registry-read-service` gère la lecture.
* **Outbox Pattern** : Implémenté via `libs/cqrs-support`. Lors d'une modification, l'agrégat est sauvegardé et l'événement est inséré dans la table `Outbox` dans la même transaction SQL.

### 3. Que fait la bibliothèque `libs/cqrs-support` ? Comment est-elle utilisée dans les autres modules ?

Cette bibliothèque fournit le socle technique pour le CQRS et la gestion des événements.
* **Rôle** : Elle définit les interfaces génériques (`DomainEvent`, `Projector`) et fournit les entités JPA d'infrastructure (`OutboxEntity`, `EventLogEntity`).
* **Utilisation** :
    * Les **Domain Services** l'utilisent pour persister les événements dans la table Outbox de manière atomique.
    * Les **Read Services** l'utilisent pour polluer (lire) les événements et les projeter dans leurs bases de lecture via des `Projector`.

### 4. Que fait la bibliothèque `libs/bom-platform`

C'est un module de type **Platform / BOM (Bill of Materials)**.
Son rôle est de centraliser la gestion des versions de toutes les dépendances du projet (Quarkus, Lombok, MapStruct, etc.). Elle garantit que tous les modules (`apps` et `libs`) utilisent exactement les mêmes versions de bibliothèques, évitant ainsi les conflits de versions et simplifiant la maintenance.

### 5. Comment l'implémentation actuelle du CQRS et du Kernel assure-t-elle la fiabilité des états internes de l'application ?

La fiabilité repose sur le **Pattern Transactional Outbox**.
Dans une architecture distribuée, il est risqué de mettre à jour la base de données puis d'envoyer un message (si le message échoue, la base est incohérente avec le système de message).
L'application résout cela en :
1.  Exécutant la logique métier dans le **Kernel** pour garantir la validité des invariants.
2.  Sauvegardant l'état de l'agrégat **ET** l'événement métier dans la table `Outbox` au sein d'une **transaction unique**.
Cela garantit l'atomicité : soit tout est sauvegardé, soit rien ne l'est. L'événement sera ensuite traité de manière asynchrone mais garantie (At-least-once delivery).
